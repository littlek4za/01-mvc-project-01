package com.personal.springboot.mvc.security;

import com.personal.springboot.mvc.service.WebUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(WebUserService webUserService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(webUserService);
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
            WebUserService webUserService,
            AuthenticationSuccessHandler customAuthenticationSuccessHandler
    ) throws Exception {
        http
                .authenticationProvider(authenticationProvider(webUserService)) //need to plug in the authenticationProvider to spring security manually if not spring might not know to use it
                .authorizeHttpRequests(configurer -> configurer //url authorized rules
                        .requestMatchers("/register/**").permitAll() // Permit register pages publicly
                        .requestMatchers("/api/**").authenticated() // API requires authentication
                        .requestMatchers("/").hasRole("EMPLOYEE") // Web pages require EMPLOYEE role
                        .anyRequest().authenticated() // All else needs login
                )
                .formLogin(form -> form // Form login (for browser user)
                                .loginPage("/showLoginPage") // Custom Log in page
                                .loginProcessingUrl("/authenticateTheUser") // Form action URL
                                .successHandler(customAuthenticationSuccessHandler) // Redirect on login success
                                .permitAll()
                )
                .httpBasic(httpBasic->{}) // Basic Auth (for client that use Postman/API)
                .csrf(csrf->csrf
                        .ignoringRequestMatchers("/api/**") // Disable CSRF only for APIs
                )
                .logout(logout -> logout // Logout support
                        .logoutUrl("/logout")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendRedirect("/access-denied") // redirect to here if they are not authorized
                        )
                );


        return http.build();
    }
}


