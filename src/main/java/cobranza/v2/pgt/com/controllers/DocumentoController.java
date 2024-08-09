
package cobranza.v2.pgt.com.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.entity.Documento;
import cobranza.v2.pgt.com.models.services.IDocumentoServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2/documento")
public class DocumentoController {
  
  @Autowired
  private IDocumentoServ documentoServ;
  
  @GetMapping
  public Iterable<Documento> index(@RequestParam(defaultValue = "A", name = "estado") String estado) {
    return documentoServ.listarAll(estado);
  }
  
  @GetMapping("buscar-alias")
  public Optional<Documento> BuscarAlias(@RequestParam(name = "alias") String alias) {
    return documentoServ.findByAlias(alias);
  }
  
}
