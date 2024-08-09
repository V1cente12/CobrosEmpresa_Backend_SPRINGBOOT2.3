
package cobranza.v2.pgt.com.models.implement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cobranza.v2.pgt.com.models.dao.IRecursosDao;
import cobranza.v2.pgt.com.models.entity.Recursos;
import cobranza.v2.pgt.com.models.services.IRecursosServ;

@Service
public class IRecursosImpl implements IRecursosServ {

		// private Logger logger =
		// LoggerFactory.getLogger(IRecursosImpl.class);

		@Autowired
		private IRecursosDao recursosDao;

		@Override
		@Transactional
		public List<Recursos> listarAll(String estado) {
				return recursosDao.findAllByEstado(estado);
		}

		@Override
		@Transactional
		public Recursos listarID(String estado, Long id) {
				return recursosDao.findByEstadoAndIdrecurso(estado,id);
		}

		@Override
		@Transactional
		public Page<Recursos> listarAllPage(String estado, String nombre, Pageable p) {
				return recursosDao.listaBuscarEstadoNombre(estado,nombre,p);
		}

		@Override
		@Transactional
		public Recursos guardar(Recursos m) {
				return recursosDao.save(m);
		}

		@Override
		@Transactional
		public void eliminar(String estado, String usuario, Long id) {
				if(recursosDao.findById(id).isPresent()) {
						recursosDao.elimnar(estado,usuario,id);
				}
		}

		@Override
		@Transactional
		public List<Recursos> listarProcesosAll(String estado) {
				return recursosDao.findByEstadoAndPadreNotNull(estado);
		}

		@Override
		@Transactional
		public Page<Recursos> listarAllPage2(String estado, String nombre, Pageable p) {
				return recursosDao.listaBuscarEstadoNombre2(estado,nombre,p);
		}

		@Override
		@Transactional(readOnly = true)
		public List<Recursos> getRecursosListID(List<Long> idrecurso) {
				return recursosDao.getRecursosListID(idrecurso);
		}
}
