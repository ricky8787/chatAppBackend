package com.example.chatapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	 	@Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.authorizeHttpRequests(authorize -> authorize
	                .requestMatchers("/api/**").permitAll()  // 允許 api 開頭的路徑免登入
	                .anyRequest().authenticated() // 其他的還是要登入
	            )
	        	.csrf(csrf -> csrf.disable()); // 關掉 CSRF (方便測試)

	        return http.build();
	    }
}
