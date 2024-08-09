
package cobranza.v2.pgt.com.models.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.Empresas;

@Service
public interface ICanalPagoServ {
  
  public boolean crearPago(Empresas idempresa,
                           List<String> myList,
                           String valorqr,
                           String usuario);
}
