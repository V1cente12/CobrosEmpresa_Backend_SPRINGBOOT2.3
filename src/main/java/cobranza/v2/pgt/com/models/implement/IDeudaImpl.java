
package cobranza.v2.pgt.com.models.implement;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import cobranza.v2.pgt.com.models.dao.IClienteDao;
import cobranza.v2.pgt.com.models.dao.IDeudaDao;
import cobranza.v2.pgt.com.models.dao.IEmpresasDao;
import cobranza.v2.pgt.com.models.dao.ILinkDao;
import cobranza.v2.pgt.com.models.dao.IReciboDao;
import cobranza.v2.pgt.com.models.entity.Clientes;
import cobranza.v2.pgt.com.models.entity.Contrato;
import cobranza.v2.pgt.com.models.entity.Deuda;
import cobranza.v2.pgt.com.models.entity.Link;
import cobranza.v2.pgt.com.models.entity.Personas;
import cobranza.v2.pgt.com.models.entity.Recibo;
import cobranza.v2.pgt.com.models.services.EmailService;
import cobranza.v2.pgt.com.models.services.IDeudaServ;
import cobranza.v2.pgt.com.utils.Auxiliar;
import cobranza.v2.pgt.com.utils.otros.ErroresArchivo;
import cobranza.v2.pgt.com.utils.otros.MailRequest;

