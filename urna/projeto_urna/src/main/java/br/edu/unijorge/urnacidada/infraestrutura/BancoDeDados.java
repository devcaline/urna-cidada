package br.edu.unijorge.urnacidada.infraestrutura;

import br.edu.unijorge.urnacidada.dados.CatalogoCandidatos;
import br.edu.unijorge.urnacidada.repositorio.ErroRepositorioException;
import br.edu.unijorge.urnacidada.repositorio.RepositorioVotos;
import com.google.cloud.firestore.Firestore;

import java.util.concurrent.ExecutionException;

/**
 * Ponto único de acesso ao banco de dados (Firebase Cloud Firestore).
 * As telas chamam {@link #votos()} e recebem um {@link RepositorioVotos}, sem depender do Firebase diretamente.
 * <p>
 * Não existe armazenamento alternativo: sem a chave do Firebase ou sem acesso ao Firestore,
 * {@link #votos()} lança {@link ErroRepositorioException} e a votação não começa.
 */
public final class BancoDeDados {
    /** Sessão usada quando nada é configurado. Troque com -Durna.sessao=... ou a variável URNA_SESSAO. */
    public static final String SESSAO_PADRAO = "oficina-2026";

    private static RepositorioVotos votos;

    private BancoDeDados() { }

    /**
     * Repositório de votos da sessão atual. Conecta e testa o acesso ao Firestore na primeira chamada;
     * se falhar, tenta de novo na próxima (por exemplo, depois de colocar a chave na pasta).
     * @throws ErroRepositorioException se não houver chave ou o Firestore não puder ser acessado
     */
    public static synchronized RepositorioVotos votos() {
        if (votos == null) {
            votos = conectar();
        }
        return votos;
    }

    /** Texto para mostrar na tela de onde vêm os dados. */
    public static String descricao() {
        return "Firebase Firestore — sessão " + idSessao();
    }

    public static String idSessao() {
        return Configuracao.ler("urna.sessao", "URNA_SESSAO").orElse(SESSAO_PADRAO);
    }

    private static RepositorioVotos conectar() {
        Firestore firestore;
        try {
            firestore = ConexaoFirebase.conectar().orElseThrow(() -> new ErroRepositorioException(
                    "Chave do Firebase não encontrada. Coloque o arquivo *firebase-adminsdk*.json"
                            + " na pasta do projeto (veja docs/banco-de-dados.md)."));
        } catch (ErroRepositorioException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ErroRepositorioException("Chave do Firebase inválida: " + e.getMessage(), e);
        }
        verificarAcesso(firestore);
        System.out.println("[BancoDeDados] Votos serão gravados em: " + descricao());
        return new RepositorioVotosFirestore(firestore, idSessao(), CatalogoCandidatos.criarCargos());
    }

    /** Faz uma leitura simples para descobrir já na abertura se a chave, o projeto e o banco estão certos. */
    private static void verificarAcesso(Firestore firestore) {
        try {
            RepositorioVotosFirestore.esperar(firestore.collection(EsquemaFirestore.SESSOES).document(idSessao()).get());
        } catch (ExecutionException e) {
            throw new ErroRepositorioException("Não foi possível acessar o Firestore: " + e.getCause().getMessage()
                    + "\nConfira se o banco Firestore foi criado no console do Firebase.", e.getCause());
        }
    }
}
