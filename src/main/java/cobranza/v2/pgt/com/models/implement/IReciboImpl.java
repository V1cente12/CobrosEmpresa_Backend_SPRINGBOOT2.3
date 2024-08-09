
package cobranza.v2.pgt.com.models.implement;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.dao.IDetalleDao;
import cobranza.v2.pgt.com.models.dao.IDeudaDao;
import cobranza.v2.pgt.com.models.dao.IEmpresasDao;
import cobranza.v2.pgt.com.models.dao.IReciboDao;
import cobranza.v2.pgt.com.models.dao.IReciboDao.AuxilarDao3;
import cobranza.v2.pgt.com.models.entity.Deuda;
import cobranza.v2.pgt.com.models.entity.Parametrica;
import cobranza.v2.pgt.com.models.entity.Recibo;
import cobranza.v2.pgt.com.models.entity.Shopify;
import cobranza.v2.pgt.com.models.services.EmailService;
import cobranza.v2.pgt.com.models.services.IDeudaServ;
import cobranza.v2.pgt.com.models.services.IParametricaServ;
import cobranza.v2.pgt.com.models.services.IReciboServ;
import cobranza.v2.pgt.com.utils.Auxiliar;
import cobranza.v2.pgt.com.utils.otros.DebtDetails;
import cobranza.v2.pgt.com.utils.otros.E2c021;
import cobranza.v2.pgt.com.utils.otros.MailRequest;

