package com.br.authentication.service;

import com.br.authentication.repository.ProfileRepository;
import com.br.authentication.model.entity.ProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        ProfileEntity customer = profileRepository.findByPhone(phone);
        if (customer == null)
            throw new UsernameNotFoundException("User Not Found with phone: " + phone);
        return UserInfoUserDetails.build(customer);
    }
}
