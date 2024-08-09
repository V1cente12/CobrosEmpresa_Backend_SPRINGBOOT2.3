package cobranza.v2.pgt.com.models.implement;

import cobranza.v2.pgt.com.models.dao.BancoRepository;
import cobranza.v2.pgt.com.models.entity.Banco;
import cobranza.v2.pgt.com.models.services.BancoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BancoServiceImpl implements BancoService {

    private final BancoRepository repository;

    public BancoServiceImpl(BancoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Banco save(Banco entity) {
        return repository.save(entity);
    }

    @Override
    public List<Banco> save(List<Banco> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Banco> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Banco> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Banco> findAll(Pageable pageable) {
        Page<Banco> entityPage = repository.findAll(pageable);
        List<Banco> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Banco update(Banco entity, Integer id) {
        Optional<Banco> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }
}
