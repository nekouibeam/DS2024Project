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
        processLinks(rootNode, rootNode.webPage.htmlString);
    }

    private void processLinks(WebNode parentNode, String contentString) throws IOException { 
        Document doc = Jsoup.parse(contentString);
        String baseUrl = UrlUtils.extractBaseUrl(parentNode.webPage.url);

        Elements allLinks = filterLinks(doc, parentNode.webPage.url);

        int subWebNum = 0;
        int tryTime = 0;

        // 定義跳步的質數
        int primeStep = 7; // 可以更改為其他質數
        int i = 0;

        // 使用 while 循環，確保能回到未處理的連結
        while (subWebNum <= 1 && tryTime <= 5) {
        	if(allLinks.size() == 0) {
        		break;
        	}
            Element link = allLinks.get(i % allLinks.size()); // 使用 % 確保索引有效
            String href = link.attr("href");
            href = URLDecoder.decode(href, "UTF-8");

            if (!isValidLink(href, baseUrl, link)) {
                tryTime++;
                i += primeStep; // 跳過該連結，按質數跳步
                continue;
            }

            if (href.startsWith("/")) {
                href = baseUrl + href;
            }

            try {
                new URL(href); // 驗證 URL 是否有效
                WebNode childNode = new WebNode(new WebPage(href, link.text()));
                parentNode.addChild(childNode);
                subWebNum++;

                // 遞迴處理子頁面
                if (parentNode.getDepth() == 1) {
                    processLinks(childNode, childNode.webPage.htmlString);
                }
            } catch (MalformedURLException e) {
                logError("Invalid URL", href, e);
                tryTime++;
            } catch (SocketTimeoutException e) {
                logError("Timeout", href, e);
                tryTime++;
            } catch (HttpStatusException e) {
                logError("HTTP Status Exception", href, e);
            } catch (Exception e) {
                logError("General Exception", href, e);
                tryTime++;
            }

            i += primeStep; // 跳步加質數
        }
    }


    private Elements filterLinks(Document doc, String url) {
        Elements allLinks = doc.select("a[href]");
        Elements menuLinks = doc.select("nav a[href], .menu a[href], .navbar a[href], header a[href]");
        allLinks.removeAll(menuLinks);

        boolean isWikipedia = url.contains("wikipedia.org");
        boolean isMoegirl = url.contains("moegirl.org.cn");

        if (isWikipedia) {
            allLinks = filterWikipediaLinks(doc, allLinks);
        }

        if (isMoegirl) {
            allLinks = filterMoegirlLinks(doc, allLinks);
        }

        return allLinks;
    }

    private boolean isValidLink(String href, String baseUrl, Element link) {
        return !(href.startsWith("#") || href.startsWith("javascript:") || href.startsWith("//") || href.equals(baseUrl)
                || href.contains("{{") || href.contains("}}") || href.contains("home.php") || href.isEmpty()
                || (link.children().size() == 1 && link.child(0).tagName().equals("img")));
    }

    private void logError(String errorType, String href, Exception e) {
        System.out.println(errorType + ": " + href);
        System.out.println(e.toString().split("\n")[0]);
    }

    private Elements filterWikipediaLinks(Document doc, Elements allLinks) {
        Elements wikipediaExcludedLinks = doc.select("div[role='note'] a[href], " +
                "table.ambox a[href], " +
                "div#spoiler a[href]");
        allLinks.removeAll(wikipediaExcludedLinks);
        return allLinks;
    }

    private Elements filterMoegirlLinks(Document doc, Elements allLinks) {
        Elements moegirlExcludedLinks = doc.select("div.notice a[href], " +
                "div.infoBox a[href], " +
                "table.moe-infobox a[href], " +
                "div#mobile-noteTA-0 a[href]");
        allLinks.removeAll(moegirlExcludedLinks);
        return allLinks;
    }
}

// Utility class for common URL operations
class UrlUtils {
    public static String extractBaseUrl(String url) {
        try {
            URL fullUrl = new URL(url);
            return fullUrl.getProtocol() + "://" + fullUrl.getHost();
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
