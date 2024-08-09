
package cobranza.v2.pgt.com.controllers.swagger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import cobranza.v2.pgt.com.CobranzaV2Application;
import cobranza.v2.pgt.com.dto.CargaMasivaListadoDTO;
import cobranza.v2.pgt.com.models.dao.CargaMasivaRepository;
import cobranza.v2.pgt.com.models.entity.CargaMasiva;
import cobranza.v2.pgt.com.models.services.CargaMasivaService;
import cobranza.v2.pgt.com.models.services.ExcelService;
import cobranza.v2.pgt.com.utils.otros.CargaMasivaRenderer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CobranzaV2Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ExportarTest {
  
  @Autowired
  private CargaMasivaService cargaMasivaService;
  
  @Autowired
  private ExcelService excelService;;
  
  @MockBean
  private CargaMasivaRepository repository;
  
  @MockBean
  private CargaMasivaRenderer cargaMasivaRenderer;
  
  @Test
  public void exportarExcel( ) {
    List<CargaMasiva> cargasMasivas = new ArrayList<CargaMasiva>( );
    CargaMasiva carga1 = CargaMasiva
            .builder( )
            .idCargaMasiva(1L)
            .mesAbono("05")
            .diaAbono("10")
            .anioAbono("2021")
            .numeroCuenta(1234567890L)
            .numeroCliente(456123L)
            .detalles(new ArrayList<>( ))
            .build( );
    
    cargasMasivas.add(carga1);
    
    Date fechaInicial = new GregorianCalendar(2021, Calendar.DECEMBER, 01).getTime( );
    Date fechaFinal = new GregorianCalendar(2022, Calendar.JANUARY, 10).getTime( );
    
    Mockito.when(repository.findAllByFechaAltaBetweenOrderByIdCargaMasiva(fechaInicial, fechaFinal))
           .thenReturn(cargasMasivas);
    
    byte[ ] bytes = excelService.generarExcelByteArray(null, fechaInicial, fechaFinal);
    System.err.println(Arrays.toString(bytes));
    
    assertTrue(bytes.length > 1);
  }
  
  @Test
  public void exportarTXT( ) {
    Long id = 1L;
    CargaMasivaListadoDTO dto = CargaMasivaListadoDTO
            .builder()
            .idCargaMasiva(id)
            .exportado(Boolean.TRUE)
            .estado("Aprobado")
            .mesAbono("05")
            .diaAbono("10")
            .anioAbono("2021")
            .numeroCuenta(1234567890L)
            .numeroCliente(456123L)
            .detalle(new ArrayList<>( ))
            .build( );
    
    String resultado = "456123       1234567890 2021005100000300000003204000000000015000";
    
    Mockito.when(cargaMasivaRenderer.exportar(dto))
           .thenReturn(resultado);
    
    String exportado = cargaMasivaRenderer.exportar(dto);
    
    assertEquals(exportado, resultado);
  }
}