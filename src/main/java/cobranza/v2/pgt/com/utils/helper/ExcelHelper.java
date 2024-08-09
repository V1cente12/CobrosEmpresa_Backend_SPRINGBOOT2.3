package cobranza.v2.pgt.com.utils.helper;

import cobranza.v2.pgt.com.dto.CargaMasivaParaReporteDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String SHEET = "Cargas Masivas";

    public byte[] cargasMasivasToExcel(List<CargaMasivaParaReporteDTO> cargasMasivas){
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        String[] cabecera = cabecera();
        try {
            Sheet sheet = workbook.createSheet(SHEET);
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < cabecera.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(cabecera[col]);
            }

            int rowIdx = 1;
            for (CargaMasivaParaReporteDTO carga : cargasMasivas) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(carga.getIdCargaMasiva());
                row.createCell(1).setCellValue(carga.getNumeroCliente());
                row.createCell(2).setCellValue(carga.getNumeroCuenta());
                row.createCell(3).setCellValue(carga.getAnioAbono());
                row.createCell(4).setCellValue(carga.getMesAbono());
                row.createCell(5).setCellValue(carga.getDiaAbono());

                String exportado = carga.getExportado()? "SI": "NO";
                row.createCell(6).setCellValue(exportado);
                row.createCell(7).setCellValue(carga.getEstado());
                row.createCell(8).setCellValue(carga.getFechaAlta().toString());

                String modificado = Objects.isNull(carga.getFechaModificacion())? "": carga.getFechaModificacion().toString();
                row.createCell(9).setCellValue(modificado);
                row.createCell(10).setCellValue(carga.getIdCargaMasivaDetalle());
                row.createCell(11).setCellValue(carga.getEstadoDetalle());

                String tipoTransaccion = tipoTransaccion(carga.getTipoTransaccion());
                row.createCell(12).setCellValue(tipoTransaccion);

                String tipoDocumento = tipoDocumento(carga.getTipoDocumento());
                row.createCell(13).setCellValue(tipoDocumento);
                row.createCell(14).setCellValue(carga.getNumeroDocumento());
                row.createCell(15).setCellValue(carga.getNombreBeneficiario());
                row.createCell(16).setCellValue(carga.getPeriodoAbono());
                row.createCell(17).setCellValue(carga.getMonedaAbono());
                row.createCell(18).setCellValue(carga.getMonto());
                row.createCell(19).setCellValue(carga.getBanco());
                row.createCell(20).setCellValue(carga.getNumeroCuentaDestino());
                row.createCell(21).setCellValue(carga.getReferencia());
                row.createCell(22).setCellValue(carga.getConcepto());
            }

            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error al importar las cargas masivas al archivo excel: " + e.getMessage());
        }
    }

    private String tipoTransaccion(String codigo){
        if(codigo.equals("01")){
            return "TRANSFERENCIA A CUENTAS BNB";
        }
        if(codigo.equals("04")){
            return "TRANSFERENCIA INTERBANCARIA";
        }
        return "";
    }

    private String tipoDocumento(String codigo){
        if(codigo.equals("01")){
            return "CI";
        }
        if(codigo.equals("02")){
            return "RUN";
        }
        if(codigo.equals("09")){
            return "NIT";
        }
        return "";
    }

    public String[] cabecera(){
        return new String[]{
                "ID",
                "NÚMERO CLIENTE",
                "NÚMERO CUENTA",
                "AÑO ABONO",
                "MES ABONO",
                "DÍA ABONO",
                "EXPORTADO",
                "ESTADO",
                "FECHA CREACIÓN",
                "ÚLTIMA MODIFICACIÓN",
                "ID DETALLE",
                "ESTADO DETALLE",
                "TIPO TRANSACCIÓN",
                "TIPO DOCUMENTO",
                "NÚMERO DOCUMENTO",
                "BENEFICIARIO",
                "PERIODO ABONO",
                "MONEDA ABONO",
                "MONTO",
                "BANCO",
                "NÚMERO CUENTA DESTINO",
                "REFERENCIA",
                "CONCEPTO"
        };
    }
}
