package cobranza.v2.pgt.com.utils;

import cobranza.v2.pgt.com.controllers.swagger.BancoController;
import cobranza.v2.pgt.com.dto.CargaMasivaDetalleParaInsertarDTO;
import cobranza.v2.pgt.com.dto.CargaMasivaParaInsertarDTO;
import cobranza.v2.pgt.com.models.services.BancoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ValidacionCargaMasivaTest {

    ValidacionCargaMasiva validacion = new ValidacionCargaMasiva();
    CargaMasivaParaInsertarDTO dto;

    @BeforeEach
    void setUp() {
        List<CargaMasivaDetalleParaInsertarDTO> detalles = new ArrayList<>();

        CargaMasivaDetalleParaInsertarDTO detalle1 = CargaMasivaDetalleParaInsertarDTO.builder()
                .idBanco(1001)
                .tipoTransaccion("01")
                .tipoDocumento("01")
                .numeroDocumento("1234567-1B")
                .nombreBeneficiario("Juan Felipe Perales")
                .numeroCuenta("1234567890")
                .monedaAbono("1")
                .monto("123400")
                .periodoAbono("202201")
                .referencia("ref-001")
                .concepto("1234 bs al banco BNB")
                .build();
        CargaMasivaDetalleParaInsertarDTO detalle2 = CargaMasivaDetalleParaInsertarDTO.builder()
                .idBanco(1003)
                .tipoTransaccion("04")
                .tipoDocumento("01")
                .numeroDocumento("1234567-1B")
                .nombreBeneficiario("Marcelo Linares")
                .numeroCuenta("12345678")
                .monedaAbono("1")
                .monto("41200")
                .periodoAbono("202201")
                .referencia("ref-001")
                .concepto("412 bs al banco Mercantil")
                .build();

        detalles.add(detalle1);
        detalles.add(detalle2);

        dto = CargaMasivaParaInsertarDTO.builder()
                .numeroCliente(1236547890L)
                .numeroCuenta(1236547890L)
                .anioAbono("2022")
                .mesAbono("01")
                .diaAbono("12")
                .detalle(detalles)
                .build();
    }

    @Test
    public void validarCargaMasiva() throws Exception {
        Map<String, String> validarCargaMasiva = validacion.validarCargaMasiva(dto);
        assertEquals(0, validarCargaMasiva.size());
    }

    @Test
    public void numeroClienteValido() throws Exception {
        Long numeroCliente = 1236547890L;
        Boolean numeroClienteValido = validacion.numeroClienteValido(numeroCliente);
        assertTrue(numeroClienteValido);
    }

    @Test
    public void numeroCuentaValida() throws Exception {
        Long numeroCuenta = 1236547890L;
        Boolean numeroCuentaValida = validacion.numeroCuentaValida(numeroCuenta);
        assertTrue(numeroCuentaValida);
    }

    @Test
    public void anioAbonoValido() throws Exception {
        String anioAbono = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        Boolean anioAbonoValido = validacion.anioAbonoValido(anioAbono);
        assertTrue(anioAbonoValido);
    }

    @Test
    public void mesAbonoValido() throws Exception {
        String mesAbono = "12";
        Boolean mesAbonoValido = validacion.mesAbonoValido(mesAbono);
        assertTrue(mesAbonoValido);
    }

    @Test
    public void diaAbonoValido() throws Exception{
        String diaAbono = "31";
        Boolean diaAbonoValido = validacion.diaAbonoValido(diaAbono);
        assertTrue(diaAbonoValido);
    }

    @Test
    public void esNumerico() {
        String numero = "123.456";
        Boolean esNumerico = validacion.esNumerico(numero);
        assertTrue(esNumerico);
    }

    @Test
    public void tipoTransaccionValida() throws Exception {
        String tipoTransaccion = "04";
        Boolean tipoTransaccionValida = validacion.tipoTransaccionValida(tipoTransaccion);
        assertTrue(tipoTransaccionValida);
    }

    @Test
    public void tipoDocumentoValido() throws Exception {
        String tipoDocumento = "09";
        Boolean tipoDocumentoValido = validacion.tipoDocumentoValido(tipoDocumento);
        assertTrue(tipoDocumentoValido);
    }

    @Test
    public void numeroDocumentoValido() throws Exception {
        String numeroDocumento = "1234567-1B";
        Boolean numeroDocumentoValido = validacion.numeroDocumentoValido(numeroDocumento);
        assertTrue(numeroDocumentoValido);
    }

    @Test
    public void nombreBeneficiarioValido() throws Exception {
        String nombreBeneficiario = "Francisco Pizarro Valverde";
        Boolean nombreBeneficiarioValido = validacion.nombreBeneficiarioValido(nombreBeneficiario);
        assertTrue(nombreBeneficiarioValido);
    }

    @Test
    public void periodoAbonoValido() throws Exception {
        String periodoAbono = "202201";
        Boolean periodoAbonoValido = validacion.periodoAbonoValido(periodoAbono);
        assertTrue(periodoAbonoValido);
    }

    @Test
    public void monedaAbonoValida() throws Exception {
        String monedaAbono = "2";
        Boolean monedaAbonoValida = validacion.monedaAbonoValida(monedaAbono);
        assertTrue(monedaAbonoValida);
    }

    @Test
    public void montoValido() throws Exception {
        String monto = "32165400";
        Boolean montoValido = validacion.montoValido(monto);
        assertTrue(montoValido);
    }

    @Test
    public void numeroCuentaAbonoValidoEsBNB() throws Exception {
        String numeroCuenta = "1234567890";
        Boolean numeroCuentaValida = validacion.numeroCuentaAbonoValido(numeroCuenta, true);
        assertTrue(numeroCuentaValida);
    }

    @Test
    public void numeroCuentaAbonoValidoNoEsBNB() throws Exception {
        String numeroCuenta = "12345678";
        Boolean numeroCuentaValida = validacion.numeroCuentaAbonoValido(numeroCuenta, false);
        assertTrue(numeroCuentaValida);
    }

    @Test
    public void referenciaValida() throws Exception {
        String referencia = "PAGO-BONO-001";
        Boolean referenciaValida = validacion.referenciaValida(referencia);
        assertTrue(referenciaValida);
    }

    @Test
    public void conceptoValido() throws Exception {
        String concepto = "Pago de 100 Bs al banco BNB";
        Boolean conceptoValido = validacion.conceptoValido(concepto);
        assertTrue(conceptoValido);
    }
}