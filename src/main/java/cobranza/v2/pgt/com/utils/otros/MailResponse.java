
package cobranza.v2.pgt.com.utils.otros;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailResponse {

		private String message;

		private boolean status;

		public String getMessage( ) {
				return message;
		}

		public void setMessage(String message) {
				this.message = message;
		}

		public boolean isStatus( ) {
				return status;
		}

		public void setStatus(boolean status) {
				this.status = status;
		}

}
