package cobranza.v2.pgt.com.models.services;

import cobranza.v2.pgt.com.models.enums.EstadoCargaMasiva;
import org.springframework.core.io.ByteArrayResource;

import java.util.Date;

public interface ExcelService {
    byte[] generarExcelByteArray(EstadoCargaMasiva estado, Date fechaInicial, Date fechaFinal);
}
