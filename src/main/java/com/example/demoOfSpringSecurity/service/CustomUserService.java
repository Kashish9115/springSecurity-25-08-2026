package com.example.demoOfSpringSecurity.service;

import com.example.demoOfSpringSecurity.entity.NewUserrr;
import com.example.demoOfSpringSecurity.repo.UserRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class CustomUserService  implements UserDetailsService {

    private  final UserRepo repo;

    public CustomUserService(UserRepo repo) {
        this.repo = repo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NewUserrr appuser= repo.findByEmail(username).get();
     UserDetails userDetails =User.builder().username(appuser.getEmail())
             .password(appuser.getPassword()).roles(appuser.getRole()).build();

      return userDetails;
    }


}
