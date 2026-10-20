package br.edu.unijorge.urnacidada.dominio.modelo;

final class Validacao {
    private Validacao() { }

    static String textoObrigatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " deve ser informado.");
        }
        return valor;
    }

    static <T> T obrigatorio(T valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException(campo + " deve ser informado.");
        }
        return valor;
    }
}
