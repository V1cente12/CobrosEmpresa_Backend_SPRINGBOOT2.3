
package cobranza.v2.pgt.com.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cobranza.v2.pgt.com.models.entity.Parametrica;

@Repository
public interface IParametricaDao extends
                                 JpaRepository<Parametrica, Long> {
  
  @Query(value = "SELECT * FROM pgt.parametrica WHERE UPPER(dominio)=UPPER(:dominio) AND "
    + " UPPER(subdominio)=UPPER(:subdominio) AND UPPER(codigo)=UPPER(:codigo)", nativeQuery = true)
  public Parametrica obtener(@Param("dominio") String dominio,
                             @Param("subdominio") String subdominio,
                             @Param("codigo") String codigo);
  
  @Query(value = "SELECT * FROM pgt.parametrica WHERE UPPER(dominio)=UPPER(:dominio) AND "
    + " UPPER(subdominio)=UPPER(:subdominio) AND UPPER(codigo)=UPPER(:codigo)", nativeQuery = true)
  public List<Parametrica> lista(@Param("dominio") String dominio,
                                 @Param("subdominio") String subdominio,
                                 @Param("codigo") String codigo);
  
  public List<Parametrica> findByDominioAndSubdominio(String dominio,
                                                      String subdominio);
  
  @Query(value = "select * from pgt.parametrica where UPPER(dominio)=UPPER(:dominio) AND UPPER(subdominio)=UPPER(:subdominio) "
    + " and :codigo=any(string_to_array(codigo,';'))", nativeQuery = true)
  public Optional<Parametrica> obtenerDominioSubDominioCodigoInIdEmpresa(@Param("dominio") String dominio,
                                                                         @Param("subdominio") String subdominio,
                                                                         @Param("codigo") String codigo);

  @Query(value = "select * from pgt.parametrica where UPPER(dominio)=UPPER(:dominio) AND UPPER(subdominio)=UPPER(:subdominio) "
          + " and :codigo=any(string_to_array(codigo,';'))", nativeQuery = true)
  public List<Parametrica> getParametricaByDominioAndSubdominioAndCodigo(@Param("dominio") String dominio,
                                                                         @Param("subdominio") String subdominio,
                                                                         @Param("codigo") String codigo);
}
