
package cobranza.v2.pgt.com.models.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import cobranza.v2.pgt.com.models.entity.Recursos;

@Service
public interface IRecursosServ {

		public List<Recursos> listarAll(String estado);

		public Recursos listarID(String estado, Long id);

		public Page<Recursos> listarAllPage(String estado, String nombre, Pageable p);

		public Recursos guardar(Recursos m);

		public void eliminar(String estado, String usuario, Long id);

		public List<Recursos> listarProcesosAll(String estado);

		public Page<Recursos> listarAllPage2(String estado, String nombre, Pageable p);

		List<Recursos> getRecursosListID(@Param("iddeuda") List<Long> idrecurso);

}
