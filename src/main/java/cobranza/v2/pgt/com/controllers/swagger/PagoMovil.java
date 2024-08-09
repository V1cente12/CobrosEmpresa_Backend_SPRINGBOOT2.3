
package cobranza.v2.pgt.com.controllers.swagger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cobranza.v2.pgt.com.models.dao.IClienteDao;
import cobranza.v2.pgt.com.models.dao.IDetalleDao;
import cobranza.v2.pgt.com.models.dao.IDeudaDao;
import cobranza.v2.pgt.com.models.dao.IEmpresasDao;
import cobranza.v2.pgt.com.models.dao.ILinkDao;
import cobranza.v2.pgt.com.models.dao.IPersonasDao;
import cobranza.v2.pgt.com.models.dao.IReciboDao;
import cobranza.v2.pgt.com.models.entity.Clientes;
import cobranza.v2.pgt.com.models.entity.Detalle;
import cobranza.v2.pgt.com.models.entity.Deuda;
import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.entity.Error;
import cobranza.v2.pgt.com.models.entity.Link;
import cobranza.v2.pgt.com.models.entity.PagoRestApi;
import cobranza.v2.pgt.com.models.entity.Personas;
import cobranza.v2.pgt.com.models.entity.Recibo;
import cobranza.v2.pgt.com.models.services.IReciboServ;
import cobranza.v2.pgt.com.models.services.IUsuariosServ;
import cobranza.v2.pgt.com.utils.Auxiliar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("PagoMovil")
@Api(value = "PagoMovil", description = "Operacion de pago movil por Pagatodo360")
public class PagoMovil {
  
  private Logger logger = LoggerFactory.getLogger(PagoRest.class);
  
  @Value("#{'${name.url}'}")
  private String URL;
  
  @Value("#{'${name.port}'}")
  private String PORT;
  
  @Autowired
  private IReciboDao reciboServ;
  
  @Autowired
  private IReciboServ reciboS;
  
  @Autowired
  private IDeudaDao deudaServ;
  
  @Autowired
  private IDetalleDao detalleServ;;
  
  @Autowired
  private IPersonasDao personaServ;
  
  @Autowired
  private IUsuariosServ usuarioServ;
  
  @Autowired
  private IEmpresasDao empresaServ;
  
  @Autowired
  private IClienteDao clienteServ;
  
  @Autowired
  private ILinkDao linkServ;;
  
  @Autowired
  private Auxiliar aux;
  
