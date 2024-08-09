
package cobranza.v2.pgt.com.controllers.swagger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cobranza.v2.pgt.com.models.dao.IClienteDao;
import cobranza.v2.pgt.com.models.dao.IDetalleDao;
import cobranza.v2.pgt.com.models.dao.IDeudaDao;
import cobranza.v2.pgt.com.models.dao.ILinkDao;
import cobranza.v2.pgt.com.models.dao.IReciboDao;
import cobranza.v2.pgt.com.models.entity.Clientes;
import cobranza.v2.pgt.com.models.entity.Detalle;
import cobranza.v2.pgt.com.models.entity.Deuda;
import cobranza.v2.pgt.com.models.entity.Error;
import cobranza.v2.pgt.com.models.entity.Link;
import cobranza.v2.pgt.com.models.entity.Parametrica;
import cobranza.v2.pgt.com.models.entity.Personas;
import cobranza.v2.pgt.com.models.entity.Recibo;
import cobranza.v2.pgt.com.models.implement.IParametricaImpl;
import cobranza.v2.pgt.com.models.services.IDeudaServ;
import cobranza.v2.pgt.com.models.services.IReciboServ;
import cobranza.v2.pgt.com.models.services.IShopifyServ;
import cobranza.v2.pgt.com.models.services.IUsuariosServ;
import cobranza.v2.pgt.com.utils.Auxiliar;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("shopify")
@Api(value = "Consumo", description = "Operacion de consumo generado por Pagatodo360")
public class ShopifyRest {
  
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
  private IDeudaServ deServ;
  
  @Autowired
  private IClienteDao clienteServ;
  
  @Autowired
  private ILinkDao linkServ;;
  @Autowired
  private IUsuariosServ usuarioServ;;
  @Autowired
  private IParametricaImpl parametricaServ;;
  
  @Autowired
  private Auxiliar aux;
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private IShopifyServ shopifyServ;
  
