
package cobranza.v2.pgt.com.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cobranza.v2.pgt.com.dto.ReciboDto;
import cobranza.v2.pgt.com.mapper.ReciboMapper;
import cobranza.v2.pgt.com.models.entity.*;
import cobranza.v2.pgt.com.models.services.*;
import cobranza.v2.pgt.com.utils.ComisionEmpresa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import cobranza.v2.pgt.com.models.services.IClienteServ;
import cobranza.v2.pgt.com.models.services.IDetalleServ;
import cobranza.v2.pgt.com.models.services.IDeudaServ;
import cobranza.v2.pgt.com.models.services.IEmpresaServ;
import cobranza.v2.pgt.com.models.services.ILinkServ;
import cobranza.v2.pgt.com.models.services.IPagoServ;
import cobranza.v2.pgt.com.models.services.IPersonasServ;
import cobranza.v2.pgt.com.models.services.IReciboServ;
import cobranza.v2.pgt.com.utils.Auxiliar;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class ReciboController {
  
  private Logger logger = LoggerFactory.getLogger(ReciboController.class);
  @Value("#{'${name.url}'}")
  private String URL;
  @Value("#{'${name.port}'}")
  private String PORT;
  @Autowired
  private IReciboServ reciboServ;
  @Autowired
  private IPersonasServ personaServ;
  @Autowired
  private IClienteServ clienteServ;
  @Autowired
  private IDeudaServ deudaServ;
  @Autowired
  private IDetalleServ detalleServ;
  @Autowired
  private ILinkServ linkServ;;
  @Autowired
  private Auxiliar aux;
  @Autowired
  private IPagoServ pagoServ;
  @Autowired
  private IEmpresaServ empresaServ;
  
  private final ReciboMapper reciboMapper;
  private final ComisionEmpresa comisionEmpresa;
  private final IComisionServ comisionService;

  public ReciboController(ReciboMapper reciboMapper,
                          ComisionEmpresa comisionEmpresa,
                          IComisionServ comisionService) {
    this.reciboMapper = reciboMapper;
    this.comisionEmpresa = comisionEmpresa;
    this.comisionService = comisionService;
  }

  @GetMapping("/recibo/total")
  public List<?> total( ) { return reciboServ.totalMontoPeriodo( ); }
  
  @GetMapping("/recibo/cantidad")
  public List<?> cantidad( ) { return reciboServ.cantidadDeuda( ); }
  
  @PostMapping("/recibo")
  public ResponseEntity<?> create(@RequestBody List<Recibo> recibo,
                                  BindingResult result) {
    Map<String, Object> response = new HashMap<>( );
    System.out.println(recibo.size( ));
    try {
      List<Recibo> recibonueva = reciboServ.guardar(recibo);
      response.put("mensaje", "El recibo ha sido creado con exito...");
      response.put("nuevo", recibonueva);
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @PostMapping("/recibo/unico")
  public ResponseEntity<?> create2(@RequestBody Recibo recibo,
                                   @RequestParam("idcliente") String idcliente,
                                   @RequestParam("idempresa") String idempresa,
                                   @RequestParam("dato") String dato) throws Exception {
    Map<String, Object> response = new HashMap<>( );
    Map<String, Object> claims = new HashMap<>( );
    Deuda deuda = new Deuda( );
    Clientes cliente = null;
    Recibo recibonueva = null;
    Deuda deudanuevo = null;
    ObjectMapper Obj = new ObjectMapper( );
    try {
      logger.info(Obj.writeValueAsString(recibo));
    }
    catch(IOException e) {
      logger.error("Error al convertir Object a Json");
      response.put("mensaje", "Error en la BBD");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    if (reciboServ.ExisteNroPedido(recibo.getNro_recibo( ), Long.valueOf(idempresa))
                  .isPresent( )) {
      logger.error("Numero de pedido existente");
      response.put("mensaje", "Numero de pedido ya existente");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    try {
      cliente = clienteServ.listarID("A", Long.valueOf(idcliente));
    }
    catch(DataAccessException e) {
      logger.error("Error al buscar idcliente :" + idcliente);
      response.put("mensaje", "Error en la BBD");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    Personas pers = cliente.getIdpersona( );
    String suscriptor = pers.getNombres( ) + " " + pers.getApellido_paterno( );
    String nombreApellido = pers.getValor_documento( ) + ";" + pers.getNombres( ) + ";" + pers
                                                                                              .getApellido_paterno( )
      + ";" + pers.getDomicilio( ) + ";" + pers.getCorreo( );
    recibo.setNombre_apellido(nombreApellido);
    recibo.setSuscripcion(suscriptor);
    recibo.setGlosa1(pers.getValor_documento( ));
    try {
      recibonueva = reciboServ.guardar(recibo);
    }
    catch(DataAccessException e) {
      logger.error("Error al guardar recibo");
      response.put("mensaje", "Error en la BBD");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    try {
      List<Detalle> detalle = new ArrayList<>( );
      for(Detalle i: recibo.getIddetalle( )) {
        i.setIdrecibo(recibonueva);
        i.setEstado("PEN");
        i.setSub_total(i.getPrecio_unitario( )
                        .multiply(new BigDecimal(i.getCantidad( ))));
        detalle.add(i);
      }
      detalleServ.saveAll(detalle);
    }
    catch(DataAccessException e) {
      logger.error("Error al guardar detalle de recibo");
      response.put("mensaje", "Error en la BBD");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    deuda.setIdrecibo(recibonueva);
    deuda.setIdcliente(cliente);
    deuda.setEstado("PEN");
    try {
      deudanuevo = deudaServ.save(deuda);
    }
    catch(DataAccessException e) {
      logger.error("Error al guardar la deuda");
      response.put("mensaje", "Error en la BBD");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    try {
      personaServ.updateCorreo(recibonueva.getGlosa2( ), cliente.getIdpersona( )
                                                                .getIdpersona( ));
    }
    catch(DataAccessException e) {
      logger.error("Error al modificar el correo : " + recibonueva.getGlosa2( ) + " de " + cliente
                                                                                                  .getIdpersona( )
                                                                                                  .getNombres( )
        + " " + cliente.getIdpersona( )
                       .getApellido_paterno( ));
      response.put("mensaje", "Error en la BBD");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    String jwtId = deuda.getIdcliente( )
                        .getIdempresa( )
                        .getIdempresa( )
                        .toString( );
    String jwtIssuer = deuda.getIddeuda( )
                            .toString( );
    String jwtSubject = recibonueva.getGlosa2( );
    claims.put("references", "0");
    String jwt = aux.createJWT2(jwtId, jwtIssuer, jwtSubject, claims, recibonueva.getFecha_vencimiento( ));
    String url = URL + ":" + PORT + "/#/carrito?id=";
    Link l = new Link( );
    l.setCodigocliente(cliente.getCodigo_cliente( ));
    l.setCorreo(cliente.getIdpersona( )
                       .getCorreo( ));
    l.setEstado("A");
    l.setIddeuda(String.valueOf(deuda.getIddeuda( )));
    l.setIdcliente(deuda.getIdcliente( ));
    l.setLink(url);
    l.setToken(jwt);
    l.setMonto(deuda.getIdrecibo( )
                    .getMonto( ));
    logger.info(url + jwt);
    try {
      linkServ.guardar(l);
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    response.put("mensaje", "El recibo ha sido creado con exito...");
    response.put("link", url + jwt);
    response.put("recibo", recibonueva);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @PostMapping("/recibo/kiosco")
  public ResponseEntity<?> create3(@RequestBody Recibo recibo,
                                   @RequestParam("idempresa") String idempresa) throws Exception {
    Map<String, Object> response = new HashMap<>( );
    Deuda deuda = new Deuda( );
    Optional<Clientes> cliente = null;
    Clientes clientenuevo = new Clientes( );
    ObjectMapper Obj = new ObjectMapper( );
    Recibo recibonueva = null;
    Pagos p = new Pagos( );
    Deuda deudanuevo = null;
    try {
      logger.info(Obj.writeValueAsString(recibo));
    }
    catch(IOException e) {
      logger.error("Error al convertir Object a Json");
      response.put("mensaje", "Error en la BBD");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    
    boolean existe = reciboServ.ExisteNroPedido(recibo.getNro_recibo( ), Long.valueOf(idempresa))
                               .isPresent( );
    logger.info("-->> : " + existe);
    if (!existe) {
      try {
        cliente = clienteServ.buscarClienteEmpresa(recibo.getIddeuda( )
                                                         .getIdcliente( )
                                                         .getCodigo_cliente( ), Long.valueOf(idempresa));
      }
      catch(DataAccessException e) {
        logger.error("Error al buscar cliente 0:" + idempresa);
        response.put("mensaje", "Error en la BBD del cliente 0");
        response.put("estado", false);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }
      if (cliente.isPresent( )) {
        recibo.getIddeuda( )
              .setIdcliente(cliente.get( ));
      }else {
        try {
          Personas persona = personaServ.valorDocumento("0");
          clientenuevo.setCodigo_cliente(persona.getValor_documento( ));
          clientenuevo.setIdpersona(persona);
          clientenuevo.setEstado("A");
          clientenuevo.setIdempresa(empresaServ.listarID("A", Long.valueOf(idempresa)));
          clientenuevo = clienteServ.save(clientenuevo);
        }
        catch(Exception e) {
          logger.error("Error al crear al cliente");
          response.put("mensaje", "Error en la BBD nuevo cliente");
          response.put("estado", false);
          return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        recibo.getIddeuda( )
              .setIdcliente(clientenuevo);
      }
      recibo.setIddeuda(null);
      recibo.setFecha_modificacion(new Date( ));
      try {
        recibonueva = reciboServ.guardar(recibo);
      }
      catch(Exception e) {
        response.put("mensaje", "Error crear recibo");
        response.put("estado", false);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }
      try {
        List<Detalle> adList = new ArrayList<>( );
        for(Detalle i: recibo.getIddetalle( )) {
          i.setIdrecibo(recibonueva);
          adList.add(i);
        }
        detalleServ.saveAll(adList);
      }
      catch(DataAccessException e) {
        logger.error("Error al guardar detalle de recibo");
        response.put("mensaje", "Error en la BBD del detalle");
        response.put("estado", false);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }
      deuda.setIdrecibo(recibonueva);
      deuda.setIdcliente(cliente.get( ));
      deuda.setEstado("PAG");
      deuda.setIdpago(p);
      deuda.setFecha_modificacion(new Date( ));
      try {
        deudanuevo = deudaServ.save(deuda);
      }
      catch(DataAccessException e) {
        logger.error("Error al guardar la deuda");
        response.put("mensaje", "Error en la BBD de la deuda");
        response.put("estado", false);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }
    }else {
      recibonueva = reciboServ.FindByNroReciboIdempresa(recibo.getNro_recibo( ), Long.valueOf(idempresa));
      recibonueva.getIddeuda( )
                 .setIdpago(p);
      recibonueva.setEstado(recibo.getEstado( ));
      recibonueva.getIddeuda( )
                 .setEstado(recibo.getIddeuda( )
                                  .getEstado( ));
      recibonueva.setReference_number(recibo.getReference_number( ));
      try {
        recibonueva = reciboServ.guardar(recibonueva);
      }
      catch(Exception e) {
        logger.error("Error al guardar recibo pagado");
        response.put("mensaje", "Error al guardar recibo pagado");
        response.put("estado", false);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }
    }
    try {
      p.setDescripcion("KIOSCO");
      p = pagoServ.guardar(p);
    }
    catch(Exception e) {
      logger.error("Error al guardar pago");
      response.put("mensaje", "Error en la BBD de pago");
      response.put("estado", false);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    response.put("recibo", recibonueva);
    response.put("mensaje", "Se creo la petición \npagada con exito..!!");
    response.put("estado", true);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }
  
  @GetMapping("/recibo/kiosco/buscar")
  public Page<Recibo> buscarcodigopagoPage(@RequestParam(defaultValue = "", name = "codigopago") String codigopago,
                                           @RequestParam(defaultValue = "", name = "fechaI") String fechaI,
                                           @RequestParam(defaultValue = "", name = "fechaF") String fechaF,
                                           @RequestParam(defaultValue = "0", name = "page") String page,
                                           @RequestParam(defaultValue = "10", name = "size") String size,
                                           @RequestParam(defaultValue = "idrecibo,desc") String[ ] sortBy) throws ParseException {
    
    Date FechaI = null, FechaF = null;
    logger.info(codigopago + "_" + page + "_" + size + "_" + String.join(",", sortBy) + "_" + fechaI + "_"
      + fechaF);
    if (fechaI.equals("") && fechaF.equals("")) {
      FechaI = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1990");
      FechaF = aux.sumarDiasAFecha(new Date( ), 1);
    }else {
      FechaI = new SimpleDateFormat("dd/MM/yyyy").parse(fechaI);
      FechaF = aux.sumarDiasAFecha(new SimpleDateFormat("dd/MM/yyyy").parse(fechaF), 1);
    }
    // System.out.println(FechaI.toString( ) + "----------" + FechaF.toString( ));
    return reciboServ.buscarFilterBySortBy(codigopago.toUpperCase( ), FechaI, FechaF, PageRequest.of(Integer
                                                                                                            .valueOf(
                                                                                                              page),
      Integer.valueOf(size), Sort.by(new Order(aux.getSortDirection(sortBy[1]), sortBy[0]))));
  }
  
  @GetMapping("/recibo/{id}")
  public ResponseEntity<?> index(@PathVariable Long id) {
    Map<String, Object> response = new HashMap<>( );
    try {
      response.put("recibo", reciboServ.findID(id));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    catch(Exception e) {
      response.put("mensaje", "Error de obtener el recibo " + id);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @PostMapping("/recibo/send")
  public ResponseEntity<?> send(@RequestParam("idrecibo") String idrecibo,
                                @RequestParam("dato") String dato) throws Exception {
    Map<String, Object> response = new HashMap<>( );
    response = reciboServ.enviarRecibo(Long.valueOf(idrecibo), dato, response);
    if (( boolean ) response.get("error")) {
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }
  
  @GetMapping("/recibo/guardarFile")
  public ResponseEntity<?> downloadFile(@RequestParam(name = "idcarga") String idcarga) throws IOException {
    Map<String, Object> response = new HashMap<>( );
    // System.out.println(idcarga);
    List<?> link = linkServ.listarIDbitacoraSN(Long.valueOf(idcarga));
    // for(Link link2: link) {
    String result = new ObjectMapper( ).writeValueAsString(link);
    // System.out.println(result);
    response.put("txt", result);
    // }
    File f = File.createTempFile("linkPT_", ".lwp");
    BufferedWriter bw = new BufferedWriter(new FileWriter(f));
    bw.write(result);
    bw.close( );
    String str;
    BufferedReader br = new BufferedReader(new FileReader(f));
    while((str = br.readLine( )) != null) {
      System.out.println("Texto guardado en el archivo temporal: " + str);
    }
    br.close( );
    byte[ ] bytes = Files.readAllBytes(f.toPath( ));
    // response.put("txt", bytes);
    f.delete( );
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @GetMapping("/recibo/liquidacion/lista")
  public ResponseEntity<?> LiquidacionLista(@RequestParam(name = "estado", defaultValue = "PAG") String estado,
                                            @RequestParam(name = "fechaI", defaultValue = "") String fechaI,
                                            @RequestParam(name = "fechaF", defaultValue = "") String fechaF) throws Exception {
    Map<String, Object> response = new HashMap<>( );
    Date FechaI = null, FechaF = null;
    logger.info(fechaI + "_" + fechaF + "_" + estado);
    if (fechaI.equals("") && fechaF.equals("")) {
      FechaI = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1990");
      FechaF = aux.sumarDiasAFecha(new Date( ), 1);
    }else {
      FechaI = new SimpleDateFormat("dd/MM/yyyy").parse(fechaI);
      FechaF = aux.sumarDiasAFecha(new SimpleDateFormat("dd/MM/yyyy").parse(fechaF), 1);
    }
    response.put("data", reciboServ.ListaLiquidacionEmpresa(estado, FechaI, FechaF));
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }
  
  @GetMapping("/recibo/liquidacion/comercio")
  public ResponseEntity<?> LiquidacionComercio(@RequestParam(name = "idempresa", defaultValue = "") String idempresa,
                                               @RequestParam(name = "estado", defaultValue = "PAG") String estado,
                                               @RequestParam(name = "fechaI", defaultValue = "") String fechaI,
                                               @RequestParam(name = "fechaF", defaultValue = "") String fechaF) throws Exception {
    Map<String, Object> response = new HashMap<>( );
    Date FechaI = null, FechaF = null;
    logger.info(fechaI + "_" + fechaF + " " + estado);
    if (fechaI.equals("") && fechaF.equals("")) {
      FechaI = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1990");
      FechaF = aux.sumarDiasAFecha(new Date( ), 1);
    }else {
      FechaI = new SimpleDateFormat("dd/MM/yyyy").parse(fechaI);
      FechaF = aux.sumarDiasAFecha(new SimpleDateFormat("dd/MM/yyyy").parse(fechaF), 1);
    }
    response.put("data", reciboServ.Liquidacion(idempresa, estado, FechaI, FechaF));
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }
  
  @GetMapping("/recibo/liquidacion/empresas")
  public ResponseEntity<?> listaLiquidacionEmpresas(@RequestParam(name = "estado", defaultValue = "PAG") String estado,
                                                    @RequestParam(value = "fechaInicial") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaInicial,
                                                    @RequestParam(value = "fechaFinal") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaFinal) {
    try{
        fechaFinal = aux.sumarDiasAFecha(fechaFinal, 1);

        List<Recibo> liquidaciones = reciboServ.liquidacionEmpresa(estado, fechaInicial, fechaFinal);
        List<ReciboDto> dtos = reciboMapper.asDTOList(liquidaciones);

      List<Comision> comisiones = comisionService.obtenerComisiones("A");
      dtos = comisionEmpresa.aplicarComisionesLiquidaciones(dtos, comisiones);

        Map<String, Object> response = new HashMap<>( );
        response.put("data", dtos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    catch (Exception e){
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
  }
}
