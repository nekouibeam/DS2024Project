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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
			String encodeKeyword = java.net.URLEncoder.encode((searchKeyword + searchType), "utf-8");
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
		// 當程式需要以自動化方式訪問網頁時，使用 User-Agent 模擬瀏覽器，讓伺服器誤認為這是一個真實的瀏覽器訪問。
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

	public List<Map.Entry<WebTree, String>> query() throws IOException {
		if (content == null) {
			content = fetchContent();
		}
		
		List<Map.Entry<WebTree, String>> webs = new ArrayList<>();
		// using Jsoup analyze html string
		Document doc = Jsoup.parse(content);
		// select particular element(tag) which you want
		Elements lis = doc.select("div");
		lis = lis.select(".kCrYT");
		int time = 0;
		for (Element li : lis) {
			if(time>=10) {
				break;
			}
			
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

				// put title and pair into TreeMap
				if (isValidURL(citeUrl)) {
					try {
						WebPage page = new WebPage(citeUrl, title);
						webs.add(Map.entry(new WebTree(page), citeUrl));
					} catch (Exception e) {
						// TODO: handle exception
						String fullMessage = e.toString(); // e.toString() 包含完整類名和訊息
						String firstLine = fullMessage.split("\n")[0]; // 提取第一行訊息
						System.out.println(firstLine);
						continue;
					}
				}

			} catch (IndexOutOfBoundsException e) {
				continue;
			}
			time++;
		}
		
		// Sort the list based on nodeScore in descending order
		  webs.sort((entry1, entry2) -> Double.compare(
		      entry2.getKey().getRoot().nodeScore, 
		      entry1.getKey().getRoot().nodeScore
		  ));
		  
		  // Verify sorted results
		    System.out.println("\n" + "Sorted results:");
		    webs.forEach(entry -> {
		        System.out.println("Title: " + entry.getKey().getRoot().webPage.name 
		            + " Score: " + entry.getKey().getRoot().nodeScore);
		    });
		    
		return webs;
	}

	private boolean isValidURL(String citeUrl) {
		try {
			@SuppressWarnings("unused")
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
		keywordList.add(new Keyword("免費", 20));
		keywordList.add(new Keyword("線上", 15));
		keywordList.add(new Keyword("同人", 25)); 
		keywordList.add(new Keyword("ISBN", 25));
		keywordList.add(new Keyword("作者", 20));
		keywordList.add(new Keyword("書評", 18));   
		keywordList.add(new Keyword("實體書", 20));    
		keywordList.add(new Keyword("圖書館", 18));    
		keywordList.add(new Keyword("書店", 15));   
		keywordList.add(new Keyword("出版年份", 15));   
	}
}