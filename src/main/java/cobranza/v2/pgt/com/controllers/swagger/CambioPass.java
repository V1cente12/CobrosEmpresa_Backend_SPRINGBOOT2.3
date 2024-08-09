
package cobranza.v2.pgt.com.controllers.swagger;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.entity.Usuarios;
import cobranza.v2.pgt.com.models.services.IUsuariosServ;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("UpdatePass")
@Api(value = "Update", description = "Operacion de modificacion de clave de sesion por Pagatodo360")
public class CambioPass {
  
  private Logger logger = LoggerFactory.getLogger(CambioPass.class);
  @Autowired
  private IUsuariosServ usuarioServ;
  
  @Autowired
  private BCryptPasswordEncoder contrasenaCodificada;
  
  // @ApiResponses(value = {@ApiResponse(code = 200, message = "La solicitud enviada es correcta...", response =
  // Error.class),@ApiResponse(code = 404, message = "La solicitud envieda no coincide con los datos requeridos..",
  // response = Error.class),@ApiResponse(code = 500, message = "Error en el servicio de la peticion de datos",
  // responseContainer = "", response = Error.class)})
  // @ApiOperation(value = "Modificacion de la clave")
  // @PostMapping
  public ResponseEntity<?> updatepass(@RequestParam String login,
                                      @RequestParam String pass,
                                      @RequestParam String pass2) {
    Map<String, Object> response = new HashMap<>( );
    logger.info("Cambiando pass de login: " + login);
    Usuarios presente = usuarioServ.logeousuario(login);
    if (presente != null) {
      if (!pass.equals("") && !pass2.equals("")) {
        if (pass.equals(pass2)) {
          String cambio = pass;
          presente.setClave(contrasenaCodificada.encode(pass));
          usuarioServ.save(presente);
          logger.info("Se cambio la contraseña o pass..!! (" + cambio + ")");
          response.put("correcto", "Se cambio la contraseña o pass..!!");
          return ResponseEntity.status(HttpStatus.OK)
                               .body(response);
        }else {
          logger.error("Datos de las contraseña o pass, no coincide..!!");
          response.put("error", "Datos de las contraseña o pass, no coincide..!!");
          return ResponseEntity.status(HttpStatus.NOT_FOUND)
                               .body(response);
        }
      }else {
        logger.error("No se permite datos vacios pass o contraseña");
        response.put("error", "No se permite datos vacios pass o contraseña");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(response);
      }
    }else {
      logger.error("Login no existe en la BDD..!!");
      response.put("error", "Login no existe en la BDD..!!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
  }
}
