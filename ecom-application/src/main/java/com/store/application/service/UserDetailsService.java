package com.store.application.service;

import com.store.application.model.UserLogin;
import com.store.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    public void createUser(UserLogin user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserLogin userLogin = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not found"));

        UserDetails build = User
                .builder()
                .username(userLogin.getUsername())
                .password(userLogin.getPassword())
                .roles(userLogin.getRole())
                .build();
        return build;
    }

    public boolean existsByUsername(String username) {

        if(repo.findByUsername(username).isPresent()){
            return true;
        }else{
            return false;
        }
    }
}
