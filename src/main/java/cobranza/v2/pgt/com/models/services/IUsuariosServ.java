
package cobranza.v2.pgt.com.models.services;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.entity.Personas;
import cobranza.v2.pgt.com.models.entity.Roles;
import cobranza.v2.pgt.com.models.entity.Usuarios;

@Service
public interface IUsuariosServ {
  
  public String token( );
  public List<Usuarios> listarAll(String estado);
  public Usuarios listarID(String estado,
                           Long id);
  public Usuarios logeousuario(String login);
  public Usuarios save(Usuarios u);
  // public boolean loadEmpresas(Integer idempresa);
  public Usuarios guardar(Usuarios u,
                          Empresas e,
                          Personas p);
  // public String findXEmpresa(Integer id);
  public List<?> BuscarLogin(String buscar);
  public Page<?> ListaPersonasUsuario(String estado,
                                      String buscar,
                                      Pageable p);
  public Optional<Usuarios> existsSession(String login,
                                          String clave);
  // public Optional<?> usuario(String login);
  public String generarToken(Usuarios u) throws ParseException;
  public boolean verificarToken(String token);
  public void envioCredenciales(String pass,
                                Usuarios usuario,
                                String[ ] correos,
                                String[ ] footer,
                                String asunto);
  public boolean existsByIdusuario(Long id);
  public List<Roles> roles(Long idusurio);
  public Optional<Usuarios> existsNitEmpresaLoginTipo(String nit,
                                                      String login,
                                                      String tipo);
  public Optional<Usuarios> existsNitEmpresaLogin(String nit,
                                                  String login);
  public void enviarTipoAleatorio(String codigo,
                                  Usuarios usuario,
                                  String[ ] correos,
                                  String[ ] footer,
                                  String asunto);
  public void cambioTipo(String nit,
                         String login,
                         String codigo);
  public void cambioPass(Long idusuario,
                         String pass);
  public List<Usuarios> ListarUsuariosIdempresa(Long idempresa,
                                                String estado);
  public Optional<Usuarios> obtenerLogin(String buscar);
  public Optional<Usuarios> BuscarLoginAndIdEmpresa(String login,
                                                    Long idempresa);
}
