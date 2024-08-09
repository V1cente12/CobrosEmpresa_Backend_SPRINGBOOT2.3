
package cobranza.v2.pgt.com.models.services;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.entity.Usuarios;

import javax.swing.text.html.Option;

public interface IEmpresaServ {
  
  List<Empresas> listarAll(String estado);
  
  Empresas listarID(String estado, Long id);

  Optional<Empresas> obtenerEmpresa(Long idEmpresa);
  
  Empresas guardar(Empresas p);
  
  Page<?> empresaPage(String estado,
                      String buscar,
                      Pageable p);
  
  int cambio(String estado,
                    String usuario,
                    Long id);
  
  Optional<?> ListaEmpresaAux(Long idempresa);

  List<?> BuscarEmpresaEstado(String estado);

  List<?> BuscarCorreosOrNit(String buscar);

  List<Object[ ]> CONCILIACION2(String fechaI,
                                       String fechaF,
                                       Long idempresa) throws ParseException;

  Page<?> CONCILIACION(String fechaI,
                              String fechaF,
                              Long idempresa,
                              Pageable p) throws ParseException;

  void envioRegistro(Usuarios usuario,
                            String[ ] correos);

  void cambiologo(String logo,
                         Long idempresa);
}
