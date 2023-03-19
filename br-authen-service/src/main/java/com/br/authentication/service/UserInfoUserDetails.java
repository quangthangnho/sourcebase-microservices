package com.br.authentication.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.br.authentication.model.entity.ProfileEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserInfoUserDetails implements UserDetails {


    private Long id;

    private String username;
    private String phone;

    private String fullName;

    private String email;

    private String idCard;

    private String dob;

    private Integer status;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;


    public UserInfoUserDetails(Long id, String username,String phone, String email, String fullName,
                           String idCard,String dob,Integer status, String password,Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.fullName = fullName;
        this.idCard = idCard;
        this.dob = dob;
        this.status = status;
        this.password = password;
        this.authorities = authorities;

    }

    public static UserInfoUserDetails build(ProfileEntity user) {
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        grantList.add(authority);
        UserInfoUserDetails userDetails = new UserInfoUserDetails(
                user.getId(),
                user.getUserId().getUserName(),
                user.getUserId().getPhone(),
                user.getUserId().getEmail(),
                user.getFullName(),
                user.getIdCard(),
                user.getDob(),
                user.getUserId().getStatus(),
                user.getUserId().getPassword(),
                grantList
        );
        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
