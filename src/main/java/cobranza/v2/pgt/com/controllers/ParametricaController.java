
package cobranza.v2.pgt.com.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.entity.Parametrica;
import cobranza.v2.pgt.com.models.services.IParametricaServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2/parametrica")
public class ParametricaController {
  
  @Autowired
  private IParametricaServ parametricaServ;
  
  @GetMapping("/obtener")
  public ResponseEntity<?> obtener(@RequestParam("dominio") String dominio,
                                   @RequestParam("subdominio") String subdominio,
                                   @RequestParam("codigo") String codigo) {
    
    return ResponseEntity.status(HttpStatus.OK)
                         .body(parametricaServ.obtener(dominio, subdominio, codigo));
  }
  
  @GetMapping("/obtener/dominio/sub")
  public ResponseEntity<?> obtenerDominioSubdominio(@RequestParam("dominio") String dominio,
                                                    @RequestParam("subdominio") String subdominio) {
    
    return ResponseEntity.status(HttpStatus.OK)
                         .body(parametricaServ.ListarDominioSubdominio(dominio, subdominio));
  }
  
  @GetMapping("/obtener/excluir")
  public ResponseEntity<?> Excluir(@RequestParam("dominio") String dominio,
                                   @RequestParam("subdominio") String subdominio,
                                   @RequestParam("codigo") String codigo) {
    Map<String, Object> response = new HashMap<>( );
    Optional<Parametrica> para = parametricaServ.obtenerDominioSubDominioCodigoInIdEmpresa(dominio,
      subdominio, codigo);
    if (!para.isPresent( )) para.of("");
    response.put("parametrica", para);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
}
