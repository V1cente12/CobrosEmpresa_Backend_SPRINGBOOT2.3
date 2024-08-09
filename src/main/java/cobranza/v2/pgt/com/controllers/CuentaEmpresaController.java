package cobranza.v2.pgt.com.controllers;

import cobranza.v2.pgt.com.controllers.swagger.BancoController;
import cobranza.v2.pgt.com.dto.CuentaEmpresaDto;
import cobranza.v2.pgt.com.dto.CuentaEmpresaParaInsertarDTO;
import cobranza.v2.pgt.com.mapper.CuentaEmpresaMapper;
import cobranza.v2.pgt.com.models.entity.Banco;
import cobranza.v2.pgt.com.models.entity.CuentaEmpresa;
import cobranza.v2.pgt.com.models.services.BancoService;
import cobranza.v2.pgt.com.models.services.CuentaEmpresaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cuenta-empresa")
public class CuentaEmpresaController {
    public static Log log = LogFactory.getLog(BancoController.class);

    private final CuentaEmpresaService cuentaEmpresaService;
    private final BancoService bancoService;
    private final CuentaEmpresaMapper cuentaEmpresaMapper;

    public CuentaEmpresaController(CuentaEmpresaService cuentaEmpresaService,
                                   BancoService bancoService,
                                   CuentaEmpresaMapper cuentaEmpresaMapper) {
        this.cuentaEmpresaService = cuentaEmpresaService;
        this.bancoService = bancoService;
        this.cuentaEmpresaMapper = cuentaEmpresaMapper;
    }

    @PostMapping("/")
    public ResponseEntity<?> registrarCuentaEmpresa(
            @RequestBody CuentaEmpresaParaInsertarDTO cuentaEmpresaParaInsertarDTO){
        try{

            CuentaEmpresa cuentaEmpresa = cuentaEmpresaMapper.asEntity(cuentaEmpresaParaInsertarDTO);
            Optional<Banco> bancoOptional = bancoService.findById(cuentaEmpresaParaInsertarDTO.getIdBanco());
            bancoOptional.ifPresent(cuentaEmpresa::setBanco);

            CuentaEmpresa cuentaEmpresaSaved = cuentaEmpresaService.save(cuentaEmpresa);
            CuentaEmpresaDto cuentaEmpresaDto = cuentaEmpresaMapper.asCuentaEmpresaDTO(cuentaEmpresaSaved);
            
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(cuentaEmpresaDto);
        }
        catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo registrar la cuenta de empresa.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCuentaEmpresa(@PathVariable Long id){
        try{
            Optional<CuentaEmpresa> empresaOptional = cuentaEmpresaService.findById(id);
            if(!empresaOptional.isPresent()){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
            cuentaEmpresaService.deleteById(id);

            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
        catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la cuenta.");
        }
    }
}
