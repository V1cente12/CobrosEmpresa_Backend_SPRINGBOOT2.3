package cobranza.v2.pgt.com.models.services;

import cobranza.v2.pgt.com.models.entity.CargaMasiva;
import cobranza.v2.pgt.com.models.enums.EstadoCargaMasiva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CargaMasivaService extends GenericService<CargaMasiva, Long> {
    Page<CargaMasiva> findByEstado(EstadoCargaMasiva estado, Pageable pageable);

    void marcarCargaMasivaExportado(Long idCargaMasiva) throws Exception;

    void anularCargaMasiva(Long id) throws Exception;

    void aprobarCargaMasiva(Long id) throws Exception;

    Page<CargaMasiva> find(String search, EstadoCargaMasiva estado, Pageable pageable);
}
