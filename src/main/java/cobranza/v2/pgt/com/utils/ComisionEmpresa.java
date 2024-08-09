package cobranza.v2.pgt.com.utils;

import cobranza.v2.pgt.com.dto.*;
import cobranza.v2.pgt.com.models.entity.Comision;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class ComisionEmpresa {

    public static final int BOLIVIANOS = 1;
    public static final int DOLARES = 2;
    public static final String BOLIVIA = "BO";
    public static final String PAGO_QR = "PAGO QR";

    public List<ReciboDto> aplicarComisionesLiquidaciones(List<ReciboDto> listaRecibos, List<Comision> comisiones){
        listaRecibos.forEach(recibo -> {
            aplicarMontoComision(recibo, comisiones);
        });
        return listaRecibos;
    }

    private void aplicarMontoComision(ReciboDto recibo, List<Comision> comisiones) {
        aplicarMontoComisionATC(recibo, comisiones);
        aplicarMontoComisionQR(recibo, comisiones);
    }

    private void aplicarMontoComisionATC(ReciboDto recibo, List<Comision> comisiones) {
        DeudaDto deudaDto = recibo.getDeuda();
        PagoDto pagoDto = deudaDto.getPago();
        String formaPago = pagoDto.getDescripcion().substring(0, 7);
        if (esPagoQR(formaPago)) {
            return;
        }
        EmpresaDto empresaDto = deudaDto.getCliente().getEmpresa();
        Long idEmpresa = empresaDto.getIdempresa();
        Comision comision = obtenerComision(idEmpresa, comisiones);

        if (Objects.nonNull(comision)) {
            calcularComisionATC(recibo, comision);
        }
    }

    private void calcularComisionATC(ReciboDto recibo, Comision comision) {
        BigDecimal comisionTarjetaPGT = comision.getTarjetaNacPgt();
        BigDecimal comisionTarjetaATC = comision.getTarjetaNacAtc();
        BigDecimal comisionTarjetaPGTInt = comision.getTarjetaInterPgt();
        BigDecimal comisionTarjetaATCInt = comision.getTarjetaInterAtc();

        CyberSourceDto cyberSourceDto = recibo.getCyberSource()
                .stream()
                .findFirst()
                .orElse(null);
        if (!esCyberSourceDeBolivia(cyberSourceDto)) {
            comisionTarjetaPGT = comisionTarjetaPGTInt;
            comisionTarjetaATC = comisionTarjetaATCInt;
        }

        BigDecimal monto = recibo.getMonto();
        BigDecimal comisionPGT = monto.multiply(comisionTarjetaPGT);
        BigDecimal comisionATC = monto.multiply(comisionTarjetaATC);

        recibo.setMontoComisionPGT(comisionPGT.setScale(3, BigDecimal.ROUND_HALF_UP));
        recibo.setMontoComisionATC(comisionATC.setScale(3, BigDecimal.ROUND_HALF_UP));
        recibo.setMontoComision(comisionPGT.add(comisionATC).setScale(3, BigDecimal.ROUND_HALF_UP));

        BigDecimal montoComision = recibo.getMontoComision();
        recibo.setMontoTotal(monto.subtract(montoComision).setScale(3, BigDecimal.ROUND_HALF_UP));
    }

    private boolean esCyberSourceDeBolivia(CyberSourceDto cyberSourceDto) {
        return Objects.nonNull(cyberSourceDto) && cyberSourceDto.getReq_bill_to_address_country().equals(BOLIVIA);
    }

    private void aplicarMontoComisionQR(ReciboDto recibo, List<Comision> comisiones) {
        String formaPago = recibo.getDeuda().getPago().getDescripcion().substring(0, 7);
        if(!esPagoQR(formaPago)){
            return;
        }
        Long idEmpresa = recibo.getDeuda().getCliente().getEmpresa().getIdempresa();
        Comision comision = obtenerComision(idEmpresa, comisiones);
        if (Objects.nonNull(comision)) {
            calcularComisionQR(recibo, comision);
        }
    }

    private void calcularComisionQR(ReciboDto recibo, Comision comision) {
        BigDecimal comisionQRBNB = comision.getQrBanco();
        BigDecimal comisionQRPGT = comision.getQrPgt();

        BigDecimal monto = recibo.getMonto();
        BigDecimal comisionBNB = monto.multiply(comisionQRBNB);
        BigDecimal comisionPGT = monto.multiply(comisionQRPGT);

        recibo.setMontoComisionBNB(comisionBNB.setScale(3, BigDecimal.ROUND_HALF_UP));
        recibo.setMontoComisionPGT(comisionPGT.setScale(3, BigDecimal.ROUND_HALF_UP));
        recibo.setMontoComision(comisionPGT.add(comisionBNB).setScale(3, BigDecimal.ROUND_HALF_UP));

        BigDecimal montoComision = recibo.getMontoComision();
        recibo.setMontoTotal(monto.subtract(montoComision).setScale(3, BigDecimal.ROUND_HALF_UP));
    }

    private boolean esPagoQR(String formaPago) {
        return formaPago.equals(PAGO_QR);
    }

    private Comision obtenerComision(Long idEmpresa, List<Comision> comisiones) {
        Comision comision = comisiones
                .stream()
                .filter(element -> element.getIdempresa().equals(idEmpresa))
                .findFirst()
                .orElse(null);

        return comision;
    }
}
