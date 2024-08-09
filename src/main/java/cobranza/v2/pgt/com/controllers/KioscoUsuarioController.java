
package cobranza.v2.pgt.com.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.services.IKioscoServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2/kioscousuario")
public class KioscoUsuarioController {
  
  @Autowired
  private IKioscoServ kioscousuarioServ;
  // @Autowired
  // private Auxiliar aux;
  
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> ListarIdusuario(@RequestParam(name = "idusuario") String idusuario) {
    Map<String, Object> response = new HashMap<>( );
    try {
      response.put("kioscousuario", kioscousuarioServ.listarIdusuario(Long.valueOf(idusuario)));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }catch(Exception e) {
      response.put("mensaje", "Error en el servicio");
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
