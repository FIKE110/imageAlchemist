package com.iyke.imagealchemist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication

@RestController
public class ImagealchemistApplication {
    
    @GetMapping("/error")
    public String error(){
        return "Error detected";
    }
    
    
	public static void main(String[] args) {
		SpringApplication.run(ImagealchemistApplication.class, args);
	}

}
