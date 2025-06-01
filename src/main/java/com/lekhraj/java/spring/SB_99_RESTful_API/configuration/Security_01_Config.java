package com.lekhraj.java.spring.SB_99_RESTful_API.configuration;

import com.lekhraj.java.spring.SB_99_RESTful_API.servlet.CustomFilter_1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import java.util.Arrays;
import java.util.List;


@ConditionalOnProperty(havingValue = "security_01", name = "sb.customize.security")
@Configuration
@EnableWebSecurity
public class Security_01_Config
{
    @Value("${spring.security.oauth2.client.registration.okta.scope}") String scope;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/ignore1", "/ignore2");
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("user1")
                .password(passwordEncoder().encode("user1Pass"))
                .roles("USER")
                .build();
        UserDetails user2 = User.withUsername("user2")
                .password(passwordEncoder().encode("user2Pass"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("adminPass"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, user2, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //========================
    // SecurityFilterChain
    // ====================
    @ConditionalOnProperty(havingValue = "SecurityFilterChain_01", name = "sb.customize.SecurityFilterChain")
    @Bean
    public SecurityFilterChain filterChainBasicAuth(HttpSecurity http) throws Exception
    {
        http.
                authorizeHttpRequests(
                registry -> registry
                        .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")//.hasAuthority("SCOPE_scope-1")
                        .requestMatchers("/security/admin/**").hasRole("ADMIN")
                        .requestMatchers("/security/user/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/**").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .csrf(csrf->csrf.disable())
                .httpBasic(Customizer.withDefaults())
                //.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                ;
                //.exceptionHandling()
                //.addFilter(new CustomFilter_1())

                // addFilterAfter/Before()
                // httpBasic -> httpBasic.authenticationEntryPoint(new DigestAuthenticationEntryPoint()
                // .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // .formLogin( x -> {} )
                // .logout( x -> {} )
                ;

        return http.build();
    }

    @ConditionalOnProperty(havingValue = "SecurityFilterChain_02", name = "sb.customize.SecurityFilterChain")
    @Bean
    public SecurityFilterChain filterChainToken3(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(registry -> registry
                                .requestMatchers("/swagger-ui/**", "/actuator/**", "/v3/api-docs/**","/h2-console","/micrometer/**", "/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated());
        //http.oauth2ResourceServer(oAuth2 -> oAuth2.jwt(Customizer.withDefaults()));
        http.oauth2ResourceServer(oAuth2 -> oAuth2.jwt(jwt -> jwt
                    .jwtAuthenticationConverter(new CustomJwtAuthenticationConverter(scope))
            ));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri("https://dev-16206041.okta.com/oauth2/ausldbxlfakbwq32P5d7/v1/keys").build();

        jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
                new JwtTimestampValidator(),
                new JwtIssuerValidator("https://dev-16206041.okta.com/oauth2/ausldbxlfakbwq32P5d7"),
                new JwtClaimValidator<List<String>>("scp",
                        scopes -> scopes != null && scopes.contains("app_read_lekhraj"))
        ));
        return jwtDecoder;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter()
    {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("SCOPE_"); // Matches with scopes in token
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return jwtAuthenticationConverter;
    }

}
