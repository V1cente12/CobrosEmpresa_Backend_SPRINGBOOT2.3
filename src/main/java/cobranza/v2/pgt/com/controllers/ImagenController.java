
package cobranza.v2.pgt.com.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.services.IEmpresaServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2/imagen")
public class ImagenController {
  
  private static final Logger log = LoggerFactory.getLogger(ImagenController.class);
  
  @Value("#{'${path-dir}'}")
  private String _PATH;
  @Value("#{'${path-img}'}")
  private String _IMG;
  @Autowired
  private IEmpresaServ empresaServ;
  
  @PostMapping("/subir")
  public ResponseEntity<?> subirimagen(@RequestParam("archivo") MultipartFile file,
                                       @RequestParam("name_archivo") String name_archivo) {
    Map<String, Object> response = new HashMap<>( );
    try {
      String realPathtoUploads = new File("").getAbsolutePath( )
                                             .replace(this._PATH, "") + this._IMG;
      if (!new File(realPathtoUploads).exists( )) { new File(realPathtoUploads).mkdir( ); }
      // String orgName = file.getOriginalFilename( ).split("\\.")[1];
      String orgName = name_archivo.split("\\.")[0].toUpperCase( ) + ".png";
      String filePath = realPathtoUploads + orgName;
      File dest = new File(filePath);
      if (dest.exists( )) {
        response.put("mensaje", "Intentar nuevamente");
        response.put("nombre", "imagennull.png");
        return ResponseEntity.status(HttpStatus.OK)
                             .body(response);
      }
      file.transferTo(dest);
      response.put("mensaje", "Logo subido.... \nse subio con exito la imagen.");
      response.put("nombre", orgName);
      return ResponseEntity.status(HttpStatus.CREATED)
                           .body(response);
    }
    catch(Exception err) {
      System.out.println(err);
      response.put("mensaje", "Error de conexion al subir la imagen");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
  }
  
  @PostMapping("/subirimagen/empresa")
  public ResponseEntity<?> subirimagen(@RequestParam("archivo") MultipartFile file,
                                       @RequestParam("id") Long idempresa) {
    log.info("---------------- SUBIENDO IMAGEN/LOGO ----------------");
    Map<String, Object> response = new HashMap<>( );
    Empresas e = empresaServ.listarID("A", Long.valueOf(idempresa));
    try {
      String realPathtoUploads = new File("").getAbsolutePath( )
                                             .replace(this._PATH, "") + this._IMG;
      log.info(realPathtoUploads);
      if (!new File(realPathtoUploads).exists( )) { new File(realPathtoUploads).mkdir( ); }
      String orgName = file.getOriginalFilename( )
                           .split("\\.")[1];
      orgName = e.getRazon_social( )
                 .replaceAll("\\s", "")
                 .toUpperCase( ) + "." + orgName;
      String filePath = realPathtoUploads + orgName;
      log.info(filePath);
      File dest = new File(filePath);
      if (dest.exists( )) {
        empresaServ.cambiologo(orgName + "," + e.getLogo( ), idempresa);
        response.put("mensaje", "Logo existente anteriormente con la misma extención : " + orgName.split(
          ".")[1]);
        response.put("nombre", orgName);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(response);
      }
      file.transferTo(dest);
      empresaServ.cambiologo(orgName + "," + e.getLogo( ), idempresa);
      response.put("mensaje", "Logo subido.... \nse subio con exito la imagen.");
      response.put("nombre", orgName);
      return ResponseEntity.status(HttpStatus.CREATED)
                           .body(response);
    }
    catch(Exception err) {
      System.out.println(err);
      response.put("mensaje", "Error de conexion al subir la imagen");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body(response);
    }
  }
}
