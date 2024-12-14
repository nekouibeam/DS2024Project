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

	@GetMapping("/search")
	public List<Map<String, String>> search(@RequestParam String keyword) {
	    GoogleQuery googleQuery = new GoogleQuery(keyword);
	    List<Map<String, String>> formattedResults = new ArrayList<>();
	    
	    try {
	        Map<WebTree, String> results = googleQuery.query();
	        for (Map.Entry<WebTree, String> entry : results.entrySet()) {
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
	    
	    return formattedResults;
	}

}
