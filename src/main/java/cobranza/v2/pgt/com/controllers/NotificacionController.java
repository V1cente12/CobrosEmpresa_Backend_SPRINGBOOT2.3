
package cobranza.v2.pgt.com.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.services.INotificacionServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class NotificacionController {
  
  @Autowired
  private INotificacionServ notificacionServ;
  
  @GetMapping(value = "notificacion/descarga")
  public byte[ ] getFile( ) {
    try {
      DefaultResourceLoader loader = new DefaultResourceLoader( );
      InputStream is = loader.getResource("classpath:file/notificacion.xlsx").getInputStream( );
      byte[ ] doc = IOUtils.toByteArray(is);
      return doc;
    }catch(IOException ex) {
      throw new RuntimeException("IOError writing file to output stream");
    }
  }
  
  @PostMapping("/notificacion/file")
  public ResponseEntity<?> subir(@RequestParam("file") MultipartFile file,
                                 @RequestParam("encabezado") String encabezado) throws Exception {
    Map<String, Object> response = new HashMap<>( );
    Map<?, ?> c = notificacionServ.archivo(file, encabezado);
    List<String> error = ( List<String> ) c.get("error");
    System.out.println("errores -> : " + error.size( ));
    if (error.size( ) == 0) {
      c.clear( );
      c = notificacionServ.enviomsj(file, encabezado);
      response.put("datos", c);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }else {
      response.put("datos", c);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    
  }
}
