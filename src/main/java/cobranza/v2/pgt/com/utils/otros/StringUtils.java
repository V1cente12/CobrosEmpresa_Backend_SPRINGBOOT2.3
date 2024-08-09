package cobranza.v2.pgt.com.utils.otros;

import com.google.common.base.CaseFormat;

public class StringUtils {

    public static String camelCaseToUnderscore(String field){
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field);
    }

    public static void main(String[] args) {
        System.err.println(StringUtils.camelCaseToUnderscore("IdCargaMasiva"));
        System.err.println(StringUtils.camelCaseToUnderscore("idCargaMasivaDetalle"));
        System.err.println(StringUtils.camelCaseToUnderscore("numeroCliente"));
    }
}
