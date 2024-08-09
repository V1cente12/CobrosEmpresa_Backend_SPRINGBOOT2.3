package cobranza.v2.pgt.com.controllers.swagger;

import cobranza.v2.pgt.com.CobranzaV2Application;
import cobranza.v2.pgt.com.mapper.BancoMapper;
import cobranza.v2.pgt.com.models.dao.BancoRepository;
import cobranza.v2.pgt.com.models.entity.Banco;
import cobranza.v2.pgt.com.models.services.BancoService;
import org.junit.Before;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CobranzaV2Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BancoTest {

    @Autowired
    private BancoService bancoService;

    @MockBean
    private BancoRepository repository;

    Banco banco = Banco.builder()
            .idBanco(1001)
            .activo(true)
            .nombre("Banco Nacional de Bolivia")
            .build();

    Optional<Banco> bancoOptional = Optional.of(banco);

    List<Banco> bancos = new ArrayList<>();

    @Before
    public void setup() {
        bancos.add(banco);
    }

    @Test
    public void listarBancosTest() {

        Mockito.when(repository.findAll())
                .thenReturn(bancos);

        List<Banco> some = bancoService.findAll();
        System.err.println(some);

        assertEquals(1, some.size());
    }

    @Test
    public void obtenerBancoTest() {

        Integer id = 1001;

        Mockito.when(repository.findById(id))
                .thenReturn(bancoOptional);

        Optional<Banco> one = bancoService.findById(id);
        System.err.println(one);

        assertEquals(banco, one.orElse(null));
    }
}