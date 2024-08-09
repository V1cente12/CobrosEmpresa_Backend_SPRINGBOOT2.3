package cobranza.v2.pgt.com.models.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.FormSeguro;


@Service
public interface IFormSeguroServ {
  FormSeguro guardar(FormSeguro seguro);
  List<FormSeguro> ListarTodo(String estado);
  
}
