
package cobranza.v2.pgt.com.models.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.CyberSource;

@Service
public interface ICyberSourceServ {
  
  public List<CyberSource> obtenerIdRecibo(Long idrecibo);
}
