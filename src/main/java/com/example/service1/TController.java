package com.example.service1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/form")
public class TController {


    @Autowired
    MemberRepository memberRepository;

    @PostConstruct
    public void init(){

    }


    @GetMapping
    public String hello(Model model){


        List<Member> allist = memberRepository.findAll();



        model.addAttribute("data","Hello this is koo blog");
        model.addAttribute("list",allist);
        return "form/gym";
    }


}
