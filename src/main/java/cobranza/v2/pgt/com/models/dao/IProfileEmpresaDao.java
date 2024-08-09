
package cobranza.v2.pgt.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cobranza.v2.pgt.com.models.entity.AtcProfileEmpresa;

public interface IProfileEmpresaDao extends JpaRepository<AtcProfileEmpresa, Long> {
  
  @Query(value = "SELECT * FROM pgt.atc_profile_empresa WHERE idprofile=:idprofile", nativeQuery = true)
  AtcProfileEmpresa obtenerID(@Param("idprofile") Long idprofile);
}
