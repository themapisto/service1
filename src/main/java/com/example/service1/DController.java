package com.example.service1;
import com.example.service1.RestTemplateTest.controller.RestTemplateTestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;





@RestController
public class DController {
    @Autowired
    DService discoveryService;

    @Autowired
    RestTemplateTestController testController;

    @GetMapping(value = "/services")
    public List<String> services() throws JsonProcessingException {

        System.out.println("gateway가 나를 불렀어!!!");
        testController.restTemplateTest2();

        return discoveryService.getServices();
    }

}