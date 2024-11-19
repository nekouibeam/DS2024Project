package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webFiliting.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SearchController {

    @GetMapping("/search")
    public Map<WebTree, String> search(@RequestParam String keyword) {
        GoogleQuery googleQuery = new GoogleQuery(keyword);
        Map<WebTree, String> results = new HashMap<>();
        
        try {
            results = googleQuery.query();
        } catch (IOException e) {
            e.printStackTrace();
            results.put(new WebTree(new WebPage(null, "error")), "An error occurred while fetching search results.");
        }
        
        return results;
    }
}
