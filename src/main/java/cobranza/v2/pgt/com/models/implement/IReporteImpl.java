
package cobranza.v2.pgt.com.models.implement;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import cobranza.v2.pgt.com.dto.DeudaQueryGeneralDto;
import cobranza.v2.pgt.com.dto.LiquidacionGeneralQueryDto;
import cobranza.v2.pgt.com.models.dao.IDeudaDao;
import cobranza.v2.pgt.com.models.dao.IDeudaDao.ShopifyrDao2;
import cobranza.v2.pgt.com.models.dao.IDeudaDao.ShopifyrDao3;
import cobranza.v2.pgt.com.models.dao.IEmpresasDao;
import cobranza.v2.pgt.com.models.dao.IReciboDao;
import cobranza.v2.pgt.com.models.entity.Comision;
import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.services.IComisionServ;
import cobranza.v2.pgt.com.models.services.IReporteServ;
import cobranza.v2.pgt.com.utils.Auxiliar;
import cobranza.v2.pgt.com.utils.Constants;
import cobranza.v2.pgt.com.utils.excepcions.NotFoundException;
import cobranza.v2.pgt.com.utils.otros.FacturaSBL;
import cobranza.v2.pgt.com.utils.otros.ReporteFacturacion;
import cobranza.v2.pgt.com.utils.otros.ReporteVendorShopify;
import cobranza.v2.pgt.com.utils.otros.facturadetalle;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class IReporteImpl implements
                          IReporteServ {
  
  @Autowired
  private Auxiliar aux;
  @Value("#{'${path-dir}'}")
  private String _pathDir;
  @Value("#{'${path-img}'}")
  private String _img;
  @Value("#{'${empresa.pagatodo}'}")
  private String _PAGATODO;
  @Value("${url.temporal}")
  private String _URL;
  @Value("#{'${url.codeqr.siat}'}")
  private String _UrlCodeQR;
  @Value("#{'${idqr}'}")
  private String IdQr;
  @Autowired
  private RestTemplate restTemplate;
  
  @Autowired
  private IDeudaDao deudaDao;
  
  @Autowired
  private IEmpresasDao empresaDao;
  @Autowired
  private IReciboDao reciboDao;
  @Autowired
  private IComisionServ comisicionS;
  
  @Override
  @Transactional
  public byte[ ] reporteDeuda(Long idempresa,
                              String estado,
                              String fechaI,
                              String fechaF,
                              String usuario) throws IOException {
    Date FI = null, FF = null;
    Map<String, Object> params = new HashMap<>( );
    try {
      if (fechaI.equals("") && fechaF.equals("")) {
        FI = aux.sumarDiasAFecha(new Date( ), -50000);
        FF = aux.sumarDiasAFecha(new Date( ), 1);
      }else {
        FI = aux.conversionStringDate(fechaI, "dd/MM/yyyy");
        FF = aux.sumarDiasAFecha(aux.conversionStringDate(fechaF, "dd/MM/yyyy"), 1);
      }
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    List<?> lista = deudaDao.listaReporte(idempresa, estado, FI, FF);
    if (lista.size( ) == 0) throw new NotFoundException(
      "No hay datos de entre las fechas \n" + fechaI + " y " + fechaF);
    Empresas empresa = empresaDao.buscarEmpresaID(Long.valueOf(idempresa), "A");
    String logo = Paths.get("")
                       .toAbsolutePath( )
                       .normalize( )
                       .toString( )
                       .replace(_pathDir, "") + _img + empresa.getLogo( )
                                                              .split(",")[0];
    File file = new File(logo);
    params.put("logo", file.getAbsolutePath( ));
    params.put("totallista", lista.size( ));
    params.put("nombre_empresa", empresa.getRazon_social( ));
    params.put("estado", estado.equals("PEN") ? "PENDIENTES" : estado.equals("PAG") ? "PAGADOS" : estado
                                                                                                        .equals(
                                                                                                          "ANU") ?
      "ANULADOS" : "TODOS");
    params.put("fechaI", FI);
    params.put("fechaF", FF);
    params.put("usuario", usuario);
    params.put("fecha_hora", new Date( ));
    params.put("rango_fechas", fechaI + " - " + fechaF);
    params.put("idempresa", Long.valueOf(idempresa));
    // byte[ ] bytes = aux.reporteLista("reporte_pago_pdf", params, lista);
    byte[ ] bytes = aux.generarReporteTipo("reporte_pago_pdf", params, lista, ".pdf");
    return bytes;
  }
  
  @Override
  @Transactional
  public byte[ ] reporteExcel(Long idempresa,
                              String estado,
                              String fechaI,
                              String fechaF,
                              String usuario) throws ParseException {
    Date FI = null, FF = null;
    Map<String, Object> params = new HashMap<>( );
    try {
      if (fechaI.equals("") && fechaF.equals("")) {
        FI = aux.sumarDiasAFecha(new Date( ), -50000);
        FF = aux.sumarDiasAFecha(new Date( ), 1);
      }else {
        FI = aux.conversionStringDate(fechaI, "dd/MM/yyyy");
        FF = aux.sumarDiasAFecha(aux.conversionStringDate(fechaF, "dd/MM/yyyy"), 1);
      }
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    List<?> lista = deudaDao.listaReporte(idempresa, estado, FI, FF);
    if (lista.size( ) == 0) throw new NotFoundException(
      "No hay datos de entre las fechas \n" + fechaI + " y " + fechaF);
    List<DeudaQueryGeneralDto> vDeudaQueryGeneralDtoList = aux.generarDataDeuda(lista);
    Empresas empresa = empresaDao.buscarEmpresaID(Long.valueOf(idempresa), "A");
    String logo = Paths.get("")
                       .toAbsolutePath( )
                       .normalize( )
                       .toString( )
                       .replace(_pathDir, "") + _img + empresa.getLogo( )
                                                              .split(",")[0];
    File file = new File(logo);
    /*params.put("logo", file.getAbsolutePath( ));
    params.put("totallista", lista.size( ));
    params.put("nombre_empresa", empresa.getRazon_social( ));
    params.put("estado", estado.equals("PEN") ? "PENDIENTES" : estado.equals("PAG") ? "PAGADOS" : estado
                                                                                                        .equals(
                                                                                                          "ANU") ?
      "ANULADOS" : "TODOS");
    params.put("fechaI", FI);
    params.put("fechaF", FF);
    params.put("usuario", usuario);
    params.put("fecha_hora", new Date( ));
    params.put("rango_fechas", fechaI + " - " + fechaF);
    params.put("idempresa", Long.valueOf(idempresa));*/
    // byte[ ] bytes = aux.generarXLSReport("reporte_pago_excel", params, lista);
    // byte[ ] bytes = aux.generarReporteTipo("reporte_pago_excel", params, lista, ".xlsx");
    
    byte[ ] bytes = aux.generarReporteDeudaExcel(vDeudaQueryGeneralDtoList, usuario, aux.ConvertDateString(
      new Date( ), "dd/MM/yyyy HH:mm:ss"), empresa.getRazon_social( ), fechaI + " - " + fechaF, file);
    return bytes;
  }
  
  @Override
  @Transactional
  public byte[ ] reporteCONCILIACION(Long idempresa,
                                     String fechaI,
                                     String fechaF) throws IOException,
                                                    ParseException {
    Map<String, Object> params = new HashMap<>( );
    List<?> lista = empresaDao.CONCILIACION_reporte(aux.conversionStringDate(fechaI, "dd/MM/yyyy hh:mm:ss"),
      aux.conversionStringDate(fechaF, "dd/MM/yyyy hh:mm:ss"), idempresa);
    System.out.println(lista.size( ));
    if (lista.size( ) == 0) throw new NotFoundException(
      "No hay datos de entre las fechas \n" + fechaI + " y " + fechaF);
    Empresas empresa = empresaDao.buscarEmpresaID(Long.valueOf(_PAGATODO), "A");
    // String logo = Paths.get("")
    // .toAbsolutePath( )
    // .normalize( )
    // .toString( )
    // .replace(_pathDir, "") + _img + empresa.getLogo( )
    // .split(",")[0];
    // File file = new File(logo);
    // params.put("logo", file.getAbsolutePath( ));
    params.put("totallista", lista.size( ));
    params.put("nombre_empresa", empresa.getRazon_social( ));
    params.put("usuario", "Pagatodo360");
    params.put("fecha_hora", new Date( ));
    params.put("rango_fechas", fechaI.split(" ")[0] + " - " + fechaF.split(" ")[0]);
    return aux.reporteLista("Liquidacion", params, lista);
  }
  
  @Override
  public byte[ ] reporteTipo(Long idempresa,
                             String estado,
                             String fechaI,
                             String fechaF,
                             String usuario,
                             String tipo) throws IOException {
    Date FI = null, FF = null;
    Map<String, Object> params = new HashMap<>( );
    try {
      if (fechaI.equals("") && fechaF.equals("")) {
        FI = aux.sumarDiasAFecha(new Date( ), -50000);
        FF = aux.sumarDiasAFecha(new Date( ), 1);
      }else {
        FI = aux.conversionStringDate(fechaI, "dd/MM/yyyy");
        FF = aux.sumarDiasAFecha(aux.conversionStringDate(fechaF, "dd/MM/yyyy"), 1);
      }
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    List<?> lista = deudaDao.listaReporte(idempresa, estado, FI, FF);
    if (lista.size( ) == 0) throw new NotFoundException(
      "No hay datos de entre las fechas \n" + fechaI + " y " + fechaF);
    Empresas empresa = empresaDao.buscarEmpresaID(Long.valueOf(idempresa), "A");
    String logo = Paths.get("")
                       .toAbsolutePath( )
                       .normalize( )
                       .toString( )
                       .replace(_pathDir, "") + _img + empresa.getLogo( )
                                                              .split(",")[0];
    File file = new File(logo);
    params.put("logo", file.getAbsolutePath( ));
    params.put("totallista", lista.size( ));
    params.put("nombre_empresa", empresa.getRazon_social( ));
    params.put("estado", estado.equals("PEN") ? "PENDIENTES" : estado.equals("PAG") ? "PAGADOS" : estado
                                                                                                        .equals(
                                                                                                          "ANU") ?
      "ANULADOS" : "TODOS");
    params.put("fechaI", FI);
    params.put("fechaF", FF);
    params.put("usuario", usuario);
    params.put("fecha_hora", new Date( ));
    params.put("rango_fechas", fechaI + " - " + fechaF);
    params.put("idempresa", Long.valueOf(idempresa));
    byte[ ] bytes = aux.generarReporteTipo("reporte_pago_excel", params, lista, "Excel");
    return bytes;
  }
  
  @Override
  public byte[ ] liquidacion(String ListIdempresa,
                             String fechaI,
                             String fechaF) throws IOException,
                                            ParseException,
                                            JRException {
    Date FI = null, FF = null;
    JasperPrint primero = null, segundo = null;
    FI = aux.conversionStringDate(fechaI, "dd/MM/yyyy");
    FF = aux.conversionStringDate(fechaF + " 23:59:59", "dd/MM/yyyy HH:mm:ss");
    List<Long> lista = Arrays.asList(ListIdempresa.split(";"))
                             .stream( )
                             .map(s -> Long.parseLong(s.trim( )))
                             .collect(Collectors.toList( ));
    if (lista.size( ) == 0) throw new NotFoundException(
      "No hay datos de entre las fechas \n" + fechaI + " y " + fechaF);
    primero = generarLiquidacion(lista, FI, FF, fechaI, fechaF, primero, "ORIGINAL", "liquidacion_original");
    segundo = generarLiquidacion(lista, FI, FF, fechaI, fechaF, primero, "COPIA", "liquidacion_copia");
    primero = concatenarJasper(primero, segundo);
    return aux.reporteBytes(primero);
  }
  
  public JasperPrint generarLiquidacion(List<Long> lista,
                                        Date FI,
                                        Date FF,
                                        String fechaI,
                                        String fechaF,
                                        JasperPrint primero,
                                        String tipo,
                                        String template) {
    for(int i = 0;i < lista.size( );i++) {
      HashMap<String, Object> params = new HashMap<String, Object>( );
      List<?> list = reciboDao.liquidacionTarjeta(String.valueOf(lista.get(i)), "PAG", FI, FF);
      
      List<?> list2 = reciboDao.liquidacionQr(String.valueOf(lista.get(i)), "PAG", FI, FF, IdQr);
      Empresas emp = empresaDao.findByEstadoAndIdempresa("A", lista.get(i));
      Optional<Comision> comi = comisicionS.BuscarIdempresaNullvendor(emp.getIdempresa( ), "A");
      LiquidacionGeneralQueryDto vLiquidacionTarjetaQueryDto = aux.generarLiquidacion(list, "TJ", emp
                                                                                                     .getIdempresa( ),
        comi.get( ));
      LiquidacionGeneralQueryDto vLiquidacionQrQueryDto = aux.generarLiquidacion(list2, "QR", emp
                                                                                                 .getIdempresa( ),
        comi.get( ));
      String logo = Paths.get("")
                         .toAbsolutePath( )
                         .normalize( )
                         .toString( )
                         .replace(_pathDir, "") + "/imagen/PAGATODO.png";
      File file = new File(logo);
      params.put("logo", file.getAbsolutePath( ));
      params.put("datasource1", vLiquidacionTarjetaQueryDto.getVLiquidacionQueryDtoList( ));
      params.put("datasource2", vLiquidacionQrQueryDto.getVLiquidacionQueryDtoList( ));
      if (fechaI.equals(fechaF)) {
        params.put("fechas", fechaI);
      }else {
        params.put("fechas", fechaI + " al " + fechaF);
      }
      params.put("comercio", emp.getRazon_social( ));
      params.put("nit", emp.getNit( ));
      params.put("tipo", tipo);
      params.put("montoTotalTarjetaBs", vLiquidacionTarjetaQueryDto.getMontoTotalTarjetaBs( ));
      params.put("montoTotalTarjetaUsd", vLiquidacionTarjetaQueryDto.getMontoTotalTarjetaUsd( ));
      params.put("montoTotalQrBs", vLiquidacionQrQueryDto.getMontoTotalQrBs( ));
      params.put("montoTotalQrUsd", vLiquidacionQrQueryDto.getMontoTotalQrUsd( ));
      params.put("totalComisionTarjetaPtBs", vLiquidacionTarjetaQueryDto.getTotalComisionTarjetaPtBs( ));
      params.put("totalComisionTarjetaPtUsd", vLiquidacionTarjetaQueryDto.getTotalComisionTarjetaPtUsd( ));
      params.put("totalComisionQrPtBs", vLiquidacionQrQueryDto.getTotalComisionQrPtBs( ));
      params.put("totalComisionQrPtUsd", vLiquidacionQrQueryDto.getTotalComisionQrPtUsd( ));
      params.put("totalComisionTarjetaAtBs", vLiquidacionTarjetaQueryDto.getTotalComisionTarjetaAtBs( ));
      params.put("totalComisionTarjetaAtUsd", vLiquidacionTarjetaQueryDto.getTotalComisionTarjetaAtUsd( ));
      params.put("totalComisionQrAtBs", vLiquidacionQrQueryDto.getTotalComisionQrAtBs( ));
      params.put("totalComisionQrAtUsd", vLiquidacionQrQueryDto.getTotalComisionQrAtUsd( ));
      params.put("liquidoPagableTarjetaBs", vLiquidacionTarjetaQueryDto.getLiquidoPagableTarjetaBs( ));
      params.put("liquidoPagableTarjetaSus", vLiquidacionTarjetaQueryDto.getLiquidoPagableTarjetaSus( ));
      params.put("liquidoPagableQrBs", vLiquidacionQrQueryDto.getLiquidoPagableQrBs( ));
      params.put("liquidoPagableQrSus", vLiquidacionQrQueryDto.getLiquidoPagableQrSus( ));
      params.put("totalComisionTarjetaBs", vLiquidacionTarjetaQueryDto.getTotalComisionTarjetaBs( ));
      params.put("totalComisionTarjetaUsd", vLiquidacionTarjetaQueryDto.getTotalComisionTarjetaUsd( ));
      params.put("totalComisionQrBs", vLiquidacionQrQueryDto.getTotalComisionQrBs( ));
      params.put("totalComisionQrUsd", vLiquidacionQrQueryDto.getTotalComisionQrUsd( ));
      if (i == 0) {
        primero = aux.reporteList(template, params, new JREmptyDataSource( ));
      }else {
        JasperPrint jp1 = aux.reporteList(template, params, new JREmptyDataSource( ));
        for(int j = 0;j < jp1.getPages( )
                             .size( );j++) {
          primero.addPage(jp1.getPages( )
                             .get(j));
        }
      }
    }
    return primero;
  }
  
  public JasperPrint concatenarJasper(JasperPrint primero,
                                      JasperPrint segunto) {
    for(int j = 0;j < segunto.getPages( )
                             .size( );j++) {
      primero.addPage(segunto.getPages( )
                             .get(j));
    }
    return primero;
  }
  
  @Override
  @Transactional
  public byte[ ] reporteShopify(Long idempresa,
                                String estado,
                                String fechaI,
                                String fechaF,
                                String usuario) throws ParseException,
                                                Exception {
    Date FI = null, FF = null;
    JasperPrint primero = null, segundo = null;
    Map<String, Object> params = new HashMap<>( );
    try {
      if (fechaI.equals("") && fechaF.equals("")) {
        FI = aux.sumarDiasAFecha(new Date( ), -50000);
        FF = aux.sumarDiasAFecha(new Date( ), 1);
      }else {
        FI = aux.conversionStringDate(fechaI, "dd/MM/yyyy");
        FF = aux.sumarDiasAFecha(aux.conversionStringDate(fechaF, "dd/MM/yyyy"), 1);
      }
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    
    List<?> comercios = deudaDao.ObtenerComerciosShopify(idempresa, estado, FI, FF);
    Empresas empresa = empresaDao.buscarEmpresaID(Long.valueOf(idempresa), "A");
    String logo = Paths.get("")
                       .toAbsolutePath( )
                       .normalize( )
                       .toString( )
                       .replace(_pathDir, "") + _img + empresa.getLogo( )
                                                              .split(",")[0];
    File file = new File(logo);
    params.put("logo", file.getAbsolutePath( ));
    params.put("nombre_empresa", empresa.getRazon_social( ));
    params.put("usuario", usuario);
    params.put("fecha_hora", aux.ConversionDateString(new Date( ), "dd/MM/yyyy HH:mm:ss"));
    params.put("rango_fechas", fechaI + " - " + fechaF);
    List<IDeudaDao.ShopifyrDao3> detalle = ( List<ShopifyrDao3> ) comercios;
    primero = this.generarPorComercioShopify(detalle, FI, FF, idempresa, estado, primero, segundo, params,
      "reporte_shopify");
    return aux.reporteBytes(primero);
  }
  
  public JasperPrint generarPorComercioShopify(List<IDeudaDao.ShopifyrDao3> comercios,
                                               Date FI,
                                               Date FF,
                                               Long idempresa,
                                               String estado,
                                               JasperPrint primero,
                                               JasperPrint segundo,
                                               Map<String, Object> params,
                                               String template) throws IOException {
    List<?> lista = new ArrayList( );
    for(int j = 0;j < comercios.size( );j++) {
      List<ReporteVendorShopify> data = new ArrayList( );
      String comercio = comercios.get(j)
                                 .getItem( );
      lista = deudaDao.listaReporteShopify(idempresa, estado, comercio, FI, FF);
      for(Object object: lista) {
        IDeudaDao.ShopifyrDao2 obj = ( ShopifyrDao2 ) object;
        ReporteVendorShopify rv = new ReporteVendorShopify( );
        rv.setId(obj.getDoc( ));
        rv.setCantidad(obj.getCantidad( ));
        rv.setCliente(obj.getCliente( ));
        rv.setDescripcion(obj.getDescripcion( ));
        rv.setDescripcion_item(obj.getDescripcion_item( ));
        rv.setFechap(obj.getFechap( ));
        rv.setFechav(obj.getFechav( ));
        rv.setItem(obj.getItem( ));
        rv.setNro_recibo(obj.getNro_recibo( ));
        rv.setPrecio_unitario(obj.getPrecio_unitario( ));
        rv.setSub_total(obj.getSub_total( ));
        rv.setDescuento(obj.getDescuento( ));
        rv.setTotal(obj.getTotal( ));
        // Map<String, Object> callback = this.aux.getRequestShopify(
        // Gson gson = new Gson( );
        // Root root = gson.fromJson(callback.get("data")
        // .toString( ), Root.class);
        // if (root.getOrders( )
        // .get(0)
        // .getTags( )
        // .isEmpty( )) {
        // rv.setEntrega("NO");
        // }else if (root.getOrders( )
        // .get(0)
        // .getTags( )
        // .equals("entregado")) {
        // rv.setEntrega("SI");
        // }else {
        // rv.setEntrega("Tienda");
        // }
        rv.setEntrega("");
        data.add(rv);
      }
      params.put("datasource1", data);
      params.put("comercio", comercio);
      params.put("tipo", "ORIGINAL");
      if (j == 0) {
        primero = aux.reporteList(template, params, new JREmptyDataSource( ));
        params.put("tipo", "COPIA");
        segundo = aux.reporteList(template, params, new JREmptyDataSource( ));
      }else {
        JasperPrint jp1 = aux.reporteList(template, params, new JREmptyDataSource( ));
        params.put("tipo", "COPIA");
        JasperPrint jp2 = aux.reporteList(template, params, new JREmptyDataSource( ));
        primero = paginarJasper(jp1, primero);
        segundo = paginarJasper(jp2, segundo);
      }
    }
    primero = concatenarJasper(primero, segundo);
    return primero;
  }
  
  JasperPrint paginarJasper(JasperPrint JP1,
                            JasperPrint JP2) {
    for(int i = 0;i < JP1.getPages( )
                         .size( );i++) {
      JP2.addPage(JP1.getPages( )
                     .get(i));
    }
    return JP2;
  }
  
  public List<ReporteFacturacion> listaFacturacion(String nit,
                                                   String codigo,
                                                   String fechaI,
                                                   String fechaF) {
    String url = _URL + "/factura/facturacion?nit=" + nit + "&fechaI=" + fechaI + "&fechaF=" + fechaF
      + " 23:59:59" + "&codigo=" + codigo;
    System.out.println(url);
    ResponseEntity<List<ReporteFacturacion>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      new ParameterizedTypeReference<List<ReporteFacturacion>>( ) {
      });
    return responseEntity.getBody( );
  }
  
  @Override
  public byte[ ] reporteFacturacion(Long idempresa,
                                    String codigo,
                                    String tipo,
                                    String fechaI,
                                    String fechaF,
                                    String usuario) throws ParseException,
                                                    Exception {
    Date FI = null, FF = null;
    try {
      if (fechaI.equals("") && fechaF.equals("")) {
        FI = aux.sumarDiasAFecha(new Date( ), -50000);
        FF = aux.sumarDiasAFecha(new Date( ), 1);
      }else {
        FI = aux.conversionStringDate(fechaI, "dd/MM/yyyy");
        FF = aux.sumarDiasAFecha(aux.conversionStringDate(fechaF.split(" ")[0], "dd/MM/yyyy"), 1);
      }
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    Empresas empresa = empresaDao.buscarEmpresaID(Long.valueOf(idempresa), "A");
    List<ReporteFacturacion> lista = this.listaFacturacion(empresa.getNit( ), codigo, fechaI, fechaF);
    if (lista.size( ) == 0) throw new NotFoundException(
      "No hay datos de entre las fechas \n" + fechaI + " y " + fechaF);
    String logo = Paths.get("")
                       .toAbsolutePath( )
                       .normalize( )
                       .toString( )
                       .replace(_pathDir, "") + _img + empresa.getLogo( )
                                                              .split(",")[0];
    File vFile = new File(logo);
    byte[ ] bytes = aux.generarReporteExcel(lista, usuario, aux.ConvertDateString(new Date( ),
      "dd/MM/yyyy HH:mm:ss"), empresa.getRazon_social( ), vFile, aux.ConvertDateString(FI, "dd/MM/yyyy")
        + " al " + aux.ConvertDateString(FF, "dd/MM/yyyy"));
    return bytes;
  }
  
  @Override
  public byte[ ] reporteFacturacionCsv(Long idempresa,
                                       String codigo,
                                       String fechaI,
                                       String fechaF) throws IOException,
                                                      ParseException,
                                                      Exception {
    byte[ ] vByte = null;
    Empresas empresa = empresaDao.buscarEmpresaID(Long.valueOf(idempresa), "A");
    List<ReporteFacturacion> vReporteFacturacionList = this.listaFacturacion(empresa.getNit( ), codigo,
      fechaI, fechaF);
    if (vReporteFacturacionList.size( ) == 0) throw new NotFoundException("No hay datos...!!");
    StringWriter vStringWriter = new StringWriter( );
    ICsvBeanWriter vCsvWriter = new CsvBeanWriter(vStringWriter, CsvPreference.STANDARD_PREFERENCE);
    String[ ] vMapData = {"fecha","codigo_susursal","municipio_susursal","descripcion_operacion",
                          "numero_documento","razon_social","numero_factura","monto_total","codigo_estado",
                          "codigorecepcion","cuf"};
    vCsvWriter.writeHeader(Constants.COL_CSV);
    for(ReporteFacturacion vReporteFacturacion: vReporteFacturacionList) {
      vCsvWriter.write(vReporteFacturacion, vMapData);
    }
    vCsvWriter.flush( );
    vByte = vStringWriter.toString( )
                         .getBytes(StandardCharsets.UTF_8);
    return vByte;
  }
  
  @Override
  public byte[ ] reporteFacturacionSbl(String idempresa,
                                       String fechaI,
                                       String fechaF,
                                       String usuario) throws IOException,
                                                       ParseException,
                                                       Exception {
    Empresas empresa = empresaDao.buscarEmpresaID(Long.valueOf(idempresa), "A");
    List<FacturaSBL> vFacturaSblList = this.ListaSBL(empresa.getNit( ), fechaI, fechaF);
    return aux.generarReporteSblExcel(vFacturaSblList, usuario, aux.ConvertDateString(new Date( ),
      "dd/MM/yyyy HH:mm:ss"), empresa.getRazon_social( ), fechaI + " al " + fechaF, empresa.getNit( ));
  }
  
  public List<FacturaSBL> ListaSBL(String nit,
                                   String fechaI,
                                   String fechaF) throws IOException,
                                                  ParseException,
                                                  Exception {
    String url = _URL + "/factura/factura/factura?nit=" + nit + "&fechaI=" + fechaI + "&fechaF=" + fechaF
      + " 23:59:59";
    ResponseEntity<List<FacturaSBL>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
      new ParameterizedTypeReference<List<FacturaSBL>>( ) {
      });
    return responseEntity.getBody( );
  }
  
  @Override
  public byte[ ] Factura(String nombre_archivo) throws IOException,
                                                ParseException,
                                                Exception {
    Map<String, Object> params = new HashMap<>( );
    params.put("nit", "371283023");
    params.put("telefono", "Teléfono: 3625362");
    params.put("municipio", "Santa Cruz");
    params.put("direccion", "AVENIDA ECUADOR NRO.2523 EDIFICIO DALLAS PISO PISO 6 DPTO. 6 ZONA SOPOCACHI");
    params.put("nro_factura", "321");
    params.put("razon_social", "COSULTORIA Y DESARROLLO TECNOLOGICO MC4 S.R.L.");
    params.put("sucursal", "CASA MATRIZ");
    params.put("punto_venta", "No. Punto de Venta 1");
    params.put("codigo_autorizacion", "19677AA82293550751B3B495F7CDC117483F4CCEA349464A3909EDC74");
    params.put("literal", "Son: Diez 49/100 Bolivianos");
    params.put("fecha", "09/01/2022 10:01 a.m.");
    params.put("nombre", "Juan Loza Cabrera");
    params.put("cambio_oficial", "6.96");
    params.put("cambio_cv", "6.96");
    params.put("documento", "69233208");
    params.put("moneda", "DOLAR");
    params.put("ingreso", "0.1");
    params.put("complemento", "");
    params.put("cod_cliente", "abc123");
    String qr = _UrlCodeQR
      + "/consulta/QR?nit=371283023&cuf=19677AA82293550751B3B495F7CDC117483F4CCEA349464A3909EDC74&numero=321&t=1";
    String qrCode = aux.crearQRCode(qr, 500, 500);
    params.put("codeqr", qrCode);
    List<facturadetalle> lista = new ArrayList<>( );
    facturadetalle fd = new facturadetalle( );
    fd.setCodigo_servicio("1");
    fd.setCantidad(1);
    fd.setDescuento(BigDecimal.ZERO);
    fd.setDescripcion("NOMBRE PRODUCTO");
    fd.setPrecio_unitario(new BigDecimal(10));
    fd.setSubtotal(new BigDecimal("10.49000"));
    fd.setUnidad_medida("Paquete");
    fd.setIce(new BigDecimal("0.43500"));
    fd.setIce_esp(new BigDecimal("0.05000"));
    lista.add(fd);
    byte[ ] bytes = aux.generarReporteTipo("factura/" + nombre_archivo, params, lista, ".pdf");
    return bytes;
  }
  
  @Override
  public byte[ ] reporteCsvShopify(Long idempresa,
                                   String estado,
                                   String fechaI,
                                   String fechaF) throws IOException,
                                                  ParseException,
                                                  Exception {
    Date FI = null, FF = null;
    JasperPrint primero = null, segundo = null;
    Map<String, Object> params = new HashMap<>( );
    try {
      if (fechaI.equals("") && fechaF.equals("")) {
        FI = aux.sumarDiasAFecha(new Date( ), -50000);
        FF = aux.sumarDiasAFecha(new Date( ), 1);
      }else {
        FI = aux.conversionStringDate(fechaI, "dd/MM/yyyy");
        FF = aux.sumarDiasAFecha(aux.conversionStringDate(fechaF, "dd/MM/yyyy"), 1);
      }
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    byte[ ] vByte = null;
    List<IDeudaDao.ShopifyrDao2> List = deudaDao.ReporteCsvShopify(idempresa, estado, FI, FF);
    if (List.size( ) == 0) throw new NotFoundException("No hay datos...!!");
    StringWriter vStringWriter = new StringWriter( );
    ICsvBeanWriter vCsvWriter = new CsvBeanWriter(vStringWriter, CsvPreference.STANDARD_PREFERENCE);
    String[ ] vMapData = {"cliente","fechav","nro_recibo","descripcion_item","fechap","cantidad",
                          "precio_unitario","sub_total","descuento","total","descripcion","Item"};
    vCsvWriter.writeHeader(Constants.SHO_CSV);
    for(IDeudaDao.ShopifyrDao2 vReporteFacturacion: List) {
      vCsvWriter.write(vReporteFacturacion, vMapData);
    }
    vCsvWriter.flush( );
    vByte = vStringWriter.toString( )
                         .getBytes(StandardCharsets.UTF_8);
    return vByte;
  }
}
