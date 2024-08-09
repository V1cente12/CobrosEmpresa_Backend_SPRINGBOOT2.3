
package cobranza.v2.pgt.com.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.Parametrica;

public interface IParametricaServ {
  
  Parametrica obtener(String dominio,
                             String subdominio,
                             String codigo);
  List<?> ListaParametrica(String descripcion,
                                  String glosa,
                                  String codigo);
  Parametrica guardar(Parametrica para);
  List<Parametrica> ListarDominioSubdominio(String dominio,
                                                   String subdominio);
  Optional<Parametrica> obtenerDominioSubDominioCodigoInIdEmpresa(String dominio,
                                                                         String subdominio,
                                                                         String codigo);

  List<Parametrica> obtenerParametricaDominoSubdominioCodigo(String dominio, String subdominio, String codigo);


  List<Parametrica> obtenerComisionesEmpresa();

}
