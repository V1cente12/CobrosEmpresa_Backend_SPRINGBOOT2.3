
package cobranza.v2.pgt.com.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.entity.CategoriaItemVenta;

@Repository
public interface ICategoriaDao extends
                               JpaRepository<CategoriaItemVenta, Long> {
  
  public List<CategoriaItemVenta> findByEstadoOrderByIdcategoriaAsc(String estado);
  public List<CategoriaItemVenta> findByEstadoAndIdempresaOrderByIdcategoriaAsc(String estado,
                                                                                Integer idempresa);
  @Query(value = "SELECT c.* FROM bff.categoria_item_venta c "
    + " WHERE c.idempresa=:idempresa and c.estado=:estado and c.idpadre is null ORDER BY idcategoria desc", nativeQuery = true)
  public List<CategoriaItemVenta> findByEstadoAndIdempresaAndIdpadre(@Param("estado") String estado,
                                                                     @Param("idempresa") Long idempresa);
  public Page<CategoriaItemVenta> findByEstadoAndIdempresa(String estado,
                                                           Integer idempresa,
                                                           Pageable p);
  public Optional<CategoriaItemVenta> findByIdcategoria(Long idcategoria);
  public Optional<CategoriaItemVenta> findByImagen(String imagen);
  
  @Transactional
  @Modifying
  @Query(value = "update bff.categoria_item_venta set estado=:estado where idcategoria=:idcategoria", nativeQuery = true)
  public void cambioEstadoId(@Param("estado") String estado,
                             @Param("idcategoria") Long idcategoria);
}
