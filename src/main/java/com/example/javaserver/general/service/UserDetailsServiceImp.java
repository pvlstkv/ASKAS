package com.example.javaserver.general.service;

import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepo.findUserByLoginEquals(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));
        return UserDetailsImp.get(user);
    }

    public UserDetails loadUserById(Integer id) throws UsernameNotFoundException {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
        return UserDetailsImp.get(user);
    }

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
}
