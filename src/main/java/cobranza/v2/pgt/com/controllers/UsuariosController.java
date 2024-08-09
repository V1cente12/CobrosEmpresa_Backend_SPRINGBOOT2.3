
package cobranza.v2.pgt.com.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.entity.Parametrica;
import cobranza.v2.pgt.com.models.entity.Personas;
import cobranza.v2.pgt.com.models.entity.RolUsuario;
import cobranza.v2.pgt.com.models.entity.Roles;
import cobranza.v2.pgt.com.models.entity.Usuarios;
import cobranza.v2.pgt.com.models.services.IEmpresaServ;
import cobranza.v2.pgt.com.models.services.IParametricaServ;
import cobranza.v2.pgt.com.models.services.IPersonasServ;
import cobranza.v2.pgt.com.models.services.IRolUsuarioServ;
import cobranza.v2.pgt.com.models.services.IRolesServ;
import cobranza.v2.pgt.com.models.services.IUsuariosServ;
import cobranza.v2.pgt.com.utils.Auxiliar;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class UsuariosController {
  
  private static final Logger logger = LoggerFactory.getLogger(UsuariosController.class);
  
  @Autowired
  private IUsuariosServ usuariosempresaServ;
  @Autowired
  private IPersonasServ personaServ;
  @Autowired
  private IEmpresaServ empresaServ;
  @Autowired
  private IRolUsuarioServ rolusuarioServ;
  @Autowired
  private IRolesServ rolServ;
  @Autowired
  private IParametricaServ paraServ;;
  @Autowired
  private BCryptPasswordEncoder contrasenaCodificada;
  @Autowired
  private Auxiliar aux;
  @Value("#{'${server.port}'}")
  private String PORT;
  
  @GetMapping("/usuario/{estado}")
  public List<Usuarios> index(@PathVariable String estado) { return usuariosempresaServ.listarAll(estado); }
  
  @GetMapping("/usuario/unico")
  public Usuarios show(@RequestParam("estado") String estado,
                       @RequestParam("id") String id) {
    return usuariosempresaServ.listarID(estado, Long.valueOf(id));
  }
  
  // @GetMapping("/usuario/comparar/{id}")
  // public boolean comparar(@PathVariable Long id) {
  // System.out.println("llega comparar a la empresa");
  // usuariosempresaServ.findXEmpresa(1012);
  // System.out.println("sale servicio");
  // return usuariosempresaServ.loadEmpresas(id.intValue( ));
  // }
  
  @PostMapping("/usuario/crear")
  public ResponseEntity<?> create(@RequestBody Usuarios usuario,
                                  @RequestParam("idempresa") String idempresa,
                                  @RequestParam("usuario") String dato,
                                  BindingResult result) {
    Personas personanueva = null;
    Usuarios usuarionuevo = null;
    Empresas empresanueva = empresaServ.listarID("A", Long.valueOf(idempresa));
    usuario.setClave(contrasenaCodificada.encode("12345*"));
    usuario.setLogin(usuario.getIdpersona( )
                            .getCorreo( ));
    usuario.setListamenus(usuario.getListamenus( ));
    usuario.setListarecursos(usuario.getListarecursos( ));
    usuario.setEstado("A");
    usuario.setUsuario_alta(dato);
    Map<String, Object> response = new HashMap<>( );
    List<?> user = usuariosempresaServ.BuscarLogin(usuario.getIdpersona( )
                                                          .getCorreo( ));
    if (!user.isEmpty( )) {
      response.put("mensaje", "El correo ya ha sido registrado :: " + usuario.getIdpersona( )
                                                                             .getCorreo( ));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    try {
      if (!personaServ.valorDoc(usuario.getIdpersona( )
                                       .getValor_documento( ))) {
        logger.info("Registro de persona..");
        personanueva = personaServ.guardar(usuario.getIdpersona( ));
      }else {
        logger.info("Persona existente..");
        personanueva = personaServ.valorDocumento(usuario.getIdpersona( )
                                                         .getValor_documento( ));
        logger.info("Persona existente.. (" + personanueva.getIdpersona( ) + ")");
        usuario.getIdpersona( )
               .setIdpersona(personanueva.getIdpersona( ));
      }
    }
    catch(Exception e) {
      logger.error("error persona save");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    usuario.setIdempresa(empresanueva);
    usuario.setIdpersona(personanueva);
    try {
      usuarionuevo = usuariosempresaServ.save(usuario);
      logger.info("Registro de usuario");
    }
    catch(Exception e) {
      logger.error("error usuario save");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    List<String> myList = new ArrayList<String>(
      Arrays.asList(usuario.getListarecursos( )
                           .split(",")));
    this.AddlinkProducto(myList, usuario.getIdempresa( )
                                        .getIdempresa( )
                                        .toString( ), usuario.getUsuario_alta( ));
    RolUsuario ru2 = new RolUsuario( );
    Roles r = rolServ.listarID("A", Long.valueOf(6));
    ru2.setIdrol(r);
    ru2.setIdusuario(usuarionuevo);
    ru2.setEstado("A");
    ru2.setUsuario_alta(dato);
    try {
      ru2 = rolusuarioServ.guardar(ru2);
    }
    catch(Exception e) {
      logger.error("error rolusuario save");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    response.put("usuario", usuarionuevo);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @GetMapping("/usuario/persona")
  public Page<?> listapersonaUsuario(@RequestParam("estado") String estado,
                                     @RequestParam("buscar") String buscar,
                                     Pageable page) {
    System.out.println(estado);
    return usuariosempresaServ.ListaPersonasUsuario(estado, buscar, page);
  }
  
  @GetMapping("/usuario/rol")
  public ResponseEntity<?> rol(@RequestParam("idusuario") String idusuario) {
    Map<String, Object> response = new HashMap<>( );
    try {
      List<Roles> r = usuariosempresaServ.roles(Long.valueOf(idusuario));
      for(Roles role: r) {
        System.out.println(role.getNombre( ));
      }
      response.put("alta", r);
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @GetMapping("/usuario/enviarTipo")
  public ResponseEntity<?> enviarTipo(@RequestParam("nit") String nit,
                                      @RequestParam("login") String login) {
    List<String> correo = new ArrayList<>( );
    Map<String, Object> response = new HashMap<>( );
    Optional<Usuarios> usuario = usuariosempresaServ.existsNitEmpresaLogin(nit, login);
    if (!usuario.isPresent( )) {
      response.put("mensaje", "Datos invalido");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    correo.add(usuario.get( )
                      .getIdpersona( )
                      .getCorreo( ));
    // if (!PORT.equals("3000")) {
    // correo.add("alejandra.alanes@pagatodo360.net");
    // correo.add("edgar.veliz@pagatodo360.net");
    // correo.add("elson.vicente@pagatodo360.net");
    // }
    String codigo = aux.generarMatricula(6);
    String asunto = "Clave Secreta";
    String[ ] footer = {"",""};
    String[ ] correos = correo.toArray(new String[correo.size( )]);
    try {
      usuariosempresaServ.cambioTipo(nit, login, codigo);
      usuariosempresaServ.enviarTipoAleatorio(codigo, usuario.get( ), correos, footer, asunto);;
    }
    catch(Exception e) {
      response.put("mensaje", "Error en el cambio de clave");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }
    response.put("mensaje", "Enviamos un codigo a su correo:" + usuario.get( )
                                                                       .getIdpersona( )
                                                                       .getCorreo( ) + "\nGracias..");
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @GetMapping("/usuario/cambiarClave")
  public ResponseEntity<?> cambiarClave(@RequestParam("nit") String nit,
                                        @RequestParam("login") String login,
                                        @RequestParam("codigo") String codigo,
                                        @RequestParam("clave") String clave) {
    Map<String, Object> response = new HashMap<>( );
    Optional<Usuarios> usuario = usuariosempresaServ.existsNitEmpresaLoginTipo(nit, login, codigo);
    if (!usuario.isPresent( )) {
      response.put("mensaje", "Los datos ingresados no son validos");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    try {
      usuariosempresaServ.cambioPass(usuario.get( )
                                            .getIdusuario( ), contrasenaCodificada.encode(clave));
    }
    catch(Exception e) {
      response.put("mensaje", "Error en el cambio de clave");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }
    String[ ] correos = {usuario.get( )
                                .getIdpersona( )
                                .getCorreo( )};
    try {
      String[ ] footer = {"",""};
      usuariosempresaServ.envioCredenciales(clave, usuario.get( ), correos, footer,
        "Confirmación de cambio de contraseña portal PagaTodo 360");
    }
    catch(Exception e) {
      response.put("mensaje", "No se pudo enviar credenciales al correo");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    response.put("mensaje", "Se reseteo su clave de acceso al portal,\nGracias..");
    response.put("empresa", usuario.get( )
                                   .getIdempresa( ));
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @PostMapping("/usuario/update/menus")
  public ResponseEntity<?> cambiarmenus(@RequestParam("idusuario") String idusuario,
                                        @RequestParam("listamenus") String listamenus,
                                        @RequestParam("listarecursos") String listarecursos,
                                        @RequestParam("usuario") String name) {
    Map<String, Object> response = new HashMap<>( );
    // if (idusuario.isBlank( )) {
    // response.put("error", "Idusuario es vacio");
    // return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    // }
    // if (listamenus.isBlank( )) {
    // response.put("error", "Menus es vacio");
    // return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    // }
    // if (listarecursos.isBlank( )) {
    // response.put("error", "Recurso es vacio");
    // return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    // }
    Usuarios usuario = usuariosempresaServ.listarID("A", Long.valueOf(idusuario));
    usuario.setListamenus(listamenus);
    usuario.setListarecursos(listarecursos);
    usuario.setUsuario_baja(name);
    try {
      usuario = usuariosempresaServ.save(usuario);
      logger.info("Actualizacion de usuario");
    }
    catch(Exception e) {
      logger.error("error usuario save");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    List<String> myList = new ArrayList<String>(Arrays.asList(listarecursos.split(",")));
    this.AddlinkProducto(myList, usuario.getIdempresa( )
                                        .getIdempresa( )
                                        .toString( ), name);
    response.put("usuario", usuario);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  private void AddlinkProducto(List<String> myList,
                               String idempresa,
                               String name) {
    if (myList.contains("31")) {
      Parametrica para = new Parametrica( );
      para.setDominio("SERVICIO");
      para.setSubdominio("IDEMPRESA");
      para.setCodigo(idempresa);
      para.setDescripcion("token");
      para.setGlosa("TOKEN");
      para.setTipo("STRING");
      para.setValor(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJRT3FuOHRWNTdOR2NJUiIsInJlZmVyZW5jZXMiOiIwIiwiaXNzIjoiZ2xvcmltYXIiLCJleHAiOjE5MjQ5MjAwMDAsImlhdCI6MTYxMjM4NTYzNywianRpIjoiUEdUIn0.3SpnznZkP_Ksi59DuARRZ6Kv6xQLQfKi325buWZjhZI");
      para.setEstado("A");
      para.setUsuarioAlta(name);
      try {
        paraServ.guardar(para);
      }
      catch(Exception e) {
        logger.error("error al guardar parametrica..");
      }
    }
  }
  
  @GetMapping("/usuario/empresa")
  public ResponseEntity<?> ListarUsuario(@RequestParam("idempresa") String idempresa,
                                         @RequestParam("estado") String estado) {
    Map<String, Object> response = new HashMap<>( );
    try {
      response.put("data", usuariosempresaServ.ListarUsuariosIdempresa(Long.valueOf(idempresa), estado));
    }
    catch(Exception e) {
      e.printStackTrace( );
      response.put("error", "Error al buscar el ID");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @GetMapping("/usuario/login")
  public ResponseEntity<?> BuscarLogin(@RequestParam("login") String login) {
    Map<String, Object> response = new HashMap<>( );
    if (usuariosempresaServ.obtenerLogin(login)
                           .isPresent( )) {
      response.put("valor", "true");
    }else {
      response.put("valor", "false");
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }
  
  @GetMapping("/usuario/actualizarclave")
  public ResponseEntity<?> actualizarClave(@RequestParam("idempresa") String idempresa,
                                           @RequestParam("login") String login,
                                           @RequestParam("usuario") String usuario,
                                           @RequestParam("pass") String pass,
                                           @RequestParam("pass2") String pass2) {
    Map<String, Object> response = new HashMap<>( );
    Usuarios obtenerUsuario = usuariosempresaServ.BuscarLoginAndIdEmpresa(login, Long.valueOf(idempresa))
                                                 .get( );
    if (!pass.equals(pass2)) {
      response.put("mensaje", "No coincide las claves");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    try {
      obtenerUsuario.setFecha_baja(new Date( ));
      obtenerUsuario.setUsuario_baja(usuario);
      obtenerUsuario.setClave(contrasenaCodificada.encode(pass));
      System.out.println(obtenerUsuario.getClave( ));
      usuariosempresaServ.save(obtenerUsuario);
    }
    catch(Exception e) {
      response.put("mensaje", "Error en el cambio de clave");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }
    response.put("mensaje", "Se cambio la clave");
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
}
