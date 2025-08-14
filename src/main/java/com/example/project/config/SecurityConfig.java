package com.example.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.project.entity.Admin;
import com.example.project.repository.AdminRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(AdminRepository adminRepository){
        return username -> {
            Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: "+username));
            return User.withUsername(admin.getUsername())
                .password(admin.getPassword())
                .roles(admin.getRole().replace("ROLE_", ""))
                .disabled(!admin.isEnabled())
                .build();
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(reg -> reg.requestMatchers("/login","/css/**","/js/**","/img/**","/webjars/**").permitAll()
            .requestMatchers("/","/universities/**","/campuses/**","/faculties/**","/departments/**").permitAll()
            .requestMatchers("/ui/**","/api/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()) .formLogin(f -> f
            .loginPage("/login").permitAll()
            .defaultSuccessUrl("/ui",true))
            .logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll());
        return http.build();
    }
}
