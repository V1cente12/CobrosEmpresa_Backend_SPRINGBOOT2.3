
package cobranza.v2.pgt.com.controllers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.entity.Comision;
import cobranza.v2.pgt.com.models.services.IComisionServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2/comision")
public class ComisionController {
  
  private static final Logger log = LoggerFactory.getLogger(ComisionController.class);
  
  @Autowired
  private IComisionServ comisionS;
  
  @GetMapping("/")
  public ResponseEntity<?> comisiones(@RequestParam(value = "estado", defaultValue = "A") String estado) {
    Map<String, Object> response = new HashMap<>( );
    List<Comision> comisiones = comisionS.obtenerComisiones(estado);
    response.put("Data", comisiones);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  @GetMapping("/empresa")
  public ResponseEntity<?> empresa(@RequestParam("idempresa") String idempresa,
                                   @RequestParam(value = "estado", defaultValue = "A") String estado) {
    Map<String, Object> response = new HashMap<>( );
    response.put("Data", comisionS.BuscarIdempresaNullvendor(Long.valueOf(idempresa), estado));
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
  @GetMapping("/vendor")
  public ResponseEntity<?> vendor(@RequestParam("vendor") String vendor,
                                  @RequestParam(value = "estado", defaultValue = "A") String estado) {
    Map<String, Object> response = new HashMap<>( );
    response.put("Data", comisionS.BuscarVendor(vendor, estado));
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
  // @PostMapping
  // public ResponseEntity<?> guardar(@RequestBody Comision entity) {
  // Map<String, Object> response = new HashMap<>( );
  // response.put("Data", comisionS.guardar(entity));
  // return ResponseEntity.status(HttpStatus.OK)
  // .body(response);
  // }
  
  @PostMapping
  public String guardar2(@RequestParam String id) {
    List<Long> list = Arrays.stream(id.split(";"))
                            .map(s -> Long.parseLong(s.trim( )))
                            .collect(Collectors.toList( ));
    System.out.println(list.size( ));
    for(int i = 0;i < list.size( );i++) {
      Comision c = new Comision( );
      c.setAmexInterAtc(BigDecimal.ZERO);
      c.setAmexInterPgt(BigDecimal.ZERO);
      c.setAmexNacAtc(BigDecimal.ZERO);
      c.setAmexNacPgt(BigDecimal.ZERO);
      c.setEstado("A");
      c.setIdempresa(list.get(i));
      c.setMontoIntegracion(BigDecimal.ZERO);
      c.setQrBanco(new BigDecimal("0.005"));
      c.setQrPgt(new BigDecimal("0.005"));
      c.setTarjetaInterAtc(new BigDecimal("0.015"));
      c.setTarjetaInterPgt(new BigDecimal("0.015"));
      c.setTarjetaNacAtc(new BigDecimal("0.015"));
      c.setTarjetaNacPgt(new BigDecimal("0.01"));
      c.setUsuarioAlta("A");
      System.out.println(c.toString( ));
      comisionS.guardar(c);
    }
    return "termino";
  }
}
