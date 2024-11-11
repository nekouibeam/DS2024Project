package tA1014;

import java.io.*;

public class Bmi_main {
	public static void main(String args[]) throws IOException {
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in)); 
		System.out.print("Please enter your weight(kg) and height(cm): "); 
		String text = buf.readLine(); 
		String[] data = text.split(" ");
		double res = getBMI(data);
		String dia = getDiagnosis(res);
		System.out.println(dia + " BMI: " + res);               
	}
	
	public static double getBMI(String[] data) {
		// 1. calculate the bmi 
		// result bmi = weight(kg) / (height(m) * height(m))
		double weight = Double.parseDouble(data[0]);
		double height = Double.parseDouble(data[1]) / 100;
		double bmi = weight / (height * height);
		return bmi;
	}
	
	
	
	public static String getDiagnosis(double bmi) {
		// 2. give comments depending on bmi
		String re;
		if(bmi >= 30) {
			re = "You are not in shape. Actually, you are not even close.";
		}else if(bmi < 30 && bmi >=26) {
			re = "To be honest, you are not in shape.";
		}else if(bmi < 26 && bmi >=20) {
			re = "You are in shape.";
		}else {
			re = "You are under shape.";
		}
        return re;
	}
}
