package com.example.demoOfSpringSecurity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class LoginResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private   int uId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  String  username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String role;

    private  String jwtToken;
}
