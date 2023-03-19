package com.br.authentication.facade;

import com.br.authentication.dto.ProfileReturnDto;
import com.br.authentication.dto.RegisterDto;
import com.br.authentication.enums.UserStatusType;
import com.br.authentication.model.entity.ProfileEntity;
import com.br.authentication.model.entity.UserEntity;
import com.br.authentication.model.response.AuthResp;
import com.br.authentication.model.response.UserResp;
import com.br.authentication.repository.ProfileRepository;
import com.br.authentication.repository.UserRepository;
import com.br.authentication.service.AuthService;
import com.br.authentication.exception.AuthenticationErrors;
import com.br.common.converter.CustomDozerBeanMapper;
import com.br.common.converter.CustomDozerJdk8BeanMapper;
import com.br.common.exception.ErrorCodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFacade {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public CustomDozerBeanMapper customDozerBeanMapper;
    @Autowired
    public CustomDozerJdk8BeanMapper customDozerJdk8BeanMapper;

    private final AuthService authService;

    @Transactional(rollbackFor = Exception.class)
    public AuthResp register(RegisterDto object){
        ProfileEntity profileEntity = new ProfileEntity();
        UserEntity userEntity = new UserEntity();
        String regrexEmail = "^(.+)@(\\S+)$";
        Pattern patternTax = Pattern.compile(regrexEmail);
        Matcher matcher = patternTax.matcher(object.getEmail());
        if(!matcher.matches()) {
            throw new ErrorCodeException(AuthenticationErrors.EMAIL_NOT_CORRECT);
        }
        if(authService.findByEmail(object.getEmail()) != null){
            throw new ErrorCodeException(AuthenticationErrors.EMAIL_EXISTED);
        }
        if(authService.findByPhone(object.getPhone()) != null){
            throw new ErrorCodeException(AuthenticationErrors.PHONE_EXISTED);
        }
        userEntity.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        userEntity.setStatus(UserStatusType.ACTIVE.getValue());
        userEntity.setPhone(object.getPhone());
        userEntity.setEmail(object.getEmail());
        userRepository.save(userEntity);
        profileEntity.setFullName(object.getFullName());
        profileEntity.setUserId(userEntity);
        authService.save(profileEntity);
        return AuthResp.of(profileEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void changePassword(String password,Long id) {
        ProfileEntity profileEntity = authService.findUserById(id);
        if(profileEntity == null){
            throw new ErrorCodeException(AuthenticationErrors.USER_NOT_EXISTED);
        }

        profileEntity.getUserId().setPassword(bCryptPasswordEncoder.encode(password));
        authService.save(profileEntity);
    }

    @Transactional
    public UserResp getUserById(Long id) {
        ProfileEntity profileEntity = authService.findUserById(id);
        if(profileEntity == null){
            throw new ErrorCodeException(AuthenticationErrors.USER_NOT_EXISTED);
        }
        return UserResp.of(customDozerBeanMapper.map(profileEntity, ProfileReturnDto.class));
    }
}
