package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}


//操作手則 請先執行這個檔案，然後打開瀏覽器輸入http://localhost:8080/search.html
//端口被佔用處理:
//1.
//netstat -ano | findstr 8080
//2.
//taskkill /PID 17204 /F
