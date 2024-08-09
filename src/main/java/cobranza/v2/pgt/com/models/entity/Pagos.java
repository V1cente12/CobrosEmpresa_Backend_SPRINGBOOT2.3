package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pago", schema = "pgt")
public class Pagos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpago;

    @Column(length = 2000)
    private String descripcion;

    private Date fecha_alta;

    @PrePersist
    public void fechaalta() {
        this.fecha_alta = new Date();
    }

    private String usuario_alta;
    private Date fecha_baja;
    private String usuario_baja;

    @OneToMany(mappedBy = "idpago")
    @JsonIgnoreProperties({ "idpago", "idrecibo", "hibernateLazyInitializer", "handler" })
    private List<Deuda> iddeuda;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdpago() {
        return idpago;
    }

    public void setIdpago(Long idpago) {
        this.idpago = idpago;
    }

    public List<Deuda> getIddeuda() {
        return iddeuda;
    }

    public void setIddeuda(List<Deuda> iddeuda) {
        this.iddeuda = iddeuda;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getUsuario_alta() {
        return usuario_alta;
    }

    public void setUsuario_alta(String usuario_alta) {
        this.usuario_alta = usuario_alta;
    }

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public String getUsuario_baja() {
        return usuario_baja;
    }

    public void setUsuario_baja(String usuario_baja) {
        this.usuario_baja = usuario_baja;
    }

    /**
     * 
     */
    private static final long serialVersionUID = -318107384519062206L;
}
