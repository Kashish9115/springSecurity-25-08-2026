package com.example.demoOfSpringSecurity.service;

import com.example.demoOfSpringSecurity.dto.*;
import com.example.demoOfSpringSecurity.entity.NewUserrr;

import java.util.List;

public interface UserService {



    ResponseDto register(RequestDto requestDto);


    ResponseDto getUserByIdd(int id);

    List<ResponseDto> getAll();


    //ResponseDto login(String username, String password);

    LoginResponseDto login(LoginDto loginDto);


  ResponseDto  getCurrentUserProfile(String username);

  ResponseDto profileUpdate(String username ,ProfileUpdateDto profileUpdateDto);

}
