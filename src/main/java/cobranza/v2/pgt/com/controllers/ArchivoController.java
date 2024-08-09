
package cobranza.v2.pgt.com.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.services.IDeudaServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class ArchivoController {
  
  @Autowired
  private IDeudaServ deudaServ;
  
  @PostMapping("/archivo/deuda")
  public ResponseEntity<?> archivo(@RequestParam("file") MultipartFile file,
                                   @RequestParam("usuario") String usuario) {
    Map<String, Object> response = deudaServ.archivo(file, usuario);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
  
}
