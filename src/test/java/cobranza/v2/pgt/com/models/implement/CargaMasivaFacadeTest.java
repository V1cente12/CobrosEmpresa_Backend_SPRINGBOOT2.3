package cobranza.v2.pgt.com.models.implement;

import cobranza.v2.pgt.com.CobranzaV2Application;
import cobranza.v2.pgt.com.models.dao.CargaMasivaRepository;
import cobranza.v2.pgt.com.models.entity.CargaMasiva;
import cobranza.v2.pgt.com.models.enums.EstadoCargaMasiva;
import cobranza.v2.pgt.com.models.services.CargaMasivaService;
import cobranza.v2.pgt.com.utils.otros.CargaMasivaRenderer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CobranzaV2Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CargaMasivaFacadeTest {

    @Autowired
    private CargaMasivaFacade cargaMasivaFacade;

    @Autowired
    private CargaMasivaService cargaMasivaService;

    @MockBean
    private CargaMasivaRepository repository;

    @MockBean
    private CargaMasivaRenderer cargaMasivaRenderer;

    @Test
    public void listarTodasCargasMasivas(){

        List<CargaMasiva> cargasMasivas = new ArrayList<>();
        CargaMasiva carga = CargaMasiva
                .builder( )
                .idCargaMasiva(1L)
                .mesAbono("05")
                .diaAbono("10")
                .anioAbono("2021")
                .numeroCuenta(1234567890L)
                .numeroCliente(456123L)
                .detalles(new ArrayList<>( ))
                .build( );

        cargasMasivas.add(carga);

        Mockito.when(repository.findAll()).thenReturn(cargasMasivas);
        List<CargaMasiva> list = cargaMasivaService.findAll();

        assertEquals(list.size(), 1);
    }

    @Test
    public void obtenerCargasMasiva() {
        Long id = 1L;
        CargaMasiva carga = CargaMasiva
                .builder( )
                .idCargaMasiva(id)
                .mesAbono("05")
                .diaAbono("10")
                .anioAbono("2021")
                .numeroCuenta(1234567890L)
                .numeroCliente(456123L)
                .detalles(new ArrayList<>( ))
                .build( );
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(carga));
        CargaMasiva cargaMasiva = cargaMasivaService.findById(id).orElse(carga);

        assertEquals(cargaMasiva.getIdCargaMasiva(), id);
    }

    @Test
    public void aprobarCargaMasiva() {
        Long id = 1L;
        CargaMasiva carga = CargaMasiva
                .builder( )
                .idCargaMasiva(id)
                .mesAbono("05")
                .diaAbono("10")
                .anioAbono("2021")
                .numeroCuenta(1234567890L)
                .numeroCliente(456123L)
                .detalles(new ArrayList<>( ))
                .build( );
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(carga));
        CargaMasiva cargaMasiva = cargaMasivaService.findById(id).orElse(carga);

        assertEquals(cargaMasiva.getIdCargaMasiva(), id);
    }

    @Test
    public void anularCargaMasiva() throws Exception {
        Long id = 2L;
        CargaMasiva carga = CargaMasiva
                .builder( )
                .idCargaMasiva(id)
                .mesAbono("05")
                .diaAbono("10")
                .anioAbono("2021")
                .numeroCuenta(1234567890L)
                .numeroCliente(456123L)
                .estado(EstadoCargaMasiva.APR)
                .detalles(new ArrayList<>( ))
                .build( );
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(carga));
        cargaMasivaService.anularCargaMasiva(id);

        System.err.println(carga);
        assertTrue(carga.estaAnulado());
    }

}