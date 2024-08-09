package cobranza.v2.pgt.com.models.entity;

import cobranza.v2.pgt.com.models.enums.EstadoCargaMasiva;
import cobranza.v2.pgt.com.models.enums.EstadoCargaMasivaDetalle;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "carga_masiva_detalle", schema = "cam")
@Where(clause = "estado = 'ACT'")
public class CargaMasivaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_carga_masiva_detalle", nullable = false)
    private Long idCargaMasivaDetalle;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_carga_masiva", nullable=false)
    private CargaMasiva cargaMasiva;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_banco", nullable=false)
    private Banco banco;

    @Column(name = "tipo_transaccion", nullable = false)
    private String tipoTransaccion;

    @Column(name = "tipo_documento", nullable = false, length = 2)
    private String tipoDocumento;

    @Column(name = "numero_documento", nullable = false, length = 15)
    private String numeroDocumento;

    @Column(name = "nombre_beneficiario", nullable = false, length = 40)
    private String nombreBeneficiario;

    @Column(name = "periodo_abono", nullable = false, length = 6)
    private String periodoAbono;

    @Column(name = "moneda_abono", nullable = false, length = 1)
    private String monedaAbono;

    @Column(name = "monto", nullable = false, length = 13)
    private String monto;

    @Column(name = "numero_cuenta", nullable = false, length = 20)
    @ApiModelProperty("para cuentas del BNB, max 10 chars")
    private String numeroCuenta;

    @Column(name = "referencia", nullable = false, length = 15)
    private String referencia;

    @Column(name = "concepto", nullable = false, length = 40)
    private String concepto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 3)
    private EstadoCargaMasivaDetalle estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_alta", nullable=false)
    private Date fechaAlta;

    @PrePersist
    public void fecha_alta() { this.fechaAlta = new Date(); }

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    @Column(name = "fecha_baja")
    private Date fechaBaja;

    @Column(name = "usuario_alta")
    private String usuarioAlta;

    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;

    @Column(name = "usuario_baja")
    private String usuarioBaja;

    public boolean estaAnulado() {
        return this.estado.getEstado().equals(EstadoCargaMasivaDetalle.ANU.getEstado());
    }

    public boolean estaActivo() {
        return this.estado.getEstado().equals(EstadoCargaMasivaDetalle.ACT.getEstado());
    }
}
