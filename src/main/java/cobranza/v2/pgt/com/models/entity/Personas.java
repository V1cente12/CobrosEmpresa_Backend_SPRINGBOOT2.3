
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "persona", schema = "pgt")
public class Personas implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idpersona;
  
  private String nombres;
  
  private String apellido_paterno;
  
  private String apellido_materno;
  
  private String apellido_marital;
  
  private String apellido_casado;
  
  private String tipo_documento;
  
  @Column(name = "valor_documento", nullable = false, length = 60)
  private String valor_documento;
  
  private String estado_civil;
  
  private Date fecha_nacimiento;
  
  private String lugar_nacimiento;
  
  private String nacionalidad;
  
  private String emision_documento;
  
  private Date fecha_aniversario;
  
  private String correo;
  
  private String profesion;
  
  private String domicilio;
  private String telefono;
  
  private Integer edad;
  
  private String foto;
  
  private Date fecha_alta;
  
  @PrePersist
  public void fechaalta( ) { this.fecha_alta = new Date( ); }
  
  private String usuario_alta;
  
  private Date fecha_baja;
  
  private String usuario_baja;
  
  private Date fecha_modificacion;
  
  private String usuario_modificacion;
  
  private String estado;
  
  @OneToMany(mappedBy = "idpersona", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnoreProperties({"idpersona","hibernateLazyInitializer","handler"})
  private List<Usuarios> idusuario;
  
  @OneToMany(mappedBy = "idpersona", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JsonIgnoreProperties({"idpersona","hibernateLazyInitializer","handler"})
  private List<Clientes> idcliente;
  
  public Long getIdpersona( ) { return idpersona; }
  
  public void setIdpersona(Long idpersona) { this.idpersona = idpersona; }
  
  public String getNombres( ) { return nombres; }
  
  public void setNombres(String nombres) { this.nombres = nombres; }
  
  public String getApellido_paterno( ) { return apellido_paterno; }
  
  public void setApellido_paterno(String apellido_paterno) {
    this.apellido_paterno = apellido_paterno;
  }
  
  public String getApellido_materno( ) { return apellido_materno; }
  
  public void setApellido_materno(String apellido_materno) {
    this.apellido_materno = apellido_materno;
  }
  
  public String getApellido_casado( ) { return apellido_casado; }
  
  public void setApellido_casado(String apellido_casado) { this.apellido_casado = apellido_casado; }
  
  public String getTipo_documento( ) { return tipo_documento; }
  
  public void setTipo_documento(String tipo_documento) { this.tipo_documento = tipo_documento; }
  
  public String getValor_documento( ) { return valor_documento; }
  
  public void setValor_documento(String valor_documento) { this.valor_documento = valor_documento; }
  
  public String getEstado_civil( ) { return estado_civil; }
  
  public void setEstado_civil(String estado_civil) { this.estado_civil = estado_civil; }
  
  public Date getFecha_nacimiento( ) { return fecha_nacimiento; }
  
  public void setFecha_nacimiento(Date fecha_nacimiento) {
    this.fecha_nacimiento = fecha_nacimiento;
  }
  
  public String getLugar_nacimiento( ) { return lugar_nacimiento; }
  
  public void setLugar_nacimiento(String lugar_nacimiento) {
    this.lugar_nacimiento = lugar_nacimiento;
  }
  
  public String getNacionalidad( ) { return nacionalidad; }
  
  public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
  
  public String getEmision_documento( ) { return emision_documento; }
  
  public void setEmision_documento(String emision_documento) {
    this.emision_documento = emision_documento;
  }
  
  public Date getFecha_alta( ) { return fecha_alta; }
  
  public void setFecha_alta(Date fecha_alta) { this.fecha_alta = fecha_alta; }
  
  public String getUsuario_alta( ) { return usuario_alta; }
  
  public void setUsuario_alta(String usuario_alta) { this.usuario_alta = usuario_alta; }
  
  public Date getFecha_baja( ) { return fecha_baja; }
  
  public void setFecha_baja(Date fecha_baja) { this.fecha_baja = fecha_baja; }
  
  public String getUsuario_baja( ) { return usuario_baja; }
  
  public void setUsuario_baja(String usuario_baja) { this.usuario_baja = usuario_baja; }
  
  public String getEstado( ) { return estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public List<Usuarios> getIdusuario( ) { return idusuario; }
  
  public void setIdusuario(List<Usuarios> idusuario) { this.idusuario = idusuario; }
  
  public String getFoto( ) { return foto; }
  
  public void setFoto(String foto) { this.foto = foto; }
  
  public Date getFecha_aniversario( ) { return fecha_aniversario; }
  
  public void setFecha_aniversario(Date fecha_aniversario) {
    this.fecha_aniversario = fecha_aniversario;
  }
  
  public String getProfesion( ) { return profesion; }
  
  public void setProfesion(String profesion) { this.profesion = profesion; }
  
  public String getDomicilio( ) { return domicilio; }
  
  public void setDomicilio(String domicilio) { this.domicilio = domicilio; }
  
  public Integer getEdad( ) { return edad; }
  
  public void setEdad(Integer edad) { this.edad = edad; }
  
  public Date getFecha_modificacion( ) { return fecha_modificacion; }
  
  public void setFecha_modificacion(Date fecha_modificacion) {
    this.fecha_modificacion = fecha_modificacion;
  }
  
  public String getUsuario_modificacion( ) { return usuario_modificacion; }
  
  public void setUsuario_modificacion(String usuario_modificacion) {
    this.usuario_modificacion = usuario_modificacion;
  }
  
  public List<Clientes> getIdcliente( ) { return idcliente; }
  
  public void setIdcliente(List<Clientes> idcliente) { this.idcliente = idcliente; }
  
  public String getApellido_marital( ) { return apellido_marital; }
  
  public void setApellido_marital(String apellido_marital) {
    this.apellido_marital = apellido_marital;
  }
  
  public String getCorreo( ) { return correo; }
  
  public void setCorreo(String correo) { this.correo = correo; }
  
  public String getTelefono( ) { return telefono; }
  
  public void setTelefono(String telefono) { this.telefono = telefono; }
  
  private static final long serialVersionUID = 1L;
  
}
