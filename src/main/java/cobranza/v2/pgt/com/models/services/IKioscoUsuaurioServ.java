
package cobranza.v2.pgt.com.models.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.KioskoUsuario;

@Service
public interface IKioscoUsuaurioServ {
  
  public List<KioskoUsuario> listarIdusuario(Long idusuario);
}
