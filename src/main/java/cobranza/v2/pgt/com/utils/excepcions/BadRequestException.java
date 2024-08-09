
package cobranza.v2.pgt.com.utils.excepcions;

public class BadRequestException extends RuntimeException {
  
  private static final String DESCRIPTION = "Bad Request Exception (400)";
  
  public BadRequestException(String detail) { super(DESCRIPTION + ". " + detail); }
}
