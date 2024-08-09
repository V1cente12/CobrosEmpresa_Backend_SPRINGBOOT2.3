
package cobranza.v2.pgt.com.models.implement;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.dao.IDeudaDao;
import cobranza.v2.pgt.com.models.dao.ILinkDao;
import cobranza.v2.pgt.com.models.entity.Deuda;
import cobranza.v2.pgt.com.models.entity.Link;
import cobranza.v2.pgt.com.models.services.ILinkServ;
import cobranza.v2.pgt.com.utils.Auxiliar;

@Service
public class ILinkImpl implements
                       ILinkServ {
  
  private static final Logger log = LoggerFactory.getLogger(ILinkImpl.class);
  
  @Autowired
  private ILinkDao linkDao;
  
  @Autowired
  private IDeudaDao deudaDao;
  @Value("#{'${name.url}'}")
  private String URL;
  @Value("#{'${name.port}'}")
  private String PORT;
  @Autowired
  private Auxiliar aux;
  
  @Override
  @Transactional
  public List<Link> listarAll(String estado,
                              Long idcliente) {
    return linkDao.findByEstadoAndIdcliente(estado, idcliente);
  }
  
  @Override
  @Transactional
  public Link listarID(String estado,
                       Long id) { return linkDao.findByEstadoAndIdlink(estado, id); }
  
  @Override
  @Transactional
  public Page<Link> listarAllPage(String estado,
                                  String nombre,
                                  Long idempresa,
                                  String fechaI,
                                  String fechaF,
                                  Pageable p) {
    try {
      if (fechaI.equals("") && fechaF.equals("")) {
        return linkDao.listaLinkPage(idempresa, estado, nombre, aux.sumarDiasAFecha(new Date( ), -50000), aux
                                                                                                             .sumarDiasAFecha(
                                                                                                               new Date( ),
                                                                                                               1),
          p);
      }else {
        return linkDao.listaLinkPage(idempresa, estado, nombre, aux.conversionStringDate(fechaI), aux
                                                                                                     .sumarDiasAFecha(
                                                                                                       aux.conversionStringDate(
                                                                                                         fechaF),
                                                                                                       1), p);
      }
    }
    catch(Exception e) {
      
      e.printStackTrace( );
    }
    return null;
    
  }
  
  @Override
  @Transactional
  public Link guardar(Link p) { return linkDao.save(p); }
  
  @Override
  @Transactional
  public void eliminar(String estado,
                       String usuario,
                       Long id) {}
  
  @Override
  @Transactional(readOnly = true)
  public List<?> listarIDbitacora(Long idbitacora) { return linkDao.findByIdbitacora(idbitacora); }
  
  @Override
  @Transactional(readOnly = true)
  public List<?> listarIDbitacoraSN(Long idbitacora) { return linkDao.findByIdbitacoraSN(idbitacora); }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Link> findByIddeuda(String iddeuda) { return linkDao.findByIddeuda(iddeuda); }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Link> findByIddeudaDesc(String iddeuda) { return linkDao.findByIddeudaDesc(iddeuda); }
  
  @Override
  @Transactional
  public Link GenerarLink(Deuda deuda,
                          Map<String, Object> claims) {
    String jwtId = deuda.getIdcliente( )
                        .getIdempresa( )
                        .getIdempresa( )
                        .toString( );
    String jwtIssuer = deuda.getIddeuda( )
                            .toString( );
    String jwtSubject = deuda.getIdrecibo( )
                             .getGlosa2( );
    claims.put("references", "0");
    String jwt = aux.createJWT2(jwtId, jwtIssuer, jwtSubject, claims, deuda.getIdrecibo( )
                                                                           .getFecha_vencimiento( ));
    String url = URL + ":" + PORT + "/#/carrito?id=";
    Link l = new Link( );
    l.setCodigocliente(deuda.getIdcliente( )
                            .getCodigo_cliente( ));
    l.setCorreo(deuda.getIdcliente( )
                     .getIdpersona( )
                     .getCorreo( ));
    l.setEstado("A");
    l.setIddeuda(String.valueOf(deuda.getIddeuda( )));
    l.setIdcliente(deuda.getIdcliente( ));
    l.setLink(url);
    l.setToken(jwt);
    l.setMonto(deuda.getIdrecibo( )
                    .getMonto( ));
    log.info(url + jwt);
    return this.guardar(l);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Link> obtenerLinkShopify(String id) { return linkDao.obtenerLinkShopify(id); }
  
}
