
package cobranza.v2.pgt.com.utils.excepcions;

public class JwtException extends UnauthorizedException {
  
  private static final String DESCRIPTION = "Jwt exception";
  
  public JwtException(String detail) { super(DESCRIPTION + ". " + detail); }
}
