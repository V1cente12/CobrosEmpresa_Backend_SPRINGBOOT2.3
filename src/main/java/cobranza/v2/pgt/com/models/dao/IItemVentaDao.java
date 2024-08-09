
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

import cobranza.v2.pgt.com.models.entity.ItemVenta;

@Repository
public interface IItemVentaDao extends
                               JpaRepository<ItemVenta, Long> {
  
  public List<ItemVenta> findByEstadoAndIdempresa(String estado,
                                                  Long idempresa);
  public Page<ItemVenta> findByEstadoAndIdempresa(String estado,
                                                  Long idempresa,
                                                  Pageable p);
  public ItemVenta findByIditemAndIdempresa(Long iditem,
                                            Long idempresa);
  public Optional<ItemVenta> findByImagen(String imagen);
  public Optional<ItemVenta> findByCodigoAux(String codigoAux);
  @Transactional
  @Modifying
  @Query(value = "update bff.item_venta set estado=:estado where iditem=:iditem", nativeQuery = true)
  public void cambioEstadoId(@Param("estado") String estado,
                             @Param("iditem") Long iditem);
  public List<ItemVenta> findByUnidadAndIdempresaAndEstado(String unidad,
                                                           Long idempresa,
                                                           String estado);
  @Transactional
  @Modifying
  @Query(value = "UPDATE bff.item_venta SET ventas = ventas + 1 WHERE iditem=:iditem", nativeQuery = true)
  public void aumentarVentas(@Param("iditem") Long iditem);
  @Transactional
  @Modifying
  @Query(value = "UPDATE bff.item_venta SET ventas = ventas - 1 WHERE iditem=:iditem", nativeQuery = true)
  public void disminuirVentas(@Param("iditem") Long iditem);
}
