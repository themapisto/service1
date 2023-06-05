package com.example.service1.RestTemplateTest.controller;

import com.example.service1.RestTemplateTest.client.ExampleClient;
import com.example.service1.RestTemplateTest.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignClientController {

        @Autowired
        private ExampleClient userClient;

        @GetMapping("/users/{id}")
        public String getUserById(@PathVariable("id") Long id) {
            System.out.println("hello");
            return userClient.getUserById(id);
        }
    }

