package webFiliting;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.example.demo.GoogleQuery;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Random;

public class FetchHtmlContent {
	
	private static final String[] USER_AGENTS = {
	        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.5304.107 Safari/537.36",
	        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36",
	        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36",
	        "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1",
	        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Firefox/106.0"
	    };

    public String fetchContent(String url) throws IOException, SocketTimeoutException, HttpStatusException{
        String retVal = "";

        // 使用 Jsoup 訪問網頁並設置 User-Agent
        Document doc = Jsoup.connect(url)
                .userAgent(getRandomUserAgent())
				.header("Accept-Encoding", "gzip, deflate, br")
        		.header("Connection", "keep-alive")
                .get();

        // 返回網頁內容
        retVal = doc.html();  // 獲得整個 HTML 網頁內容

        return retVal;
    }
    
    // 隨機選擇一個 User-Agent
    private String getRandomUserAgent() {
        Random random = new Random();
        int index = random.nextInt(USER_AGENTS.length);
        return USER_AGENTS[index];
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