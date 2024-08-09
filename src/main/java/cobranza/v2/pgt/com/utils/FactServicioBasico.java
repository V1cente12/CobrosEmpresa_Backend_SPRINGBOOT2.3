
package cobranza.v2.pgt.com.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.entity.FacturaCabecera;
import cobranza.v2.pgt.com.models.entity.FacturaDetalle;

@Service
public class FactServicioBasico {
  
  private Logger logger = LoggerFactory.getLogger(FactServicioBasico.class);
  private int headFB[] = {0,0,0,1,0,1,1,0,1,1,1,1,1,0,1,1,1,0,0,0,0,0,0,0,0,1,0,0};
  private int bodyFB[] = {0,0,1,1,0,0,0,0,0,0};
  
  List<FacturaCabecera> f = new ArrayList<FacturaCabecera>( );
  List<FacturaDetalle> d = new ArrayList<FacturaDetalle>( );
  @Autowired
  public Auxiliar aux;
  
  public List<FacturaCabecera> leerdatoexcelHEAD(MultipartFile file,
                                                 String encabezado) {
    Long inicia = System.currentTimeMillis( );
    aux.errores[0] = 0;
    aux.errores[1] = 0;
    this.f.clear( );
    try {
      Workbook libro = aux.getlibro(file);
      Sheet hoja = libro.getSheetAt(0);
      Iterator<Row> filas = hoja.iterator( );
      int rows = hoja.getLastRowNum( );
      if (encabezado.equals("1")) { filas.next( ); }
      logger.info("Total Filas del archivo(Encabezado): " + rows);
      for(int r = 1;r <= rows;r++) {
        Row fila = filas.next( );
        aux.obs[0] = vacioH(fila, "", r);
        aux.obs[0] = verificarH(fila, headFB, aux.obs[0], r);
        llenar(fila, aux.obs[0]);
      }
    }catch(Exception e) {
      e.printStackTrace( );
    }
    logger.info("Duración " + (System.currentTimeMillis( ) - inicia) + " ms.");
    return this.f;
  }
  
  public String vacioH(Row fila,
                       String x,
                       int y) {
    try {
      for(int c = fila.getFirstCellNum( );c < fila.getLastCellNum( );c++) {
        Cell cell = fila.getCell(c);
        if ((c != 4) && (c != 15)) {
          if (cell == null || cell.getCellType( ) == 3) {
            x += "Vacio: Col-" + aux.columna(c) + "; ";
            aux.errores[0] += 1;
          }
        }
      }
    }catch(Exception e) {
      e.printStackTrace( );
    }
    return x;
  }
  
  public String verificarH(Row fila,
                           int[ ] valor,
                           String x,
                           int y) {
    try {
      for(int c = fila.getFirstCellNum( );c < fila.getLastCellNum( );c++) {
        if ((fila.getCell(c)
                 .getCellType( ) != valor[c])) {
          if ((c == 4) || (c == 15)) {
            if (fila.getCell(c)
                    .getCellType( ) != 3) {
              if ((fila.getCell(c)
                       .getCellType( ) != valor[c])) {
                x += "Error: Col-" + aux.columna(c) + "; ";
                aux.errores[1] += 1;
              }
            }
          }else {
            x += "Error: Col-" + aux.columna(c) + "; ";
            aux.errores[1] += 1;
          }
        }else if ((fila.getCell(c)
                       .getCellType( ) == 1) && (c == 6)) {
                         if (aux.numero(fila.getCell(c)
                                            .getStringCellValue( ))) {
                           x += "Error: Col-" + aux.columna(c) + "; ";
                           aux.errores[1] += 1;
                         }
                       }
      }
    }catch(Exception e) {
      e.printStackTrace( );
    }
    return x;
  }
  
