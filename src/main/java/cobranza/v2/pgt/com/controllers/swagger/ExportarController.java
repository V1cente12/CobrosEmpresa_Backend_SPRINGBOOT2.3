package cobranza.v2.pgt.com.controllers.swagger;

import cobranza.v2.pgt.com.models.enums.EstadoCargaMasiva;
import cobranza.v2.pgt.com.models.implement.CargaMasivaFacade;
import cobranza.v2.pgt.com.models.services.ExcelService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/carga-masiva/exportar")
public class ExportarController {
    public static Log log = LogFactory.getLog(ExportarController.class);

    private final CargaMasivaFacade cargaMasivaFacade;
    private final ExcelService excelService;

    public ExportarController(CargaMasivaFacade cargaMasivaFacade, 
                              ExcelService excelService) {
        this.cargaMasivaFacade = cargaMasivaFacade;
        this.excelService = excelService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> exportarTXT(@PathVariable("id") Long idCargaMasiva) {
        try {
            //String fileName = "Transferencia Masiva Exportado.txt";
            String contenido = cargaMasivaFacade.exportarCargaMasiva(idCargaMasiva);

            cargaMasivaFacade.marcarCargaMasivaExportado(idCargaMasiva);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(contenido.length())
                    .body(new ByteArrayResource(contenido.getBytes()));
        }
        catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/excel")
    public ResponseEntity<?> exportarExcel(@RequestParam(value = "estado", required = false) EstadoCargaMasiva estado,
                                          @RequestParam("fechaInicial")
                                          @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaInicial,
                                          @RequestParam("fechaFinal")
                                          @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaFinal) {
        try {
            byte[] contenido = excelService.generarExcelByteArray(estado, fechaInicial, fechaFinal);

            String filename = "Cargas Masivas.xlsx";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(new ByteArrayResource(contenido));
        }
        catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
