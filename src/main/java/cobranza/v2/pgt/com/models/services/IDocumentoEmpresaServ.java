
package cobranza.v2.pgt.com.models.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.DocumentoEmpresa;

@Service
public interface IDocumentoEmpresaServ {
  
  public DocumentoEmpresa guardar(DocumentoEmpresa entity);
  public List<DocumentoEmpresa> guardarAll(List<DocumentoEmpresa> entity);
  public Iterable<DocumentoEmpresa> ObtenerIdEmpresa(Long idempresa);
}
