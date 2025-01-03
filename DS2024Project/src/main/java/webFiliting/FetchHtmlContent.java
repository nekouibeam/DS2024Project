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

/*package webFiliting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class FetchHtmlContent {
	public String fetchContent(String url) throws IOException {
		String retVal = "";

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		// set HTTP header
		//當程式需要以自動化方式訪問網頁時，使用 User-Agent 模擬瀏覽器，讓伺服器誤認為這是一個真實的瀏覽器訪問。
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while ((line = bufReader.readLine()) != null) {
			retVal += line;
		}
		return retVal;
	}
}*/