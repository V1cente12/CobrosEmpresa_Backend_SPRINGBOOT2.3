
package cobranza.v2.pgt.com.models.services;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.entity.Clientes;

@Service
public interface IClienteServ {
  
  public Page<Clientes> buscarEstadoNombre(String estado,
                                           Long idempresa,
                                           String nombre,
                                           Pageable p);
  
  public List<Clientes> saveAll(List<Clientes> cliente);
  
  public Clientes listarID(String estado,
                           Long id);
  
  public Clientes listarCodigoCliente(String codigo_cliente);
  
  public Clientes save(Clientes cliente);
  
  public Map<?, ?> archivo(MultipartFile file,
                           String usuario,
                           Long idempresa,
                           String encabezado) throws ParseException;
  
  public byte[ ] reporteClienteAll( );
  
  public byte[ ] reporteIDcliente(Long id,
                                  String cliente,
                                  Long idemepresa);
  
  public byte[ ] reporteALLcliente(String estadodeuda,
                                   Long idempresa);
  
  Optional<Clientes> buscarClienteEmpresa(String codigo,
                                          Long idempresa);
  
  boolean booleanClienteEmpresa(String codigo,
                                Long idempresa);
  
}
