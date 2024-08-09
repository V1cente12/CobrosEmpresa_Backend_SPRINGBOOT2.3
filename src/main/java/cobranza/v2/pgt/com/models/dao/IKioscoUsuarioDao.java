
package cobranza.v2.pgt.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cobranza.v2.pgt.com.models.entity.KioskoUsuario;

@Repository
public interface IKioscoUsuarioDao extends
                                   JpaRepository<KioskoUsuario, Long> {
  
  @Query(value = "SELECT * FROM bff.kiosko_usuario WHERE idusuario=:idusuario", nativeQuery = true)
  public List<KioskoUsuario> findByIdusuario(@Param("idusuario") Long idusuario);
  
}
