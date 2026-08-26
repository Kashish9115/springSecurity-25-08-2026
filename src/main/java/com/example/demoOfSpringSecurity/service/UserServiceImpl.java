package com.example.demoOfSpringSecurity.service;


import com.example.demoOfSpringSecurity.customJwt.JwtService;
import com.example.demoOfSpringSecurity.dto.*;
import com.example.demoOfSpringSecurity.entity.NewUserrr;
import com.example.demoOfSpringSecurity.exception.UserNotFoundException;
import com.example.demoOfSpringSecurity.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;



@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl  implements  UserService {

    private final UserRepo repo;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;


    private  final JwtService jwtService;

    private  final AuthenticationManager authenticationManager;

    @Override
    public ResponseDto register(RequestDto requestDto) {

        NewUserrr n = mapper.map(requestDto, NewUserrr.class);

        n.setPassword(passwordEncoder.encode(n.getPassword()));

        return mapper.map(repo.save(n), ResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto getUserByIdd(int id) {
        NewUserrr user = repo.findById(id).get();
        String userrole = "ADMIN";
        if (!user.getRole().equalsIgnoreCase(userrole)) {
            return mapper.map(user, ResponseDto.class);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseDto> getAll() {
        String role = "User";
        List<NewUserrr> userrrList = repo.findByRoleIgnoreCase(role);
        List<ResponseDto> responseDtoList = new ArrayList<>();
        for (NewUserrr u : userrrList) {
            responseDtoList
                    .add(mapper.map(u, ResponseDto.class));
        }

        return responseDtoList;

    }

//    @Override
//    public ResponseDto login(String username, String password) {
//      NewUserrr user= repo.findByEmail(username).get();
//
//     if(user!=null && passwordEncoder.matches(password, user.getPassword())){
//         return mapper.map(user, ResponseDto.class);
//     }
//     return  null;
//    }

// optimized login method
//    @Override
//    public ResponseDto login(String username, String password) {
//        NewUserrr user = repo.findByEmail(username).orElseThrow(() -> new UserNotFoundException("User not found with username : " + username));
//        passwordEncoder.matches(password, user.getPassword());
//            return mapper.map(user, ResponseDto.class);
//        }




// login with jwt token
    @Override
    public LoginResponseDto login(LoginDto loginDto ) {
        try{
            authenticationManager.authenticate
                    (UsernamePasswordAuthenticationToken
                            .unauthenticated(loginDto.getUsername(), loginDto.getPassword()));
        }catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password");

        }


        NewUserrr user=repo.findByEmail(loginDto.getUsername()).orElseThrow(()-> new  UserNotFoundException("user not found "));
        LoginResponseDto loginResponse = mapper.map(user, LoginResponseDto.class);
        loginResponse.setJwtToken(jwtService.generateToken(user.getEmail(), user.getRole()));

      return  loginResponse;
    }





    @Override
    public ResponseDto getCurrentUserProfile(String username) {
        NewUserrr byEmail = repo.findByEmail(username).get();
        if (byEmail != null) {
            System.out.println("user found ");
            return mapper.map(byEmail, ResponseDto.class);
        }
        return null;
    }




      @Override
    public ResponseDto profileUpdate(String username, ProfileUpdateDto profileUpdateDto) {

        NewUserrr user = repo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("user not found "));

            if (profileUpdateDto.getEmail() != null & !repo.existsByEmail(profileUpdateDto.getEmail())) {
                user.setEmail(profileUpdateDto.getEmail());
            }
                if(profileUpdateDto.getUsername() != null ) {
                    user.setUsername(profileUpdateDto.getUsername());
                }
                if(profileUpdateDto.getPassword() != null){
                    user.setPassword(passwordEncoder.encode(profileUpdateDto.getPassword()));
                }
                return mapper.map(repo.save(user), ResponseDto.class);
            }









}
