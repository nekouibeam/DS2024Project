package webFiliting;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SubWebpageGetter {

	public void getSubWebPage(WebNode rootNode) throws IOException {
		String rootContentString;
		rootContentString = rootNode.webPage.htmlString;
		Document doc = Jsoup.parse(rootContentString);

		// 判斷是否為維基百科或萌娘百科
		boolean isWikipedia = rootNode.webPage.url.contains("wikipedia.org");
		boolean isMoegirl = rootNode.webPage.url.contains("moegirl.org.cn");

		// 設置基礎 URL
		String baseUrl = isWikipedia || isMoegirl ? extractBaseUrl(rootNode.webPage.url) : "";

		// 獲取所有連結，並過濾頁首選單連結
		Elements allLinks = doc.select("a[href]");
		Elements menuLinks = doc.select("nav a[href], .menu a[href], .navbar a[href], header a[href]");
		allLinks.removeAll(menuLinks); // 移除頁首選單連結

		// 如果是維基百科，過濾指定的區塊內的連結
		if (isWikipedia) {
			allLinks = filterWikipediaLinks(doc, allLinks);
		}

		// 如果是萌娘百科，過濾指定的區塊內的連結
		if (isMoegirl) {
			allLinks = filterMoegirlLinks(doc, allLinks);
		}

		int subWebNum = 0;
		int tryTime = 0;

		for (Element link : allLinks) {
			String href = link.attr("href");
			href = URLDecoder.decode(href, "UTF-8");

			 // 特別處理維基百科和萌娘百科的相對路徑
	        if ((isWikipedia || isMoegirl) && href.startsWith("/")) {
	            href = baseUrl + href;
	        }

			// 通用過濾規則
			if (href.startsWith("#") || href.startsWith("javascript:") || href.isEmpty()) {
				System.out.println("Skipping invalid or non-interesting link: " + href);
				continue;
			}

			// 檢查是否為圖片連結
			if (link.children().size() == 1 && link.child(0).tagName().equals("img")) {
				System.out.println("Skipping image-only link: " + href);
				continue;
			}

			// 限制提取的子連結數量
			if (subWebNum > 5 || tryTime > 5)
				break;

			try {
				new URL(href); // 驗證 URL 是否有效
				rootNode.addChild(new WebNode(new WebPage(href, link.text())));
				subWebNum++;
			} catch (Exception e) {
				System.out.println("Skipping invalid URL: " + href);
				tryTime++;
			}
		}
	}

	private String extractBaseUrl(String url) {
		try {
			URL fullUrl = new URL(url);
			return fullUrl.getProtocol() + "://" + fullUrl.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "";
		}
	}

	private Elements filterWikipediaLinks(Document doc, Elements allLinks) {
		Elements wikipediaExcludedLinks = doc.select(
				"div[role='note'] a[href], " + // 跳過 <div role="note"> 內的連結
				"table.ambox a[href], " + // 跳過 <table class="ambox"> 內的連結
				"div#spoiler a[href]" // 跳過 <div id="spoiler"> 內的連結
		);
		allLinks.removeAll(wikipediaExcludedLinks);
		return allLinks;
	}

	private Elements filterMoegirlLinks(Document doc, Elements allLinks) {
		Elements moegirlExcludedLinks = doc.select(
				"div.notice a[href], " + // 跳過 <div class="notice"> 內的連結
				"div.infoBox a[href], " + // 跳過 <div class="infoBox"> 內的連結
				"table.moe-infobox a[href], " + // 跳過 <table class="moe-infobox"> 內的連結
				"div#mobile-noteTA-0 a[href]" // 跳過 <div id="mobile-noteTA-0"> 內的連結
		);
		allLinks.removeAll(moegirlExcludedLinks);
		return allLinks;
	}
}
