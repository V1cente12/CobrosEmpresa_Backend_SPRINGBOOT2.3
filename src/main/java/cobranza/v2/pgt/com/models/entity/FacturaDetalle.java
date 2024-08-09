package cobranza.v2.pgt.com.models.entity;

public class FacturaDetalle {

    private String id;
    private String numerofactura;
    private String actividadeconomica;
    private String codigoproductosin;
    private String codigoproducto;
    private String descripcion;
    private String unidadmedida;
    private String preciounitario;
    private String montodescuento;
    private String subtotal;
    private String cantidad;
    private String numeroserie;
    private String numeroimei;
    private String observacion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumerofactura() {
        return numerofactura;
    }

    public void setNumerofactura(String numerofactura) {
        this.numerofactura = numerofactura;
    }

    public String getActividadeconomica() {
        return actividadeconomica;
    }

    public void setActividadeconomica(String actividadeconomica) {
        this.actividadeconomica = actividadeconomica;
    }

    public String getCodigoproductosin() {
        return codigoproductosin;
    }

    public void setCodigoproductosin(String codigoproductosin) {
        this.codigoproductosin = codigoproductosin;
    }

    public String getCodigoproducto() {
        return codigoproducto;
    }

    public void setCodigoproducto(String codigoproducto) {
        this.codigoproducto = codigoproducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadmedida() {
        return unidadmedida;
    }

    public void setUnidadmedida(String unidadmedida) {
        this.unidadmedida = unidadmedida;
    }

    public String getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(String preciounitario) {
        this.preciounitario = preciounitario;
    }

    public String getMontodescuento() {
        return montodescuento;
    }

    public void setMontodescuento(String montodescuento) {
        this.montodescuento = montodescuento;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getNumeroserie() {
        return numeroserie;
    }

    public void setNumeroserie(String numeroserie) {
        this.numeroserie = numeroserie;
    }

    public String getNumeroimei() {
        return numeroimei;
    }

    public void setNumeroimei(String numeroimei) {
        this.numeroimei = numeroimei;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

}
