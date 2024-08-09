
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
@Table(name = "programacion", schema = "pgt")
public class Programacion implements Serializable {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer idprogramacion;

		private String descripcionAutorizacion;

		private String descripcionGeneral;

		private BigDecimal diasGracia;

		private String estado;

		@Temporal(TemporalType.DATE)
		private Date fechaProgramacion;

		private BigDecimal montoTotalCargos;

		private BigDecimal montoTotalConvenio;

		private BigDecimal montoTotalFacturas;

		private BigDecimal montoTotalRecibos;

		private BigDecimal nroCuotas;

		private String tieneCuotaInicial;

		private String tipoAplicacionMora;

		private String tipoCargoMora;

		private String tipoConvenio;

		private BigDecimal valorCargoMora;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "idconvenio")
		private Convenio idconvenio;

		@OneToMany(mappedBy = "idprogramacion")
		private List<Recibo> idrecibos;

		public Integer getIdprogramacion( ) {
				return idprogramacion;
		}

		public void setIdprogramacion(Integer idprogramacion) {
				this.idprogramacion = idprogramacion;
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

		public BigDecimal getDiasGracia( ) {
				return diasGracia;
		}

		public void setDiasGracia(BigDecimal diasGracia) {
				this.diasGracia = diasGracia;
		}

		public String getEstado( ) {
				return estado;
		}

		public void setEstado(String estado) {
				this.estado = estado;
		}

		public Date getFechaProgramacion( ) {
				return fechaProgramacion;
		}

		public void setFechaProgramacion(Date fechaProgramacion) {
				this.fechaProgramacion = fechaProgramacion;
		}

		public BigDecimal getMontoTotalCargos( ) {
				return montoTotalCargos;
		}

		public void setMontoTotalCargos(BigDecimal montoTotalCargos) {
				this.montoTotalCargos = montoTotalCargos;
		}

		public BigDecimal getMontoTotalConvenio( ) {
				return montoTotalConvenio;
		}

		public void setMontoTotalConvenio(BigDecimal montoTotalConvenio) {
				this.montoTotalConvenio = montoTotalConvenio;
		}

		public BigDecimal getMontoTotalFacturas( ) {
				return montoTotalFacturas;
		}

		public void setMontoTotalFacturas(BigDecimal montoTotalFacturas) {
				this.montoTotalFacturas = montoTotalFacturas;
		}

		public BigDecimal getMontoTotalRecibos( ) {
				return montoTotalRecibos;
		}

		public void setMontoTotalRecibos(BigDecimal montoTotalRecibos) {
				this.montoTotalRecibos = montoTotalRecibos;
		}

		public BigDecimal getNroCuotas( ) {
				return nroCuotas;
		}

		public void setNroCuotas(BigDecimal nroCuotas) {
				this.nroCuotas = nroCuotas;
		}

		public String getTieneCuotaInicial( ) {
				return tieneCuotaInicial;
		}

		public void setTieneCuotaInicial(String tieneCuotaInicial) {
				this.tieneCuotaInicial = tieneCuotaInicial;
		}

		public String getTipoAplicacionMora( ) {
				return tipoAplicacionMora;
		}

		public void setTipoAplicacionMora(String tipoAplicacionMora) {
				this.tipoAplicacionMora = tipoAplicacionMora;
		}

		public String getTipoCargoMora( ) {
				return tipoCargoMora;
		}

		public void setTipoCargoMora(String tipoCargoMora) {
				this.tipoCargoMora = tipoCargoMora;
		}

		public String getTipoConvenio( ) {
				return tipoConvenio;
		}

		public void setTipoConvenio(String tipoConvenio) {
				this.tipoConvenio = tipoConvenio;
		}

		public BigDecimal getValorCargoMora( ) {
				return valorCargoMora;
		}

		public void setValorCargoMora(BigDecimal valorCargoMora) {
				this.valorCargoMora = valorCargoMora;
		}

		public Convenio getIdconvenio( ) {
				return idconvenio;
		}

		public void setIdconvenio(Convenio idconvenio) {
				this.idconvenio = idconvenio;
		}

		public List<Recibo> getIdrecibos( ) {
				return idrecibos;
		}

		public void setIdrecibos(List<Recibo> idrecibos) {
				this.idrecibos = idrecibos;
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = -4242473187740985223L;

}
