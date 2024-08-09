
package cobranza.v2.pgt.com.utils;

public class Constants {
  
  // Estado de factura
  public static final String TIPO_ANULADAS = "Anuladas";
  public static final String TIPO_EMITIDAS = "Emitidas";
  public static final String TIPO_RECHAZADAS = "Rechazadas";
  
  // Nombre de columna reporte de facturas
  public static final String COL_FECHA = "Fecha";
  public static final String COL_CODSUC = "Codigo Sucursal";
  public static final String COL_SUCURSAL = "Sucursal";
  public static final String COL_TFAC = "Tipo Factura";
  public static final String COL_NIT = "NIT";
  public static final String COL_RSOCIAL = "Razón Social";
  public static final String COL_NROFACT = "Nro. Factura";
  public static final String COL_MONTO = "Monto";
  public static final String COL_ESTADO = "Estado";
  public static final String COL_TEMISION = "Tipo Emisión";
  public static final String COL_CUF = "CUF";
  public static final String[ ] COL_CSV = new String[ ]{COL_FECHA,COL_CODSUC,COL_SUCURSAL,COL_TFAC,COL_NIT,
                                                        COL_RSOCIAL,COL_NROFACT,COL_MONTO,COL_ESTADO,
                                                        COL_TEMISION,COL_CUF};
  
  // Datos documento
  public static final String NOMBRE_HOJA = "Listado de facturas";
  public static final String NOMBRE_DOCUMENTO = "Reporte general.xlsx";
  public static final String NOMBRE_TITULO = "Reporte de Facturación";
  
  // Datos principales
  public static final String USUARIO = "Usuario:";
  public static final String FH = "Fecha/Hora:";
  public static final String COMERCIO = "Comercio:";
  public static final String NIT = "Nit:";
  
  // Estados de pago de deuda
  public static final String STATE_PAGADO = "PAGADO";
  public static final String STATE_PENDIENTE = "PENDIENTE";
  public static final String STATE_ANULADO = "ANULADO";
  public static final String TITULO_DEUDAS = "REPORTE DE DEUDAS DE CLIENTES";
  
  // Nombres de columna deudas clientes
  public static final String D_NOMBRE_COMPLETO = "Nombre completo";
  public static final String D_IDENTIFICACION = "Identificación";
  public static final String D_FECHA_VENTA = "Fech. Venta";
  public static final String D_FECHA_PAGO = "Fech. Pago";
  public static final String D_PEDIDO_CUENTA = "Pedido/Cuenta";
  public static final String D_MONTO_DEUDA = "Monto Deuda";
  public static final String D_MONEDA = "Moneda";
  public static final String D_FECH_VCTO = "Fech. Vcto.";
  public static final String D_TOTAL_PAGADO = "Total Pagado";
  public static final String D_METODO_PAGO = "Método Pago";
  public static final String D_TELEFONO = "Telefono";
  public static final String D_PAIS = "Pais";
  public static final String D_CIUDAD = "Ciudad";
  public static final String D_CORREO = "Correo";
  public static final String[ ] D_COLUMNAS = new String[ ]{D_NOMBRE_COMPLETO,D_IDENTIFICACION,D_FECHA_VENTA,
                                                           D_FECHA_PAGO,D_PEDIDO_CUENTA,D_MONTO_DEUDA,
                                                           D_MONEDA,D_FECH_VCTO,D_TOTAL_PAGADO,D_METODO_PAGO,
                                                           D_TELEFONO,D_PAIS,D_CIUDAD,D_CORREO};
  public static final int D_ESTILO_NOMBRE_COLUMNA = 1;
  public static final int D_ESTILO_VALOR_COLUMNA = 2;
  public static final int D_ESTILO_TOTAL = 3;
  public static final int D_ESTILO_DESCRIPCION = 4;
  
  // Sigla Pais
  public static final String P_BOLIVIA = "BO";
  
  // Dominio
  public static final String DOM_COMISION = "COMISION";
  // Subdominio
  public static final String SUB_EMPRESA = "EMPRESA";
  
  // Tipos de tarjeta y qr
  public static final String TIPO_TJ_NAC = "TJN";
  public static final String TIPO_TJ_INT = "TJI";
  public static final String TIPO_QR = "QR";
  public static final String TIPO_COM_PGT = "PGT";
  public static final String TIPO_COM_ATC = "ATC";
  public static final String TIPO_COM_BNB = "BNB";
  
  // Listado de facturas SBL
  public static final String SBLT_TITULO_REPORTE = "LISTADO DE FACTURAS";
  public static final String SBLT_TOTAL = "TOTALES:";
  public static final String SBLC_ORDEN = "Orden";
  public static final String SBLC_POZO = "Pozo";
  public static final String SBLC_SEG = "Seg.";
  public static final String SBLC_CLIENTE = "Cliente";
  public static final String SBLC_FECHA = "Fecha";
  public static final String SBLC_FACTURA = "Factura";
  public static final String SBLC_USD = "Importe($us)";
  public static final String SBLC_TC = "TC";
  public static final String SBLC_BS = "Importe(Bs)";
  public static final String SBLC_CONTRATO = "Contrato";
  public static final String SBLC_AUTO = "Autorización";
  public static final String SBLC_DES = "Descripción";
  public static final String SBLC_TIPO = "Tipo Factura";
  public static final String[ ] SBLC_COLUMNAS = new String[ ]{SBLC_ORDEN,SBLC_POZO,SBLC_SEG,SBLC_CLIENTE,
                                                              SBLC_FECHA,SBLC_FACTURA,SBLC_USD,SBLC_TC,
                                                              SBLC_BS,SBLC_AUTO,SBLC_CONTRATO,SBLC_DES,
                                                              SBLC_TIPO};
  // Listado de estilos
  public static final int ST_BORDER_TOP = 0;
  public static final int ST_BORDER_RIGHT = 1;
  public static final int ST_BORDER_BOTTOM = 2;
  public static final int ST_BORDER_LEFT = 3;
  public static final int ST_BORDER_ALL = 4;
  public static final int ST_BK_GREY = 5;
  public static final int ST_TITLE = 6;
  public static final int ST_NAME = 7;
  public static final int ST_NAME_VALUE = 8;
  public static final int ST_CELL_VALUE = 9;
  public static final int ST_NUMERIC = 10;
  public static final int ST_TOTAL = 11;
  public static final int ST_TOTAL_USD = 1;
  public static final int ST_TOTAL_BS = 2;
  
  // Nombre de columna reporte de csv Shopify
  public static final String SHO_NOMAPE = "Nombre(s) y Apellido(s)";
  public static final String SHO_FVENTA = "Fecha Venta";
  public static final String SHO_NROPEDIDO = "Nro. Pedido";
  public static final String SHO_PROD = "Producto";
  public static final String SHO_FPAGO = "Fecha Pago";
  public static final String SHO_CANT = "Cantidad";
  public static final String SHO_PRECIO = "Precio";
  public static final String SHO_SUBTOTAL = "Sub. Total";
  public static final String SHO_DESC = "Descuento";
  public static final String SHO_TOTAL = "Total";
  public static final String SHO_MET = "Metodo Pago";
  // public static final String SHO_ENTREGA = "Entrega";
  public static final String SHO_ITEM = "Vendor";
  public static final String[ ] SHO_CSV = new String[ ]{SHO_NOMAPE,SHO_FVENTA,SHO_NROPEDIDO,SHO_PROD,
                                                        SHO_FPAGO,SHO_CANT,SHO_PRECIO,SHO_SUBTOTAL,SHO_DESC,
                                                        SHO_TOTAL,SHO_MET,SHO_ITEM};
  
}