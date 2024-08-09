package cobranza.v2.pgt.com.utils.otros;

import cobranza.v2.pgt.com.dto.CargaMasivaDetalleListadoDTO;
import cobranza.v2.pgt.com.dto.CargaMasivaListadoDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CargaMasivaRenderer {

    public String exportar(CargaMasivaListadoDTO dto){
        String cabecera = exportarCabecera(dto);
        String cuerpo = exportarCuerpo(dto);
        return cabecera + "\n" + cuerpo;
    }

    private String exportarCabecera(CargaMasivaListadoDTO dto) {
        StringBuffer cabecera = new StringBuffer();

        String numeroCliente = rellenarConEspacios(dto.getNumeroCliente().toString(), 10);
        cabecera.append(numeroCliente);

        String numeroCuenta = rellenarConEspacios(dto.getNumeroCuenta().toString(), 10);
        cabecera.append(numeroCuenta);

        String anioAbono = rellenarConEspacios(dto.getAnioAbono(), 4);
        cabecera.append(anioAbono);

        String mesAbono = rellenarConEspacios(dto.getMesAbono(), 2);
        cabecera.append(mesAbono);

        String diaAbono = rellenarConEspacios(dto.getDiaAbono(), 2);
        cabecera.append(diaAbono);

        String numeroTotal = rellenarConCeros(String.valueOf(dto.getDetalle().size()), 6);
        cabecera.append(numeroTotal);

        String montoTotalBs = calcularMontoBolivianos(dto.getDetalle());
        String montoBolivianos = rellenarConCeros(montoTotalBs, 13);
        cabecera.append(montoBolivianos);

        String montoTotalUSD = calcularMontoDolares(dto.getDetalle());
        String montoDolares = rellenarConCeros(montoTotalUSD, 13);
        cabecera.append(montoDolares);

        return cabecera.toString();
    }

    private String calcularMontoBolivianos(List<CargaMasivaDetalleListadoDTO> detalles) {
        return calcularMonto(detalles, "1");
    }

    private String calcularMontoDolares(List<CargaMasivaDetalleListadoDTO> detalles) {
        return calcularMonto(detalles, "2");
    }

    private String calcularMonto(List<CargaMasivaDetalleListadoDTO> detalles, String moneda) {
        int montoTotal = detalles.stream()
                .filter(detalle -> detalle.getMonedaAbono().equals(moneda))
                .mapToInt(detalle -> Integer.parseInt(detalle.getMonto()))
                .sum();
        return String.valueOf(montoTotal);
    }

    private String exportarCuerpo(CargaMasivaListadoDTO cargaMasivaListadoDTO) {
        StringBuffer cuerpo = new StringBuffer("");
        for (CargaMasivaDetalleListadoDTO dto: cargaMasivaListadoDTO.getDetalle()) {

            String tipoTransaccion = dto.getTipoTransaccion().trim();
            cuerpo.append(tipoTransaccion);

            String tipoDocumento = dto.getTipoDocumento().trim();
            cuerpo.append(tipoDocumento);

            String numeroDocumento = rellenarConEspacios(dto.getNumeroDocumento().trim(), 15);
            cuerpo.append(numeroDocumento);

            String nombreBeneficiario = rellenarConEspacios(dto.getNombreBeneficiario().trim(), 40);
            cuerpo.append(nombreBeneficiario);

            String periodoAbono = dto.getPeriodoAbono().trim();
            cuerpo.append(periodoAbono);

            String monedaAbono = dto.getMonedaAbono().trim();
            cuerpo.append(monedaAbono);

            String monto = rellenarConCeros(dto.getMonto().trim(), 13);
            cuerpo.append(monto);

            String banco = String.valueOf(dto.getBanco().getIdBanco());
            cuerpo.append(banco);

            String numeroCuenta = rellenarConEspacios(dto.getNumeroCuenta(), 20);
            cuerpo.append(numeroCuenta);

            String ceros = rellenarConCeros("", 17);//TODO campo fijo
            cuerpo.append(ceros);

            String referencia = rellenarConEspacios(dto.getReferencia().trim(), 15);
            cuerpo.append(referencia);

            String concepto = rellenarConEspacios(dto.getConcepto().trim(), 60);
            cuerpo.append(concepto);

            String filaFinal = "07"; //TODO campo fijo
            cuerpo.append(filaFinal);

            cuerpo.append("\n");
        }
        return cuerpo.toString();
    }

    private String rellenarConEspacios(String campo, int limite){
        int longitud = campo.trim().length();
        String espacio = " ";
        StringBuilder campoBuffer = new StringBuilder(campo.trim());

        for (int i = 0; i < limite - longitud; i++) {
            campoBuffer.append(espacio);
        }
        return campoBuffer.toString();
    }

    private String rellenarConCeros(String campo, int limite){
        int longitud = campo.trim().length();
        StringBuffer ceroBuffer = new StringBuffer("");
        String cero = "0";

        for (int i = 0; i < limite - longitud; i++) {
            ceroBuffer.append(cero);
        }

        ceroBuffer.append(campo.trim());

        return ceroBuffer.toString();
    }
}
