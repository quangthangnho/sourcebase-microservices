package com.br.authentication.service;

import com.br.authentication.dto.JwtResponse;
import com.br.authentication.model.entity.ProfileEntity;
import com.br.authentication.repository.ProfileRepository;
import com.br.authentication.dto.LogingRequestDto;
import com.br.authentication.exception.AuthenticationErrors;
import com.br.common.exception.ErrorCode;
import com.br.common.exception.ErrorCodeException;
import com.br.common.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class AuthService extends BaseService<ProfileEntity,Long, ProfileRepository> {
    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    JwtService jwtService;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    private RedisTemplate<String, String> template;

    protected AuthService(ProfileRepository repo) {
        super(repo);
    }

    public JwtResponse authenticateUser(LogingRequestDto loginRequestDTO)  {
        Authentication authentication;
        try {
            authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getPhone(), loginRequestDTO.getPassword()));
        }
        catch (Exception notFound) {
            notFound.printStackTrace();
            throw  new ErrorCodeException(AuthenticationErrors.INVALID_LOGIN);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generate(authentication);
        UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
        template.opsForValue().set(userDetails.getUsername(),jwt);
        return new JwtResponse(jwt,"",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getIdCard());
    }


    public void logoutUser(Authentication authentication) {
        if (authentication != null) {
            UserInfoUserDetails userDetails = (UserInfoUserDetails) authentication.getPrincipal();
//            template.delete(userDetails.getUsername());
        }
    }

    public ProfileEntity findByEmail(String email){
        return profileRepository.findByEmail(email);
    }

    public ProfileEntity findByPhone(String phone){
        return profileRepository.findByPhone(phone);
    }

    public ProfileEntity findUserById(Long id){
        return profileRepository.findUserById(id);
    }

    @Override
    protected ErrorCode errorCodeNotFoundEntity() {
        return null;
    }
}
