package com.gseg.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuración de seguridad.
 * @author Jesús López Barragán
 *
 */
@Configuration
@EnableWebSecurity
public class SeguridadConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * Servicion de UserDetails.
	 */
	@Autowired
	UserDetailsService userDetailsService;
	
	/**
	 * Encripta la contraseña.
	 * @return Devuelve la contraseña encriptada.
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	/**
	 * Configuración de seguridad.
	 */
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
				
		http
			.authorizeRequests()			
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/gestor/**").hasRole("ADMIN")
				.antMatchers("/gestor/**").hasRole("GESTOR")
				.antMatchers("/usuario/**").hasRole("USUARIO")
				.antMatchers("/", "/webjars/**" , "/js/**" , "/imgs/**" , "/css/**", "/h2-console/**", "/public/**", "/auth/**", "/files/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/gseg/login")
				.defaultSuccessUrl("/gseg", true)				
				.loginProcessingUrl("/gseg/login-post")
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/gseg/logout") 
				.logoutSuccessUrl("/gseg/login");
		
		http.csrf().disable();
        http.headers().frameOptions().disable();
		
	}	

}
