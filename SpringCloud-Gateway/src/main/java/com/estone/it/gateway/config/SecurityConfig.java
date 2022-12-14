package com.estone.it.gateway.config;

import java.util.LinkedList;

import com.estone.it.gateway.exception.AuthEntryPointException;
import com.estone.it.gateway.handler.JsonServerAuthenticationFailureHandler;
import com.estone.it.gateway.handler.JsonServerAuthenticationSuccessHandler;
import com.estone.it.gateway.handler.JsonServerLogoutSuccessHandler;
import com.estone.it.gateway.authentication.AuthenticationConverter;
import com.estone.it.gateway.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

/**
* @Author: pilsy
* @Date: 2020/6/29 0029 16:54
*/
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

   @Autowired
   private AuthenticationConverter authenticationConverter;

   @Autowired
   private AuthorizeConfigManager authorizeConfigManager;

   @Autowired
   private AuthEntryPointException serverAuthenticationEntryPoint;

   @Autowired
   private JsonServerAuthenticationSuccessHandler jsonServerAuthenticationSuccessHandler;

   @Autowired
   private JsonServerAuthenticationFailureHandler jsonServerAuthenticationFailureHandler;

   @Autowired
   private JsonServerLogoutSuccessHandler jsonServerLogoutSuccessHandler;

   @Autowired
   private AuthenticationManager authenticationManager;

   private static final String[] AUTH_WHITELIST = new String[]{"/login", "/logout"};

   @Bean
   public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
       SecurityWebFilterChain chain = http.formLogin()
               .loginPage("/login")
               // ????????????handler
               .authenticationSuccessHandler(jsonServerAuthenticationSuccessHandler)
               // ????????????handler
               .authenticationFailureHandler(jsonServerAuthenticationFailureHandler)
               // ???????????????handler
               .authenticationEntryPoint(serverAuthenticationEntryPoint)
               .and()
               .logout()
               // ????????????handler
               .logoutSuccessHandler(jsonServerLogoutSuccessHandler)
               .and()
               .csrf().disable()
               .httpBasic().disable()
               .authorizeExchange()
               // ???????????????
               .pathMatchers(AUTH_WHITELIST).permitAll()
               // ??????????????????
               .anyExchange().access(authorizeConfigManager)
               .and().build();
       // ????????????????????????????????????
       chain.getWebFilters()
               .filter(webFilter -> webFilter instanceof AuthenticationWebFilter)
               .subscribe(webFilter -> {
                   AuthenticationWebFilter filter = (AuthenticationWebFilter) webFilter;
                   filter.setServerAuthenticationConverter(authenticationConverter);
               });
       return chain;
   }

   /**
    * ???????????????????????????????????????????????????????????????????????????
    * @return 
    */
   @Bean
   ReactiveAuthenticationManager reactiveAuthenticationManager() {
       LinkedList<ReactiveAuthenticationManager> managers = new LinkedList<>();
       managers.add(authenticationManager);
       return new DelegatingReactiveAuthenticationManager(managers);
   }


   /**
    * BCrypt????????????
    * @return 
    */
   @Bean
   public BCryptPasswordEncoder bcryptPasswordEncoder() {
       return new BCryptPasswordEncoder();
   }

}