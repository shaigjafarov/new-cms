package com.guavapay.cms.gatewayservice.service.impl;

import com.guavapay.cms.gatewayservice.dao.entity.UserEntity;
import com.guavapay.cms.gatewayservice.dao.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> user = userRepository.findUserEntityByUsername(username);

        if (user.isEmpty()) throw new UsernameNotFoundException("Username :" + username + " not found");

        return user.get();
    }
}
