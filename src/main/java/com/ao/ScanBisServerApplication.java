package com.ao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScanBisServerApplication {
	public static void main(String[] args) {
		var context = SpringApplication.run(ScanBisServerApplication.class, args);
	}

}
