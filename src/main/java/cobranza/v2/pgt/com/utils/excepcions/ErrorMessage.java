
package cobranza.v2.pgt.com.utils.excepcions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ErrorMessage {
  
  private final Date timestamp;
  private final String error;
  private final String message;
  private final String path;
  private final List<String> errors;
  
  public ErrorMessage(Exception exception, String message, HttpServletRequest path, Date timestamp,
  List<String> errors)
  {
    this.error = exception.getClass( ).getSimpleName( );
    // this.message = exception.getMessage( );
    this.message = message;
    this.path = path.getRequestURI( );
    this.timestamp = timestamp;
    this.errors = errors;
  }
  
  public ErrorMessage(Exception exception, HttpServletRequest path, Date timestamp, String errors) {
    this.error = exception.getClass( ).getSimpleName( );
    this.message = exception.getMessage( );
    this.path = path.getRequestURI( );
    this.timestamp = timestamp;
    this.errors = Arrays.asList(error);
  }
  
  public String getError( ) { return error; }
  
  public String getMessage( ) { return message; }
  
  public String getPath( ) { return path; }
  
  public Date getTimestamp( ) { return timestamp; }
  
  public List<String> getErrors( ) { return errors; }
  
  @Override
  public String toString( ) {
    return "ErrorMessage [timestamp=" + timestamp + ", error=" + error + ", message=" + message
    + ", path=" + path + ", errors=" + errors.toString( ) + "]";
  }
}
