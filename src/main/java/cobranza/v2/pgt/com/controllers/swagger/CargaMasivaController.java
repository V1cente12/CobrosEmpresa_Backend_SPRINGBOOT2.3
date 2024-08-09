package cobranza.v2.pgt.com.controllers.swagger;

import cobranza.v2.pgt.com.dto.*;
import cobranza.v2.pgt.com.models.entity.CargaMasiva;
import cobranza.v2.pgt.com.models.enums.EstadoCargaMasiva;
import cobranza.v2.pgt.com.models.implement.CargaMasivaFacade;
import cobranza.v2.pgt.com.utils.otros.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.jsoup.Jsoup;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/carga-masiva")
public class CargaMasivaController {
    public static Log log = LogFactory.getLog(CargaMasivaController.class);

    private final CargaMasivaFacade cargaMasivaFacade;

    public CargaMasivaController(CargaMasivaFacade cargaMasivaFacade) {
        this.cargaMasivaFacade = cargaMasivaFacade;
    }

    @GetMapping("/")
    public ResponseEntity<List<CargaMasivaListadoDTO>> listarCargasMasivas(){
        try{
            List<CargaMasivaListadoDTO> todos = cargaMasivaFacade.listarTodasCargasMasivas();
            if(todos.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(todos);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCargaMasiva(@PathVariable Long id){
        try {
            CargaMasivaListadoDTO dto = cargaMasivaFacade.obtenerCargasMasiva(id);
            if (Objects.isNull(dto)) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("No se encontró ningún elemento.");
            }
            return ResponseEntity.ok(dto);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se encontró la carga masiva. \n" + e.getMessage());
        }
    }

    //@GetMapping("/page")
    public Page<CargaMasivaListadoDTO> listarPaginado(
            @RequestParam() EstadoCargaMasiva estado,
            @RequestParam(defaultValue = "0", name = "page") String page,
            @RequestParam(defaultValue = "10", name = "size") String size,
            @RequestParam(defaultValue = "idCargaMasiva,desc") String[] sortBy) throws Exception
    {
        try {
            Sort.Direction direction =
                    sortBy[1].trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

            Sort.Order order = new Sort.Order(direction, sortBy[0]);

            PageRequest paging =
                    PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), Sort.by(order));

            Page<CargaMasivaListadoDTO> dtoPage =
                    cargaMasivaFacade.paginarCargasMasivas(estado, paging);
            return dtoPage;
        }
        catch (Exception e){
            log.error(e.getMessage());
            return Page.empty();
        }
    }

    @GetMapping("/page")
    public Page<CargaMasivaListadoDTO> listarCargasMasivasPaginados(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "estado") EstadoCargaMasiva estado,
            @RequestParam(defaultValue = "0", name = "page") String page,
            @RequestParam(defaultValue = "10", name = "size") String size,
            @RequestParam(defaultValue = "idCargaMasiva,desc") String[] sortBy)
    {
        try {
            search = Objects.isNull(search) ? "" : search.toLowerCase();

            String safeSearch = Jsoup.clean(
                    StringEscapeUtils.escapeHtml4(search),
                    Safelist.basic());

            PageRequest paging = getPageRequest(search, page, size, sortBy);

            Page<CargaMasivaListadoDTO> dtoPage =
                    cargaMasivaFacade.listarCargaMasivas(safeSearch, estado, paging);
            return dtoPage;
        }
        catch (Exception e){
            log.error(e.getMessage());
            return Page.empty();
        }
    }

    private PageRequest getPageRequest(@RequestParam(value = "search", required = false) String search,
                                       @RequestParam(defaultValue = "0", name = "page") String page,
                                       @RequestParam(defaultValue = "10", name = "size") String size,
                                       @RequestParam(defaultValue = "idCargaMasiva,desc") String[] sortBy) {
        Sort.Direction direction =
                sortBy[1].trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        String sortField = search.isEmpty() ? sortBy[0] : StringUtils.camelCaseToUnderscore(sortBy[0]);;

        Sort.Order order = new Sort.Order(direction, sortField);

        return PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), Sort.by(order));
    }

    @PostMapping("/")
    public ResponseEntity<?> registrarCargaMasiva(
            @RequestBody CargaMasivaParaInsertarDTO cargaMasivaDTO){
        try{
            Map<String, String> errors = cargaMasivaFacade.validarCargaMasiva(cargaMasivaDTO);

            if(!errors.isEmpty()){
                return ResponseEntity
                        .badRequest()
                        .body(errors);
            }

            CargaMasiva cargaMasiva = cargaMasivaFacade.guardarCargaMasiva(cargaMasivaDTO);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(cargaMasiva);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo registrar la carga masiva. \n" + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCargaMasiva(@PathVariable("id") Long id,
                                                        @RequestBody CargaMasivaParaActualizarDTO dto){
        try {
            if(cargaMasivaFacade.existeCargaMasiva(id)){
                cargaMasivaFacade.actualizarCargaMasiva(id, dto);

                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body("Actualizado correctamente");
            }
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se encontró la carga masiva.");
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo actualizar la carga masiva. \n" + e.getMessage());
        }
    }

    @PutMapping("/detalle/{id}")
    public ResponseEntity<?> actualizarCargaMasivaDetalle(@PathVariable("id") Long id,
                                                        @RequestBody CargaMasivaDetalleDTO dto){
        try {
            cargaMasivaFacade.actualizarCargaMasivadetalle(id, dto);
            return ResponseEntity.ok("Actualizado correctamente");
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/anular-detalle/{id}")
    public ResponseEntity<?> anularCargaMasivaDetalle(@PathVariable("id") Long id){
        try {
            cargaMasivaFacade.anularDetalleCargaMasiva(id);
            return ResponseEntity.ok("Detalle anulado!");
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo anular el detalle de la carga masiva. \n" + e.getMessage());
        }
    }

    @PatchMapping("/aprobar/{id}")
    public ResponseEntity<?> aprobarCargaMasiva(@PathVariable("id") Long id){
        try {
            cargaMasivaFacade.aprobarCargaMasiva(id);
            return ResponseEntity.ok("Aprobado correctamente!");
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo aprobar la carga masiva. \n" + e.getMessage());
        }
    }

    @PatchMapping("/anular/{id}")
    public ResponseEntity<?> anularCargaMasiva(@PathVariable("id") Long id){
        try {
            cargaMasivaFacade.anularCargaMasiva(id);
            return ResponseEntity.ok("Anulado correctamente!");
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("No se pudo anular la carga masiva. \n" + e.getMessage());
        }
    }
}