  @ApiResponses(value = {@ApiResponse(code = 200, message = "La solicitud enviada es correcta...", response = Error.class),
                         @ApiResponse(code = 400, message = "La peticion de datos no coincide sus valores..", response = Error.class),
                         @ApiResponse(code = 404, message = "La solicitud envieda no coincide con los datos requeridos..", response = Error.class),
                         @ApiResponse(code = 500, message = "Error en el servicio de la peticion de datos", response = Error.class)})
  @ApiOperation(value = "Consumo")
  @PostMapping
  public ResponseEntity<?> consumo(@RequestBody Application data,
                                   @RequestParam String ptid,
                                   @RequestHeader Map<String, Object> headers) throws InterruptedException,
                                                                               JsonParseException,
                                                                               JsonMappingException,
                                                                               JsonGenerationException,
                                                                               IOException {
    logger.info("============================ SHOPIFY PRIMER ENDPOINT ============================");
    Map<String, Object> response = new HashMap<>( );
    Clientes cliente = new Clientes( );
    Personas persona = new Personas( );
    Recibo recibo = new Recibo( );
    Link link = new Link( );
    String nit = null;
    Deuda deuda = new Deuda( );
    Parametrica para = null;
    Map<String, Object> claims = new HashMap<>( );
    List<Detalle> detalleList = new ArrayList<Detalle>( );
    ObjectMapper oMapper = new ObjectMapper( );
    String url = null;
    try {
      logger.info(oMapper.writeValueAsString(data));
    }
    catch(Exception e) {
      e.printStackTrace( );
      logger.error("Error al convertir..");
    }
    for(NoteAttribute nota: data.getNote_attributes( )) {
      if (nota.name.equals("nit")) nit = nota.value;
    }
    try {
      if (data.getBrowser_ip( ) == null || data.getBrowser_ip( )
                                               .equals("0")) {
        Log.error("browser :: " + data.getBrowser_ip( ));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
      }
    }
    catch(Exception e) {
      e.printStackTrace( );
      logger.error("Error al browser");
    }
    headers.forEach((key,
                     value) ->
    { logger.info(String.format("Header '%s' = %s", key, value)); });
    Claims verificar = null;
    try {
      verificar = aux.decodeJWT(ptid);
      Date expiracion = null, date = new Date( );
      if (verificar.getExpiration( ) != null) {
        expiracion = verificar.getExpiration( );
      }else if (verificar.getIssuedAt( ) != null) { expiracion = verificar.getIssuedAt( ); }
      response.put("Fecha expiracion", aux.ConvertDateString(expiracion, "dd/MM/yyyy HH:mm:ss"));
      if (expiracion.getTime( ) >= date.getTime( )) { response.put("mensaje", "Token valido"); }
      
    }
    catch(Exception e) {
      response.put("mensaje", "Token invalido");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    Optional<Link> obtener = null;
    try {
      obtener = linkServ.obtenerLinkShopify(data.getId( )
                                                .toString( ));
      if (obtener.isPresent( )) {
        logger.info("Se repite el JSON..!!");
        response.put("data", "Se repite el JSON..!!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(response);
      }
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    try {
      String[ ] aux = data.getOrder_status_url( )
                          .split("\\.");
      if (aux.length <= 2) {
        url = aux[0];
      }else {
        url = aux[0] + "." + aux[1];
      }
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    try {
      para = parametricaServ.obtener("TIPO", "CALLBACK", url);
    }
    catch(Exception e) {
      logger.error("Error al obtener parametrica shopify..");
    }
    try {
      cliente = clienteServ.buscarClienteEmpresa("0", Long.valueOf(para.getDescripcion( )))
                           .get( );
    }
    catch(Exception e) {
      logger.error("Error al obtener cliente shopify..");
    }
    recibo.setNro_recibo(Long.valueOf(data.getOrder_number( )));
    recibo.setDescripcion_general("Pago en linea");
    recibo.setConcepto_recibo("");
    recibo.setGlosa1(nit);
    recibo.setGlosa2(data.getCustomer( )
                         .getEmail( ));
    recibo.setGlosa4(para.getValor( ) + data.getId( ) + "/transactions.json");
    recibo.setCodigo_pago("SHOPIFY");
    recibo.setEstado("PEN");
    recibo.setUsuario_alta("Shopify");
    recibo.setMoneda(data.getCurrency( )
                         .equals("BOB") ? 1 : 2);
    recibo.setMonto(new BigDecimal(data.getTotal_outstanding( )));
    recibo.setNombre_apellido(data.getId( ) + ";" + data.getCustomer( )
                                                        .getFirst_name( ) + ";" + data.getCustomer( )
                                                                                      .getLast_name( )
      + "; Sin datos" + ";" + data.getCustomer( )
                                  .getEmail( ) + ";");
    recibo.setSuscripcion(data.getCustomer( )
                              .getFirst_name( ) + " " + data.getCustomer( )
                                                            .getLast_name( ));
    try {
      recibo.setFecha_vencimiento(aux.conversionStringDate(aux.ConversionDateString(aux.sumarDiasAFecha(
        new Date( ), 4), "dd/MM/yyyy") + " 23:59:59", "dd/MM/yyyy hh:mm:ss"));
    }
    catch(Exception e1) {
      logger.error("error en la fecha");
      return ResponseEntity.status(HttpStatus.OK)
                           .body(response);
    }
    try {
      // GUARDA LA INFORMACION DEL NUEVO RECIBO CON DETALLE
      recibo = reciboServ.save(recibo);
    }
    catch(Exception e) {
      e.printStackTrace( );
      logger.error("Error recibo ");
    }
    for(LineItem lineItem: data.getLine_items( )) {
      Detalle d = new Detalle( );
      d.setEstado("PEN");
      d.setIdrecibo(recibo);
      d.setCodItem(lineItem.getSku( ));
      d.setDescripcion_item(lineItem.getName( ));
      d.setItem(lineItem.getVendor( ));
      d.setCantidad(lineItem.getQuantity( ));
      d.setPrecio_unitario(new BigDecimal(lineItem.getPrice( )));
      d.setSub_total(new BigDecimal(d.getCantidad( )).multiply(d.getPrecio_unitario( )));
      BigDecimal desc = lineItem.getTotal_discount( )
                                .isEmpty( ) ? new BigDecimal(0) : new BigDecimal(
                                  lineItem.getTotal_discount( ));
      d.setDescuento(desc);
      d.setTotal(d.getSub_total( )
                  .subtract(d.getDescuento( )));
      detalleList.add(d);
    }
    Detalle d = new Detalle( );
    d.setEstado("PEN");
    d.setIdrecibo(recibo);
    d.setDescripcion_item("Envio de producto");
    d.setItem("Delivery");
    d.setCantidad(1);
    d.setPrecio_unitario(new BigDecimal(
      data.getTotal_shipping_price_set( )
          .getShop_money( )
          .getAmount( )));
    d.setSub_total(new BigDecimal(1).multiply(d.getPrecio_unitario( )));
    d.setDescuento(new BigDecimal(0));
    d.setTotal(d.getSub_total( )
                .subtract(d.getDescuento( )));
    detalleList.add(d);
    if (data.getDiscount_applications( )
            .size( ) > 0) {
      ArrayList<DiscountApplication> disc = data.getDiscount_applications( );
      for(int i = 0;i < disc.size( );i++) {
        DiscountApplication app = disc.get(i);
        if (!app.getType( )
                .equals("script")) {
          BigDecimal aux = new BigDecimal(0);
          if (app.getValue_type( )
                 .equals("percentage")) {
            BigDecimal numerador = new BigDecimal(app.getValue( ));
            BigDecimal denominador = new BigDecimal(100);
            aux = recibo.getMonto( )
                        .multiply(numerador.divide(denominador));
          }else {
            aux = new BigDecimal(app.getValue( ));
          }
          d = new Detalle( );
          d.setCantidad(i + 1);
          d.setDescripcion_item(app.getTitle( ));
          d.setCodItem(app.getType( ));
          d.setDescuento(new BigDecimal(0));
          d.setEstado("PEN");
          d.setIdrecibo(recibo);
          d.setItem("Item Descuento");
          d.setPrecio_unitario(aux);
          d.setSub_total(aux);
          d.setTotal(aux.subtract(d.getDescuento( )));
        }
        detalleList.add(d);
      }
    }
    deuda.setIdrecibo(recibo);
    deuda.setIdcliente(cliente);
    deuda.setEstado("PEN");
    deuda.setUsuario_alta("Shopify");
    try {
      deuda = deudaServ.save(deuda);
      detalleServ.saveAll(detalleList);
    }
    catch(Exception ex) {
      logger.error("Error en el detalle ");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    String linkPago = URL + ":" + PORT + "/#/carrito?id=";
    String jwtId = String.valueOf(cliente.getIdempresa( )
                                         .getIdempresa( ));
    String jwtIssuer = String.valueOf(deuda.getIddeuda( ));
    String jwtSubject = data.getToken( );
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
    response.put("URL", linkPago + link.getToken( ));
    logger.info("============================ TERMINA SHOPIFY PRIMER ENDPOINT ============================");
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
  @ApiResponses(value = {@ApiResponse(code = 200, message = "La solicitud enviada es correcta...", response = Error.class),
                         @ApiResponse(code = 400, message = "La peticion de datos no coincide sus valores..", response = Error.class),
                         @ApiResponse(code = 404, message = "La solicitud envieda no coincide con los datos requeridos..", response = Error.class),
                         @ApiResponse(code = 500, message = "Error en el servicio de la peticion de datos", response = Error.class)})
  @ApiOperation(value = "Consumo")
  @GetMapping("/obtener")
  public ResponseEntity<?> obtener(@RequestParam String id,
                                   @RequestParam(defaultValue = "tj") String tp) throws InterruptedException {
    logger.info("============================ SHOPIFY SEGUNDO ENDPOINT ============================");
    Map<String, Object> response = new HashMap<>( );
    logger.info("============================ TERMINA SHOPIFY SEGUNDO ENDPOINT ============================");
    // return new RedirectView(URL + ":" + PORT + "/#/shopify/" + id, true);
    response.put("url", URL + ":" + PORT + "/#/shopify/" + id + "/" + tp);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
  @ApiResponses(value = {@ApiResponse(code = 200, message = "La solicitud enviada es correcta...", response = Error.class),
                         @ApiResponse(code = 400, message = "La peticion de datos no coincide sus valores..", response = Error.class),
                         @ApiResponse(code = 404, message = "La solicitud envieda no coincide con los datos requeridos..", response = Error.class),
                         @ApiResponse(code = 500, message = "Error en el servicio de la peticion de datos", response = Error.class)})
  @ApiOperation(value = "webkul")
  @PostMapping(value = "/webkul")
  public ResponseEntity<?> webkul(@RequestBody String root,
                                  @RequestParam String token,
                                  @RequestParam String tipo) throws InterruptedException,
                                                             JSONException,
                                                             UnsupportedEncodingException {
    logger.info("********************** SHOPIFY WEBKUL ENDPOINT **********************");
    Map<String, Object> resp = new HashMap<>( );
    logger.info("********************** 4 **********************");
    // logger.info(root);
    String data = "";
    logger.info("********************** 4 **********************");
    System.out.println(new String(data.getBytes( ), "UTF-8"));
    // logger.info(token);
    // logger.info(tipo);
    if (!usuarioServ.verificarToken(token)) {
      logger.info("Denegado el token");
    }else {
      // String URL = "https://mvmapi.webkul.com/api/v2/orders/" + root.getOrder( )
      // .getId( ) + ".json";
      // HttpHeaders headers = new HttpHeaders( );
      // headers.set("Authorization",
      // "Bearer MjM1MjlhYTM2MmY4MjlhYWEyM2Y3ZGZkYTY0NDljODI4MjRmMTMwNmVhMTAzYjdmOGM0NWU3NDc4MzY5ZmUxYQ");
      // HttpEntity<String> entity = new HttpEntity<String>(headers);
      // Root2 responseEntity = restTemplate.exchange(URL, HttpMethod.GET, entity, Root2.class)
      // .getBody( );
      // logger.info(reciboS.CallbacKAuthenticationBasic(shopifyServ.obtenerId(responseEntity.getOrder( )
      // .getId_shop( ))
      // .get( ), responseEntity.getOrder( )
      // .getRef_order_id( ),
      // "Data")
      // .toString( ));
    }
    logger.info("********************** TERMINA SHOPIFY WEBKUL ENDPOINT **********************");
    return ResponseEntity.status(HttpStatus.OK)
                         .body(resp);
  }
  
  public Detalle discount_applications(Detalle d,
                                       Recibo r,
                                       ArrayList<DiscountApplication> disc,
                                       BigDecimal total) {
    
    return d;
  }
}
