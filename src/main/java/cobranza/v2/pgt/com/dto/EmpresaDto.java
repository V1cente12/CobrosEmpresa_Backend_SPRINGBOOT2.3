package cobranza.v2.pgt.com.dto;

import cobranza.v2.pgt.com.models.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaDto {
    private Long idempresa;
    private String razon_social;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String nit;
    private String emision_nit;
    private String correos;
    private String telefono_fijo;
    private String telefono_movil;
    private String pagina_web;
    private Date fecha_creacion;;
    private Date fecha_alta;
    private String logo;
    private String usuario_alta;
    private Date fecha_baja;
    private String usuario_baja;
    private String estado;
    //private List<Clientes> idcliente;
    //private List<Usuarios> idusuario;
    //private List<EmpresaMenu> idrecurso;
    //private List<Empresas> hijo;
    //private Empresas idpadre;
    //private List<AtcMerchantDataRubroEmpresa> atcMerchantDataRubroEmpresas;
    //private AtcProfileEmpresa atcProfileEmpresa;
    //private List<DocumentoEmpresa> documentoEmpresas;
    //private List<CargaMasivaDeuda> cargaMasivaDeudas;
    private List<CuentaEmpresaDto> cuentasEmpresa;
}
