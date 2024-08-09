
package cobranza.v2.pgt.com.models.dao;

import java.math.BigDecimal;
import java.util.Date;
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

import cobranza.v2.pgt.com.models.entity.Empresas;

@Repository
public interface IEmpresasDao extends
                              JpaRepository<Empresas, Long> {
  
  @Query(value = "SELECT * FROM pgt.empresa WHERE idempresa= :idempresa and estado=:estado", nativeQuery = true)
  Empresas buscarEmpresaID(@Param("idempresa") Long idempresa,
                           @Param("estado") String estado);
  
  @Query(value = "SELECT * FROM pgt.empresa WHERE estado=:estado AND (UPPER(razon_social) LIKE "
    + " UPPER(concat('%',:buscar,'%')) OR UPPER(nombre) LIKE  UPPER(concat('%',:buscar,'%')) "
    + " OR nit LIKE concat('%',:buscar,'%'))", nativeQuery = true)
  Page<AuxilarDao> empresaPage(@Param("estado") String estado,
                               @Param("buscar") String buscar,
                               Pageable p);
  
  @Transactional
  @Modifying
  @Query(value = "update pgt.empresa set estado = :estado, usuario_modificacion=:usuario, fecha_modificacion= now() where idempresa =:id", nativeQuery = true)
  int cambio(@Param("estado") String estado,
             @Param("usuario") String usuario,
             @Param("id") Long id);
  
  public List<Empresas> findByEstado(String estado);
  
  public Empresas findByEstadoAndIdempresa(String estado,
                                           Long id);
  
  @Query(value = "SELECT e.nombre,e.idempresa,e.idpadre,e.razon_social,e.nit,e.correos "
    + " FROM pgt.empresa e WHERE e.idempresa=:idempresa ", nativeQuery = true)
  Optional<?> ListaEmpresaAux(@Param("idempresa") Long idempresa);
  
  @Query(value = "SELECT e.idempresa,e.razon_social,e.idpadre,e.nit,e.correos,e.estado FROM pgt.empresa e "
    + "WHERE e.nit=:buscar OR e.correos=:buscar", nativeQuery = true)
  List<AuxilarDao> BuscarCorreosOrNit(@Param("buscar") String buscar);
  
  @Query(value = "SELECT e.idempresa,e.razon_social,e.idpadre,e.nit,e.correos,e.estado FROM pgt.empresa e WHERE e.estado=:estado ORDER BY e.razon_social ASC", nativeQuery = true)
  List<AuxilarDao> BuscarEmpresaEstado(@Param("estado") String estado);
  
  public static interface AuxilarDao {
    
    String getNombre( );
    
    Long getIdempresa( );
    
    Long getIdpadre( );
    
    String getRazon_social( );
    
    String getNit( );
    
    String getEstado( );
    
    String getCorreos( );
    
  }
  
  @Query(value = "select r.reference_number, p.valor_documento, case when p.tipo_documento = '1' then 'CI' when p.tipo_documento = '2' then 'NIT' else p.tipo_documento end as tipo_documento,"
    + " concat(p.nombres,' ',p.apellido_paterno,' ',p.apellido_materno) as nombre_completo , p.domicilio, p.nacionalidad, p.correo, r.nro_recibo as nro_pedido, "
    + " case when r.moneda = 2 then r.monto*6.97 else r.monto end as monto_bs , case when r.moneda = 1 then 0 else r.monto end as monto_usd , r.estado, r.fecha_alta as fecha_venta,"
    + " case when r.fecha_vencimiento is null  then r.fecha_alta else r.fecha_vencimiento end as fecha_vencimiento, pg.fecha_alta as fecha_pago, "
    + " case when substr( pg.descripcion, 1,7) = 'PAGO QR' then 'QR BNB' else 'ATC' end as forma_pago , case when substr( pg.descripcion, 1,7) = 'PAGO QR' then '2' else '2.5' end as comision ,"
    + " pgt.desc_empresa(CAST(c.idempresa as text)) grupo_empresa, case when pgt.desc_empresa(r.codigo_pago) = 'MEMORIAL PARK' then 'CMP INVERSIONES' when pgt.desc_empresa(r.codigo_pago) = 'string' then '' else pgt.desc_empresa(r.codigo_pago) end as empresa,"
    + " c.idempresa from pgt.persona p inner join pgt.cliente c on  p.idpersona = c.idpersona inner join pgt.deuda d on c.idcliente = d.idcliente inner join pgt.recibo r on d.idrecibo = r.idrecibo "
    + " left join  pgt.pago pg on d.idpago = pg.idpago "
    + " where c.idempresa in (select idempresa from pgt.empresa) and r.estado = 'PAG' and c.idempresa=:idempresa and pg.fecha_alta between :fechaI and :fechaF", nativeQuery = true)
  public Page<CONCILIACION> CONCILIACION(@Param("fechaI") Date fechaI,
                                         @Param("fechaF") Date fechaF,
                                         @Param("idempresa") Long idempresa,
                                         Pageable p);
  
  public static interface CONCILIACION {
    
    String getReference_number( );
    String getValor_documento( );
    String getTipo_documento( );
    String getNombre_completo( );
    String getDomicilio( );
    String getNacionalidad( );
    String getCorreo( );
    String getNro_pedido( );
    BigDecimal getMonto_bs( );
    BigDecimal getMonto_usd( );
    String getEstado( );
    String getFecha_venta( );
    String getFecha_vencimiento( );
    String getFecha_pago( );
    String getForma_pago( );
    BigDecimal getComision( );
    String getGrupo_empresa( );
    String getEmpresa( );
    Long getIdempresa( );
  }
  
  @Query(value = "select r.reference_number, p.valor_documento, case when p.tipo_documento = '1' then 'CI' when p.tipo_documento = '2' then 'NIT' else p.tipo_documento end as tipo_documento,"
    + " concat(p.nombres,' ',p.apellido_paterno,' ',p.apellido_materno) as nombre_completo , p.domicilio, p.nacionalidad, p.correo, r.nro_recibo as nro_pedido, "
    + " case when r.moneda = 2 then r.monto*6.97 else r.monto end as monto_bs , case when r.moneda = 1 then 0 else r.monto end as monto_usd , r.estado, r.fecha_alta as fecha_venta,"
    + " case when r.fecha_vencimiento is null  then r.fecha_alta else r.fecha_vencimiento end as fecha_vencimiento, pg.fecha_alta as fecha_pago, "
    + " case when substr( pg.descripcion, 1,7) = 'PAGO QR' then 'QR BNB' else 'ATC' end as forma_pago , case when substr( pg.descripcion, 1,7) = 'PAGO QR' then '2' else '2.5' end as comision ,"
    + " pgt.desc_empresa(CAST(c.idempresa as text)) grupo_empresa, case when pgt.desc_empresa(r.codigo_pago) = 'MEMORIAL PARK' then 'CMP INVERSIONES' when pgt.desc_empresa(r.codigo_pago) = 'string' then '' else pgt.desc_empresa(r.codigo_pago) end as empresa,"
    + " c.idempresa from pgt.persona p inner join pgt.cliente c on  p.idpersona = c.idpersona inner join pgt.deuda d on c.idcliente = d.idcliente inner join pgt.recibo r on d.idrecibo = r.idrecibo "
    + " left join  pgt.pago pg on d.idpago = pg.idpago "
    + " where c.idempresa in (select idempresa from pgt.empresa) and r.estado = 'PAG' and c.idempresa=:idempresa and pg.fecha_alta between :fechaI and :fechaF", nativeQuery = true)
  public List<CONCILIACION> CONCILIACION_reporte(@Param("fechaI") Date fechaI,
                                                 @Param("fechaF") Date fechaF,
                                                 @Param("idempresa") Long idempresa);
  
  @Query(value = "select r.reference_number, p.valor_documento, case when p.tipo_documento = '1' then 'CI' when p.tipo_documento = '2' then 'NIT' else p.tipo_documento end as tipo_documento,"
    + " concat(p.nombres,' ',p.apellido_paterno,' ',p.apellido_materno) as nombre_completo , p.domicilio, p.nacionalidad, p.correo, r.nro_recibo as nro_pedido, "
    + " case when r.moneda = 2 then r.monto*6.97 else r.monto end as monto_bs , case when r.moneda = 1 then 0 else r.monto end as monto_usd , r.estado, r.fecha_alta as fecha_venta,"
    + " case when r.fecha_vencimiento is null  then r.fecha_alta else r.fecha_vencimiento end as fecha_vencimiento, pg.fecha_alta as fecha_pago, "
    + " case when substr( pg.descripcion, 1,7) = 'PAGO QR' then 'QR BNB' else 'ATC' end as forma_pago , case when substr( pg.descripcion, 1,7) = 'PAGO QR' then '2' else '2.5' end as comision ,"
    + " pgt.desc_empresa(CAST(c.idempresa as text)) grupo_empresa, case when pgt.desc_empresa(r.codigo_pago) = 'MEMORIAL PARK' then 'CMP INVERSIONES' when pgt.desc_empresa(r.codigo_pago) = 'string' then '' else pgt.desc_empresa(r.codigo_pago) end as empresa,"
    + " c.idempresa from pgt.persona p inner join pgt.cliente c on  p.idpersona = c.idpersona inner join pgt.deuda d on c.idcliente = d.idcliente inner join pgt.recibo r on d.idrecibo = r.idrecibo "
    + " left join  pgt.pago pg on d.idpago = pg.idpago "
    + " where c.idempresa in (select idempresa from pgt.empresa) and r.estado = 'PAG' and c.idempresa=:idempresa and pg.fecha_alta between :fechaI and :fechaF", nativeQuery = true)
  public List<Object[ ]> CONCILIACION_excel(@Param("fechaI") Date fechaI,
                                            @Param("fechaF") Date fechaF,
                                            @Param("idempresa") Long idempresa);
  @Transactional
  @Modifying
  @Query(value = "update pgt.empresa set logo =:logo where idempresa =:idempresa", nativeQuery = true)
  void subirlogo(@Param("logo") String logo,
                 @Param("idempresa") Long idempresa);
  
  @Query(value = "SELECT e.* FROM pgt.recibo r INNER JOIN pgt.deuda d ON d.idrecibo=r.idrecibo "
    + " INNER JOIN pgt.cliente c ON c.idcliente=d.idcliente INNER JOIN pgt.empresa e ON e.idempresa=c.idempresa "
    + " WHERE r.idrecibo=:idrecibo ", nativeQuery = true)
  Optional<Empresas> obtenerEmpresaIDrecibo(@Param("idrecibo") Long idrecibo);
}
