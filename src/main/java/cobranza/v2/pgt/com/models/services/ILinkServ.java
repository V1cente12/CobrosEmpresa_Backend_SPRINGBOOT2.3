
package cobranza.v2.pgt.com.models.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.Deuda;
import cobranza.v2.pgt.com.models.entity.Link;

public interface ILinkServ {
  
  List<Link> listarAll(String estado,
                              Long idcliente);
  
  Link listarID(String estado,
                       Long id);
  
  Page<Link> listarAllPage(String estado,
                                  String nombre,
                                  Long idempresa,
                                  String fechaI,
                                  String fechaF,
                                  Pageable p);
  
  Link guardar(Link p);
  
  void eliminar(String estado,
                       String usuario,
                       Long id);
  
  List<?> listarIDbitacora(Long idbitacora);
  List<?> listarIDbitacoraSN(Long idbitacora);
  Optional<Link> findByIddeuda(String iddeuda);
  Optional<Link> findByIddeudaDesc(String iddeuda);
  Link GenerarLink(Deuda deuda,
                          Map<String, Object> claims);
  // public Link linkIddeuda(String iddeuda);
  Optional<Link> obtenerLinkShopify(String id);
}
