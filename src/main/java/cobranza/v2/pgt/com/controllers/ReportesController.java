
package cobranza.v2.pgt.com.controllers;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.models.services.IReporteServ;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2/reportes")
public class ReportesController {
  
  private Logger logger = LoggerFactory.getLogger(ReportesController.class);
  
  private Path path;
  
  @Autowired
  private IReporteServ reporteServ;
  
  @GetMapping("/pagos")
  public byte[ ] pagos(@RequestParam("estado") String estado,
                       @RequestParam("idempresa") String idempresa,
                       @RequestParam("fechaI") String fechaI,
                       @RequestParam("fechaF") String fechaF,
                       @RequestParam("usuario") String usuario) throws Exception {
    System.out.println(idempresa + " " + estado + " " + fechaI + " " + fechaF + " " + usuario);
    return reporteServ.reporteDeuda(Long.valueOf(idempresa), estado, fechaI, fechaF, usuario);
  }
  
  @GetMapping("/conciliacion")
  public byte[ ] CONCILIACION(@RequestParam("idempresa") String idempresa,
                              @RequestParam("fechaI") String fechaI,
                              @RequestParam("fechaF") String fechaF) throws Exception {
    return reporteServ.reporteCONCILIACION(Long.valueOf(idempresa), fechaI, fechaF);
  }
  
  @GetMapping("/excel")
  public byte[ ] excel(@RequestParam("estado") String estado,
                       @RequestParam("idempresa") String idempresa,
                       @RequestParam("fechaI") String fechaI,
                       @RequestParam("fechaF") String fechaF,
                       @RequestParam("usuario") String usuario) throws Exception {
    return reporteServ.reporteExcel(Long.valueOf(idempresa), estado, fechaI, fechaF, usuario);
  }
  
  @GetMapping("/links")
  public byte[ ] links(@RequestParam("estado") String estado,
                       @RequestParam("idempresa") String idempresa,
                       @RequestParam("fechaI") String fechaI,
                       @RequestParam("fechaF") String fechaF,
                       @RequestParam("usuario") String usuario,
                       @RequestParam("tipo") String tipo) throws Exception {
    return reporteServ.reporteExcel(Long.valueOf(idempresa), estado, fechaI, fechaF, usuario);
  }
  
  @GetMapping("/liquidacion")
  public byte[ ] liquidacion(@RequestParam(name = "idempresa", defaultValue = "232;33;32;268") String idempresa,
                             @RequestParam(name = "fechaI", defaultValue = "01/09/2021") String fechaI,
                             @RequestParam(name = "fechaF", defaultValue = "02/09/2021") String fechaF) throws Exception {
    return reporteServ.liquidacion(idempresa, fechaI, fechaF);
  }
  
  @GetMapping("/shopify")
  public byte[ ] shopify(@RequestParam("estado") String estado,
                         @RequestParam("idempresa") String idempresa,
                         @RequestParam("fechaI") String fechaI,
                         @RequestParam("fechaF") String fechaF,
                         @RequestParam("usuario") String usuario) throws Exception {
    System.out.println(idempresa + " " + estado + " " + fechaI + " " + fechaF + " " + usuario);
    return reporteServ.reporteShopify(Long.valueOf(idempresa), estado, fechaI, fechaF, usuario);
  }
  
  @GetMapping("/facturacion")
  public byte[ ] facturacion(@RequestParam("idempresa") String idempresa,
                             @RequestParam("codigo") String codigo,
                             @RequestParam("tipo") String tipo,
                             @RequestParam("fechaI") String fechaI,
                             @RequestParam("fechaF") String fechaF,
                             @RequestParam("usuario") String usuario) throws Exception {
    System.out.println(idempresa + " " + fechaI + " " + fechaF + " " + usuario);
    return reporteServ.reporteFacturacion(Long.valueOf(idempresa), codigo, tipo, fechaI, fechaF, usuario);
  }
  
  @GetMapping("/factura")
  public byte[ ] factura(@RequestParam("nombre") String nombre_archivo) throws Exception {
    return reporteServ.Factura(nombre_archivo);
  }
  
  @GetMapping("/facturacion/csv")
  public byte[ ] facturacionCsv(@RequestParam("idempresa") String idempresa,
                                @RequestParam("codigo") String codigo,
                                @RequestParam("fechaI") String fechaI,
                                @RequestParam("fechaF") String fechaF) throws Exception {
    System.out.println(idempresa + " " + fechaI + " " + fechaF);
    return reporteServ.reporteFacturacionCsv(Long.valueOf(idempresa), codigo, fechaI, fechaF);
  }
  
  @GetMapping("/facturacion/sbl")
  public byte[ ] facturacionSbl(@RequestParam("idempresa") String idempresa,
                                @RequestParam("fechaI") String fechaI,
                                @RequestParam("fechaF") String fechaF,
                                @RequestParam("usuario") String usuario) throws Exception {
    return reporteServ.reporteFacturacionSbl(idempresa, fechaI, fechaF, usuario);
  }
  
  @GetMapping("/shopify/csv")
  public byte[ ] shopifyCsv(@RequestParam("idempresa") String idempresa,
                            @RequestParam("estado") String estado,
                            @RequestParam("fechaI") String fechaI,
                            @RequestParam("fechaF") String fechaF) throws Exception {
    System.out.println(idempresa + " " + fechaI + " " + fechaF);
    return reporteServ.reporteCsvShopify(Long.valueOf(idempresa), estado, fechaI, fechaF);
  }
}
