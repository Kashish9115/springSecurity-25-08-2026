package com.example.demoOfSpringSecurity.repo;

import com.example.demoOfSpringSecurity.entity.NewUserrr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo  extends JpaRepository<NewUserrr, Integer> {


   Optional<NewUserrr> findByEmail(String email);


  List<NewUserrr> findByRoleIgnoreCase(String role);

  boolean   existsByEmail(String email);

 Optional<NewUserrr>  findByProviderAndProviderId(String provider, String providerId);
}
