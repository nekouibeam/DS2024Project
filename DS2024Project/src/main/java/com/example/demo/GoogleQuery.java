package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Comparator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import webFiliting.*;

public class GoogleQuery {
	public String searchKeyword;
	public String url;
	public String content;
	public static ArrayList<Keyword> keywordList;

	public GoogleQuery(String searchKeyword, String searchType) {
		this.searchKeyword = searchKeyword;
		createKeywordList();
		for (Keyword keyword : keywordList) {
			System.out.println(keyword.name);
		}
		try {
			// This part has been specially handled for Chinese keyword processing.
			// You can comment out the following two lines
			// and use the line of code in the lower section.
			// Also, consider why the results might be incorrect
			// when entering Chinese keywords.
			String encodeKeyword = java.net.URLEncoder.encode(searchKeyword, "utf-8");
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

	public TreeMap<WebTree, String> query() throws IOException {
		if (content == null) {
			content = fetchContent();
		}
		Comparator<WebTree> comparator = new Comparator<WebTree>() {

			@Override
			public int compare(WebTree x, WebTree y) {
				// 降序排列
				return (int) (y.getRoot().nodeScore - x.getRoot().nodeScore);
			}
		};
		TreeMap<WebTree, String> webs = new TreeMap<WebTree, String>(comparator);
		// using Jsoup analyze html string
		Document doc = Jsoup.parse(content);
		// select particular element(tag) which you want
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

				// put title and pair into HashMap
				if (isValidURL(citeUrl)) {
					webs.put(new WebTree(new WebPage(citeUrl, title)), citeUrl);
				}

			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}
		return webs;
	}

	private boolean isValidURL(String citeUrl) {
		try {
			URLConnection conn = new URL(citeUrl).openConnection();
		} catch (MalformedURLException e) {
			System.out.printf("MalformedURLException, skip");
			return false;
		} catch (UnknownHostException e) {
			System.out.printf("UnknownHostException, skip");
			return false;
		} catch (IOException e) {
			System.out.printf("IOException, skip");
			return false;
		}
		return true;
	}

	public static void createKeywordList() {
		keywordList = new ArrayList<>();
		keywordList.add(new Keyword("ISBN", 20));
		keywordList.add(new Keyword("作者", 15));
		keywordList.add(new Keyword("書評", 15));
		keywordList.add(new Keyword("免費觀看", 12));
		keywordList.add(new Keyword("付費觀看", 10));
		keywordList.add(new Keyword("同人作品", 8));
	}
}