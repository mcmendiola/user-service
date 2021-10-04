package com.dailycodebuffer.user.repository;

import com.dailycodebuffer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(Long userId);
}
