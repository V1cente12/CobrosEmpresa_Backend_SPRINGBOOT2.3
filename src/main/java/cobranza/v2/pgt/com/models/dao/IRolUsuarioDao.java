
package cobranza.v2.pgt.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cobranza.v2.pgt.com.models.entity.RolUsuario;

@Repository
public interface IRolUsuarioDao extends JpaRepository<RolUsuario, Long> {

}
