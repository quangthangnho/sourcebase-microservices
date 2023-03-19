package com.br.authentication.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity(name = "Profile")
@Table(name = "tbl_profile")
@Getter
@Setter
public class ProfileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity userId;

    @Column(name = "col_fullName")
    private String fullName;

    @Column(name = "col_dob")
    private String dob;

    @Column(name = "col_idCard")
    private String idCard;

}