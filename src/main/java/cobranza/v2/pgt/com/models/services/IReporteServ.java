
package cobranza.v2.pgt.com.models.services;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;

@Service
public interface IReporteServ {
  
  public byte[ ] reporteDeuda(Long idempresa,
                              String estado,
                              String fechaI,
                              String fechaF,
                              String usuario) throws IOException;
  public byte[ ] reporteExcel(Long idempresa,
                              String estado,
                              String fechaI,
                              String fechaF,
                              String usuario) throws IOException,
                                              ParseException;
  public byte[ ] reporteTipo(Long idempresa,
                             String estado,
                             String fechaI,
                             String fechaF,
                             String usuario,
                             String tipo) throws IOException;
  public byte[ ] reporteCONCILIACION(Long idempresa,
                                     String fechaI,
                                     String fechaF) throws IOException,
                                                    ParseException;
  public byte[ ] liquidacion(String ListIdempresa,
                             String fechaI,
                             String fechaF) throws IOException,
                                            ParseException,
                                            JRException;
  public byte[ ] reporteShopify(Long idempresa,
                                String estado,
                                String fechaI,
                                String fechaF,
                                String usuario) throws ParseException,
                                                Exception;
  public byte[ ] reporteFacturacion(Long idempresa,
                                    String codigo,
                                    String tipo,
                                    String fechaI,
                                    String fechaF,
                                    String usuario) throws IOException,
                                                    ParseException,
                                                    Exception;
  
  public byte[ ] reporteFacturacionCsv(Long idempresa,
                                       String codigo,
                                       String fechaI,
                                       String fechaF) throws IOException,
                                                      ParseException,
                                                      Exception;
  public byte[ ] reporteFacturacionSbl(String idempresa,
                                       String fechaI,
                                       String fechaF,
                                       String usuario) throws IOException,
                                                       ParseException,
                                                       Exception;
  public byte[ ] Factura(String nombre_archivo) throws IOException,
                                                ParseException,
                                                Exception;
  public byte[ ] reporteCsvShopify(Long idempresa,
                                   String estado,
                                   String fechaI,
                                   String fechaF) throws IOException,
                                                  ParseException,
                                                  Exception;
}
