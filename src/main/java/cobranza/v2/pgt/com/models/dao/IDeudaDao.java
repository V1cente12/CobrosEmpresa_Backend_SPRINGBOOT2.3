
package cobranza.v2.pgt.com.models.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cobranza.v2.pgt.com.models.entity.Deuda;

@Repository
public interface IDeudaDao extends
                           JpaRepository<Deuda, Long> {
  
  List<Deuda> findByEstadoOrderByIddeudaDesc(String estado);
  
  public Page<Deuda> findByEstado(String estado,
                                  Pageable p);
  
  @Query(value = "SELECT d.* FROM pgt.deuda d RIGHT JOIN pgt.recibo r ON d.idrecibo=r.idrecibo "
    + " RIGHT JOIN pgt.cliente c ON c.idcliente=d.idcliente LEFT JOIN pgt.persona p ON p.idpersona=c.idpersona "
    + " WHERE d.estado in (:estado) and c.idempresa=:idempresa AND (p.valor_documento LIKE :nombre "
    + " OR (UPPER(concat(p.nombres,p.apellido_paterno)) LIKE UPPER(concat(:nombre,'%')))) "
    + " AND r.fecha_alta between :fechaI and :fechaF order by d.idpago desc", nativeQuery = true)
  Page<Deuda> listaFilterBySortBy(@Param("idempresa") Long idempresa,
                                  @Param("estado") List<String> estado,
                                  @Param("nombre") String nombre,
                                  @Param("fechaI") Date fechaI,
                                  @Param("fechaF") Date fechaF,
                                  Pageable p);
  
  @Query(value = "SELECT * FROM pgt.cliente c LEFT JOIN pgt.persona p on p.idpersona=c.idpersona "
    + " RIGHT JOIN pgt.deuda d ON c.idcliente=d.idcliente RIGHT JOIN pgt.recibo r ON r.idrecibo=d.idrecibo "
    + " WHERE d.estado=:estado AND r.fecha_alta between :fechaI and :fechaF AND c.idempresa=:idempresa "
    + " AND d.usuario_baja is null  ORDER BY r.monto DESC", nativeQuery = true)
  List<?> lista(@Param("idempresa") Long idempresa,
                @Param("estado") String estado,
                @Param("fechaI") Date fechaI,
                @Param("fechaF") Date fechaF);
  
  @Query(value = "SELECT * FROM pgt.cliente c LEFT JOIN pgt.persona p on p.idpersona=c.idpersona "
    + " RIGHT JOIN pgt.deuda d ON c.idcliente=d.idcliente RIGHT JOIN pgt.recibo r ON r.idrecibo=d.idrecibo "
    + " WHERE d.estado=:estado AND  p.valor_documento LIKE :nombre% "
    + " AND d.usuario_baja is null  ORDER BY r.monto DESC", nativeQuery = true)
  Page<Deuda> listaCliente(@Param("estado") String estado,
                           @Param("nombre") String nombre,
                           Pageable p);
  
  // @Modifying
  // @Query(value = "update e2c.deuda set
  // observacion=:data, estado=:estado where iddeuda
  // =:id", nativeQuery = true)
  // void visanet(@Param("data") String data,
  // @Param("estado") String estado, @Param("id")
  // Long id);
  
  @Query(value = "SELECT SUM(r.monto) FROM pgt.empresa e RIGHT JOIN pgt.cliente c ON e.idempresa=c.idempresa "
    + " RIGHT JOIN pgt.deuda d ON d.idcliente=c.idcliente RIGHT JOIN pgt.recibo r ON d.idrecibo=r.idrecibo "
    + " WHERE e.idempresa= ?1 AND d.estado=?2", nativeQuery = true)
  BigDecimal totaldeuda(Long id,
                        String esatdo);
  
  @Query(value = "SELECT sum(r.monto) FROM pgt.recibo r RIGHT JOIN pgt.deuda d on d.idrecibo=r.idrecibo WHERE d.iddeuda in (:iddeuda)", nativeQuery = true)
  BigDecimal monto(@Param("iddeuda") List<Long> iddeuda);
  
  @Query(value = "SELECT * FROM pgt.cliente c LEFT JOIN pgt.persona p on p.idpersona=c.idpersona "
    + " RIGHT JOIN pgt.deuda d ON c.idcliente=d.idcliente  RIGHT JOIN pgt.recibo r ON r.idrecibo=d.idrecibo "
    + " WHERE d.estado=:estado AND p.valor_documento LIKE :nombre AND c.idempresa=:idempresa "
    + " AND d.usuario_baja is null  ORDER BY r.fecha_alta DESC", nativeQuery = true)
  Page<Deuda> listaFilterEstadoNombre(@Param("estado") String estado,
                                      @Param("nombre") String nombre,
                                      @Param("idempresa") Long idempresa,
                                      Pageable p);
  
  @Query(value = "select r.idrecibo,COALESCE (glosa6, '---') as numero_telefono,COALESCE (split_part(glosa7, ',', 1), '---') as pais,case WHEN split_part(r.nombre_apellido , ';', 6) = 'string' "
    + "THEN '--' WHEN split_part(r.nombre_apellido , ';', 6) is null THEN '--' else split_part(r.nombre_apellido , ';', 6) end AS entrega, CASE WHEN r.estado = 'PAG' THEN 'PAGADO' "
    + "WHEN r.estado = 'PEN' THEN 'PENDIENTE' WHEN r.estado = 'ANU' THEN 'ANULADO' WHEN r.estado = 'PEN-BAN' THEN 'PEND. BANCARIO' WHEN r.estado = 'PAG-BAN' THEN 'PAG. BANCARIO' "
    + "WHEN r.estado = 'APP' THEN 'APP' END AS estado,COALESCE (split_part(glosa8 , ',', 1), '---') as ciudad,CASE WHEN r.suscripcion is null THEN concat(p.nombres,' ',p.apellido_paterno) ELSE r.suscripcion END as Cliente, "
    + "CASE WHEN r.glosa1='0' or r.glosa1 is null THEN p.valor_documento ELSE r.glosa1 END as valor_documento,p.tipo_documento,p.domicilio as direccion,p.correo,c.codigo_cliente,r.nro_recibo as nro_cuota, "
    + "CASE r.moneda WHEN 2 THEN r.monto*6.97 ELSE r.monto END as monto, r.fecha_alta as fecha_venta,r.fecha_vencimiento,r.reference_number,pg.fecha_alta as fecha_pago, "
    + "CASE WHEN substr( pg.descripcion, 1,7) = 'PAGO QR' THEN 'QR BNB' WHEN substr( pg.descripcion, 1,7) != 'PAGO QR' THEN 'ATC' END as metodo_pagado, CASE r.moneda WHEN 2 THEN 'Bs.' ELSE 'Bs.' END as moneda "
    + "from pgt.persona p inner join pgt.cliente c on  p.idpersona = c.idpersona inner join pgt.deuda d on c.idcliente = d.idcliente inner join pgt.recibo r on d.idrecibo = r.idrecibo "
    + "left join pgt.pago pg on d.idpago = pg.idpago where (UPPER(r.estado) LIKE UPPER(concat(:estado,'%')) and r.estado<>'APP') AND c.idempresa=:idempresa "
    + "AND r.fecha_alta between :fechaI and :fechaF ORDER BY r.estado DESC", nativeQuery = true)
  List<AuxilarDao> listaReporte(@Param("idempresa") Long idempresa,
                                @Param("estado") String estado,
                                @Param("fechaI") Date fechaI,
                                @Param("fechaF") Date fechaF);
  
  public static interface AuxilarDao {
    
    String getCliente( );
    String getValor_documento( );
    String getTipo_documento( );
    String getDireccion( );
    BigDecimal getMonto( );
    String getCorreo( );
    String getEntrega( );
    String getCodigo_cliente( );
    Long getNro_cuota( );
    String getEstado( );
    Date getFecha_venta( );
    Date getFecha_vencimiento( );
    Date getFecha_pago( );
    String getReference_number( );
    String getMoneda( );
    String getNumero_telefono( );
    String getMetodo_pagado( );
    String getPais( );
    String getCiudad( );
    String getIdrecibo( );
  }
  
  @Query(value = "SELECT d.* FROM pgt.recibo r INNER JOIN pgt.deuda d ON d.idrecibo=r.idrecibo "
    + " WHERE r.idrecibo=:idrecibo ", nativeQuery = true)
  Optional<Deuda> obtenerDeudaIDrecibo(@Param("idrecibo") Long idrecibo);
  @Query(value = "select d.* from pgt.recibo r inner join pgt.deuda d on d.idrecibo=r.idrecibo where r.nro_recibo =:nrorecibo ", nativeQuery = true)
  Optional<Deuda> obtenerDeudaNroRecibo(@Param("nrorecibo") String nrorecibo);
  
  @Query(value = "SELECT d.* FROM pgt.deuda d RIGHT JOIN pgt.recibo r ON d.idrecibo=r.idrecibo RIGHT JOIN pgt.cliente c ON c.idcliente=d.idcliente "
    + " LEFT JOIN pgt.persona p ON p.idpersona=c.idpersona WHERE d.estado in (:estado) and c.idempresa=:idempresa "
    + " AND (p.valor_documento LIKE :nombre OR (UPPER(concat(p.nombres,p.apellido_paterno)) LIKE UPPER(concat(:nombre,'%')))) "
    + " AND r.fecha_alta between :fechaI and :fechaF and d.observacion =(select min(observacion) from pgt.deuda  where idcliente =d.idcliente and estado =:estado)"
    + " order by d.iddeuda desc ", nativeQuery = true)
  Page<Deuda> listaSeguroVida(@Param("idempresa") Long idempresa,
                              @Param("estado") List<String> estado,
                              @Param("nombre") String nombre,
                              @Param("fechaI") Date fechaI,
                              @Param("fechaF") Date fechaF,
                              Pageable p);
  
  public static interface ShopifyrDao2 {
    
    String getDescripcion_item( );
    Integer getCantidad( );
    BigDecimal getPrecio_unitario( );
    BigDecimal getSub_total( );
    BigDecimal getTotal( );
    BigDecimal getDescuento( );
    String getFechav( );
    String getItem( );
    String getCliente( );
    Long getNro_recibo( );
    String getFechap( );
    String getDescripcion( );
    String getDoc( );
  }
  
  @Query(value = "select d.descripcion_item,d.cantidad ,d.precio_unitario ,d.sub_total ,to_char(d.fecha_alta, 'DD/MM/YYYY') as fechaV,d.item, "
    + "r.suscripcion as cliente,r.nro_recibo,to_char(p2.fecha_alta, 'DD/MM/YYYY HH24:MI:SS') as fechaP, "
    + " (case when substr( p2.descripcion, 1,7) = 'PAGO QR' then 'QR' when substr( p2.descripcion, 15,15) = '0' then 'Tarjeta' else 'Otro' end) "
    + " as descripcion,split_part(r.nombre_apellido,';', 1) as doc,d.descuento,d.total from pgt.detalle d inner join pgt.recibo r on r.idrecibo =d.idrecibo inner join "
    + " pgt.deuda d2 on d2.idrecibo =r.idrecibo inner join pgt.cliente c on c.idcliente =d2.idcliente inner join pgt.persona p on p.idpersona =c.idpersona "
    + " inner join pgt.pago p2 on p2.idpago =d2.idpago where d.fecha_alta between :fechaI and :fechaF and c.idempresa =:idempresa and d.item is not null and "
    + " r.estado =:estado and d.item=:item order by d.item,p2.fecha_alta desc", nativeQuery = true)
  List<ShopifyrDao2> listaReporteShopify(@Param("idempresa") Long idempresa,
                                         @Param("estado") String estado,
                                         @Param("item") String item,
                                         @Param("fechaI") Date fechaI,
                                         @Param("fechaF") Date fechaF);
  
  public static interface ShopifyrDao3 {
    
    String getItem( );
  }
  
  @Query(value = "select d.item from pgt.detalle d inner join pgt.recibo r on r.idrecibo =d.idrecibo inner join pgt.deuda d2 on d2.idrecibo =r.idrecibo "
    + " inner join pgt.cliente c on c.idcliente =d2.idcliente inner join pgt.persona p on p.idpersona =c.idpersona inner join pgt.pago p2 on p2.idpago =d2.idpago "
    + " where d.fecha_alta between :fechaI and :fechaF and c.idempresa =:idempresa and d.item is not null and r.estado =:estado GROUP BY d.item", nativeQuery = true)
  List<ShopifyrDao3> ObtenerComerciosShopify(@Param("idempresa") Long idempresa,
                                             @Param("estado") String estado,
                                             @Param("fechaI") Date fechaI,
                                             @Param("fechaF") Date fechaF);
  @Query(value = "select d.descripcion_item,d.cantidad ,d.precio_unitario ,d.sub_total ,to_char(d.fecha_alta, 'DD/MM/YYYY') as fechaV,d.item, "
    + "r.suscripcion as cliente,r.nro_recibo,to_char(p2.fecha_alta, 'DD/MM/YYYY HH24:MI:SS') as fechaP, "
    + " (case when substr( p2.descripcion, 1,7) = 'PAGO QR' then 'QR' when substr( p2.descripcion, 15,15) = '0' then 'Tarjeta' else 'Otro' end) "
    + " as descripcion,split_part(r.nombre_apellido,';', 1) as doc,d.descuento,d.total from pgt.detalle d inner join pgt.recibo r on r.idrecibo =d.idrecibo inner join "
    + " pgt.deuda d2 on d2.idrecibo =r.idrecibo inner join pgt.cliente c on c.idcliente =d2.idcliente inner join pgt.persona p on p.idpersona =c.idpersona "
    + " inner join pgt.pago p2 on p2.idpago =d2.idpago where d.fecha_alta between :fechaI and :fechaF and c.idempresa =:idempresa and d.item is not null and "
    + " r.estado =:estado order by d.item,p2.fecha_alta desc", nativeQuery = true)
  List<ShopifyrDao2> ReporteCsvShopify(@Param("idempresa") Long idempresa,
                                       @Param("estado") String estado,
                                       @Param("fechaI") Date fechaI,
                                       @Param("fechaF") Date fechaF);
}
