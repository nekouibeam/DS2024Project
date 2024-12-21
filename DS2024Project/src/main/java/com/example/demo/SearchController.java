package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webFiliting.WebTree;

@CrossOrigin(origins = "http://localhost:8000") 
@RestController
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	@GetMapping("/search1")//一般搜尋
	public List<Map<String, String>> search(@RequestParam String keyword) {
	    logger.info("Search API called with keyword: " + keyword);
	    GoogleQuery googleQuery = new GoogleQuery(keyword);
	    List<Map<String, String>> formattedResults = new ArrayList<>();
	    
	    try {
	        Map<WebTree, String> results = googleQuery.query();
	        logger.info("GoogleQuery results received: " + results.size() + " items");
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
	        logger.error("Error during GoogleQuery execution: " + e.getMessage(), e);
	        Map<String, String> errorEntry = new HashMap<>();
	        errorEntry.put("title", "error");
	        errorEntry.put("url", "An error occurred while fetching search results.");
	        formattedResults.add(errorEntry);
	    }
	    
	    logger.info("Returning formatted results: " + formattedResults.size() + " items");
	    return formattedResults;
	}
	@GetMapping("/search2")//實體書目
	public List<Map<String, String>> search(@RequestParam String keyword) {
	    logger.info("Search API called with keyword: " + keyword);
	    GoogleQuery googleQuery = new GoogleQuery(keyword);
	    List<Map<String, String>> formattedResults = new ArrayList<>();
	    
	    try {
	        Map<WebTree, String> results = googleQuery.query();
	        logger.info("GoogleQuery results received: " + results.size() + " items");
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
	        logger.error("Error during GoogleQuery execution: " + e.getMessage(), e);
	        Map<String, String> errorEntry = new HashMap<>();
	        errorEntry.put("title", "error");
	        errorEntry.put("url", "An error occurred while fetching search results.");
	        formattedResults.add(errorEntry);
	    }
	    
	    logger.info("Returning formatted results: " + formattedResults.size() + " items");
	    return formattedResults;
	}
	@GetMapping("/search3")//同人作品
	public List<Map<String, String>> search(@RequestParam String keyword) {
	    logger.info("Search API called with keyword: " + keyword);
	    GoogleQuery googleQuery = new GoogleQuery(keyword);
	    List<Map<String, String>> formattedResults = new ArrayList<>();
	    
	    try {
	        Map<WebTree, String> results = googleQuery.query();
	        logger.info("GoogleQuery results received: " + results.size() + " items");
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
	        logger.error("Error during GoogleQuery execution: " + e.getMessage(), e);
	        Map<String, String> errorEntry = new HashMap<>();
	        errorEntry.put("title", "error");
	        errorEntry.put("url", "An error occurred while fetching search results.");
	        formattedResults.add(errorEntry);
	    }
	    
	    logger.info("Returning formatted results: " + formattedResults.size() + " items");
	    return formattedResults;
	}


}
