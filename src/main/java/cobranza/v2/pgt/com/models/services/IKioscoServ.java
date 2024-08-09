
package cobranza.v2.pgt.com.models.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cobranza.v2.pgt.com.models.entity.Kiosko;

@Service
public interface IKioscoServ { public List<Kiosko> listarIdusuario(Long idusuario); }
