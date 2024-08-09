
package cobranza.v2.pgt.com.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.entity.Unidad;
import cobranza.v2.pgt.com.models.services.IItemVentaServ;
import cobranza.v2.pgt.com.models.services.IUnidadServ;
import cobranza.v2.pgt.com.utils.Auxiliar;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2/unidad")
public class UnidadController {
  
  @Autowired
  private IUnidadServ unidadServ;
  @Autowired
  private IItemVentaServ itemServ;
  @Autowired
  private Auxiliar aux;
  
  @GetMapping("/page")
  public Page<Unidad> index(@RequestParam(name = "estado", defaultValue = "A") String estado,
                            @RequestParam(name = "nombre", defaultValue = "") String nombre,
                            @RequestParam(name = "size", defaultValue = "10") String size,
                            @RequestParam(name = "page", defaultValue = "0") String page,
                            @RequestParam(name = "idempresa") String idempresa,
                            @RequestParam(defaultValue = "idunidad,desc") String[ ] sortBy) {
    return unidadServ.PaginaEstadoIdempresa(estado, Long.valueOf(idempresa), PageRequest.of(Integer.valueOf(
      page), Integer.valueOf(size), Sort.by(new Order(aux.getSortDirection(sortBy[1]), sortBy[0]))));
  }
  
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> ListarEstadoIdempresa(@RequestParam(name = "estado", defaultValue = "A") String estado,
                                                 @RequestParam(name = "idempresa") String idempresa) {
    return ResponseEntity.status(HttpStatus.OK)
                         .body(unidadServ.BuscarEstadoIdempresa(estado, Long.valueOf(idempresa)));
  }
  
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(@Valid @RequestBody Unidad u) {
    Map<String, Object> response = new HashMap<>( );
    try {
      if (unidadServ.BuscarCodigoIdempresa(u.getCodigo( ), u.getIdempresa( ), "A")
                    .size( ) > 0) {
        response.put("response", "El codigo '" + u.getCodigo( ) + "' ya se \n encuentra registrado..!!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(response);
      }
    }catch(Exception e) {
      response.put("response", "Error de codigo de la unidad..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
    try {
      response.put("add", unidadServ.guardar(u));
      response.put("response", "Se creo la unidad \nexitosamente..!!");
      return ResponseEntity.status(HttpStatus.OK)
                           .body(response);
    }catch(Exception e) {
      response.put("response", "Error al crearse la unidad..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
  }
  
  @GetMapping(value = "/obtenerId", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> obtenerId(@RequestParam(name = "idunidad") String idunidad) {
    return ResponseEntity.status(HttpStatus.OK)
                         .body(unidadServ.obtener(Long.valueOf(idunidad)));
  }
  
  @PostMapping(value = "/modificarId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> modificarId(@Valid @RequestBody Unidad c,
                                       @RequestParam(name = "idunidad") String idunidad) {
    Map<String, Object> response = new HashMap<>( );
    // try {
    // if (unidadServ.BuscarCodigoIdempresa(c.getCodigo( ), c.getIdempresa( ), "A")
    // .size( ) > 0) {
    // response.put("response", "El codigo '" + c.getCodigo( ) + "' ya se \n encuentra registrado..!!");
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(response);
    // }
    // }catch(Exception e) {
    // response.put("response", "Error de codigo de la unidad..!!");
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(response);
    // }
    Optional<Unidad> p = unidadServ.obtener(Long.valueOf(idunidad));
    try {
      response.put("update", unidadServ.modificar(c, p.get( )));
      response.put("response", "Se modifico la unidad \nexitosamente..!!");
      return ResponseEntity.status(HttpStatus.OK)
                           .body(response);
    }catch(Exception e) {
      response.put("response", "Error al modificar la unidad..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
  }
  
  @DeleteMapping(value = "/eliminarId", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> eliminarId(@RequestParam(name = "idunidad") String idunidad,
                                      @RequestParam(name = "estado", defaultValue = "B") String estado) {
    Map<String, Object> response = new HashMap<>( );
    String valor = estado.equals("A") ? "alta" : "baja";
    Optional<Unidad> p = unidadServ.obtener(Long.valueOf(idunidad));
    try {
      if (itemServ.BuscarUnidadIdempresa(p.get( )
                                          .getCodigo( ), p.get( )
                                                          .getIdempresa( ), "A")
                  .size( ) > 0) {
        response.put("response", "El codigo '" + p.get( )
                                                  .getCodigo( ) + "' se \n encuentra utilizado..!!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(response);
      }
    }catch(Exception e) {
      response.put("response", "Error de codigo de la unidad..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
    try {
      unidadServ.eliminarId(estado, p.get( )
                                     .getIdunidad( ));
      response.put("delete", unidadServ.obtener(Long.valueOf(idunidad)));
      response.put("response", "Se dio de " + valor + " la unidad \nexitosamente..!!");
      return ResponseEntity.status(HttpStatus.OK)
                           .body(response);
    }catch(Exception e) {
      response.put("response", "Error al dar de " + valor + " la unidad..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
  }
}
