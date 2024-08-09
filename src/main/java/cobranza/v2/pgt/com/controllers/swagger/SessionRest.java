
package cobranza.v2.pgt.com.controllers.swagger;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.entity.Error;
import cobranza.v2.pgt.com.models.entity.Usuario;
import cobranza.v2.pgt.com.models.entity.Usuarios;
import cobranza.v2.pgt.com.models.services.IUsuariosServ;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("SessionRest")
@Api(value = "Session", description = "Operacion de session por Pagatodo360")
public class SessionRest {
  
  private static final Logger log = LoggerFactory.getLogger(SessionRest.class);
  
  @Autowired
  private IUsuariosServ usuarioServ;
  
  @ApiResponses(value = {@ApiResponse(code = 200, message = "La solicitud enviada es correcta...", response = Error.class),
                         @ApiResponse(code = 404, message = "La solicitud envieda no coincide con los datos requeridos..", response = Error.class),
                         @ApiResponse(code = 500, message = "Error en el servicio de la peticion de datos", responseContainer = "", response = Error.class)})
  @ApiOperation(value = "Creacion del token")
  @PostMapping
  public ResponseEntity<?> session(@Valid @RequestBody Usuario data) throws ParseException {
    Map<String, Object> response = new HashMap<>( );
    String token;
    Optional<Usuarios> u = usuarioServ.existsSession(data.getUser( ), data.getPass( ));
    if (u.isPresent( )) {
      token = usuarioServ.generarToken(u.get( ));
      response.put("token", token);
    }else {
      response.put("error", "Datos introducidos no existe en la BDD..!!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .body(response);
    }
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
}
