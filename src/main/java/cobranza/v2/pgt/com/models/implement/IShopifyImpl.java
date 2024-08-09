
package cobranza.v2.pgt.com.models.implement;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import cobranza.v2.pgt.com.models.dao.IShopifyDao;
import cobranza.v2.pgt.com.models.entity.Shopify;
import cobranza.v2.pgt.com.models.services.IShopifyServ;

@Service
public class IShopifyImpl implements
                          IShopifyServ {
  
  @Autowired
  private IShopifyDao shopifyDao;
  
  @Autowired
  private RestOperations restOperations;
  @Value("${url.temporal}")
  private String _Url;
  @Value("${port.cliente}")
  private String _Port;
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Shopify> obtenerId(Long id) { return shopifyDao.findById(id); }
  
  @Override
  public Shopify guardar(Shopify entity) { return null; }
  
  @Override
  @Transactional(readOnly = true)
  public Map<String, Object> verificaToken(String token) {
    HttpHeaders headers = new HttpHeaders( );
    headers.setContentType(MediaType.APPLICATION_JSON);
    
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://" + this._Url + ":" + this._Port
      + "/auxiliar/verificar")
                                                       .queryParam("token", token);
    
    HttpEntity<?> requestEntity = new HttpEntity<>(headers);
    
    Map<String, Object> ret = new HashMap<>( );
    ResponseEntity<String> respuesta = null;
    System.out.println("URL TOKEN : " + builder.build( )
                                               .encode( )
                                               .toUriString( ));
    try {
      respuesta = restOperations.exchange(builder.build( )
                                                 .encode( )
                                                 .toUriString( ), HttpMethod.POST, requestEntity,
        String.class);
      System.out.println("RESPUESTA : " + respuesta.getBody( ));
      
      // JSONObject jsResp = new JSONObject(respuesta.getBody().toString());
      
      if (respuesta.getStatusCodeValue( ) == HttpStatus.SC_OK) {
        ret.put("ESTADO", "OK");
        ret.put("MENSAJE", "");
        ret.put("RESPUESTA", ""); // jsResp.getString("mensaje").toString());
      }else {
        ret.put("ESTADO", "NOK");
        ret.put("MENSAJE", "Error al verificar Token.");
        ret.put("RESPUESTA", ""); // jsResp.getString("mensaje").toString());
      }
    }
    catch(HttpClientErrorException e) {
      ret.put("ESTADO", "NOK");
      ret.put("RESPUESTA", e.getLocalizedMessage( ));
      
    }
    
    return ret;
  }
  
}
