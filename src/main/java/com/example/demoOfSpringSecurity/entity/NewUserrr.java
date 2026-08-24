package com.example.demoOfSpringSecurity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

}
