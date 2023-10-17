package com.toyproject.internbrew_backend.user.controller;

import com.toyproject.internbrew_backend.exception.DataNullException;
import com.toyproject.internbrew_backend.exception.DataRuntimeException;
import com.toyproject.internbrew_backend.user.dto.JwtToken;
import com.toyproject.internbrew_backend.user.dto.UserDto;
import com.toyproject.internbrew_backend.user.entity.User;
import com.toyproject.internbrew_backend.user.repository.UserRepository;
import com.toyproject.internbrew_backend.user.service.JwtTokenUtil;
import com.toyproject.internbrew_backend.user.service.JwtUserDetailsService;
import com.toyproject.internbrew_backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping(value="api/v1")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtuserDetailService;

    @Autowired
    private UserService userService;

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    /*
    2023-08-08
    회원가입시 유저생성 및 Auth토큰발급
    */
    @PutMapping("/user/auth")
    public ResponseEntity<?> createUserAuth(@RequestBody UserDto userDto) throws Exception {
        try {
            JwtToken token = userService.createUserAndGenerateToken(userDto);
            return ResponseEntity.ok(token);
        } catch (DataNullException e) {
            log.error("User Create Null Error");
            DataNullException response = new DataNullException(4101, "EF4101", "User Data Null Error");
            return ResponseEntity.ok().body(response);
        } catch (DataRuntimeException e) {
            log.error("User Create Runtime Error");
            DataRuntimeException response = new DataRuntimeException(4101, "EF4101", "User Runtime Error");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("User Create Error");
            Exception response = new Exception("Email not create", e);
            return ResponseEntity.ok().body(response);
        }
    }

    /*
    2023-08-09
    로그인 
    jwt 토큰 생성, 로그인 아이디, 어드민여부, 로그인날짜업데이트
    */
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) throws Exception {
        try {
            String userId = userDto.getUserId();
            String userPw = userDto.getUserPw();

            final User authenticatedUser = jwtuserDetailService.authenticateByEmailAndPassword(userId, userPw);
            final String token = jwtTokenUtil.generateToken(authenticatedUser.getUserId());
            final Map loginInfo = userService.getLoginId(userId);
            try {
                userService.LoginDate(userId);
            } catch (Exception e) {
                log.error("LoginDate Update Fail.", e);
                Exception response = new Exception("LoginDate Update Fail.", e);
                return ResponseEntity.ok().body(response);
            }
            final Object userNo = loginInfo.get("userNo");
            final Object loginId = loginInfo.get("loginId");
            final Object userName = loginInfo.get("userName");
            final Object userType = loginInfo.get("userType");
            Map<String, Object> tokenId = new HashMap<>();
            tokenId.put("token", new JwtToken(token));
            tokenId.put("userNo", userNo);
            tokenId.put("loginId", loginId);
            tokenId.put("userName", userName);
            tokenId.put("userType", userType);

            return ResponseEntity.ok(tokenId);
        } catch (DataNullException e) {
            log.error("Login Null Error");
            DataNullException response = new DataNullException(4101, "EF4101", "Invalid email or password Data Null Error");
            return ResponseEntity.ok().body(response);
        } catch (DataRuntimeException e) {
            log.error("Login Runtime Error");
            DataRuntimeException response = new DataRuntimeException(4101, "EF4101", "Invalid email or password Runtime Error");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Login Error");
            Exception response = new Exception("Invalid email or password", e);
            return ResponseEntity.ok().body(response);
        }
    }

    /*
    2023-08-18
    유저삭제메소드
    삭제하는게 아닌 userType을 N으로 , 유저 수정날짜 업데이트
    */
    @PostMapping("/user/type")
    public ResponseEntity<?> deleteUser(@RequestBody UserDto userDto) throws Exception {
        try {
            String userId = userDto.getUserId();
            Optional<User> existingUser = userRepository.findByUserId(userId);

            if (!existingUser.isPresent()) {
                log.error("User not Exist");
                throw new Exception("User not Exist");
            } else if (existingUser.get().getUserStatus().equals("N")) {
                log.error("User Already Deleted");
                throw new Exception("User Already Deleted");
            }
            userService.deleteId(userId);
            String response = new String ("User deleted successfully.");
            return ResponseEntity.ok().body(response);
        } catch (DataNullException e) {
            log.error("User Delete Null Error");
            DataNullException response = new DataNullException(4101, "EF4101", "User Delete Data Null Error");
            return ResponseEntity.ok().body(response);
        } catch (DataRuntimeException e) {
            log.error("User Delete Runtime Error");
            DataRuntimeException response = new DataRuntimeException(4101, "EF4101", "User Delete Runtime Error");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("User Delete Error");
            Exception response = new Exception("User Delete Error", e);
            return ResponseEntity.ok().body(response);
        }
    }

    /*
    비밀번호 수정 기능
    기본적인 예외처리는 Front단에서 처리후 데이터를 넘겨받음
    유저 정보 유효여부 조회후 변경
    */
    @PostMapping(value = "mypage/pw")
    public ResponseEntity<?> chagePw(@RequestBody Map<String, String> request) {

        String userId = request.get("userId");
        String userPw = request.get("userPw");
        String newPw = request.get("newPw");

        UserDto userDto = new UserDto(userId, userPw);

        try {
            Boolean result = userService.checkUserExistByIdPw(userDto);
            if (result == false) {
                Exception response = new Exception("Password is wrong");
                return ResponseEntity.ok().body(response);
            }
            userService.changeUserPw(userDto.getUserId(), newPw);
            String response = "Success change Password";
            return ResponseEntity.ok().body(response);
        } catch (DataNullException e) {
            log.error("Password Change Data Null Error");
            DataNullException response = new DataNullException(4101, "EF4101", "Password Change Data Null Error");
            return ResponseEntity.ok().body(response);
        } catch (DataRuntimeException e) {
            log.error("Password Change Error");
            DataRuntimeException response = new DataRuntimeException(4101, "EF4101", "Password Change Runtime Error");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            log.error("Password Change Error");
            Exception response = new Exception("Password Change Error", e);
            return ResponseEntity.ok().body(response);
        }
    }

}
