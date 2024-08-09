package cobranza.v2.pgt.com.controllers.swagger;

import cobranza.v2.pgt.com.dto.BancoDTO;
import cobranza.v2.pgt.com.mapper.BancoMapper;
import cobranza.v2.pgt.com.models.entity.Banco;
import cobranza.v2.pgt.com.models.services.BancoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carga-masiva/banco")
public class BancoController {

    public static Log log = LogFactory.getLog(BancoController.class);

    private final BancoService bancoService;
    private final BancoMapper bancoMapper;

    public BancoController(BancoService bancoService,
                           BancoMapper bancoMapper) {
        this.bancoService = bancoService;
        this.bancoMapper = bancoMapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<BancoDTO>> listarBancos() {
        try {
            List<Banco> bancos = bancoService.findAll();
            List<BancoDTO> dtos = bancoMapper.asDTOList(bancos);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(dtos);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BancoDTO> obtenerBanco(@PathVariable("id") Integer id) {
        try {
            Optional<Banco> optionalBanco = bancoService.findById(id);
            if(optionalBanco.isPresent()){
                BancoDTO dto = bancoMapper.asDTO(optionalBanco.get());
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(dto);
            }
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
