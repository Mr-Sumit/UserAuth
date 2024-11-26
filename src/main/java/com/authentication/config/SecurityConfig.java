package com.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.authentication.filter.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private JwtFilter jwtFilter;

	/*
	 * below method override the spring security filter chain, return custom
	 * SecurityFiterChain object. we can override it, when we have to implement our
	 * own security filter
	 */

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(customizer -> customizer.disable());

		Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> authorizeRequestsCustomizer = new Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry>() {
			@Override
			public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry t) {
				t.regexMatchers("/jwt/token", "/user/newuser").permitAll();
			}
		};
		http.authorizeRequests(authorizeRequestsCustomizer);
		// http.authorizeRequests(customizer ->
		// customizer.regexMatchers("/console/*").permitAll());

		Customizer<HeadersConfigurer<HttpSecurity>.FrameOptionsConfig> frameOptionsCustomizer = new Customizer<HeadersConfigurer<HttpSecurity>.FrameOptionsConfig>() {
			@Override
			public void customize(FrameOptionsConfig t) {
				t.disable();
			}
		};
		http.headers().frameOptions(frameOptionsCustomizer);
		// http.headers(customizer -> customizer.disable());

		http.authorizeRequests(request -> request.anyRequest().authenticated());

		Customizer<HttpBasicConfigurer<HttpSecurity>> httpBasicCustomizer = new Customizer<HttpBasicConfigurer<HttpSecurity>>() {
			@Override
			public void customize(HttpBasicConfigurer<HttpSecurity> t) {
				t.init(http);
			}
		};
		http.httpBasic(httpBasicCustomizer);
		// http.httpBasic(Customizer.withDefaults());

		Customizer<SessionManagementConfigurer<HttpSecurity>> sessionManagementCustomizer = new Customizer<SessionManagementConfigurer<HttpSecurity>>() {
			@Override
			public void customize(SessionManagementConfigurer<HttpSecurity> t) {
				t.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			}
		};

		http.sessionManagement(sessionManagementCustomizer);
		// http.sessionManagement(session ->
		// session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(this.userDetailService);
		// authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		authProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		auth.inMemoryAuthentication().withUser("user")
//		.password(encoder.encode("password")).roles("USER").and()
//		.withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN");
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
//	}
}
