
package cobranza.v2.pgt.com.controllers.swagger;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cobranza.v2.pgt.com.models.entity.Error;
import cobranza.v2.pgt.com.models.entity.Recibo;
import cobranza.v2.pgt.com.models.services.IReciboServ;
import cobranza.v2.pgt.com.utils.Auxiliar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("TransaccionPago")
@Api(value = "Session", description = "Operacion de transaccion de pago exitoso por Pagatodo360")
public class Transaccion {
  
  @Autowired
  private IReciboServ reciboServ;
  @Autowired
  private Auxiliar aux;
  
  @ApiResponses(value = {@ApiResponse(code = 200, message = "La solicitud enviada es correcta...", response = Error.class),
                         @ApiResponse(code = 400, message = "La peticion de datos no coincide sus valores..", response = Error.class),
                         @ApiResponse(code = 404, message = "La solicitud envieda no coincide con los datos requeridos..", response = Error.class),
                         @ApiResponse(code = 500, message = "Error en el servicio de la peticion de datos", response = Error.class)})
  @ApiOperation(value = "Transaccion de pago exitoso")
  @GetMapping("/porfecha")
  public ResponseEntity<?> transaccion(@RequestParam("idempresa") String idempresa,
                                       @RequestParam(defaultValue = "", name = "estado") String estado,
                                       @RequestParam(defaultValue = "", name = "FechaInicio") String fechaI,
                                       @RequestParam(defaultValue = "", name = "FechaFin") String fechaF) {
    Map<String, Object> response = new HashMap<>( );
    Date FechaI = null, FechaF = null;
    
    if (fechaI != null && !fechaI.equals("")) {
      try {
        FechaI = aux.conversionStringDate(fechaI, "dd/MM/yyyy");
      }
      catch(ParseException e1) {
        response.put("error", true);
        response.put("status", "400");
        response.put("data",
          "Fecha de inicio incorrecto, por favor en este formato: dd/MM/yyyy (dia/mes/año)");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(response);
      }
    }
    
    if (fechaF != null && !fechaF.equals("")) {
      try {
        FechaF = aux.conversionStringDate(fechaF, "dd/MM/yyyy");
      }
      catch(ParseException e1) {
        response.put("error", true);
        response.put("status", "400");
        response.put("data",
          "Fecha de final incorrecto, por favor en este formato: dd/MM/yyyy (dia/mes/año)");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(response);
      }
    }
    
    response.put("error", false);
    response.put("status", "200");
    System.out.println(Long.valueOf(idempresa) + " " + estado + " " + FechaI + " " + FechaF);
    response.put("data", reciboServ.transaccionpago(Long.valueOf(idempresa), estado, FechaI, FechaF));
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
  @ApiResponses(value = {@ApiResponse(code = 200, message = "La solicitud enviada es correcta...", response = Error.class),
                         @ApiResponse(code = 400, message = "La peticion de datos no coincide sus valores..", response = Error.class),
                         @ApiResponse(code = 404, message = "La solicitud envieda no coincide con los datos requeridos..", response = Error.class),
                         @ApiResponse(code = 500, message = "Error en el servicio de la peticion de datos", response = Error.class)})
  @ApiOperation(value = "Transaccion de numero de recibo")
  @GetMapping("/pornrorecibo")
  public ResponseEntity<?> transaccionNroReciboIdEmpresa(@RequestParam("idempresa") String idempresa,
                                                         @RequestParam(defaultValue = "", name = "nropedido") String nropedido) throws ParseException,
                                                                                                                                Exception {
    Map<String, Object> response = new HashMap<>( );
    Optional<Recibo> recibo = reciboServ.transaccionPago(Long.valueOf(nropedido), Long.valueOf(idempresa));
    if (!recibo.isPresent( )) {
      response.put("error", true);
      response.put("status", "400");
      response.put("data", "No existe el Nro. de pedido de la empresa");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                           .body(response);
    }
    String F = null, FV = null, FP = null, H = null, MP = null;
    String estado = recibo.get( )
                          .getEstado( )
                          .equals("PEN") ? "PENDIENTE" : recibo.get( )
                                                               .getEstado( )
                                                               .equals("PAG") ? "PAGADO" : "ANULADO";
    F = aux.ConversionDateString(recibo.get( )
                                       .getFecha_alta( ), "dd/MM/yyyy");
    FV = aux.ConversionDateString(recibo.get( )
                                        .getFecha_vencimiento( ), "dd/MM/yyyy");
    if (estado.equals("PAGADO")) {
      FP = aux.ConversionDateString(recibo.get( )
                                          .getIddeuda( )
                                          .getIdpago( )
                                          .getFecha_alta( ), "dd/MM/yyyy");
      H = aux.ConversionDateString(recibo.get( )
                                         .getIddeuda( )
                                         .getIdpago( )
                                         .getFecha_alta( ), "HH:mm");
      MP = recibo.get( )
                 .getIddeuda( )
                 .getIdpago( )
                 .getDescripcion( )
                 .split(":")[0].trim( )
                               .equals("PAGO QR") ? "QR" : "TARJETA";
    }
    String json = "{" + "\"Fecha pedido\": \"" + F + "\" ," + "\"Fecha vencimiento\": \"" + FV + "\" ,"
      + "\"Fecha pago\": \"" + FP + "\" ," + "\"Hora\": \"" + H + "\" ," + "\"Método de pago\": \"" + MP
      + "\" ," + "\"estado\": \"" + estado + "\"}";
    ObjectMapper mapper = new ObjectMapper( );
    JsonNode actualObj = mapper.readTree(json);
    response.put("status", "200");
    response.put("error", false);
    response.put("data", actualObj);
    return ResponseEntity.status(HttpStatus.OK)
                         .body(response);
  }
  
}
