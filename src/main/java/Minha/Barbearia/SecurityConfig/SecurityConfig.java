package Minha.Barbearia.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Configuração de segurança do Spring Security.
 *
 * Como o projeto ainda está em desenvolvimento, todos os endpoints
 * estão liberados (permitAll). Em produção, isso deve ser restrito.
 *
 * CSRF desabilitado (API REST stateless).
 * Sessão stateless (sem sessão HTTP).
 * H2 Console liberado para acesso via navegador.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
     * Configura o filtro de segurança.
     *
     * .csrf(csrf -> csrf.disable()) → desabilita proteção CSRF
     * .sessionManagement(STATELESS) → sem sessão HTTP (tokens JWT no futuro)
     * .authorizeHttpRequests → define regras de autorização
     * .headers(frameOptions.disable()) → permite iframe (H2 Console)
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().permitAll()
                )
                .headers(headers ->
                        headers.frameOptions(frame -> frame.disable()))
                .build();
    }

    /*
     * Bean de criptografia de senha.
     * Usa BCrypt, algoritmo recomendado para senhas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Bean do AuthenticationManager.
     * Necessário para autenticação via Spring Security.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}