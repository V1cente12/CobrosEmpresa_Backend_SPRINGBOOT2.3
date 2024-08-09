
package cobranza.v2.pgt.com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import cobranza.v2.pgt.com.models.entity.Clientes;
import cobranza.v2.pgt.com.models.entity.Documento;
import cobranza.v2.pgt.com.models.entity.DocumentoEmpresa;
import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.entity.Personas;
import cobranza.v2.pgt.com.models.entity.RolUsuario;
import cobranza.v2.pgt.com.models.entity.Roles;
import cobranza.v2.pgt.com.models.entity.Usuarios;
import cobranza.v2.pgt.com.models.services.IClienteServ;
import cobranza.v2.pgt.com.models.services.IDocumentoServ;
import cobranza.v2.pgt.com.models.services.IEmpresaServ;
import cobranza.v2.pgt.com.models.services.IPersonasServ;
import cobranza.v2.pgt.com.models.services.IProfileEmpresaServ;
import cobranza.v2.pgt.com.models.services.IRolUsuarioServ;
import cobranza.v2.pgt.com.models.services.IRolesServ;
import cobranza.v2.pgt.com.models.services.IUsuariosServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class PersonaController {
  
  private Logger logger = LoggerFactory.getLogger(PersonaController.class);
  @Autowired
  private IPersonasServ personaServ;
  @Autowired
  private IEmpresaServ empresaServ;
  @Autowired
  private IUsuariosServ usuarioServ;
  @Autowired
  private IRolUsuarioServ rolusuarioServ;
  @Autowired
  private IRolesServ rolServ;
  @Autowired
  private IClienteServ clienteServ;
  @Autowired
  private IProfileEmpresaServ profileServ;
  @Autowired
  private IDocumentoServ documentoServ;
  @Autowired
  private BCryptPasswordEncoder contrasenaCodificada;
  @Value("#{'${server.port}'}")
  private String PORT;
  @Value("#{'${profileempresa}'}")
  private String PROFILE;
  
  @GetMapping("/personas/{estado}")
  public List<Personas> index(@PathVariable String estado,
                              @RequestParam("idempresa") Long idempresa) {
    return personaServ.listarAll(estado, idempresa);
  }
  
  @GetMapping("/personas/{estado}/{id}")
  public Personas show(@PathVariable String estado,
                       @PathVariable Long id) {
    return personaServ.listarID(estado, id);
  }
  
  @GetMapping("/personas/page/{page}")
  public Page<?> index(@PathVariable Integer page,
                       @RequestParam("estado") String estado,
                       @RequestParam("size") String size,
                       @RequestParam("nombre") String nombre,
                       @RequestParam("idempresa") String idempresa) {
    System.out.println(page + "__" + estado + "__" + size + "__" + nombre + "__" + idempresa);
    return personaServ.listarAllPage(estado, nombre.toUpperCase( ), Long.valueOf(idempresa), PageRequest.of(
      page, Integer.valueOf(size)));
  }
  
  @GetMapping("/personas/usuario/{page}")
  public Page<Personas> usuario(@PathVariable Integer page,
                                @RequestParam("estado") String estado,
                                @RequestParam("size") String size,
                                @RequestParam("nombre") String nombre) {
    return personaServ.listarPersonaNotUsuario(estado, nombre.toUpperCase( ), PageRequest.of(page, Integer
                                                                                                          .valueOf(
                                                                                                            size)));
  }
  
  @PostMapping("/personas")
  public ResponseEntity<?> create(@RequestParam("idempresa") String idempresa,
                                  @RequestBody Personas persona) {
    Map<String, Object> response = new HashMap<>( );
    Clientes cliente = new Clientes( );
    logger.info("Registro de persona...");
    Optional<Clientes> clientenueva = null;
    Optional<?> p = null;
    ObjectMapper Obj = new ObjectMapper( );
    Personas personanueva = null;
    Empresas empresa = null;
    try {
      logger.info(Obj.writeValueAsString(persona));
    }
    catch(IOException e) {
      logger.error("Error al convertir Object a Json");
      response.put("mensaje", "Error en la BBD");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    try {
      empresa = empresaServ.listarID("A", Long.valueOf(idempresa));
    }
    catch(DataAccessException e) {
      logger.error("Error al buscar empresa " + idempresa);
      response.put("mensaje", "Error al buscar empresa " + idempresa);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    try {
      p = personaServ.PersonaByClienteEmpresa(persona.getValor_documento( ), Long.valueOf(idempresa));
    }
    catch(DataAccessException e) {
      logger.error("Error al buscar buscar la persona " + persona.getValor_documento( ));
      response.put("mensaje", "Error al buscar buscar la persona " + persona.getValor_documento( ));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    if (!p.isPresent( )) {
      logger.info("Crear persona nueva");
      try {
        personanueva = personaServ.guardar(persona);
      }
      catch(DataAccessException e) {
        logger.error("Error al guardar la persona ");
        response.put("mensaje", "Error al guardar la persona ");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }
    }else {
      logger.info("Persona exitente - Actualizar persona...");
      try {
        personanueva = personaServ.valorDocumento(persona.getValor_documento( ));
      }
      catch(Exception e) {
        logger.error("Error al buscar la persona para actualizar");
        response.put("mensaje", "Error al buscar la persona para actualizar");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }
      personanueva.setNombres(persona.getNombres( ));
      personanueva.setApellido_paterno(persona.getApellido_paterno( ));
      personanueva.setApellido_materno(persona.getApellido_materno( ));
      personanueva.setCorreo(persona.getCorreo( ));
      personanueva.setDomicilio(persona.getDomicilio( ));
      try {
        personaServ.guardar(personanueva);
      }
      catch(DataAccessException e) {
        logger.error("Error al actualizar la persona ");
        response.put("mensaje", "Error al actualizar la persona ");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }
    }
    try {
      clientenueva = clienteServ.buscarClienteEmpresa(personanueva.getValor_documento( ), Long.valueOf(
        idempresa));
    }
    catch(DataAccessException e) {
      logger.error("error al insertar a la base de datos");
      response.put("mensaje", "El error al insertar a la base de datos");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    if (!clientenueva.isPresent( )) {
      cliente.setIdpersona(personanueva);
      cliente.setCodigo_cliente(personanueva.getValor_documento( ));
      cliente.setEstado("A");
      cliente.setFecha_alta(new Date( ));
      cliente.setIdempresa(empresa);
      try {
        clientenueva = Optional.of(clienteServ.save(cliente));
      }
      catch(DataAccessException e) {
        logger.error("Error al guardar cliente");
        response.put("mensaje", "Error al guardar cliente");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }
    }
    response.put("cliente", clientenueva);
    response.put("persona", personanueva);
    response.put("idpersona", personanueva.getIdpersona( ));
    logger.info("Se agrego una nueva persona ");
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @DeleteMapping("/personas/delete")
  public ResponseEntity<?> delete(@RequestParam("usuario") String usuario,
                                  @RequestParam("id") String id,
                                  @RequestParam("estado") String estado) {
    Map<String, Object> response = new HashMap<>( );
    String cambio = "Alta";
    if (estado.equals("B")) cambio = "Baja";
    try {
      personaServ.eliminar(estado, usuario, Long.valueOf(id));
      Personas persona = personaServ.listarID(estado, Long.valueOf(id));
      response.put("persona", persona);
      response.put("mensaje", "La persona " + persona.getNombres( ) + " " + persona.getApellido_paterno( )
        + " ha sido dada de " + cambio + " con exito...");
    }
    catch(DataAccessException e) {
      e.printStackTrace( );
      response.put("mensaje", "Error al eliminar de la consulta en la BDD");
      response.put("error", e.getMessage( )
                             .concat(" : ")
                             .concat(e.getMostSpecificCause( )
                                      .getMessage( )));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }
  
  @PostMapping("/personas/registro/{idtipo}")
  public ResponseEntity<?> registro(@RequestBody Usuarios usuarios,
                                    @PathVariable Long idtipo) {
    Map<String, Object> response = new HashMap<>( );
    Personas personanueva = null;
    Usuarios usuarionuevo = null;
    Empresas empresanueva = null;
    List<String> correo = new ArrayList<>( );
    List<String> correoPGT360 = new ArrayList<>( );
    // List<String> correo2 = new ArrayList<>( );
    String pass = "12345*";
    List<DocumentoEmpresa> docemp2 = new ArrayList<>( );
    List<Documento> doc = null;
    logger.info("Registrando.....");
    logger.info("Obtener Documentos.. " + idtipo);
    try {
      doc = documentoServ.findByIdTipoEmpresa(idtipo);
    }
    catch(Exception e) {
      logger.error("Extraendo doc.");
    }
    logger.info("Cant. Documentos: " + doc.size( ));
    try {
      for(int i = 0;i < doc.size( );i++) {
        DocumentoEmpresa docemp = new DocumentoEmpresa( );
        docemp.setDocumento(doc.get(i));
        docemp.setEmpresa(usuarios.getIdempresa( ));
        docemp2.add(docemp);
      }
    }
    catch(Exception e) {
      logger.error("bucle doc.");
    }
    logger.info("Registro de empresa..");
    usuarios.getIdempresa( )
            .setAtcProfileEmpresa(profileServ.obtenerId(Long.valueOf(PROFILE)));
    usuarios.getIdempresa( )
            .setLogo("PAGATODO.png");
    usuarios.getIdempresa( )
            .setDocumentoEmpresas(docemp2);
    usuarios.getIdempresa( )
            .setCorreos(usuarios.getIdpersona( )
                                .getCorreo( ));
    usuarios.setClave(contrasenaCodificada.encode(pass));
    usuarios.setListamenus("5");
    usuarios.setListarecursos("11,12,25,26");
    usuarios.setEstado("PEN");
    // usuarios.setRoles("usuario");
    try {
      empresanueva = empresaServ.guardar(usuarios.getIdempresa( ));
      response.put("empresa", empresanueva);
    }
    catch(Exception e) {
      logger.error("error empresa save");
    }
    try {
      if (!personaServ.valorDoc(usuarios.getIdpersona( )
                                        .getValor_documento( ))) {
        logger.info("Registro de persona..");
        personanueva = personaServ.guardar(usuarios.getIdpersona( ));
      }else {
        logger.info("Persona existente..");
        personanueva = personaServ.valorDocumento(usuarios.getIdpersona( )
                                                          .getValor_documento( ));
        logger.info("Persona existente.. (" + personanueva.getIdpersona( ) + ")");
        usuarios.getIdpersona( )
                .setIdpersona(personanueva.getIdpersona( ));
        personanueva = personaServ.guardar(usuarios.getIdpersona( ));
      }
    }
    catch(Exception e) {
      logger.error("error persona save");
    }
    usuarios.setLogin(usuarios.getIdpersona( )
                              .getCorreo( ));
    usuarios.setIdempresa(empresanueva);
    usuarios.setIdpersona(personanueva);
    try {
      usuarionuevo = usuarioServ.save(usuarios);
      logger.info("Registro de usuario");
    }
    catch(Exception e) {
      logger.error("error usuario save");
    }
    RolUsuario ru2 = new RolUsuario( );
    Roles r = rolServ.listarID("A", Long.valueOf(6));
    ru2.setIdrol(r);
    ru2.setIdusuario(usuarionuevo);
    ru2.setEstado("A");
    ru2.setIdusuario(usuarionuevo);
    try {
      ru2 = rolusuarioServ.guardar(ru2);
      
    }
    catch(Exception e) {
      logger.error("error rolusuario save");
    }
    correo.add(personanueva.getCorreo( ));
    // correo2.add(personanueva.getCorreo( ));
    logger.info("Enviando el registro -> correo(s): ");
    // correoPGT360.add("luis.bulacia@pagatodo360.net");
    correoPGT360.add("mariarene.lopez@pagatodo360.net");
    correoPGT360.add("alejandra.alanes@pagatodo360.net");
    // correoPGT360.add("edgar.veliz@pagatodo360.net");
    correoPGT360.add("ventas@pagatodo360.net");
    // correo2.add("edgar.veliz@pagatodo360.net");
    if (!PORT.equals("3000")) {
      String[ ] correosPGT360 = correoPGT360.toArray(new String[correoPGT360.size( )]);
      empresaServ.envioRegistro(usuarioServ.listarID("A", usuarionuevo.getIdusuario( )), correosPGT360);
    }else {
      logger.error(correoPGT360.size( ) + " correo de Admin :" + correoPGT360.toString( ));
    }
    String[ ] correos = correo.toArray(new String[correo.size( )]);
    // String[ ] correos2 = ( String[ ] ) correo2.toArray(new String[correo2.size( )]);
    empresaServ.envioRegistro(usuarioServ.listarID("A", usuarionuevo.getIdusuario( )), correos);
    // logger.info("Enviando credenciales -> correo(s): ");
    // String[ ] footer = {"De igual manera se adjunta el manual de usuario y enlaces de tutoriales.","Si tiene alguna
    // pregunta, inquietud o sugerencia, envíenos un correo electrónico:\n ventas@pagatodo360.net"};
    // usuarioServ.envioCredenciales(pass,
    // usuarioServ.listarID("A", usuarionuevo.getIdusuario( )),
    // correos2,
    // footer,
    // "Credenciales de Acceso");
    // logger.info("Registro completado....");
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }
  
  @GetMapping("/personas/buscarPerCli")
  public Optional<?> PersonaByClienteEmpresa(@RequestParam("valor") String valor,
                                             @RequestParam("idempresa") String idempresa) {
    return personaServ.PersonaByClienteEmpresa(valor, Long.valueOf(idempresa));
  }
  
  @GetMapping("/personas/credencial")
  public ResponseEntity<?> enviarCredenciales(@RequestParam("idusuario") String idusuario) {
    Map<String, Object> response = new HashMap<>( );
    String[ ] correos = {"elson619v@gmail.com"};
    empresaServ.envioRegistro(usuarioServ.listarID("A", Long.valueOf(idusuario)), correos);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
  @GetMapping("/personas/buscar/{flat}")
  public ResponseEntity<?> PersonaByDatoOpcionalAndIdempresa(@PathVariable(name = "flat") String flat,
                                                             @RequestParam("valor") String valor,
                                                             @RequestParam("idempresa") String idempresa) {
    if (flat.equals("1")) {
      // Busca la persona de una empresa con el correo electronico
      return ResponseEntity.status(HttpStatus.OK)
                           .body(personaServ.PersonaByCorreoPersonaAndIdempresa(valor, Long.valueOf(
                             idempresa))
                                            .get( ));
    }else if (flat.equals("2")) {
      // Busca la persona de una empresa con el numero de documento o valor documento
      return ResponseEntity.status(HttpStatus.OK)
                           .body(personaServ.PersonaByValordocumentoPersonaAndIdempresa(valor, Long.valueOf(
                             idempresa))
                                            .get( ));
    }
    return null;
  }
}
