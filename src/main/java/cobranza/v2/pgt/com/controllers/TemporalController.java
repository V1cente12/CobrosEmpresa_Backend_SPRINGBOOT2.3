
package cobranza.v2.pgt.com.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cobranza.v2.pgt.com.utils.otros.AnulacionFactura;
import cobranza.v2.pgt.com.utils.otros.ClasesAux;
import cobranza.v2.pgt.com.utils.otros.Content;
import cobranza.v2.pgt.com.utils.otros.EventoSignificativo;
import cobranza.v2.pgt.com.utils.otros.FacturaEnvioEmailDto;
import cobranza.v2.pgt.com.utils.otros.ReporteFacturacion;
import cobranza.v2.pgt.com.utils.otros.Response;
import cobranza.v2.pgt.com.utils.otros.SolicitudFacturaAnulada;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2/temporal")
public class TemporalController {
  
  private static final Logger log = LoggerFactory.getLogger(TemporalController.class);
  
  @Autowired
  private RestTemplate restTemplate;
  
  @Value("${url.temporal}")
  private String _URL;
  
  @Value("${url.cert}")
  private String _URLCert;
  
  @Value("${url.facturacion}")
  private String _URLFact;
  
  @GetMapping(value = "/emitidas")
  public ResponseEntity<?> FacturasEmitidas(@RequestParam String nit,
                                            @RequestParam String operacion,
                                            @RequestParam String codigo,
                                            @RequestParam String fechaI,
                                            @RequestParam String fechaF) throws ParseException {
    String url = _URL + "/factura/emitidas?nit=" + nit + "&operacion=" + operacion + "&codigo=" + codigo
      + "&fechaI=" + fechaI + "&fechaF=" + fechaF;
    log.info(url);
    ResponseEntity<Content[ ]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      Content[ ].class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/page/emitidas")
  public ResponseEntity<?> FacturasEmitidasPage(@RequestParam String nit,
                                                @RequestParam String operacion,
                                                @RequestParam String codigo,
                                                @RequestParam String fechaI,
                                                @RequestParam String fechaF,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) throws ParseException {
    String url = _URL + "/factura/page/emitidas?nit=" + nit + "&operacion=" + operacion + "&codigo=" + codigo
      + "&fechaI=" + fechaI + "&fechaF=" + fechaF + "&size=" + size + "&page=" + page;
    // String url = "http://localhost:9003/factura/page/emitidas?nit=" + nit + "&operacion=" + operacion
    // + "&codigo=" + codigo + "&fechaI=" + fechaI + "&fechaF=" + fechaF + "&size=" + size + "&page=" + page;
    log.info(url);
    ResponseEntity<Response> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      Response.class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/filter/emitidas")
  public ResponseEntity<?> FacturasEmitidasFilterPage(@RequestParam String nit,
                                                      @RequestParam String operacion,
                                                      @RequestParam String codigo,
                                                      @RequestParam String fechaI,
                                                      @RequestParam String fechaF,
                                                      @RequestParam(defaultValue = "") String razonsocial,
                                                      @RequestParam(defaultValue = "") String nrofactura,
                                                      @RequestParam(defaultValue = "") String cuf,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "50") int size) throws ParseException {
    String url = _URL + "/factura/filter/emitidas?nit=" + nit + "&operacion=" + operacion + "&codigo="
      + codigo + "&fechaI=" + fechaI + "&fechaF=" + fechaF + "&size=" + size + "&page=" + page
      + "&razonsocial=" + razonsocial + "&nrofactura=" + nrofactura + "&cuf=" + cuf;
    // String url = "http://localhost:9003/factura/page/emitidas?nit=" + nit + "&operacion=" + operacion
    // + "&codigo=" + codigo + "&fechaI=" + fechaI + "&fechaF=" + fechaF + "&size=" + size + "&page=" + page;
    log.info(url);
    ResponseEntity<Response> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      Response.class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/anuladas")
  public ResponseEntity<?> FacturasAnuladas(@RequestParam String nit,
                                            @RequestParam String operacion,
                                            @RequestParam String codigo,
                                            @RequestParam String fechaI,
                                            @RequestParam String fechaF) throws ParseException {
    String url = _URL + "/factura/anuladas?nit=" + nit + "&operacion=" + operacion + "&codigo=" + codigo
      + "&fechaI=" + fechaI + "&fechaF=" + fechaF;
    ResponseEntity<Content[ ]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      Content[ ].class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/page/anuladas")
  public ResponseEntity<?> FacturasAnuladasPage(@RequestParam String nit,
                                                @RequestParam String operacion,
                                                @RequestParam String codigo,
                                                @RequestParam String fechaI,
                                                @RequestParam String fechaF,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) throws ParseException {
    String url = _URL + "/factura/page/anuladas?nit=" + nit + "&operacion=" + operacion + "&codigo=" + codigo
      + "&fechaI=" + fechaI + "&fechaF=" + fechaF + "&size=" + size + "&page=" + page;
    ResponseEntity<Response> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      Response.class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/filter/anuladas")
  public ResponseEntity<?> FacturasAnuladasFilterPage(@RequestParam String nit,
                                                      @RequestParam String operacion,
                                                      @RequestParam String codigo,
                                                      @RequestParam String fechaI,
                                                      @RequestParam String fechaF,
                                                      @RequestParam(defaultValue = "") String razonsocial,
                                                      @RequestParam(defaultValue = "") String nrofactura,
                                                      @RequestParam(defaultValue = "") String cuf,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) throws ParseException {
    String url = _URL + "/factura/filter/anuladas?nit=" + nit + "&operacion=" + operacion + "&codigo="
      + codigo + "&fechaI=" + fechaI + "&fechaF=" + fechaF + "&size=" + size + "&page=" + page
      + "&razonsocial=" + razonsocial + "&nrofactura=" + nrofactura + "&cuf=" + cuf;
    ResponseEntity<Response> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      Response.class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/rechazadas")
  public ResponseEntity<?> FacturasRechazadas(@RequestParam String nit,
                                              @RequestParam String operacion,
                                              @RequestParam String codigo,
                                              @RequestParam String fechaI,
                                              @RequestParam String fechaF) throws ParseException {
    String url = _URL + "/factura/rechazadas?nit=" + nit + "&operacion=" + operacion + "&codigo=" + codigo
      + "&fechaI=" + fechaI + "&fechaF=" + fechaF;
    ResponseEntity<Content[ ]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      Content[ ].class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/page/rechazadas")
  public ResponseEntity<?> FacturasRechazadasPage(@RequestParam String nit,
                                                  @RequestParam String operacion,
                                                  @RequestParam String codigo,
                                                  @RequestParam String fechaI,
                                                  @RequestParam String fechaF,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) throws ParseException {
    String url = _URL + "/factura/page/rechazadas?nit=" + nit + "&operacion=" + operacion + "&codigo="
      + codigo + "&fechaI=" + fechaI + "&fechaF=" + fechaF + "&size=" + size + "&page=" + page;
    ResponseEntity<Response> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      Response.class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/filter/rechazadas")
  public ResponseEntity<?> FacturasRechazadasFilterPage(@RequestParam String nit,
                                                        @RequestParam String operacion,
                                                        @RequestParam String codigo,
                                                        @RequestParam String fechaI,
                                                        @RequestParam String fechaF,
                                                        @RequestParam(defaultValue = "") String razonsocial,
                                                        @RequestParam(defaultValue = "") String nrofactura,
                                                        @RequestParam(defaultValue = "") String cuf,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) throws ParseException {
    String url = _URL + "/factura/filter/rechazadas?nit=" + nit + "&operacion=" + operacion + "&codigo="
      + codigo + "&fechaI=" + fechaI + "&fechaF=" + fechaF + "&size=" + size + "&page=" + page
      + "&razonsocial=" + razonsocial + "&nrofactura=" + nrofactura + "&cuf=" + cuf;
    ResponseEntity<Response> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      Response.class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/registros")
  public ResponseEntity<?> FacturasRegistros(@RequestParam String nit,
                                             @RequestParam String estado,
                                             @RequestParam String fechaI,
                                             @RequestParam String fechaF) throws ParseException {
    String url = _URL + "/factura/registros?nit=" + nit + "&estado=" + estado + "&fechaI=" + fechaI
      + "&fechaF=" + fechaF;
    ResponseEntity<EventoSignificativo[ ]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      EventoSignificativo[ ].class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/page/registros")
  public ResponseEntity<?> FacturasRegistrosPage(@RequestParam String nit,
                                                 @RequestParam String estado,
                                                 @RequestParam String fechaI,
                                                 @RequestParam String fechaF,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) throws ParseException {
    String url = _URL + "/factura/page/registros?nit=" + nit + "&estado=" + estado + "&fechaI=" + fechaI
      + "&fechaF=" + fechaF + "&size=" + size + "&page=" + page;
    ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/cufd")
  public ResponseEntity<?> FacturasCufd(@RequestParam String nit,
                                        @RequestParam String fechaI,
                                        @RequestParam String fechaF) throws ParseException {
    String url = _URL + "/factura/cufd?nit=" + nit + "&fechaI=" + fechaI + "&fechaF=" + fechaF;
    ResponseEntity<ClasesAux[ ]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      ClasesAux[ ].class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/page/cufd")
  public ResponseEntity<?> FacturasCufdPage(@RequestParam String nit,
                                            @RequestParam String fechaI,
                                            @RequestParam String fechaF,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "50") int size) throws ParseException {
    String url = _URL + "/factura/page/cufd?nit=" + nit + "&fechaI=" + fechaI + "&fechaF=" + fechaF + "&size="
      + size + "&page=" + page;
    ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/filter/cufd")
  public ResponseEntity<?> FacturasCufdFilterPage(@RequestParam String nit,
                                                  @RequestParam String fechaI,
                                                  @RequestParam String fechaF,
                                                  @RequestParam(defaultValue = "") String codsucursal,
                                                  @RequestParam(defaultValue = "") String codpuntoventa,
                                                  @RequestParam(defaultValue = "") String municipio,
                                                  @RequestParam(defaultValue = "") String cuf,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "50") int size) throws ParseException {
    String url = _URL + "/factura/filter/cufd?nit=" + nit + "&fechaI=" + fechaI + "&fechaF=" + fechaF
      + "&size=" + size + "&page=" + page + "&codsucursal=" + codsucursal + "&codpuntoventa=" + codpuntoventa
      + "&municipio=" + municipio + "&cuf=" + cuf;
    ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/cuis")
  public ResponseEntity<?> FacturasCuis(@RequestParam String nit,
                                        @RequestParam String fechaI,
                                        @RequestParam String fechaF) throws ParseException {
    String url = _URL + "/factura/cuis?nit=" + nit + "&fechaI=" + fechaI + "&fechaF=" + fechaF;
    ResponseEntity<ClasesAux[ ]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      ClasesAux[ ].class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/page/cuis")
  public ResponseEntity<?> FacturasCuisPage(@RequestParam String nit,
                                            @RequestParam String fechaI,
                                            @RequestParam String fechaF,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) throws ParseException {
    String url = _URL + "/factura/page/cuis?nit=" + nit + "&fechaI=" + fechaI + "&fechaF=" + fechaF + "&size="
      + size + "&page=" + page;
    ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/filter/cuis")
  public ResponseEntity<?> FacturasCuisFilterPage(@RequestParam String nit,
                                                  @RequestParam String fechaI,
                                                  @RequestParam String fechaF,
                                                  @RequestParam(defaultValue = "") String codsucursal,
                                                  @RequestParam(defaultValue = "") String codpuntoventa,
                                                  @RequestParam(defaultValue = "") String municipio,
                                                  @RequestParam(defaultValue = "") String cuf,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) throws ParseException {
    String url = _URL + "/factura/filter/cuis?nit=" + nit + "&fechaI=" + fechaI + "&fechaF=" + fechaF
      + "&size=" + size + "&page=" + page + "&codsucursal=" + codsucursal + "&codpuntoventa=" + codpuntoventa
      + "&municipio=" + municipio + "&cuf=" + cuf;
    ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @PostMapping(value = "/save/solfacanu")
  public ResponseEntity<?> SaveSoliFactAnul(@RequestBody SolicitudFacturaAnulada entity) throws ParseException {
    Map<String, Object> response = new HashMap<>( );
    String url = _URL + "/factura/save/solfacanu";
    HttpHeaders headers = new HttpHeaders( );
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<SolicitudFacturaAnulada> request = new HttpEntity<>(entity, headers);
    ResponseEntity<String> responseEntity = null;
    try {
      responseEntity = restTemplate.postForEntity(url, request, String.class);
    }
    catch(Exception e) {
      log.error(
        "************ SE OCACIONO UN ERROR AL MOMENTO DE GUARDAR LA FACTURA PARA SU ANULACION ***********");
      e.printStackTrace( );
    }
    response.put("data", responseEntity.getBody( ));
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
  @PostMapping(value = "/anulacion")
  public ResponseEntity<?> AnulacionFactura(@RequestBody AnulacionFactura entity) throws ParseException {
    Map<String, Object> response = new HashMap<>( );
    String url = _URLFact + "/api/facturacion/electronica/anulacion-factura";
    System.out.println(url);
    HttpHeaders headers = new HttpHeaders( );
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.add("Authorization", "Bearer " + entity.getToken( ));
    HttpEntity<AnulacionFactura> request = new HttpEntity<>(entity, headers);
    ResponseEntity<String> responseEntity = null;
    try {
      responseEntity = restTemplate.postForEntity(url, request, String.class);
    }
    catch(Exception e) {
      String msj = "SE OCACIONO UN ERROR AL MOMENTO DE ANULAR LA FACTURA";
      log.error(msj);
      e.printStackTrace( );
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(msj);
    }
    response.put("data", responseEntity.getBody( ));
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
  @PostMapping("/enviarcorreofactura")
  public ResponseEntity<?> enviarCorreo(@RequestBody FacturaEnvioEmailDto data) throws Exception {
    String url = _URLCert + "/api/v2/correo/enviarcorreofactura";
    log.info(data.toString( ));
    HttpHeaders headers = new HttpHeaders( );
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<FacturaEnvioEmailDto> request = new HttpEntity<>(data, headers);
    ResponseEntity<String> responseEntity = null;
    try {
      responseEntity = restTemplate.postForEntity(url, request, String.class);
    }
    catch(Exception e) {
      log.error("************ SE OCACIONO UN ERROR DE ENVIO DE CORREO ***********");
      e.printStackTrace( );
    }
    log.info(responseEntity.toString( ));
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity);
  }
  
  @GetMapping(value = "/facturacion")
  public ResponseEntity<?> ReporteFacturacion(@RequestParam String nit,
                                              @RequestParam("codigo") String codigo,
                                              @RequestParam String fechaI,
                                              @RequestParam String fechaF) throws ParseException {
    String url = _URL + "/factura/facturacion?nit=" + nit + "&fechaI=" + fechaI + "&fechaF=" + fechaF
      + " 23:59:59" + "&codigo=" + codigo;
    log.info(url);
    ResponseEntity<List<ReporteFacturacion>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      new ParameterizedTypeReference<List<ReporteFacturacion>>( ) {
      });
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/lista/csv")
  public ResponseEntity<?> ListaCsvArchivo(@RequestParam String nit,
                                           @RequestParam(defaultValue = "") String fechaI,
                                           @RequestParam(defaultValue = "") String fechaF,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) throws ParseException {
    String url = _URL + "/factura/lista/csv?nit=" + nit + "&fechaI=" + fechaI + "&fechaF=" + fechaF + "&size="
      + size + "&page=" + page;
    System.out.println(url);
    ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(responseEntity.getBody( ));
  }
  
  @GetMapping(value = "/descargar/csv/comercio")
  public ResponseEntity<Resource> download(@RequestParam(name = "dir") String dir) throws IOException {
    String url = _URL + "/factura/descargar/csv/comercio?dir=" + dir;
    System.out.println(url);
    ResponseEntity<ByteArrayResource> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      ByteArrayResource.class);
    return ResponseEntity.ok( )
                         .contentType(new MediaType("text", "csv"))
                         .body(responseEntity.getBody( ));
  }
}
