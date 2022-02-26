package org.setana.treenity.config;

import com.google.firebase.auth.FirebaseAuth;
import lombok.RequiredArgsConstructor;
import org.setana.treenity.security.filter.JwtFilter;
import org.setana.treenity.security.service.CustomUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserService userDetailsService;
    private final FirebaseAuth firebaseAuth;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .csrf()
            .disable()
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(new JwtFilter(userDetailsService, firebaseAuth),
                UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            // temporary
            .antMatchers("/items/**", "/trees/**", "/users/**")
            // default
            .antMatchers("/")
            // health check
            .antMatchers("/health-check")
            // swagger
            .antMatchers("/v3/api-docs",
                "/swagger-resources/**",
                "/swagger-ui/**")
            .antMatchers(HttpMethod.POST, "/auth")
            .antMatchers("/resources/**");
    }
}
