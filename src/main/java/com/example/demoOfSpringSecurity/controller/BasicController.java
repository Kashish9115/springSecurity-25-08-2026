package com.example.demoOfSpringSecurity.controller;

import com.example.demoOfSpringSecurity.dto.*;
import com.example.demoOfSpringSecurity.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/k")
@RequiredArgsConstructor
public class BasicController {

    private  final UserServiceImpl service;

    @GetMapping("/name")
    public  String getMyName(){
        return "Kashish";
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createUser(@RequestBody RequestDto requestDto){
        ResponseDto register = service.register(requestDto);

        return  ResponseEntity.status(HttpStatus.CREATED).body(register);
    }


    @GetMapping("/get/{id}")
    public  ResponseEntity<ResponseDto> getUser(@PathVariable int id){
        ResponseDto userByIdd = service.getUserByIdd(id);

        return  ResponseEntity.ok(userByIdd);
    }


    @GetMapping("/getAlluser")
    public  ResponseEntity<List<ResponseDto>> getAllUser(){
        List<ResponseDto> all = service.getAll();
       return  ResponseEntity.ok(all);
    }

    @PostMapping("/login")
    public  ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto  loginDto){
       LoginResponseDto login = service.login(loginDto);
        return  ResponseEntity.ok(login);

    }



    @GetMapping("/profile")
    public  ResponseEntity<ResponseDto> getProfile(@AuthenticationPrincipal UserDetails userDetails){
        return  ResponseEntity.ok(service.getCurrentUserProfile(userDetails.getUsername()));
    }



    @PatchMapping("/profile/update")
    public  ResponseEntity<ResponseDto> updateProfile(@AuthenticationPrincipal UserDetails userDetails,@RequestBody ProfileUpdateDto profileUpdateDto ){
       ResponseDto responseDto= service.profileUpdate(userDetails.getUsername(), profileUpdateDto);


        return  ResponseEntity.ok(responseDto);


    }

}
