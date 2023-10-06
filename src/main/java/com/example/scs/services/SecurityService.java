package com.example.scs.services;

import com.example.scs.config.CustomUserDetails;
import com.example.scs.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SecurityService {

    public String findLoggedInUsername() {
        Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (myUserDetails instanceof CustomUserDetails) {
            return ((CustomUserDetails) myUserDetails).getUser().getFullName();
        }

        return null;
    }

    public User findLoggedInUser() {
        Object myUserDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (myUserDetails instanceof CustomUserDetails) {
            return ((CustomUserDetails) myUserDetails).getUser();
        }
        return null;
    }

    public void autoLogout() {
        SecurityContextHolder.clearContext();
    }
}
