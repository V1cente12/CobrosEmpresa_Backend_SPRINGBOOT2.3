package cobranza.v2.pgt.com.models.implement;

import cobranza.v2.pgt.com.models.dao.CuentaEmpresaRepository;
import cobranza.v2.pgt.com.models.entity.CuentaEmpresa;
import cobranza.v2.pgt.com.models.services.CuentaEmpresaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CuentaEmpresaImpl implements CuentaEmpresaService {

    private final CuentaEmpresaRepository repository;

    public CuentaEmpresaImpl(CuentaEmpresaRepository repository) {
        this.repository = repository;
    }

    @Override
    public CuentaEmpresa save(CuentaEmpresa entity) {
        return repository.save(entity);
    }

    @Override
    public List<CuentaEmpresa> save(List<CuentaEmpresa> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<CuentaEmpresa> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<CuentaEmpresa> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<CuentaEmpresa> findAll(Pageable pageable) {
        Page<CuentaEmpresa> entityPage = repository.findAll(pageable);
        List<CuentaEmpresa> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public CuentaEmpresa update(CuentaEmpresa entity, Long id) {
        Optional<CuentaEmpresa> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}
