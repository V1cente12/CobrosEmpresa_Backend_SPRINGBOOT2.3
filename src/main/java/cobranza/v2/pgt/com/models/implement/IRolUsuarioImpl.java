
package cobranza.v2.pgt.com.models.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cobranza.v2.pgt.com.models.dao.IRolUsuarioDao;
import cobranza.v2.pgt.com.models.entity.RolUsuario;
import cobranza.v2.pgt.com.models.services.IRolUsuarioServ;

@Service
public class IRolUsuarioImpl implements IRolUsuarioServ {

		@Autowired
		private IRolUsuarioDao rolusuarioDao;

		@Override
		@Transactional
		public RolUsuario guardar(RolUsuario rolusuario) {
				return rolusuarioDao.save(rolusuario);
		}

}
