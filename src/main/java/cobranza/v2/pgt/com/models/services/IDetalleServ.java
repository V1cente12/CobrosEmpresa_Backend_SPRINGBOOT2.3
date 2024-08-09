
package cobranza.v2.pgt.com.models.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.Detalle;

@Service
public interface IDetalleServ {
  
  public List<Detalle> saveAll(List<Detalle> detalle);
  public Detalle save(Detalle detalle);
  public Long max( );
  public void bajaIdrecibo(Long idrecibo);
  public List<Detalle> BuscarCodItem(String codItem);
}
