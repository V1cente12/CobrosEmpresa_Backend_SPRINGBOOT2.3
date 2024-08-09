
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "shopify", schema = "pgt")
public class Shopify implements
                     Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String main_id_shop;
  private String sp_shop_name;
  private String sp_store_name;
  private String seller_name;
  private String last_name;
  private String full_name;
  private String estado;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "fecha_alta")
  private Date fechaAlta;
  
  @Column(name = "fecha_baja")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaBaja;
  
  @Column(name = "usuario_alta", length = 60)
  private String usuarioAlta;
  
  @Column(name = "usuario_baja", length = 60)
  private String usuarioBaja;
  
  @Column(length = 100)
  private String apikey;
  @Column(length = 100)
  private String password;
  @Column(length = 500)
  private String url;
}
