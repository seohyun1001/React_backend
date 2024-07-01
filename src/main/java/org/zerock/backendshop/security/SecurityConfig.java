package org.zerock.backendshop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.zerock.backendshop.security.handler.Custom403Handler;
import org.zerock.backendshop.security.jwt.JwtAuthorizationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    AuthenticationManager : 스프링 시큐리티의 인증을 담당
//    AuthenticationManager 빈 생성시 스프링의 내부 동작으로 인해 위에서 작성한 customUserDetailsService와
//    PasswordEncoder가 자동으로 설정되어 유저가 로그인을 하면 유저이름으로 customUserDetailsService를 이용해
//    유저를 찾은 후 암호화된 패스워드와 유저가 입력한 패스워드를 비교(이때 패스워드 엔코더 객체필요) 하여 인
//    증을 한다.



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(httpSecurityCorsConfigurer -> corsConfigurationSource())
//                .sessionManagement((session)->session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests((authz)-> authz
//                        .requestMatchers("/api/authentication/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/product").permitAll()
//                        .requestMatchers("/api/product/**").hasRole(Role.ADMIN.name())
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new Custom403Handler();
    }

}
