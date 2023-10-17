package com.toyproject.internbrew_backend.user.service;

import com.toyproject.internbrew_backend.exception.DataNullException;
import com.toyproject.internbrew_backend.user.dto.JwtToken;
import com.toyproject.internbrew_backend.user.dto.UserDto;
import com.toyproject.internbrew_backend.user.entity.User;
import com.toyproject.internbrew_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUserDetailsService jwtuserDetailService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    EntityManager entityManager;


    /*
    2023-08-18
    회원가입시 토큰발급
    */
    public JwtToken createUserAndGenerateToken(UserDto userDto) throws Exception {
        String userId = userDto.getUserId();
        String userPw = userDto.getUserPw();
        String userType = "N";
        String userName = userDto.getUserName();
        if (userDto.getUserId().contains("admin")) {
            userType = "Y";
        }

        if (userRepository.existsByUserId(userId)) {
            throw new Exception("This Email already exists.");
        }
        UserDto newUserDto = new UserDto();
        newUserDto.setUserId(userId);
        newUserDto.setUserPw(userPw);

        newUserDto.setUserType(userType);
        newUserDto.setUserName(userName);
        newUserDto.setUserCreateAt(LocalDateTime.now());

        User newUserEntity = User.on(newUserDto);

        userRepository.save(newUserEntity);
        final String token = jwtTokenUtil.generateToken(newUserDto.getUserId());
        return new JwtToken(token);
    }

    /*
    2023-08-16
    닉네임 또는 아이디 로그인시 아이디로 통일성있게 저장
    */
    public Map<String, String> getLoginId(String userId) {
        User user = userRepository.findByUserName(userId)
                .orElseGet(() -> userRepository.findByUserId(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found")));
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put("userNo", user.getUserNo().toString());
        loginInfo.put("loginId", user.getUserId().toString());
        loginInfo.put("userType", user.getUserType().toString());
        loginInfo.put("userName", user.getUserName().toString());

        return loginInfo;
    }

    /*
    2023-08-24
    로그인시 로그인날짜 기록용
    */
    public void LoginDate(String userId) throws Exception {
        try {
            LocalDateTime loginDate = LocalDateTime.now();
            Optional<User> userNameFind = userRepository.findByUserName(userId);
            String loginNameId = userNameFind.map(User::getUserId).orElse(null);

            Optional<User> userIdFind = userRepository.findByUserId(userId);
            String loginId = userIdFind.map(User::getUserId).orElse(null);

            if (loginNameId == null) {
                userRepository.updateLoginDate(loginId, loginDate);
            } else {
                userRepository.updateLoginDate(loginNameId, loginDate);
            }

        } catch (Exception e) {
            log.error("UserDate Can`t Update");
        }
    }


    /*
    2023-08-18
    삭제여부확인처리
    */
    public void deleteId(String userId) throws Exception {
        try {
            LocalDateTime currentDatetime = LocalDateTime.now();
            userRepository.deleteUserByUserId(userId, currentDatetime);
        } catch (Exception e) {
            log.error("User can't be deleted");
        }
    }

    public Boolean checkUserExistByIdPw(UserDto userDto) {

        Optional<User> optionalUser = userRepository.findByUserId(userDto.getUserId());
        User user = optionalUser.get();
        String userPw = user.getUserPw();
        if ( userPw.equals(userDto.getUserPw())) {
            return true;
        }
        return false;
    }

    public void changeUserPw(String userId, String newPw) {

        userRepository.changeUSerPw(userId, newPw);
    }
}
