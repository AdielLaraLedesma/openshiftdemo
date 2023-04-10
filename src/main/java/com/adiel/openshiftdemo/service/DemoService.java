package com.adiel.openshiftdemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Service
public class DemoService {

    @Value("${api.key}")
    private String apiKey;

    public String getApiKey(){
        try{
            FileSystemResource resource = new FileSystemResource(apiKey);
            Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            return FileCopyUtils.copyToString(reader).trim();
        } catch (Exception e) {
            throw new RuntimeException("Falló al leer el archivo");
        }
    }

}
