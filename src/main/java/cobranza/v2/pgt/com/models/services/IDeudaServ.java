
package cobranza.v2.pgt.com.models.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.entity.Deuda;
import cobranza.v2.pgt.com.models.entity.Recibo;

@Service
public interface IDeudaServ {
  
  public List<Deuda> saveAll(List<Deuda> deuda);
  
  public Deuda save(Deuda deuda);
  
  public List<Deuda> findAll(String estado);
  
  public Optional<Deuda> findById(Long id);
  
  public List<Deuda> archivo(List<Recibo> file,
                             String usuario);
  
  public Page<Deuda> findAll(Pageable p);
  
  public Page<Deuda> buscarEstadoPage(String estado,
                                      Pageable p);
  
  public Page<Deuda> buscarFilterBySortBy(Long idempresa,
                                          String estado,
                                          String nombre,
                                          Date fechaI,
                                          Date fechaF,
                                          Pageable p);
  
  public List<?> buscarByIdEmpresaFechaEstado(Long idempresa,
                                              String estado,
                                              Date fechaI,
                                              Date fechaF);
  
  public byte[ ] reporteDeudaEstado(String estadodeuda,
                                    Long idempresa,
                                    String nombre,
                                    String fechaI,
                                    String fechaF);
  
  public byte[ ] reporteListaDeuda(String estadodeuda,
                                   Long idempresa,
                                   String nombre,
                                   String fechaI,
                                   String fechaF);
  
  public List<Deuda> cobrosLaFuente(MultipartFile file,
                                    String usuario);
  
  public Page<Deuda> cliente(String estado,
                             String nombre,
                             Pageable p);
  
  public BigDecimal totaldeuda(String estado,
                               Long idempresa);
  
  BigDecimal monto(String iddeuda);
  
  public boolean enviarcorrreo(String correo,
                               String url,
                               String id,
                               String idempresa,
                               String codigocliente);
  
  public boolean enviarcorrreo2(String correo,
                                String url,
                                String id,
                                Deuda deuda,
                                String token);
  public boolean fechavencida(String iddeuda,
                              String idempresa,
                              String fechavencida) throws ParseException;
  
  List<?> listaReporte(Long idempresa,
                       String estado,
                       Date fechaI,
                       Date fechaF);
  public Map<String, Object> archivo(MultipartFile file,
                                     String usuario);
  /**
  		 * Cliente
  		 */
  
  public Page<Deuda> buscarEstadoNombre(String estado,
                                        Long idempresa,
                                        String nombre,
                                        Pageable p);
  
  public List<Deuda> findByIdduedaIn(List<Long> ids);
  
  public void envioCorreo(List<Long> telf,
                          List<Long> correo,
                          Long idbitacora) throws Exception;
  Optional<Deuda> obtenerDeudaNroRecibo(String nrorecibo);
  public Page<Deuda> ListaSeguroVida(Long idempresa,
                                     String estado,
                                     String nombre,
                                     Date fechaI,
                                     Date fechaF,
                                     Pageable p);
}
