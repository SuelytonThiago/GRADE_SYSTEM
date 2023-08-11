package com.example.domain.enums;

import com.example.rest.service.exceptions.CustomException;

public enum Roles {

    ROLE_STUDENT("STUDENT"),
    ROLE_TEACHER("TEACHER"),
    ROLE_ADMIN("ADMIN");

    private String name;

    private Roles(String name){
        this.name = name;
    }

    //pega o nome de um código
    public String getName(){
        return name;
    }

    //procurar as Roles pelo respectivo nome
    public static Roles nameOf(String name){
        for(Roles x  : Roles.values()){
            if(x.getName().equals(name)){
                return x;
            }
        }
        throw new CustomException("Invalid Roles name");
    }

}
