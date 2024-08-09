
package cobranza.v2.pgt.com;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Executor;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.sf.jasperreports.engine.JRException;

@SpringBootApplication
@EnableAsync
@Configuration
public class CobranzaV2Application implements
                                   CommandLineRunner,
                                   WebMvcConfigurer
// ,AsyncConfigurer
{
  
  private static final Logger log = LoggerFactory.getLogger(CobranzaV2Application.class);
  
  @Value("#{'${spring.application.version}'}")
  private String VERSION;
  
  @Autowired
  private BCryptPasswordEncoder contrasenaCodificada;
  
  public static void main(String[ ] args) throws JRException {
    SpringApplication.run(CobranzaV2Application.class, args);
  }
  
  @Override
  public void run(String... args) {
    log.info("***************************************************************************");
    log.info("*********\t\t\tVersion : " + VERSION + "\t\t\t*********");
    log.info("***************************************************************************");
    // for(int i = 0;i < 1;i++) {
    // System.out.println(contraseñaCodificada.encode("addiuva"));
    // }
  }
  
  @Bean(name = "asyncExecutor")
  public Executor asyncExecutor( ) {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor( );
    executor.setCorePoolSize(3);
    executor.setMaxPoolSize(3);
    executor.setQueueCapacity(100);
    executor.setThreadNamePrefix("AsynchThread-");
    executor.initialize( );
    return executor;
  }
  
  private static final String[ ] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/META-INF/resources/",
                                                                 "classpath:/resources/","classpath:/static/",
                                                                 "classpath:/public/",
                                                                 "classpath:/static/vendor/",
                                                                 "classpath:/static/custom/"};
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**")
            .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
  }
  
  @Bean(name = "simpleRestTemplate")
  @Primary
  public RestTemplate restTemplateByPassSSL( ) throws KeyStoreException,
                                               NoSuchAlgorithmException,
                                               KeyManagementException {
    TrustStrategy acceptingTrustStrategy = (X509Certificate[ ] chain,
                                            String authType) -> true;
    HostnameVerifier hostnameVerifier = (s,
                                         sslSession) -> true;
    SSLContext sslContext = SSLContexts.custom( )
                                       .loadTrustMaterial(null, acceptingTrustStrategy)
                                       .build( );
    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
    CloseableHttpClient httpClient = HttpClients.custom( )
                                                .setSSLSocketFactory(csf)
                                                .build( );
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory( );
    requestFactory.setHttpClient(httpClient);
    
    return new RestTemplate(requestFactory);
  }
  
  @Bean
  public ClientHttpRequestFactory createClientHttpRequestFactory(@Value("${connect.timeout}") final int connectTimeout,
                                                                 @Value("${read.timeout}") int readTimeout) {
    HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory =
                                                                                  new HttpComponentsClientHttpRequestFactory( );
    httpComponentsClientHttpRequestFactory.setReadTimeout(readTimeout);
    httpComponentsClientHttpRequestFactory.setConnectTimeout(connectTimeout);
    
    return httpComponentsClientHttpRequestFactory;
    
  }
  
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) { return builder.build( ); }
}
