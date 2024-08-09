
package cobranza.v2.pgt.com.utils.excepcions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends
                               RuntimeException {
  
  /**
   * 
   */
  private static final long serialVersionUID = 4358366174563008808L;
  // private static final String DESCRIPTION = "Not Found Exception (404)";
  
  // public NotFoundException(String detail) { super(DESCRIPTION + ". " + detail); }
  public NotFoundException(String detail) { super(detail); }
}
