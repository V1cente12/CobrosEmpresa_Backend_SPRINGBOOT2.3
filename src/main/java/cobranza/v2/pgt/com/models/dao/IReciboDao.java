
package cobranza.v2.pgt.com.models.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import cobranza.v2.pgt.com.models.entity.Parametrica;
import cobranza.v2.pgt.com.models.entity.Recibo;

public interface IReciboDao extends
                            CrudRepository<Recibo, Long> {
  
  @Modifying
  @Query(value = "update pgt.recibo set monto=:monto where idrecibo=:idrecibo", nativeQuery = true)
  @Transactional
  void updatemonto(@Param("monto") BigDecimal monto,
                   @Param("idrecibo") Long idrecibo);
  
  @Query(value = "SELECT r.* FROM pgt.recibo r WHERE r.concepto_recibo = ?1 and r.periodo= ?2", nativeQuery = true)
  Recibo buscarReciboCIyPeriodo(String concepto_recibo,
                                String periodo);
  
  @Query(value = "SELECT max(r.idrecibo) FROM pgt.recibo r ", nativeQuery = true)
  Long maxID( );
  
  @Query(value = "SELECT r.periodo, r.nro_recibo, SUM(CASE WHEN d.estado='PEN' THEN r.monto ELSE 0 END) as pen, "
    + "SUM(CASE WHEN d.estado='PAG' THEN r.monto ELSE 0 END) as pag FROM pgt.deuda d "
    + "LEFT JOIN pgt.recibo r ON r.idrecibo=d.idrecibo "
    + "GROUP BY r.periodo, r.nro_recibo ORDER BY r.nro_recibo ", nativeQuery = true)
  List<AuxilarDao> totalMontoPeriodo( );
  
  @Query(value = "SELECT r.periodo, r.nro_recibo, COUNT(CASE WHEN d.estado='PEN' THEN r.idrecibo END) as pen, "
    + "COUNT(CASE WHEN d.estado='PAG' THEN r.idrecibo END) as pag FROM pgt.deuda d "
    + "LEFT JOIN pgt.recibo r ON r.idrecibo=d.idrecibo "
    + "GROUP BY r.periodo, r.nro_recibo ORDER BY r.nro_recibo", nativeQuery = true)
  List<AuxilarDao> cantidadDeuda( );
  
  @Query(value = "SELECT r.* FROM pgt.recibo r INNER JOIN pgt.deuda d ON d.idrecibo=r.idrecibo "
    + " INNER JOIN pgt.cliente c ON c.idcliente=d.idcliente WHERE c.idempresa=:idempresa AND r.nro_recibo=:nropedido and r.estado='PEN'", nativeQuery = true)
  public Optional<Recibo> ExisteNroPedido(@Param("nropedido") Long nropedido,
                                          @Param("idempresa") Long idempresa);
  @Query(value = "SELECT r.* FROM pgt.detalle rd INNER JOIN pgt.recibo r ON r.idrecibo=rd.idrecibo "
    + " INNER JOIN pgt.deuda d ON d.idrecibo=r.idrecibo INNER JOIN pgt.cliente c ON c.idcliente=d.idcliente "
    + " WHERE r.nro_recibo=:nro_recibo and c.idempresa=:idempresa and r.estado='PEN'", nativeQuery = true)
  public Recibo findByNroreciboIdempresa(@Param("nro_recibo") Long nro_recibo,
                                         @Param("idempresa") Long idempresa);
  
  Recibo findByIdrecibo(Long idrecibo);
  @Query(value = "SELECT r.* FROM pgt.recibo r WHERE reference_number=:reference", nativeQuery = true)
  List<Recibo> buscarIdRefenrece(@Param("reference") String reference);
  
  public static interface AuxilarDao {
    
    String getPeriodo( );
    Integer getNro_recibo( );
    BigDecimal getPen( );
    BigDecimal getPag( );
    
  }
  
  @Query(value = "select r.reference_number,p.valor_documento,case when p.tipo_documento = '1' then 'CI' when p.tipo_documento = '2' then 'NIT' else p.tipo_documento end as tipo_documento,"
    + " r.nro_recibo as nro_pedido,case when r.moneda = 2 then r.monto*6.97 else r.monto end as monto_bs,case when r.moneda = 1 then 0 else r.monto end as monto_usd,r.estado,"
    + " to_char(r.fecha_alta,'DD-MM-YYYY') as fecha_venta,case when r.fecha_vencimiento is null then to_char(r.fecha_alta,'DD-MM-YYYY') else to_char(r.fecha_vencimiento,'DD-MM-YYYY') end as fecha_vencimiento,"
    + " to_char(pg.fecha_alta, 'DD-MM-YYYY') as fecha_pago,to_char(pg.fecha_alta, 'HH24:MI:SS') as hora_pago,case when substr( pg.descripcion, 1,7) = 'PAGO QR' then 'QR BNB' else 'ATC' end as forma_pago,"
    + " pgt.desc_empresa(CAST(c.idempresa as text)) grupo_empresa,"
    + " case when pgt.desc_empresa(r.codigo_pago) = 'MEMORIAL PARK' then 'CMP INVERSIONES' when pgt.desc_empresa(r.codigo_pago) = 'string' then '' else pgt.desc_empresa(r.codigo_pago) end as empresa,"
    + " c.idempresa from pgt.persona p inner join pgt.cliente c on  p.idpersona = c.idpersona inner join pgt.deuda d on c.idcliente = d.idcliente inner join pgt.recibo r on d.idrecibo = r.idrecibo "
    + " left join  pgt.pago pg on d.idpago = pg.idpago  where c.idempresa in (select idempresa from pgt.empresa) and r.estado LIKE concat('%',:estado) "
    + " and c.idempresa =:idempresa and pg.fecha_alta between :fechaI and :fechaF order by  pg.fecha_alta desc", nativeQuery = true)
  public List<AuxilarDao2> transaccionpago(@Param("idempresa") Long idempresa,
                                           @Param("estado") String estado,
                                           @Param("fechaI") Date fechaI,
                                           @Param("fechaF") Date fechaF);
  
  public static interface AuxilarDao2 {
    
    String getReference_number( );
    String getValor_documento( );
    String getTipo_documento( );
    BigDecimal getNro_pedido( );
    BigDecimal getMonto_bs( );
    BigDecimal getMonto_usd( );
    String getEstado( );
    String getFecha_venta( );
    String getFecha_vencimiento( );
    String getFecha_pago( );
    String getHora_pago( );
    String getForma_pago( );
    String getGrupo_empresa( );
    String getEmpresa( );
    Integer getIdempresa( );
  }
  
  @Modifying
  @Query(value = "update pgt.recibo set codigo_pago=:valor where estado = 'PEN' "
    + " and idrecibo in (select d.idrecibo from pgt.deuda d inner join pgt.cliente c on c.idcliente= d.idcliente "
    + " where d.estado = 'PEN' and d.idrecibo =:idrecibo)", nativeQuery = true)
  @Transactional
  void updatekiosco(@Param("valor") String valor,
                    @Param("idrecibo") Long idrecibo);
  
  @Query(value = "SELECT r.* FROM pgt.recibo r WHERE UPPER(r.codigo_pago) IN :codigopago "
    + " AND r.estado='PEN' AND r.fecha_alta between :fechaI and :fechaF ORDER BY r.idrecibo DESC", nativeQuery = true)
  Page<Recibo> listaFilterBySortBy(@Param("codigopago") List<String> codigopago,
                                   @Param("fechaI") Date fechaI,
                                   @Param("fechaF") Date fechaF,
                                   Pageable p);
  @Query(value = "SELECT * FROM pgt.recibo WHERE idrecibo=:idrecibo", nativeQuery = true)
  Optional<Recibo> obtenerIDrecibo(@Param("idrecibo") Long idrecibo);
  
  @Query(value = "select o.obj_response  from e2c.objecto_response o  where  o.id_objeto =:data", nativeQuery = true)
  String Appmovil(@Param("data") String data);
  
  @Query(value = "select distinct moneda from pgt.recibo  where idrecibo in (:idrecibo)", nativeQuery = true)
  List<?> ListMultimoneda(@Param("idrecibo") List<Long> idrecibo);
  
  public static interface AuxilarDao3 {
    
    String getComercio( );
    String getNit( );
    Long getId( );
    
  }
  
  @Query(value = "select e.razon_social as comercio,e.nit ,c.idempresa as id from pgt.empresa e inner join pgt.cliente c "
    + " on c.idempresa =e.idempresa inner join pgt.deuda d on c.idcliente = d.idcliente inner join pgt.recibo r "
    + " on d.idrecibo = r.idrecibo left join  pgt.pago pg on d.idpago = pg.idpago where  r.estado LIKE concat('%',:estado) "
    + " and pg.fecha_alta between :fechaI and :fechaF and isnumeric(split_part(pg.descripcion, '-', 1)) "
    + " group by e.razon_social,e.nit ,c.idempresa order by  e.razon_social,e.nit ,c.idempresa  desc", nativeQuery = true)
  public List<AuxilarDao3> ListaLiquidacionEmpresaTarjeta(@Param("estado") String estado,
                                                          @Param("fechaI") Date fechaI,
                                                          @Param("fechaF") Date fechaF);
  @Query(value = "select e.razon_social as comercio,e.nit ,c.idempresa as id from pgt.empresa e inner join pgt.cliente c "
    + " on c.idempresa =e.idempresa inner join pgt.deuda d on c.idcliente = d.idcliente inner join pgt.recibo r "
    + " on d.idrecibo = r.idrecibo left join  pgt.pago pg on d.idpago = pg.idpago where  r.estado LIKE concat('%',:estado) "
    + " and pg.fecha_alta between :fechaI and :fechaF and cast(c.idempresa as varchar) in (select p2.codigo "
    + " from pgt.parametrica p2 where p2.tipo IN ('NUMERIC') AND p2.valor IN (:qr) and p2.codigo=cast(c.idempresa as varchar)) "
    + " AND split_part(pg.descripcion, ':', 1)='PAGO QR ' group by e.razon_social,e.nit ,c.idempresa "
    + " order by e.razon_social,e.nit ,c.idempresa desc", nativeQuery = true)
  public List<AuxilarDao3> ListaLiquidacionEmpresaQr(@Param("estado") String estado,
                                                     @Param("fechaI") Date fechaI,
                                                     @Param("fechaF") Date fechaF,
                                                     @Param("qr") String idqr);
  
  @Query(value = "select r.reference_number,p.valor_documento,case when p.tipo_documento = '1' then 'CI' when p.tipo_documento = '2' then 'NIT' else p.tipo_documento end as tipo_documento,"
    + " r.nro_recibo as nro_pedido,case when r.moneda = 2 then r.monto*6.97 else r.monto end as monto_bs,case when r.moneda = 1 then 0 else r.monto end as monto_usd,r.estado,"
    + " to_char(r.fecha_alta,'DD-MM-YYYY') as fecha_venta,case when r.fecha_vencimiento is null then to_char(r.fecha_alta,'DD-MM-YYYY') else to_char(r.fecha_vencimiento,'DD-MM-YYYY') end as fecha_vencimiento,"
    + " to_char(pg.fecha_alta, 'DD-MM-YYYY') as fecha_pago,to_char(pg.fecha_alta, 'HH24:MI:SS') as hora_pago,case when substr( pg.descripcion, 1,7) = 'PAGO QR' then 'QR BNB' else 'ATC' end as forma_pago,"
    + " pgt.desc_empresa(CAST(c.idempresa as text)) grupo_empresa,case when pgt.desc_empresa(r.codigo_pago) = 'MEMORIAL PARK' then 'CMP INVERSIONES' when pgt.desc_empresa(r.codigo_pago) = 'string' then '' else pgt.desc_empresa(r.codigo_pago) end as empresa,"
    + " c.idempresa,c2.req_bill_to_address_country as pais,c2.card_type_name as tarjeta from pgt.persona p inner join pgt.cliente c on  p.idpersona = c.idpersona inner join pgt.deuda d on c.idcliente = d.idcliente inner join pgt.recibo r on d.idrecibo = r.idrecibo "
    + " left join  pgt.pago pg on d.idpago = pg.idpago inner join pgt.cybersource c2 on c2.idrecibo =r.idrecibo  where c.idempresa in (select idempresa from pgt.empresa) and r.estado LIKE concat('%',:estado) "
    + " and cast(c.idempresa as varchar) LIKE concat(:idempresa) and pg.fecha_alta between :fechaI and :fechaF and c2.idparametrica =1 order by  pg.fecha_alta desc", nativeQuery = true)
  public List<AuxilarDao4> liquidacionTarjeta(@Param("idempresa") String idempresa,
                                              @Param("estado") String estado,
                                              @Param("fechaI") Date fechaI,
                                              @Param("fechaF") Date fechaF);
  
  public static interface AuxilarDao4 {
    
    String getReference_number( );
    String getValor_documento( );
    String getTipo_documento( );
    BigDecimal getNro_pedido( );
    BigDecimal getMonto_bs( );
    BigDecimal getMonto_usd( );
    String getEstado( );
    String getFecha_venta( );
    String getFecha_vencimiento( );
    String getFecha_pago( );
    String getHora_pago( );
    String getForma_pago( );
    String getGrupo_empresa( );
    String getEmpresa( );
    Integer getIdempresa( );
    String getPais( );
    String getTarjeta( );
  }
  
  @Query(value = "select r.reference_number,p.valor_documento,case when p.tipo_documento = '1' then 'CI' when p.tipo_documento = '2' then 'NIT' else p.tipo_documento end as tipo_documento, "
    + " r.nro_recibo as nro_pedido,case when r.moneda = 2 then r.monto*6.97 else r.monto end as monto_bs,case when r.moneda = 1 then 0 else r.monto end as monto_usd,r.estado, "
    + " to_char(r.fecha_alta,'DD-MM-YYYY') as fecha_venta,case when r.fecha_vencimiento is null then to_char(r.fecha_alta,'DD-MM-YYYY') else to_char(r.fecha_vencimiento,'DD-MM-YYYY') end as fecha_vencimiento, "
    + " to_char(pg.fecha_alta, 'DD-MM-YYYY') as fecha_pago,to_char(pg.fecha_alta, 'HH24:MI:SS') as hora_pago,case when substr( pg.descripcion, 1,7) = 'PAGO QR' then 'QR BNB' else 'ATC' end as forma_pago, "
    + " pgt.desc_empresa(CAST(c.idempresa as text)) grupo_empresa, case when pgt.desc_empresa(r.codigo_pago) = 'MEMORIAL PARK' then 'CMP INVERSIONES' when pgt.desc_empresa(r.codigo_pago) = 'string' then '' else pgt.desc_empresa(r.codigo_pago) end as empresa, "
    + " c.idempresa from pgt.recibo r inner join pgt.deuda d on d.idrecibo =r.idrecibo inner join pgt.cliente c on c.idcliente =d.idcliente inner join pgt.persona p on p.idpersona =c.idpersona "
    + " inner join pgt.empresa e on e.idempresa =c.idempresa inner join pgt.pago pg on d.idpago = pg.idpago where r.estado LIKE concat('%',:estado) and substr( pg.descripcion, 1,7) = 'PAGO QR' "
    + " and cast(c.idempresa as varchar) in (select p2.codigo from pgt.parametrica p2 where p2.tipo IN ('NUMERIC') AND p2.valor IN (:qr) and p2.codigo=:idempresa) and pg.fecha_alta between :fechaI and :fechaF "
    + " order by  pg.fecha_alta desc", nativeQuery = true)
  public List<AuxilarDao2> liquidacionQr(@Param("idempresa") String idempresa,
                                         @Param("estado") String estado,
                                         @Param("fechaI") Date fechaI,
                                         @Param("fechaF") Date fechaF,
                                         @Param("qr") String idqr);
  
  @Query("SELECT recibo FROM Recibo recibo " + "INNER JOIN recibo.idcyber cybers "
    + "WHERE recibo.estado = :estado " + " and cybers.idparametrica = :parametrica "
    + " and recibo.iddeuda.idpago.fecha_alta between :fechaInicial and :fechaFinal "
    + "ORDER BY recibo.iddeuda.idpago.fecha_alta DESC")
  List<Recibo> liquidacionEmpresaTarjeta(@Param("estado") String estado,
                                         @Param("parametrica") Parametrica parametrica,
                                         @Param("fechaInicial") Date fechaInicial,
                                         @Param("fechaFinal") Date fechaFinal);
  
  @Query("SELECT recibo FROM Recibo recibo " + "WHERE recibo.estado = :estado "
    + " and recibo.iddeuda.idpago.descripcion like 'PAGO QR%' "
    + " and cast(recibo.iddeuda.idcliente.idempresa.idempresa as string) in ( "
    + "   SELECT parametrica.codigo FROM Parametrica parametrica "
    + "   WHERE parametrica.tipo = 'NUMERIC' "
    + "     and parametrica.valor = :idQR) "
    + " and recibo.iddeuda.idpago.fecha_alta between :fechaInicial and :fechaFinal "
    + "ORDER BY recibo.iddeuda.idpago.fecha_alta DESC")
  List<Recibo> liquidacionEmpresaQR(@Param("estado") String estado,
                                    @Param("fechaInicial") Date fechaInicial,
                                    @Param("fechaFinal") Date fechaFinal,
                                    @Param("idQR") String idQR);
  
  @Query(value = "SELECT r.* FROM pgt.recibo r INNER JOIN pgt.deuda d ON d.idrecibo=r.idrecibo "
    + " INNER JOIN pgt.cliente c ON c.idcliente=d.idcliente WHERE c.idempresa=:idempresa AND r.nro_recibo=:nropedido", nativeQuery = true)
  Optional<Recibo> transaccionPago(Long nropedido,
                                   Long idempresa);
}
