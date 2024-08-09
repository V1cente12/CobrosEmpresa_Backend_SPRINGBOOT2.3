
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
@Table(name = "atc_rubro", schema = "pgt")
public class AtcRubro {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(unique = true, nullable = false)
		private Integer idrubro;

		@Column(nullable = false, length = 100)
		private String codigo;

		@Column(nullable = false, length = 200)
		private String nombre;

		@OneToMany(mappedBy = "atcRubro")
		@JsonIgnoreProperties({ "atcRubro", "hibernateLazyInitializer", "handler" })
		private List<AtcMerchantDataRubro> atcMerchantDataRubros;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "idvertical", nullable = false)
		@JsonIgnoreProperties({ "atcRubros", "hibernateLazyInitializer", "handler" })
		private AtcVertical atcVertical;

		public AtcRubro() {
		}

		public Integer getIdrubro( ) {
				return this.idrubro;
		}

		public void setIdrubro(Integer idrubro) {
				this.idrubro = idrubro;
		}

		public String getCodigo( ) {
				return this.codigo;
		}

		public void setCodigo(String codigo) {
				this.codigo = codigo;
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
				atcMerchantDataRubro.setAtcRubro(this);

				return atcMerchantDataRubro;
		}

		public AtcMerchantDataRubro removeAtcMerchantDataRubro(
		  AtcMerchantDataRubro atcMerchantDataRubro) {
				getAtcMerchantDataRubros().remove(atcMerchantDataRubro);
				atcMerchantDataRubro.setAtcRubro(null);

				return atcMerchantDataRubro;
		}

		public AtcVertical getAtcVertical( ) {
				return this.atcVertical;
		}

		public void setAtcVertical(AtcVertical atcVertical) {
				this.atcVertical = atcVertical;
		}

}