package com.lekhraj.java.spring.etl.springbatch.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="app_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
