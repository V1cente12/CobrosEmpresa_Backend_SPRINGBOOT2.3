package cobranza.v2.pgt.com.models.services;

import cobranza.v2.pgt.com.models.entity.CargaMasivaDetalle;

public interface CargaMasivaDetalleService extends GenericService<CargaMasivaDetalle, Long> {
    void anularDetalleCargaMasiva(Long id) throws Exception;
}
