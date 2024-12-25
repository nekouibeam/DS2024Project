package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webFiliting.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

	@GetMapping("/search1")
    public List<Map<String, String>> searchNovel(@RequestParam String keyword) {
        return search(keyword, " 小說");
    }

    @GetMapping("/search2")
    public List<Map<String, String>> searchPhysical(@RequestParam String keyword) {
        return search(keyword, " 實體書");
    }

    @GetMapping("/search3")
    public List<Map<String, String>> searchFanfic(@RequestParam String keyword) {
        return search(keyword, " 同人");
    }
    
    
	public List<Map<String, String>> search(String keyword, String type) {
	    GoogleQuery googleQuery = new GoogleQuery(keyword,type);
	    List<Map<String, String>> formattedResults = new ArrayList<>();
	    
	    try {
	    	List<Map.Entry<WebTree, String>> results = googleQuery.query();
	        for (Map.Entry<WebTree, String> entry : results) {
	            WebTree webTree = entry.getKey();
	            String url = entry.getValue();
	            
	            // 將 WebTree 和 URL 組裝成結構化 JSON
	            Map<String, String> formattedEntry = new HashMap<>();
	            formattedEntry.put("title", webTree.getRootName()); // 假設 WebTree 有 getRootName 方法
	            formattedEntry.put("url", url);
	            formattedResults.add(formattedEntry);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        Map<String, String> errorEntry = new HashMap<>();
	        errorEntry.put("title", "error");
	        errorEntry.put("url", "An error occurred while fetching search results.");
	        formattedResults.add(errorEntry);
	    }
	    
	    //for testing
	    System.out.println("Formatted Results:");
    	for (Map<String, String> result : formattedResults) {
    	    System.out.println("Title: " + result.get("title"));
    	    System.out.println("URL: " + result.get("url"));
    	    System.out.println("----------------------");
    	}
    	
    	//return
	    return formattedResults;
	}

}