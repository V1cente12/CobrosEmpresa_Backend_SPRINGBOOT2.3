
package cobranza.v2.pgt.com.utils.otros;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="DESCRIPCION CLASE DE RETORNO E2C021">

/**
 *
 * @author eveliz
 */
// </editor-fold>
@XmlRootElement
public class E2c021 implements
                    Serializable {
  
  private static final long serialVersionUID = 1L;
  private Long id;
  private String responseFlag; // Estado de la ejecucion
  private String replyMessage; // Mensaje de la ejecucion
  private List<DebtDetails> detalleDeuda;
  private List<DataMap> dataMap;
  
  // <editor-fold defaultstate="collapsed" desc="GETTER/SETTERS">
  
  public List<DebtDetails> getDetalleDeuda( ) { return detalleDeuda; }
  
  public void setDetalleDeuda(List<DebtDetails> detalleDeuda) { this.detalleDeuda = detalleDeuda; }
  
  public String getResponseFlag( ) { return responseFlag; }
  
  public void setResponseFlag(String responseFlag) { this.responseFlag = responseFlag; }
  
  public String getReplyMessage( ) { return replyMessage; }
  
  public void setReplyMessage(String replyMessage) { this.replyMessage = replyMessage; }
  
  /**
   *
   * @return
   */
  public Long getId( ) { return id; }
  
  /**
   *
   * @param id
   */
  public void setId(Long id) { this.id = id; }
  
  public List<DataMap> getDataMap( ) { return dataMap; }
  
  public void setDataMap(List<DataMap> dataMap) { this.dataMap = dataMap; }
  
  // </editor-fold>
  // <editor-fold defaultstate="collapsed" desc="UTILS">
  
  @Override
  public int hashCode( ) {
    int hash = 0;
    hash += (id != null ? id.hashCode( ) : 0);
    return hash;
  }
  
  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof E2c021)) { return false; }
    E2c021 other = ( E2c021 ) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString( ) { return "E2c021[ id=" + id + " ]"; }
  // </editor-fold>
}
