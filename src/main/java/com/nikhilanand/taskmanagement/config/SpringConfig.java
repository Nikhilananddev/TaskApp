package com.nikhilanand.taskmanagement.config;

import com.nikhilanand.taskmanagement.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.nikhilanand.taskmanagement.global.GlobalVariables.APP_API_ENDPOINT;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SpringConfig {


    @Autowired
    UserDetailService userService;
    @Autowired
    JWTAuthenticationFilter jwtAuthenticationFilter;

    // 1.
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//                ("/login", "/register","/home","/hotels/available")

        httpSecurity.csrf(csrf -> csrf.disable());

        httpSecurity.authenticationProvider(authenticationProvider());

        httpSecurity.authorizeHttpRequests(configurer -> configurer
                .requestMatchers(
                        APP_API_ENDPOINT + "/login",
                        APP_API_ENDPOINT + "/register",
                        APP_API_ENDPOINT + "/home",
                        "/home"
                )

                .permitAll()
                .anyRequest()
                .authenticated());


        // Do not save the state, instead authenticate with token
        httpSecurity.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //Invoke JWT before the traditionational authentication
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();


    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }


    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
