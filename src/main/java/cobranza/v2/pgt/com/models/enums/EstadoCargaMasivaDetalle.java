package cobranza.v2.pgt.com.models.enums;

public enum EstadoCargaMasivaDetalle {
    ACT("Activo"),
    ANU("Anulado");

    private final String estado;

    EstadoCargaMasivaDetalle(String estado){
        this.estado = estado;
    }

    public String getEstado() {
        return this.estado;
    }
}
