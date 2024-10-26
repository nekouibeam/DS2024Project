package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class BmiService {

    public double calculateBmi(double heightCm, double weightKg) {
        double heightM = heightCm / 100;
        return weightKg / (heightM * heightM);
    }

    public String getBmiCategory(double bmi) {
        if (bmi < 18.5) {
            return "體重過輕";
        } else if (bmi < 24) {
            return "正常範圍";
        } else if (bmi < 27) {
            return "過重";
        } else if (bmi < 30) {
            return "輕度肥胖";
        } else if (bmi < 35) {
            return "中度肥胖";
        } else {
            return "重度肥胖";
        }
    }
}
