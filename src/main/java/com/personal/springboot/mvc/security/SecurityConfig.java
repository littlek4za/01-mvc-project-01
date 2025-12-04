package com.personal.springboot.mvc.security;

import com.personal.springboot.mvc.service.WebUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // this tell spring security how to verify users - using db and also password encoder which is from WebUserService
    // WebUserService which is an extends of UserDetailsService has method to load username and role and password


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
                .authorizeHttpRequests(configurer -> configurer //url authorized rules
                        //web authorize
                        .requestMatchers("/register/**").permitAll() // Permit register pages publicly
                        .requestMatchers("/").hasAnyRole("EMPLOYEE","MANAGER","ADMIN") // Web pages require EMPLOYEE role
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                        //api authorize
                        // API requires authentication
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasAnyRole("EMPLOYEE","MANAGER","ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasAnyRole("EMPLOYEE","MANAGER","ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/employees").hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/employees/**").hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated() // All else needs login
                )
                .formLogin(form -> form // Form login (for browser user)
                                .loginPage("/showLoginPage") // Custom Log in page
                                .loginProcessingUrl("/authenticateTheUser") // Form action URL
                                .successHandler(customAuthenticationSuccessHandler) // Redirect on login success
                                .permitAll() //everyone can access formLogin without role
                )
                .httpBasic(Customizer.withDefaults()) // Basic Auth (for client that use Postman/API)
                .csrf(csrf->csrf
                        .ignoringRequestMatchers("/api/**") // Disable CSRF only for APIs
                )
                .logout(logout -> logout // Logout support
                        .logoutUrl("/logout")
                        .permitAll() //everyone can access logout without role
                )
                .exceptionHandling(exception -> exception //if user is authenticated but no permission, redirect them to /access-denied
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendRedirect("/access-denied") // redirect to here if they are not authorized
                        )
                );


        return http.build();
    }
}


