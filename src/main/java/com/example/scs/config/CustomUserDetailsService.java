package com.example.scs.config;

import com.example.scs.config.CustomUserDetails;
import com.example.scs.model.User;
import com.example.scs.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Lazy
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }

    public int updateResetPasswordToken(String token, String email){
        User user = userRepo.findByEmail(email);
        if (user != null) {
            user.setReset_password_token(token);
            userRepo.update_token(user);
            return 1;
        } else {
            System.out.println("Could not find any customer with the email " + email);
            return 0;
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepo.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setReset_password_token(null);
        userRepo.update_token(user);
    }
}