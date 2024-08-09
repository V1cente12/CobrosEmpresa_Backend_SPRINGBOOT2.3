
package cobranza.v2.pgt.com.models.implement;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.dao.IRolesDao;
import cobranza.v2.pgt.com.models.dao.IUsuariosDao;
import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.entity.Personas;
import cobranza.v2.pgt.com.models.entity.Roles;
import cobranza.v2.pgt.com.models.entity.Usuarios;
import cobranza.v2.pgt.com.models.services.EmailService;
import cobranza.v2.pgt.com.models.services.IUsuariosServ;
import cobranza.v2.pgt.com.utils.Auxiliar;
import cobranza.v2.pgt.com.utils.excepcions.NotFoundException;
import cobranza.v2.pgt.com.utils.otros.MailRequest;

// import com.pagatodo360.entity.ObjAliasCredencialesEmpresa;
// import com.pagatodo360.entity.ObjAliasCriteriosEmpresa;
// import com.pagatodo360.entity.ObjAliasEmpresa;
// import com.pagatodo360.entity.ObjE2C051;
// import com.pagatodo360.entity.ObjE2C071;
// import com.pagatodo360.entity.ObjEmpresa;
// import com.pagatodo360.entity.ObjRequest;
// import com.pagatodo360.servicio.ClientePTD360;

