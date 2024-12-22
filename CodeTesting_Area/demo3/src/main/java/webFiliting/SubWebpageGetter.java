package webFiliting;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;

import org.jsoup.HttpStatusException;
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
		String baseUrl = extractBaseUrl(rootNode.webPage.url);

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

			// 限制提取的子連結數量
			if (subWebNum > 2 || tryTime > 10)
				break;
			
			// 通用過濾規則
			if (href.startsWith("#") || href.startsWith("javascript:") || href.startsWith("//") || href.equals(baseUrl)
					|| href.contains("{{") || href.contains("}}") || href.contains("home.php") || href.isEmpty()) {
				System.out.println("Skipping invalid or non-interesting link: " + href);
				tryTime++;
				continue;
			}

			// 檢查是否為圖片連結
			if (link.children().size() == 1 && link.child(0).tagName().equals("img")) {
				System.out.println("Skipping image-only link: " + href);
				tryTime++;
				continue;
			}

			// 處理相對路徑
			if (href.startsWith("/")) {
				System.out.println("/ start: " + href);
				href = baseUrl + href;
			}

			try {
				new URL(href); // 驗證 URL 是否有效 //throws MalformedURLException
				rootNode.addChild(new WebNode(new WebPage(href, link.text()))); // throws SocketTimeoutException, HttpStatusException
				subWebNum++;
			} catch (MalformedURLException e) {
				System.out.println("Skipping invalid URL: " + href);
				String fullMessage = e.toString(); // e.toString() 包含完整類名和訊息
				String firstLine = fullMessage.split("\n")[0]; // 提取第一行訊息
				System.out.println(firstLine);
				tryTime++;
			} catch (SocketTimeoutException e) {
				// TODO: handle exception
				System.out.println("Time Out" + href);
				String fullMessage = e.toString(); // e.toString() 包含完整類名和訊息
				String firstLine = fullMessage.split("\n")[0]; // 提取第一行訊息
				System.out.println(firstLine);
				tryTime++;
			} catch (HttpStatusException e) {
				// TODO: handle exception
				String fullMessage = e.toString(); // e.toString() 包含完整類名和訊息
				String firstLine = fullMessage.split("\n")[0]; // 提取第一行訊息
				//if(firstLine.contains("Status=400")) {
				//	e.printStackTrace();
				//}
				System.out.println(firstLine);
			} catch (Exception e) {
				// TODO: handle exception
				String fullMessage = e.toString(); // e.toString() 包含完整類名和訊息
				String firstLine = fullMessage.split("\n")[0]; // 提取第一行訊息
				System.out.println(firstLine);
				tryTime++;
			}
		}
	}

	private String extractBaseUrl(String url) {
		try {
			URL fullUrl = new URL(url);
			return fullUrl.getProtocol() + "://" + fullUrl.getHost();
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
			return "";
		}
	}

	private Elements filterWikipediaLinks(Document doc, Elements allLinks) {
		Elements wikipediaExcludedLinks = doc.select("div[role='note'] a[href], " + // 跳過 <div role="note"> 內的連結
				"table.ambox a[href], " + // 跳過 <table class="ambox"> 內的連結
				"div#spoiler a[href]" // 跳過 <div id="spoiler"> 內的連結
		);
		allLinks.removeAll(wikipediaExcludedLinks);
		return allLinks;
	}

	private Elements filterMoegirlLinks(Document doc, Elements allLinks) {
		Elements moegirlExcludedLinks = doc.select("div.notice a[href], " + // 跳過 <div class="notice"> 內的連結
				"div.infoBox a[href], " + // 跳過 <div class="infoBox"> 內的連結
				"table.moe-infobox a[href], " + // 跳過 <table class="moe-infobox"> 內的連結
				"div#mobile-noteTA-0 a[href]" // 跳過 <div id="mobile-noteTA-0"> 內的連結
		);
		allLinks.removeAll(moegirlExcludedLinks);
		return allLinks;
	}
}
