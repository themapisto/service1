package com.example.service1.T;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/form")
public class TController {


    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private TService tService;



    @GetMapping
    public String hello(Model model){

        model.addAttribute("data", "koo Spring Boot!!!!");
        model.addAttribute("list",tService.MemberList(1L));
        System.out.println(tService.MemberList((1L)).toString());

        return "form/index";
    }


    @Data
    static class User {

        private String username;
        private int age;

        public User(String username, int age){
            this.username = username;
            this.age = age;

        }

    }

}
