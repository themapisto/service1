package com.example.service1.RestTemplateTest.services;

import com.example.service1.RestTemplateTest.client.IpExtractor;
import com.example.service1.RestTemplateTest.models.Person;
import com.example.service1.RestTemplateTest.models.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
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
import java.util.Random;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class RestTemplateTestService {

    private ApiService<Response> apiService;
    private String bearertoken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjcyMTI2OTc5Mjk5MDc3Mjg5MzUifQ.eyJpc3MiOiJodHRwOi8vaWRlbnRpdHktc2VydmljZS5wcmVsdWRlLnN2Yy5jbHVzdGVyLmxvY2FsOjgwMDAiLCJpYXQiOjE2ODQ5OTI2NzAsImV4cCI6MTY4NTAyMTQ3MCwianRpIjoiZTgyOWM4NTktZTM4NS00ZTYzLWJlNGUtY2VjM2FmYjhlM2E2IiwiY29udGV4dCI6Ilt7XCJtdGRcIjpcInVybjpvYXNpczpuYW1lczp0YzpTQU1MOjIuMDphYzpjbGFzc2VzOlBhc3N3b3JkUHJvdGVjdGVkVHJhbnNwb3J0XCIsXCJpYXRcIjoxNjg0OTkyNjcwLFwiaWRcIjoxNX1dIiwiYXpwIjoicHJlbHVkZS11c2VyLTdwVm52MHBVM1AiLCJzdWIiOiJTeXN0ZW0gRG9tYWluOjA3YjI4M2QwLTA4ODctNDEwNy05Y2M0LWJiZDkzMzYzOTU1ZCIsImRvbWFpbiI6IlN5c3RlbSBEb21haW4iLCJ1c2VybmFtZSI6Im16YyIsInBlcm1zIjpbImNzcDpvcmdfb3duZXIiLCJleHRlcm5hbC9hOWQxY2E0OC1hZDg3LTQyOTYtYmVlZS1kY2UzMDJiOGE3ZTYvQ29kZVN0cmVhbTphZG1pbmlzdHJhdG9yIiwiZXh0ZXJuYWwvNmYwMDRlOGQtZjQxNC00YjU5LWE1OTMtZDhkNGJmNzAwNTIwL29yY2hlc3RyYXRpb246ZGVzaWduZXIiLCJleHRlcm5hbC83OTNhMWJjZi04NGRjLTQwYmUtYWUwNi1mZmQ1N2Y1OTNiYWIvYXV0b21hdGlvbnNlcnZpY2U6Y2xvdWRfYWRtaW4iLCJleHRlcm5hbC82ZjAwNGU4ZC1mNDE0LTRiNTktYTU5My1kOGQ0YmY3MDA1MjAvb3JjaGVzdHJhdGlvbjp2aWV3ZXIiLCJleHRlcm5hbC9kMTE5YzUxZi1jZDFkLTRlZDEtYmY5Zi1jMGEzNzk5MDhkZTcvY2F0YWxvZzphZG1pbiIsImV4dGVybmFsL2E5ZDFjYTQ4LWFkODctNDI5Ni1iZWVlLWRjZTMwMmI4YTdlNi9Db2RlU3RyZWFtOmRldmVsb3BlciIsImV4dGVybmFsL2I1NDJhZDhjLWYyYzMtNGJkMi04MDdhLTQyNGIyMGJkMTliZS9zYWx0c3RhY2s6YWRtaW4iLCJleHRlcm5hbC83OTNhMWJjZi04NGRjLTQwYmUtYWUwNi1mZmQ1N2Y1OTNiYWIvbWlncmF0aW9uOmFkbWluIiwiZXh0ZXJuYWwvNmYwMDRlOGQtZjQxNC00YjU5LWE1OTMtZDhkNGJmNzAwNTIwL29yY2hlc3RyYXRpb246YWRtaW4iXSwiY29udGV4dF9uYW1lIjoiYmFlMjhmMjYtNGZmZS00OTE3LTlhNGEtZTAwYzVlNGU2Mjg0IiwiYWNjdCI6Im16YyJ9.kVjMZvLjGYDpg6k3h9Qo62kx_B0jvM_ubV8lmg3KdgQTTpmYjgLuTDFFQHWGloV0O4RS5ZjPR8u0qkL3Zpfc89fR4ExnDosEudJnuNia5U_k6-db__nxKjRgEkA81qksxrqY7OtKuLdGLBQRcKSasuzAsanzjMTTNKJqWfg61-Rvygiqt4n-Hepj4Rl57fQBfKewmIuM-moGgLk4yf4922rXoa0QguXlw1faxqe1rjxfdheTGwoC_bZyh1-ypsiveemXp5VC4iDaqm1aMZADctE3v3eWre9HAPFNokkTxPRYhq3HNLyb2oIxCdXILl-mnNfjJtFviSkI9J4OAEOLqA";
    // private String url = "https://vra-cmp.matilda-mzc.com/csp/gateway/am/api/login?access_token";
    @Autowired
    public RestTemplateTestService(ApiService<Response> apiService) {
        this.apiService = apiService;
    }

    public Response callPostExternalServer() {
        Person person = new Person();
        person.setName("test");
        person.setAge(0);
        person.addInfo("id", "b05e2380-5501-4fd9-bcda-dd5cb6c15f83");
        person.addInfo("blueprintName", "test");
        person.addInfo("updatedBy", "mzc");
        person.addInfo("projectName", "Sample-Project");



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
        jsonObject.addProperty("username","mzc");
        jsonObject.addProperty("password","Matilda00!");


        HttpEntity<?> entity = new HttpEntity<String> (jsonObject.toString(), header);

        ResponseEntity<?> resultMap = restTemplate.exchange("https://vra-cmp.matilda-mzc.com/csp/gateway/am/api/login?access_token", HttpMethod.POST,entity , Object.class);

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
        jsonObject.addProperty("refreshToken","G7oD3ut8nLcjqMgxBildbUJP8NRD1imT");
        //jsonObject.addProperty("password","xptmxm5");


        HttpEntity<?> entity = new HttpEntity<String> (jsonObject.toString(), header);

        ResponseEntity<?> resultMap = restTemplate.exchange("https://vra-cmp.matilda-mzc.com/iaas/api/login", HttpMethod.POST,entity , Object.class);

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
        header.add("Authorization","Bearer " + bearertoken);
        //param add
        JsonObject jsonObject = new JsonObject();
        //jsonObject.addProperty("password","xptmxm5");


        HttpEntity<?> entity = new HttpEntity<String> (jsonObject.toString(), header);

        ResponseEntity<?> resultMap = restTemplate.exchange("https://vra-cmp.matilda-mzc.com/deployment/api/deployments", HttpMethod.GET,entity , Object.class);

        result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
        result.put("header", resultMap.getHeaders()); //헤더 정보 확인
        result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

        ObjectMapper mapper = new ObjectMapper();
        jsonInString = mapper.writeValueAsString(resultMap.getBody());

        return jsonInString;

    }

    public String callPostExternalServer5() throws JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

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
        header.add("Authorization","Bearer " + bearertoken);

        // create param
        // create param
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("blueprintId", "b05e2380-5501-4fd9-bcda-dd5cb6c15f83");
        jsonObject.addProperty("blueprintVersion", "4");
        Random rand = new Random();
        int randomNumber = rand.nextInt(101); // 0~10 사이의 임의의 정수
        System.out.println(randomNumber);
        jsonObject.addProperty("deploymentName", "5.blueprint_API Test"+randomNumber);
        jsonObject.addProperty("destroy", false);
        jsonObject.addProperty("ignoreDeleteFailures", false);

        JsonObject inputsObject = new JsonObject();
        inputsObject.addProperty("name_", "koo_vm_test");
        inputsObject.addProperty("flavor_", "small");
        inputsObject.addProperty("image_", "ubuntu20.04");
        // Available IP Address API 사용
        inputsObject.addProperty("address", "10.20.4." + randomNumber);

        jsonObject.add("inputs", inputsObject);

        jsonObject.addProperty("plan", false);
        jsonObject.addProperty("projectId", "eba37079-913c-4ed2-9fbb-6c6b9fae284e");
        jsonObject.addProperty("reason", "string");

        HttpEntity<?> entity = new HttpEntity<String> (jsonObject.toString(), header);

        ResponseEntity<?> resultMap = restTemplate.exchange("https://vra-cmp.matilda-mzc.com/blueprint/api/blueprint-requests", HttpMethod.POST,entity , Object.class);

        result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
        result.put("header", resultMap.getHeaders()); //헤더 정보 확인
        result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

        ObjectMapper mapper = new ObjectMapper();
        jsonInString = mapper.writeValueAsString(resultMap.getBody());

        return jsonInString;

    }

    public int callPostExternalServer6() throws JsonProcessingException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

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
        header.add("Authorization","Bearer " + bearertoken);
        // create param
        // create param
        Gson gson = new Gson();
        JsonObject json = new JsonObject();

        JsonObject except = new JsonObject();
        except.addProperty("name", "except");
        except.addProperty("type", "string");

        JsonObject exceptValue = new JsonObject();
        JsonObject exceptString = new JsonObject();
        exceptString.addProperty("value", "");
        exceptValue.add("string", exceptString);
        except.add("value", exceptValue);

        JsonObject search = new JsonObject();
        search.addProperty("name", "search");
        search.addProperty("type", "string");

        JsonObject searchValue = new JsonObject();
        JsonObject searchString = new JsonObject();
        searchString.addProperty("value", "");
        searchValue.add("string", searchString);
        search.add("value", searchValue);

        json.add("parameters", gson.toJsonTree(new JsonObject[]{except, search}));

        System.out.println(json.toString());




        HttpEntity<?> entity = new HttpEntity<String> (json.toString(), header);

        ResponseEntity<?> resultMap = restTemplate.exchange("https://vra-cmp.matilda-mzc.com/vco/api/actions/beddd12a-bc15-4e13-9534-18b5d6218acd/executions", HttpMethod.POST,entity , Object.class);

        result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
        result.put("header", resultMap.getHeaders()); //헤더 정보 확인
        result.put("body", resultMap.getBody()); //실제 데이터 정보 확인

        ObjectMapper mapper = new ObjectMapper();
        jsonInString = mapper.writeValueAsString(resultMap.getBody());

        JsonArray values = IpExtractor.extractValues(jsonInString);
        System.out.println(values);
        Gson gson1 =new Gson();
        String result123=gson1.toJson(values);

        JsonArray jsonArray = JsonParser.parseString(result123).getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            System.out.println(jsonArray.get(i).getAsString());
        }


        return jsonArray.size();

    }





    private RestTemplate makeRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = SSLContexts.custom()
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

