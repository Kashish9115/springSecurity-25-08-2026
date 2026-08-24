package com.example.demoOfSpringSecurity.service;

import com.example.demoOfSpringSecurity.config.SecurityFilterConfig;
import com.example.demoOfSpringSecurity.dto.RequestDto;
import com.example.demoOfSpringSecurity.dto.ResponseDto;
import com.example.demoOfSpringSecurity.entity.NewUserrr;
import com.example.demoOfSpringSecurity.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;



@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl  implements  UserService{

    private  final UserRepo repo;
    private  final ModelMapper mapper;
 private  final PasswordEncoder passwordEncoder;

    @Override
    public ResponseDto register(RequestDto requestDto) {

       NewUserrr n= mapper.map(requestDto, NewUserrr.class);

   n.setPassword(passwordEncoder.encode(n.getPassword()));

   return     mapper.map(repo.save(n), ResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto getUserByIdd(int id) {
        NewUserrr user = repo.findById(id).get();
    String userrole="ADMIN";
        if(!user.getRole().equalsIgnoreCase(userrole)) {
            return mapper.map(user, ResponseDto.class);
        }
        return  null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseDto> getAll() {
        String role="User";
        List<NewUserrr> userrrList =  repo.findByRoleIgnoreCase(role);
        List<ResponseDto> responseDtoList = new ArrayList<>();
        for(NewUserrr u : userrrList){
           responseDtoList
                   .add(mapper.map(u, ResponseDto.class));
        }

     return  responseDtoList;

    }
}
