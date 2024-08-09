
package cobranza.v2.pgt.com.models.implement;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.services.INotificacionServ;
import cobranza.v2.pgt.com.utils.Auxiliar;

@Service
public class INotificacionImpl implements INotificacionServ {
  
  @Autowired
  private Auxiliar aux;
  
  @Override
  public Map<?, ?> archivo(MultipartFile file, String encabezado) throws ParseException {
    Map<String, List<?>> dato = new HashMap<>( );
    Workbook libro = aux.getlibro(file);
    Sheet cliente = libro.getSheetAt(0);
    DataFormatter dataFormatter = new DataFormatter( );
    List<String> error = new ArrayList<>( );
    for(Row row: cliente) {
      if (encabezado.equals("0")) {
        for(Cell cell: row) {
          System.out.print(row.getRowNum( ) + "-" + cell.getColumnIndex( ) + "\t");
          System.out.print(dataFormatter.formatCellValue(cell) + "(" + cell.getCellType( ) + ")\t");
          if (cell.getCellType( ) == 3) {
            error.add(String.valueOf("Vacio -> Fila: " + row.getRowNum( )) + ", Columna:"
            + aux.columna(cell.getColumnIndex( )));
          }else {
            switch(cell.getColumnIndex( )){
              case 0:
                if (cell.getCellType( ) != 1) {
                  error.add(String.valueOf("Error tipo dato -> Fila: " + row.getRowNum( ))
                  + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                }
                break;
              case 1:
                if (cell.getCellType( ) != 0) {
                  error.add(String.valueOf("Error tipo dato -> Fila: " + row.getRowNum( ))
                  + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                }
                break;
            }
          }
        }
      }else {
        encabezado = "0";
      }
      System.out.println( );
    }
    dato.put("error", error);
    return dato;
  }
  
  @Override
  public Map<?, ?> enviomsj(MultipartFile file, String encabezado) throws Exception {
    Map<String, List<?>> dato = new HashMap<>( );
    List<String> lista = new ArrayList<>( );
    Workbook libro = aux.getlibro(file);
    Sheet cliente = libro.getSheetAt(0);
    DataFormatter dataFormatter = new DataFormatter( );
    for(Row row: cliente) {
      String numero = null, msj = null;
      if (encabezado.equals("0")) {
        for(Cell cell: row) {
          switch(cell.getColumnIndex( )){
            case 0:
              msj = dataFormatter.formatCellValue(cell);
              break;
            case 1:
              numero = dataFormatter.formatCellValue(cell);
              break;
          }
        }
        lista.add(numero + ":" + msj);
        aux.sendUrl("591" + numero, msj, "");
      }else {
        encabezado = "0";
      }
    }
    dato.put("datos", lista);
    return dato;
  }
  
}
