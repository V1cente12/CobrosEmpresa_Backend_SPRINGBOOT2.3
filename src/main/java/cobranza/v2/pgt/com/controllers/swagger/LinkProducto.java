
package cobranza.v2.pgt.com.controllers.swagger;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
import cobranza.v2.pgt.com.models.entity.Input;
import cobranza.v2.pgt.com.models.entity.Link;
import cobranza.v2.pgt.com.models.entity.PagoRestApi;
import cobranza.v2.pgt.com.models.entity.Personas;
import cobranza.v2.pgt.com.models.entity.Recibo;
import cobranza.v2.pgt.com.models.services.IUsuariosServ;
import cobranza.v2.pgt.com.utils.Auxiliar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("linkproducto")
@Api(value = "Pago", description = "Operacion link de producto por Pagatodo360")
public class LinkProducto {
  
  private Logger logger = LoggerFactory.getLogger(PagoRest.class);
  
  @Value("#{'${name.url}'}")
  private String URL;
  
  @Value("#{'${name.port}'}")
  private String PORT;
  @Value("#{'${SAPECommerce}'}")
  private String SAPECommerce;
  
  @Autowired
  private IReciboDao reciboServ;
  
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
  
  @Value("#{'${empresa.daher}'}")
  private String DAHER;
  @Value("#{'${empresa.id.addiuva}'}")
  private String ADDIUVA;
  @Value("#{'${empresa.id.getserver}'}")
  private String GETSERVER;
  @Value("#{'${empresa.phmedical}'}")
  private String Phmedical;
  
  @Autowired
  @Qualifier("simpleRestTemplate")
  private RestTemplate simpleRestTemplate;
  
