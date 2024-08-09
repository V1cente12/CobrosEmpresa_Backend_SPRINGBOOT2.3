
package cobranza.v2.pgt.com.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "convenio", schema = "pgt")
public class Convenio implements Serializable {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer idconvenio;

		private String autorizacionSuspensionConclusion;

		private String descripcionAutorizacion;

		private String descripcionGeneral;

		private String estado;

		@Temporal(TemporalType.DATE)
		private Date fechaFinalizacion;

		@Temporal(TemporalType.DATE)
		private Date fechaSuspensionConclusion;

		@Temporal(TemporalType.DATE)
		private Date fechaVigencia;

		private String moneda;

		private String tipoConvenio;

		private BigDecimal totalDeuda;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "iddeuda")
		private Deuda iddeuda;

		@OneToMany(mappedBy = "idconvenio")
		private List<Programacion> idprogramacions;

		/**
		 * 
		 */
		private static final long serialVersionUID = -4038353683454488245L;

		public Integer getIdconvenio( ) {
				return idconvenio;
		}

		public void setIdconvenio(Integer idconvenio) {
				this.idconvenio = idconvenio;
		}

		public String getAutorizacionSuspensionConclusion( ) {
				return autorizacionSuspensionConclusion;
		}

		public void setAutorizacionSuspensionConclusion(String autorizacionSuspensionConclusion) {
				this.autorizacionSuspensionConclusion = autorizacionSuspensionConclusion;
		}

		public String getDescripcionAutorizacion( ) {
				return descripcionAutorizacion;
		}

		public void setDescripcionAutorizacion(String descripcionAutorizacion) {
				this.descripcionAutorizacion = descripcionAutorizacion;
		}

		public String getDescripcionGeneral( ) {
				return descripcionGeneral;
		}

		public void setDescripcionGeneral(String descripcionGeneral) {
				this.descripcionGeneral = descripcionGeneral;
		}

		public String getEstado( ) {
				return estado;
		}

		public void setEstado(String estado) {
				this.estado = estado;
		}

		public Date getFechaFinalizacion( ) {
				return fechaFinalizacion;
		}

		public void setFechaFinalizacion(Date fechaFinalizacion) {
				this.fechaFinalizacion = fechaFinalizacion;
		}

		public Date getFechaSuspensionConclusion( ) {
				return fechaSuspensionConclusion;
		}

		public void setFechaSuspensionConclusion(Date fechaSuspensionConclusion) {
				this.fechaSuspensionConclusion = fechaSuspensionConclusion;
		}

		public Date getFechaVigencia( ) {
				return fechaVigencia;
		}

		public void setFechaVigencia(Date fechaVigencia) {
				this.fechaVigencia = fechaVigencia;
		}

		public String getMoneda( ) {
				return moneda;
		}

		public void setMoneda(String moneda) {
				this.moneda = moneda;
		}

		public String getTipoConvenio( ) {
				return tipoConvenio;
		}

		public void setTipoConvenio(String tipoConvenio) {
				this.tipoConvenio = tipoConvenio;
		}

		public BigDecimal getTotalDeuda( ) {
				return totalDeuda;
		}

		public void setTotalDeuda(BigDecimal totalDeuda) {
				this.totalDeuda = totalDeuda;
		}

		public Deuda getIddeuda( ) {
				return iddeuda;
		}

		public void setIddeuda(Deuda iddeuda) {
				this.iddeuda = iddeuda;
		}

		public List<Programacion> getIdprogramacions( ) {
				return idprogramacions;
		}

		public void setIdprogramacions(List<Programacion> idprogramacions) {
				this.idprogramacions = idprogramacions;
		}

}
