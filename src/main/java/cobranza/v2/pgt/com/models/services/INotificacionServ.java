
package cobranza.v2.pgt.com.models.services;

import java.text.ParseException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface INotificacionServ {
  
  public Map<?, ?> archivo(MultipartFile file, String encabezado) throws ParseException;
  public Map<?, ?> enviomsj(MultipartFile file, String encabezado) throws Exception;
  
}
