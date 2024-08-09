
package cobranza.v2.pgt.com.config.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends
                               WebSecurityConfigurerAdapter {
  
  @Autowired
  private UserDetailsService user;
  
  @Bean
  @Autowired
  public BCryptPasswordEncoder contrasenaCodificada( ) { return new BCryptPasswordEncoder( ); }
  
  @Override
  @Autowired
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.user);
  }
  
  @Override
  @Bean("gestorAutentificacion")
  protected AuthenticationManager authenticationManager( ) throws Exception {
    return super.authenticationManager( );
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests( )
        .anyRequest( )
        .authenticated( )
        .and( )
        .csrf( )
        .disable( )
        .sessionManagement( )
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }
  
}
