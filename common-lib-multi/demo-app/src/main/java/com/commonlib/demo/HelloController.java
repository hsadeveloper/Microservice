package com.commonlib.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String home() {
        return "Hello from demo-app (secured by common-lib)!";
    }
}
