package cobranza.v2.pgt.com.utils;

import cobranza.v2.pgt.com.dto.CargaMasivaDetalleParaInsertarDTO;
import cobranza.v2.pgt.com.dto.CargaMasivaParaInsertarDTO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class ValidacionCargaMasiva {

    public static final Integer CODIGO_BNB = 1001;

    public Map<String, String> validarCargaMasiva(CargaMasivaParaInsertarDTO dto) throws Exception{
        Map<String, String> errors = new HashMap<>();

        if(!numeroClienteValido(dto.getNumeroCliente())){
            errors.put("numeroCliente","El número del cliente no es válido");
        }

        if(!numeroCuentaValida(dto.getNumeroCuenta())){
            errors.put("numeroCuenta","El número de cuenta para débito no es válido");
        }

        if(!anioAbonoValido(dto.getAnioAbono())){
            errors.put("anioAbono","El año de abono no es válido");
        }

        if(!mesAbonoValido(dto.getMesAbono())){
            errors.put("mesAbono","El mes de abono no es válido");
        }

        if(!diaAbonoValido(dto.getDiaAbono())){
            errors.put("diaAbono","El día de abono no es válido");
        }

        int i = 1;
        for (CargaMasivaDetalleParaInsertarDTO detalle: dto.getDetalle()) {
            if(!tipoTransaccionValida(detalle.getTipoTransaccion())){
                errors.put("tipoTransaccion"+i,"El tipo de transacción no es válido en el detalle " + i);
            }

            if(!tipoDocumentoValido(detalle.getTipoDocumento())){
                errors.put("tipoDocumento"+i,"El tipo de documento no es válido en el detalle " + i);
            }

            if(!numeroDocumentoValido(detalle.getNumeroDocumento())){
                errors.put("numeroDocumento"+i,"El número de documento no es válido en el detalle " + i);
            }

            if(!nombreBeneficiarioValido(detalle.getNombreBeneficiario())){
                errors.put("nombreBeneficiario"+i,"El nombre del beneficiario no es válido en el detalle " + i);
            }

            if(!periodoAbonoValido(detalle.getPeriodoAbono())){
                errors.put("periodoAbono"+i,"El periodo del abono no es válido en el detalle " + i);
            }
            if(!monedaAbonoValida(detalle.getMonedaAbono())){
                errors.put("monedaAbono"+i,"La moneda del abono no es válida en el detalle " + i);
            }

            if(!montoValido(detalle.getMonto())){
                errors.put("montoAbono"+i,"El monto del abono no es válido en el detalle " + i);
            }

            boolean esBNB = detalle.getIdBanco().equals(CODIGO_BNB);

            if(!numeroCuentaAbonoValido(detalle.getNumeroCuenta(), esBNB)){
                errors.put("numeroCuentaAbono"+i,"El número de cuenta del abono no es válido en el detalle " + i);
            }

            if(!referenciaValida(detalle.getReferencia())){
                errors.put("referencia"+i,"La referencia del abono no es válida en el detalle " + i);
            }

            if(!conceptoValido(detalle.getConcepto())){
                errors.put("concepto"+i,"El concepto del abono no es válido en el detalle " + i);
            }
            i++;
        }

        return errors;
    }

    public Boolean numeroClienteValido(Long numeroCliente) throws Exception{
        return longitudValida(numeroCliente.toString(), 10) &&
                numeroCliente > 0L;
    }

    public Boolean numeroCuentaValida(Long numeroCuenta) throws Exception{
        return longitudValida(numeroCuenta.toString(), 10) &&
                numeroCuenta > 0L;
    }

    public Boolean anioAbonoValido(String anioAbono) throws Exception{
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return Integer.parseInt(anioAbono) == year ||
                Integer.parseInt(anioAbono) == year + 1;
    }

    public Boolean mesAbonoValido(String mesAbono) throws Exception{
        return longitudValida(mesAbono, 2) &&
                Integer.parseInt(mesAbono) >= 1 &&
                Integer.parseInt(mesAbono) <= 12;
    }

    public Boolean diaAbonoValido(String diaAbono) throws Exception{
        return longitudValida(diaAbono, 2) &&
                Integer.parseInt(diaAbono) >= 1 &&
                Integer.parseInt(diaAbono) <= 31;
    }

    private Boolean longitudValida(String campo, int maximo) throws Exception{
        return longitudValida(campo, maximo, 1);
    }

    private Boolean longitudValida(String campo, int maximo, int minimo) throws Exception{
        if(campo.trim().length() <= maximo && campo.trim().length() >= minimo) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean esNumerico(String cadenaNumerica) {
        if (cadenaNumerica == null) {
            return Boolean.FALSE;
        }
        try {
            Double.parseDouble(cadenaNumerica);
        } catch (NumberFormatException nfe) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Boolean tipoTransaccionValida(String tipoTransaccion) throws Exception{
        String[] values = {"01","04"}; //BNB, Interbancaria
        return Arrays.asList(values).contains(tipoTransaccion);
    }

    public Boolean tipoDocumentoValido(String tipoDocumento) throws Exception{
        String[] values = {"01","02","09"}; //CI, RUN, NIT
        return Arrays.asList(values).contains(tipoDocumento);
    }

    public Boolean numeroDocumentoValido(String numeroDocumento) throws Exception{
        return longitudValida(numeroDocumento, 15);
    }

    public Boolean nombreBeneficiarioValido(String nombreBeneficiario) throws Exception{
        return longitudValida(nombreBeneficiario, 40);
    }

    public Boolean periodoAbonoValido(String periodoAbono) throws Exception{
        return longitudValida(periodoAbono, 6, 6) &&
                esNumerico(periodoAbono);
    }

    public Boolean monedaAbonoValida(String monedaAbono) throws Exception{
        String[] values = {"1","2"}; //Bs, $us
        return Arrays.asList(values).contains(monedaAbono);
    }

    public Boolean montoValido(String monto) throws Exception{
        return longitudValida(monto, 13) &&
                esNumerico(monto) &&
                Integer.parseInt(monto) > 0;
    }

    public Boolean numeroCuentaAbonoValido(String numeroCuenta, boolean esBNB) throws Exception{
        int maximo = esBNB ? 10 : 15;
        int minimo = esBNB ? 10 : 8;
        return longitudValida(numeroCuenta, maximo, minimo) &&
                esNumerico(numeroCuenta);
    }

    public Boolean referenciaValida(String referencia) throws Exception{
        return longitudValida(referencia, 15);
    }

    public Boolean conceptoValido(String concepto) throws Exception{
        return longitudValida(concepto, 60);
    }
}
