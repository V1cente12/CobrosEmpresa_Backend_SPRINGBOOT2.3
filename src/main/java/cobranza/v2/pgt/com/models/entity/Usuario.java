
package cobranza.v2.pgt.com.models.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class Usuario {
  
  @NotNull
  @NotBlank
  @ApiModelProperty(notes = "Usuario de acceso", name = "user", example = "null", required = true)
  private String user;
  @NotNull
  @NotBlank
  @ApiModelProperty(notes = "Clave de acceso", name = "pass", example = "null", required = true)
  private String pass;
  
  public String getUser( ) { return user; }
  
  public void setUser(String user) { this.user = user; }
  
  public String getPass( ) { return pass; }
  
  public void setPass(String pass) { this.pass = pass; }
  
}
