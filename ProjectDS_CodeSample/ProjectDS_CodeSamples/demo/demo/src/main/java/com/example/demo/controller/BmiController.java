package com.example.demo.controller;

import com.example.demo.service.BmiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BmiController {

    @Autowired
    private BmiService bmiService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/calculate")
    public String calculateBmi(@RequestParam("height") double height,
                               @RequestParam("weight") double weight,
                               Model model) {
        double bmi = bmiService.calculateBmi(height, weight);
        String category = bmiService.getBmiCategory(bmi);
        model.addAttribute("bmi", bmi);
        model.addAttribute("category", category);
        return "result";
    }

    // API端點
    @GetMapping("/api/bmi")
    @ResponseBody
    public BmiResponse getBmi(@RequestParam("height") double height,
                              @RequestParam("weight") double weight) {
        double bmi = bmiService.calculateBmi(height, weight);
        String category = bmiService.getBmiCategory(bmi);
        return new BmiResponse(bmi, category);
    }

    // BmiResponse內部類
    public static class BmiResponse {
        private double bmi;
        private String category;

        public BmiResponse(double bmi, String category) {
            this.bmi = bmi;
            this.category = category;
        }

        public double getBmi() {
            return bmi;
        }

        public String getCategory() {
            return category;
        }
    }
}