@Service
public class IUsuariosImpl implements
                           IUsuariosServ,
                           UserDetailsService {
  
  private Logger logger = LoggerFactory.getLogger(IUsuariosImpl.class);
  @Autowired
  private IUsuariosDao usuariosDao;
  @Autowired
  private IRolesDao rolesDao;
  @Autowired
  private Auxiliar aux;
  @Autowired
  private EmailService emailServ;
  // @Autowired
  // private BCryptPasswordEncoder contrasenaCodificada;
  // private List<ObjAliasCriteriosEmpresa>
  // listaCriteriosXEmpresa;
  
  @Override
  @Transactional
  public List<Usuarios> listarAll(String estado) { return usuariosDao.findAllByEstado(estado); }
  
  @Override
  @Transactional
  public Usuarios listarID(String estado,
                           Long id) {
    return usuariosDao.findByEstadoAndIdusuario(estado, id);
  }
  
  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    logger.info("iniciando sesion el usuario: " + login);
    Usuarios seg = usuariosDao.findByLogin(login)
                              .get( );
    if (seg == null) {
      logger.error("Error al logearse: No existe los datos en el sistema");
      throw new UsernameNotFoundException("Error al logearse: No existe los datos en el sistema");
    }
    List<GrantedAuthority> authorities = null;;
    try {
      List<Roles> roles = rolesDao.roles(seg.getIdusuario( ));
      authorities = new ArrayList<GrantedAuthority>( );
      for(Roles role: roles) {
        authorities.add(new SimpleGrantedAuthority(role.getNombre( )));
      }
    }
    catch(Exception e) {
      e.printStackTrace( );;
    }
    return new User(seg.getLogin( ), seg.getClave( ), true, true, true, true, authorities);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Usuarios logeousuario(String login) {
    try {
      return usuariosDao.findByLogin(login)
                        .get( );
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    return null;
  }
  
  @Override
  @Transactional(readOnly = true)
  public String token( ) {
    try {
      return aux.generarToken( );
    }
    catch(IOException e) {
      e.printStackTrace( );
    }
    return null;
  }
  
  // @Override
  // @Transactional(readOnly = true)
  // public boolean loadEmpresas(Integer idempresa) {
  // List<PglDosificacionEmpresa> comboCobranzas = new ArrayList<>( );
  // ObjRequest req;
  // ObjE2C051 e2C051;
  // req = new ObjRequest( );
  // req.setFinancialEntity("9003");
  // req.setFinancialEntityAgency("OF. Central");
  // req.setFinancialEntityCity("SCZ");
  // req.setFinancialEntityOperator("administrador@beco.com.bo");
  // req.setPassword("hV92UKnUhk");
  // req.setUser("AFPgt");
  // ClientePTD360 servicios = new ClientePTD360( );
  // // Ejecuta WS 360 Metodo 50 Obtiene AGENTES
  // // ECONOMICOS Y FILTROS DE BUSQUEDA
  // e2C051 = servicios.e2C050(req);
  // Integer primerEmpresa = 0;
  // if (e2C051.getFlagError( )
  // .equals("EXITOSO")) {
  // // this.listaCriteriosXEmpresa =
  // // e2C051.getCriteriosEmpresa();
  // primerEmpresa = e2C051.getCriteriosEmpresa( )
  // .get(0)
  // .getCe( )
  // .getIdEmpresa( )
  // .getIdEmpresa( );
  // ObjEmpresa primera = e2C051.getCriteriosEmpresa( )
  // .get(0)
  // .getCe( )
  // .getIdEmpresa( );
  // String empresa = primera.getNombre( );
  // // ADICIONA PRIMERA EMPRESA EN LA LISTA
  // comboCobranzas.add(new PglDosificacionEmpresa(
  // e2C051.getCriteriosEmpresa( )
  // .get(0)
  // .getCe( )
  // .getIdEmpresa( )
  // .getIdEmpresa( ),
  // primera.getEstado( ),
  // empresa,
  // primera.getNit( ),
  // primera.getClase( ),
  // null,
  // ""));
  // // CICLO SOBRE LOS OTROS REGISTROS DE AGENTES
  // // ECONOMICOS
  // for(ObjAliasCriteriosEmpresa item: e2C051.getCriteriosEmpresa( )) {
  // // Si el ambito es M001 es para el primer metodo
  // if (item.getCe( )
  // .getAmbito( )
  // .equals("M001")) {
  // // System.out.print("-->>" +
  // // item.getCe().getIdEmpresa().getIdEmpresa() + "
  // // <<-->> ");
  // if (!primerEmpresa.equals(item.getCe( )
  // .getIdEmpresa( )
  // .getIdEmpresa( ))) {
  // String nombreEmpresa = item.getCe( )
  // .getIdEmpresa( )
  // .getNombre( );
  // if (!comboCobranzas.contains(new PglDosificacionEmpresa(
  // item.getCe( )
  // .getIdEmpresa( )
  // .getIdEmpresa( ),
  // item.getCe( )
  // .getIdEmpresa( )
  // .getEstado( ),
  // nombreEmpresa,
  // item.getCe( )
  // .getIdEmpresa( )
  // .getNit( ),
  // item.getCe( )
  // .getIdEmpresa( )
  // .getClase( ),
  // null,
  // ""))) {
  // System.out.println(item.getCe( )
  // .getIdEmpresa( )
  // .getIdEmpresa( ) + " <<-->> " + item.getCe( )
  // .getIdEmpresa( )
  // .getNombre( ) + " -->> " + item
  // .getCe( )
  // .getIdEmpresa( )
  // .getIdEmpresa( )
  // .equals(
  // idempresa));
  // comboCobranzas.add(new PglDosificacionEmpresa(
  // item.getCe( )
  // .getIdEmpresa( )
  // .getIdEmpresa( ),
  // item.getCe( )
  // .getIdEmpresa( )
  // .getEstado( ),
  // nombreEmpresa,
  // item.getCe( )
  // .getIdEmpresa( )
  // .getNit( ),
  // item.getCe( )
  // .getIdEmpresa( )
  // .getClase( ),
  // null,
  // ""));
  // primerEmpresa = item.getCe( )
  // .getIdEmpresa( )
  // .getIdEmpresa( ); // ACTUALIZA PRIMERA
  // // EMPRESA PARA
  // // COMPARACIONES
  // System.out.println(item.getCe( )
  // .getIdEmpresa( )
  // .getIdEmpresa( ) + "-->> " + nombreEmpresa);
  // if (item.getCe( )
  // .getIdEmpresa( )
  // .getIdEmpresa( )
  // .equals(idempresa)) { return true; }
  // }
  // }
  // }
  // }
  // }
  // return false;
  // }
  
  @Override
  @Transactional
  public Usuarios guardar(Usuarios u,
                          Empresas e,
                          Personas p) {
    u.setIdempresa(e);
    u.setIdpersona(p);
    return usuariosDao.save(u);
  }
  
  // @Override
  // public String findXEmpresa(Integer id) {
  // ObjRequest req;
  // req = new ObjRequest( );
  // ClientePTD360 servicios = new ClientePTD360( );
  // System.out.println("llega ClientePTD360-ippublica");
  // ObjE2C071 e2C071 = servicios.e2C070(req);
  // System.out.println("response de WS");
  // System.out.println(e2C071.getFlagError( ));
  // if (e2C071.getFlagError( )
  // .equals("EXITOSO")) {
  // System.out.println("OBTIENE LA LISTA DE EMPRESAS DEL SERVICIO");
  // List<ObjAliasCredencialesEmpresa> ace = e2C071.getAliasEmpresa( );
  // ObjAliasCredencialesEmpresa encontrado = new ObjAliasCredencialesEmpresa( );
  // for(ObjAliasCredencialesEmpresa alias: ace) {
  // ObjAliasEmpresa ae = alias.getAliasEmpresa( );
  // if (ae.getIdEmpresa( )
  // .getTipo( )
  // .equals("AE")) {
  // Integer codigo = ae.getIdEmpresa( )
  // .getIdEmpresa( )
  // .intValue( );
  // if (codigo.equals(id)) {
  // System.out.print(alias.getAliasEmpresa( )
  // .getIdEmpresa( )
  // .getIdEmpresa( ) + " - ");
  // System.out.print(ae.getIdEmpresa( )
  // .getTipo( ) + " - ");
  // System.out.print(ae.getIdEmpresa( )
  // .getUsuarioAlta( ) + " - ");
  // System.out.print(ae.getIdEmpresa( )
  // .getFechaAlta( )
  // .getTime( ) + " - ");
  // System.out.println(ae.getIdEmpresa( )
  // .getNombre( ));
  // }
  // }
  // }
  // }
  // return null;
  // }
  
  @Override
  @Transactional(readOnly = true)
  public Page<?> ListaPersonasUsuario(String estado,
                                      String buscar,
                                      Pageable p) {
    return usuariosDao.ListaPersonasUsuario(estado, buscar, p);
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<?> BuscarLogin(String buscar) { return usuariosDao.BuscarLogin(buscar); }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Usuarios> existsSession(String login,
                                          String clave) {
    PasswordEncoder passencoder = new BCryptPasswordEncoder( );
    Optional<Usuarios> u = usuariosDao.findByLogin(login);
    if (passencoder.matches(clave, u.get( )
                                    .getClave( ))) {
      return u;
    }else {
      return null;
    }
  }
  
  @Override
  @Transactional(readOnly = true)
  public String generarToken(Usuarios u) throws ParseException {
    Map<String, Object> claims = new HashMap<>( );
    claims.put("references", "0");
    String codigo = aux.generarMatricula(7) + u.getIdempresa( )
                                               .getIdempresa( ) + aux.generarMatricula(5);
    return aux.createJWT2("PGT", u.getLogin( ), codigo, claims, aux.sumarDiasAFecha(new Date( ), 1));
  }
  
  @Override
  @Transactional(readOnly = true)
  public boolean verificarToken(String token) {
    Date today;
    try {
      today = aux.decodeJWT(token)
                 .getExpiration( );
    }
    catch(Exception e) {
      return false;
    }
    Date d = new Date( );
    try {
      if (today.getTime( ) >= d.getTime( )) {
        return true;
      }else {
        return false;
      }
    }
    catch(Exception e) {
      return false;
    }
  }
  
  @Override
  @Transactional
  public Usuarios save(Usuarios u) { return usuariosDao.save(u); }
  
  @Async("asyncExecutor")
  @Override
  @Transactional
  public void envioCredenciales(String pass,
                                Usuarios usuario,
                                String[ ] correos,
                                String[ ] footer,
                                String asunto) {
    Map<String, Object> response = new HashMap<>( );
    MailRequest mailrequest = new MailRequest( );
    List<String> roles = new ArrayList<String>( );
    List<Roles> r = this.roles(Long.valueOf(usuario.getIdusuario( )));
    for(Roles role: r) {
      roles.add(role.getNombre( ));
    }
    response.put("empresa", usuario.getIdempresa( )
                                   .getRazon_social( ));
    response.put("usuario", usuario.getLogin( ));
    response.put("password", pass);
    response.put("footer0", footer[0]);
    response.put("footer1", footer[1]);
    response.put("rol", roles.toString( ));
    mailrequest.setFrom("pagos@pagatodo360.net");
    mailrequest.setSubject(asunto);
    if (!emailServ.sendEmail(correos, mailrequest, response, "credenciales.html", "")) {
      emailServ.sendEmail(correos, mailrequest, response, "credenciales.html", "");
    }
  }
  
  @Override
  public boolean existsByIdusuario(Long id) { return usuariosDao.existsById(id); }
  
  @Override
  @Transactional(readOnly = true)
  public List<Roles> roles(Long idusurio) { return rolesDao.roles(idusurio); }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Usuarios> existsNitEmpresaLoginTipo(String nit,
                                                      String login,
                                                      String tipo) {
    return usuariosDao.existsNitEmpresaLoginTipo(nit, login, tipo);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Usuarios> existsNitEmpresaLogin(String nit,
                                                  String login) {
    return usuariosDao.existsNitEmpresaLogin(nit, login);
  }
  
  @Override
  @Async("asyncExecutor")
  public void enviarTipoAleatorio(String codigo,
                                  Usuarios usuario,
                                  String[ ] correos,
                                  String[ ] footer,
                                  String asunto) {
    MailRequest mailrequest = new MailRequest( );
    Map<String, Object> response = new HashMap<>( );
    response.put("empresa", usuario.getIdempresa( )
                                   .getRazon_social( ));
    response.put("nombre", usuario.getIdpersona( )
                                  .getNombres( ) + " " + usuario.getIdpersona( )
                                                                .getApellido_paterno( ));
    response.put("clave", codigo);
    response.put("footer0", footer[0]);
    response.put("footer1", footer[1]);
    mailrequest.setFrom("pagos@pagatodo360.net");
    mailrequest.setSubject(asunto);
    if (!emailServ.sendEmail(correos, mailrequest, response, "clave_secreta.html", "")) {
      emailServ.sendEmail(correos, mailrequest, response, "clave_secreta.html", "");
    }
  }
  
  @Override
  public void cambioTipo(String nit,
                         String login,
                         String codigo) {
    usuariosDao.cambiotipo(nit, login, codigo);
  }
  
  @Override
  public void cambioPass(Long idusuario,
                         String pass) { usuariosDao.cambioPass(idusuario, pass); }
  
  @Override
  @Transactional(readOnly = true)
  public List<Usuarios> ListarUsuariosIdempresa(Long idempresa,
                                                String estado) {
    return usuariosDao.ListarUsuariosIdempresa(idempresa, estado);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Usuarios> obtenerLogin(String buscar) { return usuariosDao.findByLogin(buscar); }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Usuarios> BuscarLoginAndIdEmpresa(String login,
                                                    Long idempresa) {
    return Optional.of(usuariosDao.BuscarLoginAndIdEmpresa(login, idempresa)
                                  .orElseThrow(( ) -> new NotFoundException(
                                    "No se encuentra login :: " + login)));
  }
  
}
