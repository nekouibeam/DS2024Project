package webFiliting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class WordCounter {
    private String urlStr;
    private String content;

    public WordCounter(String urlStr) {
        this.urlStr = urlStr;
    }

    private String fetchContent() throws IOException, UnknownHostException {
    	String retVal = "";
    	URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
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

    public int countKeyword(String keyword) throws IOException, UnknownHostException {
        if (content == null) {
            content = fetchContent();
        }

        content = content.toUpperCase();
        keyword = keyword.toUpperCase();

        int retVal = 0;
        int fromIdx = 0;
        int found;

        while ((found = content.indexOf(keyword, fromIdx)) != -1) {
            retVal++;
            fromIdx = found + keyword.length();
        }

        System.out.println("Keyword '" + keyword + "' found " + retVal + " times in URL: " + this.urlStr);

        return retVal;
    }
}