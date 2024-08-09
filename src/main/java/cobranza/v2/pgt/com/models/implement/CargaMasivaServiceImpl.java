package cobranza.v2.pgt.com.models.implement;

import cobranza.v2.pgt.com.models.dao.CargaMasivaRepository;
import cobranza.v2.pgt.com.models.entity.CargaMasiva;
import cobranza.v2.pgt.com.models.enums.EstadoCargaMasiva;
import cobranza.v2.pgt.com.models.services.CargaMasivaService;
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
public class CargaMasivaServiceImpl implements CargaMasivaService {

    private final CargaMasivaRepository repository;

    public CargaMasivaServiceImpl(CargaMasivaRepository repository) {
        this.repository = repository;
    }

    @Override
    public CargaMasiva save(CargaMasiva entity) {
        return repository.save(entity);
    }

    @Override
    public List<CargaMasiva> save(List<CargaMasiva> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<CargaMasiva> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<CargaMasiva> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<CargaMasiva> findAll(Pageable pageable) {
        Page<CargaMasiva> entityPage = repository.findAll(pageable);
        List<CargaMasiva> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Page<CargaMasiva> findByEstado(EstadoCargaMasiva estado, Pageable pageable) {
        Page<CargaMasiva> entityPage = repository.findAllByEstado(estado, pageable);
        List<CargaMasiva> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public CargaMasiva update(CargaMasiva entity, Long id) {
        Optional<CargaMasiva> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public void marcarCargaMasivaExportado(Long idCargaMasiva) throws Exception{
        Optional<CargaMasiva> cargaOptional = findById(idCargaMasiva);
        if(cargaOptional.isPresent()){
            CargaMasiva cargaMasiva = cargaOptional.get();

            if(!cargaMasiva.getExportado()) {
                cargaMasiva.setExportado(Boolean.TRUE);
                cargaMasiva.setFechaModificacion(new Date());

                save(cargaMasiva);
            }
            return;
        }
        throw new Exception("No se encontró la carga masiva.");
    }

    private void cambiarEstadoCargaMasiva(CargaMasiva cargaMasiva, EstadoCargaMasiva estado) throws Exception {
        cargaMasiva.setFechaModificacion(new Date());
        cargaMasiva.setEstado(estado);

        save(cargaMasiva);
    }

    @Override
    public void anularCargaMasiva(Long id) throws Exception {
        Optional<CargaMasiva> cargaOptional = findById(id);
        if(cargaOptional.isPresent()){
            CargaMasiva cargaMasiva = cargaOptional.get();
            if(cargaMasiva.estaAnulado()){
                throw new Exception("La carga masiva ya está anulada.");
            }
            cambiarEstadoCargaMasiva(cargaMasiva, EstadoCargaMasiva.ANU);
            return;
        }
        throw new Exception("No se encontró la carga masiva.");
    }

    @Override
    public void aprobarCargaMasiva(Long id) throws Exception {
        Optional<CargaMasiva> cargaOptional = findById(id);
        if(cargaOptional.isPresent()){
            CargaMasiva cargaMasiva = cargaOptional.get();
            if(!cargaMasiva.estaEnBorrador()){
                throw new Exception("Sólo se puede aprobar cuando está en Borrador.");
            }
            cambiarEstadoCargaMasiva(cargaMasiva, EstadoCargaMasiva.APR);
            return;
        }
        throw new Exception("No se encontró la carga masiva.");
    }

    @Override
    public Page<CargaMasiva> find(String search, EstadoCargaMasiva estado, Pageable pageable) {
        Page<CargaMasiva> entityPage = repository.find(search, estado.name(), pageable);
        List<CargaMasiva> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }
}
