
package cobranza.v2.pgt.com.utils.excepcions;

public class MailException extends ConflictException {
  
  private static final String DESCRIPTION = "Mail exception";
  
  public MailException(String detail) { super(DESCRIPTION + ". " + detail); }
}