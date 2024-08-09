
package cobranza.v2.pgt.com.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.services.IDocumentoEmpresaServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2/documentoempresa")
public class DocumentoEmpresaController {
  
  @Value("#{'${file.path}'}")
  private String path;
  
  @Autowired
  private IDocumentoEmpresaServ documentoempresaServ;
  
  @GetMapping("/{idempmresa}")
  public ResponseEntity<?> findById(@PathVariable String idempmresa) {
    return ResponseEntity.status(HttpStatus.OK)
                         .body(documentoempresaServ.ObtenerIdEmpresa(Long.valueOf(idempmresa)));
  }
  
  @GetMapping(value = "/doc-archivo")
  public ByteArrayResource getFile(@RequestParam("nombrearchivo") String nombrearchivo) {
    try {
      System.out.println(nombrearchivo);
      File file = new File(path + "/" + nombrearchivo);
      Path paths = Paths.get(file.getAbsolutePath( ));
      ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(paths));
      // byte[ ] doc = IOUtils.toByteArray(is);
      return resource;
    }
    catch(IOException ex) {
      throw new RuntimeException("IOError writing file to output stream");
    }
  }
}
