package cobranza.v2.pgt.com.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.entity.FormSeguro;
import cobranza.v2.pgt.com.models.services.IFormSeguroServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class FormSeguroController {
  @Autowired
  private IFormSeguroServ formseguroServ;
  
  @PostMapping("/seguro")
  public ResponseEntity<?> create(@RequestBody FormSeguro seguro) {
    Map<String, Object> response = new HashMap<>();
    try {
      seguro = formseguroServ.guardar(seguro);
      response.put("mensaje","El seguro ha sido creado con exito...");
      response.put("seguro",seguro);
    }
    catch(Exception e) {
      e.printStackTrace();
      return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
  }
  @GetMapping("/seguro")
  public ResponseEntity<?> listarEstado(@RequestParam("estado") String estado) {
    Map<String, Object> response = new HashMap<>();
    try {
      List<FormSeguro> lista= formseguroServ.ListarTodo(estado);
      response.put("lista",lista);
    }
    catch(Exception e) {
      e.printStackTrace();
      return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
  }
}
