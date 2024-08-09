
package cobranza.v2.pgt.com.models.implement;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.dao.IEmpresasDao;
import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.entity.Usuarios;
import cobranza.v2.pgt.com.models.services.EmailService;
import cobranza.v2.pgt.com.models.services.IEmpresaServ;
import cobranza.v2.pgt.com.utils.Auxiliar;
import cobranza.v2.pgt.com.utils.otros.MailRequest;

@Service
public class IEmpresaImpl implements
                          IEmpresaServ {
  
  @Autowired
  private IEmpresasDao empresaDao;
  @Autowired
  private Auxiliar aux;
  @Autowired
  private EmailService emailServ;
  
  @Override
  @Transactional(readOnly = true)
  public List<Empresas> listarAll(String estado) { return empresaDao.findByEstado(estado); }
  
  @Override
  @Transactional(readOnly = true)
  public Empresas listarID(String estado,
                           Long id) { return empresaDao.buscarEmpresaID(id, estado); }

  @Override
  public Optional<Empresas> obtenerEmpresa(Long idEmpresa) {
    return empresaDao.findById(idEmpresa);
  }

  @Override
  public Empresas guardar(Empresas p) { return empresaDao.save(p); }
  
  @Override
  public int cambio(String estado,
                    String usuario,
                    Long id) {
    System.out.println(estado + "--- " + usuario + "---" + id);
    return empresaDao.cambio(estado, usuario, id);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<?> ListaEmpresaAux(Long idempresa) { return empresaDao.ListaEmpresaAux(idempresa); }
  
  @Override
  @Transactional(readOnly = true)
  public Page<?> empresaPage(String estado,
                             String buscar,
                             Pageable p) {
    return empresaDao.empresaPage(estado, buscar, p);
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<?> BuscarCorreosOrNit(String buscar) { return empresaDao.BuscarCorreosOrNit(buscar); }
  
  @Override
  @Transactional(readOnly = true)
  public Page<?> CONCILIACION(String fechaI,
                              String fechaF,
                              Long idempresa,
                              Pageable p) throws ParseException {
    System.out.println(fechaI + " --- " + fechaF);
    Page<?> auxiliar = empresaDao.CONCILIACION(aux.conversionStringDate(fechaI, "dd/MM/yyyy hh:mm:ss"), aux
                                                                                                           .conversionStringDate(
                                                                                                             fechaF,
                                                                                                             "dd/MM/yyyy hh:mm:ss"),
      idempresa, p);
    return auxiliar;
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<?> BuscarEmpresaEstado(String estado) { return empresaDao.BuscarEmpresaEstado(estado); }
  
  @Override
  @Transactional(readOnly = true)
  public List<Object[ ]> CONCILIACION2(String fechaI,
                                       String fechaF,
                                       Long idempresa) throws ParseException {
    List<Object[ ]> auxiliar = empresaDao.CONCILIACION_excel(aux.conversionStringDate(fechaI,
      "dd/MM/yyyy hh:mm:ss"), aux.conversionStringDate(fechaF, "dd/MM/yyyy hh:mm:ss"), idempresa);
    return auxiliar;
  }
  
  @Async("asyncExecutor")
  @Override
  @Transactional
  public void envioRegistro(Usuarios usuario,
                            String[ ] correos) {
    Map<String, Object> response = new HashMap<>( );
    MailRequest mailrequest = new MailRequest( );
    response.put("empresa", usuario.getIdempresa( )
                                   .getRazon_social( ));
    response.put("nit", usuario.getIdempresa( )
                               .getNit( ));
    response.put("pagina", usuario.getIdempresa( )
                                  .getPagina_web( ));
    response.put("telefono", usuario.getIdempresa( )
                                    .getTelefono_fijo( ));
    response.put("direccion", usuario.getIdempresa( )
                                     .getDireccion( ));
    response.put("tipo", usuario.getIdempresa( )
                                .getDocumentoEmpresas( )
                                .get(0)
                                .getDocumento( )
                                .getTipoEmpresa( )
                                .getNombre( ));
    response.put("ciudad", usuario.getIdempresa( )
                                  .getCiudad( ));
    response.put("referido", usuario.getIdpersona( )
                                    .getApellido_marital( ));
    response.put("nombre", usuario.getIdpersona( )
                                  .getNombres( ));
    response.put("apellidos", usuario.getIdpersona( )
                                     .getApellido_paterno( ));
    response.put("correo", usuario.getIdpersona( )
                                  .getCorreo( ));
    response.put("cedula", usuario.getIdpersona( )
                                  .getValor_documento( ));
    response.put("celular", usuario.getIdpersona( )
                                   .getTelefono( ));
    mailrequest.setSubject("Registro de empresa");
    mailrequest.setFrom("ventas@pagatodo360.net");
    // String pdf="ManUsuPor.pdf";
    String pdf = "";
    if (!emailServ.sendEmail(correos, mailrequest, response, "confirmacion.html", pdf)) {
      emailServ.sendEmail(correos, mailrequest, response, "confirmacion.html", pdf);
    }
  }
  
  @Override
  public void cambiologo(String logo,
                         Long idempresa) { empresaDao.subirlogo(logo, idempresa); }
}