  @ApiResponses(value = {@ApiResponse(code = 200, message = "La solicitud enviada es correcta...", response = Error.class),
                         @ApiResponse(code = 400, message = "La peticion de datos no coincide sus valores..", response = Error.class),
                         @ApiResponse(code = 404, message = "La solicitud envieda no coincide con los datos requeridos..", response = Error.class),
                         @ApiResponse(code = 500, message = "Error en el servicio de la peticion de datos", response = Error.class)})
  @ApiOperation(value = "Crea pago")
  @PostMapping(name = "linkproducto")
  public ResponseEntity<?> create(@RequestBody Input encrypt,
                                  BindingResult bindingResult) throws Exception {
    Map<String, Object> response = new HashMap<>( );
    String secretKey = "mustbe16byteskey";
    String encodedBase64Key = encodeKey(secretKey);
    // System.out.println("EncodedBase64Key = " + encodedBase64Key);
    // String toDecodeBase64Key = decodeKey(encodedBase64Key);
    // System.out.println("toDecodeBase64Key = " + toDecodeBase64Key);
    // String encrStr = this.encrypt(toEncrypt, encodedBase64Key);
    // System.out.println(encrStr);
    // System.out.println(encrypt);
    String decrStr = this.decrypt(encrypt.getData( ), encodedBase64Key);
    // System.out.println("Decryption of str = " + decrStr);
    
    Map<String, Object> claims = new HashMap<>( );
    ObjectMapper oMapper = new ObjectMapper( );
    Clientes cliente = new Clientes( );
    Personas persona = new Personas( );
    Recibo recibo = new Recibo( );
    Link link = new Link( );
    Deuda deuda = new Deuda( );
    PagoRestApi data = oMapper.readValue(decrStr, PagoRestApi.class);
    try {
      logger.info(oMapper.writeValueAsString(data));
    }
    catch(JsonProcessingException e2) {
      e2.printStackTrace( );
    }
    List<FieldError> errors = bindingResult.getFieldErrors( );
    System.out.println("====================");
    response = this.aux.validarBody(errors, data, response, bindingResult);
    System.out.println("====================");
    if (( boolean ) response.get("error")) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                                .body(response);
    System.out.println("====================");
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
    List<Detalle> detalleList = new ArrayList<Detalle>( );
    
    // CAMPO FIELDS APELLIDO PATERNO OBLIGATORIO
    if (data.getApellido_paterno( )
            .length( ) <= 2) {
      response.put("mensaje", "Se requiere el apellido paterno de la persona");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    
    // COMPARA SI EXISTE EL NRO DE PEDIDO
    // if (reciboServ.ExisteNroPedido(data.getNro_recibo( ), data.getIdempresa( )) != null) {
    //
    // response.put("mensaje", "Numero de pedido ya existente en el registro de la empresa " + data
    // .getNro_recibo( ));
    // return ResponseEntity.status(HttpStatus.NOT_FOUND)
    // .body(response);
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
      persona.setDomicilio(data.getDomicilio( ));
    }else {
      // PERSONA EXISTENTE
      logger.info("Persona exitente");
      personanueva.setNombres(data.getNombres( ));
      personanueva.setApellido_paterno(data.getApellido_paterno( ));
      personanueva.setApellido_materno(data.getApellido_materno( ) == "string" ? "" : data
                                                                                          .getApellido_materno( ));
      personanueva.setTipo_documento(data.getTipo_documento( ));
      personanueva.setDomicilio(data.getDomicilio( ));
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
    
    // PDATA_4 RECIBE LA FECHA DE VENCIMIENTO
    try {
      
      // SI NO HAY DATOS SE INGRESA LA FECHA ACTUAL +1 DIA
      if (data.getPData04( ) == null || data.getPData04( )
                                            .equals("") || data.getPData04( )
                                                               .equals("string")) {
        data.setPData04(aux.ConversionDateString(aux.sumarDiasAFecha(new Date( ), 1), "dd/MM/yyyy"));
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
    recibo.setNombre_apellido(data.getValor_documento( ) + ";" + data.getNombres( ) + ";" + data
                                                                                                .getApellido_paterno( )
      + ";" + data.getDomicilio( ) + ";" + data.getCorreo( ) + ';' + data.getPData09( ));
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
      response.put("URL", linkPago + link.getToken( ));
      logger.info("URL :: " + linkPago + link.getToken( ));
    }
    catch(Exception e) {
      response.put("mensaje", "Error en generar el link de pago!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
  private static final String ALGO = "AES"; // Default uses ECB PKCS5Padding
  
  public static String encrypt(String Data,
                               String secret) throws Exception {
    Key key = generateKey(secret);
    Cipher c = Cipher.getInstance(ALGO);
    c.init(Cipher.ENCRYPT_MODE, key);
    byte[ ] encVal = c.doFinal(Data.getBytes( ));
    String encryptedValue = Base64.getEncoder( )
                                  .encodeToString(encVal);
    return encryptedValue;
  }
  
  public static String decrypt(String strToDecrypt,
                               String secret) throws Exception {
    Key key = generateKey(secret);
    Cipher cipher = Cipher.getInstance(ALGO);
    cipher.init(Cipher.DECRYPT_MODE, key);
    try {
      return new String(
        cipher.doFinal(Base64.getDecoder( )
                             .decode(strToDecrypt.getBytes(StandardCharsets.UTF_8))));
    }
    catch(Exception e) {
      System.out.println("Error while decrypting: " + e.toString( ));
    }
    return null;
  }
  
  private static Key generateKey(String secret) throws Exception {
    byte[ ] decoded = Base64.getDecoder( )
                            .decode(secret.getBytes( ));
    Key key = new SecretKeySpec(decoded, ALGO);
    return key;
  }
  
  public static String decodeKey(String str) {
    byte[ ] decoded = Base64.getDecoder( )
                            .decode(str.getBytes( ));
    return new String(decoded);
  }
  
  public static String encodeKey(String str) {
    byte[ ] encoded = Base64.getEncoder( )
                            .encode(str.getBytes( ));
    return new String(encoded);
  }
}
