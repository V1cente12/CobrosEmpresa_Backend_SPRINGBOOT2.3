
package cobranza.v2.pgt.com.SinRegistro;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.services.IEmpresaServ;
import cobranza.v2.pgt.com.models.services.IPersonasServ;
import cobranza.v2.pgt.com.models.services.IUsuariosServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranza")
public class RegistroSin {
  
  @Autowired
  private IUsuariosServ usuarioServ;
  
  @Autowired
  private IEmpresaServ empresaServ;
  
  @Autowired
  private IPersonasServ personaServ;
  
  @GetMapping("/empresas/buscarNitOrCorreos/{buscar}")
  @ResponseStatus(HttpStatus.OK)
  public List<?> buscarNitOrCorreos(@PathVariable String buscar) {
    return empresaServ.BuscarCorreosOrNit(buscar);
  }
  
  @GetMapping("/personas/buscarRepresentante/{buscar}")
  @ResponseStatus(HttpStatus.OK)
  public List<?> buscarRepresentante(@PathVariable String buscar) {
    return personaServ.BuscarRepresentanteEmpresa(buscar);
  }
  
  @GetMapping("/usuarios/buscarlogin/{buscar}")
  @ResponseStatus(HttpStatus.OK)
  public List<?> buscarlogin(@PathVariable String buscar) { return usuarioServ.BuscarLogin(buscar); }
  
  @GetMapping("/personas/buscar/{valor}")
  @ResponseStatus(HttpStatus.OK)
  public Optional<?> index(@PathVariable String valor) { return personaServ.buscarUnicoValor(valor); }
  
  @GetMapping("/usuarios/buscarNitLoginTipo")
  @ResponseStatus(HttpStatus.OK)
  public Optional<?> buscarNitLoginTipo(@RequestParam("nit") String nit,
                                        @RequestParam("login") String login,
                                        @RequestParam("tipo") String codigo) {
    return usuarioServ.existsNitEmpresaLoginTipo(nit, login, codigo);
  }
}
