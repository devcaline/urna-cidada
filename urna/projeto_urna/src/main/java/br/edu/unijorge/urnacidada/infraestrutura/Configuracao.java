package br.edu.unijorge.urnacidada.infraestrutura;

import java.util.Optional;

/** Lê configurações de -Dpropriedade=valor (prioridade) ou de variável de ambiente. */
final class Configuracao {
    private Configuracao() { }

    static Optional<String> ler(String propriedade, String variavelAmbiente) {
        String valor = propriedade == null ? null : System.getProperty(propriedade);
        if (valor == null || valor.isBlank()) {
            valor = System.getenv(variavelAmbiente);
        }
        return valor == null || valor.isBlank() ? Optional.empty() : Optional.of(valor.trim());
    }
}
