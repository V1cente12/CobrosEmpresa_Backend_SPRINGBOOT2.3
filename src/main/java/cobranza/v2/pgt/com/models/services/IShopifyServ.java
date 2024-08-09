
package cobranza.v2.pgt.com.models.services;

import java.util.Map;
import java.util.Optional;

import cobranza.v2.pgt.com.models.entity.Shopify;

public interface IShopifyServ {
  
  public Optional<Shopify> obtenerId(Long id);
  public Shopify guardar(Shopify entity);
  public Map<String, Object> verificaToken(String token);
}
