
package cobranza.v2.pgt.com.models.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cobranza.v2.pgt.com.models.entity.Link;

@Repository
public interface ILinkDao extends
                          JpaRepository<Link, Long> {
  
  Link findByEstadoAndIdlink(String estado,
                             Long id);
  
  Link findByIddeudaAndIdbitacora(String iddeuda,
                                  Long idbitacora);
  
  List<Link> findByEstadoAndIdcliente(String estado,
                                      Long id);
  public Optional<Link> findByIddeuda(String iddeuda);
  
  @Query(value = "SELECT * FROM pgt.links WHERE iddeuda=:iddeuda ORDER BY idlink DESC LIMIT 1", nativeQuery = true)
  public Optional<Link> findByIddeudaDesc(@Param("iddeuda") String iddeuda);
  
  @Query(value = "SELECT l.* FROM pgt.links l LEFT JOIN pgt.cliente c ON c.idcliente=l.idcliente "
    + " LEFT JOIN pgt.persona p ON p.idpersona=c.idpersona "
    + " LEFT JOIN pgt.empresa e ON e.idempresa=c.idempresa WHERE l.estado LIKE  :estado% "
    + " AND (UPPER(concat(p.nombres,' ',p.apellido_paterno)) LIKE  :nombre% OR p.valor_documento LIKE :nombre%) "
    + " AND e.idempresa=:idempresa AND l.fecha_alta between :fechaI and :fechaF", nativeQuery = true)
  Page<Link> listaLinkPage(@Param("idempresa") Long idempresa,
                           @Param("estado") String estado,
                           @Param("nombre") String nombre,
                           @Param("fechaI") Date fechaI,
                           @Param("fechaF") Date fechaF,
                           Pageable p);
  
  @Query(value = "SELECT codigocliente,correo,telefono,concat(link,token) as link,monto,iddeuda,true as selected1,true as selected2 FROM pgt.links WHERE idbitacora=:idbitacora", nativeQuery = true)
  List<AuxilarDao> findByIdbitacora(@Param("idbitacora") Long idbitacora);
  @Query(value = "SELECT codigocliente,correo,telefono,concat(link,token) as link,monto FROM pgt.links WHERE idbitacora=:idbitacora", nativeQuery = true)
  List<AuxilarDaoSN> findByIdbitacoraSN(@Param("idbitacora") Long idbitacora);
  
  public static interface AuxilarDao {
    
    String getCodigocliente( );
    String getCorreo( );
    String getTelefono( );
    String getMonto( );
    String getIddeuda( );
    String getLink( );
    String getSelected1( );
    String getSelected2( );
  }
  
  public static interface AuxilarDaoSN {
    
    String getCodigocliente( );
    String getCorreo( );
    String getTelefono( );
    String getMonto( );
    String getLink( );
  }
  
  @Query(value = "select l.* from pgt.recibo r inner join pgt.deuda d on d.idrecibo =r.idrecibo inner join pgt.cliente c on c.idcliente =d.idcliente "
    + " inner join pgt.links l on l.idcliente =c.idcliente where r.nombre_apellido like concat(:id,'%') and l.iddeuda =cast(d.iddeuda as varchar) limit 1", nativeQuery = true)
  public Optional<Link> obtenerLinkShopify(@Param("id") String id);
}
