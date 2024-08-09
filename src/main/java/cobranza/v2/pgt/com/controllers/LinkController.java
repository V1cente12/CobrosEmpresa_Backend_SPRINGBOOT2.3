
package cobranza.v2.pgt.com.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.services.IDeudaServ;
import cobranza.v2.pgt.com.models.services.ILinkServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class LinkController {
  
  @Value("#{'${name.url}'}")
  private String URL;
  @Value("#{'${name.port}'}")
  private String PORT;
  
  private ILinkServ linkServ;
  private IDeudaServ deudaServ;
  
  @Autowired
  public LinkController(ILinkServ linkServ,
                        IDeudaServ deudaServ) {
    this.linkServ = linkServ;
    this.deudaServ = deudaServ;
  }
  
  @GetMapping(value = "/link/todolink", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> ListarTodo(@RequestParam(name = "idbitacora") String idbitacora) {
    return ResponseEntity.status(HttpStatus.OK).body(linkServ.listarIDbitacora(Long.valueOf(idbitacora)));
  }
  
  @PostMapping(value = "/link/enviarlink")
  public ResponseEntity<?> enviarlink(@RequestParam(name = "telf") List<Long> telf,
                                      @RequestParam(name = "correo") List<Long> correo,
                                      @RequestParam(name = "idbitacora") String idbitacora) throws Exception {
    Map<String, Object> response = new HashMap<>( );
    deudaServ.envioCorreo(telf, correo, Long.valueOf(idbitacora));
    response.put("msj", "Se esta procesando los envios de link de pago..");
    return ResponseEntity.status(HttpStatus.OK).body(telf);
  }
}