@Service
public class IReciboImpl implements
                         IReciboServ {
  
  public String MERCHANT_ID = "522591303";
  
  public String USER = "integraciones.visanet@necomplus.com";
  
  public String PASS = "d5e7nk$M";
  
  public String URL_API_AUTHORIZATION = "https://apitestenv.vnforapps.com/api.security/v1/security";
  
  public String URL_API_SESSION =
                                "https://apitestenv.vnforapps.com/api.ecommerce/v2/ecommerce/token/session/";
  
  public String URL_API_AUTORIZACION =
                                     "https://apitestenv.vnforapps.com/api.authorization/v3/authorization/ecommerce/";
  
  @Value("#{'${name.url}'}")
  private String URL;
  @Value("#{'${name.port}'}")
  private String PORT;
  @Value("#{'${idqr}'}")
  private String IdQr;
  private Logger logger = LoggerFactory.getLogger(IReciboImpl.class);
  
  @Autowired
  private IReciboDao reciboDao;
  @Autowired
  private IDeudaDao deudaDao;
  @Autowired
  private IEmpresasDao empresaDao;
  
  @Autowired
  private IDetalleDao detalleDao;
  @Autowired
  private IDeudaServ deudaServ;
  @Autowired
  private IParametricaServ paraServ;
  
  @Autowired
  private Auxiliar aux;
  @Value("#{'${empresa.datec}'}")
  private String DATEC;
  @Value("#{'${empresa.dibel}'}")
  private String DIBEL;
  @Autowired
  private EmailService emailServ;
  
  @Override
  public List<Recibo> findAll(String estado) { return ( List<Recibo> ) reciboDao.findAll( ); }
  
  @Override
  @Transactional
  public List<Recibo> saveAll(List<Recibo> recibo) { return ( List<Recibo> ) reciboDao.saveAll(recibo); }
  
  @Override
  @Transactional
  public List<Recibo> archivo(MultipartFile file,
                              String usuario) {
    Long inicia = System.currentTimeMillis( );
    Workbook libro = aux.getlibro(file);
    Sheet recibo = libro.getSheetAt(0);
    List<Recibo> lstCustomers = new ArrayList<Recibo>( );
    for(int i = 1;i <= recibo.getLastRowNum( );i++) {
      Recibo c = new Recibo( );
      Row fila = recibo.getRow(i);
      String CI = aux.cambio(fila.getCell(0));
      String periodo = aux.cambioLocalDate(( int ) fila.getCell(5)
                                                       .getNumericCellValue( ));
      Recibo r = reciboDao.buscarReciboCIyPeriodo(CI, periodo);
      if (r == null) {
        c.setConcepto_recibo(CI);
        c.setDescripcion_general(fila.getCell(4)
                                     .getStringCellValue( ));
        c.setPeriodo(periodo);
        c.setNro_recibo(( long ) fila.getCell(6)
                                     .getNumericCellValue( ));
        c.setMonto(new BigDecimal(
          fila.getCell(8)
              .getNumericCellValue( )));
        try {
          c.setFecha_vencimiento(aux.conversionStringDate(fila.getCell(9)));
        }
        catch(ParseException e) {
          e.printStackTrace( );
        }
        c.setUsuario_alta(usuario);
        c.setEstado("PEN");
        lstCustomers.add(c);
      }else {
        logger.error("Problemas con el ID: " + aux.cambio(fila.getCell(0)) + ", fila : " + (i + 1));
      }
    }
    logger.info("Duración " + (System.currentTimeMillis( ) - inicia) + " ms.");
    return lstCustomers;
    // return null;
  }
  
  @Override
  @Transactional
  public List<?> totalMontoPeriodo( ) { return reciboDao.totalMontoPeriodo( ); }
  
  @Override
  @Transactional
  public List<?> cantidadDeuda( ) { return reciboDao.cantidadDeuda( ); }
  
  @Override
  @Transactional
  public List<Recibo> guardar(List<Recibo> p) { return ( List<Recibo> ) reciboDao.saveAll(p); }
  
  @Override
  @Transactional
  public Recibo guardar(Recibo recibo) {
    Recibo r = reciboDao.save(recibo);
    return r;
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Recibo> findID(Long id) { return reciboDao.findById(id); }
  
  @Override
  @Transactional
  public Optional<Recibo> ExisteNroPedido(Long nropedido,
                                          Long idempresa) {
    return reciboDao.ExisteNroPedido(nropedido, idempresa);
  }
  
  @Override
  public boolean SendComprobante(Recibo r) throws Exception {
    Map<String, Object> response = new HashMap<>( );
    MailRequest mailrequest = new MailRequest( );
    Double total = 0.0;
    String fechaV = null, HTML = null;
    List<String> logo = null;
    String[ ] correo = {r.getGlosa2( )};
    total = r.getMonto( )
             .doubleValue( );
    try {
      fechaV = aux.ConvertDateString(r.getIddeuda( )
                                      .getIdpago( )
                                      .getFecha_alta( ), "dd/MM/yyyy hh:mm:ss");
    }
    catch(ParseException e) {
      logger.error("Error al parsear fecha de vencimiento: " + r.getIddeuda( )
                                                                .getIdpago( )
                                                                .getFecha_alta( ));
    }
    String nombre = r.getIddeuda( )
                     .getIdcliente( )
                     .getIdpersona( )
                     .getNombres( ) + " " + r.getIddeuda( )
                                             .getIdcliente( )
                                             .getIdpersona( )
                                             .getApellido_paterno( );
    response.put("nombre", nombre);
    response.put("numero", r.getReference_number( )
                            .split("-")[0]);
    response.put("total", total);
    response.put("concepto", "Pago(s) de:");
    logo = aux.convertStringListString(r.getIddeuda( )
                                        .getIdcliente( )
                                        .getIdempresa( )
                                        .getLogo( ));
    logger.info("-->> " + logo);
    response.put("logo", logo);
    response.put("moneda", r.getMoneda( ) == 0 || r.getMoneda( ) == 1 ? "Bs." : "$us");
    response.put("fechaV", fechaV);
    response.put("lista", r.getIddetalle( )
                           .size( ));
    response.put("detalle", r.getIddetalle( ));
    HTML = "pago_exitoso.html";
    response.put("html", HTML);
    if (r.getIddeuda( )
         .getIdcliente( )
         .getIdempresa( )
         .getIdempresa( )
         .toString( )
         .equals(DATEC)) {
      mailrequest.setFrom("pagos@datecstore.com");
      response.put("footer",
        "Si tiene alguna pregunta, inquietud o sugerencia, envíenos un correo electrónico: dstore@datecstore.com o escríbenos a nuestro número de whatsapp +59162002022");
    }else if (r.getIddeuda( )
               .getIdcliente( )
               .getIdempresa( )
               .getIdempresa( )
               .toString( )
               .equals(DIBEL)) {
                 mailrequest.setFrom("pagos@pagatodo360.net");
                 response.put("footer",
                   "Si tiene alguna pregunta, inquietud o sugerencia, envíenos un correo electrónico: soluciones@dibeltecnologia.com o escríbenos a nuestro número de whatsapp +591 63188443");
               }else {
                 mailrequest.setFrom("pagos@pagatodo360.net");
                 response.put("footer",
                   "Si tiene alguna pregunta, inquietud o sugerencia, envíenos un correo electrónico: ventas@pagatodo360.net o llamarnos a nuestro número 315-1100 Int,5358");
               }
    mailrequest.setSubject("Comprobante #" + r.getReference_number( )
                                              .split("-")[0]);
    response.put("nro_pago", r.getNro_recibo( )
                              .toString( ));
    response.put("mensaje", "Hemos terminado de procesar el pago de tu pedido numero #" + r.getNro_recibo( )
      + " para " + r.getIddeuda( )
                    .getIdcliente( )
                    .getIdempresa( )
                    .getRazon_social( ));
    logger.info("Parametros: " + new JSONObject(response));
    return emailServ.sendEmail(correo, mailrequest, response, "pago_exitoso.html", "");
  }
  
  @Override
  @Transactional(readOnly = true)
  public Recibo FindByNroReciboIdempresa(Long nro_recibo,
                                         Long idempresa) {
    return reciboDao.findByNroreciboIdempresa(nro_recibo, idempresa);
  }
  
  @Override
  public void updatemonto(BigDecimal monto,
                          Long idrecibo) { reciboDao.updatemonto(monto, idrecibo); }
  
  @Override
  @Transactional(readOnly = true)
  public List<?> transaccionpago(Long idempresa,
                                 String estado,
                                 Date fechaI,
                                 Date fechaF) {
    List<?> dato = reciboDao.transaccionpago(idempresa, estado, fechaI, fechaF);
    return dato;
  }
  
  @Override
  public void updatekiosco(String valor,
                           Long idrecibo) { reciboDao.updatekiosco(valor, idrecibo); }
  
  @Override
  @Transactional(readOnly = true)
  public Page<Recibo> buscarFilterBySortBy(String codigopago,
                                           Date fechaI,
                                           Date fechaF,
                                           Pageable p) {
    return reciboDao.listaFilterBySortBy(Arrays.asList(codigopago.split(",")), fechaI, fechaF, p);
  }
  
  @Override
  @Transactional
  public Map<String, Object> enviarRecibo(Long idrecibo,
                                          String dato,
                                          Map<String, Object> response) throws Exception {
    Map<String, Object> claims = new HashMap<>( );
    Recibo recibo = reciboDao.obtenerIDrecibo(idrecibo)
                             .get( );
    String jwtId = empresaDao.obtenerEmpresaIDrecibo(recibo.getIdrecibo( ))
                             .get( )
                             .getIdempresa( )
                             .toString( );
    Deuda deuda = deudaDao.obtenerDeudaIDrecibo(recibo.getIdrecibo( ))
                          .get( );
    String jwtSubject = recibo.getGlosa2( );
    claims.put("references", "0");
    String jwt = aux.createJWT2(jwtId, deuda.getIddeuda( )
                                            .toString( ), jwtSubject, claims, recibo.getFecha_vencimiento( ));
    String url = URL + ":" + PORT + "/#/carrito?id=";
    String[ ] parts = dato.split(",");
    String part1 = parts[0];
    String part2 = parts[1];
    if (part1.equals("true")) {
      if (deudaServ.enviarcorrreo2(jwtSubject, url, jwtId, deuda, jwt)) {
        response.put("error", false);
        response.put("mensaje", "Se envio al correo :: " + recibo.getGlosa2( ));
      }else {
        response.put("error", true);
        response.put("mensaje", "Error al enviar el correo :: " + recibo.getGlosa2( ));
        return response;
      }
    }
    if (part2.equals("true")) {
      String part3 = parts[2];
      Auxiliar.sendUrl("591" + part3, "Por favor ingrese a su link de pago: " + url + jwt, "");
      response.put("error", false);
      response.put("mensaje", "Se envio al telefono");
    }
    logger.info(response.toString( ));
    return response;
  }
  
  @Override
  public Map obtenerRecibos(String list) {
    try {
      String sBuscada = list.split(";")[0];
      Map codBuscados = new HashMap( );
      for(int i = 1;i < list.split(";").length;i++) {
        logger.info("buscando :: " + list.split(";")[i]);
        codBuscados.put(list.split(";")[i], list.split(";")[i]);
      }
      String data = reciboDao.Appmovil(sBuscada);
      logger.info("Data obtenida..");
      JAXBContext context = null;
      context = JAXBContext.newInstance(E2c021.class);
      logger.info("Instanciando data....");
      Unmarshaller unmarshaller = context.createUnmarshaller( );
      StringReader readerResultado = new StringReader(data);
      E2c021 resp = ( E2c021 ) unmarshaller.unmarshal(readerResultado);
      logger.info("creando data....");
      for(DebtDetails det: resp.getDetalleDeuda( )) {
        if (codBuscados.get(det.getCodeResponse( )) != null) {
          codBuscados.put(det.getCodeResponse( ), det.getAddDebtDetailsParameters( )
                                                     .get("idRecibo"));
        }
      }
      return codBuscados;
    }
    catch(JAXBException ex) {
      return null;
    }
  }
  
  @Override
  public List<?> ListMultimoneda(List<Long> idrecibo) { return reciboDao.ListMultimoneda(idrecibo); }
  
  @Override
  @Transactional(readOnly = true)
  public List<?> ListaLiquidacionEmpresa(String estado,
                                         Date fechaI,
                                         Date fechaF) {
    // List<Parametrica> listpara = paraServ.ListarDominioSubdominio("QR_BNB", "ID_EMP");
    // List<Long> lista = new ArrayList<>( );
    // for(Parametrica parametrica: listpara) {
    // if (!parametrica.getCodigo( )
    // .equals("15")) lista.add(Long.valueOf(parametrica.getCodigo( )));
    // }
    List<AuxilarDao3> tarjeta = reciboDao.ListaLiquidacionEmpresaTarjeta(estado, fechaI, fechaF);
    logger.info("Tamaño de tarjeta :: " + tarjeta.size( ));
    List<AuxilarDao3> qr = reciboDao.ListaLiquidacionEmpresaQr(estado, fechaI, fechaF, IdQr);
    logger.info("Tamaño de Qr :: " + qr.size( ));
    List<AuxilarDao3> resultList2 = new ArrayList<AuxilarDao3>(tarjeta);
    for(int i = 0;i < qr.size( );i++) {
      int cont = 0;
      for(int j = 0;j < resultList2.size( );j++) {
        if (resultList2.get(j)
                       .getId( )
                       .equals(qr.get(i)
                                 .getId( ))) cont++;
      }
      if (cont == 0) resultList2.add(qr.get(i));
    }
    return resultList2;
    
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<?> Liquidacion(String idempresa,
                             String estado,
                             Date fechaI,
                             Date fechaF) {
    return reciboDao.liquidacionTarjeta(idempresa, estado, fechaI, fechaF);
  }
  
  @Override
  public byte[ ] reporteLiquidacion( ) {
    Map<String, Object> params = new HashMap<>( );
    params.put("fechas", "23-12-2021 al 23-12-2021");
    params.put("comercio", "Pagatodo360");
    params.put("nit", "12312312");
    params.put("tarjeta", "12312312");
    params.put("qr", "12312312");
    return aux.reporte("liquidacion", params);
  }
  
  @Override
  public Map<String, Object> CallbacKAuthenticationBasic(Shopify shopify,
                                                         String id,
                                                         String tipo) {
    logger.info("Lamando al callback autentificacion Basic ......................");
    Map<String, Object> responsecallback = new HashMap<>( );
    String cuerpo = "{\"kind\": \"capture\" ,\"authorization\": \"" + tipo + "\", \"source\": \"external\"}";
    String requestBody = "{" + "\"transaction\": " + cuerpo + "}";
    logger.info(requestBody);
    String base64Creds = Base64.getEncoder( )
                               .encodeToString((shopify.getApikey( ) + ":" + shopify.getPassword( ))
                                                                                                    .getBytes( ));
    logger.info("Basic " + base64Creds);
    try {
      responsecallback = aux.postRequest(shopify.getUrl( ) + id + "/transactions.json", requestBody, "Basic "
        + base64Creds);
      logger.info("-->> " + responsecallback.toString( ));
    }
    catch(Exception e) {
      logger.error("Error en el callback Shopify..");
    }
    return responsecallback;
  }
  
  @Override
  public List<Recibo> liquidacionEmpresa(String estado,
                                         Date fechaInicial,
                                         Date fechaFinal) {
    Parametrica parametrica = new Parametrica( );
    parametrica.setIdparametrica(1L);
    
    List<Recibo> liquidacionTarjeta = reciboDao.liquidacionEmpresaTarjeta(
            estado, parametrica, fechaInicial, fechaFinal);

    List<Recibo> liquidacionQr = reciboDao.liquidacionEmpresaQR(estado, fechaInicial, fechaFinal, IdQr);

    List<Recibo> lista = new ArrayList<>( );
    lista.addAll(liquidacionTarjeta);
    lista.addAll(liquidacionQr);
    
    return lista;
  }
  
  @Override
  @Transactional
  public Optional<Recibo> transaccionPago(Long nropedido,
                                          Long idempresa) {
    return reciboDao.transaccionPago(nropedido, idempresa);
  }
}
