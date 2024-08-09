
package cobranza.v2.pgt.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cobranza.v2.pgt.com.models.entity.Shopify;

@Repository
public interface IShopifyDao extends
                             JpaRepository<Shopify, Long> {
  
}
