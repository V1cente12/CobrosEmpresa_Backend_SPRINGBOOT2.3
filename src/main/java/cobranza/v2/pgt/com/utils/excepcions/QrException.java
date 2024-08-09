
package cobranza.v2.pgt.com.utils.excepcions;

public class QrException extends
                         Exception {
  
  public QrException() {}
  
  public QrException(String message) { super(message); }
  
  public QrException(String message,
                     Throwable cause) { super(message, cause); }
  
  public QrException(Throwable cause) { super(cause); }
  
  public QrException(String message,
                     Throwable cause,
                     boolean enableSuppression,
                     boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
