
package cobranza.v2.pgt.com.models.services;

import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.TipoEmpresa;

@Service
public interface ITipoEmpresaServ { public Iterable<TipoEmpresa> listarAll(String estado); }
