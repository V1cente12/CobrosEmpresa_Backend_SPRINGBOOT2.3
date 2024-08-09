
package cobranza.v2.pgt.com.models.implement;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import cobranza.v2.pgt.com.utils.Auxiliar;
import cobranza.v2.pgt.com.utils.otros.ArchivoTxT;
import net.sf.jasperreports.engine.JREmptyDataSource;

@Service
public class IClienteImpl implements
                          IClienteServ {
  
  private Logger logger = LoggerFactory.getLogger(IClienteImpl.class);
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
  private Auxiliar aux;
  @Value("#{'${name.url}'}")
  private String URL;
  @Value("#{'${name.port}'}")
  private String PORT;
  
  @Override
  public List<Clientes> saveAll(List<Clientes> cliente) { return clienteDao.saveAll(cliente); }
  
  @Override
  public Map<?, ?> archivo(MultipartFile file,
                           String usuario,
                           Long idempresa,
                           String encabezado) throws ParseException {
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
    for(Row row: cliente) {
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
        System.out.println("----------------------------");
        System.out.println("\n" + aux.isRowEmpty(row, 15));
        System.out.print(row.getRowNum( ) + "\t");
        if (aux.isRowEmpty(row, 15)) {
          for(Cell cell: row) {
            System.out.print(dataFormatter.formatCellValue(cell) + "(" + cell.getCellType( ) + ") -> \t");
            if (cell.getCellType( ) == 3 && cell.getColumnIndex( ) != 3 && cell.getColumnIndex( ) != 4 && cell
                                                                                                              .getColumnIndex( ) != 9
              && cell.getColumnIndex( ) != 10 && cell.getColumnIndex( ) != 11 && cell
                                                                                     .getColumnIndex( ) != 12) {
              error.add(String.valueOf("Vacio -> Fila: " + row.getRowNum( )) + ", Columna:" + aux.columna(cell
                                                                                                              .getColumnIndex( )));
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
                  if (cell.getCellType( ) == 1 || cell.getCellType( ) == 3) {
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
          // System.out.println("\n" + error.size( ));
          if (error.size( ) == 0) {
            Personas peraux = personaDao.buscarValorDoc(persona.getValor_documento( ));
            Clientes cliaux = clienteDao.buscarClienteEmpresa(clientes.getCodigo_cliente( ), idempresa)
                                        .get( );
            if (peraux != null) {
              System.out.println("existe la persona");
              String telf = persona.getTelefono( );
              persona = peraux;
              persona.setTelefono(telf);
            }
            if (cliaux != null) {
              System.out.println("existe el cliente");
              clientes = cliaux;
            }
          }
          clientes.setEstado("A");
          persona.setEstado("A");
          recibo.setEstado("PEN");
          recibo.setGlosa1(persona.getValor_documento( ));
          recibo.setGlosa2(persona.getCorreo( ));
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
          // System.out.println( );
          personas.add(persona);
          // ******************************************************
          if (error.size( ) == 0) {
            carga.setEstado("A");
            carga.setDescripcion("preuba");
            carga.setEmpresa(empresa);
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
            System.out.println(persona.getIdpersona( ));
          }
        }
      }else {
        encabezado = "0";
      }
    }
    System.out.println(error);
    System.out.println(personas);
    System.out.println(archivoTxT);
    dato.put("error", error);
    dato.put("persona", personas);
    dato.put("txt", archivoTxT);
    dato.put("idcarga", carga.getId( ));
    logger.info("Duración " + (System.currentTimeMillis( ) - inicia) + " ms.");
    return dato;
    
  }
  
  @Override
  public byte[ ] reporteClienteAll( ) {
    Map<String, Object> params = new HashMap<>( );
    return aux.reporteAll("lista", params, new JREmptyDataSource( ));
  }
  
  @Override
  public byte[ ] reporteIDcliente(Long id,
                                  String cliente,
                                  Long idempresa) {
    Map<String, Object> params = new HashMap<>( );
    params.put("IDcliente", id);
    params.put("cliente", cliente);
    params.put("idempresa", idempresa);
    return aux.reporteAll("listaIDcliente", params, new JREmptyDataSource( ));
  }
  
  @Override
  public byte[ ] reporteALLcliente(String estadodeuda,
                                   Long idempresa) {
    Map<String, Object> params = new HashMap<>( );
    System.out.println(estadodeuda);
    params.put("EstadoDeuda", estadodeuda);
    params.put("idempresa", idempresa);
    return aux.reporteAll("listaALLclienteEstadodeuda", params, new JREmptyDataSource( ));
  }
  
  @Override
  public Page<Clientes> buscarEstadoNombre(String estado,
                                           Long idempresa,
                                           String nombre,
                                           Pageable p) {
    return clienteDao.listaFilterEstadoNombre(estado, nombre, idempresa, p);
  }
  
  @Override
  @Transactional
  public Clientes save(Clientes cliente) { return clienteDao.save(cliente); }
  
  @Override
  @Transactional(readOnly = true)
  public Clientes listarID(String estado,
                           Long id) { return clienteDao.listaID(estado, id); }
  
  @Override
  @Transactional(readOnly = true)
  public Clientes listarCodigoCliente(String codigo_cliente) {
    return clienteDao.buscarCodigoCliente(codigo_cliente);
  }
  
  @Override
  @Transactional(readOnly = true)
  public Optional<Clientes> buscarClienteEmpresa(String codigo,
                                                 Long idempresa) {
    return clienteDao.buscarClienteEmpresa(codigo, idempresa);
  }
  
  @Override
  @Transactional(readOnly = true)
  public boolean booleanClienteEmpresa(String codigo,
                                       Long idempresa) {
    return clienteDao.booleanClienteEmpresa(codigo, idempresa);
  }
}
