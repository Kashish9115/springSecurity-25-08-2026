package com.example.demoOfSpringSecurity.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewUserrr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private   int uId;

    @Column(nullable = true)
    private  String  username;

    @Column(nullable = false)
    private  String  password;

    @Column(unique = true ,nullable = false)
    private String email;

    @Column(nullable = false)
    private String role;

    @Column(nullable = true)
 private    String provider;
    @Column(nullable = true)
   private String providerId;

}
