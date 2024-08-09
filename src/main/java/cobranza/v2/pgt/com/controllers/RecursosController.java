
package cobranza.v2.pgt.com.controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.entity.Recursos;
import cobranza.v2.pgt.com.models.services.IRecursosServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class RecursosController {
  
  @Autowired
  private IRecursosServ recursosServ;
  
  @GetMapping("/recursos/{estado}")
  public List<Recursos> index(@PathVariable String estado) { return recursosServ.listarAll(estado); }
  
  @GetMapping("/recursos/{estado}/{id}")
  public Recursos show(@PathVariable String estado,
                       @PathVariable Long id) {
    return recursosServ.listarID(estado, id);
  }
  
  @GetMapping("/recursos/page/{page}")
  public Page<Recursos> index(@PathVariable Integer page,
                              @RequestParam("estado") String estado,
                              @RequestParam("size") String size,
                              @RequestParam("nombre") String nombre) {
    return recursosServ.listarAllPage(estado, nombre.toUpperCase( ), PageRequest.of(page, Integer.valueOf(size)));
  }
  
  @PostMapping("/recursos")
  public ResponseEntity<?> create(@RequestBody Recursos recursos,
                                  BindingResult result) {
    Map<String, Object> response = new HashMap<>( );
    try {
      Recursos recursosnueva = recursosServ.guardar(recursos);
      response.put("mensaje", "El recurso " + recursosnueva.getNombre( ) + " ha sido creado con exito...");
      response.put("recursos", recursosnueva);
      response.put("idrecursos", recursosnueva.getIdrecurso( ));
    }catch(Exception e) {
      e.printStackTrace( );
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @DeleteMapping("/recursos/delete")
  public ResponseEntity<?> delete(@RequestParam("usuario") String usuario,
                                  @RequestParam("id") String id,
                                  @RequestParam("estado") String estado) {
    Map<String, Object> response = new HashMap<>( );
    String cambio = "Alta";
    if (estado.equals("B")) cambio = "Baja";
    try {
      recursosServ.eliminar(estado, usuario, Long.valueOf(id));
      Recursos recursos = recursosServ.listarID(estado, Long.valueOf(id));
      response.put("recursos", recursos);
      response.put("mensaje", "El recursos " + recursos.getNombre( ) + " ha sido dada de " + cambio
        + " con exito...");
    }catch(DataAccessException e) {
      e.printStackTrace( );
      response.put("mensaje", "Error al eliminar de la consulta en la BDD");
      response.put("error", e.getMessage( ).concat(" : ").concat(e.getMostSpecificCause( ).getMessage( )));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }
  
  @GetMapping("/procesos/{estado}")
  public List<Recursos> index2(@PathVariable String estado) { return recursosServ.listarProcesosAll(estado); }
  
  @GetMapping("/procesos/{estado}/{id}")
  public Recursos show2(@PathVariable String estado,
                        @PathVariable Long id) {
    return recursosServ.listarID(estado, id);
  }
  
  @GetMapping("/procesos/page/{page}")
  public Page<Recursos> index2(@PathVariable Integer page,
                               @RequestParam("estado") String estado,
                               @RequestParam("size") String size,
                               @RequestParam("nombre") String nombre) {
    return recursosServ.listarAllPage2(estado, nombre.toUpperCase( ), PageRequest.of(page, Integer.valueOf(size)));
  }
  
  @PostMapping("/procesos")
  public ResponseEntity<?> create2(@RequestBody Recursos recursos,
                                   BindingResult result) {
    Map<String, Object> response = new HashMap<>( );
    try {
      recursos.setFecha_alta(new Date( ));
      Recursos procesosnueva = recursosServ.guardar(recursos);
      response.put("mensaje", "El procesos " + procesosnueva.getNombre( ) + " ha sido creado con exito...");
      response.put("procesos", procesosnueva);
      response.put("idprocesos", procesosnueva.getIdrecurso( ));
    }catch(Exception e) {
      e.printStackTrace( );
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @DeleteMapping("/procesos/delete")
  public ResponseEntity<?> delete2(@RequestParam("usuario") String usuario,
                                   @RequestParam("id") String id,
                                   @RequestParam("estado") String estado) {
    Map<String, Object> response = new HashMap<>( );
    String cambio = "Alta";
    if (estado.equals("B")) cambio = "Baja";
    try {
      recursosServ.eliminar(estado, usuario, Long.valueOf(id));
      Recursos menus = recursosServ.listarID(estado, Long.valueOf(id));
      response.put("procesos", menus);
      response.put("mensaje", "El procesos " + menus.getNombre( ) + " ha sido dada de " + cambio
        + " con exito...");
    }catch(DataAccessException e) {
      e.printStackTrace( );
      response.put("mensaje", "Error al eliminar de la consulta en la BDD");
      response.put("error", e.getMessage( ).concat(" : ").concat(e.getMostSpecificCause( ).getMessage( )));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }
  
  @GetMapping("/recursos/menus")
  public List<Recursos> monto(@RequestParam("idrecurso") String idrecurso) {
    if (idrecurso.length( ) != 0) {
      List<Long> list =
                      Arrays.asList(idrecurso.split(",")).stream( ).map(s -> Long.parseLong(s.trim( ))).collect(Collectors.toList( ));
      return recursosServ.getRecursosListID(list);
    }
    return null;
  }
}
