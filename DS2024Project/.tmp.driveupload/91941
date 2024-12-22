package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import webFiliting.*;

public class Main 
{
    public static void main(String[] args) 
    {
        try 
        {
        	/*String retVal = "";

    		URL u = new URL("https://activity.books.com.tw/crosscat/show/A00000032518");
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
    		}*/
    		
        	GoogleQuery.createKeywordList();
        	Comparator<WebTree> comparator = new Comparator<WebTree>() {

    			@Override
    			public int compare(WebTree x, WebTree y) {
    				// 降序排列
    				return (int) (y.getRoot().nodeScore - x.getRoot().nodeScore);
    			}
    		};
    		TreeMap<WebTree, String> webs = new TreeMap<WebTree, String>(comparator);
    		WebTree tree1 = new WebTree(new WebPage("https://activity.books.com.tw/crosscat/show/A00000032518", "root"));
    		WebTree tree2 = new WebTree(new WebPage("https://zh.moegirl.org.cn/zh-tw/%E5%88%80%E5%89%91%E7%A5%9E%E5%9F%9F", "root"));
        	WebTree tree3 = new WebTree(new WebPage("https://zh.wikipedia.org/zh-tw/%E5%88%80%E5%8A%8D%E7%A5%9E%E5%9F%9F", "root"));
        	webs.put(tree1,"https://activity.books.com.tw/crosscat/show/A00000032518");
        	webs.put(tree2,"https://zh.moegirl.org.cn/zh-tw/%E5%88%80%E5%89%91%E7%A5%9E%E5%9F%9F");
        	webs.put(tree3,"https://zh.wikipedia.org/zh-tw/%E5%88%80%E5%8A%8D%E7%A5%9E%E5%9F%9F");
    		
        	tree1.eularPrintTree();
        	tree2.eularPrintTree();
        	tree3.eularPrintTree();
        	
        	List<Map<String, String>> formattedResults = new ArrayList<>();
        	for (Map.Entry<WebTree, String> entry : webs.entrySet()) {
	            WebTree webTree = entry.getKey();
	            String url = entry.getValue();
	            
	            // 將 WebTree 和 URL 組裝成結構化 JSON
	            Map<String, String> formattedEntry = new HashMap<>();
	            formattedEntry.put("title", webTree.getRootName()); // 假設 WebTree 有 getRootName 方法
	            formattedEntry.put("url", url);
	            formattedResults.add(formattedEntry);
	        }
        	
        	// **輸出 formattedResults 內的內容**
        	System.out.println("Formatted Results:");
        	for (Map<String, String> result : formattedResults) {
        	    System.out.println("Title: " + result.get("title"));
        	    System.out.println("URL: " + result.get("url"));
        	    System.out.println("----------------------");
        	}
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}     


