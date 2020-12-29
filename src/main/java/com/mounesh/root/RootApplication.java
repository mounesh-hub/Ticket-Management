package com.mounesh.root;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
// @EnableEurekaClient
public class RootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RootApplication.class, args);
    }

    @GetMapping("/admin")
    public String getWelcomeMessage(){
        return "Welcome Admin";
    }

    @GetMapping("/user")
    public String getMessage(){
        return "Welcome User";
    }
}

