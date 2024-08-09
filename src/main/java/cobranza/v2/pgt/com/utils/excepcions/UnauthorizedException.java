
package cobranza.v2.pgt.com.utils.excepcions;

public class UnauthorizedException extends RuntimeException {
  
  private static final String DESCRIPTION = "Unauthorized Exception (401)";
  
  public UnauthorizedException(String detail) { super(DESCRIPTION + ". " + detail); }
}
