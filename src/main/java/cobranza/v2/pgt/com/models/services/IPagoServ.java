
package cobranza.v2.pgt.com.models.services;

import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.Pagos;

@Service
public interface IPagoServ {
  
  public Pagos obtenerIddeuda(Long id);
  public Pagos guardar(Pagos pago);
  
}
