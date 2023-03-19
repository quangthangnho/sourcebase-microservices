package com.br.authentication.model.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

@Entity(name = "User")
@Table(name = "tbl_user")
@Getter
@Setter
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(name = "col_phone", unique = true)
    private String phone;

    @Column(name = "col_email")
    private String email;

    @Column(name = "col_userName")
    private String userName;

    @Column(name = "col_status")
    private Integer status;

    @Column(name = "col_lastActive", columnDefinition = "timestamp")
    private ZonedDateTime lastActive;

    @Column(name = "col_password")
    private String password;
}