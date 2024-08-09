
package cobranza.v2.pgt.com.models.services;

import java.util.List;
import java.util.Optional;
import cobranza.v2.pgt.com.models.entity.Comision;

public interface IComisionServ {
  
  Optional<Comision> BuscarIdempresaNullvendor(Long idempresa, String estado);

  List<Comision> obtenerComisiones(String estado);

  Optional<Comision> BuscarVendor(String vendor,
                                         String estado);
  
  Comision guardar(Comision entiry);
}
