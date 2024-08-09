
package cobranza.v2.pgt.com.models.services;

import org.springframework.stereotype.Service;
import cobranza.v2.pgt.com.models.entity.RolUsuario;

@Service
public interface IRolUsuarioServ {

		public RolUsuario guardar(RolUsuario rolusuario);

}
