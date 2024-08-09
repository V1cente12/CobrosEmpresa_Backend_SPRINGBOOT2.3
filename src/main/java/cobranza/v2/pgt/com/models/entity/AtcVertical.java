
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
@Table(name = "atc_vertical", schema = "pgt")
public class AtcVertical {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(unique = true, nullable = false)
		private Integer idvertical;

		@Column(length = 255)
		private String nombre;

		@OneToMany(mappedBy = "atcVertical")
		@JsonIgnoreProperties({ "atcVertical", "hibernateLazyInitializer", "handler" })
		private List<AtcRubro> atcRubros;

		public AtcVertical() {
		}

		public Integer getIdvertical( ) {
				return this.idvertical;
		}

		public void setIdvertical(Integer idvertical) {
				this.idvertical = idvertical;
		}

		public String getNombre( ) {
				return this.nombre;
		}

		public void setNombre(String nombre) {
				this.nombre = nombre;
		}

		public List<AtcRubro> getAtcRubros( ) {
				return this.atcRubros;
		}

		public void setAtcRubros(List<AtcRubro> atcRubros) {
				this.atcRubros = atcRubros;
		}

		public AtcRubro addAtcRubro(AtcRubro atcRubro) {
				getAtcRubros().add(atcRubro);
				atcRubro.setAtcVertical(this);

				return atcRubro;
		}

		public AtcRubro removeAtcRubro(AtcRubro atcRubro) {
				getAtcRubros().remove(atcRubro);
				atcRubro.setAtcVertical(null);

				return atcRubro;
		}

}