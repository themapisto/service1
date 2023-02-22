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
        //postman
        return ResponseEntity.ok(restTemplateTestService.callPostExternalServer());
    }
    @RequestMapping("/rest/test2")
    public ResponseEntity<?> restTemplateTest2() throws JsonProcessingException {
        //client.taskoo.net 으로 /services 호출
        System.out.println("client 호출할거야");
        return ResponseEntity.ok(restTemplateTestService.callPostExternalServer2());
    }
    @RequestMapping("/rest/test3")
    public ResponseEntity<?> restTemplateTest3() {
        return ResponseEntity.ok(restTemplateTestService.callPostExternalServer3());
    }

}