package com.toyproject.internbrew_backend.user.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserDto {

    private String userNo;
    private String userId;
    private String userPw;
    private String userType;
    private String userName;
    private LocalDateTime loginDate;
    private LocalDateTime userCreateAt;
    private LocalDateTime userUpdateAt;
    private String createUser;
    private String updateUser;
    private String userStatus;

    public UserDto() {

    }

    public UserDto(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }

    public UserDto(String userNo, String userId, String userPw, String userType, String userName,
                   LocalDateTime loginDate, LocalDateTime userCreateAt, LocalDateTime userUpdateAt,
                   String createUser, String updateUser, String userStatus) {
        this.userNo = userNo;
        this.userId = userId;
        this.userPw = userPw;
        this.userType = userType;
        this.userName = userName;
        this.loginDate = loginDate;
        this.userCreateAt = userCreateAt;
        this.userUpdateAt = userUpdateAt;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.userStatus = userStatus;
    }

}
