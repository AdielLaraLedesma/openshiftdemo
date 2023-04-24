package com.adiel.openshiftdemo.controller;

import com.adiel.openshiftdemo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @Value("${api.version}")
    private String version;

    @GetMapping("/hello")
    public String hello(){
        return "Hola mundo \nVersion " + version;
    }

    @GetMapping("/apikey")
    public String getApiKey(){
        return demoService.getApiKey();
    }

}
