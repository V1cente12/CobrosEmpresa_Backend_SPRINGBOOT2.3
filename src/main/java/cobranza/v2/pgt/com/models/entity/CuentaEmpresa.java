package cobranza.v2.pgt.com.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cuenta_empresa", schema = "pgt")
@Builder
public class CuentaEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta_empresa", nullable = false)
    private Long idCuentaEmpresa;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_empresa", nullable=false)
    private Empresas empresa;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_banco", nullable = false)
    private Banco banco;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_persona", nullable = false)
    private Personas titular;

    @Column(name = "numero_cuenta", nullable = false)
    private Long numeroCuenta;

    @Column(name = "moneda_cuenta", nullable = false)
    private String monedaCuenta;

    @Column(name = "tipo_cuenta", nullable = true)
    private String tipoCuenta;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "fecha_alta", nullable = false)
    private Date fechaAlta;

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

}
