
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

import cobranza.v2.pgt.com.models.entity.Personas;

@Repository
public interface IPersonasDao extends
                              JpaRepository<Personas, Long> {
  
  @Query(value = "SELECT p.* FROM pgt.cliente c LEFT JOIN pgt.empresa e on e.idempresa=c.idempresa"
    + " RIGHT JOIN pgt.persona p on p.idpersona=c.idpersona "
    + " WHERE c.idempresa=:idempresa and c.estado=:estado", nativeQuery = true)
  List<Personas> listarpersonaempresa(@Param("estado") String estado,
                                      @Param("idempresa") Long idempresa);
  
  @Query(value = "SELECT p.* FROM pgt.persona p WHERE p.estado=:estado and p.idpersona=:idpersona limit 1", nativeQuery = true)
  Personas obtenerByEstadoAndIdpersona(@Param("estado") String estado,
                                       @Param("idpersona") Long idpersona);
  
  @Query(value = "SELECT p.* FROM pgt.persona p WHERE p.estado='A' and p.idpersona=153750", nativeQuery = true)
  List<Personas> test( );
  
  @Modifying
  @Query(value = "update pgt.persona set estado = :estado, usuario_baja=:usuario, fecha_baja= now() where idpersona =:id", nativeQuery = true)
  void elimnar(@Param("estado") String estado,
               @Param("usuario") String usuario,
               @Param("id") Long id);
  
  @Query(value = "SELECT * FROM pgt.persona WHERE valor_documento= :valor limit 1", nativeQuery = true)
  Personas buscarValorDoc(@Param("valor") String valor_documento);
  
  @Query(value = "SELECT * FROM pgt.persona WHERE valor_documento= :valor limit 1", nativeQuery = true)
  Optional<AuxilarDao> buscarUnicoValor(@Param("valor") String valor_documento);
  
  @Query(value = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM pgt.persona c WHERE c.valor_documento= :valor", nativeQuery = true)
  boolean ValorDoc(@Param("valor") String valor_documento);
  
  @Query(value = "SELECT p.* FROM  pgt.persona p LEFT JOIN pgt.cliente c ON c.idpersona=p.idpersona "
    + " WHERE c.idpersona is null AND p.estado=:estado AND (UPPER(concat(p.nombres,' ',p.apellido_paterno)) "
    + " LIKE  :nombre% OR p.valor_documento LIKE :nombre%) ORDER BY p.idpersona ASC", nativeQuery = true)
  Page<Personas> listaPersonaNotUsuario(@Param("estado") String estado,
                                        @Param("nombre") String nombre,
                                        Pageable p);
  
  @Query(value = "SELECT p.* FROM pgt.persona p LEFT JOIN pgt.usuario u ON u.idpersona=p.idpersona "
    + " LEFT JOIN pgt.empresa e ON e.idempresa=u.idempresa "
    + " WHERE u.idempresa=:idempresa and u.estado=:estado", nativeQuery = true)
  List<Personas> listarpersonaUserEmp(@Param("estado") String estado,
                                      @Param("idempresa") Long idempresa);
  
  @Query(value = "SELECT p.correo,p.domicilio,p.tipo_documento,p.idpersona,p.nombres,p.apellido_paterno,p.apellido_materno,p.valor_documento, "
    + " p.estado_civil,p.profesion,p.nacionalidad FROM pgt.persona p "
    + " RIGHT JOIN pgt.usuario u ON u.idpersona=p.idpersona "
    + " RIGHT JOIN pgt.empresa e ON e.idempresa=u.idempresa WHERE p.valor_documento=:buscar", nativeQuery = true)
  List<AuxilarDao> BuscarRepresentanteEmpresa(@Param("buscar") String buscar);
  
  @Query(value = "SELECT p.correo,p.domicilio,p.tipo_documento,p.apellido_paterno,p.apellido_materno,p.idpersona,p.nombres,p.valor_documento,p.estado_civil,p.profesion,p.nacionalidad "
    + " FROM pgt.persona p WHERE p.estado=:estado AND (UPPER(concat(p.nombres,' ',p.apellido_paterno)) "
    + " LIKE  UPPER(concat('%',:nombre,'%')) OR p.valor_documento LIKE :nombre%) AND p.usuario_baja is null "
    + " ORDER BY p.idpersona DESC", nativeQuery = true)
  Page<AuxilarDao> listaBuscarEstadoNombre(@Param("estado") String estado,
                                           @Param("nombre") String nombre,
                                           Pageable p);
  
  @Query(value = "SELECT p.correo,p.domicilio,p.tipo_documento,p.apellido_paterno,p.apellido_materno,p.idpersona, "
    + " p.nombres,p.valor_documento,p.estado_civil,p.profesion,p.nacionalidad "
    + " FROM pgt.persona p INNER JOIN pgt.cliente c ON c.idpersona=p.idpersona"
    + " WHERE p.valor_documento=:valor and c.idempresa=:idempresa limit 1", nativeQuery = true)
  Optional<AuxilarDao> PersonaByClienteEmpresa(@Param("valor") String valor,
                                               @Param("idempresa") Long idempresa);
  
  @Query(value = "SELECT * FROM pgt.persona p INNER JOIN pgt.usuario u ON u.idpersona=p.idpersona "
    + " INNER JOIN pgt.empresa e ON e.idempresa=u.idempresa WHERE p.correo=:correo and e.idempresa=:idempresa limit 1", nativeQuery = true)
  public Optional<Personas> PersonaByCorreoPersonaAndIdempresa(@Param("correo") String correo,
                                                               @Param("idempresa") Long idempresa);
  
  @Query(value = "SELECT * FROM pgt.persona p INNER JOIN pgt.usuario u ON u.idpersona=p.idpersona "
    + " INNER JOIN pgt.empresa e ON e.idempresa=u.idempresa WHERE p.valor_documento=:valor and e.idempresa=:idempresa limit 1", nativeQuery = true)
  public Optional<Personas> PersonaByValordocumentoPersonaAndIdempresa(@Param("valor") String valor,
                                                                       @Param("idempresa") Long idempresa);
  
  @Modifying
  @Query(value = "update pgt.persona set correo = :correo where idpersona =:id", nativeQuery = true)
  void updateCorreo(@Param("correo") String correo,
                    @Param("id") Long id);
  
  public static interface AuxilarDao {
    
    Long getIdpersona( );
    
    String getNombres( );
    
    String getApellido_paterno( );
    
    String getApellido_materno( );
    
    Long getValor_documento( );
    
    String getEstado_civil( );
    
    String getProfesion( );
    
    String getNacionalidad( );
    
    String getDomicilio( );
    
    String getCorreo( );
    
    String getTipo_documento( );
    
  }
}
