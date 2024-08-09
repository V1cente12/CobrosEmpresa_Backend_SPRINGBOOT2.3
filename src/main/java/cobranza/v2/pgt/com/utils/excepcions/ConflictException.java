
package cobranza.v2.pgt.com.utils.excepcions;

public class ConflictException extends RuntimeException {
  
  private static final String DESCRIPTION = "Conflict Exception (409)";
  
  public ConflictException(String detail) { super(DESCRIPTION + ". " + detail); }
}
