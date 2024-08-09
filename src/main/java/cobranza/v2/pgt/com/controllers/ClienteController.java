
package cobranza.v2.pgt.com.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cobranza.v2.pgt.com.models.dao.ICargaMasivaDeudaDao;
import cobranza.v2.pgt.com.models.dao.IClienteDao;
import cobranza.v2.pgt.com.models.dao.IEmpresasDao;
import cobranza.v2.pgt.com.models.dao.ILinkDao;
import cobranza.v2.pgt.com.models.dao.IPersonasDao;
import cobranza.v2.pgt.com.models.dao.IReciboDao;
import cobranza.v2.pgt.com.models.entity.CargaMasivaDeuda;
import cobranza.v2.pgt.com.models.entity.Clientes;
import cobranza.v2.pgt.com.models.entity.Contrato;
import cobranza.v2.pgt.com.models.entity.Detalle;
import cobranza.v2.pgt.com.models.entity.Deuda;
import cobranza.v2.pgt.com.models.entity.Empresas;
import cobranza.v2.pgt.com.models.entity.Link;
import cobranza.v2.pgt.com.models.entity.Personas;
import cobranza.v2.pgt.com.models.entity.Recibo;
import cobranza.v2.pgt.com.models.services.IClienteServ;
import cobranza.v2.pgt.com.models.services.IEmpresaServ;
import cobranza.v2.pgt.com.models.services.IPersonasServ;
import cobranza.v2.pgt.com.utils.Auxiliar;
import cobranza.v2.pgt.com.utils.otros.ArchivoTxT;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/cobranzaV2")
public class ClienteController {
  
  private static final Logger log = LoggerFactory.getLogger(ClienteController.class);
  
  @Autowired
  private IClienteServ clienteServ;
  @Autowired
  private IEmpresaServ empresaServ;
  @Autowired
  private Auxiliar aux;
  @Autowired
  private IEmpresasDao empresaDao;
  @Autowired
  private IClienteDao clienteDao;
  @Autowired
  private IReciboDao reciboDao;
  @Autowired
  private IPersonasDao personaDao;
  @Autowired
  private ILinkDao linkDao;
  @Autowired
  private ICargaMasivaDeudaDao cargaDao;
  @Autowired
  private IPersonasServ personaServ;
  @Value("#{'${name.url}'}")
  private String URL;
  @Value("#{'${name.port}'}")
  private String PORT;
  
  @GetMapping("/cliente/page/{page}")
  public Page<Clientes> index(@PathVariable Integer page,
                              @RequestParam("estado") String estado,
                              @RequestParam("nombre") String nombre,
                              @RequestParam("size") String size,
                              @RequestParam("idempresa") String idempresa) {
    Page<Clientes> p = clienteServ.buscarEstadoNombre(estado, Long.valueOf(idempresa), nombre.toUpperCase( ),
      PageRequest.of(page, Integer.valueOf(size)));
    return p;
  }
  
