package com.example.service1.RestTemplateTest.services;

import com.example.service1.RestTemplateTest.models.Person;
import com.example.service1.RestTemplateTest.models.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class RestTemplateTestService {

    private ApiService<Response> apiService;

    @Autowired
    public RestTemplateTestService(ApiService<Response> apiService) {
        this.apiService = apiService;
    }

    public Response callPostExternalServer() {
        Person person = new Person();
        person.setName("esther");
        person.setAge(30);
        person.addInfo("location", "용산");

        return apiService.post("https://postman-echo.com/post", HttpHeaders.EMPTY, person, Response.class).getBody();
    }

    public String callPostExternalServer2() throws JsonProcessingException {


        HashMap<String, Object> result = new HashMap<String, Object>();
        String jsonInString = "";


        RestTemplate restTemplate = new RestTemplate();

        //
        //Header

        HttpHeaders header = new HttpHeaders();
        header.setContentType(APPLICATION_JSON);
        header.setAccept(Collections.singletonList(APPLICATION_JSON));
        header.add("Authorization","bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiYXV0aG9yaXRpZXMiOlsiQU5PTllNT1VTIl0sImp0aSI6IjZhS0xuMVlJSmF1Rjc4Y2xLVm1Md1ZCOXBlRSIsImNsaWVudF9pZCI6ImF0dGljLWN1c3RvbWVyIn0.gaWZ9ldwDcnToWSCbtqt81kf7Rpce80ZgTLOCXyzi28");

        //
        // create param
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username","user001");
        jsonObject.addProperty("password","qwer1234");


        HttpEntity<?> entity = new HttpEntity<String> (jsonObject.toString(), header);

        ResponseEntity<?> resultMap = restTemplate.exchange("http://client.taskoo.net/services", HttpMethod.GET,entity , Object.class);

        result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
        result.put("header", resultMap.getHeaders()); //헤더 정보 확인
        result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

        ObjectMapper mapper = new ObjectMapper();
        jsonInString = mapper.writeValueAsString(resultMap.getBody());

        return jsonInString;

    }



    public Response callPostExternalServer3() {
        Person person = new Person();
        person.setName("ted");
        person.setAge(38);
        person.addInfo("location", "역삼");

        return apiService.post("https://postman-echo.com/post", HttpHeaders.EMPTY, person, Response.class).getBody();
    }
}