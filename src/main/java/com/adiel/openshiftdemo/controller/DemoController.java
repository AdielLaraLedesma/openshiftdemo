package com.adiel.openshiftdemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Value("${version}")
    private String version;

    @GetMapping("/hello")
    public String hello(){
        return "Hola mundo \nVersion " + version;
    }

}