  @PostMapping("/cliente/file")
  public ResponseEntity<?> subir(@RequestParam MultipartFile file,
                                 @RequestParam("usuario") String usuario,
                                 @RequestParam("idempresa") Long idempresa,
                                 @RequestParam("encabezado") String encabezado,
                                 HttpServletResponse response2) throws Exception {
    Map<String, Object> response = new HashMap<>( );
    System.out.println(usuario + "_" + idempresa + "_" + encabezado + "_" + file.getName( ));
    Long inicia = System.currentTimeMillis( );
    Map<String, Object> dato = new HashMap<>( );
    Map<String, Object> claims = new HashMap<>( );
    Workbook libro = aux.getlibro(file);
    Sheet cliente = libro.getSheetAt(0);
    DataFormatter dataFormatter = new DataFormatter( );
    List<String> error = new ArrayList<>( );
    CargaMasivaDeuda carga = new CargaMasivaDeuda( );
    List<Personas> personas = new ArrayList<>( );
    List<ArchivoTxT> archivoTxT = new ArrayList<>( );
    Empresas empresa = empresaDao.buscarEmpresaID(idempresa, "A");
    for(int i = 0;i < cliente.getPhysicalNumberOfRows( );i++) {
      final Row row = cliente.getRow(i);
      Personas persona = new Personas( );
      Clientes clientes = new Clientes( );
      Contrato contrato = new Contrato( );
      Deuda deuda = new Deuda( );
      Recibo recibo = new Recibo( );
      Detalle detalle = new Detalle( );
      List<Detalle> listdetalle = new ArrayList<>( );
      List<Clientes> listcliente = new ArrayList<>( );
      List<Contrato> listcontrato = new ArrayList<>( );
      List<Deuda> listdeuda = new ArrayList<>( );
      if (encabezado.equals("0")) {
        for(int j = 0;j < row.getPhysicalNumberOfCells( );j++) {
          final Cell cell = row.getCell(j);
          try {
            if (cell == null) {
              if (j != 4 && j != 9 && j != 10 && j != 11 && j != 12) {
                error.add(String.valueOf("Vacio -> Fila: " + row.getRowNum( )) + ", Columna:" + aux.columna(
                  j));
              }
            }else {
              switch(cell.getColumnIndex( )){
                case 0:
                  if (aux.numero(dataFormatter.formatCellValue(cell))) {
                    persona.setValor_documento(dataFormatter.formatCellValue(cell));
                  }else {
                    error.add(String.valueOf("Error tipo dato (Numérico) -> Fila: " + row.getRowNum( ))
                      + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                  }
                  break;
                case 1:
                  if (aux.numero(dataFormatter.formatCellValue(cell))) {
                    persona.setTipo_documento(dataFormatter.formatCellValue(cell));
                  }else {
                    error.add(String.valueOf("Error tipo dato (Numérico) -> Fila: " + row.getRowNum( ))
                      + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                  }
                  break;
                case 2:
                  if (cell.getCellType( ) == 1) {
                    persona.setNombres(dataFormatter.formatCellValue(cell));
                  }else {
                    error.add(String.valueOf("Error tipo dato (Texto) -> Fila: " + row.getRowNum( ))
                      + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                  }
                  break;
                case 3:
                  if (cell.getCellType( ) == 1) {
                    persona.setApellido_paterno(dataFormatter.formatCellValue(cell));
                  }else {
                    error.add(String.valueOf("Error tipo dato (Texto) -> Fila: " + row.getRowNum( ))
                      + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                  }
                  break;
                case 4:
                  if (cell.getCellType( ) == 1 || cell.getCellType( ) == 3) {
                    persona.setApellido_materno(dataFormatter.formatCellValue(cell));
                  }else {
                    error.add(String.valueOf("Error tipo dato (Texto) -> Fila: " + row.getRowNum( ))
                      + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                  }
                  break;
                case 5:
                  if (cell.getCellType( ) == 1) {
                    persona.setDomicilio(dataFormatter.formatCellValue(cell));
                  }else {
                    error.add(String.valueOf("Error tipo dato (Texto) -> Fila: " + row.getRowNum( ))
                      + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                  }
                  break;
                case 6:
                  if (aux.numero(dataFormatter.formatCellValue(cell))) {
                    persona.setTelefono(dataFormatter.formatCellValue(cell));
                  }else {
                    error.add(String.valueOf("Error tipo dato (Numérico) -> Fila: " + row.getRowNum( ))
                      + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                  }
                  break;
                case 7:
                  if (cell.getCellType( ) == 1) {
                    persona.setCorreo(dataFormatter.formatCellValue(cell));
                  }else {
                    error.add(String.valueOf("Error tipo dato (Texto) -> Fila: " + row.getRowNum( ))
                      + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                  }
                  break;
                case 8:
                  clientes.setCodigo_cliente(dataFormatter.formatCellValue(cell));
                  break;
                case 9:
                  if (cell.getCellType( ) != 3) {
                    contrato.setCodcontrato(dataFormatter.formatCellValue(cell));
                  }
                  break;
                case 10:
                  if (cell.getCellType( ) != 3) {
                    contrato.setRazonSocial(dataFormatter.formatCellValue(cell));
                    recibo.setDescripcion_general(dataFormatter.formatCellValue(cell));
                  }
                  break;
                case 11:
                  if (cell.getCellType( ) != 3) {
                    if (cell.getCellType( ) == 0) {
                      contrato.setNit(dataFormatter.formatCellValue(cell));
                    }else {
                      error.add(String.valueOf("Error tipo dato (Texto) -> Fila: " + row.getRowNum( ))
                        + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                    }
                  }
                  break;
                case 12:
                  if (cell.getCellType( ) != 3) { contrato.setServicio(dataFormatter.formatCellValue(cell)); }
                  break;
                case 13:
                  if (aux.numero(dataFormatter.formatCellValue(cell))) {
                    if (reciboDao.ExisteNroPedido(Long.valueOf(dataFormatter.formatCellValue(cell)),
                      idempresa)
                                 .isPresent( )) {
                      error.add(String.valueOf("Numero de pedido ya existente -> Fila: " + row.getRowNum( ))
                        + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                    }else {
                      recibo.setNro_recibo(Long.valueOf(dataFormatter.formatCellValue(cell)));
                    }
                  }else {
                    error.add(String.valueOf("Error tipo dato (Numérico) -> Fila: " + row.getRowNum( ))
                      + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                  }
                  break;
                case 14:
                  recibo.setTipo_recibo(dataFormatter.formatCellValue(cell));
                  break;
                case 15:
                  if (aux.isdate(dataFormatter.formatCellValue(cell), "yyyy-MM-dd")) {
                    recibo.setFecha_alta(aux.conversionStringDate(dataFormatter.formatCellValue(cell),
                      "yyyy-MM-dd"));
                  }else {
                    error.add(String.valueOf("Error formato (yyyy-MM-dd) -> Fila: " + row.getRowNum( ))
                      + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                  }
                  break;
                case 16:
                  if (aux.isdate(dataFormatter.formatCellValue(cell), "yyyy-MM-dd")) {
                    recibo.setFecha_vencimiento(aux.conversionStringDate(dataFormatter.formatCellValue(cell),
                      "yyyy-MM-dd"));
                  }else {
                    error.add(String.valueOf("Error formato (yyyy-MM-dd) -> Fila: " + row.getRowNum( ))
                      + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                  }
                  break;
                case 17:
                  if (cell.getCellType( ) == 0) {
                    recibo.setMonto(BigDecimal.valueOf(Double.valueOf(cell.getNumericCellValue( ))));
                  }else {
                    error.add(String.valueOf("Error tipo dato (Numérico) -> Fila: " + row.getRowNum( ))
                      + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                  }
                  break;
                case 18:
                  if (aux.numero(dataFormatter.formatCellValue(cell))) {
                    recibo.setMoneda(Integer.valueOf(dataFormatter.formatCellValue(cell)));
                  }else {
                    error.add(String.valueOf("Error tipo dato (Numérico) -> Fila: " + row.getRowNum( ))
                      + ", Columna:" + aux.columna(cell.getColumnIndex( )));
                  }
                  break;
                case 19:
                  recibo.setConcepto_recibo(dataFormatter.formatCellValue(cell));
                  break;
              }
            }
          }
          catch(Exception e) {
            e.printStackTrace( );
          }
          // System.out.println( );
        }
        // System.out.println("Error" + error.size( ));
        if (error.size( ) == 0) {
          Personas peraux = personaDao.buscarValorDoc(persona.getValor_documento( ));
          Optional<Clientes> cliaux = clienteDao.buscarClienteEmpresa(clientes.getCodigo_cliente( ),
            idempresa);
          if (peraux != null) {
            // System.out.println("existe la persona");
            String telf = persona.getTelefono( );
            String direccion = persona.getDomicilio( );
            persona = peraux;
            persona.setTelefono(telf);
            persona.setDomicilio(direccion);
          }
          if (cliaux.isPresent( )) {
            // System.out.println("existe el cliente");
            clientes = cliaux.get( );
          }else {
            clientes.setIdpersona(persona);
            clientes.setIdempresa(empresa);
            clientes.setCodigo_cliente(persona.getValor_documento( ));
            clientes.setUsuario_alta("carga masiva - cliente nuevo");
          }
          clientes.setEstado("A");
          persona.setEstado("A");
          recibo.setEstado("PEN");
          recibo.setGlosa1(persona.getValor_documento( ));
          recibo.setGlosa2(persona.getCorreo( ));
          String suscriptor = persona.getNombres( ) + " " + persona.getApellido_paterno( );
          String nombreApellido = persona.getValor_documento( ) + ";" + persona.getNombres( ) + ";" + persona
                                                                                                             .getApellido_paterno( )
            + ";" + persona.getDomicilio( ) + ";" + persona.getCorreo( );
          recibo.setNombre_apellido(nombreApellido);
          recibo.setSuscripcion(suscriptor);
          String fechaV = aux.ConversionDateString(new Date( ), "dd/MM/yyyy") + " 23:59:59";
          recibo.setFecha_vencimiento(aux.conversionStringDate(fechaV, "dd/MM/yyyy hh:mm:ss"));
          detalle.setCantidad(1);
          detalle.setPrecio_unitario(recibo.getMonto( ));
          detalle.setSub_total(recibo.getMonto( ));
          detalle.setDescripcion_item(recibo.getConcepto_recibo( ));
          detalle.setIdrecibo(recibo);
          listdetalle.add(detalle);
          recibo.setIddetalle(listdetalle);
          clientes.setIdempresa(empresa);
          listcliente.add(clientes);
          deuda.setEstado("PEN");
          deuda.setIdrecibo(recibo);
          listdeuda.add(deuda);
          listcontrato.add(contrato);
          clientes.setIddeuda(listdeuda);
          persona.setIdcliente(listcliente);
          clientes.setIdpersona(persona);
          deuda.setIdcliente(clientes);
          // empresa.setIdcliente(listcliente);
          if (listcontrato.get(0)
                          .getCodcontrato( ) != null) {
            contrato.setIdempresa(idempresa);
            clientes.setIdcontrato(listcontrato);
            contrato.setIdcliente(clientes);
          }
          personas.add(persona);
          if (error.size( ) == 0) {
            carga.setEstado("A");
            carga.setDescripcion("preuba");
            carga.setEmpresa(empresa);
            // System.out.println(carga);
            // System.out.println(persona);
            carga = cargaDao.save(carga);
            persona = personaDao.save(persona);
            ArchivoTxT txt = new ArchivoTxT( );
            claims.put("references", "0");
            String jwt = aux.createJWT2(String.valueOf(idempresa), String.valueOf(persona.getIdcliente( )
                                                                                         .get(0)
                                                                                         .getIddeuda( )
                                                                                         .get(0)
                                                                                         .getIddeuda( )),
              persona.getCorreo( ), claims, recibo.getFecha_vencimiento( ));
            Link link = new Link( );
            link.setCorreo(persona.getCorreo( ));
            link.setEstado("PEN");
            link.setTelefono(persona.getTelefono( ));
            link.setIdbitacora(carga.getId( ));
            link.setIdcliente(persona.getIdcliente( )
                                     .get(0));
            link.setCodigocliente(persona.getIdcliente( )
                                         .get(0)
                                         .getCodigo_cliente( ));
            link.setMonto(persona.getIdcliente( )
                                 .get(0)
                                 .getIddeuda( )
                                 .get(0)
                                 .getIdrecibo( )
                                 .getMonto( ));
            link.setIddeuda(String.valueOf(persona.getIdcliente( )
                                                  .get(0)
                                                  .getIddeuda( )
                                                  .get(0)
                                                  .getIddeuda( )));
            link.setLink(URL + ":" + PORT + "/#/carrito?id=");
            link.setToken(jwt);
            linkDao.save(link);
            txt.setCodcliente(persona.getIdcliente( )
                                     .get(0)
                                     .getCodigo_cliente( ));
            txt.setCorreo(persona.getCorreo( ));
            txt.setTelefono(persona.getTelefono( ));
            txt.setValordocumento(persona.getValor_documento( ));
            txt.setLink(link.getLink( ) + link.getToken( ));
            archivoTxT.add(txt);
            // System.out.println(persona.getIdpersona( ));
          }
        }
      }else {
        encabezado = "0";
      }
    }
    // System.out.println("Errores : " + error.size( ));
    if (error.size( ) == 0) {
      response.put("error", error);
      response.put("persona", personas);
      response.put("txt", archivoTxT);
      response.put("idcarga", carga.getId( ));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }else {
      response.put("error", error);
      response.put("persona", personas);
      response.put("txt", archivoTxT);
      response.put("idcarga", carga.getId( ));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
  }
  
  @GetMapping(value = "cliente/doc-cobranza")
  public byte[ ] getFile( ) {
    try {
      DefaultResourceLoader loader = new DefaultResourceLoader( );
      InputStream is = loader.getResource("classpath:file/formato-cobranza.xlsx")
                             .getInputStream( );
      byte[ ] doc = IOUtils.toByteArray(is);
      return doc;
    }
    catch(IOException ex) {
      throw new RuntimeException("IOError writing file to output stream");
    }
  }
  
  @GetMapping("/cliente/reporte")
  public byte[ ] reporteAll(HttpServletResponse response) throws Exception {
    return clienteServ.reporteClienteAll( );
  }
  
  @GetMapping("/cliente/reporteID")
  public byte[ ] reporteID(HttpServletResponse response,
                           @RequestParam("cliente") String cliente,
                           @RequestParam("id") String id,
                           @RequestParam("idempresa") String idempresa) throws Exception {
    return clienteServ.reporteIDcliente(Long.valueOf(id), cliente, Long.valueOf(idempresa));
  }
  
  @GetMapping("/cliente/reporteALL")
  public byte[ ] reporteAll(HttpServletResponse response,
                            @RequestParam("idempresa") String idempresa,
                            @RequestParam("estado") String estado) throws Exception {
    return clienteServ.reporteALLcliente(estado, Long.valueOf(idempresa));
  }
  
  @PostMapping("/cliente")
  public ResponseEntity<?> create(@RequestParam("idempresa") String idempresa,
                                  @RequestParam("idpersona") String idpersona) {
    Map<String, Object> response = new HashMap<>( );
    try {
      Empresas e = empresaServ.listarID("A", Long.valueOf(idempresa));
      Personas p = personaServ.listarID("A", Long.valueOf(idpersona));
      Clientes cliente = new Clientes( );
      cliente.setFecha_alta(new Date( ));
      cliente.setIdempresa(e);
      cliente.setIdpersona(p);
      Clientes clientenueva = clienteServ.save(cliente);
      response.put("mensaje", "El cliente ha sido creado con exito...");
      response.put("cliente", clientenueva);
    }
    catch(Exception e) {
      e.printStackTrace( );
    }
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }
}
