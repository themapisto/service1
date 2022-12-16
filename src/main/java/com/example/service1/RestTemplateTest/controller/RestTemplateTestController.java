package com.example.service1.RestTemplateTest.controller;

import com.example.service1.RestTemplateTest.services.RestTemplateTestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTemplateTestController {
    private RestTemplateTestService restTemplateTestService;
    @Autowired
    public RestTemplateTestController(RestTemplateTestService restTemplateTestService) {
        this.restTemplateTestService =restTemplateTestService;
    }

    @RequestMapping("/rest/test1")
    public ResponseEntity<?> restTemplateTest1() {
        return ResponseEntity.ok(restTemplateTestService.callPostExternalServer());
    }
    @RequestMapping("/rest/test2")
    public ResponseEntity<?> restTemplateTest2() throws JsonProcessingException {
        System.out.println("error ㄴㅏㅆ어요!!@!@!@!@");
        return ResponseEntity.ok(restTemplateTestService.callPostExternalServer2());
    }
}
