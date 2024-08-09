
package cobranza.v2.pgt.com.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.Personas;

@Service
public interface IPersonasServ {
  
  public List<Personas> listarAll(String estado,
                                  Long idempresa);
  
  public Personas listarID(String estado,
                           Long id);
  
  public Page<?> listarAllPage(String estado,
                               String nombre,
                               Long idempresa,
                               Pageable p);
  
  public Personas guardar(Personas p);
  public List<Personas> guardarAll(List<Personas> p);
  
  public void eliminar(String estado,
                       String usuario,
                       Long id);
  
  public Page<Personas> listarPersonaNotUsuario(String estado,
                                                String nombre,
                                                Pageable p);
  
  public Personas valorDocumento(String valor);
  
  Optional<?> buscarUnicoValor(String valor_documento);
  
  Optional<?> PersonaByClienteEmpresa(String valor,
                                      Long idempresa);
  
  public boolean valorDoc(String valor);
  
  List<?> BuscarRepresentanteEmpresa(String buscar);
  
  public void updateCorreo(String correo,
                           Long id);
  
  public Optional<Personas> PersonaByCorreoPersonaAndIdempresa(String correo,
                                                               Long idempresa);
  
  public Optional<Personas> PersonaByValordocumentoPersonaAndIdempresa(String valor,
                                                                       Long idempresa);
  
}
