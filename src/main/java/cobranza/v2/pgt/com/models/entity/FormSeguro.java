package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "form_seguro", schema = "bff")
@Data
public class FormSeguro implements
Serializable {
  
/**
* 
*/
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long idformseg;
private String nombre;
private String apellido;
private String sexo;
@JsonFormat(pattern="yyyy-MM-dd")
@Temporal(TemporalType.TIMESTAMP)
private Date fecha_nacimiento;
private Integer edad;
private String lugar_nacimiento;
private String documento;
private String expidido;
private String estado_civil;
private String celular;
private String correo;
private String domicilio;
private String ciudad;
private String provincia; 
private String departamento;
private String estado;
private String item;
@Temporal(TemporalType.TIMESTAMP)
private Date fechaAlta;
@PrePersist
public void fecha_alta( ) { this.fechaAlta = new Date( ); }
private String usuarioAlta;
@Temporal(TemporalType.TIMESTAMP)
private Date fechaBaja;
private String usuarioBaja;
@OneToMany(mappedBy = "idformseg", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
@JsonIgnoreProperties({"idformseg","hibernateLazyInitializer","handler"})
private List<FormSeguroDetalle> detalle;
}
