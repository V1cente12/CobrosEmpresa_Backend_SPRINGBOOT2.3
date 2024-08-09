
package cobranza.v2.pgt.com.controllers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.entity.Deuda;
import cobranza.v2.pgt.com.models.entity.Pagos;
import cobranza.v2.pgt.com.models.services.IDeudaServ;
import cobranza.v2.pgt.com.models.services.IItemVentaServ;
import cobranza.v2.pgt.com.models.services.ILinkServ;
import cobranza.v2.pgt.com.models.services.IPagoServ;
import cobranza.v2.pgt.com.models.services.IReciboServ;
import cobranza.v2.pgt.com.utils.Auxiliar;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class DeudaController {
  
  @Value("#{'${name.url}'}")
  private String URL;
  
  @Value("#{'${name.port}'}")
  private String PORT;
  @Autowired
  private IDeudaServ deudaServ;
  @Autowired
  private IReciboServ reciboServ;
  @Autowired
  private IPagoServ pagoServ;
  @Autowired
  private ILinkServ linkServ;
  @Autowired
  private IItemVentaServ itemServ;
  
  @Autowired
  private Auxiliar aux;
  
  private static final Logger log = LoggerFactory.getLogger(DeudaController.class);
  
  @GetMapping("/deuda/{estado}")
  public List<Deuda> index(@PathVariable String estado) { return deudaServ.findAll(estado); }
  
  @GetMapping("/deuda/obtener/{id}")
  public ResponseEntity<?> show(@PathVariable String id) {
    Deuda deuda = deudaServ.findById(Long.valueOf(id))
                           .get( );
    return new ResponseEntity<Deuda>(deuda, HttpStatus.OK);
  }
  
  @PostMapping("/deuda")
  public ResponseEntity<?> create(@RequestBody Deuda deuda,
                                  BindingResult result) {
    Map<String, Object> response = new HashMap<>( );
    try {
      deuda.setFecha_alta(new Date( ));
      Deuda deudanueva = deudaServ.save(deuda);
      
      response.put("mensaje", "La persona ha sido creado con exito...");
      response.put("deuda", deudanueva);
      // response.put("idpersona",
      // personanueva.getIdpersona());
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @GetMapping("/deuda/obtenerpago/{id}")
  public ResponseEntity<?> pago(@PathVariable Long id) {
    Pagos pago = pagoServ.obtenerIddeuda(id);
    return new ResponseEntity<Pagos>(pago, HttpStatus.OK);
  }
  
  // @Secured({ "admin", "user" })
  // @GetMapping("/deuda/page/{page}")
  // @ResponseStatus(HttpStatus.OK)
  // public Page<Deuda> index(@PathVariable Integer
  // page, @RequestParam("estado") String estado,
  // @RequestParam("size") String size) {
  // System.out.println(estado + " " + size);
  // return deudaServ.buscarEstadoPage(estado,
  // PageRequest.of(page, Integer.valueOf(size)));
  // }
  
  @GetMapping("/deuda/page")
  public Page<Deuda> index(@RequestParam(defaultValue = "PEN", name = "estado") String estado,
                           @RequestParam(defaultValue = "", name = "nombre") String nombre,
                           @RequestParam("idempresa") String idempresa,
                           @RequestParam(defaultValue = "", name = "fechaI") String fechaI,
                           @RequestParam(defaultValue = "", name = "fechaF") String fechaF,
                           @RequestParam(defaultValue = "0", name = "page") String page,
                           @RequestParam(defaultValue = "10", name = "size") String size,
                           @RequestParam(defaultValue = "iddeuda,desc") String[ ] sortBy) throws ParseException {
    
    Date FechaI = null, FechaF = null;
    log.info(estado + "_" + idempresa + "_" + page + "_" + size + "_" + String.join(",", sortBy) + "_"
      + nombre + "_" + fechaI + "_" + fechaF);
    if (fechaI.equals("") && fechaF.equals("")) {
      FechaI = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1990");
      FechaF = aux.sumarDiasAFecha(new Date( ), 1);
    }else {
      FechaI = new SimpleDateFormat("dd/MM/yyyy").parse(fechaI);
      FechaF = aux.sumarDiasAFecha(new SimpleDateFormat("dd/MM/yyyy").parse(fechaF), 1);
    }
    // System.out.println(FechaI.toString( ) + "----------" + FechaF.toString( ));
    return deudaServ.buscarFilterBySortBy(Long.valueOf(idempresa), estado, nombre.toUpperCase( ), FechaI,
      FechaF, PageRequest.of(Integer.valueOf(page), Integer.valueOf(size), Sort.by(new Order(
        aux.getSortDirection(sortBy[1]),
        sortBy[0]))));
  }
  
  @GetMapping("/deuda/reporte/cliente")
  public byte[ ] reporteAll(HttpServletResponse response,
                            @RequestParam("estado") String estado,
                            @RequestParam("idempresa") String idempresa,
                            @RequestParam("fechaI") String fechaI,
                            @RequestParam("fechaF") String fechaF,
                            @RequestParam("nombre") String nombre) throws Exception {
    return deudaServ.reporteDeudaEstado(estado, Long.valueOf(idempresa), nombre.toUpperCase( ), fechaI,
      fechaF);
  }
  
  @GetMapping("/deuda/reporte/all")
  public byte[ ] reporteAlldueda(HttpServletResponse response,
                                 @RequestParam("estado") String estado,
                                 @RequestParam("idempresa") String idempresa,
                                 @RequestParam("fechaI") String fechaI,
                                 @RequestParam("fechaF") String fechaF,
                                 @RequestParam("nombre") String nombre) throws Exception {
    return deudaServ.reporteListaDeuda(estado, Long.valueOf(idempresa), nombre.toUpperCase( ), fechaI,
      fechaF);
  }
  
  @PostMapping("/deuda/lafuente/cobro")
  public ResponseEntity<?> cobro(@RequestParam("file") MultipartFile file,
                                 @RequestParam("usuario") String usuario) {
    Map<String, Object> response = new HashMap<>( );
    List<Deuda> r = deudaServ.saveAll(deudaServ.cobrosLaFuente(file, usuario));
    response.put("mensaje", "El archivo se subio con exito...");
    response.put("r", r);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @GetMapping("/deuda/pagecliente/{page}")
  public Page<Deuda> cliente(@PathVariable Integer page,
                             @RequestParam("estado") String estado,
                             @RequestParam("nombre") String nombre,
                             @RequestParam("size") String size) {
    Page<Deuda> p;
    p = deudaServ.cliente(estado, nombre.toUpperCase( ), PageRequest.of(page, Integer.valueOf(size)));
    return p;
  }
  
  @GetMapping("/deuda/total/{id}")
  public BigDecimal total(@PathVariable Long id,
                          @RequestParam("estado") String estado) {
    return deudaServ.totaldeuda(estado, id);
  }
  
  @PostMapping("/deuda/correo/{id}/{correo}/{idempresa}/{codigocliente}")
  public ResponseEntity<?> correo(@PathVariable String id,
                                  @PathVariable String correo,
                                  @PathVariable String codigocliente,
                                  @PathVariable String idempresa) {
    Map<String, Object> response = new HashMap<>( );
    if (deudaServ.enviarcorrreo(correo, URL + ":" + PORT + "/#/carrito?id=", id, idempresa, codigocliente)) {
      response.put("mensaje", "El archivo se subio con exito...");
    }else {
      response.put("mensaje", "No se pudo enviar al correo " + correo);
    }
    response.put("mensaje", "El archivo se subio con exito...");
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @PostMapping("/deuda/correo/{id}/{correo}/{idempresa}")
  public ResponseEntity<?> correo2(@PathVariable String id,
                                   @PathVariable String correo,
                                   @PathVariable String idempresa) {
    Map<String, Object> response = new HashMap<>( );
    if (deudaServ.enviarcorrreo(correo, URL + ":" + PORT + "/#/carrito?id=", id, idempresa, idempresa)) {
      response.put("mensaje", "El archivo se subio con exito...");
    }else {
      response.put("mensaje", "No se pudo enviar al correo " + correo);
    }
    response.put("mensaje", "El archivo se subio con exito...");
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @GetMapping("/deuda/monto")
  public BigDecimal monto(@RequestParam("iddeuda") String iddeuda) {
    BigDecimal monto = deudaServ.monto(iddeuda);
    return monto;
  }
  
  @PutMapping("/deuda/cambioestado")
  public ResponseEntity<?> CambioEstado(@RequestParam("id") String id,
                                        @RequestParam("estado") String estado,
                                        @RequestParam("user") String user) {
    Map<String, Object> response = new HashMap<>( );
    Map<String, Object> claims = new HashMap<>( );
    Deuda deudanuevo = new Deuda( );
    Optional<Deuda> deuda = deudaServ.findById(Long.valueOf(id));
    if (!deuda.isPresent( )) {
      response.put("error", "No se encontro la deuda..");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    String iditem = null;
    try {
      iditem = deuda.get( )
                    .getIdrecibo( )
                    .getIddetalle( )
                    .get(0)
                    .getCodItem( );
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    if (estado.equals("PAG-BAN")) {
      Pagos p = new Pagos( );
      p.setDescripcion("Trans-Banc");
      p.setUsuario_alta(user);
      response.put("pago", pagoServ.guardar(p));
      deuda.get( )
           .setFecha_modificacion(new Date( ));
      deuda.get( )
           .setUsuario_modificacion(user);
      deuda.get( )
           .setIdpago(p);
    }else if (estado.equals("ANU") && iditem != null && !iditem.equals("") && !iditem.equals("string")) {
      itemServ.disminuirVentas(Long.valueOf(iditem));
    }
    if (deuda.get( )
             .getEstado( )
             .equals("SVP")) {
      linkServ.GenerarLink(deuda.get( ), response);
      Calendar calendar = Calendar.getInstance( );
      calendar.set(Calendar.DAY_OF_MONTH, 1);
      calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
      deuda.get( )
           .getIdrecibo( )
           .setFecha_vencimiento(calendar.getTime( ));
    }
    deuda.get( )
         .setEstado(estado);
    deuda.get( )
         .getIdrecibo( )
         .setEstado(estado);
    deudaServ.save(deuda.get( ));
    // if (!deuda.get( )
    // .getObservacion( )
    // .equals("1")) {
    // System.out.println("==================");
    // int cuota = Integer.valueOf(deuda.get( )
    // .getObservacion( )) - 1;
    // deudanuevo.setEstado("SVP");
    // deudanuevo.setIdcliente(deuda.get( ).getIdcliente( ));
    // deudanuevo.setObservacion(String.valueOf(cuota));
    // deudanuevo.getIdrecibo( ).set
    // }
    response.put("deuda", deuda.get( ));
    response.put("mensaje", "Se genero correctamente..");
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
  @PostMapping("/deuda/correocomprobante/{id}")
  public ResponseEntity<?> correoComprobante(@PathVariable Long id) throws Exception {
    Map<String, Object> response = new HashMap<>( );
    Optional<Deuda> deuda = deudaServ.findById(id);
    if (reciboServ.SendComprobante(deuda.get( )
                                        .getIdrecibo( ))) {
      response.put("mensaje", "Se envio correo..");
    }else {
      response.put("mensaje", "No se pudo enviar al correo " + deuda.get( )
                                                                    .getIdrecibo( )
                                                                    .getGlosa2( ));
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }
  
  @GetMapping("/deuda/fechavencida")
  public ResponseEntity<?> fecha(@RequestParam(name = "fecha") String fecha,
                                 @RequestParam(name = "iddeuda") String iddeuda,
                                 @RequestParam(name = "idempresa") String idempresa) throws ParseException {
    Map<String, Object> response = new HashMap<>( );
    if (deudaServ.fechavencida(iddeuda, idempresa, fecha)) {
      response.put("mensaje", "El archivo se subio con exito...");
    }else {
      response.put("mensaje", "No se pudo enviar al correo ");
    }
    response.put("mensaje", "El archivo se subio con exito...");
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @GetMapping("/deuda/seguro")
  public Page<Deuda> SeguroVida(@RequestParam(defaultValue = "PEN", name = "estado") String estado,
                                @RequestParam(defaultValue = "", name = "nombre") String nombre,
                                @RequestParam("idempresa") String idempresa,
                                @RequestParam(defaultValue = "", name = "fechaI") String fechaI,
                                @RequestParam(defaultValue = "", name = "fechaF") String fechaF,
                                @RequestParam(defaultValue = "0", name = "page") String page,
                                @RequestParam(defaultValue = "10", name = "size") String size,
                                @RequestParam(defaultValue = "iddeuda,desc") String[ ] sortBy) throws ParseException {
    
    Date FechaI = null, FechaF = null;
    log.info(estado + "_" + idempresa + "_" + page + "_" + size + "_" + String.join(",", sortBy) + "_"
      + nombre + "_" + fechaI + "_" + fechaF);
    if (fechaI.equals("") && fechaF.equals("")) {
      FechaI = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1990");
      FechaF = aux.sumarDiasAFecha(new Date( ), 1);
    }else {
      FechaI = new SimpleDateFormat("dd/MM/yyyy").parse(fechaI);
      FechaF = aux.sumarDiasAFecha(new SimpleDateFormat("dd/MM/yyyy").parse(fechaF), 1);
    }
    // System.out.println(FechaI.toString( ) + "----------" + FechaF.toString( ));
    return deudaServ.ListaSeguroVida(Long.valueOf(idempresa), estado, nombre.toUpperCase( ), FechaI, FechaF,
      PageRequest.of(Integer.valueOf(page), Integer.valueOf(size), Sort.by(new Order(
        aux.getSortDirection(sortBy[1]),
        sortBy[0]))));
  }
}
