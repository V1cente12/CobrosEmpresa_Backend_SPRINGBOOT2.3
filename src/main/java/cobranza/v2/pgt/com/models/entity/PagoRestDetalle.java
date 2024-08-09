
package cobranza.v2.pgt.com.models.entity;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

public class PagoRestDetalle {
  
  @ApiModelProperty(notes = "Codigo/Item del producto de detalle de la deuda/recibo", example = "ACC-323", required = true)
  @NotEmpty(message = "item no puede ser vacio")
  private String item;
  
  @ApiModelProperty(notes = "Nombre del producto de detalle de la deuda/recibo", example = "Martillo", required = true)
  @NotEmpty(message = "producto no puede ser vacio")
  private String descripcion_item;
  
  @ApiModelProperty(notes = "Cantidad del producto de detalle de la deuda/recibo", example = "3", required = true)
  @NotEmpty(message = "cantidad no puede ser vacio")
  private int cantidad;
  
  @ApiModelProperty(notes = "Precio del producto de detalle de la deuda/recibo", example = "100.25", required = true)
  @NotEmpty(message = "precio no puede ser vacio")
  private BigDecimal precio_unitario;
  
  @ApiModelProperty(notes = "Subtotal del producto de detalle de la deuda/recibo", example = "300.75", required = true)
  @NotEmpty(message = "subtotal no puede ser vacio")
  private BigDecimal sub_total;
  
  @ApiModelProperty(notes = "Dato 1", example = "''", required = false)
  private String DData01;
  
  @ApiModelProperty(notes = "Dato 2", example = "''", required = false)
  private String DData02;
  
  @ApiModelProperty(notes = "Dato 3", example = "''", required = false)
  private String DData03;
  
  public String getItem( ) { return item; }
  
  public void setItem(String item) { this.item = item; }
  
  public String getDescripcion_item( ) { return descripcion_item; }
  
  public void setDescripcion_item(String descripcion_item) { this.descripcion_item = descripcion_item; }
  
  public Integer getCantidad( ) { return cantidad; }
  
  public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
  
  public void setCantidad(int cantidad) { this.cantidad = cantidad; }
  
  public BigDecimal getPrecio_unitario( ) { return precio_unitario; }
  
  public void setPrecio_unitario(BigDecimal precio_unitario) {
    this.precio_unitario = precio_unitario.setScale(2, BigDecimal.ROUND_HALF_UP);
  }
  
  public BigDecimal getSub_total( ) { return sub_total; }
  
  public void setSub_total(BigDecimal sub_total) {
    this.sub_total = sub_total.setScale(2, BigDecimal.ROUND_HALF_UP);
  }
  
  public String getDData01( ) { return DData01; }
  
  public void setDData01(String dData01) { DData01 = dData01; }
  
  public String getDData02( ) { return DData02; }
  
  public void setDData02(String dData02) { DData02 = dData02; }
  
  public String getDData03( ) { return DData03; }
  
  public void setDData03(String dData03) { DData03 = dData03; }
  
}
