package cobranza.v2.pgt.com.models.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cobranza.v2.pgt.com.models.entity.Contrato;

@Repository
public interface IContratoDao extends CrudRepository<Contrato, Long> {

}
