package com.example.service1.RestTemplateTest.services;

import com.example.service1.RestTemplateTest.models.Person;
import com.example.service1.RestTemplateTest.models.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
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

    public String callPostExternalServer2() throws JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        //refresh Token 받기
        //
        HashMap<String, Object> result = new HashMap<String, Object>();
        String jsonInString = "";


        RestTemplate restTemplate = this.makeRestTemplate();

        //
        //Header

        HttpHeaders header = new HttpHeaders();
        header.setContentType(APPLICATION_JSON);
        header.setAccept(Collections.singletonList(APPLICATION_JSON));
        //header.add("Authorization","bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiYXV0aG9yaXRpZXMiOlsiQU5PTllNT1VTIl0sImp0aSI6IjZhS0xuMVlJSmF1Rjc4Y2xLVm1Md1ZCOXBlRSIsImNsaWVudF9pZCI6ImF0dGljLWN1c3RvbWVyIn0.gaWZ9ldwDcnToWSCbtqt81kf7Rpce80ZgTLOCXyzi28");

        //
        // create param
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username","test5");
        jsonObject.addProperty("password","xptmxm5");


        HttpEntity<?> entity = new HttpEntity<String> (jsonObject.toString(), header);

        ResponseEntity<?> resultMap = restTemplate.exchange("https://vlab-vra.vmkloud.com/csp/gateway/am/api/login?access_token", HttpMethod.POST,entity , Object.class);

        result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
        result.put("header", resultMap.getHeaders()); //헤더 정보 확인
        result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

        ObjectMapper mapper = new ObjectMapper();
        jsonInString = mapper.writeValueAsString(resultMap.getBody());

        return jsonInString;

    }


    // accesstoken
    public String callPostExternalServer3() throws JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        //refresh Token 받기
        //
        HashMap<String, Object> result = new HashMap<String, Object>();
        String jsonInString = "";


        RestTemplate restTemplate = this.makeRestTemplate();

        //
        //Header

        HttpHeaders header = new HttpHeaders();
        header.setContentType(APPLICATION_JSON);
        header.setAccept(Collections.singletonList(APPLICATION_JSON));
        //header.add("Authorization","bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiYXV0aG9yaXRpZXMiOlsiQU5PTllNT1VTIl0sImp0aSI6IjZhS0xuMVlJSmF1Rjc4Y2xLVm1Md1ZCOXBlRSIsImNsaWVudF9pZCI6ImF0dGljLWN1c3RvbWVyIn0.gaWZ9ldwDcnToWSCbtqt81kf7Rpce80ZgTLOCXyzi28");

        //
        // create param
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("refreshToken","lbHaoEZwrsPxZMc3dxOThpCbLCJLUtGL");
        //jsonObject.addProperty("password","xptmxm5");


        HttpEntity<?> entity = new HttpEntity<String> (jsonObject.toString(), header);

        ResponseEntity<?> resultMap = restTemplate.exchange("https://vlab-vra.vmkloud.com/iaas/api/login", HttpMethod.POST,entity , Object.class);

        result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
        result.put("header", resultMap.getHeaders()); //헤더 정보 확인
        result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

        ObjectMapper mapper = new ObjectMapper();
        jsonInString = mapper.writeValueAsString(resultMap.getBody());

        return jsonInString;

    }

//eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjQzMzI0NDA1MDYzNjM1NDU3MDQifQ.eyJpc3MiOiJDTj1QcmVsdWRlIElkZW50aXR5IFNlcnZpY2UsT1U9Q01CVSxPPVZNd2FyZSxMPVNvZmlhLFNUPVNvZmlhLEM9QkciLCJpYXQiOjE2ODI4NDI4NTQsImV4cCI6MTY4Mjg3MTY1NCwianRpIjoiM2E4YjcwZTktNDIwOS00OTc1LWFlNDUtNzEzYzAzMTlkNTQ4IiwiY29udGV4dCI6Ilt7XCJtdGRcIjpcInVybjpvYXNpczpuYW1lczp0YzpTQU1MOjIuMDphYzpjbGFzc2VzOlBhc3N3b3JkUHJvdGVjdGVkVHJhbnNwb3J0XCIsXCJpYXRcIjoxNjgyODQyODU0LFwiaWRcIjoxNX1dIiwiYXpwIjoicHJlbHVkZS11c2VyLVBZN0lFU2tjbDciLCJzdWIiOiJ2bWtsb3VkLmNvbTo4OTljZTUzOS1hMTIwLTQxMjYtYjFjYi05ZTI3MDY0NDlkMzUiLCJkb21haW4iOiJ2bWtsb3VkLmNvbSIsInVzZXJuYW1lIjoidGVzdDUiLCJwZXJtcyI6WyJjc3A6b3JnX293bmVyIiwiZXh0ZXJuYWwvYTAyODJjMDAtNDZiOS00OTc1LWEzZjAtYWMyYTNlY2Y5OTJhL0NvZGVTdHJlYW06dXNlciIsImV4dGVybmFsL2EwMjgyYzAwLTQ2YjktNDk3NS1hM2YwLWFjMmEzZWNmOTkyYS9Db2RlU3RyZWFtOmV4ZWN1dG9yIiwiZXh0ZXJuYWwvMTJjNDdiMTEtYjRkYy00N2ZiLWFiNzctZjMyOGE3ZjJlNmY4L2F1dG9tYXRpb25zZXJ2aWNlOnVzZXIiLCJleHRlcm5hbC81Y2UxZWQwOS01NzE5LTQ2MTEtYTRhNi1lMzQ2NDgzMjk2YWMvY2F0YWxvZzp1c2VyIiwiZXh0ZXJuYWwvMTJjNDdiMTEtYjRkYy00N2ZiLWFiNzctZjMyOGE3ZjJlNmY4L21pZ3JhdGlvbjphZG1pbiIsImV4dGVybmFsL2EwMjgyYzAwLTQ2YjktNDk3NS1hM2YwLWFjMmEzZWNmOTkyYS9Db2RlU3RyZWFtOmFkbWluaXN0cmF0b3IiLCJleHRlcm5hbC8yYjIwOTRjNi00NGI2LTQwNjAtOTJjYy1mZWE0NWM1ZWEyOGMvc2FsdHN0YWNrOmFkbWluIiwiZXh0ZXJuYWwvMWNiOTVkNDItYzBjYy00MzA4LWEyMmQtNjQzNzc0ZjJkMTVjL29yY2hlc3RyYXRpb246YWRtaW4iLCJleHRlcm5hbC81Y2UxZWQwOS01NzE5LTQ2MTEtYTRhNi1lMzQ2NDgzMjk2YWMvY2F0YWxvZzphZG1pbiIsImV4dGVybmFsLzFjYjk1ZDQyLWMwY2MtNDMwOC1hMjJkLTY0Mzc3NGYyZDE1Yy9vcmNoZXN0cmF0aW9uOmRlc2lnbmVyIiwiZXh0ZXJuYWwvMTJjNDdiMTEtYjRkYy00N2ZiLWFiNzctZjMyOGE3ZjJlNmY4L2F1dG9tYXRpb25zZXJ2aWNlOmNsb3VkX2FkbWluIiwiZXh0ZXJuYWwvYTAyODJjMDAtNDZiOS00OTc1LWEzZjAtYWMyYTNlY2Y5OTJhL0NvZGVTdHJlYW06ZGV2ZWxvcGVyIiwiZXh0ZXJuYWwvMmIyMDk0YzYtNDRiNi00MDYwLTkyY2MtZmVhNDVjNWVhMjhjL3NhbHRzdGFjazp1c2VyIiwiZXh0ZXJuYWwvMmIyMDk0YzYtNDRiNi00MDYwLTkyY2MtZmVhNDVjNWVhMjhjL3NhbHRzdGFjazpyb290Il0sImNvbnRleHRfbmFtZSI6IjIzYjdlZWM3LTJiYmYtNDI3NS1hOTI4LTkwN2M0MThiYmEzYiIsImFjY3QiOiJ0ZXN0NSJ9.Zuqd7L4Rz9PlCmwbzvQK_Q1o3lc7H6jI2xrKj5EYQSReMNOE3gEVSRbj8SuF2Qt8y7Xy6Z6rfKP86ky7b0Xc4gz-4MAvf-GxZa61pDxDqF5KxyEJ2p9C3eWJlA7lIHS2qYYA-N4B0PWAQQ3JadJLXwtsLxt-Vfn9X_ZyFTyMmJGlZLo8Eq63NaABZyFNApEYkcn03x0tIoNvJoAElNiZOz5JDXKc1pSGKvtZbSL1QGc2msH7zUBG6LM74WUF_wKCm3smpIg7bzzKRMGuhT8higZ3x9rrg8dCEOA4UKOfvv8CFaABHdxFsnqLTYSd0Z12lro6Ndt9zZPFCwHNPKkmCA

    public String callPostExternalServer4() throws JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        //refresh Token 받기
        //
        HashMap<String, Object> result = new HashMap<String, Object>();
        String jsonInString = "";


        RestTemplate restTemplate = this.makeRestTemplate();

        //
        //Header

        HttpHeaders header = new HttpHeaders();
        header.setContentType(APPLICATION_JSON);
        header.setAccept(Collections.singletonList(APPLICATION_JSON));
        header.add("Authorization","bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjQzMzI0NDA1MDYzNjM1NDU3MDQifQ.eyJpc3MiOiJDTj1QcmVsdWRlIElkZW50aXR5IFNlcnZpY2UsT1U9Q01CVSxPPVZNd2FyZSxMPVNvZmlhLFNUPVNvZmlhLEM9QkciLCJpYXQiOjE2ODI4NDI4NTQsImV4cCI6MTY4Mjg3MTY1NCwianRpIjoiM2E4YjcwZTktNDIwOS00OTc1LWFlNDUtNzEzYzAzMTlkNTQ4IiwiY29udGV4dCI6Ilt7XCJtdGRcIjpcInVybjpvYXNpczpuYW1lczp0YzpTQU1MOjIuMDphYzpjbGFzc2VzOlBhc3N3b3JkUHJvdGVjdGVkVHJhbnNwb3J0XCIsXCJpYXRcIjoxNjgyODQyODU0LFwiaWRcIjoxNX1dIiwiYXpwIjoicHJlbHVkZS11c2VyLVBZN0lFU2tjbDciLCJzdWIiOiJ2bWtsb3VkLmNvbTo4OTljZTUzOS1hMTIwLTQxMjYtYjFjYi05ZTI3MDY0NDlkMzUiLCJkb21haW4iOiJ2bWtsb3VkLmNvbSIsInVzZXJuYW1lIjoidGVzdDUiLCJwZXJtcyI6WyJjc3A6b3JnX293bmVyIiwiZXh0ZXJuYWwvYTAyODJjMDAtNDZiOS00OTc1LWEzZjAtYWMyYTNlY2Y5OTJhL0NvZGVTdHJlYW06dXNlciIsImV4dGVybmFsL2EwMjgyYzAwLTQ2YjktNDk3NS1hM2YwLWFjMmEzZWNmOTkyYS9Db2RlU3RyZWFtOmV4ZWN1dG9yIiwiZXh0ZXJuYWwvMTJjNDdiMTEtYjRkYy00N2ZiLWFiNzctZjMyOGE3ZjJlNmY4L2F1dG9tYXRpb25zZXJ2aWNlOnVzZXIiLCJleHRlcm5hbC81Y2UxZWQwOS01NzE5LTQ2MTEtYTRhNi1lMzQ2NDgzMjk2YWMvY2F0YWxvZzp1c2VyIiwiZXh0ZXJuYWwvMTJjNDdiMTEtYjRkYy00N2ZiLWFiNzctZjMyOGE3ZjJlNmY4L21pZ3JhdGlvbjphZG1pbiIsImV4dGVybmFsL2EwMjgyYzAwLTQ2YjktNDk3NS1hM2YwLWFjMmEzZWNmOTkyYS9Db2RlU3RyZWFtOmFkbWluaXN0cmF0b3IiLCJleHRlcm5hbC8yYjIwOTRjNi00NGI2LTQwNjAtOTJjYy1mZWE0NWM1ZWEyOGMvc2FsdHN0YWNrOmFkbWluIiwiZXh0ZXJuYWwvMWNiOTVkNDItYzBjYy00MzA4LWEyMmQtNjQzNzc0ZjJkMTVjL29yY2hlc3RyYXRpb246YWRtaW4iLCJleHRlcm5hbC81Y2UxZWQwOS01NzE5LTQ2MTEtYTRhNi1lMzQ2NDgzMjk2YWMvY2F0YWxvZzphZG1pbiIsImV4dGVybmFsLzFjYjk1ZDQyLWMwY2MtNDMwOC1hMjJkLTY0Mzc3NGYyZDE1Yy9vcmNoZXN0cmF0aW9uOmRlc2lnbmVyIiwiZXh0ZXJuYWwvMTJjNDdiMTEtYjRkYy00N2ZiLWFiNzctZjMyOGE3ZjJlNmY4L2F1dG9tYXRpb25zZXJ2aWNlOmNsb3VkX2FkbWluIiwiZXh0ZXJuYWwvYTAyODJjMDAtNDZiOS00OTc1LWEzZjAtYWMyYTNlY2Y5OTJhL0NvZGVTdHJlYW06ZGV2ZWxvcGVyIiwiZXh0ZXJuYWwvMmIyMDk0YzYtNDRiNi00MDYwLTkyY2MtZmVhNDVjNWVhMjhjL3NhbHRzdGFjazp1c2VyIiwiZXh0ZXJuYWwvMmIyMDk0YzYtNDRiNi00MDYwLTkyY2MtZmVhNDVjNWVhMjhjL3NhbHRzdGFjazpyb290Il0sImNvbnRleHRfbmFtZSI6IjIzYjdlZWM3LTJiYmYtNDI3NS1hOTI4LTkwN2M0MThiYmEzYiIsImFjY3QiOiJ0ZXN0NSJ9.Zuqd7L4Rz9PlCmwbzvQK_Q1o3lc7H6jI2xrKj5EYQSReMNOE3gEVSRbj8SuF2Qt8y7Xy6Z6rfKP86ky7b0Xc4gz-4MAvf-GxZa61pDxDqF5KxyEJ2p9C3eWJlA7lIHS2qYYA-N4B0PWAQQ3JadJLXwtsLxt-Vfn9X_ZyFTyMmJGlZLo8Eq63NaABZyFNApEYkcn03x0tIoNvJoAElNiZOz5JDXKc1pSGKvtZbSL1QGc2msH7zUBG6LM74WUF_wKCm3smpIg7bzzKRMGuhT8higZ3x9rrg8dCEOA4UKOfvv8CFaABHdxFsnqLTYSd0Z12lro6Ndt9zZPFCwHNPKkmCA");

        //
        // create param
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("refreshToken","lbHaoEZwrsPxZMc3dxOThpCbLCJLUtGL");
        //jsonObject.addProperty("password","xptmxm5");


        HttpEntity<?> entity = new HttpEntity<String> (jsonObject.toString(), header);

        ResponseEntity<?> resultMap = restTemplate.exchange("https://vlab-vra.vmkloud.com/deployment/api/deployments", HttpMethod.GET,entity , Object.class);

        result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
        result.put("header", resultMap.getHeaders()); //헤더 정보 확인
        result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

        ObjectMapper mapper = new ObjectMapper();
        jsonInString = mapper.writeValueAsString(resultMap.getBody());

        return jsonInString;

    }





    private RestTemplate makeRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        requestFactory.setConnectTimeout(3 * 1000);

        requestFactory.setReadTimeout(3 * 1000);

        return new RestTemplate(requestFactory);
    }

}

