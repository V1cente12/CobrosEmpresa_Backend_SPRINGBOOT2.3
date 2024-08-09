
package cobranza.v2.pgt.com.config.JWT;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

		// private Logger logger =
		// LoggerFactory.getLogger(AuthorizationServerConfig.class);

		@Autowired
		private BCryptPasswordEncoder contrasenaCodificada;

		@Autowired
		private InfoAdicionalToken infoAdicionalToken;

		@Value("#{'${security.jwt.client-id}'}")
		private String client;

		@Value("#{'${security.jwt.secret-key}'}")
		private String secret;

		@Value("#{'${security.jwt.grant-type}'}")
		private String granttype;

		@Value("#{'${security.jwt.expire-lengt}'}")
		private int expire;

		@Autowired
		@Qualifier("gestorAutentificacion")
		private AuthenticationManager gestorAutentificacion;

		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
				security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		}

		@Override
		@Autowired
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
				clients.inMemory().withClient(client).secret(contrasenaCodificada.encode(secret))
				  .scopes("read","write").authorizedGrantTypes(granttype,"refresh_token")
				  .accessTokenValiditySeconds(expire).refreshTokenValiditySeconds(-1);
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
				TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
				tokenEnhancerChain
				  .setTokenEnhancers(Arrays.asList(infoAdicionalToken,convertidorTokenAcceso()));
				endpoints.authenticationManager(gestorAutentificacion).tokenStore(archivarToken())
				  .accessTokenConverter(convertidorTokenAcceso()).tokenEnhancer(tokenEnhancerChain);
		}

		@Bean
		public JwtTokenStore archivarToken( ) {
				return new JwtTokenStore(convertidorTokenAcceso());
		}

		@Bean
		public JwtAccessTokenConverter convertidorTokenAcceso( ) {
//				JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//				jwtAccessTokenConverter.setSigningKey(JwtConfigKey.RSA_Private);
//				jwtAccessTokenConverter.setVerifierKey(JwtConfigKey.RSA_Publica);
				return new JwtAccessTokenConverter();
		}

}