@Service
public class IDeudaImpl implements
                        IDeudaServ {
  
  private Logger logger = LoggerFactory.getLogger(IDeudaImpl.class);
  private int premier[] = {1,1,1,1,1,1,1,1};
  @Autowired
  private IDeudaDao deudaDao;
  @Autowired
  private IReciboDao reciboDao;
  @Autowired
  private ILinkDao linkDao;
  @Autowired
  private IEmpresasDao empresaDao;
  // @Autowired
  // private IContratoDao contratoDao;
  @Autowired
  private IClienteDao clienteDao;
  // @Autowired
  // private IReciboDao reciboDao;
  @Autowired
  private Auxiliar aux;
  @Autowired
  private EmailService emailServ;
  
  @Value("#{'${empresa.premier}'}")
  private String PREMIER;
  
  @Value("#{'${empresa.datec}'}")
  private String DATEC;
  @Value("#{'${empresa.dibel}'}")
  private String DIBEL;
  @Value("#{'${empresa.genio}'}")
  private String GENIO;
  @Value("#{'${empresa.dstore}'}")
  private String Dstore;
  @Value("#{'${url-imagen}'}")
  private String UrlImagen;
  
  @Override
  public List<Deuda> saveAll(List<Deuda> deuda) { return deudaDao.saveAll(deuda); }
  
  @Override
  public List<Deuda> archivo(List<Recibo> file,
                             String usuario) {
    Long inicia = System.currentTimeMillis( );
    List<Deuda> lstCustomers = new ArrayList<Deuda>( );
    for(int i = 0;i < file.size( );i++) {
      Deuda d = new Deuda( );
      Clientes c = clienteDao.buscarCodigoCliente(file.get(i)
                                                      .getConcepto_recibo( ));
      d.setIdrecibo(file.get(i));
      d.setIdcliente(c);
      d.setUsuario_alta(usuario);
      d.setEstado("PEN");
      lstCustomers.add(d);
    }
    logger.info("Duración " + (System.currentTimeMillis( ) - inicia) + " ms.");
    return lstCustomers;
  }
  
  @Override
  @Transactional
  public List<Deuda> findAll(String estado) {
    Long inicia = System.currentTimeMillis( );
    logger.info("Generando listado de deuda.... ");
    List<Deuda> l = deudaDao.findByEstadoOrderByIddeudaDesc(estado);
    logger.info("Duración de listado : " + (System.currentTimeMillis( ) - inicia) + " ms.");
    return l;
  }
  
  @Override
  @Transactional(readOnly = true)
  public Page<Deuda> findAll(Pageable p) { return deudaDao.findAll(p); }
  
  @Override
  public Page<Deuda> buscarEstadoPage(String estado,
                                      Pageable p) { return deudaDao.findByEstado(estado, p); }
  
  @Override
  @Transactional(readOnly = true)
  public Page<Deuda> buscarFilterBySortBy(Long idempresa,
                                          String estado,
                                          String nombre,
                                          Date fechaI,
                                          Date fechaF,
                                          Pageable p) {
    return deudaDao.listaFilterBySortBy(idempresa, aux.conversionStringListString(estado), nombre, fechaI,
      fechaF, p);
  }
  
  @Override
  @Transactional(readOnly = true)
  public byte[ ] reporteDeudaEstado(String estadodeuda,
                                    Long idempresa,
                                    String nombre,
                                    String fechaI,
                                    String fechaF) {
    Map<String, Object> params = new HashMap<>( );
    params.put("EstadoDeuda", estadodeuda);
    params.put("idempresa", idempresa);
    params.put("nombre", nombre);
    try {
      if (fechaI.equals("") && fechaF.equals("")) {
        params.put("fechaI", aux.sumarDiasAFecha(new Date( ), -50000));
        params.put("fechaF", aux.sumarDiasAFecha(new Date( ), 1));
      }else {
        params.put("fechaI", aux.conversionStringDate(fechaI));
        params.put("fechaF", aux.sumarDiasAFecha(aux.conversionStringDate(fechaF), 1));
      }
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    System.out.println(estadodeuda + " " + idempresa);
    return aux.reporte("listaALLclienteEstadodeuda", params);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Deuda> findById(Long id) { return deudaDao.findById(id); }
  
  @Override
  @Transactional
  public List<Deuda> cobrosLaFuente(MultipartFile file,
                                    String usuario) {
    Long inicia = System.currentTimeMillis( );
    List<Deuda> lista = new ArrayList<Deuda>( );
    try {
      InputStreamReader leer = new InputStreamReader(file.getInputStream( ));
      CSVParser parser = new CSVParserBuilder( ).withSeparator(';')
                                                .build( );
      CSVReader csvleer = new CSVReaderBuilder(leer).withCSVParser(parser)
                                                    .build( );
      List<String[ ]> filas = csvleer.readAll( );
      for(String[ ] row: filas) {
        Clientes id = clienteDao.buscarCodigoCliente(row[0].substring(0, 12)
                                                           .trim( ));
        Recibo recibo = new Recibo( );
        Contrato contrato = new Contrato( );
        Deuda deuda = new Deuda( );
        if (id != null) {
          // contrato.setIdpersona(id);
          contrato.setCodcontrato(row[0].substring(52, 65));
          deuda.setIdcontrato(contrato);
          recibo.setConcepto_recibo(row[0].substring(95, 155));
          recibo.setMonto(new BigDecimal(row[0].substring(65, 77)));
          recibo.setMoneda(Integer.valueOf(row[0].substring(77, 78)));
          String fechaV = row[0].substring(86, 88) + "/" + row[0].substring(88, 90) + "/" + row[0].substring(
            90, 94);
          recibo.setFecha_vencimiento(aux.conversionStringDate(fechaV, "dd/MM/yyyy"));
          recibo.setGlosa1(row[0].substring(176, 236));
          recibo.setGlosa2(row[0].substring(236, 286));
          recibo.setGlosa3(row[0].substring(286, 296));
          recibo.setCodigo_pago(row[0].substring(94, 95));
          recibo.setDescripcion_general(row[0].substring(173, 176));
          recibo.setNro_recibo(Long.valueOf(row[0].substring(170, 173)));
          recibo.setIdcontrato(contrato);
          recibo.setUsuario_alta(usuario);
          recibo.setEstado("PEN");
          deuda.setIdrecibo(recibo);
          deuda.setEstado("PEN");
          // deuda.setIdpersona(id);
          deuda.setUsuario_alta(usuario);
          lista.add(deuda);
        }else {
          logger.info("La persona no esta en la base de datos...");
        }
        // System.out.print(row[0].substring(0, 12) +
        // "-");
        // System.out.print(row[0].substring(12, 52) +
        // "-");
        // System.out.print(row[0].substring(52, 65) +
        // "-");
        // System.out.print(row[0].substring(65, 77) +
        // "-");
        // System.out.print(row[0].substring(77, 78) +
        // "-");
        // System.out.print(row[0].substring(78, 86) +
        // "-");
        // System.out.print(row[0].substring(86, 94) +
        // "-");
        // System.out.print(row[0].substring(94, 95) +
        // "-");
        // System.out.print(row[0].substring(95, 155) +
        // "-");
        // System.out.print(row[0].substring(155, 169) +
        // "-");
        // System.out.print(row[0].substring(169, 170) +
        // "-");
        // System.out.print(row[0].substring(170, 173) +
        // "-");
        // System.out.print(row[0].substring(173, 176) +
        // "-");
        // System.out.print(row[0].substring(176, 236) +
        // "-");
        // System.out.print(row[0].substring(236, 286) +
        // "-");
        // System.out.println(row[0].substring(286, 296));
      }
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    logger.info("Duración " + (System.currentTimeMillis( ) - inicia) + " ms.");
    return lista;
  }
  
  @Override
  public Page<Deuda> cliente(String estado,
                             String nombre,
                             Pageable p) {
    return deudaDao.listaCliente(estado, nombre, p);
  }
  
  @Override
  @Transactional
  public BigDecimal totaldeuda(String estado,
                               Long idempresa) {
    return deudaDao.totaldeuda(idempresa, estado);
  }
  
  @Override
  @Transactional
  public byte[ ] reporteListaDeuda(String estadodeuda,
                                   Long idempresa,
                                   String nombre,
                                   String fechaI,
                                   String fechaF) {
    Map<String, Object> params = new HashMap<>( );
    params.put("EstadoDeuda", estadodeuda);
    params.put("idempresa", idempresa);
    params.put("nombre", nombre);
    try {
      if (fechaI.equals("") && fechaF.equals("")) {
        params.put("fechaI", aux.sumarDiasAFecha(new Date( ), -50000));
        params.put("fechaF", aux.sumarDiasAFecha(new Date( ), 1));
      }else {
        params.put("fechaI", aux.conversionStringDate(fechaI));
        params.put("fechaF", aux.sumarDiasAFecha(aux.conversionStringDate(fechaF), 1));
      }
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    System.out.println(estadodeuda + " " + idempresa);
    return aux.reporte("listaALLdeudacliente", params);
  }
  
  @Override
  @Transactional
  public BigDecimal monto(String iddeuda) {
    if (iddeuda.length( ) != 0) {
      List<Long> list = Arrays.asList(iddeuda.split(","))
                              .stream( )
                              .map(s -> Long.parseLong(s.trim( )))
                              .collect(Collectors.toList( ));
      return deudaDao.monto(list);
    }
    return new BigDecimal(0);
  }
  
  @Override
  public boolean enviarcorrreo(String correo,
                               String url,
                               String id,
                               String idempresa,
                               String codigocliente) {
    String jwtId = idempresa;
    String jwtIssuer = id;
    String jwtSubject = correo;
    int jwtTimeToLive = 3600000 * 24 * 30;
    Map<String, Object> response = new HashMap<>( );
    Map<String, Object> claims = new HashMap<>( );
    MailRequest mailrequest = new MailRequest( );
    Deuda deuda = new Deuda( );
    try {
      Link l = new Link( );
      l.setCodigocliente(codigocliente);
      l.setCorreo(correo);
      l.setEstado("A");
      l.setIddeuda(id);
      l.setIdcliente(clienteDao.buscarCodigoCliente(codigocliente));
      l.setLink(url);
      l.setMonto(monto(id));
      claims.put("references", "0");
      deuda = deudaDao.findById(Long.valueOf(id))
                      .get( );
      String jwt = aux.createJWT2(idempresa, id, correo, claims, deuda.getIdrecibo( )
                                                                      .getFecha_vencimiento( ));
      String tipo = deuda.getIdrecibo( )
                         .getMoneda( )
                         .toString( )
                         .equals("1") ? "Bs." : "$us.";
      response.put("url", UrlImagen);
      response.put("cabecera", deuda.getIdcliente( )
                                    .getIdpersona( )
                                    .getNombres( ));
      response.put("numero", deuda.getIdrecibo( )
                                  .getNro_recibo( )
                                  .toString( ));
      response.put("mensaje", deuda.getIdcliente( )
                                   .getIdempresa( )
                                   .getRazon_social( ));
      response.put("monto", deuda.getIdrecibo( )
                                 .getMonto( )
                                 .toString( ) + " " + tipo);
      response.put("link", url + jwt.toString( ));
      response.put("dato", idempresa.equals(PREMIER) ? "true" : "false");
      response.put("fecha", aux.ConversionDateString(deuda.getIdrecibo( )
                                                          .getFecha_vencimiento( ), "dd/MM/yyyy"));
      // mailrequest.setTo(correo);
      response.put("logo", aux.convertStringListString(deuda.getIdcliente( )
                                                            .getIdempresa( )
                                                            .getLogo( )));
      if (deuda.getIdcliente( )
               .getIdempresa( )
               .getIdempresa( )
               .toString( )
               .equals(DATEC)) {
        mailrequest.setFrom("pagos@datecstore.com");
        response.put("footer",
          "Si tiene alguna pregunta, inquietud o sugerencia, envíenos un correo electrónico: dstore@datecstore.com o escríbenos a nuestro número de whatsapp +59162002022");
      }else if (deuda.getIdcliente( )
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
      mailrequest.setSubject("Pago de deuda");
      String[ ] address = {correo};
      emailServ.sendEmail(address, mailrequest, response, "envio_pago.html", "");
      l.setToken(jwt.toString( ));
      linkDao.save(l);
      logger.info("jwt = \"" + jwt.toString( ) + "\"");
      return true;
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    return false;
  }
  
  @Override
  public boolean fechavencida(String iddeuda,
                              String idempresa,
                              String fechavencida) throws ParseException {
    Map<String, Object> claims = new HashMap<>( );
    Map<String, Object> response = new HashMap<>( );
    MailRequest mailrequest = new MailRequest( );
    try {
      Deuda deuda = deudaDao.findById(Long.valueOf(iddeuda))
                            .get( );
      Link link = linkDao.findByIddeudaDesc(iddeuda)
                         .get( );
      if (fechavencida != null && !fechavencida.equals("")) {
        deuda.getIdrecibo( )
             .setFecha_vencimiento(aux.conversionStringDate(fechavencida, "dd/MM/yyyy hh:mm:ss"));
        deuda = deudaDao.save(deuda);
        String jwt = aux.createJWT2(idempresa, iddeuda, deuda.getIdrecibo( )
                                                             .getGlosa2( ), claims, deuda.getIdrecibo( )
                                                                                         .getFecha_vencimiento( ));
        link.setToken(jwt);
        link = linkDao.save(link);
      }
      String nombre = null;
      if (deuda.getIdrecibo( )
               .getSuscripcion( ) != null) {
        nombre = deuda.getIdrecibo( )
                      .getSuscripcion( );
      }else {
        nombre = deuda.getIdcliente( )
                      .getIdpersona( )
                      .getNombres( ) + " " + deuda.getIdcliente( )
                                                  .getIdpersona( )
                                                  .getApellido_paterno( );
      }
      response.put("nombre", nombre);
      String tipo = deuda.getIdrecibo( )
                         .getMoneda( )
                         .toString( )
                         .equals("1") ? "Bs." : "$us.";
      response.put("url", UrlImagen);
      response.put("cabecera", deuda.getIdcliente( )
                                    .getIdpersona( )
                                    .getNombres( ));
      response.put("numero", deuda.getIdrecibo( )
                                  .getNro_recibo( )
                                  .toString( ));
      response.put("mensaje", deuda.getIdcliente( )
                                   .getIdempresa( )
                                   .getRazon_social( ));
      response.put("monto", deuda.getIdrecibo( )
                                 .getMonto( )
                                 .toString( ) + " " + tipo);
      response.put("link", link.getLink( ) + link.getToken( ));
      response.put("dato", idempresa.equals(PREMIER) ? "true" : "false");
      try {
        response.put("fecha", aux.ConversionDateString(deuda.getIdrecibo( )
                                                            .getFecha_vencimiento( ), "dd/MM/yyyy"));
      }
      catch(ParseException e) {
        e.printStackTrace( );
      }
      catch(Exception e) {
        e.printStackTrace( );
      }
      // mailrequest.setTo(correo);
      response.put("logo", aux.convertStringListString(deuda.getIdcliente( )
                                                            .getIdempresa( )
                                                            .getLogo( )));
      if (deuda.getIdcliente( )
               .getIdempresa( )
               .getIdempresa( )
               .toString( )
               .equals(DATEC)) {
        mailrequest.setFrom("pagos@datecstore.com");
        response.put("footer",
          "Si tiene alguna pregunta, inquietud o sugerencia, envíenos un correo electrónico: dstore@datecstore.com o escríbenos a nuestro número de whatsapp +59162002022");
      }else if (deuda.getIdcliente( )
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
      mailrequest.setSubject("Pago de deuda");
      String[ ] address = {deuda.getIdrecibo( )
                                .getGlosa2( )};
      return emailServ.sendEmail(address, mailrequest, response, "envio_pago.html", "");
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    return false;
  }
  
  @Override
  @Transactional(readOnly = true)
  public Page<Deuda> buscarEstadoNombre(String estado,
                                        Long idempresa,
                                        String nombre,
                                        Pageable p) {
    return deudaDao.listaFilterEstadoNombre(estado, nombre, idempresa, p);
  }
  
  @Override
  @Transactional
  public Deuda save(Deuda deuda) { return deudaDao.save(deuda); }
  
  @Override
  public boolean enviarcorrreo2(String correo,
                                String url,
                                String id,
                                Deuda deuda,
                                String token) {
    Map<String, Object> response = new HashMap<>( );
    MailRequest mailrequest = new MailRequest( );
    Link l = new Link( );
    l.setCorreo(correo);
    l.setEstado("A");
    l.setCodigocliente(deuda.getIdcliente( )
                            .getCodigo_cliente( ));
    l.setIddeuda(deuda.getIddeuda( )
                      .toString( ));
    l.setIdcliente(deuda.getIdcliente( ));
    l.setLink(url);
    l.setMonto(this.monto(id));
    l.setToken(token);
    if (linkDao.findByIddeuda(String.valueOf(deuda.getIddeuda( )))
               .isPresent( )) {
      response.put("mensaje", "Deuda ya generada para el link con monto ::: " + deuda.getIdrecibo( )
                                                                                     .getMonto( ));
      logger.error("Deuda ya generada para el link");
    }else {
      try {
        linkDao.save(l);
      }
      catch(DataAccessException e) {
        logger.error("error al guardar link de pago");
        return false;
      }
    }
    String tipo = deuda.getIdrecibo( )
                       .getMoneda( )
                       .toString( )
                       .equals("1") ? "Bs." : "$us.";
    response.put("cabecera", deuda.getIdcliente( )
                                  .getIdpersona( )
                                  .getNombres( ));
    response.put("numero", deuda.getIdrecibo( )
                                .getNro_recibo( )
                                .toString( ));
    response.put("mensaje", deuda.getIdcliente( )
                                 .getIdempresa( )
                                 .getRazon_social( ));
    response.put("monto", deuda.getIdrecibo( )
                               .getMonto( )
                               .toString( ) + " " + tipo);
    response.put("link", url + token);
    response.put("dato", id.equals(PREMIER) ? "true" : "false");
    response.put("logo", aux.convertStringListString(deuda.getIdcliente( )
                                                          .getIdempresa( )
                                                          .getLogo( )));
    String nombre = null;
    if (deuda.getIdrecibo( )
             .getSuscripcion( ) != null) {
      nombre = deuda.getIdrecibo( )
                    .getSuscripcion( );
    }else {
      nombre = deuda.getIdcliente( )
                    .getIdpersona( )
                    .getNombres( ) + " " + deuda.getIdcliente( )
                                                .getIdpersona( )
                                                .getApellido_paterno( );
    }
    response.put("nombre", nombre);
    try {
      response.put("fecha", aux.ConversionDateString(deuda.getIdrecibo( )
                                                          .getFecha_vencimiento( ), "dd/MM/yyyy"));
    }
    catch(Exception e1) {
      logger.error("error al convertir fecha de vencimiento");
      return false;
    }
    response.put("url", UrlImagen);
    if (deuda.getIdcliente( )
             .getIdempresa( )
             .getIdempresa( )
             .toString( )
             .equals(DATEC)) {
      logger.info("=========== CORREO DE DATEC ===========");
      mailrequest.setFrom("pagos@datecstore.com");
      response.put("footer", "");
    }else if (deuda.getIdcliente( )
                   .getIdempresa( )
                   .getIdempresa( )
                   .toString( )
                   .equals(GENIO)) {
                     logger.info("=========== CORREO DE EL GENIOX ===========");
                     mailrequest.setFrom("elgeniox@elgeniox.com");
                     response.put("footer",
                       "Si tiene alguna pregunta, inquietud o sugerencia, envíenos un correo electrónico: elgeniox@elgeniox.com");
                   }else if (deuda.getIdcliente( )
                                  .getIdempresa( )
                                  .getIdempresa( )
                                  .toString( )
                                  .equals(Dstore)) {
                                    logger.info("=========== CORREO DE DSTORE ===========");
                                    mailrequest.setFrom("pagos@datecstore.com");
                                    response.put("footer",
                                      "Si tienes alguna pregunta, sugerencia o inquietud, envíanos un correo electrónico a: dstore@datecstore.com o llámanos a nuestro número de whatsapp +591 62002022");
                                  }else if (deuda.getIdcliente( )
                                                 .getIdempresa( )
                                                 .getIdempresa( )
                                                 .toString( )
                                                 .equals(DIBEL)) {
                                                   logger.info("=========== CORREO DE DIBEL ===========");
                                                   mailrequest.setFrom("pagos@pagatodo360.net");
                                                   response.put("footer",
                                                     "Si tiene alguna pregunta, inquietud o sugerencia, envíenos un correo electrónico: soluciones@dibeltecnologia.com o escríbenos a nuestro número de whatsapp +591 63188443");
                                                 }else {
                                                   logger.info("=========== CORREO DE DEFAULT ===========");
                                                   mailrequest.setFrom("pagos@pagatodo360.net");
                                                   response.put("footer",
                                                     "Si tiene alguna pregunta, inquietud o sugerencia, envíenos un correo electrónico: ventas@pagatodo360.net o llamarnos a nuestro número 315-1100 Int,5358");
                                                 }
    mailrequest.setSubject("Pago de deuda");
    logger.info(url + token);
    String[ ] address = {correo};
    return emailServ.sendEmail(address, mailrequest, response, "envio_pago.html", "");
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<?> buscarByIdEmpresaFechaEstado(Long idempresa,
                                              String estado,
                                              Date fechaI,
                                              Date fechaF) {
    return deudaDao.lista(idempresa, estado, fechaI, fechaF);
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<?> listaReporte(Long idempresa,
                              String estado,
                              Date fechaI,
                              Date fechaF) {
    return deudaDao.listaReporte(idempresa, estado, fechaI, fechaF);
  }
  
  @Override
  @Transactional
  public Map<String, Object> archivo(MultipartFile file,
                                     String usuario) {
    Map<String, Object> response = new HashMap<>( );
    Workbook libro = aux.getlibro(file);
    Sheet hoja = libro.getSheetAt(0);
    Iterator<Row> filas = hoja.iterator( );
    int rows = hoja.getLastRowNum( );
    filas.next( );
    logger.info("Total Filas del archivo(Encabezado): " + rows);
    List<ErroresArchivo> listaerror = new ArrayList<>( );
    List<Deuda> listdeuda = new ArrayList<>( );
    List<Clientes> listcliente = new ArrayList<>( );
    for(int r = 1;r <= rows;r++) {
      ErroresArchivo error = new ErroresArchivo( );
      Deuda deuda = new Deuda( );
      Recibo recibo = new Recibo( );
      Contrato contrato = new Contrato( );
      Clientes cliente = new Clientes( );
      Personas persona = new Personas( );
      Row fila = filas.next( );
      System.out.println("fila -->> " + fila.getRowNum( ));
      if (fila.getCell(28)
              .getCellType( ) == 0 || aux.numero(aux.cambio(fila.getCell(28)))) {
        recibo.setMoneda(Integer.valueOf(aux.cambio(fila.getCell(28))));
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: AC");
        listaerror.add(error);
      }
      if (fila.getCell(27)
              .getCellType( ) == 0 || aux.numero(aux.cambio(fila.getCell(27)))) {
        recibo.setMonto(BigDecimal.valueOf(Double.valueOf(aux.cambio(fila.getCell(27)))));
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: AB");
        listaerror.add(error);
      }
      if (HSSFDateUtil.isCellDateFormatted(fila.getCell(26))) {
        recibo.setFecha_vencimiento(fila.getCell(26)
                                        .getDateCellValue( ));
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: AA");
        listaerror.add(error);
      }
      if (HSSFDateUtil.isCellDateFormatted(fila.getCell(25))) {
        recibo.setFecha_alta(fila.getCell(25)
                                 .getDateCellValue( ));
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: Z");
        listaerror.add(error);
      }
      if (fila.getCell(24)
              .getCellType( ) == 1) {
        recibo.setTipo_recibo(fila.getCell(24)
                                  .getStringCellValue( ));;
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: Y");
        listaerror.add(error);
      }
      if (fila.getCell(23)
              .getCellType( ) == 0 || aux.numero(aux.cambio(fila.getCell(23)))) {
        recibo.setNro_recibo(Long.valueOf(aux.cambio(fila.getCell(23))));;
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: X");
        listaerror.add(error);
      }
      // **************************************************
      // CONTRATO
      // **************************************************
      if (fila.getCell(16)
              .getCellType( ) == 1) {
        contrato.setServicio(fila.getCell(16)
                                 .getStringCellValue( ));
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: Q");
        listaerror.add(error);
      }
      if (fila.getCell(15)
              .getCellType( ) == 0 || aux.numero(aux.cambio(fila.getCell(15)))) {
        contrato.setNit(aux.cambio(fila.getCell(15)));
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: P");
        listaerror.add(error);
      }
      if (fila.getCell(14)
              .getCellType( ) == 1) {
        contrato.setRazonSocial(fila.getCell(14)
                                    .getStringCellValue( ));;
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: O");
        listaerror.add(error);
      }
      // if (fila.getCell(13).getCellType( ) == 0|| aux.numero(aux.cambio(fila.getCell(13)))) {
      // contrato.setNit(aux.cambio(fila.getCell(13)));
      // }else {
      // error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: N");
      // listaerror.add(error);
      // }
      
      // **************************************************
      // CLIENTE
      // **************************************************
      if (fila.getCell(8)
              .getCellType( ) == 1) {
        cliente.setCodigo_cliente(fila.getCell(8)
                                      .getStringCellValue( ));
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: I");
        listaerror.add(error);
      }
      // **************************************************
      // PERSONA
      // **************************************************
      if (fila.getCell(0)
              .getCellType( ) == 1 || aux.numero(aux.cambio(fila.getCell(0)))) {
        persona.setValor_documento(aux.cambio(fila.getCell(0)));
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: A");
        listaerror.add(error);
      }
      if (fila.getCell(1)
              .getCellType( ) == 0 || aux.numero(aux.cambio(fila.getCell(1)))) {
        persona.setTipo_documento(aux.cambio(fila.getCell(1)));
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: B");
        listaerror.add(error);
      }
      if (fila.getCell(2)
              .getCellType( ) == 1) {
        persona.setNombres(fila.getCell(2)
                               .getStringCellValue( ));
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: C");
        listaerror.add(error);
      }
      if (fila.getCell(3)
              .getCellType( ) == 1) {
        persona.setApellido_paterno(fila.getCell(3)
                                        .getStringCellValue( ));
      }else {
        error.setMensaje("error fila: " + fila.getRowNum( ) + ", Columna: D");
        listaerror.add(error);
      }
      // **************************************************
      // CONSTRUCCION DE JSON
      // **************************************************
      if (listaerror.size( ) == 0) {
        cliente.setEstado("A");
        contrato.setEstado("A");
        recibo.setEstado("PEN");
        deuda.setEstado("PEN");
        deuda.setIdcontrato(contrato);
        deuda.setIdrecibo(recibo);
        listdeuda.add(deuda);
        cliente.setIddeuda(listdeuda);
        listcliente.add(cliente);
      }
    }
    response.put("errores", listaerror);
    response.put("deuda", listdeuda);
    response.put("cliente", listcliente);
    return response;
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<Deuda> findByIdduedaIn(List<Long> ids) { return deudaDao.findAllById(ids); }
  
  @Async("asyncExecutor")
  @Override
  @Transactional
  public void envioCorreo(List<Long> telf,
                          List<Long> correo,
                          Long idbitacora) throws Exception {
    List<Deuda> ListCorreo = deudaDao.findAllById(correo);
    for(Deuda deuda: ListCorreo) {
      String jwtSubject = deuda.getIdrecibo( )
                               .getGlosa2( );
      Link l = linkDao.findByIddeudaAndIdbitacora(String.valueOf(deuda.getIddeuda( )), idbitacora);
      String url = l.getLink( );
      String jwtId = deuda.getIdcliente( )
                          .getIdempresa( )
                          .getIdempresa( )
                          .toString( );
      if (this.enviarcorrreo2(jwtSubject, url, jwtId, deuda, l.getToken( ))) {
        System.out.println("se envio el correo.............." + jwtSubject);
      }
    }
    List<Deuda> ListTelf = deudaDao.findAllById(telf);
    for(Deuda deuda: ListTelf) {
      Link l = linkDao.findByIddeudaAndIdbitacora(String.valueOf(deuda.getIddeuda( )), idbitacora);
      System.out.println(l.getTelefono( ) + "......" + deuda.getIddeuda( ));
      Auxiliar.sendUrl("591" + l.getTelefono( ), "Por favor ingrese a su link de pago: " + l.getLink( ) + l
                                                                                                           .getToken( ),
        "");
    }
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Deuda> obtenerDeudaNroRecibo(String nrorecibo) {
    return deudaDao.obtenerDeudaNroRecibo(nrorecibo);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Page<Deuda> ListaSeguroVida(Long idempresa,
                                     String estado,
                                     String nombre,
                                     Date fechaI,
                                     Date fechaF,
                                     Pageable p) {
    return deudaDao.listaSeguroVida(idempresa, aux.conversionStringListString(estado), nombre, fechaI, fechaF,
      p);
  }
  
}
