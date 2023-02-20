package com.example.service1.T;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name="MEMBER")
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExercise1() {
        return exercise1;
    }

    public void setExercise1(String exercise1) {
        this.exercise1 = exercise1;
    }

    public String getExercise2() {
        return exercise2;
    }

    public void setExercise2(String exercise2) {
        this.exercise2 = exercise2;
    }

    public String getExercise3() {
        return exercise3;
    }

    public void setExercise3(String exercise3) {
        this.exercise3 = exercise3;
    }

    public String getExercise4() {
        return exercise4;
    }

    public void setExercise4(String exercise4) {
        this.exercise4 = exercise4;
    }

    public String getExercise5() {
        return exercise5;
    }

    public void setExercise5(String exercise5) {
        this.exercise5 = exercise5;
    }

    public String getExercise6() {
        return exercise6;
    }

    public void setExercise6(String exercise6) {
        this.exercise6 = exercise6;
    }

    public Long getMeberDesc() {
        return meberDesc;
    }

    public void setMeberDesc(Long meberDesc) {
        this.meberDesc = meberDesc;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}