  public void llenar(Row fila,
                     String obs) {
    FacturaCabecera c = new FacturaCabecera( );
    try {
      c.setNumerofactura(aux.cambio(fila.getCell(0)));
      c.setNitemisor(aux.cambio(fila.getCell(1)));
      c.setCodigosucursal(aux.cambio(fila.getCell(2)));
      c.setDireccion(aux.cambio(fila.getCell(3)));
      c.setCodigopuntoventa(aux.cambio(fila.getCell(4)));
      c.setFechaemision(aux.ConversionDate(fila.getCell(5)));
      c.setMes(aux.cambio(fila.getCell(6)));
      c.setGestion(aux.cambio(fila.getCell(7)));
      c.setCiudad(aux.cambio(fila.getCell(8)));
      c.setZona(aux.cambio(fila.getCell(9)));
      c.setNumeromedidor(aux.cambio(fila.getCell(10)));
      c.setDomicilio(aux.cambio(fila.getCell(11)));
      c.setNombrerazonsocial(aux.cambio(fila.getCell(12)));
      c.setCodigotipodocumentoidentidad(aux.cambio(fila.getCell(13)));
      c.setNumerodocumento(aux.cambio(fila.getCell(14)));
      c.setComplemento(aux.cambio(fila.getCell(15)));
      c.setCodigocliente(aux.cambio(fila.getCell(16)));
      c.setMontototal(aux.cambio(fila.getCell(17)));
      c.setMontodescuento(aux.cambio(fila.getCell(18)));
      c.setMontototalsujetoiva(aux.cambio(fila.getCell(19)));
      c.setConsumokwh(aux.cambio(fila.getCell(20)));
      c.setConsumometroscubicos(aux.cambio(fila.getCell(21)));
      c.setMontoDescuentoLey1886(aux.cambio(fila.getCell(22)));
      c.setTasaaseo(aux.cambio(fila.getCell(23)));
      c.setTasaalumbrado(aux.cambio(fila.getCell(24)));
      c.setUsuario(aux.cambio(fila.getCell(25)));
      c.setDescuentosinafectacion(aux.cambio(fila.getCell(26)));
      c.setCodigoExcepcionDocumento(aux.cambio(fila.getCell(27)));
      c.setObservacion(obs);
    }catch(Exception e) {
      e.printStackTrace( );
    }
    this.f.add(c);
  }
  
  public List<FacturaDetalle> leerdatoexcelBODY(MultipartFile file,
                                                String marco) {
    Long inicia = System.currentTimeMillis( );
    aux.errores[2] = 0;
    aux.errores[3] = 0;
    this.d.clear( );
    Workbook libro = aux.getlibro(file);
    Sheet detalle = libro.getSheetAt(1);
    Iterator<Row> filas = detalle.iterator( );
    int rows = detalle.getLastRowNum( );
    if (marco.equals("1")) { filas.next( ); }
    logger.info("Total Filas del archivo(Detalles): " + rows);
    for(int r = 1;r <= rows;r++) {
      Row fila = filas.next( );
      aux.obs[1] = vacioB(fila, "", r);
      aux.obs[1] = verificarB(fila, bodyFB, aux.obs[1], r);
      llenarB(fila, aux.obs[1]);
      aux.obs[1] = "";
    }
    logger.info("Duración " + (System.currentTimeMillis( ) - inicia) + " ms.");
    return this.d;
  }
  
  public String vacioB(Row fila,
                       String x,
                       int y) {
    try {
      for(int c = fila.getFirstCellNum( );c < fila.getLastCellNum( );c++) {
        Cell cell = fila.getCell(c);
        if (cell == null || cell.getCellType( ) == 3) {
          x += "Vacio: Col-" + aux.columna(c) + "; ";
          aux.errores[2] += 1;
        }
      }
    }catch(Exception e) {
      e.printStackTrace( );
    }
    return x;
  }
  
  public String verificarB(Row fila,
                           int[ ] valor,
                           String x,
                           int y) {
    try {
      for(int c = fila.getFirstCellNum( );c < fila.getLastCellNum( );c++) {
        if ((fila.getCell(c)
                 .getCellType( ) != valor[c])) {
          x += "Error: Col-" + aux.columna(c) + "; ";
          aux.errores[3] += 1;
        }
      }
    }catch(Exception e) {
      e.printStackTrace( );
    }
    return x;
  }
  
  public void llenarB(Row fila,
                      String obs) {
    FacturaDetalle d = new FacturaDetalle( );
    try {
      d.setNumerofactura(aux.cambio(fila.getCell(0)));
      d.setCodigoproductosin(aux.cambio(fila.getCell(1)));
      d.setCodigoproducto(aux.cambio(fila.getCell(2)));
      d.setDescripcion(aux.cambio(fila.getCell(3)));
      d.setUnidadmedida(aux.cambio(fila.getCell(4)));
      d.setPreciounitario(aux.cambio(fila.getCell(5)));
      d.setMontodescuento(aux.cambio(fila.getCell(6)));
      d.setSubtotal(aux.cambio(fila.getCell(7)));
      d.setCantidad(aux.cambio(fila.getCell(8)));
      d.setActividadeconomica(aux.cambio(fila.getCell(9)));
      d.setObservacion(obs);
    }catch(Exception e) {
      e.printStackTrace( );
    }
    this.d.add(d);
  }
  
