
package cobranza.v2.pgt.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.entity.Deuda;
import cobranza.v2.pgt.com.models.services.IDeudaServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class CobranzaClienteController {
  
  @Autowired
  private IDeudaServ deudaServ;
  
  @GetMapping("/cobranzaCliente/page/{page}")
  public Page<Deuda> index(@PathVariable Integer page,
                           @RequestParam("estado") String estado,
                           @RequestParam("nombre") String nombre,
                           @RequestParam("idempresa") String idempresa,
                           @RequestParam("size") String size) {
    System.out.println(size + " " + nombre + " " + estado + " " + idempresa);
    Page<Deuda> p =
                  deudaServ.buscarEstadoNombre(estado, Long.valueOf(idempresa), nombre, PageRequest.of(page, Integer.valueOf(size)));
    return p;
  }
  
}
