
package cobranza.v2.pgt.com.models.dao;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cobranza.v2.pgt.com.models.entity.Recursos;
import cobranza.v2.pgt.com.models.entity.Roles;

@Repository
public interface IRolesDao extends JpaRepository<Roles, Long> {

		List<Roles> findAllByEstado(String estado);

		Roles findByEstadoAndIdrol(String estado, Long id);

		@Query(value = "SELECT r.* FROM pgt.usuario u "
		  + " LEFT JOIN pgt.rol_usuario ru on ru.idusuario=u.idusuario "
		  + " LEFT JOIN pgt.rol r on r.idrol=ru.idrol WHERE u.idusuario=?1", nativeQuery = true)
		List<Roles> roles(Long idusuario);

		@Query(value = "SELECT p.* FROM e2c.pgc_roles p WHERE p.estado=:estado AND p.idempresa=:idempresa AND UPPER(p.nombre) LIKE  :nombre%  AND p.usuario_baja is null ORDER BY p.idrol DESC", nativeQuery = true)
		Page<Roles> listaBuscarEstadoNombre(@Param("estado") String estado,
		  @Param("idempresa") Long idempresa, @Param("nombre") String nombre, Pageable p);

		@Modifying
		@Query(value = "update e2c.pgc_roles set estado =:estado, usuario_baja=:usuario, fecha_baja= now() where idrol=:id", nativeQuery = true)
		void elimnar(@Param("estado") String estado, @Param("usuario") String usuario,
		  @Param("id") Long id);

		@Query(value = "SELECT * FROM  e2c.pgc_roles r "
		  + " WHERE r.estado=:estado AND (UPPER(concat(r.nombre)) LIKE  :nombre%) "
		  + " AND em.idempresa=:idempresa AND r.usuario_baja is null ORDER BY r.idrol ASC", nativeQuery = true)
		Page<Recursos> listaMenuPage(@Param("estado") String estado, @Param("nombre") String nombre,
		  @Param("idempresa") Long idempresa, Pageable p);
}
