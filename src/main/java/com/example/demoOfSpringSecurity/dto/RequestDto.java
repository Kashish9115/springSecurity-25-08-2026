package com.example.demoOfSpringSecurity.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NonNull;

@Data
public class RequestDto {





    private  String  username;


    private  String  password;


    private String email;


    private String role;
}
