
package cobranza.v2.pgt.com.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ParametricaQueryDto {
  
  private String codigo;
  private String descripcion;
  private String dominio;
  private String estado;
  private String glosa;
  private String subdominio;
  private String tipo;
  private String valor;
}
