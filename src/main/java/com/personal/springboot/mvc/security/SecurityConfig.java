package com.personal.springboot.mvc.security;

import com.personal.springboot.mvc.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }


//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource){
//        JdbcUserDetailsManager theUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        theUserDetailsManager.setUsersByUsernameQuery("SELECT username, password, enable FROM User WHERE username=?");
//        theUserDetailsManager.setAuthoritiesByUsernameQuery(
//                "SELECT u.username, r.name AS authority " +
//                        "FROM User u " +
//                        "JOIN users_roles ur ON u.id = ur.user_id " +
//                        "JOIN role r ON ur.role_id = r.id " +
//                        "WHERE u.username = ?"
//        );
//
//        return theUserDetailsManager;
//    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            UserService userService,
            AuthenticationSuccessHandler customAuthenticationSuccessHandler
    ) throws Exception {
        http.authenticationProvider(authenticationProvider(userService)) //need to plug in the authenticationProvider to spring security manually if not spring might not know to use it
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/").hasRole("EMPLOYEE")
                                .requestMatchers("/register/**").permitAll()
                                .anyRequest().authenticated()
                ).formLogin(form ->
                        form
                                .loginPage("/showLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .successHandler(customAuthenticationSuccessHandler)
                                .permitAll()
                ).logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                ).exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendRedirect("/access-denied")
                        )
                );


        return http.build();
    }
}


