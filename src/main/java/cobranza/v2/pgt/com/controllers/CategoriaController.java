
package cobranza.v2.pgt.com.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.entity.CategoriaItemVenta;
import cobranza.v2.pgt.com.models.services.EmailService;
import cobranza.v2.pgt.com.models.services.ICategoriaServ;
import cobranza.v2.pgt.com.models.services.IImagenServ;
import cobranza.v2.pgt.com.utils.Auxiliar;
import cobranza.v2.pgt.com.utils.otros.MailRequest;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2/categoria")
public class CategoriaController {
  
  @Autowired
  private ICategoriaServ categoriaServ;
  @Autowired
  private Auxiliar aux;
  @Autowired
  private IImagenServ imagenServ;
  @Autowired
  private EmailService emailServ;
  
  @GetMapping("/page")
  public ResponseEntity<?> index(@RequestParam(name = "estado", defaultValue = "A") String estado,
                                 @RequestParam(name = "nombre", defaultValue = "") String nombre,
                                 @RequestParam(name = "size", defaultValue = "10") String size,
                                 @RequestParam(name = "page", defaultValue = "0") String page,
                                 @RequestParam(name = "idempresa") String idempresa) {
    return ResponseEntity.status(HttpStatus.OK)
                         .body(categoriaServ.listarEstadoIdempresa(estado, Integer.valueOf(idempresa),
                           PageRequest.of(Integer.valueOf(page), Integer.valueOf(size))));
  }
  
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> ListarEstadoIdempresa(@RequestParam(name = "estado", defaultValue = "A") String estado,
                                                 @RequestParam(name = "idempresa") String idempresa) {
    return ResponseEntity.status(HttpStatus.OK)
                         .body(categoriaServ.listarEstadoIdempresa(estado, Integer.valueOf(idempresa)));
  }
  
