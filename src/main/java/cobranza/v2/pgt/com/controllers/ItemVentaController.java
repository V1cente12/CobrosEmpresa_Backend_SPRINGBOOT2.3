
package cobranza.v2.pgt.com.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.entity.ItemVenta;
import cobranza.v2.pgt.com.models.services.ICategoriaServ;
import cobranza.v2.pgt.com.models.services.IDetalleServ;
import cobranza.v2.pgt.com.models.services.IImagenServ;
import cobranza.v2.pgt.com.models.services.IItemVentaServ;
import cobranza.v2.pgt.com.models.services.IUnidadServ;
import cobranza.v2.pgt.com.utils.Auxiliar;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2/itemventa")
public class ItemVentaController {
  
  @Autowired
  private IItemVentaServ itemventaServ;
  @Autowired
  private IImagenServ imagenServ;
  @Autowired
  private IDetalleServ detalleServ;
  @Autowired
  private IUnidadServ unidadServ;
  @Autowired
  private ICategoriaServ categoriaServ;
  @Autowired
  private Auxiliar aux;
  
  @GetMapping("/page")
  public Page<ItemVenta> index(@RequestParam(name = "estado", defaultValue = "A") String estado,
                               @RequestParam(name = "nombre", defaultValue = "") String nombre,
                               @RequestParam(name = "size", defaultValue = "10") String size,
                               @RequestParam(name = "page", defaultValue = "0") String page,
                               @RequestParam(name = "idempresa") String idempresa,
                               @RequestParam(defaultValue = "iditem,desc") String[ ] sortBy) {
    return itemventaServ.listarEstadoIdempresa(estado, Long.valueOf(idempresa), PageRequest.of(Integer
                                                                                                      .valueOf(
                                                                                                        page),
      Integer.valueOf(size), Sort.by(new Order(aux.getSortDirection(sortBy[1]), sortBy[0]))));
  }
  
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> ListarTodo(@RequestParam(name = "estado", defaultValue = "A") String estado,
                                      @RequestParam(name = "idempresa") String idempresa) {
    return ResponseEntity.status(HttpStatus.OK)
                         .body(itemventaServ.listarEstadoIdempresa(estado, Long.valueOf(idempresa)));
  }
  
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(@RequestParam("categoriaItemVenta") String categoriaItemVenta,
                                  @RequestParam("codigo") String codigo,
                                  @RequestParam("descripcion") String descripcion,
                                  @RequestParam("descuento") String descuento,
                                  @RequestParam("estado") String estado,
                                  @RequestParam("idempresa") String idempresa,
                                  @RequestParam("link") String link,
                                  @RequestParam("manejaStock") String manejaStock,
                                  @RequestParam("moneda") String moneda,
                                  @RequestParam("nombre") String nombre,
                                  @RequestParam("precio") String precio,
                                  @RequestParam("stock") String stock,
                                  @RequestParam("tipo") String tipo,
                                  @RequestParam("cuotas") String cuotas,
                                  @RequestParam("montocuotas") String montocuotas,
                                  @RequestParam("unidad") String unidad,
                                  @RequestParam("ventas") String ventas,
                                  @RequestParam("fechaVencimiento") String fechaVencimiento,
                                  @RequestParam(value = "imagen", required = false) MultipartFile imagen) throws JsonGenerationException,
                                                                                                          JsonMappingException,
                                                                                                          IOException {
    Map<String, Object> response = new HashMap<>( );
    ItemVenta iv = new ItemVenta( );
    try {
      iv.setCodigo(codigo);
      iv.setDescripcion(descripcion);
      iv.setDescuento(new BigDecimal(descuento));
      iv.setEstado(estado);
      iv.setFechaVencimiento(aux.conversionStringDate(fechaVencimiento, "dd/MM/yyyy hh:mm:ss"));
      iv.setIdempresa(Long.valueOf(idempresa));
      iv.setLink(Boolean.parseBoolean(link));
      iv.setManejaStock(Boolean.parseBoolean(manejaStock));
      iv.setMoneda(moneda);
      iv.setNombre(nombre);
      iv.setCuotas(new BigDecimal(cuotas));
      iv.setMontoCuotas(new BigDecimal(montocuotas));
      iv.setStock(new BigDecimal(stock));
      iv.setTipo(tipo);
      unidad = unidadServ.obtener(Long.valueOf(unidad))
                         .get( )
                         .getNombre( );
      iv.setUnidad(unidad);
      iv.setPrecio(new BigDecimal(precio));
      iv.setVentas(Integer.valueOf(ventas));
      iv.setCategoriaItemVenta(categoriaServ.obtener(Long.valueOf(categoriaItemVenta))
                                            .get( ));
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    String codigoAux = aux.generarMatricula(15);
    do {
      codigoAux = aux.generarMatricula(15);
    }
    while(itemventaServ.existeCodigoAux(codigoAux));
    iv.setCodigoAux(codigoAux);
    if (imagen != null) {
      String name_imagen = aux.generarMatricula(12)
                              .toUpperCase( ) + ".png";
      do {
        name_imagen = aux.generarMatricula(12)
                         .toUpperCase( ) + ".png";
      }
      while(itemventaServ.existeNombreImagen(name_imagen));
      iv.setImagen(name_imagen);
      imagenServ.subir(imagen, iv.getImagen( ));
    }else {
      iv.setImagen("CARRITO.png");
    }
    try {
      response.put("add", itemventaServ.guardar(iv));
      response.put("response", "Se creo el item \nexitosamente..!!");
      return ResponseEntity.status(HttpStatus.OK)
                           .body(response);
    }
    catch(Exception e) {
      response.put("response", "Error al crearse el item..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
  }
  
  @GetMapping(value = "/obtenerId", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> obtenerId(@RequestParam(name = "iditem") String iditem) {
    return ResponseEntity.status(HttpStatus.OK)
                         .body(itemventaServ.obtener(Long.valueOf(iditem)));
  }
  
  @PostMapping(value = "/modificarId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> modificarId(@Valid @RequestBody ItemVenta c,
                                       @RequestParam(name = "iditem") String iditem) {
    Map<String, Object> response = new HashMap<>( );
    Optional<ItemVenta> p = itemventaServ.obtener(Long.valueOf(iditem));
    c.setImagen(p.get( )
                 .getImagen( ));
    c.setCodigoAux(p.get( )
                    .getCodigoAux( ));
    try {
      response.put("update", itemventaServ.modificar(c, p.get( )));
      response.put("response", "Se modifico el item \nexitosamente..!!");
      return ResponseEntity.status(HttpStatus.OK)
                           .body(response);
    }
    catch(Exception e) {
      response.put("response", "Error al modificar el item..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
  }
  
  @DeleteMapping(value = "/eliminarId", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> eliminarId(@RequestParam(name = "iditem") String iditem,
                                      @RequestParam(name = "estado", defaultValue = "B") String estado) {
    Map<String, Object> response = new HashMap<>( );
    String valor = estado.equals("A") ? "alta" : "baja";
    Optional<ItemVenta> p = itemventaServ.obtener(Long.valueOf(iditem));
    // try {
    // if (detalleServ.BuscarCodItem(iditem)
    // .size( ) > 0) {
    // response.put("response", "El item '" + p.get( )
    // .getNombre( ) + "' se \n encuentra utilizado..!!");
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(response);
    // }
    // }catch(Exception e) {
    // response.put("response", "Error de codigo de la unidad..!!");
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(response);
    // }
    try {
      itemventaServ.eliminarId(estado, p.get( )
                                        .getIditem( ));
      response.put("delete", itemventaServ.obtener(Long.valueOf(iditem)));
      response.put("response", "Se dio de " + valor + " el item \nexitosamente..!!");
      return ResponseEntity.status(HttpStatus.OK)
                           .body(response);
    }
    catch(Exception e) {
      response.put("response", "Error al dar de " + valor + " el item..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
  }
  
  @PostMapping(value = "/modificarImagen", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> modificarImagen(@RequestParam(name = "iditem") String iditem) {
    Map<String, Object> response = new HashMap<>( );
    Optional<ItemVenta> p = itemventaServ.obtener(Long.valueOf(iditem));
    String name_imagen = aux.generarMatricula(12)
                            .toUpperCase( ) + ".png";
    do {
      name_imagen = aux.generarMatricula(12)
                       .toUpperCase( ) + ".png";
    }
    while(itemventaServ.existeNombreImagen(name_imagen));
    p.get( )
     .setImagen(name_imagen);
    try {
      // response.put("update", p.get( ));
      response.put("update", itemventaServ.guardar(p.get( )));
      response.put("response", "Se modifico la imagen del item \nexitosamente..!!");
      return ResponseEntity.status(HttpStatus.OK)
                           .body(response);
    }
    catch(Exception e) {
      response.put("response", "Error al modificar la imagen de la item..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
  }
}
