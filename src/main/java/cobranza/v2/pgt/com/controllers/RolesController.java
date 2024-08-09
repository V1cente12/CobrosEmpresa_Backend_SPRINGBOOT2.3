
package cobranza.v2.pgt.com.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.entity.Roles;
import cobranza.v2.pgt.com.models.services.IRolesServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class RolesController {
  
  @Autowired
  private IRolesServ rolesempresaServ;
  
  @GetMapping("/roles/{estado}")
  public List<Roles> index(@PathVariable String estado) { return rolesempresaServ.listarAll(estado); }
  
  @GetMapping("/roles/{estado}/{id}")
  public Roles show(@PathVariable String estado,
                    @PathVariable Long id) {
    return rolesempresaServ.listarID(estado, id);
  }
  
  @GetMapping("/roles/page/{page}")
  public Page<Roles> index(@PathVariable Integer page,
                           @RequestParam("estado") String estado,
                           @RequestParam("size") String size,
                           @RequestParam("nombre") String nombre,
                           @RequestParam("idempresa") String idempresa) {
    return rolesempresaServ.listarAllPage(estado, nombre.toUpperCase( ), Long.valueOf(idempresa), PageRequest.of(page, Integer.valueOf(size)));
  }
  
  @PostMapping("/roles")
  public ResponseEntity<?> create(@RequestBody Roles rol) {
    Map<String, Object> response = new HashMap<>( );
    try {
      Roles rolnueva = rolesempresaServ.guardar(rol);
      response.put("mensaje", "El rol " + rolnueva.getNombre( ) + " ha sido creado con exito...");
      response.put("rol", rolnueva);
      response.put("idrol", rolnueva.getIdrol( ));
    }catch(Exception e) {
      e.printStackTrace( );
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @DeleteMapping("/roles/delete")
  public ResponseEntity<?> delete(@RequestParam("usuario") String usuario,
                                  @RequestParam("id") String id,
                                  @RequestParam("estado") String estado) {
    Map<String, Object> response = new HashMap<>( );
    String cambio = "Alta";
    if (estado.equals("B")) cambio = "Baja";
    try {
      rolesempresaServ.eliminar(estado, usuario, Long.valueOf(id));
      Roles rol = rolesempresaServ.listarID(estado, Long.valueOf(id));
      response.put("rol", rol);
      response.put("mensaje", "El rol " + rol.getNombre( ) + " ha sido dada de " + cambio + " con exito...");
    }catch(DataAccessException e) {
      e.printStackTrace( );
      response.put("mensaje", "Error al eliminar de la consulta en la BDD");
      response.put("error", e.getMessage( ).concat(" : ").concat(e.getMostSpecificCause( ).getMessage( )));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }
  
  // @PostMapping("/sendEmail")
  // public void sendEmail(@RequestBody MailRequest request) {
  // Map<String, Object> model = new HashMap<>();
  // model.put("Name",request.getName());
  // model.put("location","Bangalore,India");
  // service.sendEmail(request,model,"logo.png","email-template.ftl");
  //
  // }
}
