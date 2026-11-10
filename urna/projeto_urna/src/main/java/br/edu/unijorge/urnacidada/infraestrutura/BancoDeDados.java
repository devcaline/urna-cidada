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
    /** Sessão da votação real. Usada quando nada é escolhido nem configurado. */
    public static final String SESSAO_PADRAO = "oficina-2026";
    /** Sessão para testes e demonstração, separada da votação real. */
    public static final String SESSAO_ENSAIO = "ensaio";

    private static RepositorioVotos votos;
    /** Sessão escolhida na tela inicial (botões ENSAIO/OFICINA); tem prioridade sobre a configuração. */
    private static String sessaoEscolhida;

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

    /**
     * Sessão em uso: a escolhida na tela inicial; senão -Durna.sessao=... ou a variável URNA_SESSAO;
     * senão {@link #SESSAO_PADRAO}.
     */
    public static synchronized String idSessao() {
        if (sessaoEscolhida != null) {
            return sessaoEscolhida;
        }
        return Configuracao.ler("urna.sessao", "URNA_SESSAO").orElse(SESSAO_PADRAO);
    }

    /** Define a sessão das próximas votações. Se mudar, a próxima chamada a {@link #votos()} reconecta nela. */
    public static synchronized void usarSessao(String idSessao) {
        if (idSessao == null || idSessao.isBlank()) {
            throw new IllegalArgumentException("Sessão deve ser informada.");
        }
        if (!idSessao.trim().equals(idSessao())) {
            votos = null;
        }
        sessaoEscolhida = idSessao.trim();
    }

    /** Volta a usar a sessão da configuração (para testes). */
    static synchronized void esquecerSessaoEscolhida() {
        sessaoEscolhida = null;
        votos = null;
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
