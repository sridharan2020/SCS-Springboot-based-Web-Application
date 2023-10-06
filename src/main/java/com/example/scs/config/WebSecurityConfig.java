package com.example.scs.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    CustomUserDetailsService customUserDetailsService;



    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false);
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/", "/home", "/register", "/process_register", "/login", "/forgot_password", "/reset_password", "/static/js/", "/static/css/**","/script/**","/images/**").permitAll();
        http.authorizeRequests().antMatchers("/events/default/**", "/events", "/events/**", "/initiatives/default/**", "/initiatives", "/initiatives/**", "/counselors/**", "/verticals/**", "/css/**").permitAll();
        http.authorizeRequests().antMatchers("/counselors/default/**", "/counselors", "/counselors/", "/initiatives/default/**", "/initiatives", "/initiatives/").permitAll();
        http.authorizeRequests().antMatchers("/events/student/**", "/initiatives/student/**", "/counselors/student/**").access("hasAnyAuthority('STUDENT')");
        http.authorizeRequests().antMatchers("/events/member/**", "/initiatives/member/**", "/counselors/member/**").access("hasAnyAuthority('SCS_MEMBER')");
        http.authorizeRequests().antMatchers("/events/admin/**", "/initiatives/admin/**", "/counselors/admin/**").access("hasAnyAuthority('ADMIN')");
        http.authorizeRequests().antMatchers("/counsellingSessionss", "/counsellingSessionss/**").access("hasAnyAuthority('STUDENT', 'SCS_MEMBER', 'ADMIN')");

        http.authorizeRequests().antMatchers("/inductionPrograms", "/inductionPrograms/**", "/iMGroupss/**", "/inductionmentors/**", "/admin/students", "/member/students", "/student/students", "/default/students").permitAll();


        http.authorizeRequests().antMatchers("/users").access("hasAnyAuthority('ADMIN')");
        http.authorizeRequests().antMatchers("/dashboard").access("hasAnyAuthority('ADMIN', 'SCS_MEMBER', 'STUDENT', 'FACULTY')");
        http.authorizeRequests().antMatchers("/application/add").access("hasAnyAuthority('ADMIN','STUDENT')");
        http.authorizeRequests().antMatchers("/application","/application/**").access("hasAnyAuthority('ADMIN','STUDENT','SCS_MEMBER')");
        http.authorizeRequests().antMatchers("/bills","/bills/**","/monthlyReports","/monthlyReports/**").access("hasAnyAuthority('ADMIN','SCS_MEMBER')");
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }


}