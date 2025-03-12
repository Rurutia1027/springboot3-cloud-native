package com.cloud.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationSuccessHandler bookShopAuthenticaitonSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler bookShopAuthenticationFailureHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BookSecurityAuthorizationManager bookSecurityAuthorizationManager;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setCreateTableOnStartup(false);
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                // .csrf(csrf -> csrf.disable())
                // Ensures XSRF-TOKEN cookie is sent
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/auth"))
                .authorizeHttpRequests(auth -> auth
                        // all request query /book endpoint are allowed
                        .requestMatchers("/book", "/login.html", "/auth", "/session.html").permitAll()
                        .requestMatchers("/book/**").authenticated()
                        // other url address should be authenticated
                        // declare customized authorized handler here
                        //.anyRequest().access(bookSecurityAuthorizationManager))
                        .anyRequest().access(AuthorityAuthorizationManager.hasAnyAuthority("admin")))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionFixation()
                        .changeSessionId())

                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/auth")
                        .usernameParameter("user")
                        .passwordParameter("pass")
                        .successHandler(bookShopAuthenticaitonSuccessHandler)
                        .failureHandler(bookShopAuthenticationFailureHandler)
                        .permitAll())
//                        // session manager
//                        .sessionManagement()
//                        // when session got expired which url to redirect -> #invalidSessionUrl()
//                        .invalidSessionUrl("/session.html")
//                        .maximumSessions(1)
//                        // When a user reaches the maximum allowed sessions,
//                        // any attempt to log in from another device or browser will be denied.
//                        .maxSessionsPreventsLogin(true)
//                        .and()
//                        .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                // how long will Remember Me store the token to persistent_logins db table
                .tokenValiditySeconds(60);
        return http.build();
    }

    // different from Spring 2.x we need to implement AuthenticationManager by ourselves
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(new MyUserDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(List.of(authProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

