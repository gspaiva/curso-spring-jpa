package com.gabriel.paiva.cursomc.cursomc.configs;

import com.gabriel.paiva.cursomc.cursomc.security.JWTAuthenticationFilter;
import com.gabriel.paiva.cursomc.cursomc.security.JWTAuthorizationFilter;
import com.gabriel.paiva.cursomc.cursomc.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile(value = "prod")
public class SecurityConfigProd extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/produtos/**",
            "/categorias/**"
    };

    private static final String[] PUBLIC_MATCHERS_POST = {
            "/clientes/**"
    };

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,PUBLIC_MATCHERS_GET).permitAll()
                .antMatchers(HttpMethod.POST,PUBLIC_MATCHERS_POST).permitAll()
                .anyRequest().authenticated();

        http.addFilter(new JWTAuthenticationFilter(authenticationManager(),jwtUtils));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(),jwtUtils,userDetailsService));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
