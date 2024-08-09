
package cobranza.v2.pgt.com.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import cobranza.v2.pgt.com.dto.DeudaQueryDto;
import cobranza.v2.pgt.com.dto.DeudaQueryGeneralDto;
import cobranza.v2.pgt.com.dto.LiquidacionGeneralQueryDto;
import cobranza.v2.pgt.com.dto.LiquidacionQueryDto;
import cobranza.v2.pgt.com.dto.ParametricaQueryDto;
import cobranza.v2.pgt.com.models.dao.IDeudaDao;
import cobranza.v2.pgt.com.models.dao.IReciboDao;
import cobranza.v2.pgt.com.models.entity.Comision;
import cobranza.v2.pgt.com.models.entity.PagoRestApi;
import cobranza.v2.pgt.com.models.entity.PagoRestDetalle;
import cobranza.v2.pgt.com.models.entity.Parametrica;
import cobranza.v2.pgt.com.models.enums.DocumentoSector;
import cobranza.v2.pgt.com.models.services.EmailService;
import cobranza.v2.pgt.com.models.services.IParametricaServ;
import cobranza.v2.pgt.com.utils.excepcions.OperationException;
import cobranza.v2.pgt.com.utils.excepcions.QrException;
import cobranza.v2.pgt.com.utils.otros.FacturaSBL;
import cobranza.v2.pgt.com.utils.otros.MailRequest;
import cobranza.v2.pgt.com.utils.otros.ReporteFacturacion;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class Auxiliar {
  
  private Logger logger = LoggerFactory.getLogger(Auxiliar.class);
  
  public String letras[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T",
                            "U","V","W","X","Y","Z"};
  
  private final String[ ] UNIDADES = {"","un ","dos ","tres ","cuatro ","cinco ","seis ","siete ","ocho ",
                                      "nueve "};
  
  private final String[ ] DECENAS = {"diez ","once ","doce ","trece ","catorce ","quince ","dieciseis ",
                                     "diecisiete ","dieciocho ","diecinueve","veinte ","treinta ","cuarenta ",
                                     "cincuenta ","sesenta ","setenta ","ochenta ","noventa "};
  
  private final String[ ] CENTENAS = {"","ciento ","doscientos ","trecientos ","cuatrocientos ","quinientos ",
                                      "seiscientos ","setecientos ","ochocientos ","novecientos "};
  private String _http = "http://";
  private String _https = "https://";
  
  public int[ ] errores = {0,0,0,0};
  
  public String[ ] obs = new String[2];
  
  private static final String INSTANCE_ID = "20";
  private static final String CLIENT_ID = "edgar.veliz@pagatodo360.net";
  private static final String CLIENT_SECRET = "b7d7e91ee03d44e5aadcf7b59dad2d16";
  private static final String WA_GATEWAY_URL2 = "http://api.whatsmate.net/v3/whatsapp/single/url/message/"
    + INSTANCE_ID;
  private static final String WA_GATEWAY_URL = "http://api.whatsmate.net/v3/whatsapp/single/text/message/"
    + INSTANCE_ID;
  private static String HMAC_SHA256 = "HmacSHA256";
  private static final String secretKey = "mustbe16byteskey";
  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  @Autowired
  private EmailService emailService;
  
  @Autowired
  private IParametricaServ parametricaServ;
  
  public String getAuthotization( ) {
    return Base64.getEncoder( )
                 .encodeToString(("jwtclientC0br4nz@PGT360:secretkeyC0br4nz@PGT360").getBytes( ));
  }
  
  public Workbook getlibro(MultipartFile file) {
    Workbook libro = null;
    String extencion = FilenameUtils.getExtension(file.getOriginalFilename( ));
    try {
      if (extencion.equalsIgnoreCase("xlsx")) {
        libro = new XSSFWorkbook(file.getInputStream( ));
      }else if (extencion.equalsIgnoreCase("xls")) { libro = new HSSFWorkbook(file.getInputStream( )); }
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    return libro;
  }
  
  public String cambio(Cell x) {
    if (x.getCellType( ) == 0) { return Integer.toString(( int ) x.getNumericCellValue( )); }
    return x.getStringCellValue( );
  }
  
  public boolean numero(String valor) {
    try {
      Long.parseLong(valor);
      return true;
    }
    catch(NumberFormatException excepcion) {
      return false;
    }
  }
  
  public String columna(int x) { return this.letras[x]; }
  
  public int[ ] errores( ) { return this.errores; }
  
  @SuppressWarnings("deprecation")
  public String ConversionDateString(Cell x) throws ParseException {
    return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(this.cambio(x)));
    
  }
  
  public String ConversionDateString(Date fecha,
                                     String formato) throws ParseException,
                                                     Exception {
    return new SimpleDateFormat(formato).format(fecha);
  }
  
  /**
     * 
     * @param x Cell Ej.: 31-12-1998
     * @return Thu Dec 31 00:00:00 IST 1998
     * @throws ParseException
     */
  public Date conversionStringDate(Cell x) throws ParseException {
    return new SimpleDateFormat("dd-MM-yy").parse(x.getStringCellValue( ));
  }
  
  public Date conversionStringDate(String fecha) throws ParseException {
    return new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
  }
  
  public Date conversionStringDate(String fecha,
                                   String formato) throws ParseException {
    return new SimpleDateFormat(formato).parse(fecha);
  }
  
  public boolean isdate(String fecha,
                        String formato) {
    try {
      Date d = new SimpleDateFormat(formato).parse(fecha);
      return true;
    }
    catch(ParseException e) {
      e.printStackTrace( );
      return false;
    }
  }
  
  public Date sumarDiasAFecha(Date fecha,
                              int dias) {
    if (dias == 0) return fecha;
    Calendar calendar = Calendar.getInstance( );
    calendar.setTime(fecha);
    calendar.add(Calendar.DAY_OF_YEAR, dias);
    return calendar.getTime( );
  }
  
  @SuppressWarnings("deprecation")
  public String ConversionDate(Cell x) throws ParseException {
    System.out.print(new Date(this.cambio(x)));
    System.out.println("\t" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(this.cambio(x))));
    return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(this.cambio(x)));
    
  }
  
  public String convertJsonString(List<?> lista) {
    ObjectMapper mapper = new ObjectMapper( );
    String jsonString = "";
    try {
      jsonString = mapper.writeValueAsString(lista);
    }
    catch(JsonProcessingException e) {
      e.printStackTrace( );
    }
    return jsonString;
  }
  
  public List<String> convertStringListString(String data) {
    return new ArrayList<String>(Arrays.asList(data.split(",")));
  }
  
  // public List<Long> convertStringListLong(List<String> data) {
  // return new ArrayList<Long>(Arrays.asList(data.split(",")));
  // }
  
  public String cambioLocalDate(int days) {
    LocalDate start = LocalDate.of(1900, 1, 1);
    LocalDate date = start.plusDays(days)
                          .minusDays(2);
    String s = date.format(DateTimeFormatter.BASIC_ISO_DATE);
    return s.substring(0, 6);
  }
  
  public String Convertir(String numero,
                          boolean mayusculas) {
    String literal = "";
    String parte_decimal;
    numero = numero.replace(".", ",");
    if (numero.indexOf(",") == -1) { numero = numero + ",00"; }
    if (Pattern.matches("\\d{1,9},\\d{1,2}", numero)) {
      String Num[] = numero.split(",");
      parte_decimal = Num[1] + "/100 bolivianos";
      if (Integer.parseInt(Num[0]) == 0) {
        literal = "cero ";
      }else if (Integer.parseInt(Num[0]) > 999999) {
        literal = getMillones(Num[0]);
      }else if (Integer.parseInt(Num[0]) > 999) {
        literal = getMiles(Num[0]);
      }else if (Integer.parseInt(Num[0]) > 99) {
        literal = getCentenas(Num[0]);
      }else if (Integer.parseInt(Num[0]) > 9) {
        literal = getDecenas(Num[0]);
      }else {
        literal = getUnidades(Num[0]);
      }
      if (mayusculas) {
        return (literal + parte_decimal).toUpperCase( );
      }else {
        return(literal.substring(0, 1)
                      .toUpperCase( ) + literal.substring(1) + parte_decimal);
      }
    }else {
      return literal = null;
    }
  }
  
  private String getUnidades(String numero) {
    String num = numero.substring(numero.length( ) - 1);
    return UNIDADES[Integer.parseInt(num)];
  }
  
  private String getDecenas(String num) {
    int n = Integer.parseInt(num);
    if (n < 10) {
      return getUnidades(num);
    }else if (n > 19) {
      String u = getUnidades(num);
      if (u.equals("")) {
        return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8];
      }else {
        return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u;
      }
    }else {
      return DECENAS[n - 10];
    }
  }
  
  private String getCentenas(String num) {
    if (Integer.parseInt(num) > 99) {
      if (Integer.parseInt(num) == 100) {
        return " cien ";
      }else {
        return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
      }
    }else {
      return getDecenas(Integer.parseInt(num) + "");
    }
  }
  
  private String getMiles(String numero) {
    String c = numero.substring(numero.length( ) - 3);
    String m = numero.substring(0, numero.length( ) - 3);
    String n = "";
    if (Integer.parseInt(m) > 0) {
      n = getCentenas(m);
      return n + "mil " + getCentenas(c);
    }else {
      return "" + getCentenas(c);
    }
    
  }
  
  private String getMillones(String numero) {
    String miles = numero.substring(numero.length( ) - 6);
    String millon = numero.substring(0, numero.length( ) - 6);
    String n = "";
    if (millon.length( ) > 1) {
      n = getCentenas(millon) + "millones ";
    }else {
      n = getUnidades(millon) + "millon ";
    }
    return n + getMiles(miles);
  }
  
  public void exportarReporte(String archivo,
                              Map<String, Object> parameters,
                              HttpServletResponse response) throws Exception {
    logger.info("Generando el reporte " + archivo + " para exportar");
    JasperPrint jasperPrint = null;
    ServletOutputStream outputStream = null;
    try {
      JasperReport report = JasperCompileManager.compileReport(new ClassPathResource(
        "/reports/" + archivo + ".jrxml").getInputStream( ));
      jasperPrint = JasperFillManager.fillReport(report, parameters, jdbcTemplate.getDataSource( )
                                                                                 .getConnection( ));
      response.setHeader("Content-Disposition", "attachment;filename" + archivo + ".xlsx");
      response.setContentType("application/octet-stream");
      response.setContentLength(4096);
      outputStream = response.getOutputStream( );
      JRXlsxExporter exporter = new JRXlsxExporter( );
      exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
      exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
      exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, archivo + ".xlsx");
      exporter.exportReport( );
      logger.info("Reporte Exportado...");
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
  }
  
  public byte[ ] reporteAll(String archivo,
                            Map<String, Object> parameters,
                            JREmptyDataSource jrb) {
    byte[ ] bytes = null;
    logger.info("Generando el reporte....." + archivo);
    try {
      JasperReport report = JasperCompileManager.compileReport(new ClassPathResource(
        "/reports/" + archivo + ".jrxml").getInputStream( ));
      JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, jrb);
      bytes = JasperExportManager.exportReportToPdf(jasperPrint);
      logger.info("Reporte generado");
    }
    catch(JRException | IOException e) {
      e.printStackTrace( );
    }
    return bytes;
  }
  
  public JasperPrint reporteList(String archivo,
                                 Map<String, Object> parameters,
                                 JREmptyDataSource jrb) {
    JasperPrint jasperPrint = null;
    try {
      JasperReport report = JasperCompileManager.compileReport(new ClassPathResource(
        "/reports/" + archivo + ".jrxml").getInputStream( ));
      logger.info("Generando el reporte de " + parameters.get("comercio") + " :: archivo " + archivo);
      jasperPrint = JasperFillManager.fillReport(report, parameters, jrb);
    }
    catch(JRException | IOException e) {
      e.printStackTrace( );
    }
    return jasperPrint;
  }
  
  public byte[ ] reporteBytes(JasperPrint jprintlist) {
    byte[ ] bytes = null;
    try {
      bytes = JasperExportManager.exportReportToPdf(jprintlist);
      logger.info("Reporte generado");
    }
    catch(JRException e) {
      e.printStackTrace( );
    }
    return bytes;
  }
  
  public byte[ ] reporte(String archivo,
                         Map<String, Object> parameters) {
    byte[ ] bytes = null;
    try {
      logger.info("Generando el reporte.... " + archivo);
      logger.info("Parametros: " + new JSONObject(parameters));
      JasperReport report = JasperCompileManager.compileReport(new ClassPathResource(
        "/reports/" + archivo + ".jrxml").getInputStream( ));
      JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, jdbcTemplate.getDataSource( )
                                                                                             .getConnection( ));
      // JasperPrint jasperPrint =
      // JasperFillManager.fillReport(
      // new ClassPathResource("/reports/" + archivo +
      // ".jasper").getInputStream(), parameters,
      // jdbcTemplate.getDataSource().getConnection());
      bytes = JasperExportManager.exportReportToPdf(jasperPrint);
      logger.info("Reporte generado");
    }
    catch(JRException e) {
      e.printStackTrace( );
    }
    catch(SQLException e) {
      e.printStackTrace( );
    }
    catch(IOException e) {
      e.printStackTrace( );
    }
    return bytes;
  }
  
  public byte[ ] reporteLista(String archivo,
                              Map<String, Object> parameters,
                              List<?> lista) {
    byte[ ] bytes = null;
    try {
      logger.info("Generando el reporte.... " + archivo);
      logger.info("Parametros: " + new JSONObject(parameters));
      JRDataSource Source = new JRBeanCollectionDataSource(lista);
      JasperReport report = JasperCompileManager.compileReport(new ClassPathResource(
        "/reports/" + archivo + ".jrxml").getInputStream( ));
      JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, Source);
      bytes = JasperExportManager.exportReportToPdf(jasperPrint);
      logger.info("Reporte generado....");
    }
    catch(JRException e) {
      e.printStackTrace( );
    }
    catch(IOException e) {
      e.printStackTrace( );
    }
    return bytes;
  }
  
  public byte[ ] generarXLSReport(String archivo,
                                  Map<String, Object> params,
                                  List<?> lista) {
    byte[ ] bytes = null;
    JasperReport jasperReport = null;
    JRDataSource Source = new JRBeanCollectionDataSource(lista);
    try {
      jasperReport = JasperCompileManager.compileReport(new ClassPathResource(
        "/reports/" + archivo + ".jrxml").getInputStream( ));
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, Source);
      try(ByteArrayOutputStream byteArray = new ByteArrayOutputStream( )) {
        JRXlsxExporter exporter = new JRXlsxExporter( );
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
        exporter.exportReport( );
        bytes = byteArray.toByteArray( );
        logger.info("Reporte generado....");
      }
      catch(IOException e) {
        System.out.println("==============");
        System.out.println(e);
      }
      return bytes;
    }
    catch(JRException | IOException e) {
      System.out.println("------------------");
      System.out.println(e);
    }
    return bytes;
  }
  
  public byte[ ] generarReporteTipo(String archivo,
                                    Map<String, Object> params,
                                    List<?> lista,
                                    String tipo) {
    JasperReport jasperReport = null;
    JRDataSource dataSource = null;
    JRAbstractExporter exporter = null;
    byte[ ] bytes = null;
    try {
      jasperReport = JasperCompileManager.compileReport(new ClassPathResource(
        "/reports/" + archivo + ".jrxml").getInputStream( ));
    }
    catch(JRException | IOException e) {
      System.err.println(e);
    }
    if (lista.size( ) > 0) {
      dataSource = new JRBeanCollectionDataSource(lista);
    }else {
      dataSource = new JREmptyDataSource( );
    }
    try {
      switch(tipo){
        case ".xlsx":
          exporter = new JRXlsxExporter( );
          break;
        case ".csv":
          exporter = new JRCsvExporter( );
          break;
        case ".doc":
          exporter = new JRDocxExporter( );
          break;
        case ".rtf":
          exporter = new JRRtfExporter( );
          break;
        case ".txt":
          exporter = new JRTextExporter( );
          break;
        case ".xml":
          exporter = new JRXmlExporter( );
          break;
        case ".html":
          exporter = new HtmlExporter( );
          break;
        case ".pdf":
          exporter = new JRPdfExporter( );
          break;
        default:
          exporter = new JRPdfExporter( );
          break;
      }
      // if ("PDF".equals(tipo)) {
      // exporter = new JRPdfExporter( );
      // }else if ("Excel".equals(tipo)) { exporter = new JRXlsxExporter( ); }
    }
    catch(Exception e) {
      System.out.println(e);
    }
    try {
      ByteArrayOutputStream byteArray = new ByteArrayOutputStream( );
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
      exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
      exporter.exportReport( );
      bytes = byteArray.toByteArray( );
      logger.info("Reporte generado....");
      byteArray.close( );
    }
    catch(JRException | IOException e) {
      System.err.println(e);
    }
    return bytes;
  }
  
  public byte[ ] generarReporteDeudaExcel(List<DeudaQueryGeneralDto> pDeudaQueryGeneralDtoList,
                                          String pUsuario,
                                          String pFechaHora,
                                          String pComercio,
                                          String pIntervalo,
                                          File pLogo) {
    byte[ ] vByteArray = null;
    int vNumeroFila = 0;
    XSSFWorkbook vLibro = new XSSFWorkbook( );
    XSSFSheet vHoja = vLibro.createSheet(Constants.NOMBRE_HOJA);
    vHoja.setDisplayGridlines(false);
    vHoja.setMargin(Sheet.RightMargin, 0.5);
    vHoja.setMargin(Sheet.BottomMargin, 0.5);
    vHoja.setMargin(Sheet.LeftMargin, 0.5);
    vHoja.setMargin(Sheet.TopMargin, 0.5);
    vHoja.setMargin(Sheet.FooterMargin, 0);
    vHoja.setMargin(Sheet.HeaderMargin, 0);
    vHoja.getPrintSetup( )
         .setLandscape(true);
    vHoja.getPrintSetup( )
         .setPaperSize(HSSFPrintSetup.LETTER_PAPERSIZE);
    vNumeroFila = this.cargarDeudaTitulo(vLibro, vHoja, vNumeroFila, pIntervalo, "TODOS", pUsuario,
      pFechaHora, pLogo);
    if (pDeudaQueryGeneralDtoList.size( ) > 0) {
      for(DeudaQueryGeneralDto vDeudaQueryGeneralDto: pDeudaQueryGeneralDtoList) {
        Map<Integer, Object[ ]> vDeudas = this.cargarDeudasMap(vDeudaQueryGeneralDto
                                                                                    .getVDeudaQueryDtoList( ));
        switch(vDeudaQueryGeneralDto.getVEstadoDeuda( )){
          case Constants.STATE_PAGADO:
            vNumeroFila = this.cargarDeudaDescripcion(vLibro, vHoja, vNumeroFila, "ESTADO: "
              + Constants.STATE_PAGADO);
            break;
          case Constants.STATE_ANULADO:
            vNumeroFila = this.cargarDeudaDescripcion(vLibro, vHoja, vNumeroFila, "ESTADO: "
              + Constants.STATE_ANULADO);
            break;
          case Constants.STATE_PENDIENTE:
            vNumeroFila = this.cargarDeudaDescripcion(vLibro, vHoja, vNumeroFila, "ESTADO: "
              + Constants.STATE_PENDIENTE);
            break;
        }
        vNumeroFila = this.cargarDeudaNombreColumna(vLibro, vHoja, vNumeroFila, Constants.D_COLUMNAS);
        vNumeroFila = this.cargarDeudaTabla(vDeudas, vLibro, vHoja, vNumeroFila, Constants.D_COLUMNAS.length);
        vNumeroFila = this.cargarDeudaTotal(vLibro, vHoja, vNumeroFila, vDeudaQueryGeneralDto
                                                                                             .getVEstadoDeuda( ),
          vDeudaQueryGeneralDto.getVTotalDeuda( ), vDeudaQueryGeneralDto.getVTotalPagado( ));
      }
      vNumeroFila = this.cargarDeudaTablaResumen(vLibro, vHoja, vNumeroFila, pDeudaQueryGeneralDtoList);
    }
    ByteArrayOutputStream vByteArrayOutputStream = new ByteArrayOutputStream( );
    try {
      vLibro.write(vByteArrayOutputStream);
      vByteArrayOutputStream.close( );
      vByteArray = vByteArrayOutputStream.toByteArray( );
    }
    catch(IOException io) {
      logger.error(io.getMessage( ));
    }
    logger.info("Excel generado....");
    return vByteArray;
  }
  
  public byte[ ] generarReporteExcel(List<ReporteFacturacion> pReporteFacturacion,
                                     String pUsuario,
                                     String pFechaHora,
                                     String pComercio,
                                     File pLogo,
                                     String pRango) {
    byte[ ] vByteArray = null;
    int vNumeroFila = 0;
    XSSFWorkbook vLibro = new XSSFWorkbook( );
    XSSFSheet vHoja = vLibro.createSheet(Constants.NOMBRE_HOJA);
    vHoja.setDisplayGridlines(false);
    vHoja.setMargin(Sheet.RightMargin, 0.5);
    vHoja.setMargin(Sheet.BottomMargin, 0.5);
    vHoja.setMargin(Sheet.LeftMargin, 0.5);
    vHoja.setMargin(Sheet.TopMargin, 0.5);
    vHoja.setMargin(Sheet.FooterMargin, 0);
    vHoja.setMargin(Sheet.HeaderMargin, 0);
    // vNumeroFila = this.cargarImagen(vLibro, vHoja, pLogo, vNumeroFila);
    vNumeroFila = this.cargarTituloPrincipalExcel(vLibro, vHoja, vNumeroFila, pRango);
    vNumeroFila = this.cargarPrincipalExcel(vHoja, vNumeroFila, pUsuario, pFechaHora, pComercio);
    String[ ] vCabecera = new String[ ]{Constants.COL_FECHA,Constants.COL_CODSUC,Constants.COL_SUCURSAL,
                                        Constants.COL_TFAC,Constants.COL_NIT,Constants.COL_RSOCIAL,
                                        Constants.COL_NROFACT,Constants.COL_MONTO,Constants.COL_ESTADO,
                                        Constants.COL_TEMISION,Constants.COL_CUF};
    List<ReporteFacturacion> vEmitidas = pReporteFacturacion.stream( )
                                                            .filter(f -> f.getOtros( )
                                                                          .equals(Constants.TIPO_EMITIDAS))
                                                            .collect(Collectors.toList( ));
    List<ReporteFacturacion> vAnuladas = pReporteFacturacion.stream( )
                                                            .filter(f -> f.getOtros( )
                                                                          .equals(Constants.TIPO_ANULADAS))
                                                            .collect(Collectors.toList( ));
    List<ReporteFacturacion> vRechazadas = pReporteFacturacion.stream( )
                                                              .filter(f -> f.getOtros( )
                                                                            .equals(
                                                                              Constants.TIPO_RECHAZADAS))
                                                              .collect(Collectors.toList( ));
    if (vEmitidas.size( ) > 0) {
      Map<Integer, String[ ]> vEmitidasContenido = this.cargarDatosMap(vEmitidas);
      vNumeroFila = this.cargarTitulosExcel(vLibro, vHoja, vNumeroFila, Constants.TIPO_EMITIDAS);
      vNumeroFila = this.cargarCabeceraExcel(vLibro, vHoja, vNumeroFila, vCabecera);
      vNumeroFila = this.cargarDatosExcel(vEmitidasContenido, vLibro, vHoja, vNumeroFila, vCabecera.length);
    }
    if (vAnuladas.size( ) > 0) {
      Map<Integer, String[ ]> vAnuladasContenido = this.cargarDatosMap(vAnuladas);
      vNumeroFila = this.cargarTitulosExcel(vLibro, vHoja, vNumeroFila, Constants.TIPO_ANULADAS);
      vNumeroFila = this.cargarCabeceraExcel(vLibro, vHoja, vNumeroFila, vCabecera);
      vNumeroFila = this.cargarDatosExcel(vAnuladasContenido, vLibro, vHoja, vNumeroFila, vCabecera.length);
    }
    if (vRechazadas.size( ) > 0) {
      Map<Integer, String[ ]> vRechazadasContenido = this.cargarDatosMap(vRechazadas);
      vNumeroFila = this.cargarTitulosExcel(vLibro, vHoja, vNumeroFila, Constants.TIPO_RECHAZADAS);
      vNumeroFila = this.cargarCabeceraExcel(vLibro, vHoja, vNumeroFila, vCabecera);
      vNumeroFila = this.cargarDatosExcel(vRechazadasContenido, vLibro, vHoja, vNumeroFila, vCabecera.length);
    }
    ByteArrayOutputStream vByteArrayOutputStream = new ByteArrayOutputStream( );
    try {
      vLibro.write(vByteArrayOutputStream);
      vByteArrayOutputStream.close( );
      vByteArray = vByteArrayOutputStream.toByteArray( );
    }
    catch(IOException io) {
      logger.error(io.getMessage( ));
    }
    logger.info("Excel generado....");
    return vByteArray;
  }
  
  public byte[ ] generarReporteSblExcel(List<FacturaSBL> pFacturaSbl,
                                        String pUsuario,
                                        String pFechaHora,
                                        String pComercio,
                                        String pFechaRango,
                                        String pNit) {
    
    byte[ ] vByteArray = null;
    int vNumeroFila = 0;
    XSSFWorkbook vLibro = new XSSFWorkbook( );
    XSSFSheet vHoja = vLibro.createSheet(Constants.SBLT_TITULO_REPORTE);
    vHoja.setDisplayGridlines(false);
    vHoja.setMargin(Sheet.RightMargin, 0.5);
    vHoja.setMargin(Sheet.BottomMargin, 0.5);
    vHoja.setMargin(Sheet.LeftMargin, 0.5);
    vHoja.setMargin(Sheet.TopMargin, 0.5);
    vHoja.setMargin(Sheet.FooterMargin, 0);
    vHoja.setMargin(Sheet.HeaderMargin, 0);
    vHoja.getPrintSetup( )
         .setLandscape(true);
    vHoja.getPrintSetup( )
         .setPaperSize(HSSFPrintSetup.LETTER_PAPERSIZE);
    vNumeroFila = this.cargarSblCabecera(vLibro, vHoja, vNumeroFila, pFechaRango, pFechaHora, pUsuario, pNit,
      pComercio);
    vNumeroFila = this.cargarSblColumnas(vLibro, vHoja, vNumeroFila, Constants.SBLC_COLUMNAS);
    Map<Integer, Object[ ]> vFacturaSbl = this.cargarFacturasMap(pFacturaSbl);
    vNumeroFila = this.cargarSblValores(vFacturaSbl, vLibro, vHoja, vNumeroFila,
      Constants.SBLC_COLUMNAS.length);
    double vTotalUsd = pFacturaSbl.stream( )
                                  .mapToDouble(o -> o.getMontoTotalMoneda( ) == null ? 0.0 : o
                                                                                              .getMontoTotalMoneda( )
                                                                                              .doubleValue( ))
                                  .sum( );
    double vTotalBs = pFacturaSbl.stream( )
                                 .mapToDouble(o -> o.getMontoTotal( ) == null ? 0.0 : o.getMontoTotal( )
                                                                                       .doubleValue( ))
                                 .sum( );
    vNumeroFila = this.cargarSblTotales(vLibro, vHoja, vNumeroFila, vFacturaSbl.size( ), vTotalUsd, vTotalBs);
    ByteArrayOutputStream vByteArrayOutputStream = new ByteArrayOutputStream( );
    try {
      vLibro.write(vByteArrayOutputStream);
      vByteArrayOutputStream.close( );
      vByteArray = vByteArrayOutputStream.toByteArray( );
    }
    catch(IOException io) {
      logger.error(io.getMessage( ));
    }
    logger.info("Excel generado....");
    return vByteArray;
  }
  
  public String generarToken( ) throws IOException {
    String auth = getAuthotization( );
    URL url = new URL("http://localhost:37/oauth/token");
    HttpURLConnection con = ( HttpURLConnection ) url.openConnection( );
    con.setRequestMethod("POST");
    con.setRequestProperty("Authorization", "Basic " + auth);
    
    InputStreamReader in = new InputStreamReader(con.getInputStream( ));
    BufferedReader br = new BufferedReader(in);
    String output;
    StringBuffer buffer = new StringBuffer( );
    while((output = br.readLine( )) != null) {
      buffer.append(output);
    }
    in.close( );
    con.disconnect( );
    
    return buffer.toString( );
  }
  
  public Map<String, Object> postRequest(String url,
                                         String requestBody,
                                         String token) throws IOException {
    URL httpUrl = new URL(url);
    HttpURLConnection con = ( HttpURLConnection ) httpUrl.openConnection( );
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json");
    con.setRequestProperty("Authorization", token);
    con.setDoOutput(true);
    
    try {
      byte[ ] outputInBytes = requestBody.getBytes("UTF-8");
      OutputStream os = con.getOutputStream( );
      os.write(outputInBytes);
      os.close( );
      
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream( )));
      String inputLine;
      StringBuffer buffer = new StringBuffer( );
      
      while((inputLine = in.readLine( )) != null) {
        buffer.append(inputLine);
      }
      in.close( );
      
      Map<String, Object> map = new HashMap<>( );
      map.put("status", con.getResponseCode( ));
      map.put("data", buffer.toString( ));
      map.put("error", false);
      System.out.println("Sin Error");
      return map;
    }
    catch(Exception e) {
      InputStream errorstream = con.getErrorStream( );
      String response = "";
      String line;
      BufferedReader br = new BufferedReader(new InputStreamReader(errorstream));
      while((line = br.readLine( )) != null) {
        response += line;
      }
      Map<String, Object> map = new HashMap<>( );
      map.put("status", con.getResponseCode( ));
      map.put("data", response);
      map.put("error", true);
      System.out.println("Error: " + response);
      return map;
    }
  }
  
  public Map<String, Object> getRequestShopify(String url,
                                               String credencial) throws IOException {
    URL httpUrl = new URL(url);
    HttpURLConnection con = ( HttpURLConnection ) httpUrl.openConnection( );
    con.setRequestMethod("GET");
    con.setRequestProperty("Content-Type", "application/json");
    con.setRequestProperty("X-Shopify-Access-Token", credencial);
    con.setDoOutput(true);
    
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream( )));
      String inputLine;
      StringBuffer buffer = new StringBuffer( );
      
      while((inputLine = in.readLine( )) != null) {
        buffer.append(inputLine);
      }
      in.close( );
      
      Map<String, Object> map = new HashMap<>( );
      map.put("status", con.getResponseCode( ));
      map.put("data", buffer.toString( ));
      map.put("error", false);
      System.out.println("Sin Error");
      return map;
    }
    catch(Exception e) {
      InputStream errorstream = con.getErrorStream( );
      String response = "";
      String line;
      BufferedReader br = new BufferedReader(new InputStreamReader(errorstream));
      while((line = br.readLine( )) != null) {
        response += line;
      }
      Map<String, Object> map = new HashMap<>( );
      map.put("status", con.getResponseCode( ));
      map.put("data", response);
      map.put("error", true);
      System.out.println("Error: " + response);
      return map;
    }
  }
  
  public String createJWT2(String id,
                           String issuer,
                           String subject,
                           Map claims,
                           Date fecha) {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    byte[ ] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("jf9i4jgu83nfl0jfu57ejf7");
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName( ));
    JwtBuilder builder = Jwts.builder( )
                             .setClaims(claims)
                             .setId(id)
                             .setIssuedAt(new Date(System.currentTimeMillis( )))
                             .setSubject(subject)
                             .setIssuer(issuer)
                             .signWith(signatureAlgorithm, signingKey);
    builder.setExpiration(fecha);
    return builder.compact( );
  }
  
  public Claims decodeJWT(String jwt) {
    Claims claims = Jwts.parser( )
                        .setSigningKey(DatatypeConverter.parseBase64Binary("jf9i4jgu83nfl0jfu57ejf7"))
                        .parseClaimsJws(jwt)
                        .getBody( );
    return claims;
  }
  
  public void enviarcorreo(String correo,
                           String msj) {
    logger.info("enviando correo " + correo);
    MailRequest mail = new MailRequest( );
    mail.setTo(correo);
    mail.setFrom("pagatodo.360.2020@gmail.com");
    mail.setSubject("Envio de pago de Pagatodo360");
    emailService.sendSimpleMessage(mail);
  }
  
  public static void sendMessage(String number,
                                 String message) throws Exception {
    
    String jsonPayload = new StringBuilder( ).append("{")
                                             .append("\"number\":\"")
                                             .append(number)
                                             .append("\",")
                                             .append("\"message\":\"")
                                             .append(message)
                                             .append("\"")
                                             .append("}")
                                             .toString( );
    
    URL url = new URL(WA_GATEWAY_URL);
    HttpURLConnection conn = ( HttpURLConnection ) url.openConnection( );
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
    conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
    conn.setRequestProperty("Content-Type", "application/json");
    
    OutputStream os = conn.getOutputStream( );
    os.write(jsonPayload.getBytes( ));
    os.flush( );
    os.close( );
    
    int statusCode = conn.getResponseCode( );
    System.out.println("Response from WA Gateway: \n");
    System.out.println("Status Code: " + statusCode);
    BufferedReader br = new BufferedReader(
      new InputStreamReader((statusCode == 200) ? conn.getInputStream( ) : conn.getErrorStream( )));
    String output;
    while((output = br.readLine( )) != null) {
      System.out.println(output);
    }
    conn.disconnect( );
  }
  
  /**
   * Sends out a URL via WhatsMate WA Gateway.
   */
  public static void sendUrl(String number,
                             String url,
                             String message) throws Exception {
    String jsonPayload = new StringBuilder( ).append("{")
                                             .append("\"number\":\"")
                                             .append(number)
                                             .append("\",")
                                             .append("\"message\":\"")
                                             .append(message)
                                             .append("\",")
                                             .append("\"url\":\"")
                                             .append(url)
                                             .append("\"")
                                             .append("}")
                                             .toString( );
    
    URL url3 = new URL(WA_GATEWAY_URL2);
    HttpURLConnection conn = ( HttpURLConnection ) url3.openConnection( );
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
    conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
    conn.setRequestProperty("Content-Type", "application/json");
    
    OutputStream os = conn.getOutputStream( );
    os.write(jsonPayload.getBytes( ));
    os.flush( );
    os.close( );
    
    int statusCode = conn.getResponseCode( );
    System.out.println("Response from WA Gateway: \n");
    System.out.println("Status Code: " + statusCode);
    BufferedReader br = new BufferedReader(
      new InputStreamReader((statusCode == 200) ? conn.getInputStream( ) : conn.getErrorStream( )));
    String output;
    while((output = br.readLine( )) != null) {
      System.out.println(output);
    }
    conn.disconnect( );
  }
  
  public boolean isRowEmpty(Row row,
                            int totalRow) {
    int cont = 0;
    for(int c = row.getFirstCellNum( );c < row.getLastCellNum( );c++) {
      Cell cell = row.getCell(c);
      if (cell != null && cell.getCellType( ) != Cell.CELL_TYPE_BLANK) cont++;
    }
    // System.out.println("cont " + cont);
    if (totalRow <= cont) {
      return true;
    }else {
      return false;
    }
  }
  
  public Sort.Direction getSortDirection(String direction) {
    if (direction.equals("asc")) {
      return Sort.Direction.ASC;
    }else if (direction.equals("desc")) { return Sort.Direction.DESC; }
    return Sort.Direction.ASC;
  }
  
  /**
   * 
   * @param d Date, Time or TimeStamp
   * @param format String 'MM/dd/yyyy','dd-M-yyyy
   *        hh:mm:ss','dd MMMM yyyy'.....
   * @return String 04/13/2015, 13-4-2015 10:59:26 ,
   *         13 April 2015
   * @throws ParseException
   */
  public String ConvertDateString(Date d,
                                  String format) throws ParseException {
    return new SimpleDateFormat(format).format(d);
  }
  
  public String generarMatricula(int longitud) {
    String matricula = "";
    int a;
    String CaracteresNoDeseados = ":;<=>?@[\\]^_`";
    for(int i = 0;i < longitud;i++) {
      do {
        a = ( int ) (Math.random( ) * 74 + 48);
      }
      while(CaracteresNoDeseados.indexOf(a) >= 0);
      char letra = ( char ) a;
      matricula += letra;
    }
    return matricula;
  }
  
  public Map<String, Object> validaProtocolo(String protocolo,
                                             Map<String, Object> map,
                                             String data) {
    map.put("error", false);
    if (!protocolo.equals("string") && (!protocolo.equals(""))) {
      try {
        new URL(protocolo).toURI( );
      }
      catch(URISyntaxException exception) {
        map.put("mensaje", "(" + data + ") URL erroenia, ej.: 'http://xxx.yyy.zzz' o 'https://xxx.yyy.zzz'");
        map.put("error", true);
      }
      catch(MalformedURLException exception) {
        map.put("mensaje", "(" + data + ") falta protocolo URL, ej.: 'http://' o 'https://'");
        map.put("error", true);
      }
    }
    return map;
  }
  
  public Map<String, Object> validarBody(List<FieldError> errors,
                                         PagoRestApi pago,
                                         Map<String, Object> map,
                                         BindingResult bindingResult) {
    map.put("error", false);
    System.out.println("******************");
    System.out.println(bindingResult.hasErrors( ));
    if (bindingResult.hasErrors( )) {
      System.out.println("******************");
      for(FieldError error: errors) {
        System.out.println("==>> " + error.toString( ));
        map.put("mensaje", "Campo requerido " + error.getDefaultMessage( ));
        map.put("error", true);
      }
    }else {
      if (pago.getDetalle( )
              .size( ) != 0) {
        BigDecimal _subtotal = BigDecimal.ZERO;
        for(Iterator<PagoRestDetalle> i = pago.getDetalle( )
                                              .iterator( );i.hasNext( );) {
          PagoRestDetalle detalle = i.next( );
          if (detalle.getDescripcion_item( )
                     .isEmpty( ) || detalle.getDescripcion_item( ) == null || detalle.getDescripcion_item( )
                                                                                     .equals("")) {
            map.put("mensaje", "Descripcion del detalle vacio");
            map.put("error", true);
          }
          if (pago.getDetalle( )
                  .isEmpty( )) {
            map.put("mensaje", "detalle vacio");
            map.put("error", true);
          }
          if (detalle.getItem( )
                     .isEmpty( ) || detalle.getItem( ) == null || detalle.getItem( )
                                                                         .equals("")) {
            map.put("mensaje", "Item del detalle vacio");
            map.put("error", true);
          }
          if (!(this.compararPrecios(detalle.getPrecio_unitario( ), detalle.getCantidad( ), detalle
                                                                                                   .getSub_total( )))) {
            map.put("mensaje", "Cantidad por el precio no coincide con el subtotal");
            map.put("error", true);
          }
          _subtotal = _subtotal.add(detalle.getSub_total( ));
        }
        if (!(_subtotal.equals(pago.getMonto( )))) {
          map.put("mensaje", "El monto total no coincide con la sumatoria de los subtotales");
          map.put("error", true);
        }
      }else {
        map.put("mensaje", "Detalle vacio..¡");
        map.put("error", true);
      }
    }
    if (pago.getNro_recibo( ) < 0) {
      map.put("mensaje", "Nro. recibo negativo..!!");
      map.put("error", true);
    }
    if (Long.valueOf(pago.getNit( )) < 0) {
      map.put("mensaje", "N.I.T. negativo..!!");
      map.put("error", true);
    }
    return map;
  }
  
  public boolean compararPrecios(BigDecimal precio,
                                 Integer cant,
                                 BigDecimal subtotal) {
    BigDecimal sub = precio.multiply(new BigDecimal(cant));
    sub = sub.setScale(2, BigDecimal.ROUND_HALF_UP);
    if (sub.equals(subtotal)) return true;
    return false;
  }
  
  public String sign(String data,
                     String secretKey) throws InvalidKeyException,
                                       NoSuchAlgorithmException,
                                       UnsupportedEncodingException {
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes( ), HMAC_SHA256);
    Mac mac = Mac.getInstance(HMAC_SHA256);
    mac.init(secretKeySpec);
    byte[ ] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
    return DatatypeConverter.printBase64Binary(rawHmac)
                            .replace("\n", "");
  }
  
  public static String encrypt(String Data,
                               String secret) throws Exception {
    Key key = generateKey(secret);
    Cipher c = Cipher.getInstance("AES");
    c.init(Cipher.ENCRYPT_MODE, key);
    byte[ ] encVal = c.doFinal(Data.getBytes( ));
    String encryptedValue = Base64.getEncoder( )
                                  .encodeToString(encVal);
    return encryptedValue;
  }
  
  public static String decrypt(String strToDecrypt,
                               String secret) throws Exception {
    Key key = generateKey(secret);
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, key);
    try {
      return new String(
        cipher.doFinal(Base64.getDecoder( )
                             .decode(strToDecrypt.getBytes(StandardCharsets.UTF_8))));
    }
    catch(Exception e) {
      System.out.println("Error while decrypting: " + e.toString( ));
    }
    return null;
  }
  
  private static Key generateKey(String secret) throws Exception {
    byte[ ] decoded = Base64.getDecoder( )
                            .decode(secret.getBytes( ));
    Key key = new SecretKeySpec(decoded, "AES");
    return key;
  }
  
  public static String decodeKey(String str) {
    byte[ ] decoded = Base64.getDecoder( )
                            .decode(str.getBytes( ));
    return new String(decoded);
  }
  
  public static String encodeKey(String str) {
    byte[ ] encoded = Base64.getEncoder( )
                            .encode(str.getBytes( ));
    return new String(encoded);
  }
  
  public List<String> conversionStringListString(String data) {
    return new ArrayList<String>(Arrays.asList(data.split(",")));
  }
  
  public List<Long> conversionStringListLong(String data) {
    return Arrays.asList(data.split(","))
                 .stream( )
                 .map(s -> Long.parseLong(s.trim( )))
                 .collect(Collectors.toList( ));
  }
  
  public LiquidacionGeneralQueryDto generarDataLiquidacion(List<?> lista,
                                                           String tipo,
                                                           Long pIdEmpresa) {
    List<LiquidacionQueryDto> vLiquidacionQueryDtoList = new ArrayList<>( );
    LiquidacionGeneralQueryDto vLiquidacionGeneralQueryDto = new LiquidacionGeneralQueryDto( );
    if (lista.size( ) > 0) {
      BigDecimal vComisionPgt = BigDecimal.ZERO;
      BigDecimal vComisionNo = BigDecimal.ZERO;
      BigDecimal vComisionTotal = BigDecimal.ZERO;
      List<Parametrica> vParametricaList = this.parametricaServ.obtenerParametricaDominoSubdominioCodigo(
        Constants.DOM_COMISION, Constants.SUB_EMPRESA, pIdEmpresa.toString( ));
      List<ParametricaQueryDto> vParametricaQueryDtoList = new ArrayList<>( );
      if (vParametricaList.size( ) > 0) {
        for(Parametrica vParametrica: vParametricaList) {
          ParametricaQueryDto vParametricaQueryDto = new ParametricaQueryDto( );
          BeanUtils.copyProperties(vParametrica, vParametricaQueryDto);
          vParametricaQueryDtoList.add(vParametricaQueryDto);
        }
      }
      
      List<ParametricaQueryDto> vComisionList = null;
      
      if (tipo.equals("TJ")) {
        for(Object obj: lista) {
          IReciboDao.AuxilarDao4 detalle = ( IReciboDao.AuxilarDao4 ) obj;
          LiquidacionQueryDto vLiquidacionQueryDto = new LiquidacionQueryDto( );
          vLiquidacionQueryDto.setReferenceNumber(detalle.getReference_number( ));
          vLiquidacionQueryDto.setValorDocumento(detalle.getValor_documento( ));
          vLiquidacionQueryDto.setTipoDocumento(detalle.getTipo_documento( ));
          vLiquidacionQueryDto.setNroPedido(detalle.getNro_pedido( ));
          if (detalle.getMonto_usd( )
                     .compareTo(BigDecimal.ZERO) > 0) vLiquidacionQueryDto.setMontoBs(BigDecimal.ZERO
                                                                                                     .setScale(
                                                                                                       3,
                                                                                                       RoundingMode.HALF_UP));
          else vLiquidacionQueryDto.setMontoBs(detalle.getMonto_bs( )
                                                      .setScale(3, RoundingMode.HALF_UP));
          vLiquidacionQueryDto.setMontoUsd(detalle.getMonto_usd( )
                                                  .setScale(3, RoundingMode.HALF_UP));
          vLiquidacionQueryDto.setEstado(detalle.getEstado( ));
          vLiquidacionQueryDto.setFechaVenta(detalle.getFecha_venta( ));
          vLiquidacionQueryDto.setFechaVencimiento(detalle.getFecha_vencimiento( ));
          vLiquidacionQueryDto.setFechaPago(detalle.getFecha_pago( ));
          vLiquidacionQueryDto.setHoraPago(detalle.getHora_pago( ));
          vLiquidacionQueryDto.setFormaPago(detalle.getForma_pago( ));
          vLiquidacionQueryDto.setGrupoEmpresa(detalle.getGrupo_empresa( ));
          vLiquidacionQueryDto.setEmpresa(detalle.getEmpresa( ));
          vLiquidacionQueryDto.setIdEmpresa(detalle.getIdempresa( ));
          vLiquidacionQueryDto.setPais(detalle.getPais( ));
          vLiquidacionQueryDto.setTarjeta(detalle.getTarjeta( ));
          System.out.println("===============================");
          System.out.println(vLiquidacionQueryDto.toString( ));
          System.out.println("===============================");
          if (vLiquidacionQueryDto.getPais( )
                                  .equals(Constants.P_BOLIVIA)) {
            vComisionList = vParametricaQueryDtoList.stream( )
                                                    .filter(o -> o.getGlosa( )
                                                                  .split("-")[0].equals(
                                                                    Constants.TIPO_TJ_NAC))
                                                    .collect(Collectors.toList( ));
            vComisionPgt = new BigDecimal(
              vComisionList.stream( )
                           .filter(o -> o.getGlosa( )
                                         .equals(Constants.TIPO_TJ_NAC + "-" + Constants.TIPO_COM_PGT))
                           .map(ParametricaQueryDto::getValor)
                           .findFirst( )
                           .orElse("0.00"));
            vComisionNo = new BigDecimal(
              vComisionList.stream( )
                           .filter(o -> o.getGlosa( )
                                         .equals(Constants.TIPO_TJ_NAC + "-" + Constants.TIPO_COM_ATC))
                           .map(ParametricaQueryDto::getValor)
                           .findFirst( )
                           .orElse("0.00"));
            
          }else {
            vComisionList = vParametricaQueryDtoList.stream( )
                                                    .filter(o -> o.getGlosa( )
                                                                  .split("-")[0].equals(
                                                                    Constants.TIPO_TJ_INT))
                                                    .collect(Collectors.toList( ));
            vComisionPgt = new BigDecimal(
              vComisionList.stream( )
                           .filter(o -> o.getGlosa( )
                                         .equals(Constants.TIPO_TJ_INT + "-" + Constants.TIPO_COM_PGT))
                           .map(ParametricaQueryDto::getValor)
                           .findFirst( )
                           .orElse("0.00"));
            vComisionNo = new BigDecimal(
              vComisionList.stream( )
                           .filter(o -> o.getGlosa( )
                                         .equals(Constants.TIPO_TJ_INT + "-" + Constants.TIPO_COM_ATC))
                           .map(ParametricaQueryDto::getValor)
                           .findFirst( )
                           .orElse("0.00"));
          }
          
          vComisionTotal = vComisionPgt.add(vComisionNo);
          if (vLiquidacionQueryDto.getMontoUsd( )
                                  .compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal vDescuentoSus = vLiquidacionQueryDto.getMontoUsd( )
                                                           .multiply(vComisionTotal);
            BigDecimal vSubtotalSus = vLiquidacionQueryDto.getMontoUsd( )
                                                          .subtract(vDescuentoSus);
            BigDecimal vMontoTotalTarjetaUsd = vLiquidacionGeneralQueryDto.getMontoTotalTarjetaUsd( )
                                                                          .add(vLiquidacionQueryDto
                                                                                                   .getMontoUsd( ));
            BigDecimal vTotalComisionTarjetaPtUsd = vLiquidacionGeneralQueryDto
                                                                               .getTotalComisionTarjetaPtUsd( )
                                                                               .add(vLiquidacionQueryDto
                                                                                                        .getMontoUsd( )
                                                                                                        .multiply(
                                                                                                          vComisionPgt));
            BigDecimal vTotalComisionTarjetaAtUsd = vLiquidacionGeneralQueryDto
                                                                               .getTotalComisionTarjetaAtUsd( )
                                                                               .add(vLiquidacionQueryDto
                                                                                                        .getMontoUsd( )
                                                                                                        .multiply(
                                                                                                          vComisionNo));
            vLiquidacionQueryDto.setDescuento(vDescuentoSus.setScale(3, RoundingMode.HALF_UP));
            vLiquidacionQueryDto.setSubtotal(vSubtotalSus.setScale(3, RoundingMode.HALF_UP));
            vLiquidacionGeneralQueryDto.setMontoTotalTarjetaUsd(vMontoTotalTarjetaUsd.setScale(3,
              RoundingMode.HALF_UP));
            vLiquidacionGeneralQueryDto.setTotalComisionTarjetaPtUsd(vTotalComisionTarjetaPtUsd.setScale(3,
              RoundingMode.HALF_UP));
            vLiquidacionGeneralQueryDto.setTotalComisionTarjetaAtUsd(vTotalComisionTarjetaAtUsd.setScale(3,
              RoundingMode.HALF_UP));
          }else {
            BigDecimal vDescuentoBs = vLiquidacionQueryDto.getMontoBs( )
                                                          .multiply(vComisionTotal);
            BigDecimal vSubtotal = vLiquidacionQueryDto.getMontoBs( )
                                                       .subtract(vDescuentoBs);
            BigDecimal vMontoTotalTarjetaBs = vLiquidacionGeneralQueryDto.getMontoTotalTarjetaBs( )
                                                                         .add(vLiquidacionQueryDto
                                                                                                  .getMontoBs( ));
            BigDecimal vTotalComisionTarjetaPtBs = vLiquidacionGeneralQueryDto.getTotalComisionTarjetaPtBs( )
                                                                              .add(vLiquidacionQueryDto
                                                                                                       .getMontoBs( )
                                                                                                       .multiply(
                                                                                                         vComisionPgt));
            BigDecimal vTotalComisionTarjetaAtBs = vLiquidacionGeneralQueryDto.getTotalComisionTarjetaAtBs( )
                                                                              .add(vLiquidacionQueryDto
                                                                                                       .getMontoBs( )
                                                                                                       .multiply(
                                                                                                         vComisionNo));
            vLiquidacionQueryDto.setDescuento(vDescuentoBs.setScale(3, RoundingMode.HALF_UP));
            vLiquidacionQueryDto.setSubtotal(vSubtotal.setScale(3, RoundingMode.HALF_UP));
            vLiquidacionGeneralQueryDto.setMontoTotalTarjetaBs(vMontoTotalTarjetaBs.setScale(3,
              RoundingMode.HALF_UP));
            vLiquidacionGeneralQueryDto.setTotalComisionTarjetaPtBs(vTotalComisionTarjetaPtBs.setScale(3,
              RoundingMode.HALF_UP));
            vLiquidacionGeneralQueryDto.setTotalComisionTarjetaAtBs(vTotalComisionTarjetaAtBs.setScale(3,
              RoundingMode.HALF_UP));
          }
          vLiquidacionQueryDtoList.add(vLiquidacionQueryDto);
        }
      }else if (tipo.equals("QR")) {
        for(Object obj: lista) {
          IReciboDao.AuxilarDao2 detalle = ( IReciboDao.AuxilarDao2 ) obj;
          LiquidacionQueryDto vLiquidacionQueryDto = new LiquidacionQueryDto( );
          vLiquidacionQueryDto.setReferenceNumber(detalle.getReference_number( ));
          vLiquidacionQueryDto.setValorDocumento(detalle.getValor_documento( ));
          vLiquidacionQueryDto.setTipoDocumento(detalle.getTipo_documento( ));
          vLiquidacionQueryDto.setNroPedido(detalle.getNro_pedido( ));
          vLiquidacionQueryDto.setMontoBs(detalle.getMonto_bs( )
                                                 .setScale(3, RoundingMode.HALF_UP));
          vLiquidacionQueryDto.setMontoUsd(detalle.getMonto_usd( )
                                                  .setScale(3, RoundingMode.HALF_UP));
          vLiquidacionQueryDto.setEstado(detalle.getEstado( ));
          vLiquidacionQueryDto.setFechaVenta(detalle.getFecha_venta( ));
          vLiquidacionQueryDto.setFechaVencimiento(detalle.getFecha_vencimiento( ));
          vLiquidacionQueryDto.setFechaPago(detalle.getFecha_pago( ));
          vLiquidacionQueryDto.setHoraPago(detalle.getHora_pago( ));
          vLiquidacionQueryDto.setFormaPago(detalle.getForma_pago( ));
          vLiquidacionQueryDto.setGrupoEmpresa(detalle.getGrupo_empresa( ));
          vLiquidacionQueryDto.setEmpresa(detalle.getEmpresa( ));
          vLiquidacionQueryDto.setIdEmpresa(detalle.getIdempresa( ));
          
          vComisionList = vParametricaQueryDtoList.stream( )
                                                  .filter(o -> o.getGlosa( )
                                                                .split("-")[0].equals(Constants.TIPO_QR))
                                                  .collect(Collectors.toList( ));
          vComisionPgt = new BigDecimal(
            vComisionList.stream( )
                         .filter(o -> o.getGlosa( )
                                       .equals(Constants.TIPO_QR + "-" + Constants.TIPO_COM_PGT))
                         .map(ParametricaQueryDto::getValor)
                         .findFirst( )
                         .orElse("0.00"));
          vComisionNo = new BigDecimal(
            vComisionList.stream( )
                         .filter(o -> o.getGlosa( )
                                       .equals(Constants.TIPO_QR + "-" + Constants.TIPO_COM_BNB))
                         .map(ParametricaQueryDto::getValor)
                         .findFirst( )
                         .orElse("0.00"));
          vComisionTotal = vComisionPgt.add(vComisionNo);
          
          if (vLiquidacionQueryDto.getMontoUsd( )
                                  .compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal vDescuentoSus = vLiquidacionQueryDto.getMontoUsd( )
                                                           .multiply(vComisionTotal);
            BigDecimal vSubTotal = vLiquidacionQueryDto.getMontoUsd( )
                                                       .subtract(vDescuentoSus);
            BigDecimal vMontoTotalQrUsd = vLiquidacionGeneralQueryDto.getMontoTotalQrUsd( )
                                                                     .add(vLiquidacionQueryDto
                                                                                              .getMontoUsd( ));
            BigDecimal vTotalComisionQrPtUsd = vLiquidacionGeneralQueryDto.getTotalComisionQrPtUsd( )
                                                                          .add(vLiquidacionQueryDto
                                                                                                   .getMontoUsd( )
                                                                                                   .multiply(
                                                                                                     vComisionPgt));
            BigDecimal vTotalComisionQrAtUsd = vLiquidacionGeneralQueryDto.getTotalComisionQrAtUsd( )
                                                                          .add(vLiquidacionQueryDto
                                                                                                   .getMontoUsd( )
                                                                                                   .multiply(
                                                                                                     vComisionNo));
            
            vLiquidacionQueryDto.setDescuento(vDescuentoSus.setScale(3, RoundingMode.HALF_UP));
            vLiquidacionQueryDto.setSubtotal(vSubTotal.setScale(3, RoundingMode.HALF_UP));
            vLiquidacionGeneralQueryDto.setMontoTotalQrUsd(vMontoTotalQrUsd.setScale(3,
              RoundingMode.HALF_UP));
            vLiquidacionGeneralQueryDto.setTotalComisionQrPtUsd(vTotalComisionQrPtUsd.setScale(3,
              RoundingMode.HALF_UP));
            vLiquidacionGeneralQueryDto.setTotalComisionQrAtUsd(vTotalComisionQrAtUsd.setScale(3,
              RoundingMode.HALF_UP));
          }else {
            BigDecimal vDescuentoBs = vLiquidacionQueryDto.getMontoBs( )
                                                          .multiply(vComisionTotal);
            BigDecimal vSubtotal = vLiquidacionQueryDto.getMontoBs( )
                                                       .subtract(vDescuentoBs);
            BigDecimal vMontoTotalQrBs = vLiquidacionGeneralQueryDto.getMontoTotalQrBs( )
                                                                    .add(vLiquidacionQueryDto.getMontoBs( ));
            BigDecimal vTotalComisionQrPtBs = vLiquidacionGeneralQueryDto.getTotalComisionQrPtBs( )
                                                                         .add(vLiquidacionQueryDto
                                                                                                  .getMontoBs( )
                                                                                                  .multiply(
                                                                                                    vComisionPgt));
            BigDecimal vTotalComisionQrAtBs = vLiquidacionGeneralQueryDto.getTotalComisionQrAtBs( )
                                                                         .add(vLiquidacionQueryDto
                                                                                                  .getMontoBs( )
                                                                                                  .multiply(
                                                                                                    vComisionNo));
            
            vLiquidacionQueryDto.setDescuento(vDescuentoBs.setScale(3, RoundingMode.HALF_UP));
            vLiquidacionQueryDto.setSubtotal(vSubtotal.setScale(3, RoundingMode.HALF_UP));
            vLiquidacionGeneralQueryDto.setMontoTotalQrBs(vMontoTotalQrBs.setScale(3, RoundingMode.HALF_UP));
            vLiquidacionGeneralQueryDto.setTotalComisionQrPtBs(vTotalComisionQrPtBs.setScale(3,
              RoundingMode.HALF_UP));
            vLiquidacionGeneralQueryDto.setTotalComisionQrAtBs(vTotalComisionQrAtBs.setScale(3,
              RoundingMode.HALF_UP));
          }
          vLiquidacionQueryDtoList.add(vLiquidacionQueryDto);
        }
      }
      BigDecimal vTotalComisionTarjetaBs = vLiquidacionGeneralQueryDto.getTotalComisionTarjetaPtBs( )
                                                                      .add(vLiquidacionGeneralQueryDto
                                                                                                      .getTotalComisionTarjetaAtBs( ));
      BigDecimal vTotalComisionTarjetaUsd = vLiquidacionGeneralQueryDto.getTotalComisionTarjetaPtUsd( )
                                                                       .add(vLiquidacionGeneralQueryDto
                                                                                                       .getTotalComisionTarjetaAtUsd( ));
      BigDecimal vTotalComisionQrBs = vLiquidacionGeneralQueryDto.getTotalComisionQrPtBs( )
                                                                 .add(vLiquidacionGeneralQueryDto
                                                                                                 .getTotalComisionQrAtBs( ));
      BigDecimal vTotalComisionQrUsd = vLiquidacionGeneralQueryDto.getTotalComisionQrPtUsd( )
                                                                  .add(vLiquidacionGeneralQueryDto
                                                                                                  .getTotalComisionQrAtUsd( ));
      BigDecimal vLiquidoPagableTarjetaBs = vLiquidacionGeneralQueryDto.getMontoTotalTarjetaBs( )
                                                                       .subtract(vTotalComisionTarjetaBs);
      BigDecimal vLiquidoPagableTarjetaUsd = vLiquidacionGeneralQueryDto.getMontoTotalTarjetaUsd( )
                                                                        .subtract(vTotalComisionTarjetaUsd);
      BigDecimal vLiquidoPagableQrBs = vLiquidacionGeneralQueryDto.getMontoTotalQrBs( )
                                                                  .subtract(vTotalComisionQrBs);
      BigDecimal vLiquidoPagableQrUsd = vLiquidacionGeneralQueryDto.getMontoTotalQrUsd( )
                                                                   .subtract(vTotalComisionQrUsd);
      
      vLiquidacionGeneralQueryDto.setTotalComisionTarjetaBs(vTotalComisionTarjetaBs.setScale(3,
        RoundingMode.HALF_UP));
      vLiquidacionGeneralQueryDto.setTotalComisionTarjetaUsd(vTotalComisionTarjetaUsd.setScale(3,
        RoundingMode.HALF_UP));
      vLiquidacionGeneralQueryDto.setTotalComisionQrBs(vTotalComisionQrBs.setScale(3, RoundingMode.HALF_UP));
      vLiquidacionGeneralQueryDto.setTotalComisionQrUsd(vTotalComisionQrUsd.setScale(3,
        RoundingMode.HALF_UP));
      vLiquidacionGeneralQueryDto.setLiquidoPagableTarjetaBs(vLiquidoPagableTarjetaBs);
      vLiquidacionGeneralQueryDto.setLiquidoPagableTarjetaSus(vLiquidoPagableTarjetaUsd);
      vLiquidacionGeneralQueryDto.setLiquidoPagableQrBs(vLiquidoPagableQrBs);
      vLiquidacionGeneralQueryDto.setLiquidoPagableQrSus(vLiquidoPagableQrUsd);
      vLiquidacionGeneralQueryDto.setVLiquidacionQueryDtoList(vLiquidacionQueryDtoList);
    }
    return vLiquidacionGeneralQueryDto;
  }
  
  public List<DeudaQueryGeneralDto> generarDataDeuda(List<?> pLista) {
    List<DeudaQueryGeneralDto> vDeudaQueryGeneralDtoList = new ArrayList<>( );
    if (pLista.size( ) > 0) {
      List<DeudaQueryDto> vDeudaQueryDtoListPagado = new ArrayList<>( );
      List<DeudaQueryDto> vDeudaQueryDtoListPendiente = new ArrayList<>( );
      List<DeudaQueryDto> vDeudaQueryDtoListAnulado = new ArrayList<>( );
      
      DeudaQueryGeneralDto vDeudaQueryGeneralDtoPagado = new DeudaQueryGeneralDto( );
      DeudaQueryGeneralDto vDeudaQueryGeneralDtoPendiente = new DeudaQueryGeneralDto( );
      DeudaQueryGeneralDto vDeudaQueryGeneralDtoAnulado = new DeudaQueryGeneralDto( );
      
      for(Object vObjeto: pLista) {
        IDeudaDao.AuxilarDao vDeuda = ( IDeudaDao.AuxilarDao ) vObjeto;
        DeudaQueryDto vDeudaQueryDto = new DeudaQueryDto( );
        BeanUtils.copyProperties(vDeuda, vDeudaQueryDto);
        switch(vDeudaQueryDto.getEstado( )){
          case Constants.STATE_PAGADO:
            vDeudaQueryDtoListPagado.add(vDeudaQueryDto);
            vDeudaQueryGeneralDtoPagado.setVTotalDeuda((vDeudaQueryGeneralDtoPagado.getVTotalDeuda( )
                                                                                   .add(vDeudaQueryDto
                                                                                                      .getMonto( ))).setScale(
                                                                                                        3,
                                                                                                        RoundingMode.HALF_UP));
            vDeudaQueryGeneralDtoPagado.setVTotalPagado((vDeudaQueryGeneralDtoPagado.getVTotalPagado( )
                                                                                    .add(vDeudaQueryDto
                                                                                                       .getMonto( ))).setScale(
                                                                                                         3,
                                                                                                         RoundingMode.HALF_UP));
            break;
          case Constants.STATE_PENDIENTE:
            vDeudaQueryDtoListPendiente.add(vDeudaQueryDto);
            vDeudaQueryGeneralDtoPendiente.setVTotalDeuda((vDeudaQueryGeneralDtoPendiente.getVTotalDeuda( )
                                                                                         .add(vDeudaQueryDto
                                                                                                            .getMonto( ))).setScale(
                                                                                                              3,
                                                                                                              RoundingMode.HALF_UP));
            vDeudaQueryGeneralDtoPendiente.setVTotalPagado((vDeudaQueryGeneralDtoPendiente.getVTotalPagado( )
                                                                                          .add(vDeudaQueryDto
                                                                                                             .getMonto( ))).setScale(
                                                                                                               3,
                                                                                                               RoundingMode.HALF_UP));
            break;
          case Constants.STATE_ANULADO:
            vDeudaQueryDtoListAnulado.add(vDeudaQueryDto);
            vDeudaQueryGeneralDtoAnulado.setVTotalDeuda((vDeudaQueryGeneralDtoAnulado.getVTotalDeuda( )
                                                                                     .add(vDeudaQueryDto
                                                                                                        .getMonto( ))).setScale(
                                                                                                          3,
                                                                                                          RoundingMode.HALF_UP));
            vDeudaQueryGeneralDtoAnulado.setVTotalPagado((vDeudaQueryGeneralDtoAnulado.getVTotalPagado( )
                                                                                      .add(vDeudaQueryDto
                                                                                                         .getMonto( ))).setScale(
                                                                                                           3,
                                                                                                           RoundingMode.HALF_UP));
            break;
        }
      }
      vDeudaQueryGeneralDtoPagado.setVDeudaQueryDtoList(vDeudaQueryDtoListPagado);
      vDeudaQueryGeneralDtoPagado.setVEstadoDeuda(Constants.STATE_PAGADO);
      vDeudaQueryGeneralDtoList.add(vDeudaQueryGeneralDtoPagado);
      vDeudaQueryGeneralDtoPendiente.setVDeudaQueryDtoList(vDeudaQueryDtoListPendiente);
      vDeudaQueryGeneralDtoPendiente.setVEstadoDeuda(Constants.STATE_PENDIENTE);
      vDeudaQueryGeneralDtoList.add(vDeudaQueryGeneralDtoPendiente);
      vDeudaQueryGeneralDtoAnulado.setVDeudaQueryDtoList(vDeudaQueryDtoListAnulado);
      vDeudaQueryGeneralDtoAnulado.setVEstadoDeuda(Constants.STATE_ANULADO);
      vDeudaQueryGeneralDtoList.add(vDeudaQueryGeneralDtoAnulado);
    }
    return vDeudaQueryGeneralDtoList;
  }
  
  public Map<Integer, String[ ]> cargarDatosMap(List<ReporteFacturacion> pReporteFacturacionList) {
    Map<Integer, String[ ]> vFacturas = new HashMap<>( );
    if (pReporteFacturacionList != null) {
      for(int i = 0;i < pReporteFacturacionList.size( );i++) {
        vFacturas.put(i, new String[ ]{pReporteFacturacionList.get(i)
                                                              .getFecha( ),pReporteFacturacionList.get(i)
                                                                                                  .getCodigo_susursal( ),
                                       pReporteFacturacionList.get(i)
                                                              .getMunicipio_susursal( ),
                                       pReporteFacturacionList.get(i)
                                                              .getDescripcion_operacion( ),
                                       pReporteFacturacionList.get(i)
                                                              .getNumero_documento( ),pReporteFacturacionList
                                                                                                             .get(
                                                                                                               i)
                                                                                                             .getRazon_social( ),
                                       pReporteFacturacionList.get(i)
                                                              .getNumero_factura( ),pReporteFacturacionList
                                                                                                           .get(
                                                                                                             i)
                                                                                                           .getMonto_total( ),
                                       pReporteFacturacionList.get(i)
                                                              .getCodigo_estado( ),pReporteFacturacionList
                                                                                                          .get(
                                                                                                            i)
                                                                                                          .getCodigorecepcion( ),
                                       pReporteFacturacionList.get(i)
                                                              .getCuf( ),});
      }
    }
    return vFacturas;
  }
  
  public Map<Integer, Object[ ]> cargarDeudasMap(List<DeudaQueryDto> pDeudaQueryDtoList) {
    Map<Integer, Object[ ]> vDeudas = new HashMap<>( );
    if (pDeudaQueryDtoList.size( ) > 0) {
      for(int i = 0;i < pDeudaQueryDtoList.size( );i++) {
        vDeudas.put(i, new Object[ ]{pDeudaQueryDtoList.get(i)
                                                       .getCliente( ),pDeudaQueryDtoList.get(i)
                                                                                        .getValor_documento( ),
                                     pDeudaQueryDtoList.get(i)
                                                       .getFecha_venta( ),pDeudaQueryDtoList.get(i)
                                                                                            .getFecha_pago( ),
                                     pDeudaQueryDtoList.get(i)
                                                       .getNro_cuota( ),pDeudaQueryDtoList.get(i)
                                                                                          .getMonto( )
                                                                                          .setScale(3,
                                                                                            RoundingMode.HALF_UP),
                                     pDeudaQueryDtoList.get(i)
                                                       .getMoneda( ),pDeudaQueryDtoList.get(i)
                                                                                       .getFecha_vencimiento( ),
                                     pDeudaQueryDtoList.get(i)
                                                       .getMonto( )
                                                       .setScale(3, RoundingMode.HALF_UP),pDeudaQueryDtoList
                                                                                                            .get(
                                                                                                              i)
                                                                                                            .getMetodo_pagado( ),
                                     pDeudaQueryDtoList.get(i)
                                                       .getNumero_telefono( ),pDeudaQueryDtoList.get(i)
                                                                                                .getPais( ),
                                     pDeudaQueryDtoList.get(i)
                                                       .getCiudad( ),pDeudaQueryDtoList.get(i)
                                                                                       .getCorreo( )});
      }
    }
    return vDeudas;
  }
  
  public int cargarTituloPrincipalExcel(XSSFWorkbook pLibro,
                                        XSSFSheet pHoja,
                                        int pInicio,
                                        String pRango) {
    // Titulo
    XSSFRow vRowTitulo = pHoja.createRow(pInicio);
    XSSFCell vCeldaTitulo = vRowTitulo.createCell(0);
    vCeldaTitulo.setCellValue(Constants.NOMBRE_TITULO);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 0, 10));
    vCeldaTitulo.setCellStyle(this.estilosSbl(pLibro, Constants.ST_TITLE));
    pInicio++;
    // Rango
    XSSFRow vRowRango = pHoja.createRow(pInicio);
    XSSFCell vCeldaRango = vRowRango.createCell(0);
    vCeldaRango.setCellValue(pRango);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 0, 10));
    vCeldaRango.setCellStyle(this.estilosSbl(pLibro, Constants.ST_NAME_VALUE));
    pInicio++;
    
    return pInicio;
    
  }
  
  public int cargarPrincipalExcel(XSSFSheet pHoja,
                                  int pInicio,
                                  String pUsuario,
                                  String pFechaHora,
                                  String pComercio) {
    Map<Integer, String[ ]> vPrincipal = new HashMap<>( );
    vPrincipal.put(0, new String[ ]{Constants.USUARIO,pUsuario});
    vPrincipal.put(1, new String[ ]{Constants.FH,pFechaHora});
    vPrincipal.put(2, new String[ ]{Constants.COMERCIO,pComercio});
    
    for(Map.Entry<Integer, String[ ]> entry: vPrincipal.entrySet( )) {
      XSSFRow vRow = pHoja.createRow(pInicio);
      for(int i = 0;i < 2;i++) {
        XSSFCell vCelda = vRow.createCell(i);
        vCelda.setCellValue(entry.getValue( )[i]);
      }
      pInicio++;
    }
    return pInicio;
  }
  
  public int cargarTitulosExcel(XSSFWorkbook pLibro,
                                XSSFSheet pHoja,
                                int pInicio,
                                String pTitulo) {
    XSSFRow vTitulo = pHoja.createRow(pInicio);
    vTitulo.setHeight(( short ) (vTitulo.getHeight( ) * 1.5));
    XSSFCell vCelda = vTitulo.createCell(0);
    vCelda.setCellValue(pTitulo);
    vCelda.setCellStyle(this.estiloPrimario(pLibro));
    pInicio++;
    return pInicio;
  }
  
  public int cargarCabeceraExcel(XSSFWorkbook pLibro,
                                 XSSFSheet pHoja,
                                 int pInicio,
                                 String[ ] pColumnas) {
    XSSFRow vRow = pHoja.createRow(pInicio);
    vRow.setHeight(( short ) (vRow.getHeight( ) * 1.5));
    for(int i = 0;i < pColumnas.length;i++) {
      XSSFCell vCelda = vRow.createCell(i);
      vCelda.setCellValue(pColumnas[i]);
      vCelda.setCellStyle(this.estiloPrimario(pLibro));
    }
    pInicio++;
    return pInicio;
  }
  
  public int cargarDatosExcel(Map<Integer, String[ ]> pFacturas,
                              XSSFWorkbook pLibro,
                              XSSFSheet pHoja,
                              int pInicio,
                              int pColumnas) {
    for(Map.Entry<Integer, String[ ]> entry: pFacturas.entrySet( )) {
      XSSFRow vRow = pHoja.createRow(pInicio);
      for(int i = 0;i < pColumnas;i++) {
        if (i == 1) pHoja.setColumnWidth(i, 6 * 256);
        else pHoja.autoSizeColumn(i);
        XSSFCell vCelda = vRow.createCell(i);
        vCelda.setCellValue(entry.getValue( )[i]);
        vCelda.setCellStyle(this.estiloSecundario(pLibro));
      }
      pInicio++;
    }
    return pInicio;
  }
  
  public int cargarDeudaNombreColumna(XSSFWorkbook pLibro,
                                      XSSFSheet pHoja,
                                      int pInicio,
                                      String[ ] pColumnas) {
    XSSFRow vRow = pHoja.createRow(pInicio);
    vRow.setHeight(( short ) (vRow.getHeight( ) * 1.5));
    for(int i = 0;i < pColumnas.length;i++) {
      XSSFCell vCelda = vRow.createCell(i);
      vCelda.setCellValue(pColumnas[i]);
      vCelda.setCellStyle(this.estiloDeuda(pLibro, Constants.D_ESTILO_NOMBRE_COLUMNA));
    }
    pInicio++;
    return pInicio;
  }
  
  public int cargarDeudaDescripcion(XSSFWorkbook pLibro,
                                    XSSFSheet pHoja,
                                    int pInicio,
                                    String pTitulo) {
    XSSFRow vTitulo = pHoja.createRow(pInicio);
    vTitulo.setHeight(( short ) (vTitulo.getHeight( ) * 1.5));
    XSSFCell vCelda = vTitulo.createCell(0);
    vCelda.setCellValue(pTitulo);
    vCelda.setCellStyle(this.estiloDeuda(pLibro, Constants.D_ESTILO_DESCRIPCION));
    pInicio++;
    return pInicio;
  }
  
  public int cargarDeudaTotal(XSSFWorkbook pLibro,
                              XSSFSheet pHoja,
                              int pInicio,
                              String pTipo,
                              BigDecimal pTotalDeuda,
                              BigDecimal pTotalPagado) {
    XSSFRow vTitulo = pHoja.createRow(pInicio);
    vTitulo.setHeight(( short ) (vTitulo.getHeight( ) * 1.3));
    for(int i = 0;i < 9;i++) {
      XSSFCell vCelda = vTitulo.createCell(i);
      switch(i){
        case 3:
          vCelda.setCellValue("TOTAL " + pTipo + ":");
          pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, i, i + 1));
          vCelda.setCellStyle(this.estiloDeuda(pLibro, Constants.D_ESTILO_TOTAL));
          break;
        case 4:
          vCelda.setCellStyle(this.estiloDeuda(pLibro, Constants.D_ESTILO_TOTAL));
        case 5:
        case 8:
          vCelda.setCellValue(pTotalDeuda == null ? 0.00 : pTotalDeuda.doubleValue( ));
          vCelda.setCellStyle(this.estiloDeuda(pLibro, Constants.D_ESTILO_TOTAL));
          break;
      }
      
    }
    pInicio++;
    return pInicio;
  }
  
  public int cargarDeudaTabla(Map<Integer, Object[ ]> pDeudas,
                              XSSFWorkbook pLibro,
                              XSSFSheet pHoja,
                              int pInicio,
                              int pColumnas) {
    for(Map.Entry<Integer, Object[ ]> entry: pDeudas.entrySet( )) {
      XSSFRow vRow = pHoja.createRow(pInicio);
      for(int i = 0;i < pColumnas;i++) {
        pHoja.autoSizeColumn(i);
        XSSFCell vCelda = vRow.createCell(i);
        switch(i){
          case 2:
          case 3:
          case 7:
            try {
              String vDate = (( Date ) (entry.getValue( )[i])) == null ? "" : this.ConvertDateString(
                (( Date ) (entry.getValue( )[i])), "dd/MM/yyyy HH:mm:ss");
              vCelda.setCellValue(vDate);
            }
            catch(ParseException pe) {
              logger.error(pe.getMessage( ));
            }
            break;
          case 4:
            vCelda.setCellValue(String.valueOf(entry.getValue( )[i]));
            break;
          case 5:
          case 8:
            vCelda.setCellValue((( BigDecimal ) (entry.getValue( )[i])).doubleValue( ));
            break;
          default:
            vCelda.setCellValue(( String ) entry.getValue( )[i]);
            break;
        }
        vCelda.setCellStyle(estiloDeuda(pLibro, 2));
      }
      pInicio++;
    }
    return pInicio;
  }
  
  public int cargarDeudaTitulo(XSSFWorkbook pLibro,
                               XSSFSheet pHoja,
                               int pInicio,
                               String pRango,
                               String pTipo,
                               String pUsuario,
                               String pFechaHora,
                               File pLogo) {
    CellStyle vCellStyle = pLibro.createCellStyle( );
    vCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    vCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    Font vFont = pLibro.createFont( );
    vFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
    vFont.setFontHeight(( short ) 200);
    vCellStyle.setFont(vFont);
    
    this.cargarImagen(pLibro, pHoja, pLogo, pInicio);
    
    XSSFRow vRowTitulo = pHoja.createRow(pInicio);
    XSSFCell vCeldaTitulo = vRowTitulo.createCell(2);
    vCeldaTitulo.setCellValue(Constants.TITULO_DEUDAS);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 2, 7));
    vCeldaTitulo.setCellStyle(vCellStyle);
    
    XSSFCell vCeldaUsuario = vRowTitulo.createCell(10);
    vCeldaUsuario.setCellValue(Constants.USUARIO);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 10, 12));
    vCeldaUsuario.setCellStyle(vCellStyle);
    
    XSSFCell vValorUsuario = vRowTitulo.createCell(13);
    vValorUsuario.setCellValue(pUsuario);
    vValorUsuario.setCellStyle(vCellStyle);
    
    pInicio++;
    
    XSSFRow vRowTipo = pHoja.createRow(pInicio);
    XSSFCell vCeldaTipo = vRowTipo.createCell(2);
    vCeldaTipo.setCellValue(pTipo);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 2, 7));
    vCeldaTipo.setCellStyle(vCellStyle);
    
    XSSFCell vCeldaFecha = vRowTipo.createCell(10);
    vCeldaFecha.setCellValue(Constants.FH);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 10, 12));
    vCeldaFecha.setCellStyle(vCellStyle);
    
    XSSFCell vValorFecha = vRowTipo.createCell(13);
    vValorFecha.setCellValue(pFechaHora);
    vValorFecha.setCellStyle(vCellStyle);
    
    pInicio++;
    
    XSSFRow vRowIntervalo = pHoja.createRow(pInicio);
    XSSFCell vCeldaIntervalo = vRowIntervalo.createCell(2);
    vCeldaIntervalo.setCellValue(pRango);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 2, 7));
    vCeldaIntervalo.setCellStyle(vCellStyle);
    pInicio++;
    return pInicio;
  }
  
  public int cargarDeudaTablaResumen(XSSFWorkbook pLibro,
                                     XSSFSheet pHoja,
                                     int pInicio,
                                     List<DeudaQueryGeneralDto> pDeudaQueryGeneralDtoList) {
    CellStyle vCellStyle = pLibro.createCellStyle( );
    vCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    vCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    Font vFont = pLibro.createFont( );
    vFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
    vFont.setFontName("Sans Serif");
    vFont.setFontHeight(( short ) 220);
    vCellStyle.setFont(vFont);
    
    pInicio = pInicio + 3;
    
    XSSFRow vRowTitulo = pHoja.createRow(pInicio);
    XSSFCell vCeldaTitulo = vRowTitulo.createCell(4);
    vCeldaTitulo.setCellValue("TABLA RESUMEN");
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 4, 9));
    vCeldaTitulo.setCellStyle(vCellStyle);
    pInicio++;
    
    XSSFRow vRowTituloDescripcion = pHoja.createRow(pInicio);
    XSSFCell vCeldaEspacio = vRowTituloDescripcion.createCell(4);
    vCeldaEspacio.setCellValue("");
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 4, 5));
    
    XSSFCell vCeldaCantidad = vRowTituloDescripcion.createCell(6);
    vCeldaCantidad.setCellValue("CANTIDAD");
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 6, 7));
    vCeldaCantidad.setCellStyle(estilosSbl(pLibro, Constants.ST_BK_GREY));
    
    XSSFCell vCeldaMonto = vRowTituloDescripcion.createCell(8);
    vCeldaMonto.setCellValue("MONTO");
    vCeldaMonto.setCellStyle(estilosSbl(pLibro, Constants.ST_BK_GREY));
    pInicio++;
    
    DeudaQueryGeneralDto pPendienteGeneralDto = pDeudaQueryGeneralDtoList.stream( )
                                                                         .filter(o -> o.getVEstadoDeuda( )
                                                                                       .equals(
                                                                                         Constants.STATE_PENDIENTE))
                                                                         .findFirst( )
                                                                         .orElse(null);
    DeudaQueryGeneralDto pAnuladoGeneralDto = pDeudaQueryGeneralDtoList.stream( )
                                                                       .filter(o -> o.getVEstadoDeuda( )
                                                                                     .equals(
                                                                                       Constants.STATE_ANULADO))
                                                                       .findFirst( )
                                                                       .orElse(null);
    DeudaQueryGeneralDto pPagadoGeneralDto = pDeudaQueryGeneralDtoList.stream( )
                                                                      .filter(o -> o.getVEstadoDeuda( )
                                                                                    .equals(
                                                                                      Constants.STATE_PAGADO))
                                                                      .findFirst( )
                                                                      .orElse(null);
    
    XSSFRow vRowPendientes = pHoja.createRow(pInicio);
    
    XSSFCell vPendientesTitulo = vRowPendientes.createCell(4);
    vPendientesTitulo.setCellValue("PENDIENTES");
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 4, 5));
    vPendientesTitulo.setCellStyle(estilosSbl(pLibro, Constants.ST_BK_GREY));
    
    XSSFCell vPendientesCantidad = vRowPendientes.createCell(6);
    int vCantidadPendientes = pPendienteGeneralDto == null ? 0 : pPendienteGeneralDto.getVDeudaQueryDtoList( )
                                                                                     .size( );
    vPendientesCantidad.setCellValue(vCantidadPendientes);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 6, 7));
    vPendientesCantidad.setCellStyle(estilosSbl(pLibro, Constants.ST_NUMERIC));
    
    XSSFCell vPendientesMonto = vRowPendientes.createCell(8);
    vPendientesMonto.setCellValue(pPendienteGeneralDto == null ? BigDecimal.ZERO.setScale(3,
      RoundingMode.HALF_UP)
                                                                                .doubleValue( ) :
      pPendienteGeneralDto.getVTotalDeuda( )
                          .setScale(3, RoundingMode.HALF_UP)
                          .doubleValue( ));
    vPendientesMonto.setCellStyle(estilosSbl(pLibro, Constants.ST_NUMERIC));
    pInicio++;
    
    XSSFRow vRowPagados = pHoja.createRow(pInicio);
    
    XSSFCell vPagadosTitulo = vRowPagados.createCell(4);
    vPagadosTitulo.setCellValue("PAGADOS");
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 4, 5));
    vPagadosTitulo.setCellStyle(estilosSbl(pLibro, Constants.ST_BK_GREY));
    
    XSSFCell vPagadosCantidad = vRowPagados.createCell(6);
    int vCantidadPagados = pPagadoGeneralDto == null ? 0 : pPagadoGeneralDto.getVDeudaQueryDtoList( )
                                                                            .size( );
    vPagadosCantidad.setCellValue(vCantidadPagados);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 6, 7));
    vPagadosCantidad.setCellStyle(estilosSbl(pLibro, Constants.ST_NUMERIC));
    
    XSSFCell vPagadosMonto = vRowPagados.createCell(8);
    vPagadosMonto.setCellValue(pPagadoGeneralDto == null ? BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP)
                                                                          .doubleValue( ) : pPagadoGeneralDto
                                                                                                             .getVTotalDeuda( )
                                                                                                             .setScale(
                                                                                                               3,
                                                                                                               RoundingMode.HALF_UP)
                                                                                                             .doubleValue( ));
    vPagadosMonto.setCellStyle(estilosSbl(pLibro, Constants.ST_NUMERIC));
    pInicio++;
    
    XSSFRow vRowAnulados = pHoja.createRow(pInicio);
    
    XSSFCell vAnuladoTitulo = vRowAnulados.createCell(4);
    vAnuladoTitulo.setCellValue("ANULADOS");
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 4, 5));
    vAnuladoTitulo.setCellStyle(estilosSbl(pLibro, Constants.ST_BK_GREY));
    
    XSSFCell vAnuladosCantidad = vRowAnulados.createCell(6);
    int vCantidadAnulados = pAnuladoGeneralDto == null ? 0 : pAnuladoGeneralDto.getVDeudaQueryDtoList( )
                                                                               .size( );
    vAnuladosCantidad.setCellValue(vCantidadAnulados);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 6, 7));
    vAnuladosCantidad.setCellStyle(estilosSbl(pLibro, Constants.ST_NUMERIC));
    
    XSSFCell vAnuladosMonto = vRowAnulados.createCell(8);
    vAnuladosMonto.setCellValue(pAnuladoGeneralDto == null ? BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP)
                                                                            .doubleValue( ) :
      pAnuladoGeneralDto.getVTotalDeuda( )
                        .setScale(3, RoundingMode.HALF_UP)
                        .doubleValue( ));
    vAnuladosMonto.setCellStyle(estilosSbl(pLibro, Constants.ST_NUMERIC));
    pInicio++;
    
    XSSFRow vRowTotales = pHoja.createRow(pInicio);
    XSSFCell vTotalesTitulo = vRowTotales.createCell(4);
    vTotalesTitulo.setCellValue("TOTALES");
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 4, 5));
    vTotalesTitulo.setCellStyle(estilosSbl(pLibro, Constants.ST_BK_GREY));
    
    XSSFCell vTotales = vRowTotales.createCell(6);
    vTotales.setCellValue(vCantidadAnulados + vCantidadPagados + vCantidadPendientes);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 6, 7));
    vTotales.setCellStyle(estilosSbl(pLibro, Constants.ST_NUMERIC));
    
    pInicio++;
    
    return pInicio;
  }
  
  public CellStyle estiloPrimario(XSSFWorkbook pLibro) {
    CellStyle vCellStyle = pLibro.createCellStyle( );
    vCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
    vCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
    vCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
    vCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
    vCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
    vCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
    vCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    vCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    vCellStyle.setWrapText(true);
    Font vFont = pLibro.createFont( );
    vFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
    vFont.setFontHeight(( short ) 180);
    vCellStyle.setFont(vFont);
    return vCellStyle;
  }
  
  public CellStyle estiloDeuda(XSSFWorkbook pLibro,
                               int pTipo) {
    CellStyle vCellStyle = pLibro.createCellStyle( );
    vCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    Font vFont = pLibro.createFont( );
    vFont.setFontName("Sans Serif");
    vCellStyle.setFont(vFont);
    switch(pTipo){
      case Constants.D_ESTILO_NOMBRE_COLUMNA:
        vCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        vCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        vCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        vFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        vFont.setFontHeight(( short ) 140);
        break;
      case Constants.D_ESTILO_VALOR_COLUMNA:
        vFont.setFontHeight(( short ) 120);
        break;
      case Constants.D_ESTILO_DESCRIPCION:
        vCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        vCellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
        vFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        vFont.setFontHeight(( short ) 150);
        break;
      case Constants.D_ESTILO_TOTAL:
        vCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        vCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        vCellStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
        vCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        vFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        vFont.setFontHeight(( short ) 130);
        break;
    }
    return vCellStyle;
  }
  
  public CellStyle estiloSecundario(XSSFWorkbook pLibro) {
    CellStyle vCellStyle = pLibro.createCellStyle( );
    vCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
    vCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
    vCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
    vCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
    vCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    vCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    Font vFont = pLibro.createFont( );
    vFont.setFontHeight(( short ) 130);
    vCellStyle.setFont(vFont);;
    return vCellStyle;
  }
  
  public int cargarImagen(XSSFWorkbook pLibro,
                          XSSFSheet pHoja,
                          File pLogo,
                          int pInicio) {
    try {
      InputStream my_banner_image = new FileInputStream(pLogo.getAbsolutePath( ));
      byte[ ] bytes = IOUtils.toByteArray(my_banner_image);
      int my_picture_id = pLibro.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
      my_banner_image.close( );
      XSSFDrawing drawing = pHoja.createDrawingPatriarch( );
      XSSFClientAnchor my_anchor = new XSSFClientAnchor( );
      my_anchor.setCol1(0); // Column B
      my_anchor.setRow1(0); // Row 3
      my_anchor.setCol2(2);
      my_anchor.setRow2(4);
      XSSFPicture my_picture = drawing.createPicture(my_anchor, my_picture_id);
      my_picture.resize(0.25);
    }
    catch(Exception e) {
      logger.error(e.getMessage( ));
    }
    return pInicio++;
  }
  
  public CellStyle estilosSbl(XSSFWorkbook pLibro,
                              int pTipo) {
    CellStyle vCellStyle = pLibro.createCellStyle( );
    vCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    Font vFont = pLibro.createFont( );
    vFont.setFontName("Sans Serif");
    switch(pTipo){
      case Constants.ST_BORDER_TOP:
        vCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        vCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        vFont.setFontHeight(( short ) 150);
        break;
      case Constants.ST_BORDER_RIGHT:
        vCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        vCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        vFont.setFontHeight(( short ) 150);
        break;
      case Constants.ST_BORDER_BOTTOM:
        vCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        vCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        vFont.setFontHeight(( short ) 150);
        break;
      case Constants.ST_BORDER_LEFT:
        vCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        vCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        vFont.setFontHeight(( short ) 150);
        break;
      case Constants.ST_BORDER_ALL:
        vCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        vCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        vCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        vCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        vCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        vFont.setFontHeight(( short ) 150);
        break;
      case Constants.ST_BK_GREY:
        vCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        vCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        vCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        vFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        vFont.setFontHeight(( short ) 180);
        break;
      case Constants.ST_TITLE:
        vFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        vFont.setFontHeight(( short ) 300);
        vCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        break;
      case Constants.ST_NAME:
        vFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        vFont.setFontHeight(( short ) 200);
        vCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        break;
      case Constants.ST_NAME_VALUE:
        vFont.setFontHeight(( short ) 200);
        vCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        break;
      case Constants.ST_CELL_VALUE:
        vFont.setFontHeight(( short ) 170);
        vCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        break;
      case Constants.ST_NUMERIC:
        vFont.setFontHeight(( short ) 170);
        vCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        break;
      case Constants.ST_TOTAL:
        vCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        vCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        vFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        vFont.setFontHeight(( short ) 170);
        vCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        break;
      default:
        vCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        break;
    }
    vCellStyle.setFont(vFont);
    return vCellStyle;
  }
  
  public Map<Integer, Object[ ]> cargarFacturasMap(List<FacturaSBL> pFacturaSBL) {
    Map<Integer, Object[ ]> vFacturas = new HashMap<>( );
    if (pFacturaSBL.size( ) > 0) {
      int count = 0;
      for(int i = 0;i < pFacturaSBL.size( );i++) {
        FacturaSBL FSLB = pFacturaSBL.get(i);
        for(int j = 0;j < FSLB.getDetalleFacturas( )
                              .size( );j++) {
          String[ ] vDescripcion = FSLB.getDetalleFacturas( )
                                       .get(j)
                                       .getDescripcion( )
                                       .split("##");
          vFacturas.put(count, new Object[ ]{vDescripcion.length > 1 ? vDescripcion[1] : "-",FSLB.getPozo( ),
                                             FSLB.getSegmento( ),FSLB.getNombreRazonSocial( ),FSLB
                                                                                                  .getFechaEmision( ),
                                             FSLB.getNumeroFactura( ),FSLB.getMontoTotalMoneda( ),FSLB
                                                                                                      .getTipoCambio( ),
                                             FSLB.getMontoTotal( ),FSLB.getCuf( ),FSLB.getContrato( ),
                                             vDescripcion.length > 0 ? vDescripcion[0] : "-",FSLB
                                                                                                 .getCodigoDocumentoSector( )});
          count++;
        }
      }
    }
    return vFacturas;
  }
  
  public int cargarSblCabecera(XSSFWorkbook pLibro,
                               XSSFSheet pHoja,
                               int pInicio,
                               String pRango,
                               String pFecha,
                               String pUsuario,
                               String pNit,
                               String pComercio) {
    // Titulo
    XSSFRow vRowTitulo = pHoja.createRow(pInicio);
    XSSFCell vCeldaTitulo = vRowTitulo.createCell(6);
    vCeldaTitulo.setCellValue(Constants.SBLT_TITULO_REPORTE);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 6, 9));
    vCeldaTitulo.setCellStyle(this.estilosSbl(pLibro, Constants.ST_TITLE));
    pInicio++;
    
    // Rango
    XSSFRow vRowRango = pHoja.createRow(pInicio);
    XSSFCell vCeldaRango = vRowRango.createCell(6);
    vCeldaRango.setCellValue(pRango);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 6, 9));
    vCeldaRango.setCellStyle(this.estilosSbl(pLibro, Constants.ST_NAME_VALUE));
    pInicio++;
    
    // Comercio
    XSSFRow vRowComercio = pHoja.createRow(pInicio);
    XSSFCell vCeldaComercio = vRowComercio.createCell(0);
    vCeldaComercio.setCellValue(Constants.COMERCIO);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 0, 1));
    vCeldaComercio.setCellStyle(this.estilosSbl(pLibro, Constants.ST_NAME));
    
    XSSFCell vCeldaComercioValor = vRowComercio.createCell(2);
    vCeldaComercioValor.setCellValue(pComercio);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 2, 3));
    vCeldaComercioValor.setCellStyle(this.estilosSbl(pLibro, Constants.ST_NAME_VALUE));
    pInicio++;
    
    // Nit
    XSSFRow vRowNit = pHoja.createRow(pInicio);
    XSSFCell vCeldaNit = vRowNit.createCell(0);
    vCeldaNit.setCellValue(Constants.NIT);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 0, 1));
    vCeldaNit.setCellStyle(this.estilosSbl(pLibro, Constants.ST_NAME));
    
    XSSFCell vCeldaNitValor = vRowNit.createCell(2);
    vCeldaNitValor.setCellValue(pNit);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 2, 3));
    vCeldaNitValor.setCellStyle(this.estilosSbl(pLibro, Constants.ST_NAME_VALUE));
    pInicio++;
    
    // Usuario
    XSSFRow vRowUsuario = pHoja.createRow(pInicio);
    XSSFCell vCeldaUsuario = vRowUsuario.createCell(0);
    vCeldaUsuario.setCellValue(Constants.USUARIO);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 0, 1));
    vCeldaUsuario.setCellStyle(this.estilosSbl(pLibro, Constants.ST_NAME));
    
    XSSFCell vCeldaUsuarioValor = vRowUsuario.createCell(2);
    vCeldaUsuarioValor.setCellValue(pUsuario);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 2, 3));
    vCeldaUsuarioValor.setCellStyle(this.estilosSbl(pLibro, Constants.ST_NAME_VALUE));
    pInicio++;
    
    // Fecha
    XSSFRow vRowFecha = pHoja.createRow(pInicio);
    XSSFCell vCeldaFecha = vRowFecha.createCell(0);
    vCeldaFecha.setCellValue(Constants.FH);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 0, 1));
    vCeldaFecha.setCellStyle(this.estilosSbl(pLibro, Constants.ST_NAME));
    
    XSSFCell vCeldaFechaValor = vRowFecha.createCell(2);
    vCeldaFechaValor.setCellValue(pFecha);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 2, 3));
    vCeldaFechaValor.setCellStyle(this.estilosSbl(pLibro, Constants.ST_NAME_VALUE));
    pInicio++;
    return pInicio;
  }
  
  public int cargarSblColumnas(XSSFWorkbook pLibro,
                               XSSFSheet pHoja,
                               int pInicio,
                               String[ ] pColumnas) {
    XSSFRow vRow = pHoja.createRow(pInicio);
    vRow.setHeight(( short ) (vRow.getHeight( ) * 1.5));
    for(int i = 0;i < pColumnas.length;i++) {
      XSSFCell vCelda = vRow.createCell(i);
      vCelda.setCellValue(pColumnas[i]);
      vCelda.setCellStyle(this.estilosSbl(pLibro, Constants.ST_BK_GREY));
    }
    pInicio++;
    return pInicio;
  }
  
  public int cargarSblValores(Map<Integer, Object[ ]> pFacturas,
                              XSSFWorkbook pLibro,
                              XSSFSheet pHoja,
                              int pInicio,
                              int pColumnas) {
    for(Map.Entry<Integer, Object[ ]> entry: pFacturas.entrySet( )) {
      XSSFRow vRow = pHoja.createRow(pInicio);
      for(int i = 0;i < pColumnas;i++) {
        pHoja.autoSizeColumn(i);
        XSSFCell vCelda = vRow.createCell(i);
        switch(i){
          case 4:
            Timestamp vFecha = ( Timestamp ) entry.getValue( )[i];
            try {
              String vDate = vFecha == null ? "" : this.ConvertDateString(new Date(vFecha.getTime( )),
                "dd/MM/yyyy HH:mm:ss");
              vCelda.setCellValue(vDate);
            }
            catch(ParseException pe) {
              logger.error(pe.getMessage( ));
            }
            vCelda.setCellStyle(this.estilosSbl(pLibro, Constants.ST_CELL_VALUE));
            break;
          case 5:
            Integer vNumeroFactura = ( Integer ) entry.getValue( )[i];
            vCelda.setCellValue(vNumeroFactura == null ? "-" : String.valueOf(vNumeroFactura));
            vCelda.setCellStyle(this.estilosSbl(pLibro, Constants.ST_CELL_VALUE));
            break;
          case 12:
            Integer vTipoDocumentoSector = ( Integer ) entry.getValue( )[i];
            vCelda.setCellValue(vTipoDocumentoSector == null ? "-" : String.valueOf(DocumentoSector
                                                                                                   .getEnumFromCode(
                                                                                                     vTipoDocumentoSector)
                                                                                                   .getDescripcion( )));
            vCelda.setCellStyle(this.estilosSbl(pLibro, Constants.ST_CELL_VALUE));
            break;
          case 6:
          case 7:
          case 8:
            BigDecimal vValor = ( BigDecimal ) entry.getValue( )[i];
            vCelda.setCellValue(vValor == null ? 0.00 : vValor.doubleValue( ));
            vCelda.setCellStyle(this.estilosSbl(pLibro, Constants.ST_NUMERIC));
            break;
          default:
            String vData = ( String ) entry.getValue( )[i];
            vCelda.setCellValue(vData == null ? "-" : vData);
            vCelda.setCellStyle(this.estilosSbl(pLibro, Constants.ST_CELL_VALUE));
            break;
        }
      }
      pInicio++;
    }
    return pInicio;
  }
  
  public int cargarSblTotales(XSSFWorkbook pLibro,
                              XSSFSheet pHoja,
                              int pInicio,
                              int pTotalRegistros,
                              double pTotalUsd,
                              double pTotalBs) {
    XSSFRow vRowTotal = pHoja.createRow(pInicio);
    vRowTotal.setHeight(( short ) (vRowTotal.getHeight( ) * 1.8));
    XSSFCell vCeldaEspacio = vRowTotal.createCell(4);
    vCeldaEspacio.setCellValue(Constants.SBLT_TOTAL);
    pHoja.addMergedRegion(new CellRangeAddress(pInicio, pInicio, 4, 5));
    CellStyle vCellStyle = this.estilosSbl(pLibro, Constants.ST_TOTAL);
    vCeldaEspacio.setCellStyle(vCellStyle);
    
    XSSFCell vCeldaImporteUsd = vRowTotal.createCell(6);
    vCeldaImporteUsd.setCellValue(pTotalUsd);
    CellStyle vStyleUsd = this.estilosSbl(pLibro, Constants.ST_TOTAL);
    vCeldaImporteUsd.setCellStyle(vStyleUsd);
    
    XSSFCell vCeldaImporteBs = vRowTotal.createCell(8);
    vCeldaImporteBs.setCellValue(pTotalBs);
    CellStyle vStyleBs = this.estilosSbl(pLibro, Constants.ST_TOTAL);
    vCeldaImporteBs.setCellStyle(vStyleBs);
    
    pInicio++;
    return pInicio;
  }
  
  public String crearQRCode(String data,
                            int width,
                            int height) throws QrException {
    String resultImage;
    try {
      if (!data.isEmpty( )) {
        ByteArrayOutputStream os = new ByteArrayOutputStream( );
        @SuppressWarnings("rawtypes")
        HashMap<EncodeHintType, Comparable> hints = new HashMap<>( );
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        
        QRCodeWriter writer = new QRCodeWriter( );
        BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height, hints);
        
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ImageIO.write(bufferedImage, "png", os);
        
        resultImage = new String(
          Base64.getEncoder( )
                .encode(os.toByteArray( )));
        return resultImage;
      }else {
        throw new OperationException("data can not be empty");
      }
    }
    catch(IOException | WriterException e) {
      throw new QrException(e.getMessage( ));
    }
    catch(OperationException e) {
      throw new QrException(e.getMessage( ));
    }
    catch(Exception e) {
      throw new QrException(e.getMessage( ));
    }
  }
  
  public LiquidacionGeneralQueryDto generarLiquidacion(List<?> lista,
                                                       String tipo,
                                                       Long pIdEmpresa,
                                                       Comision comision) {
    List<LiquidacionQueryDto> vLiquidacionQueryDtoList = new ArrayList<>( );
    LiquidacionGeneralQueryDto vLiquidacionGeneralQueryDto = new LiquidacionGeneralQueryDto( );
    BigDecimal vComisionPgt = BigDecimal.ZERO;
    BigDecimal vComisionNo = BigDecimal.ZERO;
    BigDecimal vComisionTotal = BigDecimal.ZERO;
    if (tipo.equals("TJ")) {
      for(Object obj: lista) {
        IReciboDao.AuxilarDao4 detalle = ( IReciboDao.AuxilarDao4 ) obj;
        LiquidacionQueryDto vLiquidacionQueryDto = new LiquidacionQueryDto( );
        vLiquidacionQueryDto.setReferenceNumber(detalle.getReference_number( ));
        vLiquidacionQueryDto.setValorDocumento(detalle.getValor_documento( ));
        vLiquidacionQueryDto.setTipoDocumento(detalle.getTipo_documento( ));
        vLiquidacionQueryDto.setNroPedido(detalle.getNro_pedido( ));
        if (detalle.getMonto_usd( )
                   .compareTo(BigDecimal.ZERO) > 0) vLiquidacionQueryDto.setMontoBs(BigDecimal.ZERO.setScale(
                     3, RoundingMode.HALF_UP));
        else vLiquidacionQueryDto.setMontoBs(detalle.getMonto_bs( )
                                                    .setScale(3, RoundingMode.HALF_UP));
        vLiquidacionQueryDto.setMontoUsd(detalle.getMonto_usd( )
                                                .setScale(3, RoundingMode.HALF_UP));
        vLiquidacionQueryDto.setEstado(detalle.getEstado( ));
        vLiquidacionQueryDto.setFechaVenta(detalle.getFecha_venta( ));
        vLiquidacionQueryDto.setFechaVencimiento(detalle.getFecha_vencimiento( ));
        vLiquidacionQueryDto.setFechaPago(detalle.getFecha_pago( ));
        vLiquidacionQueryDto.setHoraPago(detalle.getHora_pago( ));
        vLiquidacionQueryDto.setFormaPago(detalle.getForma_pago( ));
        vLiquidacionQueryDto.setGrupoEmpresa(detalle.getGrupo_empresa( ));
        vLiquidacionQueryDto.setEmpresa(detalle.getEmpresa( ));
        vLiquidacionQueryDto.setIdEmpresa(detalle.getIdempresa( ));
        vLiquidacionQueryDto.setPais(detalle.getPais( ));
        vLiquidacionQueryDto.setTarjeta(detalle.getTarjeta( ));
        if (vLiquidacionQueryDto.getPais( )
                                .equals(Constants.P_BOLIVIA)) {
          vComisionPgt = comision.getTarjetaNacPgt( );
          vComisionNo = comision.getTarjetaNacAtc( );
        }else {
          vComisionPgt = comision.getTarjetaInterPgt( );
          vComisionNo = comision.getTarjetaInterAtc( );
        }
        vComisionTotal = vComisionPgt.add(vComisionNo);
        if (vLiquidacionQueryDto.getMontoUsd( )
                                .compareTo(BigDecimal.ZERO) > 0) {
          BigDecimal vDescuentoSus = vLiquidacionQueryDto.getMontoUsd( )
                                                         .multiply(vComisionTotal);
          BigDecimal vSubtotalSus = vLiquidacionQueryDto.getMontoUsd( )
                                                        .subtract(vDescuentoSus);
          BigDecimal vMontoTotalTarjetaUsd = vLiquidacionGeneralQueryDto.getMontoTotalTarjetaUsd( )
                                                                        .add(vLiquidacionQueryDto
                                                                                                 .getMontoUsd( ));
          BigDecimal vTotalComisionTarjetaPtUsd = vLiquidacionGeneralQueryDto.getTotalComisionTarjetaPtUsd( )
                                                                             .add(vLiquidacionQueryDto
                                                                                                      .getMontoUsd( )
                                                                                                      .multiply(
                                                                                                        vComisionPgt));
          BigDecimal vTotalComisionTarjetaAtUsd = vLiquidacionGeneralQueryDto.getTotalComisionTarjetaAtUsd( )
                                                                             .add(vLiquidacionQueryDto
                                                                                                      .getMontoUsd( )
                                                                                                      .multiply(
                                                                                                        vComisionNo));
          vLiquidacionQueryDto.setDescuento(vDescuentoSus.setScale(3, RoundingMode.HALF_UP));
          vLiquidacionQueryDto.setSubtotal(vSubtotalSus.setScale(3, RoundingMode.HALF_UP));
          vLiquidacionGeneralQueryDto.setMontoTotalTarjetaUsd(vMontoTotalTarjetaUsd.setScale(3,
            RoundingMode.HALF_UP));
          vLiquidacionGeneralQueryDto.setTotalComisionTarjetaPtUsd(vTotalComisionTarjetaPtUsd.setScale(3,
            RoundingMode.HALF_UP));
          vLiquidacionGeneralQueryDto.setTotalComisionTarjetaAtUsd(vTotalComisionTarjetaAtUsd.setScale(3,
            RoundingMode.HALF_UP));
        }else {
          BigDecimal vDescuentoBs = vLiquidacionQueryDto.getMontoBs( )
                                                        .multiply(vComisionTotal);
          BigDecimal vSubtotal = vLiquidacionQueryDto.getMontoBs( )
                                                     .subtract(vDescuentoBs);
          BigDecimal vMontoTotalTarjetaBs = vLiquidacionGeneralQueryDto.getMontoTotalTarjetaBs( )
                                                                       .add(vLiquidacionQueryDto
                                                                                                .getMontoBs( ));
          BigDecimal vTotalComisionTarjetaPtBs = vLiquidacionGeneralQueryDto.getTotalComisionTarjetaPtBs( )
                                                                            .add(vLiquidacionQueryDto
                                                                                                     .getMontoBs( )
                                                                                                     .multiply(
                                                                                                       vComisionPgt));
          BigDecimal vTotalComisionTarjetaAtBs = vLiquidacionGeneralQueryDto.getTotalComisionTarjetaAtBs( )
                                                                            .add(vLiquidacionQueryDto
                                                                                                     .getMontoBs( )
                                                                                                     .multiply(
                                                                                                       vComisionNo));
          vLiquidacionQueryDto.setDescuento(vDescuentoBs.setScale(3, RoundingMode.HALF_UP));
          vLiquidacionQueryDto.setSubtotal(vSubtotal.setScale(3, RoundingMode.HALF_UP));
          vLiquidacionGeneralQueryDto.setMontoTotalTarjetaBs(vMontoTotalTarjetaBs.setScale(3,
            RoundingMode.HALF_UP));
          vLiquidacionGeneralQueryDto.setTotalComisionTarjetaPtBs(vTotalComisionTarjetaPtBs.setScale(3,
            RoundingMode.HALF_UP));
          vLiquidacionGeneralQueryDto.setTotalComisionTarjetaAtBs(vTotalComisionTarjetaAtBs.setScale(3,
            RoundingMode.HALF_UP));
        }
        vLiquidacionQueryDtoList.add(vLiquidacionQueryDto);
      }
    }else if (tipo.equals("QR")) {
      for(Object obj: lista) {
        IReciboDao.AuxilarDao2 detalle = ( IReciboDao.AuxilarDao2 ) obj;
        LiquidacionQueryDto vLiquidacionQueryDto = new LiquidacionQueryDto( );
        vLiquidacionQueryDto.setReferenceNumber(detalle.getReference_number( ));
        vLiquidacionQueryDto.setValorDocumento(detalle.getValor_documento( ));
        vLiquidacionQueryDto.setTipoDocumento(detalle.getTipo_documento( ));
        vLiquidacionQueryDto.setNroPedido(detalle.getNro_pedido( ));
        vLiquidacionQueryDto.setMontoBs(detalle.getMonto_bs( )
                                               .setScale(3, RoundingMode.HALF_UP));
        vLiquidacionQueryDto.setMontoUsd(detalle.getMonto_usd( )
                                                .setScale(3, RoundingMode.HALF_UP));
        vLiquidacionQueryDto.setEstado(detalle.getEstado( ));
        vLiquidacionQueryDto.setFechaVenta(detalle.getFecha_venta( ));
        vLiquidacionQueryDto.setFechaVencimiento(detalle.getFecha_vencimiento( ));
        vLiquidacionQueryDto.setFechaPago(detalle.getFecha_pago( ));
        vLiquidacionQueryDto.setHoraPago(detalle.getHora_pago( ));
        vLiquidacionQueryDto.setFormaPago(detalle.getForma_pago( ));
        vLiquidacionQueryDto.setGrupoEmpresa(detalle.getGrupo_empresa( ));
        vLiquidacionQueryDto.setEmpresa(detalle.getEmpresa( ));
        vLiquidacionQueryDto.setIdEmpresa(detalle.getIdempresa( ));
        vComisionPgt = comision.getQrPgt( );
        vComisionNo = comision.getQrBanco( );
        vComisionTotal = vComisionPgt.add(vComisionNo);
        if (vLiquidacionQueryDto.getMontoUsd( )
                                .compareTo(BigDecimal.ZERO) > 0) {
          BigDecimal vDescuentoSus = vLiquidacionQueryDto.getMontoUsd( )
                                                         .multiply(vComisionTotal);
          BigDecimal vSubTotal = vLiquidacionQueryDto.getMontoUsd( )
                                                     .subtract(vDescuentoSus);
          BigDecimal vMontoTotalQrUsd = vLiquidacionGeneralQueryDto.getMontoTotalQrUsd( )
                                                                   .add(vLiquidacionQueryDto.getMontoUsd( ));
          BigDecimal vTotalComisionQrPtUsd = vLiquidacionGeneralQueryDto.getTotalComisionQrPtUsd( )
                                                                        .add(vLiquidacionQueryDto
                                                                                                 .getMontoUsd( )
                                                                                                 .multiply(
                                                                                                   vComisionPgt));
          BigDecimal vTotalComisionQrAtUsd = vLiquidacionGeneralQueryDto.getTotalComisionQrAtUsd( )
                                                                        .add(vLiquidacionQueryDto
                                                                                                 .getMontoUsd( )
                                                                                                 .multiply(
                                                                                                   vComisionNo));
          
          vLiquidacionQueryDto.setDescuento(vDescuentoSus.setScale(3, RoundingMode.HALF_UP));
          vLiquidacionQueryDto.setSubtotal(vSubTotal.setScale(3, RoundingMode.HALF_UP));
          vLiquidacionGeneralQueryDto.setMontoTotalQrUsd(vMontoTotalQrUsd.setScale(3, RoundingMode.HALF_UP));
          vLiquidacionGeneralQueryDto.setTotalComisionQrPtUsd(vTotalComisionQrPtUsd.setScale(3,
            RoundingMode.HALF_UP));
          vLiquidacionGeneralQueryDto.setTotalComisionQrAtUsd(vTotalComisionQrAtUsd.setScale(3,
            RoundingMode.HALF_UP));
        }else {
          BigDecimal vDescuentoBs = vLiquidacionQueryDto.getMontoBs( )
                                                        .multiply(vComisionTotal);
          BigDecimal vSubtotal = vLiquidacionQueryDto.getMontoBs( )
                                                     .subtract(vDescuentoBs);
          BigDecimal vMontoTotalQrBs = vLiquidacionGeneralQueryDto.getMontoTotalQrBs( )
                                                                  .add(vLiquidacionQueryDto.getMontoBs( ));
          BigDecimal vTotalComisionQrPtBs = vLiquidacionGeneralQueryDto.getTotalComisionQrPtBs( )
                                                                       .add(vLiquidacionQueryDto.getMontoBs( )
                                                                                                .multiply(
                                                                                                  vComisionPgt));
          BigDecimal vTotalComisionQrAtBs = vLiquidacionGeneralQueryDto.getTotalComisionQrAtBs( )
                                                                       .add(vLiquidacionQueryDto.getMontoBs( )
                                                                                                .multiply(
                                                                                                  vComisionNo));
          
          vLiquidacionQueryDto.setDescuento(vDescuentoBs.setScale(3, RoundingMode.HALF_UP));
          vLiquidacionQueryDto.setSubtotal(vSubtotal.setScale(3, RoundingMode.HALF_UP));
          vLiquidacionGeneralQueryDto.setMontoTotalQrBs(vMontoTotalQrBs.setScale(3, RoundingMode.HALF_UP));
          vLiquidacionGeneralQueryDto.setTotalComisionQrPtBs(vTotalComisionQrPtBs.setScale(3,
            RoundingMode.HALF_UP));
          vLiquidacionGeneralQueryDto.setTotalComisionQrAtBs(vTotalComisionQrAtBs.setScale(3,
            RoundingMode.HALF_UP));
        }
        vLiquidacionQueryDtoList.add(vLiquidacionQueryDto);
      }
    }
    BigDecimal vTotalComisionTarjetaBs = vLiquidacionGeneralQueryDto.getTotalComisionTarjetaPtBs( )
                                                                    .add(vLiquidacionGeneralQueryDto
                                                                                                    .getTotalComisionTarjetaAtBs( ));
    BigDecimal vTotalComisionTarjetaUsd = vLiquidacionGeneralQueryDto.getTotalComisionTarjetaPtUsd( )
                                                                     .add(vLiquidacionGeneralQueryDto
                                                                                                     .getTotalComisionTarjetaAtUsd( ));
    BigDecimal vTotalComisionQrBs = vLiquidacionGeneralQueryDto.getTotalComisionQrPtBs( )
                                                               .add(vLiquidacionGeneralQueryDto
                                                                                               .getTotalComisionQrAtBs( ));
    BigDecimal vTotalComisionQrUsd = vLiquidacionGeneralQueryDto.getTotalComisionQrPtUsd( )
                                                                .add(vLiquidacionGeneralQueryDto
                                                                                                .getTotalComisionQrAtUsd( ));
    BigDecimal vLiquidoPagableTarjetaBs = vLiquidacionGeneralQueryDto.getMontoTotalTarjetaBs( )
                                                                     .subtract(vTotalComisionTarjetaBs);
    BigDecimal vLiquidoPagableTarjetaUsd = vLiquidacionGeneralQueryDto.getMontoTotalTarjetaUsd( )
                                                                      .subtract(vTotalComisionTarjetaUsd);
    BigDecimal vLiquidoPagableQrBs = vLiquidacionGeneralQueryDto.getMontoTotalQrBs( )
                                                                .subtract(vTotalComisionQrBs);
    BigDecimal vLiquidoPagableQrUsd = vLiquidacionGeneralQueryDto.getMontoTotalQrUsd( )
                                                                 .subtract(vTotalComisionQrUsd);
    
    vLiquidacionGeneralQueryDto.setTotalComisionTarjetaBs(vTotalComisionTarjetaBs.setScale(3,
      RoundingMode.HALF_UP));
    vLiquidacionGeneralQueryDto.setTotalComisionTarjetaUsd(vTotalComisionTarjetaUsd.setScale(3,
      RoundingMode.HALF_UP));
    vLiquidacionGeneralQueryDto.setTotalComisionQrBs(vTotalComisionQrBs.setScale(3, RoundingMode.HALF_UP));
    vLiquidacionGeneralQueryDto.setTotalComisionQrUsd(vTotalComisionQrUsd.setScale(3, RoundingMode.HALF_UP));
    vLiquidacionGeneralQueryDto.setLiquidoPagableTarjetaBs(vLiquidoPagableTarjetaBs);
    vLiquidacionGeneralQueryDto.setLiquidoPagableTarjetaSus(vLiquidoPagableTarjetaUsd);
    vLiquidacionGeneralQueryDto.setLiquidoPagableQrBs(vLiquidoPagableQrBs);
    vLiquidacionGeneralQueryDto.setLiquidoPagableQrSus(vLiquidoPagableQrUsd);
    vLiquidacionGeneralQueryDto.setVLiquidacionQueryDtoList(vLiquidacionQueryDtoList);
    return vLiquidacionGeneralQueryDto;
  }
  
}