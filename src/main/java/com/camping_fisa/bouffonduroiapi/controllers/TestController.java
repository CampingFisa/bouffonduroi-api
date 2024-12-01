package com.camping_fisa.bouffonduroiapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class TestController {

    @GetMapping("/test")
    public String testEndpoint() {
        return "L'api fonctionne correctement en production V2.";
    }
}