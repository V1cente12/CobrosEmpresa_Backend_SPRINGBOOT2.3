package cobranza.v2.pgt.com.models.implement;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.dao.IFormSeguroDao;
import cobranza.v2.pgt.com.models.entity.FormSeguro;
import cobranza.v2.pgt.com.models.services.IFormSeguroServ;


@Service
public class IFormSeguroImpl implements IFormSeguroServ{

  @Autowired
  private IFormSeguroDao formSeguro;
  @Override
  public FormSeguro guardar(FormSeguro seguro) { 
  return formSeguro.save(seguro); }
  
  @Override
  @Transactional
  public List<FormSeguro> ListarTodo(String estado) { 
  return formSeguro.findByEstado(estado); }
  
}
