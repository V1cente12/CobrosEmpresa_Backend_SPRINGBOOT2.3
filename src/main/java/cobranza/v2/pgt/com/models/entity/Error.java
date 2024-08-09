
package cobranza.v2.pgt.com.models.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Message", description = "Mensaje de respuesta de la solicitud..")
public class Error {

		@ApiModelProperty(notes = "Mensaje de respuesta", name = "mensaje", example = "<<-- Mensaje de respuesta del REST-API de pagatodo..-->>", required = true)
		private String mensaje;

		public String getMensaje( ) {
				return mensaje;
		}

		public void setMensaje(String mensaje) {
				this.mensaje = mensaje;
		}

}
