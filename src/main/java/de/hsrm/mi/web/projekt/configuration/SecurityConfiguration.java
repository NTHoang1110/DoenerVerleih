package de.hsrm.mi.web.projekt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Value("${SECRETKEY}")
    private String schluessel;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChainApp(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(toH2Console()).permitAll()

                .requestMatchers("/benutzer").hasRole("ADMIN")
                .requestMatchers("/benutzer/**").hasRole("ADMIN")
                .requestMatchers("/doener", "/doener/*").authenticated()
                .requestMatchers("/stompbroker", "/api/**", "/images/**", "/style.css").permitAll())
                .formLogin(folo -> folo.defaultSuccessUrl("/doener"))
                .csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console())
                        .ignoringRequestMatchers("/benutzer/**"))
                .headers(hdrs -> hdrs.frameOptions(fo -> fo.sameOrigin()))
                .build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainAPI(HttpSecurity http) throws Exception {
        return http.securityMatchers(s -> s.requestMatchers("/api/**", "/stompbroker"))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz.requestMatchers("/api/token", "/stompbroker").permitAll()
                        .requestMatchers("/api/**").permitAll())
                .oauth2ResourceServer(o -> o.jwt(j -> j.decoder(jwtDecoder())))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint((req, res, ex) -> res.sendError(401)))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] keyBytes = schluessel.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }
}
