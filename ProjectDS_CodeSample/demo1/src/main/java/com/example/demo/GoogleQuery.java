package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery {
	public String searchKeyword;
	public String url;
	public String content;

	public GoogleQuery(String searchKeyword) {
		this.searchKeyword = searchKeyword;
		try {
			// This part has been specially handled for Chinese keyword processing.
			// You can comment out the following two lines
			// and use the line of code in the lower section.
			// Also, consider why the results might be incorrect
			// when entering Chinese keywords.
			String encodeKeyword = java.net.URLEncoder.encode((searchKeyword + " 小說"), "utf-8");
			this.url = "https://www.google.com/search?q=" + encodeKeyword + "&oe=utf8&num=20";

			// this.url =
			// "https://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=20";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private String fetchContent() throws IOException {
		String retVal = "";

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		// set HTTP header
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

	public HashMap<String, String> query() throws IOException {
		if (content == null) {
			content = fetchContent();
		}

		HashMap<String, String> retVal = new HashMap<String, String>();

		Document doc = Jsoup.parse(content);

		Elements lis = doc.select("div");
		lis = lis.select(".kCrYT");

		for (Element li : lis) {
			try {
				String citeUrl = li.select("a").get(0).attr("href").replace("/url?q=", "");
				citeUrl = URLDecoder.decode(citeUrl, "UTF-8");
				int index = citeUrl.indexOf("&");
				if (index != -1) {
					citeUrl = citeUrl.substring(0, index);
				}
				String title = li.select("a").get(0).select(".vvjwJb").text();

				if (title.equals("")) {
					continue;
				}

				System.out.println("Title: " + title + " , url: " + citeUrl);

				retVal.put(title, citeUrl);

			} catch (IndexOutOfBoundsException e) {
				// e.printStackTrace();
			}
		}

		return retVal;
	}
}