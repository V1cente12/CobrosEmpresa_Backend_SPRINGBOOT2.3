
package cobranza.v2.pgt.com.models.implement;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.services.IImagenServ;

@Service
public class IImagenImpl implements
                         IImagenServ {
  
  @Value("#{'${path-dir}'}")
  private String _PATH;
  @Value("#{'${path-img}'}")
  private String _IMG;
  
  @Override
  public void subir(MultipartFile imagen,
                    String nombre) {
    try {
      String realPathtoUploads = new File("").getAbsolutePath( )
                                             .replace(this._PATH, "") + this._IMG;
      Path folder = Paths.get(realPathtoUploads);
      if (!Files.exists(folder)) { Files.createDirectories(folder); }
      Files.copy(imagen.getInputStream( ), folder.resolve(nombre));
    }catch(Exception err) {
      err.printStackTrace( );
    }
  }
  
}
