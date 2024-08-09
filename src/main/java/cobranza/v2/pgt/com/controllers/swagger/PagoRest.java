
package cobranza.v2.pgt.com.controllers.swagger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
import cobranza.v2.pgt.com.models.services.IItemVentaServ;
import cobranza.v2.pgt.com.models.services.IReciboServ;
import cobranza.v2.pgt.com.models.services.IUsuariosServ;
import cobranza.v2.pgt.com.utils.Auxiliar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;;

@RestController
@RequestMapping("PagoRest")
@Api(value = "Pago", description = "Operacion de pago por Pagatodo360")
public class PagoRest {
  
  private Logger logger = LoggerFactory.getLogger(PagoRest.class);
  
  @Value("#{'${name.url}'}")
  private String URL;
  @Value("#{'${url.qr}'}")
  private String _HOST;
  
  @Value("#{'${name.port}'}")
  private String PORT;
  @Value("#{'${port.qr}'}")
  private String PORT_QR;
  @Value("#{'${SAPECommerce}'}")
  private String SAPECommerce;
  
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
  private IItemVentaServ itemServ;
  
  @Autowired
  private ILinkDao linkServ;;
  
  @Autowired
  private Auxiliar aux;
  
  @Value("#{'${empresa.daher}'}")
  private String DAHER;
  @Value("#{'${empresa.id.addiuva}'}")
  private String ADDIUVA;
  @Value("#{'${empresa.id.getserver}'}")
  private String GETSERVER;
  @Value("#{'${empresa.phmedical}'}")
  private String Phmedical;
  @Value("#{'${empresa.genio}'}")
  private String Genio;
  
  @Autowired
  @Qualifier("simpleRestTemplate")
  private RestTemplate simpleRestTemplate;
  
