
package cobranza.v2.pgt.com.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cobranza.v2.pgt.com.dto.EmpresaDto;
import cobranza.v2.pgt.com.mapper.EmpresaMapper;
import cobranza.v2.pgt.com.models.entity.AtcProfileEmpresa;
import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.entity.Link;
import cobranza.v2.pgt.com.models.services.IEmpresaServ;
import cobranza.v2.pgt.com.models.services.ILinkServ;
import cobranza.v2.pgt.com.models.services.IProfileEmpresaServ;
import cobranza.v2.pgt.com.utils.Auxiliar;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class EmpresaController {
  
  @Autowired
  private IEmpresaServ empresaServ;
  
  @Autowired
  private IProfileEmpresaServ proS;
  
  @Autowired
  private ILinkServ linkServ;
  
  @Autowired
  private Auxiliar aux;
  
  private final EmpresaMapper empresaMapper;
  
  public EmpresaController(EmpresaMapper empresaMapper) { this.empresaMapper = empresaMapper; }
  
  @GetMapping("/empresa/link/{page}")
  public Page<Link> pagelink(@PathVariable Integer page,
                             @RequestParam("nombre") String nombre,
                             @RequestParam("idempresa") String idempresa,
                             @RequestParam("estado") String estado,
                             @RequestParam("size") String size,
                             @RequestParam("fechaI") String fechaI,
                             @RequestParam("fechaF") String fechaF) {
    System.out.println(fechaI + "-" + fechaF + "-" + nombre + "-" + size + " " + idempresa + " " + estado);
    Page<Link> p = linkServ.listarAllPage(estado, nombre.toUpperCase( ), Long.valueOf(idempresa), fechaI,
      fechaF, PageRequest.of(page, Integer.valueOf(size)));
    
    return p;
  }
  
  @PostMapping("/empresas")
  public ResponseEntity<?> create(@RequestBody Empresas empresa,
                                  BindingResult result) {
    Map<String, Object> response = new HashMap<>( );
    try {
      empresa.setFecha_alta(new Date( ));
      empresa.setEstado("A");
      Empresas p = empresaServ.guardar(empresa);
      response.put("empresa", p);
      response.put("idpersona", p.getIdempresa( ));
      response.put("mensaje", "La empresa ha sido creado con exito...");
      
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @GetMapping("/empresas")
  public List<Empresas> index(@RequestParam("estado") String estado) { return empresaServ.listarAll(estado); }
  
  @GetMapping("/empresas/{id}")
  public Empresas show(@RequestParam("estado") String estado,
                       @PathVariable Long id) {
    return empresaServ.listarID(estado, id);
  }
  
  @GetMapping("/empresas/page/{page}")
  public Page<?> page(@PathVariable Integer page,
                      @RequestParam("estado") String estado,
                      @RequestParam("size") String size,
                      @RequestParam("buscar") String buscar) {
    return empresaServ.empresaPage(estado, buscar, PageRequest.of(page, Integer.valueOf(size)));
  }
  
  @GetMapping("/empresa/{id}")
  public ResponseEntity<EmpresaDto> obtenerEmpresa(@PathVariable Long id) {
    try {
      Optional<Empresas> empresaOptional = empresaServ.obtenerEmpresa(id);
      if (empresaOptional.isPresent( )) {
        EmpresaDto empresaDto = empresaMapper.asEmpresaDto(empresaOptional.get( ));
        return new ResponseEntity<>(empresaDto, HttpStatus.OK);
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
                           .build( );
    }
    catch(Exception e) {
      e.printStackTrace( );
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .build( );
    }
    
  }
  
  @GetMapping("/empresas/cambio")
  public int cambio(@RequestParam("estado") String estado,
                    @RequestParam("id") Long id,
                    @RequestParam("usuario") String usuario) {
    return empresaServ.cambio(estado, usuario, id);
  }
  
  @PostMapping("/profile")
  public ResponseEntity<?> create2(@RequestBody AtcProfileEmpresa empresa,
                                   BindingResult result) {
    Map<String, Object> response = new HashMap<>( );
    try {
      empresa.setEstado("A");
      AtcProfileEmpresa p = proS.save(empresa);
      response.put("empresa", p);
      response.put("idpersona", p.getEmpresas( ));
      response.put("mensaje", "La empresa ha sido creado con exito...");
      
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
  
  @GetMapping("/empresa/conciliacion")
  public ResponseEntity<?> conciliacion(@RequestParam("fechaI") String fechaI,
                                        @RequestParam("fechaF") String fechaF,
                                        @RequestParam("idempresa") String idempresa,
                                        @RequestParam(defaultValue = "0", name = "page") String page,
                                        @RequestParam(defaultValue = "10", name = "size") String size,
                                        @RequestParam(defaultValue = "pg.fecha_alta,desc") String[ ] sortBy) throws ParseException {
    Map<String, Object> response = new HashMap<>( );
    System.out.println(fechaI + " --- " + fechaF);
    response.put("conciliacion", empresaServ.CONCILIACION(fechaI, fechaF, Long.valueOf(idempresa), PageRequest
                                                                                                              .of(
                                                                                                                Integer.valueOf(
                                                                                                                  page),
                                                                                                                Integer.valueOf(
                                                                                                                  size),
                                                                                                                Sort.by(
                                                                                                                  new Order(
                                                                                                                    aux.getSortDirection(
                                                                                                                      sortBy[1]),
                                                                                                                    sortBy[0])))));
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }
  
  @GetMapping("/empresa/conciliacionExcel")
  public void export(@RequestParam("idempresa") String idempresa,
                     @RequestParam("fechaI") String fechaI,
                     @RequestParam("fechaF") String fechaF,
                     HttpServletResponse response) throws NumberFormatException,
                                                   ParseException,
                                                   IOException {
    List<Object[ ]> persons = empresaServ.CONCILIACION2(fechaI, fechaF, Long.valueOf(idempresa));
    Workbook workbook = new HSSFWorkbook( );
    Sheet sheet = workbook.createSheet("All Applications List");
    Row header = sheet.createRow(0);
    header.createCell(0)
          .setCellValue("Reference_number");
    header.createCell(1)
          .setCellValue("Valor_documento");
    header.createCell(2)
          .setCellValue("Tipo_documento");
    header.createCell(3)
          .setCellValue("Nombre_completo");
    int rowNum = 1;
    for(Object[ ] obj: persons) {
      Row row = sheet.createRow(rowNum++);
      row.createCell(0)
         .setCellValue(obj[0].toString( ));
      row.createCell(1)
         .setCellValue(obj[1].toString( ));
      row.createCell(2)
         .setCellValue(obj[2].toString( ));
      row.createCell(2)
         .setCellValue(obj[3].toString( ));
    }
    response.setHeader("Content-disposition", "attachment; filename=conciliacion.xlsx");
    workbook.write(response.getOutputStream( ));
  }
  
  @GetMapping("/empresa/listaempresaAux/{estado}")
  public List<?> BuscarEmpresaEstado(@PathVariable("estado") String estado) {
    return empresaServ.BuscarEmpresaEstado(estado);
  }
}
