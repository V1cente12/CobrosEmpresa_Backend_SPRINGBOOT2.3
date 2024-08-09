
package cobranza.v2.pgt.com.models.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.dao.IPersonasDao;
import cobranza.v2.pgt.com.models.dao.IUsuariosDao;
import cobranza.v2.pgt.com.models.entity.Personas;
import cobranza.v2.pgt.com.models.services.IPersonasServ;
import cobranza.v2.pgt.com.utils.excepcions.NotFoundException;

@Service
public class IPersonasImpl implements
                           IPersonasServ {
  
  // private Logger logger =
  // LoggerFactory.getLogger(IPersonaEmpresaImpl.class);
  
  @Autowired
  private IPersonasDao personaempresaDao;
  
  @Autowired
  private IUsuariosDao usuarioDao;
  
  @Override
  @Transactional
  public List<Personas> listarAll(String estado,
                                  Long idempresa) {
    return personaempresaDao.listarpersonaUserEmp(estado, idempresa);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Personas listarID(String estado,
                           Long id) {
    return personaempresaDao.obtenerByEstadoAndIdpersona(estado, id);
  }
  
  @Override
  @Transactional
  public Page<?> listarAllPage(String estado,
                               String nombre,
                               Long idempresa,
                               Pageable p) {
    return personaempresaDao.listaBuscarEstadoNombre(estado, nombre, p);
  }
  
  @Override
  @Transactional
  public Personas guardar(Personas p) { return personaempresaDao.save(p); }
  
  @Override
  @Transactional
  public void eliminar(String estado,
                       String usuario,
                       Long id) {
    if (personaempresaDao.findById(id).isPresent( )) { personaempresaDao.elimnar(estado, usuario, id); }
    if (usuarioDao.existsByIdusuario(id)) {
      usuarioDao.eliminar(estado, usuario, id);
      usuarioDao.eliminarRolUsr(estado, usuario, id);
    }
    
  }
  
  @Override
  @Transactional(readOnly = true)
  public Page<Personas> listarPersonaNotUsuario(String estado,
                                                String nombre,
                                                Pageable p) {
    return personaempresaDao.listaPersonaNotUsuario(estado, nombre, p);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Personas valorDocumento(String valor) { return personaempresaDao.buscarValorDoc(valor); }
  
  @Override
  @Transactional
  public boolean valorDoc(String valor) { return personaempresaDao.ValorDoc(valor); }
  
  @Override
  @Transactional(readOnly = true)
  public List<?> BuscarRepresentanteEmpresa(String buscar) {
    return personaempresaDao.BuscarRepresentanteEmpresa(buscar);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<?> buscarUnicoValor(String valor_documento) {
    return personaempresaDao.buscarUnicoValor(valor_documento);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<?> PersonaByClienteEmpresa(String valor,
                                             Long idempresa) {
    return personaempresaDao.PersonaByClienteEmpresa(valor, idempresa);
  }
  
  @Override
  @Transactional
  public void updateCorreo(String correo,
                           Long id) { personaempresaDao.updateCorreo(correo, id); }
  
  @Override
  @Transactional
  public List<Personas> guardarAll(List<Personas> p) { return personaempresaDao.saveAll(p); }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Personas> PersonaByCorreoPersonaAndIdempresa(String correo,
                                                               Long idempresa) {
    Optional<Personas> p = personaempresaDao.PersonaByCorreoPersonaAndIdempresa(correo, idempresa);
    System.out.println(p.isPresent( ));
    return Optional.of(p.orElseThrow(( ) -> new NotFoundException("No hay datos de correo: " + correo
      + ", idempresa : " + idempresa)));
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Personas> PersonaByValordocumentoPersonaAndIdempresa(String valor,
                                                                       Long idempresa) {
    return Optional.of(personaempresaDao.PersonaByValordocumentoPersonaAndIdempresa(valor, idempresa).orElseThrow(( ) -> new NotFoundException("No hay datos de numero de documento: "
      + valor + ", idempresa : " + idempresa)));
  }
  
}
