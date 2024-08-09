package cobranza.v2.pgt.com.models.implement;

import cobranza.v2.pgt.com.models.dao.CargaMasivaDetalleRepository;
import cobranza.v2.pgt.com.models.entity.CargaMasiva;
import cobranza.v2.pgt.com.models.entity.CargaMasivaDetalle;
import cobranza.v2.pgt.com.models.enums.EstadoCargaMasiva;
import cobranza.v2.pgt.com.models.enums.EstadoCargaMasivaDetalle;
import cobranza.v2.pgt.com.models.services.CargaMasivaDetalleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CargaMasivaDetalleServiceImpl implements CargaMasivaDetalleService {

    private final CargaMasivaDetalleRepository repository;

    public CargaMasivaDetalleServiceImpl(CargaMasivaDetalleRepository repository) {
        this.repository = repository;
    }

    @Override
    public CargaMasivaDetalle save(CargaMasivaDetalle entity) {
        return repository.save(entity);
    }

    @Override
    public List<CargaMasivaDetalle> save(List<CargaMasivaDetalle> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<CargaMasivaDetalle> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<CargaMasivaDetalle> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<CargaMasivaDetalle> findAll(Pageable pageable) {
        Page<CargaMasivaDetalle> entityPage = repository.findAll(pageable);
        List<CargaMasivaDetalle> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public CargaMasivaDetalle update(CargaMasivaDetalle entity, Long id) {
        Optional<CargaMasivaDetalle> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public void anularDetalleCargaMasiva(Long id) throws Exception {
        Optional<CargaMasivaDetalle> detalleOptional = findById(id);
        if(detalleOptional.isPresent()){
            CargaMasivaDetalle detalle = detalleOptional.get();
            if(detalle.estaAnulado()){
                throw new Exception("El detalle de la carga masiva ya está anulada.");
            }
            detalle.setFechaModificacion(new Date());
            detalle.setEstado(EstadoCargaMasivaDetalle.ANU);

            save(detalle);
            return;
        }
        throw new Exception("No se encontró el detalle de la carga masiva.");
    }
}
