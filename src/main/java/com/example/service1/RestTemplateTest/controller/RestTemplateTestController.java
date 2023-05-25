package com.example.service1.RestTemplateTest.controller;

import com.example.service1.RestTemplateTest.services.RestTemplateTestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;


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
    public ResponseEntity<?> restTemplateTest2() throws JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //client.taskoo.net 으로 /services 호출
        System.out.println("vlab-vra refresh token get");
        return ResponseEntity.ok(restTemplateTestService.callPostExternalServer2());
    }
    @RequestMapping("/rest/test3")
    public ResponseEntity<?> restTemplateTest3() throws JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //client.taskoo.net 으로 /services 호출
        System.out.println("vlab-vra access token get");
        return ResponseEntity.ok(restTemplateTestService.callPostExternalServer3());
    }

    @RequestMapping("/rest/test4")
    public ResponseEntity<?> restTemplateTest4() throws JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //client.taskoo.net 으로 /services 호출
        System.out.println("vlab-vra getDeployment");
        return ResponseEntity.ok(restTemplateTestService.callPostExternalServer4());
    }

    @RequestMapping("/rest/test5")
    public ResponseEntity<?> restTemplateTest5() throws JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //client.taskoo.net 으로 /services 호출
        System.out.println("vlab-vra PostDeployment");
        return ResponseEntity.ok(restTemplateTestService.callPostExternalServer5());
    }

    @RequestMapping("/rest/test6")
    public ResponseEntity<?> restTemplateTest6() throws JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //client.taskoo.net 으로 /services 호출
        System.out.println("action 실행하기");
        return ResponseEntity.ok(restTemplateTestService.callPostExternalServer6());
    }



}