  @GetMapping(value = "/padre", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> ListarEstadoIdempresaPadre(@RequestParam(name = "estado", defaultValue = "A") String estado,
                                                      @RequestParam(name = "idempresa") String idempresa) {
    return ResponseEntity.status(HttpStatus.OK)
                         .body(categoriaServ.listarEstadoIdempresaPadre(estado, Long.valueOf(idempresa)));
  }
  
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(@RequestParam("estado") String estado,
                                  @RequestParam("idempresa") String idempresa,
                                  @RequestParam("nombre") String nombre,
                                  @RequestParam("idpadre") String idpadre,
                                  @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
    Map<String, Object> response = new HashMap<>( );
    CategoriaItemVenta c = new CategoriaItemVenta( );
    c.setIdempresa(Long.valueOf(idempresa));
    c.setEstado(estado);
    c.setNombre(nombre);
    if (!idpadre.equals("0")) {
      Optional<CategoriaItemVenta> p = categoriaServ.obtener(Long.valueOf(idpadre));
      c.setCategoriaItemVenta(p.get( ));
    }
    if (imagen != null) {
      String name_imagen = aux.generarMatricula(12)
                              .toUpperCase( ) + ".png";
      do {
        name_imagen = aux.generarMatricula(12)
                         .toUpperCase( ) + ".png";
      }while(categoriaServ.existeNombreImagen(name_imagen));
      c.setImagen(name_imagen);
      imagenServ.subir(imagen, c.getImagen( ));
    }else {
      c.setImagen("CARRITO.png");
    }
    try {
      response.put("add", categoriaServ.guardar(c));
      response.put("response", "Se creo la categoria \nexitosamente..!!");
      return ResponseEntity.status(HttpStatus.OK)
                           .body(response);
    }catch(Exception e) {
      response.put("response", "Error al crearse la categoria..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
  }
  
  @GetMapping(value = "/obtenerId", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> obtenerId(@RequestParam(name = "idcategoria") String idcategoria) {
    return ResponseEntity.status(HttpStatus.OK)
                         .body(categoriaServ.obtener(Long.valueOf(idcategoria)));
  }
  
  @PostMapping(value = "/modificarId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> modificarId(@Valid @RequestBody CategoriaItemVenta c,
                                       @RequestParam(name = "idcategoria") String idcategoria,
                                       @RequestParam(name = "idpadre") String idpadre) {
    Map<String, Object> response = new HashMap<>( );
    Optional<CategoriaItemVenta> p = categoriaServ.obtener(Long.valueOf(idcategoria));
    if (!idpadre.equals("0")) {
      Optional<CategoriaItemVenta> p2 = categoriaServ.obtener(Long.valueOf(idpadre));
      c.setCategoriaItemVenta(p2.get( ));
    }
    c.setImagen(p.get( )
                 .getImagen( ));
    try {
      
      response.put("update", categoriaServ.modificar(c, p.get( )));
      response.put("response", "Se modifico la categoria \nexitosamente..!!");
      return ResponseEntity.status(HttpStatus.OK)
                           .body(response);
    }catch(Exception e) {
      response.put("response", "Error al modificar la categoria..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
  }
  
  @PostMapping(value = "/modificarImagen", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> modificarImagen(@RequestParam(name = "idcategoria") String idcategoria,
                                           @RequestParam(name = "idpadre") String idpadre) {
    Map<String, Object> response = new HashMap<>( );
    Optional<CategoriaItemVenta> p = categoriaServ.obtener(Long.valueOf(idcategoria));
    String name_imagen = aux.generarMatricula(12)
                            .toUpperCase( ) + ".png";
    do {
      name_imagen = aux.generarMatricula(12)
                       .toUpperCase( ) + ".png";
    }while(categoriaServ.existeNombreImagen(name_imagen));
    p.get( )
     .setImagen(name_imagen);
    if (!idpadre.equals("0")) {
      Optional<CategoriaItemVenta> p2 = categoriaServ.obtener(Long.valueOf(idpadre));
      p.get( )
       .setCategoriaItemVenta(p2.get( ));
    }
    try {
      // response.put("update", p.get( ));
      response.put("update", categoriaServ.guardar(p.get( )));
      response.put("response", "Se modifico la imagen de la categoria \nexitosamente..!!");
      return ResponseEntity.status(HttpStatus.OK)
                           .body(response);
    }catch(Exception e) {
      response.put("response", "Error al modificar la imagen de la categoria..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
  }
  
  @DeleteMapping(value = "/eliminarId", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> eliminarId(@RequestParam(name = "idcategoria") String idcategoria,
                                      @RequestParam(name = "estado", defaultValue = "B") String estado) {
    Map<String, Object> response = new HashMap<>( );
    String valor = estado.equals("A") ? "alta" : "baja";
    Optional<CategoriaItemVenta> p = categoriaServ.obtener(Long.valueOf(idcategoria));
    try {
      /**
       * Categoria
       */
      CategoriaItemVenta CI = p.get( );
      if (CI.getCategoriaItemVentas( )
            .size( ) > 0) {
        for(int i = 0;i < CI.getCategoriaItemVentas( )
                            .size( );i++) {
          if (this.items(CI.getCategoriaItemVentas( )
                           .get(i))) {
            response.put("response", "La categoria '" + CI.getNombre( ) + "' se \n encuentra utilizado..!!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(response);
          }
        }
      }
      /**
       * Subcategoria
       */
      else if (this.items(CI)) {
        response.put("response", "La subcategoria '" + CI.getNombre( ) + "' se \n encuentra utilizado..!!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(response);
      }
    }catch(Exception e) {
      response.put("response", "Error de codigo de la unidad..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
    try {
      categoriaServ.eliminarId(estado, p.get( )
                                        .getIdcategoria( ));
      response.put("delete", categoriaServ.obtener(Long.valueOf(idcategoria)));
      response.put("response", "Se dio de " + valor + " la categoria \nexitosamente..!!");
      return ResponseEntity.status(HttpStatus.OK)
                           .body(response);
    }catch(Exception e) {
      response.put("response", "Error al dar de " + valor + " la categoria..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
  }
  
  @GetMapping(value = "/obtenerImagen", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> obtenerImagen(@RequestParam(name = "nombre") String nombre) {
    return ResponseEntity.status(HttpStatus.OK)
                         .body(categoriaServ.existeNombreImagen(nombre));
  }
  
  /**
   * Busca los items de una categoria
   */
  public boolean items(CategoriaItemVenta c) {
    if (c.getItemVentas( )
         .size( ) > 0) { return true; }
    return false;
  }
  
  @GetMapping(value = "/prueba", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> prueba(@RequestParam(name = "nombre") String nombre) {
    Map<String, Object> response = new HashMap<>( );
    String[ ] address = {"elson619v@gmail.com"};
    MailRequest mailrequest = new MailRequest( );
    mailrequest.setSubject("Pago de deuda");
    mailrequest.setTo("elson.vicente@pagatodo360.net");
    mailrequest.setFrom("pagos@pagatodo360.net");
    // emailServ.sendEmail(address, mailrequest, response, nombre, "");
    emailServ.sendSimpleMessage(mailrequest);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
}
