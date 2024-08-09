package cobranza.v2.pgt.com.models.entity;

import cobranza.v2.pgt.com.models.enums.EstadoCargaMasiva;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "carga_masiva", schema = "cam")
public class CargaMasiva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_carga_masiva", nullable = false)
    private Long idCargaMasiva;

    @Column(name = "numero_cliente", nullable = false)
    private Long numeroCliente;

    @Column(name = "numero_cuenta", nullable = false)
    private Long numeroCuenta;

    @Column(name = "anio_abono", nullable = false)
    private String anioAbono;

    @Column(name = "mes_abono", nullable = false)
    private String mesAbono;

    @Column(name = "dia_abono", nullable = false)
    private String diaAbono;

    @Column(name = "exportado", nullable = false)
    private Boolean exportado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCargaMasiva estado;

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

    @OneToMany(mappedBy="cargaMasiva")
    @JsonIgnore
    private List<CargaMasivaDetalle> detalles;

    public boolean estaAprobado() {
        return this.estado.getEstado().equals(EstadoCargaMasiva.APR.getEstado());
    }

    public boolean estaAnulado() {
        return this.estado.getEstado().equals(EstadoCargaMasiva.ANU.getEstado());
    }

    public boolean estaEnBorrador() {
        return this.estado.getEstado().equals(EstadoCargaMasiva.BOR.getEstado());
    }
}
