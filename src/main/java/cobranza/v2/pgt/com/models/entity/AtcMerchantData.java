
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

@Entity
@Table(name = "atc_merchant_data", schema = "pgt")
public class AtcMerchantData {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "idmerchant_data", unique = true, nullable = false)
		@JsonIgnoreProperties({ "atcMerchantData", "hibernateLazyInitializer", "handler" })
		private Long idmerchantData;

		@Column(length = 200)
		private String campo;

		@Column(name = "descripcion_campo", length = 300)
		private String descripcionCampo;

		@Column(length = 250)
		private String ejemplo;

		@Column(length = 50)
		private String estado;

		@Column(length = 100)
		private String formato;

		@Column(nullable = false, length = 200)
		private String nombre;

		@OneToMany(mappedBy = "atcMerchantData")
		@JsonIgnoreProperties({ "atcMerchantData", "hibernateLazyInitializer", "handler" })
		private List<AtcMerchantDataRubro> atcMerchantDataRubros;

		public AtcMerchantData() {
		}

		public Long getIdmerchantData( ) {
				return this.idmerchantData;
		}

		public void setIdmerchantData(Long idmerchantData) {
				this.idmerchantData = idmerchantData;
		}

		public String getCampo( ) {
				return this.campo;
		}

		public void setCampo(String campo) {
				this.campo = campo;
		}

		public String getDescripcionCampo( ) {
				return this.descripcionCampo;
		}

		public void setDescripcionCampo(String descripcionCampo) {
				this.descripcionCampo = descripcionCampo;
		}

		public String getEjemplo( ) {
				return this.ejemplo;
		}

		public void setEjemplo(String ejemplo) {
				this.ejemplo = ejemplo;
		}

		public String getEstado( ) {
				return this.estado;
		}

		public void setEstado(String estado) {
				this.estado = estado;
		}

		public String getFormato( ) {
				return this.formato;
		}

		public void setFormato(String formato) {
				this.formato = formato;
		}

		public String getNombre( ) {
				return this.nombre;
		}

		public void setNombre(String nombre) {
				this.nombre = nombre;
		}

		public List<AtcMerchantDataRubro> getAtcMerchantDataRubros( ) {
				return this.atcMerchantDataRubros;
		}

		public void setAtcMerchantDataRubros(List<AtcMerchantDataRubro> atcMerchantDataRubros) {
				this.atcMerchantDataRubros = atcMerchantDataRubros;
		}

		public AtcMerchantDataRubro addAtcMerchantDataRubro(AtcMerchantDataRubro atcMerchantDataRubro) {
				getAtcMerchantDataRubros().add(atcMerchantDataRubro);
				atcMerchantDataRubro.setAtcMerchantData(this);

				return atcMerchantDataRubro;
		}

		public AtcMerchantDataRubro removeAtcMerchantDataRubro(
		  AtcMerchantDataRubro atcMerchantDataRubro) {
				getAtcMerchantDataRubros().remove(atcMerchantDataRubro);
				atcMerchantDataRubro.setAtcMerchantData(null);

				return atcMerchantDataRubro;
		}

}