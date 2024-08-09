package cobranza.v2.pgt.com.models.implement;

import cobranza.v2.pgt.com.dto.*;
import cobranza.v2.pgt.com.mapper.CargaMasivaDetalleMapper;
import cobranza.v2.pgt.com.mapper.CargaMasivaMapper;
import cobranza.v2.pgt.com.models.entity.CargaMasiva;
import cobranza.v2.pgt.com.models.entity.CargaMasivaDetalle;
import cobranza.v2.pgt.com.models.enums.EstadoCargaMasiva;
import cobranza.v2.pgt.com.models.enums.EstadoCargaMasivaDetalle;
import cobranza.v2.pgt.com.models.services.CargaMasivaDetalleService;
import cobranza.v2.pgt.com.models.services.CargaMasivaService;
import cobranza.v2.pgt.com.utils.ValidacionCargaMasiva;
import cobranza.v2.pgt.com.utils.otros.CargaMasivaRenderer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Transactional
public class CargaMasivaFacade {
    public static Log log = LogFactory.getLog(CargaMasivaFacade.class);

    private CargaMasivaService cargaMasivaService;
    private CargaMasivaDetalleService cargaMasivaDetalleService;
    private CargaMasivaMapper cargaMasivaMapper;
    private CargaMasivaDetalleMapper cargaMasivaDetalleMapper;
    private ValidacionCargaMasiva validacionCargaMasiva;
    private CargaMasivaRenderer cargaMasivaRenderer;

    public CargaMasivaFacade(CargaMasivaService cargaMasivaService,
                             CargaMasivaDetalleService cargaMasivaDetalleService,
                             CargaMasivaMapper cargaMasivaMapper,
                             CargaMasivaDetalleMapper cargaMasivaDetalleMapper,
                             ValidacionCargaMasiva validacionCargaMasiva,
                             CargaMasivaRenderer cargaMasivaRenderer) {
        this.cargaMasivaService = cargaMasivaService;
        this.cargaMasivaDetalleService = cargaMasivaDetalleService;
        this.cargaMasivaMapper = cargaMasivaMapper;
        this.cargaMasivaDetalleMapper = cargaMasivaDetalleMapper;
        this.validacionCargaMasiva = validacionCargaMasiva;
        this.cargaMasivaRenderer = cargaMasivaRenderer;
    }

    public List<CargaMasivaListadoDTO> listarTodasCargasMasivas(){
        List<CargaMasiva> all = cargaMasivaService.findAll();
        List<CargaMasivaListadoDTO> dtos = cargaMasivaMapper.fromEntityToCargaMasivaListadoDTOList(all);
        return dtos;
    }

    public CargaMasivaListadoDTO obtenerCargasMasiva(Long idCargaMasiva){
        Optional<CargaMasiva> entity = cargaMasivaService.findById(idCargaMasiva);

        if(entity.isPresent()){
            return cargaMasivaMapper.fromEntityToCargaMasivaListadoDTO(entity.get());
        }
        return null;
    }

    public Page<CargaMasivaListadoDTO> paginarCargasMasivas(EstadoCargaMasiva estado, Pageable pageable) {
        Page<CargaMasiva> page;
        if(Objects.isNull(estado)){
            page = cargaMasivaService.findAll(pageable);
        }
        else{
            page = cargaMasivaService.findByEstado(estado, pageable);
        }
        Page<CargaMasivaListadoDTO> pageDTO;
        pageDTO = cargaMasivaMapper.fromEntityPagetoListadoPage(page);
        return pageDTO;
    }

    public CargaMasiva guardarCargaMasiva(CargaMasivaParaInsertarDTO dto) throws Exception{

        CargaMasiva cargaMasiva = cargaMasivaMapper.fromInsertarDTOToEntity(dto);
        cargaMasiva.setExportado(Boolean.FALSE);
        cargaMasiva.setEstado(EstadoCargaMasiva.BOR);

        CargaMasiva cargaMasivaSaved = cargaMasivaService.save(cargaMasiva);

        if(Objects.isNull(cargaMasivaSaved)){
            return null;
        }

        List<CargaMasivaDetalle> cargaMasivaDetalles
                = cargaMasivaDetalleMapper.fromInsertarDTOListToEntityList(dto);

        List<CargaMasivaDetalle> detalles
                = cargarCargaMasivaAlDetalle(cargaMasivaSaved, cargaMasivaDetalles);

        cargaMasivaDetalleService.save(detalles);

        return cargaMasivaSaved;
    }

    public boolean existeCargaMasiva(Long idCargaMasiva){
        Optional<CargaMasiva> cargaOptional = cargaMasivaService.findById(idCargaMasiva);

        return cargaOptional.isPresent();
    }

