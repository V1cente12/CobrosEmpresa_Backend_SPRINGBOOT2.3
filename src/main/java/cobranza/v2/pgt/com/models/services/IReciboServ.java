
package cobranza.v2.pgt.com.models.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.entity.Recibo;
import cobranza.v2.pgt.com.models.entity.Shopify;

@Service
public interface IReciboServ {
  
  List<Recibo> findAll(String estado);

  void updatemonto(BigDecimal monto,
                          Long idrecibo);
  
  Optional<Recibo> findID(Long id);
  
  List<Recibo> saveAll(List<Recibo> recibo);
  
  List<Recibo> archivo(MultipartFile file,
                              String usuario);
  
  List<?> totalMontoPeriodo( );
  
  List<?> cantidadDeuda( );
  
  List<Recibo> guardar(List<Recibo> p);
  
  Recibo guardar(Recibo recibo);
  
  Optional<Recibo> ExisteNroPedido(Long nropedido,
                                          Long idempresa);
  boolean SendComprobante(Recibo r) throws Exception;

  Recibo FindByNroReciboIdempresa(Long nro_recibo,
                                         Long idempresa);
  List<?> transaccionpago(Long idempresa,
                                 String estado,
                                 Date fechaI,
                                 Date fechaF);
  void updatekiosco(String valor,
                           Long idrecibo);

  Page<Recibo> buscarFilterBySortBy(String codigopago,
                                           Date fechaI,
                                           Date fechaF,
                                           Pageable p);
  Map<String, Object> enviarRecibo(Long idrecibo,
                                          String dato,
                                          Map<String, Object> response) throws Exception;
  Map obtenerRecibos(String list);
  
  List<?> ListMultimoneda(List<Long> idrecibo);

  List<?> ListaLiquidacionEmpresa(String estado,
                                  Date fechaI,
                                  Date fechaF);
  List<?> Liquidacion(String idempresa,
                      String estado,
                      Date fechaI,
                      Date fechaF);
  byte[ ] reporteLiquidacion();

  Map<String, Object> CallbacKAuthenticationBasic(Shopify shopify,
                                                         String id,
                                                         String tipo);

  List<Recibo> liquidacionEmpresa(String estado, Date fechaI, Date fechaF);

  // public String tokenvisanet();
  //
  // public Map<String, Object> sesionvisanet(String
  // data, String merchantId);
  //
  // public Map<String, Object>
  // autorizacionvisanet(String data, Long id);
  Optional<Recibo> transaccionPago(Long nropedido,
                                   Long idempresa);
}
