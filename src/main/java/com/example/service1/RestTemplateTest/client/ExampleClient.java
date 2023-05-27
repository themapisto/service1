package com.example.service1.RestTemplateTest.client;

import com.example.service1.RestTemplateTest.models.Person;
import com.example.service1.RestTemplateTest.models.Response;
import com.example.service1.RestTemplateTest.models.Store;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Currency;

@FeignClient(name = "SERVICE1")
public interface ExampleClient {
    @GetMapping("/form/{id}")
    String getUserById(@PathVariable("id") Long id);
}