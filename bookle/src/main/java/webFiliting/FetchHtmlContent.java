package webFiliting;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.SocketTimeoutException;

public class FetchHtmlContent {

    public String fetchContent(String url) throws IOException, SocketTimeoutException, HttpStatusException{
        String retVal = "";

        // 使用 Jsoup 訪問網頁並設置 User-Agent
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.6056.2 Safari/537.36")
                //.timeout(2000)
                .get();

        // 返回網頁內容
        retVal = doc.html();  // 獲得整個 HTML 網頁內容

        return retVal;
    }
}