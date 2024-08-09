
package cobranza.v2.pgt.com.utils.otros;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Response {
  
  public List<Content> content;
  public CustomPage customPage;
}