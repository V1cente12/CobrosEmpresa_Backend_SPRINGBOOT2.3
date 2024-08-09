
package cobranza.v2.pgt.com.models.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cobranza.v2.pgt.com.models.entity.Clientes;

@Repository
public interface IClienteDao extends
                             JpaRepository<Clientes, Long> {
  
  @Query(value = "SELECT * FROM pgt.cliente WHERE codigo_cliente= ?1", nativeQuery = true)
  Clientes buscarCodigoCliente(String codigo_cliente);
  
  @Query(value = "SELECT * FROM pgt.cliente WHERE codigo_cliente=:codigo and idempresa=:idempresa LIMIT 1", nativeQuery = true)
  Optional<Clientes> buscarClienteEmpresa(@Param("codigo") String codigo,
                                          @Param("idempresa") Long idempresa);
  
  @Query(value = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM pgt.cliente c WHERE c.codigo_cliente=:codigo and c.idempresa=:idempresa", nativeQuery = true)
  boolean booleanClienteEmpresa(@Param("codigo") String codigo,
                                @Param("idempresa") Long idempresa);
  
  @Query(value = "SELECT c.* FROM  pgt.persona p RIGHT JOIN pgt.cliente c on p.idpersona=c.idpersona "
    + " LEFT JOIN pgt.usuario u on u.idpersona=p.idpersona WHERE u.idpersona is null "
    + " AND c.estado=:estado  AND c.idempresa=:idempresa AND (UPPER(concat(p.nombre,' ',p.apellido_paterno)) "
    + " LIKE :titulo% OR p.valor_documento LIKE :titulo%) ORDER BY p.idpersona ASC", nativeQuery = true)
  Page<Clientes> listaFilterEstadoNombre(@Param("estado") String estado,
                                         @Param("titulo") String nombre,
                                         @Param("idempresa") Long idempresa,
                                         Pageable p);
  
  @Query(value = "SELECT * FROM pgt.cliente WHERE estado=:estado and idcliente=:id", nativeQuery = true)
  Clientes listaID(@Param("estado") String estado,
                   @Param("id") Long idcliente);
  
}
