package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webFiliting.WebTree;

@RestController
public class SearchController {

    @GetMapping("/search1")
    public List<Map<String, String>> searchNovel(@RequestParam String keyword) {
        return performSearch(keyword, " 小說");
    }

    @GetMapping("/search2")
    public List<Map<String, String>> searchPhysical(@RequestParam String keyword) {
        return performSearch(keyword, " 實體書");
    }

    @GetMapping("/search3")
    public List<Map<String, String>> searchFanfic(@RequestParam String keyword) {
        return performSearch(keyword, " 同人");
    }

    // 抽取共同的搜索邏輯到私有方法
    private List<Map<String, String>> performSearch(String keyword, String type) {
        GoogleQuery googleQuery = new GoogleQuery(keyword, type);
        List<Map<String, String>> formattedResults = new ArrayList<>();
        
        System.out.println("Searching for: " + keyword + " with type: " + type);
        System.out.println("Output all scores:");
        
        try {
            Map<WebTree, String> results = googleQuery.query();
            for (Map.Entry<WebTree, String> entry : results.entrySet()) {
                WebTree webTree = entry.getKey();
                String url = entry.getValue();
                
                System.out.println(webTree.getRoot().webPage.score);
                
                Map<String, String> formattedEntry = new HashMap<>();
                formattedEntry.put("title", webTree.getRootName());
                formattedEntry.put("url", url);
                formattedResults.add(formattedEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> errorEntry = new HashMap<>();
            errorEntry.put("title", "Error");
            errorEntry.put("url", "An error occurred while fetching search results.");
            formattedResults.add(errorEntry);
        }
        
        System.out.println("Formatted Results:");
        for (Map<String, String> result : formattedResults) {
            System.out.println("Title: " + result.get("title"));
            System.out.println("URL: " + result.get("url"));
            System.out.println("----------------------");
        }
        
        return formattedResults;
    }
}