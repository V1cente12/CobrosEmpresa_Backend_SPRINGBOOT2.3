
package cobranza.v2.pgt.com.models.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IImagenServ {
  
  public void subir(MultipartFile imagen,
                    String nombre);
}
