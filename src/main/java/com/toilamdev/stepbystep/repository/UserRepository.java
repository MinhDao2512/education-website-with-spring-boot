package com.toilamdev.stepbystep.repository;

import com.toilamdev.stepbystep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUserByEmail(String email);

    boolean existsUserByPhoneNumber(String phoneNumber);

    Optional<User> findUserByEmail(String email);
}