  @ApiResponses(value = {@ApiResponse(code = 200, message = "La solicitud enviada es correcta...", response = Error.class),
                         @ApiResponse(code = 400, message = "La peticion de datos no coincide sus valores..", response = Error.class),
                         @ApiResponse(code = 404, message = "La solicitud envieda no coincide con los datos requeridos..", response = Error.class),
                         @ApiResponse(code = 500, message = "Error en el servicio de la peticion de datos", response = Error.class)})
  @ApiOperation(value = "Crea pago")
  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody PagoRestApi data,
                                  BindingResult bindingResult) {
    Map<String, Object> response = new HashMap<>( );
    Map<String, Object> response2 = new HashMap<>( );
    Map<String, Object> claims = new HashMap<>( );
    ObjectMapper oMapper = new ObjectMapper( );
    Clientes cliente = new Clientes( );
    Personas persona = new Personas( );
    Recibo recibo = new Recibo( );
    Link link = new Link( );
    Deuda deuda = new Deuda( );
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
    // COMPARACION DE LAS PRIMERAS EMPRESAS INGRESADAS DE PGT360
    if (!(DAHER.equals(String.valueOf(data.getIdempresa( )))) && !(ADDIUVA.equals(String.valueOf(data
                                                                                                     .getIdempresa( ))))
      && !(Phmedical.equals(String.valueOf(data.getIdempresa( )))) && !(GETSERVER.equals(String.valueOf(data
                                                                                                            .getIdempresa( ))))) {
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
    }
    List<Detalle> detalleList = new ArrayList<Detalle>( );
    // CAMPO FIELDS APELLIDO PATERNO OBLIGATORIO
    if (data.getApellido_paterno( )
            .length( ) <= 2) {
      response.put("mensaje", "Se requiere el apellido paterno de la persona");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    // COMPARA SI EXISTE EL NRO DE PEDIDO
    System.out.println(data.getNro_recibo( ) + " _ " + data.getIdempresa( ));
    if (reciboServ.ExisteNroPedido(data.getNro_recibo( ), data.getIdempresa( ))
                  .isPresent( )) {
      
      if (SAPECommerce.equals(data.getIdempresa( )
                                  .toString( )) || Genio.equals(data.getIdempresa( )
                                                                    .toString( ))) {
        // BUSCAR EL NRO DE PEDIDIO DE SAPE
        Recibo rec2 = reciboServ.findByNroreciboIdempresa(data.getNro_recibo( ), data.getIdempresa( ));
        if (rec2 == null) {
          response.put("mensaje", "El numero de pedido ya fue pagado");
          return ResponseEntity.status(HttpStatus.NOT_FOUND)
                               .body(response);
        }else {
          // ACTUALIZA LA PERSONA
          Personas personanueva = personaServ.buscarValorDoc(data.getValor_documento( ));
          try {
            if (personanueva == null) {
              logger.info("Persona nueva");
              // PERSONA NUEVA
              persona.setValor_documento(data.getValor_documento( ));
              persona.setNombres(data.getNombres( ));
              persona.setApellido_paterno(data.getApellido_paterno( ));
              persona.setApellido_materno(data.getApellido_materno( ) == "string" ? "" : data
                                                                                             .getApellido_materno( ));
              persona.setTipo_documento(data.getTipo_documento( ));
              persona.setDomicilio(data.getDomicilio( )
                                       .replace("\n", " ; ")
                                       .replaceAll("\n", " ; ")
                                       .replaceAll("\\R", " ; "));
            }else {
              // PERSONA EXISTENTE
              logger.info("Persona exitente");
              personanueva.setNombres(data.getNombres( ));
              personanueva.setApellido_paterno(data.getApellido_paterno( ));
              personanueva.setApellido_materno(data.getApellido_materno( ) == "string" ? "" : data
                                                                                                  .getApellido_materno( ));
              personanueva.setTipo_documento(data.getTipo_documento( ));
              personanueva.setDomicilio(data.getDomicilio( )
                                            .replace("\n", " ; ")
                                            .replaceAll("\n", " ; ")
                                            .replaceAll("\\R", " ; "));
              persona = null;
              persona = personanueva;
            }
            personaServ.save(persona);
          }
          catch(Exception e) {
            logger.error("Persona de SAP");
          }
          try {
            if (!clienteServ.booleanClienteEmpresa(data.getValor_documento( ), data.getIdempresa( ))) {
              Empresas e = empresaServ.findByEstadoAndIdempresa("A", Long.valueOf(data.getIdempresa( )));
              cliente.setIdpersona(persona);
              cliente.setCodigo_cliente(persona.getValor_documento( ));
              cliente.setEstado("A");
              cliente.setIdempresa(e);
              cliente = clienteServ.save(cliente);
              logger.info("Se agrego una nueva persona-cliente SAP");
            }else {
              logger.info("Cliente exitente");
              cliente = clienteServ.buscarClienteEmpresa(persona.getValor_documento( ), Long.valueOf(data
                                                                                                         .getIdempresa( )))
                                   .get( );
              cliente.setCodigo_cliente(data.getValor_documento( ));
            }
            clienteServ.save(cliente);
          }
          catch(Exception e) {
            logger.error("Cliente de SAP");
          }
          try {
            Recibo recibonuevo = reciboServ.findByNroreciboIdempresa(data.getNro_recibo( ), data
                                                                                                .getIdempresa( ));
            if (data.getValor_documento( )
                    .equals("0")) recibonuevo.setSuscripcion(data.getNombres( ) + " " + data
                                                                                            .getApellido_paterno( ));
            recibonuevo.setNro_recibo(data.getNro_recibo( ));
            recibonuevo.setDescripcion_general(data.getDescripcion_general( ));
            recibonuevo.setConcepto_recibo(data.getConcepto_recibo( ));
            recibonuevo.setGlosa1(data.getNit( ));
            recibonuevo.setGlosa2(data.getCorreo( ));
            recibonuevo.setGlosa3(data.getPData01( ));
            recibonuevo.setGlosa4(data.getPData02( ));
            recibonuevo.setGlosa5(data.getPData03( ));
            recibonuevo.setCodigo_pago(data.getPData05( ));
            recibonuevo.setMoneda(data.getMoneda( ));
            recibonuevo.setMonto(data.getMonto( ));
            recibonuevo.setFecha_modificacion(new Date( ));
            recibo.setNombre_apellido(data.getValor_documento( ) + ";" + data.getNombres( ) + ";" + data
                                                                                                        .getApellido_paterno( )
              + ";" + data.getDomicilio( )
                          .replace("\n", " ") + ";" + data.getCorreo( ) + ';' + data.getPData09( ));
            try {
              
              // SI NO HAY DATOS SE INGRESA LA FECHA ACTUAL +1 DIA
              if (data.getPData04( ) == null || data.getPData04( )
                                                    .equals("") || data.getPData04( )
                                                                       .equals("string")) {
                if (SAPECommerce.equals(data.getIdempresa( )
                                            .toString( ))) {
                  data.setPData04(aux.ConversionDateString(aux.sumarDiasAFecha(new Date( ), 364),
                    "dd/MM/yyyy"));
                }else {
                  data.setPData04(aux.ConversionDateString(aux.sumarDiasAFecha(new Date( ), 1),
                    "dd/MM/yyyy"));
                }
                
                // recibo.setFecha_vencimiento(aux.sumarDiasAFecha(new Date( ), 1));
              }
              // SETTER DEL VALOR DE LA FECHA DE VENCIENTO
              recibonuevo.setFecha_vencimiento(aux.conversionStringDate(data.getPData04( ) + " 23:59:59",
                "dd/MM/yyyy hh:mm:ss"));
            }
            catch(Exception e1) {
              response.put("mensaje", "Fecha de vencimiento incorrecto: " + data.getPData04( )
                + ", por favor en este formato: dd/MM/yyyy (dia/mes/año)");
              return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                   .body(response);
            }
            reciboServ.save(recibonuevo);
          }
          catch(Exception e) {
            logger.error("Recibo de SAP");
          }
          
          // ENCUENTRA EL RECIBO
          BigDecimal monto = new BigDecimal(0);
          for(int i = 0;i < data.getDetalle( )
                                .size( );i++) {
            Detalle d = new Detalle( );
            // d.setCodItem(data.getPData01( ));
            d.setEstado("PEN");
            d.setIdrecibo(rec2);
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
            monto = monto.add(d.getSub_total( ));
            detalleList.add(d);
          }
          
          // ACTUALIZA EL MONTO EN EL RECIBO
          reciboServ.updatemonto(monto, rec2.getIdrecibo( ));
          
          // ELIMINA EL DETALLE DEL RECIBO
          detalleServ.bajaIdrecibo(rec2.getIdrecibo( ));
          
          // GUARDAR EL NUEVO DETALLE
          detalleServ.saveAll(detalleList);
          Link URLink = linkServ.findByIddeuda(rec2.getIddeuda( )
                                                   .getIddeuda( )
                                                   .toString( ))
                                .get( );
          // GENERA EL LINK DE PAGO COMO RESPUESTA
          String url = URLink.getLink( ) + URLink.getToken( );
          response.put("URL", url);
          response2.put("URL", response.get("URL"));
          return ResponseEntity.status(HttpStatus.OK)
                               .body(response2);
        }
      }else {
        response.put("mensaje", "Numero de pedido ya existente en el registro de la empresa " + data
                                                                                                    .getNro_recibo( ));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(response);
      }
    }
    // BUSCA EL NRO DE DOCUMENTO EN LA BBD DE LA PERSONA INGRESADA
    Personas personanueva = personaServ.buscarValorDoc(data.getValor_documento( ));
    System.out.println("pasas persona");
    if (personanueva == null) {
      
      // PERSONA NUEVA
      persona.setValor_documento(data.getValor_documento( ));
      persona.setNombres(data.getNombres( ));
      persona.setApellido_paterno(data.getApellido_paterno( ));
      persona.setApellido_materno(data.getApellido_materno( ) == "string" ? "" : data.getApellido_materno( ));
      persona.setTipo_documento(data.getTipo_documento( ));
      persona.setDomicilio(data.getDomicilio( )
                               .replace("\n", " ; ")
                               .replaceAll("\n", " ; ")
                               .replaceAll("\\R", " ; "));
    }else {
      // PERSONA EXISTENTE
      logger.info("Persona exitente");
      personanueva.setNombres(data.getNombres( ));
      personanueva.setApellido_paterno(data.getApellido_paterno( ));
      personanueva.setApellido_materno(data.getApellido_materno( ) == "string" ? "" : data
                                                                                          .getApellido_materno( ));
      personanueva.setTipo_documento(data.getTipo_documento( ));
      personanueva.setDomicilio(data.getDomicilio( )
                                    .replace("\n", " ; ")
                                    .replaceAll("\n", " ; ")
                                    .replaceAll("\\R", " ; "));
      persona = null;
      persona = personanueva;
    }
    System.out.println("pasas cliente");
    if (data.getValor_documento( )
            .equals("0")) recibo.setSuscripcion(data.getNombres( ) + " " + data.getApellido_paterno( ));
    System.out.println("====");
    recibo.setNro_recibo(data.getNro_recibo( ));
    recibo.setDescripcion_general(data.getDescripcion_general( ));
    recibo.setConcepto_recibo(data.getConcepto_recibo( ));
    recibo.setEstado("PEN");
    recibo.setGlosa1(data.getNit( ));
    recibo.setGlosa2(data.getCorreo( ));
    recibo.setGlosa3(data.getPData01( ));
    recibo.setGlosa4(data.getPData02( ));
    recibo.setGlosa5(data.getPData03( ));
    recibo.setNombre_apellido(data.getValor_documento( ) + ";" + data.getNombres( ) + ";" + data
                                                                                                .getApellido_paterno( )
      + ";" + data.getDomicilio( )
                  .replace("\n", " ") + ";" + data.getCorreo( ) + ';' + data.getPData09( ));
    // PDATA_4 RECIBE LA FECHA DE VENCIMIENTO
    try {
      
      // SI NO HAY DATOS SE INGRESA LA FECHA ACTUAL +1 DIA
      if (data.getPData04( ) == null || data.getPData04( )
                                            .equals("") || data.getPData04( )
                                                               .equals("string")) {
        if (SAPECommerce.equals(data.getIdempresa( )
                                    .toString( ))) {
          data.setPData04(aux.ConversionDateString(aux.sumarDiasAFecha(new Date( ), 364), "dd/MM/yyyy"));
        }else {
          data.setPData04(aux.ConversionDateString(aux.sumarDiasAFecha(new Date( ), 1), "dd/MM/yyyy"));
        }
        
        // recibo.setFecha_vencimiento(aux.sumarDiasAFecha(new Date( ), 1));
      }
      // SETTER DEL VALOR DE LA FECHA DE VENCIENTO
      recibo.setFecha_vencimiento(aux.conversionStringDate(data.getPData04( ) + " 23:59:59",
        "dd/MM/yyyy hh:mm:ss"));
    }
    catch(Exception e1) {
      response.put("mensaje", "Fecha de vencimiento incorrecto: " + data.getPData04( )
        + ", por favor en este formato: dd/MM/yyyy (dia/mes/año)");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                           .body(response);
    }
    recibo.setCodigo_pago(data.getPData05( ));
    recibo.setMoneda(data.getMoneda( ));
    recibo.setMonto(data.getMonto( ));
    try {
      persona = personaServ.save(persona);
    }
    catch(Exception e) {
      response.put("mensaje", "No se puede realizar la consulta a la BDD o servidor caido..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
    
    try {
      
      // VERIFICACION DEL CLIENTE SI EXISTE EN LA EMPRESA
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
      // GUARDA LA INFORMACION DEL NUEVO RECIBO CON DETALLE
      recibo = reciboServ.save(recibo);
    }
    catch(Exception e) {
      e.printStackTrace( );
      logger.info("Error recibo ");
    }
    for(int i = 0;i < data.getDetalle( )
                          .size( );i++) {
      Detalle d = new Detalle( );
      d.setEstado("PEN");
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
      d.setCodItem(data.getDetalle( )
                       .get(i)
                       .getDData01( ));
      detalleList.add(d);
      if (recibo.getCodigo_pago( ) != null) {
        if (recibo.getCodigo_pago( )
                  .equals("S.O.010")) {
          itemServ.aumentarVentas(Long.valueOf(d.getCodItem( )));
        }
      }
      deuda.setIdrecibo(recibo);
      deuda.setIdcliente(cliente);
      deuda.setEstado("PEN");
    }
    try {
      deuda = deudaServ.save(deuda);
      detalleServ.saveAll(detalleList);
    }
    catch(Exception e) {
      response.put("mensaje", "Error en los datos de la deuda/recibo!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    // PERSISTIR EL LINK Y GENERAR EL TOKEN EN LA URL
    String linkPago = URL + ":" + PORT + "/#/carrito?id=";
    String jwtId = String.valueOf(cliente.getIdempresa( )
                                         .getIdempresa( ));
    String jwtIssuer = String.valueOf(deuda.getIddeuda( ));
    String jwtSubject = data.getToken( );
    // int jwtTimeToLive = 24 * 7;
    claims.put("references", "0");
    String token = aux.createJWT2(jwtId, jwtIssuer, jwtSubject, claims, recibo.getFecha_vencimiento( ));
    try {
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
    }
    catch(Exception e) {
      response.put("mensaje", "Error en generar link!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    try {
      response.put("URL", linkPago + link.getToken( ));
    }
    catch(Exception e) {
      response.put("mensaje", "Error en generar el link de pago!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    response2.put("URL", response.get("URL"));
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response2);
  }
  
  @ApiResponses(value = {@ApiResponse(code = 200, message = "La solicitud enviada es correcta...", response = Error.class),
                         @ApiResponse(code = 400, message = "La peticion de datos no coincide sus valores..", response = Error.class),
                         @ApiResponse(code = 404, message = "La solicitud envieda no coincide con los datos requeridos..", response = Error.class),
                         @ApiResponse(code = 500, message = "Error en el servicio de la peticion de datos", response = Error.class)})
  @ApiOperation(value = "Crea pago en QR")
  @PostMapping("/QR")
  public ResponseEntity<?> createQR(@Valid @RequestBody PagoRestApi data,
                                    BindingResult bindingResult) throws ParseException,
                                                                 Exception {
    Map<String, Object> response = new HashMap<>( );
    Map<String, Object> claims = new HashMap<>( );
    ObjectMapper oMapper = new ObjectMapper( );
    Clientes cliente = new Clientes( );
    Personas persona = new Personas( );
    Recibo recibo = new Recibo( );
    Link link = new Link( );
    Deuda deuda = new Deuda( );
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
    // COMPARACION DE LAS PRIMERAS EMPRESAS INGRESADAS DE PGT360
    if (!(DAHER.equals(String.valueOf(data.getIdempresa( )))) && !(ADDIUVA.equals(String.valueOf(data
                                                                                                     .getIdempresa( ))))
      && !(Phmedical.equals(String.valueOf(data.getIdempresa( )))) && !(GETSERVER.equals(String.valueOf(data
                                                                                                            .getIdempresa( ))))) {
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
    }
    List<Detalle> detalleList = new ArrayList<Detalle>( );
    
    // CAMPO FIELDS APELLIDO PATERNO OBLIGATORIO
    if (data.getApellido_paterno( )
            .length( ) <= 2) {
      response.put("mensaje", "Se requiere el apellido paterno de la persona");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    
    // COMPARA SI EXISTE EL NRO DE PEDIDO
    // if (reciboServ.ExisteNroPedido(data.getNro_recibo( ), data.getIdempresa( ))
    // .isPresent( )) {
    //
    // response.put("mensaje", "Numero de pedido ya existente en el registro de la empresa" + data
    // .getNro_recibo( ));
    // return ResponseEntity.status(HttpStatus.NOT_FOUND)
    // .body(response);
    //
    // }
    
    // BUSCA EL NRO DE DOCUMENTO EN LA BBD DE LA PERSONA INGRESADA
    Personas personanueva = personaServ.buscarValorDoc(data.getValor_documento( ));
    if (personanueva == null) {
      
      // PERSONA NUEVA
      persona.setValor_documento(data.getValor_documento( ));
      persona.setNombres(data.getNombres( ));
      persona.setApellido_paterno(data.getApellido_paterno( ));
      persona.setApellido_materno(data.getApellido_materno( ) == "string" ? "" : data.getApellido_materno( ));
      persona.setTipo_documento(data.getTipo_documento( ));
      persona.setDomicilio(data.getDomicilio( )
                               .replace("\n", " ; ")
                               .replaceAll("\n", " ; ")
                               .replaceAll("\\R", " ; "));
    }else {
      // PERSONA EXISTENTE
      logger.info("Persona exitente");
      personanueva.setNombres(data.getNombres( ));
      personanueva.setApellido_paterno(data.getApellido_paterno( ));
      personanueva.setApellido_materno(data.getApellido_materno( ) == "string" ? "" : data
                                                                                          .getApellido_materno( ));
      personanueva.setTipo_documento(data.getTipo_documento( ));
      personanueva.setDomicilio(data.getDomicilio( )
                                    .replace("\n", " ; ")
                                    .replaceAll("\n", " ; ")
                                    .replaceAll("\\R", " ; "));
      persona = null;
      persona = personanueva;
    }
    if (data.getNro_recibo( ) == 0) {
      recibo.setNro_recibo(new Date( ).getTime( ));
    }else {
      recibo.setNro_recibo(data.getNro_recibo( ));
    }
    
    recibo.setDescripcion_general(data.getDescripcion_general( ));
    recibo.setConcepto_recibo(data.getConcepto_recibo( ));
    recibo.setEstado("PEN");
    recibo.setGlosa1(data.getNit( ));
    recibo.setGlosa2(data.getCorreo( ));
    recibo.setGlosa3(data.getPData01( ));
    recibo.setGlosa4(data.getPData02( ));
    recibo.setGlosa5(data.getPData03( ));
    
    // PDATA_4 RECIBE LA FECHA DE VENCIMIENTO
    try {
      
      // SI NO HAY DATOS SE INGRESA LA FECHA ACTUAL +1 DIA
      if (data.getPData04( ) == null || data.getPData04( )
                                            .equals("") || data.getPData04( )
                                                               .equals("string")) {
        data.setPData04(aux.ConversionDateString(aux.sumarDiasAFecha(new Date( ), 1), "dd/MM/yyyy"));
      }
      // SETTER DEL VALOR DE LA FECHA DE VENCIENTO
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
      + ";" + data.getDomicilio( )
                  .replace("\n", " ") + ";" + data.getCorreo( ) + ';' + data.getPData09( ));
    try {
      persona = personaServ.save(persona);
    }
    catch(Exception e) {
      response.put("mensaje", "No se puede realizar la consulta a la BDD o servidor caido..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
    try {
      
      // VERIFICACION DEL CLIENTE SI EXISTE EN LA EMPRESA
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
      // GUARDA LA INFORMACION DEL NUEVO RECIBO CON DETALLE
      recibo = reciboServ.save(recibo);
      for(int i = 0;i < data.getDetalle( )
                            .size( );i++) {
        Detalle d = new Detalle( );
        d.setEstado("PEN");
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
      deuda.setEstado("PEN");
      deuda = deudaServ.save(deuda);
      detalleServ.saveAll(detalleList);
      
    }
    catch(Exception e) {
      response.put("mensaje", "Error en los datos de la deuda/recibo!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    
    // PERSISTIR EL LINK Y GENERAR EL TOKEN EN LA URL
    String linkPago = URL + ":" + PORT + "/#/carrito?id=";
    String jwtId = String.valueOf(cliente.getIdempresa( )
                                         .getIdempresa( ));
    String jwtIssuer = String.valueOf(deuda.getIddeuda( ));
    String jwtSubject = data.getToken( );
    // int jwtTimeToLive = 24 * 7;
    claims.put("references", "0");
    String token = aux.createJWT2(jwtId, jwtIssuer, jwtSubject, claims, recibo.getFecha_vencimiento( ));
    try {
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
    }
    catch(Exception e) {
      response.put("mensaje", "Error en generar link!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    logger.info(recibo.getCodigo_pago( ));
    if (recibo.getCodigo_pago( ) != null && recibo.getCodigo_pago( )
                                                  .equals("Q.R.")) {
      String json = "{ \"aliasQr\": " + cliente.getIdempresa( )
                                               .getIdempresa( )
        + ", \"banco\": \"BNB\", \"fechaExpiracion\": \"" + aux.ConversionDateString(recibo
                                                                                           .getFecha_vencimiento( ),
          "yyyy-MM-dd") + "\", \"glosa\": \"Pago por Servicios\", \"idEmpresa\": " + cliente.getIdempresa( )
                                                                                            .getIdempresa( )
        + ", \"moneda\": \"BOB\", \"monto\": " + recibo.getMonto( ) + ", \"recibos\": \"" + recibo
                                                                                                  .getIdrecibo( )
        + "\", \"referenceNumber\": " + new Date( ).getTime( ) + ", \"usoUnico\": true}";
      logger.info(json);
      try {
        HttpHeaders headers = new HttpHeaders( );
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
        logger.info(this._HOST + ":" + this.PORT_QR + "/apiqr/v1/generaqr");
        ResponseEntity<String> response2 = simpleRestTemplate.exchange(this._HOST + ":" + this.PORT_QR
          + "/apiqr/v1/generaqr", HttpMethod.POST, entity, String.class);
        String jsonString = response2.getBody( );
        ObjectMapper mapper = new ObjectMapper( );
        JsonNode actualObj = mapper.readTree(jsonString);
        response.put("QR", actualObj.get("qr"));
      }
      catch(Exception e) {
        response.put("mensaje", "Error en generar el QR..");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(response);
      }
    }
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
  @ApiResponses(value = {@ApiResponse(code = 200, message = "La solicitud enviada es correcta...", response = Error.class),
                         @ApiResponse(code = 400, message = "La peticion de datos no coincide sus valores..", response = Error.class),
                         @ApiResponse(code = 404, message = "La solicitud envieda no coincide con los datos requeridos..", response = Error.class),
                         @ApiResponse(code = 500, message = "Error en el servicio de la peticion de datos", response = Error.class)})
  @ApiOperation(value = "Crea pago")
  @PostMapping("/Kiosco")
  public ResponseEntity<?> createKiosco(@Valid @RequestBody PagoRestApi data,
                                        BindingResult bindingResult) {
    Map<String, Object> response = new HashMap<>( );
    Map<String, Object> claims = new HashMap<>( );
    ObjectMapper oMapper = new ObjectMapper( );
    Clientes cliente = new Clientes( );
    Personas persona = new Personas( );
    Recibo recibo = new Recibo( );
    Link link = new Link( );
    Deuda deuda = new Deuda( );
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
    List<Detalle> detalleList = new ArrayList<Detalle>( );
    
    // CAMPO FIELDS APELLIDO PATERNO OBLIGATORIO
    if (data.getApellido_paterno( )
            .length( ) <= 2) {
      response.put("mensaje", "Se requiere el apellido paterno de la persona");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    
    // COMPARA SI EXISTE EL NRO DE PEDIDO
    if (reciboServ.ExisteNroPedido(data.getNro_recibo( ), data.getIdempresa( ))
                  .isPresent( )) {
      
      response.put("mensaje", "Numero de pedido ya existente en el registro de la empresa" + data
                                                                                                 .getNro_recibo( ));
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
      
    }
    
    // BUSCA EL NRO DE DOCUMENTO EN LA BBD DE LA PERSONA INGRESADA
    Personas personanueva = personaServ.buscarValorDoc(data.getValor_documento( ));
    if (personanueva == null) {
      
      // PERSONA NUEVA
      persona.setValor_documento(data.getValor_documento( ));
      persona.setNombres(data.getNombres( ));
      persona.setApellido_paterno(data.getApellido_paterno( ));
      persona.setApellido_materno(data.getApellido_materno( ) == "string" ? "" : data.getApellido_materno( ));
      persona.setTipo_documento(data.getTipo_documento( ));
      persona.setDomicilio(data.getDomicilio( )
                               .replace("\n", " ; ")
                               .replaceAll("\n", " ; ")
                               .replaceAll("\\R", " ; "));
    }else {
      // PERSONA EXISTENTE
      logger.info("Persona exitente");
      personanueva.setNombres(data.getNombres( ));
      personanueva.setApellido_paterno(data.getApellido_paterno( ));
      personanueva.setApellido_materno(data.getApellido_materno( ) == "string" ? "" : data
                                                                                          .getApellido_materno( ));
      personanueva.setTipo_documento(data.getTipo_documento( ));
      personanueva.setDomicilio(data.getDomicilio( )
                                    .replace("\n", " ; ")
                                    .replaceAll("\n", " ; ")
                                    .replaceAll("\\R", " ; "));
      persona = null;
      persona = personanueva;
    }
    recibo.setNro_recibo(data.getNro_recibo( ));
    recibo.setDescripcion_general(data.getDescripcion_general( ));
    recibo.setConcepto_recibo(data.getConcepto_recibo( ));
    recibo.setEstado("PEN");
    recibo.setGlosa1(data.getNit( ));
    recibo.setGlosa2(data.getCorreo( ));
    recibo.setGlosa3(data.getPData01( ));
    recibo.setGlosa4(data.getPData02( ));
    recibo.setGlosa5(data.getPData03( ));
    recibo.setGlosa6(data.getPData06( ));
    recibo.setGlosa7(data.getPData07( ));
    recibo.setGlosa8(data.getPData08( ));
    recibo.setNombre_apellido(data.getValor_documento( ) + ";" + data.getNombres( ) + ";" + data
                                                                                                .getApellido_paterno( )
      + ";" + data.getDomicilio( )
                  .replace("\n", " ") + ";" + data.getCorreo( ) + ';' + data.getPData09( ));
    // PDATA_4 RECIBE LA FECHA DE VENCIMIENTO
    try {
      
      // SI NO HAY DATOS SE INGRESA LA FECHA ACTUAL +1 DIA
      if (data.getPData04( ) == null || data.getPData04( )
                                            .equals("") || data.getPData04( )
                                                               .equals("string")) {
        data.setPData04(aux.ConversionDateString(aux.sumarDiasAFecha(new Date( ), 1), "dd/MM/yyyy"));
      }
      // SETTER DEL VALOR DE LA FECHA DE VENCIENTO
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
    try {
      persona = personaServ.save(persona);
    }
    catch(Exception e) {
      response.put("mensaje", "No se puede realizar la consulta a la BDD o servidor caido..!!");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
    try {
      
      // VERIFICACION DEL CLIENTE SI EXISTE EN LA EMPRESA
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
      
      // GUARDA LA INFORMACION DEL NUEVO RECIBO CON DETALLE
      recibo = reciboServ.save(recibo);
      for(int i = 0;i < data.getDetalle( )
                            .size( );i++) {
        Detalle d = new Detalle( );
        d.setEstado("PEN");
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
        d.setCodItem(data.getDetalle( )
                         .get(i)
                         .getDData01( ));
        detalleList.add(d);
      }
      deuda.setIdrecibo(recibo);
      deuda.setIdcliente(cliente);
      deuda.setEstado("PEN");
      deuda = deudaServ.save(deuda);
      detalleServ.saveAll(detalleList);
      
    }
    catch(Exception e) {
      response.put("mensaje", "Error en los datos de la deuda/recibo!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    
    // PERSISTIR EL LINK Y GENERAR EL TOKEN EN LA URL
    String linkPago = URL + ":" + PORT + "/#/carrito?id=";
    String jwtId = String.valueOf(cliente.getIdempresa( )
                                         .getIdempresa( ));
    String jwtIssuer = String.valueOf(deuda.getIddeuda( ));
    String jwtSubject = data.getToken( );
    // int jwtTimeToLive = 24 * 7;
    claims.put("references", "0");
    String token = aux.createJWT2(jwtId, jwtIssuer, jwtSubject, claims, recibo.getFecha_vencimiento( ));
    try {
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
    }
    catch(Exception e) {
      response.put("mensaje", "Error en generar link!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    try {
      if (recibo.getCodigo_pago( )
                .equals("K.O.001")) { response.put("iddeuda", deuda.getIddeuda( )); }
    }
    catch(Exception e) {
      response.put("mensaje", "Error en generar el link de pago para el kiosco!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    try {
      if (recibo.getCodigo_pago( )
                .equals("SHOPIFY")) {
        response = reciboS.enviarRecibo(recibo.getIdrecibo( ), "true,false", response);
      }
    }
    catch(Exception e) {
      response.put("mensaje", "Error en enviar correo de Shopifi");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    try {
      if (recibo.getCodigo_pago( )
                .equals("S.O.010")) {
        for(int i = 0;i < data.getDetalle( )
                              .size( );i++) {
          itemServ.aumentarVentas(Long.valueOf(data.getDetalle( )
                                                   .get(i)
                                                   .getDData01( )));
        }
      }
    }
    catch(Exception e) {
      response.put("mensaje", "Error en multipagos!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    try {
      response.put("URL", linkPago + link.getToken( ));
    }
    catch(Exception e) {
      response.put("mensaje", "Error en generar el link de pago!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
  @ApiResponses(value = {@ApiResponse(code = 200, message = "La solicitud enviada es correcta...", response = Error.class),
                         @ApiResponse(code = 400, message = "La peticion de datos no coincide sus valores..", response = Error.class),
                         @ApiResponse(code = 404, message = "La solicitud envieda no coincide con los datos requeridos..", response = Error.class),
                         @ApiResponse(code = 500, message = "Error en el servicio de la peticion de datos", response = Error.class)})
  @ApiOperation(value = "Actualizar tipo de pago")
  @PostMapping("/KioscoUpdate")
  public ResponseEntity<?> UpdateKiosco(@RequestParam("valor") String valor,
                                        @RequestParam("idrecibo") String idrecibo) {
    Map<String, Object> response = new HashMap<>( );
    try {
      reciboServ.updatekiosco(valor, Long.valueOf(idrecibo));
      response.put("response", true);
    }
    catch(Exception e) {
      e.printStackTrace( );
      response.put("response", false);
    }
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
}
