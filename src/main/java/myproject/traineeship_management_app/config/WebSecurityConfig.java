package myproject.traineeship_management_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import myproject.traineeship_management_app.services.UserServiceImpl;

//import myy803.socialbookstore.services.UserServiceImpl;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomSecuritySuccessHandler customSecuritySuccessHandler;

	
	  @Bean public UserDetailsService userDetailsService() { return new
	  UserServiceImpl(); }
	 

    //Χρησιμοποιείται από το Spring Security για να συγκρίνει το password 
    // που βάζει ο χρήστης με το password που έχει αποθηκευτεί στη βάση.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        
         // Configures the custom authentication provider with 
         // the custom user details service and encoder
         
    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	
    	 // setup the filter chain, last we have the 
    	 // authentication filter for all requests
    	 
    	/*If you logged in as a PROFESSOR and hit /professor/dashboard, 
    	 * Spring saw that URL was neither in the permitAll list nor protected 
    	 * by a .hasAuthority("PROFESSOR") rule, so it treated it as “authenticated only” 
    	 * and then… redirected you back to the login page because 
    	 * (due to how your success handler and login page interact) it never fully allowed you through.
    	 */
    	
    	
                http.authorizeHttpRequests(
                		(authz) -> authz
                		.requestMatchers("/", "/login", "/register", "/save").permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/company/**").hasAnyAuthority("COMPANY")
                        .requestMatchers("/professor/**").hasAnyAuthority("PROFESSOR")
                        .requestMatchers("/committee/**").hasAuthority("COMMITTEE")
                        .requestMatchers("/student/**").hasAnyAuthority("STUDENT")
                        .anyRequest().authenticated()
                		);
                
                http.formLogin(fL -> fL.loginPage("/login")
                		.failureUrl("/login?error=true")
                        .successHandler(customSecuritySuccessHandler)
                        .usernameParameter("username")
                        .passwordParameter("password"));
                
                http.logout(logOut -> logOut.logoutUrl("/logout")
                		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                		.logoutSuccessUrl("/")
                		);

                
                // Sets the authentication provider with the custom
                // user details service and encoder
                 
          
                http.authenticationProvider(authenticationProvider());

                return http.build();
    }
    
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }

}