package com.example.service1.RestTemplateTest.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private Person data;

    private List<String> services = new ArrayList<String>();
    private Map<String, String> headers;

    public Person getData() {
        return data;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}