    public void actualizarCargaMasiva(Long id, CargaMasivaParaActualizarDTO cargaDTO) throws Exception{
        Optional<CargaMasiva> cargaOptional = cargaMasivaService.findById(id);

        if(cargaOptional.isPresent()) {
            CargaMasiva entity = cargaMasivaMapper.fromActualizarDTOToEntity(cargaDTO, cargaOptional.get());
            entity.setFechaModificacion(new Date());

            cargaMasivaService.update(entity, id);

            actualizarDetallesCargaMasiva(cargaDTO.getDetalles());
            return;
        }
        throw new Exception("No se encontró la carga casiva.");
    }

    private void actualizarDetallesCargaMasiva(List<CargaMasivaDetalleDTO> detalles) throws Exception{
        AtomicReference<String> error = new AtomicReference<>("");

        detalles.forEach(dto -> {
            try {
                if(Objects.isNull(dto.getIdCargaMasivaDetalle())){
                    guardarDetalle(dto);
                }else{
                    actualizarUnDetalle(dto);
                }
            } catch (Exception e) {
                error.set(e.getMessage());
                e.printStackTrace();
            }
        });

        if(!error.get().equals("")){
            throw new Exception(error.get());
        }
    }

    private CargaMasivaDetalle guardarDetalle(CargaMasivaDetalleDTO dto) {
        CargaMasivaDetalle detalle = cargaMasivaDetalleMapper.asEntity(dto);
        detalle.setEstado(EstadoCargaMasivaDetalle.ACT);

        CargaMasivaDetalle detalleSaved = cargaMasivaDetalleService.save(detalle);
        return detalleSaved;

    }

    private List<CargaMasivaDetalle> cargarCargaMasivaAlDetalle(CargaMasiva cargaMasiva, List<CargaMasivaDetalle> cargaMasivaDetalles) {
        for (CargaMasivaDetalle detalle: cargaMasivaDetalles) {
            detalle.setCargaMasiva(cargaMasiva);
            detalle.setEstado(EstadoCargaMasivaDetalle.ACT);
        }
        return cargaMasivaDetalles;
    }

    public Map<String, String> validarCargaMasiva(CargaMasivaParaInsertarDTO dto) throws Exception{
        return validacionCargaMasiva.validarCargaMasiva(dto);
    }

    public void aprobarCargaMasiva(Long id) throws Exception{
        cargaMasivaService.aprobarCargaMasiva(id);
    }

    public void anularCargaMasiva(Long id) throws Exception{
        cargaMasivaService.anularCargaMasiva(id);
    }

    public void anularDetalleCargaMasiva(Long id) throws Exception{
        cargaMasivaDetalleService.anularDetalleCargaMasiva(id);
    }

    public String exportarCargaMasiva(Long idCargaMasiva)throws Exception{
        Optional<CargaMasiva> optional = cargaMasivaService.findById(idCargaMasiva);

        CargaMasiva entity = optional.orElse(null);

        assert entity != null;
        if(!entity.estaAprobado()){
            throw new Exception("La carga masiva no esta activa.");
        }

        CargaMasivaListadoDTO dto = cargaMasivaMapper.fromEntityToCargaMasivaListadoDTO(entity);

        return cargaMasivaRenderer.exportar(dto);
    }

    public void marcarCargaMasivaExportado(Long idCargaMasiva) throws Exception {
        cargaMasivaService.marcarCargaMasivaExportado(idCargaMasiva);
    }

    public void actualizarCargaMasivadetalle(Long id, CargaMasivaDetalleDTO dto) throws Exception{
        if(Objects.isNull(dto.getIdCargaMasivaDetalle())){
            dto.setIdCargaMasivaDetalle(id);
        }
        actualizarUnDetalle(dto);
    }

    private void actualizarUnDetalle(CargaMasivaDetalleDTO dto) throws Exception{
        Optional<CargaMasivaDetalle> cargaDetalle = cargaMasivaDetalleService.findById(dto.getIdCargaMasivaDetalle());

        if (cargaDetalle.isPresent()) {
            CargaMasivaDetalle detalle = cargaMasivaDetalleMapper.asEntity(dto, cargaDetalle.get());
            detalle.setFechaModificacion(new Date());

            cargaMasivaDetalleService.update(detalle, detalle.getIdCargaMasivaDetalle());
            return;
        }
        throw new Exception("No se encontró el detalle de la carga masiva.");
    }

    public Page<CargaMasivaListadoDTO> listarCargaMasivas(String search, EstadoCargaMasiva estado, PageRequest pageable) {
        Page<CargaMasiva> page;
        if(search.isEmpty()){

            page = cargaMasivaService.findByEstado(estado, pageable);
        }
        else{
            page = cargaMasivaService.find(search, estado, pageable);
        }
        Page<CargaMasivaListadoDTO> pageDTO = cargaMasivaMapper.fromEntityPagetoListadoPage(page);
        return pageDTO;
    }
}

