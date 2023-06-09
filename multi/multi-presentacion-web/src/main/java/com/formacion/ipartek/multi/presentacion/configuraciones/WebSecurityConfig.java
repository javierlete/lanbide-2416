package com.formacion.ipartek.multi.presentacion.configuraciones;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad de la aplicación
 * @author javierlete
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Autowired
	private DataSource dataSource;
	
	/**
	 * Codificación de las password
	 * @return BCrypt
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	/**
	 * Autorizaciones basadas en los usuarios de nuestra base de datos
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
	  throws Exception {
	    auth.jdbcAuthentication().dataSource(dataSource)
	    .usersByUsernameQuery("SELECT email, password, 1 FROM usuarios WHERE email = ?")
	    .authoritiesByUsernameQuery("""
	    		SELECT u.email, CONCAT('ROLE_',r.nombre)
	    		FROM usuarios u
	    		JOIN roles_usuarios ur ON u.id = ur.usuarios_id
	    		JOIN roles r ON ur.roles_id = r.id
	    		WHERE u.email = ?
	    		"""
	    );
	}
	
	/**
	 * <p>Configuración de las autorizaciones de la aplicación</p>
	 * <ul>
	 * <li>Sólo los <code>USER</code> pueden acceder a <code>/usuarios</code></li>
	 * <li>Sólo los <code>ADMIN</code> pueden acceder a <code>/admin</code></li>
	 * <li>Todos los usuarios pueden acceder al resto la web</li>
	 * </ul>
	 * @param auth
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable())
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/usuarios/**").hasRole("USER")
				.anyRequest().permitAll()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.defaultSuccessUrl("/procesar-usuario")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}

// https://www.baeldung.com/spring-security-jdbc-authentication

// https://bcrypt-generator.com/
}
