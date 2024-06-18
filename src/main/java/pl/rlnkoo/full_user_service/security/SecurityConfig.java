package pl.rlnkoo.full_user_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/", "/registration/**").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(httpSecurityFormLoginConfigurer -> {
                        httpSecurityFormLoginConfigurer
                                .loginPage("/login")
                                .usernameParameter("email")
                                .defaultSuccessUrl("/")
                                .permitAll();
                })
                .logout(logoutConfigurer -> {
                    logoutConfigurer
                            .invalidateHttpSession(true)
                            .clearAuthentication(true)
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                            .logoutSuccessUrl("/")
                            .permitAll();
                })
                    .build();
    }
}
