
package cobranza.v2.pgt.com.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import cobranza.v2.pgt.com.models.entity.TipoEmpresa;
import cobranza.v2.pgt.com.models.services.ITipoEmpresaServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2/tipoempresa")
public class TipoEmpresaController {
  
  @Value("#{'${file.path}'}")
  private String path;
  
  @Autowired
  private ITipoEmpresaServ tipoempresaS;
  
  @Value("#{'${path-dir}'}")
  private String _PATH;
  @Value("#{'${path-img}'}")
  private String _IMG;
  
  @GetMapping
  public Iterable<TipoEmpresa> index(@RequestParam(defaultValue = "A", name = "estado") String estado) {
    return tipoempresaS.listarAll(estado);
  }
  
  @GetMapping(value = "/doc-contrato")
  public byte[ ] getFile( ) {
    try {
      DefaultResourceLoader loader = new DefaultResourceLoader( );
      InputStream is = loader.getResource("classpath:file/CONTRATO.docx")
                             .getInputStream( );
      byte[ ] doc = IOUtils.toByteArray(is);
      return doc;
    }catch(IOException ex) {
      throw new RuntimeException("IOError writing file to output stream");
    }
  }
  
  @PostMapping("/subir-doc2")
  public ResponseEntity<?> uploadFiles2(@RequestParam("files") MultipartFile[ ] files,
                                        @RequestParam("idempresa") String idempresa,
                                        HttpServletRequest request) throws IOException {
    InputStream inputStream = null;
    OutputStream outputStream = null;
    Map<String, Object> response = new HashMap<>( );
    
    // Empresas e = empresaS.listarID("A", Long.valueOf(idempresa));
    String rpath = request.getRealPath("")
                          .replace("\\cobranzaV2\\src\\main\\webapp", "");
    // System.out.println(rpath.replace("\\cobranzaV2\\src\\main\\webapp", ""));
    Path path2 = Paths.get(rpath);
    System.out.println(path2);
    for(MultipartFile file: files) {
      File newFile = new File(
        path + file.getOriginalFilename( )
                   .split("\\.")[0] + "-" + idempresa + "." + file.getOriginalFilename( )
                                                                  .split("\\.")[1]);
      inputStream = file.getInputStream( );
      if (!newFile.exists( )) { newFile.createNewFile( ); }
      outputStream = new FileOutputStream(newFile);
      int read = 0;
      byte[ ] bytes = new byte[1024];
      while((read = inputStream.read(bytes)) != -1) {
        outputStream.write(bytes, 0, read);
      }
    }
    response.put("archivos", ", el/los archivo(s) no se guardaron ");
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
}
