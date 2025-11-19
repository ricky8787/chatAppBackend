package com.example.chatapp.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.chatapp.service.CustomUserDetailsService;
import com.example.chatapp.service.JwtService;
import com.example.chatapp.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
		private final CustomUserDetailsService customUserDetailsService;
		
		@Bean
	    public AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(customUserDetailsService);
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }
		
		@Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
	    
	 	@Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.authorizeHttpRequests(authorize -> authorize
	        		 .requestMatchers("/api/auth/login", "/api/auth/register").permitAll() // 登入 & 註冊不需要登入
	                 .requestMatchers("/api/**").authenticated()  // 這些 API 需要登入
	            )
	        	.cors(cors -> cors.configurationSource(corsConfigurationSource()))
	        	.csrf(csrf -> csrf.disable()); // 關掉 CSRF (方便測試)
	        

	        return http.build();
	    }
	 	
	 	 // ✅ **這是 CORS 設定**
	    @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true); // 允許 Cookie、授權標頭
	        config.setAllowedOrigins(List.of("http://localhost:5173", "http://127.0.0.1:5173")); // 允許前端的 URL
	        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", config);
	        return source;
	    }
	 	
	 	@Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 	
	 	

}