  // public List<Cabecera> GenerarLista(MultipartFile file) {
  // Workbook libro = aux.getlibro(file);
  // Sheet encabezado = libro.getSheetAt(0);
  // Sheet detalle = libro.getSheetAt(1);
  // int aux = 1;
  // List<Cabecera> lstCustomers = new ArrayList<Cabecera>( );
  // for(int i = 1;i <= encabezado.getLastRowNum( );i++) {
  // Cabecera e = new Cabecera( );
  // List<DetalleFact> items2 = new ArrayList<DetalleFact>( );
  // Row filaencabezado = encabezado.getRow(i);
  // e.setNumerofactura(( int ) filaencabezado.getCell(0)
  // .getNumericCellValue( ));
  // e.setNitemisor(( long ) filaencabezado.getCell(1)
  // .getNumericCellValue( ));
  // e.setCodigosucursal(( int ) filaencabezado.getCell(2)
  // .getNumericCellValue( ));
  // e.setDireccion(filaencabezado.getCell(3)
  // .getStringCellValue( ));
  // e.setCodigopuntoventa(( int ) filaencabezado.getCell(4)
  // .getNumericCellValue( ));
  // try {
  // e.setFechaemision(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(filaencabezado.getCell(5)
  // .getStringCellValue( )));
  // }catch(ParseException e1) {
  // e1.printStackTrace( );
  // }
  // e.setMes(filaencabezado.getCell(6)
  // .getStringCellValue( ));
  // e.setGestion(( int ) filaencabezado.getCell(7)
  // .getNumericCellValue( ));
  // e.setCiudad(filaencabezado.getCell(8)
  // .getStringCellValue( ));
  // e.setZona(filaencabezado.getCell(9)
  // .getStringCellValue( ));
  // e.setNumeromedidor(filaencabezado.getCell(10)
  // .getStringCellValue( ));
  // e.setDomicilio(filaencabezado.getCell(11)
  // .getStringCellValue( ));
  // e.setNombrerazonsocial(filaencabezado.getCell(12)
  // .getStringCellValue( ));
  // e.setCodigotipodocumentoidentidad(( int ) filaencabezado.getCell(13)
  // .getNumericCellValue( ));
  // e.setNumerodocumento(filaencabezado.getCell(14)
  // .getStringCellValue( ));
  // e.setComplemento(filaencabezado.getCell(15)
  // .getStringCellValue( ));
  // e.setCodigocliente(filaencabezado.getCell(16)
  // .getStringCellValue( ));
  // e.setMontototal(new BigDecimal(
  // filaencabezado.getCell(17)
  // .getNumericCellValue( )));
  // e.setMontodescuento(new BigDecimal(
  // filaencabezado.getCell(18)
  // .getNumericCellValue( )));
  // e.setMontototalsujetoiva(new BigDecimal(
  // filaencabezado.getCell(19)
  // .getNumericCellValue( )));
  // e.setConsumokwh(new BigDecimal(
  // filaencabezado.getCell(20)
  // .getNumericCellValue( )));
  // e.setConsumometroscubicos(new BigDecimal(
  // filaencabezado.getCell(13)
  // .getNumericCellValue( )));
  // e.setMontoDescuentoLey1886(new BigDecimal(
  // filaencabezado.getCell(13)
  // .getNumericCellValue( )));
  // e.setTasaaseo(new BigDecimal(
  // filaencabezado.getCell(13)
  // .getNumericCellValue( )));
  // e.setTasaalumbrado(new BigDecimal(
  // filaencabezado.getCell(13)
  // .getNumericCellValue( )));
  // for(int j = aux;j <= detalle.getLastRowNum( );j++) {
  // Row filadetalle = detalle.getRow(j);
  // if ((( int ) filaencabezado.getCell(0)
  // .getNumericCellValue( )) == (( int ) filadetalle.getCell(0)
  // .getNumericCellValue( ))) {
  // Long fact = ( long ) filadetalle.getCell(0)
  // .getNumericCellValue( );
  // int act = ( int ) filadetalle.getCell(9)
  // .getNumericCellValue( );
  // BigDecimal cat = BigDecimal.valueOf(filadetalle.getCell(8)
  // .getNumericCellValue( ));
  // int cods = ( int ) filadetalle.getCell(1)
  // .getNumericCellValue( );
  // String cod = filadetalle.getCell(2)
  // .getStringCellValue( );
  // String desc = filadetalle.getCell(3)
  // .getStringCellValue( );
  // BigDecimal mon = BigDecimal.valueOf(filadetalle.getCell(6)
  // .getNumericCellValue( ));
  // BigDecimal pre = BigDecimal.valueOf(filadetalle.getCell(5)
  // .getNumericCellValue( ));
  // BigDecimal sub = BigDecimal.valueOf(filadetalle.getCell(7)
  // .getNumericCellValue( ));
  // int uni = ( int ) filadetalle.getCell(4)
  // .getNumericCellValue( );
  // DetalleFact item = new DetalleFact(fact, act, cat, cods, cod, desc, mon, pre, sub, uni);
  // items2.add(item);
  // }else {
  // aux = j;
  // break;
  // }
  // }
  // e.setDetalle(items2);
  // lstCustomers.add(e);
  // }
  // return lstCustomers;
  // }
  
  // public String convertJsonString(List<Cabecera> lista) {
  // ObjectMapper mapper = new ObjectMapper( );
  // String jsonString = "";
  // try {
  // jsonString = mapper.writeValueAsString(lista);
  // }catch(JsonProcessingException e) {
  // e.printStackTrace( );
  // }
  // return jsonString;
  // }
  
}
