package com.toyproject.internbrew_backend.user.entity;

import com.toyproject.internbrew_backend.payment.entity.Payment;
import com.toyproject.internbrew_backend.user.dto.UserDto;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER_TB")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "pk_user_no", columnDefinition = "VARCHAR(50) NOT NULL")
    private String userNo;

    @Column(name = "user_id", length = 50, unique = true)
    private String userId;

    @Column(name = "user_type", length = 1, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
    private String userType = "N";

    @Column(name = "user_name", length = 50, columnDefinition = "VARCHAR(50)", unique = true)
    private String userName;

    @Column(name = "user_pw", length = 50, columnDefinition = "VARCHAR(50)")
    private String userPw;

    @Column(name = "user_login_date", columnDefinition = "DATETIME DEFAULT NULL")
    private LocalDateTime loginDate;

    @Column(name = "user_create_at", columnDefinition = "DATETIME DEFAULT NULL")
    private LocalDateTime userCreateAt;

    @Column(name = "user_updated_at", columnDefinition = "DATETIME DEFAULT NULL")
    private LocalDateTime userUpdateAt;

    @Column(name = "user_create", length = 50, columnDefinition = "VARCHAR(50) DEFAULT NULL")
    private String createUser;

    @Column(name = "user_update", length = 50, columnDefinition = "VARCHAR(50) DEFAULT NULL")
    private String updateUser;

    @Column(name = "user_status", length = 1, columnDefinition = "VARCHAR(1) DEFAULT 'Y'")
    private String userStatus = "Y";

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    private List<Payment> payments = new ArrayList<>();

    private User(UserDto dto) {
        this.userNo = dto.getUserNo();
        this.userId = dto.getUserId();
        this.userType = dto.getUserType();
        this.userName = dto.getUserName();
        this.userPw = dto.getUserPw();
        this.loginDate = dto.getLoginDate();
        this.userCreateAt = dto.getUserCreateAt();
        this.userUpdateAt = dto.getUserUpdateAt();
        this.createUser = dto.getCreateUser();
        this.updateUser = dto.getUpdateUser();
        this.userStatus = dto.getUserStatus();
    }

    public static User on(UserDto dto) {
        return new User(dto);
    }
}
