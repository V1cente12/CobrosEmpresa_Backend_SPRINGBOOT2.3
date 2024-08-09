package cobranza.v2.pgt.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cobranza.v2.pgt.com.models.entity.FormSeguro;


public interface IFormSeguroDao extends JpaRepository<FormSeguro, Long>{
  
  List<FormSeguro> findByEstado(String estado);
  
}
