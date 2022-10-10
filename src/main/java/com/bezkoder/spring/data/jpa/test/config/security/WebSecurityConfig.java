package com.bezkoder.spring.data.jpa.test.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private UserDetailsServiceImpl userDetailsService;
  @Autowired private DataSource dataSource;
  @Autowired private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
  @Autowired private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder;
  }

  @Bean
  public AuthenticationSuccessHandler successHandler() {
    SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
    handler.setUseReferer(true);
    return handler;
  }

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    // Sét đặt dịch vụ để tìm kiếm User trong Database.
    // Và sét đặt PasswordEncoder.
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests().antMatchers("/", "/accout/**").permitAll();
    http.authorizeRequests()
        .antMatchers("/user/**")
        .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
    http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");
    http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

    http.authorizeRequests()
        .and()
        .formLogin()
        .loginProcessingUrl("/j_spring_security_login") //
        .loginPage("/accout/login")
        .successHandler(customAuthenticationSuccessHandler)
        .failureHandler(customAuthenticationFailureHandler)
        .usernameParameter("username") //
        .passwordParameter("password") //
        .and()
        .logout() //
        .logoutUrl("/j_spring_security_logout") //
        //        .logoutSuccessUrl("/login");
        .logoutSuccessUrl("/accout/login?message=logout");

    // Cấu hình Remember Me.
    http.authorizeRequests()
        .and() //
        .rememberMe()
        .tokenRepository(this.persistentTokenRepository()) //
        .tokenValiditySeconds(1 * 24 * 60 * 60);
    //    http.sessionManagement().invalidSessionUrl("/accout/login?message=timeout");
       // Cấu hình concurrent session
    http.sessionManagement()
        .sessionFixation()
        .newSession()
        .maximumSessions(1)
        .expiredUrl("/accout/login?message=max_session")
        .maxSessionsPreventsLogin(true);
  }

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
    db.setDataSource(dataSource);
    return db;
  }
}
