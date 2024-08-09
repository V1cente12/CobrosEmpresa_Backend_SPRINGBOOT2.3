
package cobranza.v2.pgt.com.config.JWT;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends
                                  ResourceServerConfigurerAdapter {
  
  @Value("#{'${cors}'}")
  private String _cors;
  
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests( )
        .antMatchers("/UpdatePass", "/cobranzaV2/archivo/deuda/**", "/cobranzaV2/documento/**",
          "/cobranzaV2/cliente/file", "/cobranzaV2/usuario/enviarTipo", "/cobranzaV2/usuario/cambiarClave",
          "/cobranzaV2/tipoempresa", "/cobranzaV2/recibo/kiosco", "/cobranzaV2/recibo/guardarFile",
          "/cobranzaV2/itemventa", "/cobranzaV2/link/todolink", "/cobranzaV2/cobranzaCliente/**",
          "/cobranzaV2/personas/**", "**/personas/**", "/cobranza/**", "/swagger-resources/**",
          "/swagger-ui.html", "/v2/api-docs", "/webjars/**", "/PagoRest/**", "/SessionRest", "/PagoRest",
          "/PagoMovil", "/TransaccionPago/**", "/Crear/deudaApp/**", "/SessionRest/**", "/shopify/**",
          "/linkproducto/**", "/PagoRest/Kiosco/**", "/api/v1/carga-masiva/**","/")
        .permitAll( )
        /* .antMatchers(HttpMethod.GET,
         * "/api/personas/{id}").hasAnyRole("USER",
         * "ADMIN") .antMatchers(HttpMethod.POST,
         * "/api/personas/imagenes").hasAnyRole("USER",
         * "ADMIN") .antMatchers(HttpMethod.POST,
         * "/api/personas").hasRole("ADMIN").antMatchers(
         * "/api/personas/**").hasRole("ADMIN") */
        .anyRequest( )
        .authenticated( )
        .and( )
        .cors( )
        .configurationSource(corsConfigurationSource( ));
  }
  
  @Bean
  public CorsConfigurationSource corsConfigurationSource( ) {
    CorsConfiguration config = new CorsConfiguration( );
    config.setAllowedOrigins(Arrays.asList("*"));
    config.setAllowedMethods(Arrays.asList("*"));
    config.setAllowCredentials(true);
    config.setAllowedHeaders(Arrays.asList("*"));
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource( );
    source.registerCorsConfiguration("/**", config);
    return source;
  }
  
  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilter( ) {
    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(
      new CorsFilter(corsConfigurationSource( )));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }
}
