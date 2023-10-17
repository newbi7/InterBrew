package com.toyproject.internbrew_backend.user.service;

import com.toyproject.internbrew_backend.user.entity.Role;
import com.toyproject.internbrew_backend.user.entity.User;
import com.toyproject.internbrew_backend.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    /*
    2023-08-08
    사용자 이메일을 기반으로 사용자 정보를 데이터베이스에서 조회
    */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId).get();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        if (userId.equals("admin@admin.com")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getUserPw(), grantedAuthorities);
    }

    /*
    2023-08-08
    사용자 아이디와 비밀번호를 기반으로 사용자 인증을 처리
    */
    public User authenticateByEmailAndPassword(String userId, String userPw) throws Exception {
        User user = userRepository.findByUserName(userId)
                .orElseGet(() -> userRepository.findByUserId(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found")));

        if (!userPw.equals(user.getUserPw())) {
            throw new Exception("Password not matched");
        }

        return user;
    }

}
