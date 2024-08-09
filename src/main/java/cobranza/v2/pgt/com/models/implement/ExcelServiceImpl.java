package cobranza.v2.pgt.com.models.implement;

import cobranza.v2.pgt.com.dto.CargaMasivaParaReporteDTO;
import cobranza.v2.pgt.com.mapper.CargaMasivaMapper;
import cobranza.v2.pgt.com.models.dao.CargaMasivaRepository;
import cobranza.v2.pgt.com.models.entity.CargaMasiva;
import cobranza.v2.pgt.com.models.enums.EstadoCargaMasiva;
import cobranza.v2.pgt.com.models.services.ExcelService;
import cobranza.v2.pgt.com.utils.helper.ExcelHelper;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ExcelServiceImpl implements ExcelService {

    private final CargaMasivaRepository cargaMasivaRepository;
    private final CargaMasivaMapper cargaMasivaMapper;
    private final ExcelHelper excelHelper;

    public ExcelServiceImpl(CargaMasivaRepository cargaMasivaRepository,
                            CargaMasivaMapper cargaMasivaMapper,
                            ExcelHelper excelHelper) {
        this.cargaMasivaRepository = cargaMasivaRepository;
        this.cargaMasivaMapper = cargaMasivaMapper;
        this.excelHelper = excelHelper;
    }

    @Override
    public byte[] generarExcelByteArray(EstadoCargaMasiva estado, Date fechaInicial, Date fechaFinal) {

        Date nuevaFechaFinal = finalDeFecha(fechaFinal);

        List<CargaMasiva> cargasMasivas;
        if(Objects.isNull(estado))
        {
            cargasMasivas = cargaMasivaRepository.findAllByFechaAltaBetweenOrderByIdCargaMasiva(
                    fechaInicial,
                    nuevaFechaFinal);
        }else {
            cargasMasivas = cargaMasivaRepository.findAllByEstadoAndFechaAltaBetweenOrderByIdCargaMasiva(
                    estado,
                    fechaInicial,
                    nuevaFechaFinal);
        }

        List<CargaMasivaParaReporteDTO> dtos =
                cargaMasivaMapper.fromEntityToCargaMasivaParaReporteDTOList(cargasMasivas);

        byte[] resource = excelHelper.cargasMasivasToExcel(dtos);
        return resource;
    }

    private Date finalDeFecha(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
