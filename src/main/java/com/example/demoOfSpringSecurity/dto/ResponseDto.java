package com.example.demoOfSpringSecurity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ResponseDto {



    @JsonInclude(JsonInclude.Include.NON_NULL)
    private   int uId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  String  username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String role;
}
