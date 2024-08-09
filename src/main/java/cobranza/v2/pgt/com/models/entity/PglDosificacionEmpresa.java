package cobranza.v2.pgt.com.models.entity;

public class PglDosificacionEmpresa {

    public Integer idEmpresa;
    public String estado;
    public String empresa;
    public String nit;
    public String clase;
    public Object object;
    public String string;

    public PglDosificacionEmpresa(Integer idEmpresa, String estado, String empresa, String nit, String clase, Object object,
        String string) {
        super();
        this.idEmpresa = idEmpresa;
        this.estado = estado;
        this.empresa = empresa;
        this.nit = nit;
        this.clase = clase;
        this.object = object;
        this.string = string;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    // public PglDosificacionEmpresa(Integer idEmpresa, String estado, String empresa, String nit, String clase, Object object,
    // String string) {
    // }

}
