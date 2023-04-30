package com.example.service1.RestTemplateTest.controller;

import com.example.service1.RestTemplateTest.services.RestTemplateTestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import static com.sun.scenario.Settings.set;

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



}