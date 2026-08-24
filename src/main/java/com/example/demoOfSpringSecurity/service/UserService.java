package com.example.demoOfSpringSecurity.service;

import com.example.demoOfSpringSecurity.dto.RequestDto;
import com.example.demoOfSpringSecurity.dto.ResponseDto;
import com.example.demoOfSpringSecurity.entity.NewUserrr;

import java.util.List;

public interface UserService {



    ResponseDto register(RequestDto requestDto);


    ResponseDto getUserByIdd(int id);

    List<ResponseDto> getAll();

}
