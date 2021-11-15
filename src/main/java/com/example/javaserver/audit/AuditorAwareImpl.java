package com.example.javaserver.audit;

import com.example.javaserver.general.model.UserDetailsImp;
import com.example.javaserver.user.model.User;
import com.example.javaserver.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class AuditorAwareImpl implements AuditorAware<Integer> {
    private final UserService userService;

    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        UserDetailsImp userDetailsImp = ((UserDetailsImp) authentication.getPrincipal());
        return Optional.ofNullable(userDetailsImp.getId());
    }
}
