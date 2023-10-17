package com.toyproject.internbrew_backend.user.repository;

import com.toyproject.internbrew_backend.user.dto.UserDto;
import com.toyproject.internbrew_backend.user.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUserId(String userId);

    boolean existsByUserId(String userId);

    Optional<User> findByUserName(String userName);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.loginDate = :loginDate WHERE u.userId = :loginId")
    void updateLoginDate(@Param("loginId") String loginId, @Param("loginDate") LocalDateTime loginDate);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.userStatus = 'N', u.userUpdateAt = :currentDateTime WHERE u.userId = :userId")
    void deleteUserByUserId(@Param("userId") String userId, @Param("currentDateTime") LocalDateTime currentDateTime);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.userPw = :newPw WHERE u.userId = :userId")
    void changeUSerPw(@Param("userId") String userId, @Param("newPw") String newPw);
}
