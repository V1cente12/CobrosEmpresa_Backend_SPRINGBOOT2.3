
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

import cobranza.v2.pgt.com.models.entity.Usuarios;

@Repository
public interface IUsuariosDao extends
                              JpaRepository<Usuarios, Long> {
  
  List<Usuarios> findAllByEstado(String estado);
  Usuarios findByEstadoAndIdusuario(String estado,
                                    Long id);
  public Optional<Usuarios> findByLogin(String username);
  boolean existsByIdusuario(Long id);
  
  @Modifying
  @Query(value = "update pgt.usuario set estado = :estado, usuario_baja=:usuario, fecha_baja= now() where idpersona =:id", nativeQuery = true)
  void eliminar(@Param("estado") String estado,
                @Param("usuario") String usuario,
                @Param("id") Long id);
  
  @Modifying
  @Query(value = "update pgt.rol_usuario set estado =:estado, usuario_baja=:usuario, fecha_baja= now() from pgc_usuarios as u where u.idpersona=:id", nativeQuery = true)
  void eliminarRolUsr(@Param("estado") String estado,
                      @Param("usuario") String usuario,
                      @Param("id") Long id);
  
  // ------------------------------------------------------------------------------//
  // ------------------------------CLIENTE-----------------------------------------//
  // ------------------------------------------------------------------------------//
  
  @Modifying
  @Query(value = "update pgt.usuario set estado = :estado, usuario_baja=:usuario, fecha_baja= now() where idusuario = :id", nativeQuery = true)
  void BajaCliente(@Param("estado") String estado,
                   @Param("usuario") String usuario,
                   @Param("id") Long id);
  
  @Query(value = "SELECT u.clave,p.idpersona,p.nombres,p.valor_documento,CASE WHEN u.idpersona is null "
    + " THEN 0 ELSE 1 END AS usuario,e.razon_social,e.nit FROM pgt.persona p "
    + " LEFT JOIN pgt.usuario u ON u.idpersona=p.idpersona "
    + " LEFT JOIN pgt.empresa e ON e.idempresa=u.idempresa "
    + "WHERE p.estado=:estado AND (UPPER(p.nombres) LIKE UPPER(concat('%',:buscar,'%')) OR p.valor_documento LIKE :buscar%) "
    + " ORDER BY u.fecha_alta", nativeQuery = true)
  Page<AuxilarDao> ListaPersonasUsuario(@Param("estado") String estado,
                                        @Param("buscar") String buscar,
                                        Pageable p);
  
  @Query(value = "SELECT u.idpersona, u.login,u.idempresa FROM pgt.usuario u Where u.login=:buscar", nativeQuery = true)
  List<AuxilarDao2> BuscarLogin(@Param("buscar") String buscar);
  
  public static interface AuxilarDao {
    
    String getClave( );
    
    Long getIdpersona( );
    
    String getNombres( );
    
    String getValor_documento( );
    
    Integer getUsuario( );
    
    String getRazon_social( );
    
    Integer getNit( );
    
  }
  
  public static interface AuxilarDao2 {
    
    Long getIdpersona( );
    
    String getLogin( );
    
    Long getIdempresa( );
    
  }
  
  @Query(value = "SELECT u.* FROM pgt.usuario u INNER JOIN pgt.empresa e ON e.idempresa=u.idempresa "
    + " WHERE e.nit=:nit and u.login=:login", nativeQuery = true)
  public Optional<Usuarios> existsNitEmpresaLogin(@Param("nit") String nit,
                                                  @Param("login") String login);
  @Query(value = "SELECT u.* FROM pgt.usuario u INNER JOIN pgt.empresa e ON e.idempresa=u.idempresa "
    + " WHERE e.nit=:nit and u.login=:login  and u.tipo=:tipo", nativeQuery = true)
  public Optional<Usuarios> existsNitEmpresaLoginTipo(@Param("nit") String nit,
                                                      @Param("login") String login,
                                                      @Param("tipo") String tipo);
  @Transactional
  @Modifying
  @Query(value = "UPDATE pgt.usuario u2 SET tipo=:tipo WHERE (SELECT u.idusuario FROM pgt.usuario u "
    + " INNER JOIN pgt.empresa e ON e.idempresa=u.idempresa WHERE e.nit=:nit and u.login=:login) = u2.idusuario", nativeQuery = true)
  void cambiotipo(@Param("nit") String nit,
                  @Param("login") String login,
                  @Param("tipo") String tipo);
  @Transactional
  @Modifying
  @Query(value = "UPDATE pgt.usuario u2 SET clave=:pass WHERE  u2.idusuario=:idusuario", nativeQuery = true)
  void cambioPass(@Param("idusuario") Long idusuario,
                  @Param("pass") String pass);
  
  @Query(value = "SELECT u.* FROM pgt.usuario u INNER JOIN pgt.empresa e ON e.idempresa=u.idempresa WHERE u.login=:login and e.nit=:nit", nativeQuery = true)
  public Usuarios findByLoginAndNit(@Param("login") String login,
                                    @Param("nit") String nit);
  
  @Query(value = "SELECT * FROM pgt.usuario WHERE estado=:estado and idempresa=:idempresa", nativeQuery = true)
  public List<Usuarios> ListarUsuariosIdempresa(@Param("idempresa") Long idempresa,
                                                @Param("estado") String estado);
  
  @Query(value = "select * from pgt.usuario where login =:login and idempresa =:idempresa", nativeQuery = true)
  public Optional<Usuarios> BuscarLoginAndIdEmpresa(@Param("login") String login,
                                                    @Param("idempresa") Long idempresa);
}
