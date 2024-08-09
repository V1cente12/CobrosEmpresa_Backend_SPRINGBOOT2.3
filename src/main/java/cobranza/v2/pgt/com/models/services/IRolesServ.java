
package cobranza.v2.pgt.com.models.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import cobranza.v2.pgt.com.models.entity.Roles;

@Service
public interface IRolesServ {

		public List<Roles> listarAll(String estado);

		public Roles listarID(String estado, Long id);

		public Page<Roles> listarAllPage(String estado, String nombre, Long idempresa, Pageable p);

		public Roles guardar(Roles r);

		public void eliminar(String estado, String usuario, Long id);

}
