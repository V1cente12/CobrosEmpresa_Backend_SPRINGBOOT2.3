
package cobranza.v2.pgt.com.models.services;

import cobranza.v2.pgt.com.models.entity.AtcProfileEmpresa;

public interface IProfileEmpresaServ {
  
  AtcProfileEmpresa obtenerId(Long idprofile);
  
  AtcProfileEmpresa save(AtcProfileEmpresa entity);
}
