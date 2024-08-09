
package cobranza.v2.pgt.com.models.implement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cobranza.v2.pgt.com.models.dao.IRolesDao;
import cobranza.v2.pgt.com.models.entity.Roles;
import cobranza.v2.pgt.com.models.services.IRolesServ;

@Service
public class IRolesImpl implements IRolesServ {

		// private Logger logger =
		// LoggerFactory.getLogger(IUsuariosEmpresaImpl.class);

		@Autowired
		private IRolesDao rolesempresaDao;

		@Override
		@Transactional
		public List<Roles> listarAll(String estado) {
				return rolesempresaDao.findAllByEstado(estado);
		}

		@Override
		@Transactional
		public Roles listarID(String estado, Long id) {
				return rolesempresaDao.findByEstadoAndIdrol(estado,id);
		}

		@Override
		@Transactional
		public Page<Roles> listarAllPage(String estado, String nombre, Long idempresa, Pageable p) {
				return rolesempresaDao.listaBuscarEstadoNombre(estado,idempresa,nombre,p);
		}

		@Override
		@Transactional
		public Roles guardar(Roles r) {
				return rolesempresaDao.save(r);
		}

		@Override
		@Transactional
		public void eliminar(String estado, String usuario, Long id) {
				if(rolesempresaDao.findById(id).isPresent()) {
						rolesempresaDao.elimnar(estado,usuario,id);
				}
		}

}