  @ApiResponses(value = {@ApiResponse(code = 200, message = "La solicitud enviada es correcta...", response = Error.class),
                         @ApiResponse(code = 400, message = "La peticion de datos no coincide sus valores..", response = Error.class),
                         @ApiResponse(code = 404, message = "La solicitud envieda no coincide con los datos requeridos..", response = Error.class),
                         @ApiResponse(code = 500, message = "Error en el servicio de la peticion de datos", response = Error.class)})
  @ApiOperation(value = "Crea pago movil")
  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody PagoRestApi data,
                                  BindingResult bindingResult) {
    Map<String, Object> response = new HashMap<>( );
    Map<String, Object> claims = new HashMap<>( );
    Clientes cliente = new Clientes( );
    Personas persona = new Personas( );
    Recibo recibo = new Recibo( );
    Map<String, String> MultipleRecibo = null;
    List<String> IdRecibos = new ArrayList<>( );
    List<Long> IDs = new ArrayList<>( );
    Link link = new Link( );
    Deuda deuda = new Deuda( );
    Detalle detalle = new Detalle( );
    ObjectMapper oMapper = new ObjectMapper( );
    try {
      logger.info(oMapper.writeValueAsString(data));
    }
    catch(JsonProcessingException e2) {
      e2.printStackTrace( );
    }
    List<FieldError> errors = bindingResult.getFieldErrors( );
    response = this.aux.validarBody(errors, data, response, bindingResult);
    if (( boolean ) response.get("error")) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                                .body(response);
    if (!usuarioServ.verificarToken(data.getToken( ))) {
      response.put("mensaje", "Token expirado, vuelva a generar otro..");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    // CALLBACK DE SERVICIO DE LOS COMERCIOS
    if (data.getPData01( ) != null) {
      response = this.aux.validaProtocolo(data.getPData01( ), response, "PData1");
      if (( boolean ) response.get("error")) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                                  .body(response);
    }
    // COMPARACION DE LOS HHTP O HTTPS
    if (data.getPData02( ) != null) {
      response = this.aux.validaProtocolo(data.getPData02( ), response, "PData2");
      if (( boolean ) response.get("error")) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                                  .body(response);
    }
    
    // COMPARACION DE LOS HHTP O HTTPS
    if (data.getPData03( ) != null) {
      response = this.aux.validaProtocolo(data.getPData03( ), response, "PData3");
      if (( boolean ) response.get("error")) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                                  .body(response);
    }
    Optional<Recibo> existe = reciboServ.ExisteNroPedido(data.getNro_recibo( ), data.getIdempresa( ));
    if (existe.isPresent( )) {
      logger.info("Recibo/deuda ya existe..");
      logger.info("Generando URL..");
      String jwtId = String.valueOf(data.getIdempresa( ));
      String jwtIssuer = String.valueOf(existe.get( )
                                              .getIddeuda( )
                                              .getIddeuda( ));
      String jwtSubject = data.getToken( );
      claims.put("references", "2");
      String token = aux.createJWT2(jwtId, jwtIssuer, jwtSubject, claims, existe.get( )
                                                                                .getFecha_vencimiento( ));
      String linkPago = URL + ":" + PORT + "/#/carrito?id=";
      link.setCodigocliente(existe.get( )
                                  .getIddeuda( )
                                  .getIdcliente( )
                                  .getCodigo_cliente( ));
      link.setCorreo(data.getCorreo( ));
      link.setEstado("A");
      link.setIdcliente(existe.get( )
                              .getIddeuda( )
                              .getIdcliente( ));
      link.setIddeuda(existe.get( )
                            .getIddeuda( )
                            .getIddeuda( )
                            .toString( ));
      link.setMonto(existe.get( )
                          .getMonto( ));
      link.setToken(token);
      link.setLink(linkPago);
      Optional<Link> URLink = linkServ.findByIddeudaDesc(existe.get( )
                                                               .getIddeuda( )
                                                               .getIddeuda( )
                                                               .toString( ));
      if (URLink.isPresent( )) {
        link.setFecha_modificacion(new Date( ));
        link.setFecha_alta(URLink.get( )
                                 .getFecha_alta( ));
        link.setUsuario_modificacion(data.getNombres( ) + " " + data.getApellido_paterno( ));
        link.setIdlink(URLink.get( )
                             .getIdlink( ));
      }
      try {
        detalle = detalleServ.findByIdrecibo(existe.get( )
                                                   .getIdrecibo( ));
      }
      catch(Exception e) {
        logger.info("Error al obtener el detalle del recibo");
      }
      existe.get( )
            .setNombre_apellido(data.getValor_documento( ) + ";" + data.getNombres( ) + ";" + data
                                                                                                  .getApellido_paterno( )
              + ";" + data.getDomicilio( ) + ";" + data.getCorreo( ) + ';' + data.getPData09( ));
      existe.get( )
            .setCodigo_pago(data.getPData05( ));
      existe.get( )
            .setFecha_modificacion(new Date( ));
      existe.get( )
            .getIddeuda( )
            .setFecha_modificacion(new Date( ));
      existe.get( )
            .setGlosa2(data.getCorreo( ));
      existe.get( )
            .getIddeuda( )
            .setObservacion(data.getPData02( ));
      existe.get( )
            .setConcepto_recibo(data.getConcepto_recibo( ));
      existe.get( )
            .setDescripcion_general(data.getDescripcion_general( ));
      detalle.setItem("0");
      detalle.setDescripcion_item(data.getDetalle( )
                                      .get(0)
                                      .getDescripcion_item( ));
      existe.get( )
            .getIddetalle( )
            .add(detalle);
      try {
        reciboServ.save(existe.get( ));
      }
      catch(Exception e) {
        logger.info("Error al actualizar el recibo");
      }
      // GENERA EL LINK DE PAGO COMO RESPUESTA
      try {
        linkServ.save(link);
      }
      catch(Exception e) {
        System.out.println(e);
        response.put("mensaje", "Error en generar link!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(response);
      }
      logger.info(linkPago + token);
      response.put("URL", linkPago + token);
      return ResponseEntity.status(HttpStatus.OK)
                           .body(response);
    }else if (data.getNro_recibo( ) == 0) data.setNro_recibo(new Date( ).getTime( ));
    List<Detalle> detalleList = new ArrayList<Detalle>( );
    if (data.getPData05( ) != null && data.getPData05( )
                                          .length( ) > 2) {
      try {
        logger.info(data.getPData05( ));
        MultipleRecibo = reciboS.obtenerRecibos(data.getPData05( ));
        logger.info(MultipleRecibo.toString( ));
      }
      catch(Exception e) {
        logger.error("" + e);
      }
      try {
        for(Map.Entry<String, String> entry: MultipleRecibo.entrySet( )) {
          IdRecibos.add(entry.getValue( ));
        }
        IDs = IdRecibos.stream( )
                       .map(Long::valueOf)
                       .collect(Collectors.toList( ));
        if (reciboS.ListMultimoneda(IDs)
                   .size( ) > 1) {
          response.put("mensaje", "Multiple seleccion de tipo de monedas");
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                               .body(response);
        }
      }
      catch(Exception e) {
        e.printStackTrace( );
      }
    }
    Personas personanueva = personaServ.buscarValorDoc(data.getValor_documento( ));
    if (personanueva == null) {
      persona.setValor_documento(data.getValor_documento( ));
      persona.setNombres(data.getNombres( ));
      persona.setApellido_paterno(data.getApellido_paterno( ));
      persona.setApellido_materno(data.getApellido_materno( ) == "string" ? "" : data.getApellido_materno( ));
      persona.setTipo_documento(data.getTipo_documento( ));
      persona.setDomicilio(data.getDomicilio( ));
    }else {
      // personanueva.setNombres(data.getNombres( ));
      // personanueva.setApellido_paterno(data.getApellido_paterno( ));
      // personanueva.setApellido_materno(data.getApellido_materno( ) == "string" ? "" : data
      // .getApellido_materno( ));
      // personanueva.setTipo_documento(data.getTipo_documento( ));
      // personanueva.setDomicilio(data.getDomicilio( ));
      persona = null;
      persona = personanueva;
    }
    if (data.getValor_documento( )
            .equals("0")) recibo.setSuscripcion(data.getNombres( ) + " " + data.getApellido_paterno( ));
    recibo.setNro_recibo(data.getNro_recibo( ));
    recibo.setDescripcion_general(data.getDescripcion_general( ));
    recibo.setConcepto_recibo(data.getConcepto_recibo( ));
    recibo.setEstado("APP");
    recibo.setGlosa1(data.getNit( ));
    recibo.setGlosa2(data.getCorreo( ));
    recibo.setGlosa3(data.getPData01( ));
    recibo.setGlosa4(data.getPData02( ));
    recibo.setGlosa5(data.getPData03( ));
    try {
      if (data.getPData04( ) == null || data.getPData04( )
                                            .equals("") || data.getPData04( )
                                                               .equals("string")) {
        data.setPData04(aux.ConversionDateString(aux.sumarDiasAFecha(new Date( ), 1), "dd/MM/yyyy"));
        
      }
      recibo.setFecha_vencimiento(aux.conversionStringDate(data.getPData04( ) + " 23:59:59",
        "dd/MM/yyyy hh:mm:ss"));
    }
    catch(ParseException e1) {
      response.put("mensaje", "Fecha de vencimiento incorrecto: " + data.getPData04( )
        + ", por favor en este formato: dd/MM/yyyy (dia/mes/año)");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                           .body(response);
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    recibo.setCodigo_pago(data.getPData05( ));
    recibo.setMoneda(data.getMoneda( ));
    recibo.setMonto(data.getMonto( ));
    recibo.setNombre_apellido(data.getValor_documento( ) + ";" + data.getNombres( ) + ";" + data
                                                                                                .getApellido_paterno( )
      + ";" + data.getDomicilio( ) + ";" + data.getCorreo( ) + ';' + data.getPData09( ));
    logger.info("Registro de persona...");
    try {
      persona = personaServ.save(persona);
    }
    catch(Exception e) {
      response.put("mensaje", "No se puede realizar la consulta a la BDD o servidor caido..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
    try {
      if (!clienteServ.booleanClienteEmpresa(data.getValor_documento( ), data.getIdempresa( ))) {
        Empresas e = empresaServ.findByEstadoAndIdempresa("A", Long.valueOf(data.getIdempresa( )));
        cliente.setIdpersona(persona);
        cliente.setCodigo_cliente(persona.getValor_documento( ));
        cliente.setEstado("A");
        cliente.setIdempresa(e);
        cliente = clienteServ.save(cliente);
        logger.info("Se agrego una nueva persona-cliente ");
      }else {
        logger.info("Cliente exitente");
        cliente = clienteServ.buscarClienteEmpresa(persona.getValor_documento( ), Long.valueOf(data
                                                                                                   .getIdempresa( )))
                             .get( );
      }
    }
    catch(Exception e) {
      response.put("mensaje", "No se puede realizar la consulta a la BDD o servidor caido..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
    try {
      recibo = reciboServ.save(recibo);
    }
    catch(Exception e) {
      response.put("mensaje", "Error en la deuda del recibo");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    for(int i = 0;i < data.getDetalle( )
                          .size( );i++) {
      Detalle d = new Detalle( );
      d.setIdrecibo(recibo);
      d.setItem(data.getDetalle( )
                    .get(i)
                    .getItem( ));
      d.setDescripcion_item(data.getDetalle( )
                                .get(i)
                                .getDescripcion_item( ));
      d.setCantidad(Integer.valueOf(data.getDetalle( )
                                        .get(i)
                                        .getCantidad( )));
      d.setPrecio_unitario(data.getDetalle( )
                               .get(i)
                               .getPrecio_unitario( ));
      d.setSub_total(data.getDetalle( )
                         .get(i)
                         .getSub_total( ));
      detalleList.add(d);
    }
    deuda.setIdrecibo(recibo);
    deuda.setIdcliente(cliente);
    deuda.setObservacion(data.getPData02( ));
    deuda.setEstado("APP");
    try {
      deuda = deudaServ.save(deuda);
      detalleServ.saveAll(detalleList);
    }
    catch(Exception e) {
      response.put("mensaje", "Error en el detalle de la deuda del recibo");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    try {
      logger.info("Generando URL..");
      String jwtId = String.valueOf(cliente.getIdempresa( )
                                           .getIdempresa( ));
      String jwtIssuer = String.valueOf(deuda.getIddeuda( ));
      String jwtSubject = data.getToken( );
      claims.put("references", "2");
      String token = aux.createJWT2(jwtId, jwtIssuer, jwtSubject, claims, recibo.getFecha_vencimiento( ));
      String linkPago = URL + ":" + PORT + "/#/carrito?id=";
      link.setCodigocliente(cliente.getCodigo_cliente( ));
      link.setCorreo(persona.getCorreo( ));
      link.setEstado("A");
      link.setIdcliente(cliente);
      link.setIddeuda(deuda.getIddeuda( )
                           .toString( ));
      link.setMonto(recibo.getMonto( ));
      link.setToken(token);
      link.setLink(linkPago);
      linkServ.save(link);
      logger.info(linkPago + token);
      response.put("URL", linkPago + token);
    }
    catch(Exception e) {
      response.put("mensaje", "Error en generar link!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
}
