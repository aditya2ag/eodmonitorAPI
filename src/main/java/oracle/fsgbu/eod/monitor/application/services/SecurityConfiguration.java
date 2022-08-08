package oracle.fsgbu.eod.monitor.application.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import oracle.fsgbu.eod.monitor.application.services.filter.JwtRequestFilterCustom;
import oracle.fsgbu.eod.monitor.application.services.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

	private void debug(String msg) {
		log.debug(this.getClass().getName() + "-->" + msg);
		System.out.println(this.getClass().getName() + "-->" + msg);
	}

	/*
	 * @Autowired UserDetailsService userDetailsService;
	 */
	private static final String[] PUBLIC_URLS = { "/service/appLogin", "/v2/api-docs", "/v3/api-docs",
			"/swagger-resources/**", "/swagger-ui/**", "/webjars/**" };

	@Autowired
	JwtRequestFilterCustom jwtRequestFilterCustom;

	@Bean(name = BeanIds.USER_DETAILS_SERVICE)
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean()
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	AuthenticationManager authManagerBean() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.userDetailsService(userDetailsService());
		debug("Inside configure1");
		auth.authenticationProvider(authenticationProvider());
		// super.configure(auth);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// formLogin().usernameParameter("email").permitAll();
		http.csrf().disable().cors().disable();

		/*
		 * .antMatchers("/service/appLogin").permitAll()
		 * .antMatchers("/v3/api-docs").permitAll()
		 */
		// http.authorizeHttpRequests().antMatchers("/service/*").permitAll();

		http.authorizeRequests().antMatchers(PUBLIC_URLS).permitAll().
		antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		.anyRequest().authenticated().and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilterCustom, UsernamePasswordAuthenticationFilter.class);
		debug("Inside SecurityConfiguration");
		// super.configure(http);
	};
}
