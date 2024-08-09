/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. */

package cobranza.v2.pgt.com.utils.otros;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author eveliz
 */
public class DataMap implements
                     Serializable {
  
  Map<String, Object> dMap;
  
  public DataMap() {}
  
  public DataMap(Map dataMap) { this.dMap = dataMap; }
  
  public Map<String, Object> getdMap( ) { return dMap; }
  
  public void setdMap(Map<String, Object> dMap) { this.dMap = dMap; }
  
  public void setData(String k,
                      Object o) {
    if (dMap == null) {
      dMap = new HashMap<>( );
    }else {
      dMap.put(k, o);
    }
  }
  
  public Object getData(String k) { return dMap.get(k); }
}
