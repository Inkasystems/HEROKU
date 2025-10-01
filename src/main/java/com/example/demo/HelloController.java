package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "¡Hola desde Heroku!FADSHFJDSKAHFAJSDHFKJDSAHKJFSD";
    }
}