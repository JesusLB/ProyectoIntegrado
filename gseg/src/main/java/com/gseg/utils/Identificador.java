package com.gseg.utils;

/**
 * Clase que calcula o valida un documento de identificación. (DNI,NIE,CIF).
 * @author Jesús López Barragán
 */
public abstract class Identificador {

    private final static String LETRAS_NIF = "TRWAGMYFPDXBNJZSQVHLCKE";
    private final static String LETRAS_CIF = "ABCDEFGHJKLMNPQRSUVW";
    private final static String LETRAS_NIE = "XYZ";
    private final static String DIGITO_CONTROL_CIF = "JABCDEFGHI";
    private final static String CIF_LETRA = "KPQRSNW";

    /**
     * Calcula el dígito o letra de control.
     *
     * @param nif documento a calcular
     * @return devuelve el documento con el dígito o letra de control calculado.
     */
    public static String calcular(String nif) {
        nif = nif.toUpperCase();
        String a = nif.substring(0, 1);
        String nifCalculado = "";

        if (LETRAS_CIF.contains(a)) {
            nifCalculado = calculaCif(nif);
        } else if (LETRAS_NIE.contains(a)) {
        	nifCalculado = calculaNie(nif);
        } else {
        	nifCalculado = calculaDni(nif);
        }
        return nifCalculado;
    }

    /**
     * Valida un documento de identificación.
     *
     * @param nif documento a validar
     * @return true si es válido, false si no lo es.
     */
    public static boolean isValido(String nif) {
        nif = nif.toUpperCase();
        String a = nif.substring(0, 1);
        boolean esValido;        

        if (LETRAS_CIF.contains(a)) {
            esValido = isCifValido(nif);
        } else if (LETRAS_NIE.contains(a)) {
            esValido = isNieValido(nif);
        } else {
            esValido = isDniValido(nif);
        }
        return esValido;
    }

    private static String calculaDni(String dni) {
        String str = completaCeros(dni, 8);
        
        if(str.length()==9){
            str=str.substring(0,dni.length()-1);
        }
        return str + calculaLetra(str);
    }

    private static String calculaNie(String nie) {
        String str = null;
        
        if(nie.length()==9){
            nie=nie.substring(0, nie.length()-1);
        }

        if (nie.startsWith("X")) {
            str = nie.replace('X', '0');
        } else if (nie.startsWith("Y")) {
            str = nie.replace('Y', '1');
        } else if (nie.startsWith("Z")) {
            str = nie.replace('Z', '2');
        }

        return nie + calculaLetra(str);
    }

    private static String calculaCif(String cif) {
        return cif + calculaDigitoControl(cif);
    }

    private static int posicionImpar(String str) {
        int aux = Integer.parseInt(str);
        aux = aux * 2;
        aux = (aux / 10) + (aux % 10);

        return aux;
    }

    private static boolean isDniValido(String dni) {
        String aux = dni.substring(0, 8);
        aux = calculaDni(aux);

        return dni.equals(aux);
    }

    private static boolean isNieValido(String nie) {
        String aux = nie.substring(0, 8);
        aux = calculaNie(aux);

        return nie.equals(aux);
    }

    private static boolean isCifValido(String cif) {
        String aux = cif.substring(0, 8);
        aux = calculaCif(aux);

        return cif.equals(aux);
    }

    private static char calculaLetra(String aux) {
        return LETRAS_NIF.charAt(Integer.parseInt(aux) % 23);
    }

    private static String calculaDigitoControl(String cif) {
        String str = cif.substring(1, 8);
        String cabecera = cif.substring(0, 1);
        int sumaPar = 0;
        int sumaImpar = 0;
        int sumaTotal;

        for (int i = 1; i < str.length(); i += 2) {
            int aux = Integer.parseInt("" + str.charAt(i));
            sumaPar += aux;
        }

        for (int i = 0; i < str.length(); i += 2) {
            sumaImpar += posicionImpar("" + str.charAt(i));
        }

        sumaTotal = sumaPar + sumaImpar;
        sumaTotal = 10 - (sumaTotal % 10);
        
        if(sumaTotal==10){
            sumaTotal=0;
        }

        if (CIF_LETRA.contains(cabecera)) {
            str = "" + DIGITO_CONTROL_CIF.charAt(sumaTotal);
        } else {
            str = "" + sumaTotal;
        }

        return str;
    }

    private static String completaCeros(String str, int num) {
        while (str.length() < num) {
            str = "0" + str;
        }
        return str;
    }
}