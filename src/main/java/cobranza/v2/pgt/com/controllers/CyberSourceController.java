
package cobranza.v2.pgt.com.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.entity.CyberSource;
import cobranza.v2.pgt.com.models.services.ICyberSourceServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class CyberSourceController {
  
  private Logger logger = LoggerFactory.getLogger(CyberSourceController.class);
  
  @Autowired
  private ICyberSourceServ cyberServ;
  
  @GetMapping("/cyber/obtener/{id}")
  public List<CyberSource> index(@PathVariable Long id) {
    List<CyberSource> c = null;
    try {
      c = cyberServ.obtenerIdRecibo(id);
    }catch(DataAccessException e) {
      logger.error("error a la BDD del id : " + id);
    }
    return c;
  }
}
