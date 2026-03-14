package com.store.application.repository;

import com.store.application.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserLogin,Long> {
    Optional<UserLogin> findByUsername(String username);

    List<UserLogin> findByRole(String admin);

}
