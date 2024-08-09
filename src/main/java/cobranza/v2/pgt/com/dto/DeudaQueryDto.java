package cobranza.v2.pgt.com.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class DeudaQueryDto {
    private String cliente;
    private String valor_documento;
    private String tipo_documento;
    private String direccion;
    private BigDecimal monto;
    private String correo;
    private String entrega;
    private String codigo_cliente;
    private Long nro_cuota;
    private String estado;
    private Date fecha_venta;
    private Date fecha_vencimiento;
    private Date fecha_pago;
    private String reference_number;
    private String moneda;
    private String numero_telefono;
    private String metodo_pagado;
    private String pais;
    private String ciudad;
    private String idrecibo;
}
