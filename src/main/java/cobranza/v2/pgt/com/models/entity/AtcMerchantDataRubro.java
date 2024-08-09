
package cobranza.v2.pgt.com.models.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "atc_merchant_data_rubro", schema = "pgt")
public class AtcMerchantDataRubro {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "idmerchantdata_rubro", unique = true, nullable = false)
		private Integer idmerchantdataRubro;

		@Column(length = 50)
		private String tipo;

		@Column(length = 200)
		private String valor;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "idmerchantdata", nullable = false)
		@JsonIgnoreProperties({ "atcMerchantDataRubros", "hibernateLazyInitializer", "handler" })
		private AtcMerchantData atcMerchantData;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "idrubro", nullable = false)
		@JsonIgnoreProperties({ "atcMerchantDataRubros", "hibernateLazyInitializer", "handler" })
		private AtcRubro atcRubro;

		@OneToMany(mappedBy = "atcMerchantDataRubro")
		@JsonIgnoreProperties({ "atcMerchantDataRubro", "hibernateLazyInitializer", "handler" })
		private List<AtcMerchantDataRubroEmpresa> atcMerchantDataRubroEmpresas;

		public AtcMerchantDataRubro() {
		}

		public Integer getIdmerchantdataRubro( ) {
				return this.idmerchantdataRubro;
		}

		public void setIdmerchantdataRubro(Integer idmerchantdataRubro) {
				this.idmerchantdataRubro = idmerchantdataRubro;
		}

		public String getTipo( ) {
				return this.tipo;
		}

		public void setTipo(String tipo) {
				this.tipo = tipo;
		}

		public String getValor( ) {
				return this.valor;
		}

		public void setValor(String valor) {
				this.valor = valor;
		}

		public AtcMerchantData getAtcMerchantData( ) {
				return this.atcMerchantData;
		}

		public void setAtcMerchantData(AtcMerchantData atcMerchantData) {
				this.atcMerchantData = atcMerchantData;
		}

		public AtcRubro getAtcRubro( ) {
				return this.atcRubro;
		}

		public void setAtcRubro(AtcRubro atcRubro) {
				this.atcRubro = atcRubro;
		}

		public List<AtcMerchantDataRubroEmpresa> getAtcMerchantDataRubroEmpresas( ) {
				return this.atcMerchantDataRubroEmpresas;
		}

		public void setAtcMerchantDataRubroEmpresas(
		  List<AtcMerchantDataRubroEmpresa> atcMerchantDataRubroEmpresas) {
				this.atcMerchantDataRubroEmpresas = atcMerchantDataRubroEmpresas;
		}

		public AtcMerchantDataRubroEmpresa addAtcMerchantDataRubroEmpresa(
		  AtcMerchantDataRubroEmpresa atcMerchantDataRubroEmpresa) {
				getAtcMerchantDataRubroEmpresas().add(atcMerchantDataRubroEmpresa);
				atcMerchantDataRubroEmpresa.setAtcMerchantDataRubro(this);

				return atcMerchantDataRubroEmpresa;
		}

		public AtcMerchantDataRubroEmpresa removeAtcMerchantDataRubroEmpresa(
		  AtcMerchantDataRubroEmpresa atcMerchantDataRubroEmpresa) {
				getAtcMerchantDataRubroEmpresas().remove(atcMerchantDataRubroEmpresa);
				atcMerchantDataRubroEmpresa.setAtcMerchantDataRubro(null);

				return atcMerchantDataRubroEmpresa;
		}

}