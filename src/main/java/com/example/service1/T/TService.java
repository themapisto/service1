package com.example.service1.T;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class TService {

    @Autowired
    private MemberRepository memberRepository;


    public List<Member> MemberList(@RequestParam Long id){
        List<Member> allist =memberRepository.findAll();


        return allist;
    }


}
