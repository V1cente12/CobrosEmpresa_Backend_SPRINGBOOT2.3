package cobranza.v2.pgt.com.models.enums;

public enum EstadoCargaMasiva {
    BOR("Borrador"),
    APR("Aprobado"),
    ANU("Anulado");

    private final String estado;

    EstadoCargaMasiva(String estado){
        this.estado = estado;
    }

    public String getEstado() {
        return this.estado;
    }
}
