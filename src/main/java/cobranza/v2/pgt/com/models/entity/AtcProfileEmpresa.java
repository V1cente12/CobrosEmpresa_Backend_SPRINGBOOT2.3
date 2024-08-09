
package cobranza.v2.pgt.com.models.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the
 * atc_profile_empresa database table.
 * 
 */
@Entity
@Table(name = "atc_profile_empresa", schema = "pgt")
public class AtcProfileEmpresa {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Long idprofile;
  
  @Column(name = "access_key", nullable = false, length = 255)
  private String accessKey;
  
  @Column(name = "dato_adicional1", length = 255)
  private String datoAdicional1;
  
  @Column(name = "dato_adicional2", length = 255)
  private String datoAdicional2;
  
  @Column(length = 50)
  private String estado;
  
  @Column(name = "mode_profile", nullable = false, length = 100)
  private String modeProfile;
  
  @Column(name = "org_id", nullable = false, length = 255)
  private String orgId;
  
  @Column(name = "profile_id", nullable = false, length = 255)
  private String profileId;
  
  @Column(name = "session_id", nullable = false, length = 255)
  private String sessionId;
  
  @Column(name = "secret_key", nullable = false, length = 500)
  private String secretKey;
  
  @OneToMany(mappedBy = "atcProfileEmpresa")
  @JsonIgnoreProperties({"atcProfileEmpresa","hibernateLazyInitializer","handler"})
  private List<Empresas> empresas;
  
  public AtcProfileEmpresa() {}
  
  public Long getIdprofile( ) { return idprofile; }
  
  public void setIdprofile(Long idprofile) { this.idprofile = idprofile; }
  
  public String getAccessKey( ) { return this.accessKey; }
  
  public void setAccessKey(String accessKey) { this.accessKey = accessKey; }
  
  public String getDatoAdicional1( ) { return this.datoAdicional1; }
  
  public void setDatoAdicional1(String datoAdicional1) { this.datoAdicional1 = datoAdicional1; }
  
  public String getDatoAdicional2( ) { return this.datoAdicional2; }
  
  public void setDatoAdicional2(String datoAdicional2) { this.datoAdicional2 = datoAdicional2; }
  
  public String getEstado( ) { return this.estado; }
  
  public void setEstado(String estado) { this.estado = estado; }
  
  public String getModeProfile( ) { return this.modeProfile; }
  
  public void setModeProfile(String modeProfile) { this.modeProfile = modeProfile; }
  
  public String getOrgId( ) { return this.orgId; }
  
  public void setOrgId(String orgId) { this.orgId = orgId; }
  
  public String getProfileId( ) { return this.profileId; }
  
  public void setProfileId(String profileId) { this.profileId = profileId; }
  
  public String getSecretKey( ) { return this.secretKey; }
  
  public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
  
  
  public List<Empresas> getEmpresas( ) { return empresas; }
  
  
  public void setEmpresas(List<Empresas> empresas) { this.empresas = empresas; }
  
  public String getSessionId( ) { return sessionId; }
  
  public void setSessionId(String sessionId) { this.sessionId = sessionId; }
  
}