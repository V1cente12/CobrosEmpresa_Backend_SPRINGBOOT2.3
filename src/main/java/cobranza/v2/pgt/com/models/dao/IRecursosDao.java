
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

@Repository
public interface IRecursosDao extends JpaRepository<Recursos, Long> {

		@Query(value = "SELECT p.* FROM pgt.recurso p WHERE p.estado=:estado AND p.usuario_baja is null AND p.padre is null ORDER BY p.idrecurso DESC", nativeQuery = true)
		List<Recursos> findAllByEstado(@Param("estado") String estado);

		Recursos findByEstadoAndIdrecurso(String estado, Long id);

		@Query(value = "SELECT p.* FROM pgt.recurso p WHERE p.estado=:estado AND UPPER(p.nombre) LIKE  :nombre% AND p.usuario_baja is null AND p.padre is null ORDER BY p.idrecurso DESC", nativeQuery = true)
		Page<Recursos> listaBuscarEstadoNombre(@Param("estado") String estado,
		  @Param("nombre") String nombre, Pageable p);

		@Modifying
		@Query(value = "update pgt.recurso set estado =:estado, usuario_baja=:usuario, fecha_baja= now() where idrecurso= :id", nativeQuery = true)
		void elimnar(@Param("estado") String estado, @Param("usuario") String usuario,
		  @Param("id") Long id);

		/**
		 * ---------------------------------------------------------------------------
		 */
		List<Recursos> findByEstadoAndPadreNotNull(String estado);

		@Query(value = "SELECT p.* FROM pgt.recurso p WHERE p.estado=:estado AND UPPER(p.nombre) LIKE  :nombre% AND p.usuario_baja is null AND p.padre is not null ORDER BY p.idrecurso DESC", nativeQuery = true)
		Page<Recursos> listaBuscarEstadoNombre2(@Param("estado") String estado,
		  @Param("nombre") String nombre, Pageable p);

		@Query(value = "SELECT r.* FROM pgt.recurso r WHERE r.idrecurso in (:idrecurso)", nativeQuery = true)
		List<Recursos> getRecursosListID(@Param("idrecurso") List<Long> idrecurso);
}
