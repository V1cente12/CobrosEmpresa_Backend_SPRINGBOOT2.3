
package cobranza.v2.pgt.com.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.services.ICanalPagoServ;
import cobranza.v2.pgt.com.models.services.IEmpresaServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2/canalpago")
public class CanalPagoController {
  
  @Autowired
  private ICanalPagoServ canalServ;
  @Autowired
  private IEmpresaServ empresaServ;
  
  @PostMapping
  public ResponseEntity<?> guardarcanalPago(@RequestParam("idempresa") String idempresa,
                                            @RequestParam("listpago") String listpago,
                                            @RequestParam("valorqr") String valorqr,
                                            @RequestParam("usuario") String usuario) {
    Map<String, Object> response = new HashMap<>( );
    List<String> myList = new ArrayList<String>(Arrays.asList(listpago.split(",")));
    Empresas empresa = null;
    try {
      empresa = empresaServ.listarID("A", Long.valueOf(idempresa));
    }
    catch(Exception e) {}
    if (canalServ.crearPago(empresa, myList, valorqr, usuario)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
}
