
package cobranza.v2.pgt.com.utils.excepcions;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {
  
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler({UnauthorizedException.class,AccessDeniedException.class})
  @ResponseBody
  public ErrorMessage unauthorized(HttpServletRequest request,
                                   Exception exception) {
    return new ErrorMessage(exception, request, new Date( ), "");
  }
  
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({NotFoundException.class})
  @ResponseBody
  public ErrorMessage notFoundRequest(HttpServletRequest request,
                                      Exception exception) {
    return new ErrorMessage(exception, request, new Date( ), "");
  }
  
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({DataAccessException.class,ConstraintViolationException.class,BadRequestException.class,
                     DuplicateKeyException.class,HttpRequestMethodNotSupportedException.class,
                     MethodArgumentNotValidException.class,HttpMessageNotReadableException.class})
  @ResponseBody
  public ErrorMessage badRequest(MethodArgumentNotValidException ex,
                                 HttpServletRequest request,
                                 Exception exception) {
    List<String> errors = ex.getBindingResult( )
                            .getFieldErrors( )
                            .stream( )
                            .map(error -> error.getField( ) + ": " + error.getDefaultMessage( ))
                            .collect(Collectors.toList( ));
    List<String> globalErrors = ex.getBindingResult( )
                                  .getGlobalErrors( )
                                  .stream( )
                                  .map(error -> error.getObjectName( ) + " -> " + error.getDefaultMessage( ))
                                  .collect(Collectors.toList( ));
    return new ErrorMessage(
      exception,
      "Validacion de datos",
      request,
      new Date( ),
      Stream.concat(globalErrors.stream( ), errors.stream( ))
            .collect(Collectors.toList( )));
  }
  
  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler({ConflictException.class})
  @ResponseBody
  public ErrorMessage conflict(HttpServletRequest request,
                               Exception exception) {
    return new ErrorMessage(exception, request, new Date( ), "");
  }
  
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler({ForbiddenException.class})
  @ResponseBody
  public ErrorMessage forbidden(HttpServletRequest request,
                                Exception exception) {
    return new ErrorMessage(exception, request, new Date( ), "");
  }
  
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({Exception.class})
  @ResponseBody
  public ErrorMessage exception(HttpServletRequest request,
                                Exception exception) {
    return new ErrorMessage(exception, request, new Date( ), "");
  }
  
}
