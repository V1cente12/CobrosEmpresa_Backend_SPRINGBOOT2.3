
package cobranza.v2.pgt.com.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "atc_merchant_data_rubro_empresa", schema = "pgt")
public class AtcMerchantDataRubroEmpresa {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(unique = true, nullable = false)
		private Integer id;

		@Column(length = 200)
		private String valor;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "idmerchantdata_rubro", nullable = false)
		@JsonIgnoreProperties({ "atcMerchantDataRubroEmpresas", "hibernateLazyInitializer", "handler" })
		private AtcMerchantDataRubro atcMerchantDataRubro;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "idempresa", nullable = false)
		@JsonIgnoreProperties(value = { "atcMerchantDataRubroEmpresas", "idrecurso", "idusuario",
		    "idcliente", "hibernateLazyInitializer", "handler" })
		private Empresas idempresa;

		public AtcMerchantDataRubroEmpresa() {
		}

		public Integer getId( ) {
				return this.id;
		}

		public void setId(Integer id) {
				this.id = id;
		}

		public String getValor( ) {
				return this.valor;
		}

		public void setValor(String valor) {
				this.valor = valor;
		}

		public AtcMerchantDataRubro getAtcMerchantDataRubro( ) {
				return this.atcMerchantDataRubro;
		}

		public void setAtcMerchantDataRubro(AtcMerchantDataRubro atcMerchantDataRubro) {
				this.atcMerchantDataRubro = atcMerchantDataRubro;
		}

		public Empresas getIdempresa( ) {
				return idempresa;
		}

		public void setIdempresa(Empresas idempresa) {
				this.idempresa = idempresa;
		}

}