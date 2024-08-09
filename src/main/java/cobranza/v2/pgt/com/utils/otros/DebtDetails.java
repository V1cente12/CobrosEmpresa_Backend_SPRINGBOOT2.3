
package cobranza.v2.pgt.com.utils.otros;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author eveliz
 */
@XmlType
public class DebtDetails implements
                         Serializable {
  
  private String codeResponse;
  private String idDebtDetails;
  private String debtAmount;
  private String informationDebtDetails;
  private Map<String, String> addDebtDetailsParameters;
  
  public String getDebtAmount( ) { return debtAmount; }
  
  public void setDebtAmount(String debtAmount) { this.debtAmount = debtAmount; }
  
  public String getCodeResponse( ) { return codeResponse; }
  
  public void setCodeResponse(String codeResponse) { this.codeResponse = codeResponse; }
  
  public String getIdDebtDetails( ) { return idDebtDetails; }
  
  public void setIdDebtDetails(String idDebtDetails) { this.idDebtDetails = idDebtDetails; }
  
  public String getInformationDebtDetails( ) { return informationDebtDetails; }
  
  public void setInformationDebtDetails(String informationDebtDetails) {
    this.informationDebtDetails = informationDebtDetails;
  }
  
  public Map<String, String> getAddDebtDetailsParameters( ) { return addDebtDetailsParameters; }
  
  public void setAddDebtDetailsParameters(Map<String, String> addDebtDetailsParameters) {
    this.addDebtDetailsParameters = addDebtDetailsParameters;
  }
  
  @Override
  public int hashCode( ) {
    int hash = 0;
    hash += (idDebtDetails != null ? idDebtDetails.hashCode( ) : 0);
    return hash;
  }
  
  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof DebtDetails)) { return false; }
    DebtDetails other = ( DebtDetails ) object;
    return !((this.idDebtDetails == null && other.idDebtDetails != null) || (this.idDebtDetails != null
      && !this.idDebtDetails.equals(other.idDebtDetails)));
  }
  
}
