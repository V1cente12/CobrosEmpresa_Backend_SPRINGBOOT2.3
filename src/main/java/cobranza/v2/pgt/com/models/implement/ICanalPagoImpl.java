
package cobranza.v2.pgt.com.models.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.dao.ICanalPagoDao;
import cobranza.v2.pgt.com.models.dao.IParametricaDao;
import cobranza.v2.pgt.com.models.entity.EmpresaCanalPago;
import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.entity.Parametrica;
import cobranza.v2.pgt.com.models.services.ICanalPagoServ;

@Service
public class ICanalPagoImpl implements
                            ICanalPagoServ {
  
  @Autowired
  private IParametricaDao parametricaDao;
  @Autowired
  private ICanalPagoDao canalDao;
  
  @Override
  public boolean crearPago(Empresas idempresa,
                           List<String> myList,
                           String valorqr,
                           String usuario) {
    List<EmpresaCanalPago> listcanal = new ArrayList<EmpresaCanalPago>( );
    for(String string: myList) {
      EmpresaCanalPago canal = new EmpresaCanalPago( );
      canal.setIdempresa(idempresa.getIdempresa( ));
      canal.setCanalPagos(string);
      canal.setTipoCodigoBanco("AAA");
      if (string.equals("QR")) this.AddpagoQr(idempresa, valorqr, usuario);
      listcanal.add(canal);
    }
    System.out.println(listcanal.toString( ));
    try {
      listcanal = canalDao.saveAll(listcanal);
      System.out.println(listcanal.toString( ));
    }
    catch(Exception e) {
      e.printStackTrace( );
      return true;
    }
    return false;
  }
  
  private void AddpagoQr(Empresas empresa,
                         String valorqr,
                         String usuario) {
    List<Parametrica> listapara = new ArrayList<Parametrica>( );
    Parametrica para = new Parametrica( );
    para.setDominio("QR_BNB");
    para.setSubdominio("ID_EMP");
    para.setCodigo(empresa.getIdempresa( )
                          .toString( ));
    para.setDescripcion(empresa.getRazon_social( ));
    para.setTipo("NUMERIC");
    para.setValor(valorqr);
    para.setEstado("A");
    para.setUsuarioAlta(usuario);
    listapara.add(para);
    Parametrica para2 = new Parametrica( );
    para2.setDominio("NOTIFICACION");
    para2.setSubdominio("CORREO");
    para2.setCodigo(empresa.getIdempresa( )
                           .toString( ));
    para2.setDescripcion("ENVIA CORREO A CLIENTE");
    para2.setTipo("BOOLEAN");
    para2.setValor("true");
    para2.setEstado("A");
    para2.setUsuarioAlta(usuario);
    listapara.add(para2);
    parametricaDao.saveAll(listapara);
  }
}
