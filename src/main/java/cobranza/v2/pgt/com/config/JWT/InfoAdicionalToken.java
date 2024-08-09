
package cobranza.v2.pgt.com.config.JWT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.entity.Kiosko;
import cobranza.v2.pgt.com.models.entity.Personas;
import cobranza.v2.pgt.com.models.entity.Usuarios;
import cobranza.v2.pgt.com.models.services.IEmpresaServ;
import cobranza.v2.pgt.com.models.services.IKioscoServ;
import cobranza.v2.pgt.com.models.services.IPersonasServ;
import cobranza.v2.pgt.com.models.services.IUsuariosServ;

@Component
public class InfoAdicionalToken implements
                                TokenEnhancer {
  
  private Logger logger = LoggerFactory.getLogger(InfoAdicionalToken.class);
  
  @Autowired
  private IUsuariosServ usuarioService;
  
  @Autowired
  private IPersonasServ personaService;
  
  @Autowired
  private IEmpresaServ empresaServ;
  
  @Autowired
  private IKioscoServ kioscoServ;
  
  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
                                   OAuth2Authentication authentication) {
    Map<String, Object> info = new HashMap<>( );
    logger.info("Generando Token...");
    // System.out.println(authentication.getCredentials( ));
    // System.out.println(authentication.getDetails( ));
    // System.out.println(authentication.getName( ));
    // System.out.println(authentication.getPrincipal( ));
    // System.out.println(authentication.getAuthorities( ));
    // System.out.println(authentication.getOAuth2Request( ));
    // System.out.println(authentication.getUserAuthentication( ));
    Usuarios dato = usuarioService.logeousuario(authentication.getName( ));
    info.put("info_adicional", "Hola que tal user..! ".concat(authentication.getName( )));
    Personas persona = personaService.listarID(String.valueOf('A'), dato.getIdpersona( )
                                                                        .getIdpersona( ));
    String usuario = persona.getNombres( ) + " " + (persona.getApellido_paterno( ) != null ? persona
                                                                                                    .getApellido_paterno( ) :
      "");
    Empresas emp = empresaServ.listarID("A", dato.getIdempresa( )
                                                 .getIdempresa( ));
    List<Kiosko> kiosco = kioscoServ.listarIdusuario(dato.getIdusuario( ));
    if (kiosco.size( ) != 0) {
      ArrayList<String> cajero = new ArrayList<String>( );
      for(int i = 0;i < kiosco.size( );i++) {
        cajero.add(kiosco.get(i)
                         .getCodigo( ));
      }
      info.put("cajero", cajero);
    }
    info.put("nombre", persona.getNombres( ));
    info.put("usuario", usuario);
    // info.put("rol", dato.getRoles( ));
    info.put("rol", "Usuario");
    info.put("apellidoP", persona.getApellido_paterno( ));
    info.put("apellidoM", persona.getApellido_materno( ));
    info.put("foto", emp.getLogo( ) == null ? "PAGATODO.png" : emp.getLogo( )
                                                                  .split(",")[0]);
    info.put("idpersona", persona.getIdpersona( ));
    info.put("idusuario", dato.getIdusuario( ));
    info.put("idempresa", dato.getIdempresa( )
                              .getIdempresa( ));
    info.put("empresa", emp.getRazon_social( ));
    info.put("listaMenus", dato.getListamenus( ));
    info.put("listaRecursos", dato.getListarecursos( ));
    logger.info("Token generado para el usuario: " + usuario);
    (( DefaultOAuth2AccessToken ) accessToken).setAdditionalInformation(info);
    return accessToken;
  }
}
