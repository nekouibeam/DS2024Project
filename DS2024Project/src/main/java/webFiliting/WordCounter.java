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
        URL url = new URL(this.urlStr);
        URLConnection conn = url.openConnection();
        InputStream in = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        StringBuilder retVal = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            retVal.append(line).append("\n");
        }

        System.out.println("Fetched content for URL: " + this.urlStr);
        System.out.println(retVal.toString());

        return retVal.toString();
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