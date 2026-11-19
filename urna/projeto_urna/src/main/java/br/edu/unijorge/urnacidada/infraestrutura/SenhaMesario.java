package br.edu.unijorge.urnacidada.infraestrutura;

import br.edu.unijorge.urnacidada.dominio.modelo.Mesario;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Carrega a senha do mesário de fora do código, para ela não ir para o GitHub (RN18).
 * Ordem de busca: -Durna.senha=..., variável URNA_SENHA ou arquivo senha-mesario.txt
 * na pasta atual ou em até 3 pastas acima (o .gitignore bloqueia esse arquivo).
 */
public final class SenhaMesario {
    static final String ARQUIVO = "senha-mesario.txt";
    private static final int PASTAS_ACIMA = 3;
    private static Mesario mesario;

    private SenhaMesario() { }

    /** Mesário com a senha configurada. Lança IllegalStateException se a senha não foi configurada. */
    public static synchronized Mesario mesario() {
        if (mesario == null) {
            mesario = new Mesario(lerSenha());
        }
        return mesario;
    }

    private static String lerSenha() {
        Optional<String> informada = Configuracao.ler("urna.senha", "URNA_SENHA");
        if (informada.isPresent()) {
            return informada.get();
        }
        Path pasta = Path.of("").toAbsolutePath().normalize();
        for (int nivel = 0; nivel <= PASTAS_ACIMA && pasta != null; nivel++) {
            Path arquivo = pasta.resolve(ARQUIVO);
            if (Files.isRegularFile(arquivo)) {
                try {
                    String senha = Files.readString(arquivo, StandardCharsets.UTF_8).trim();
                    if (!senha.isEmpty()) {
                        return senha;
                    }
                } catch (IOException e) {
                    throw new IllegalStateException("Não foi possível ler " + arquivo.toAbsolutePath(), e);
                }
            }
            pasta = pasta.getParent();
        }
        throw new IllegalStateException("Senha do mesário não configurada. Crie o arquivo " + ARQUIVO
                + " na pasta raiz do projeto (veja docs/banco-de-dados.md).");
    }
}