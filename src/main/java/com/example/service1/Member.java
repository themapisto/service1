package com.example.service1;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name="MEMBER")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public
class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name= "ID" )
    private Long id;

    @Column(name = "NAME")
    private String username;

    @Column(name = "EX1")
    private String exercise1;

    @Column(name = "EX2")
    private String exercise2;

    @Column(name = "EX3")
    private String exercise3;

    @Column(name = "EX4")
    private String exercise4;

    @Column(name = "EX5")
    private String exercise5;

    @Column(name = "EX6")
    private String exercise6;

    @Column(length = 200,nullable = false)
    private Long meberDesc;

    private Integer age;

}