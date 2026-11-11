package br.edu.unijorge.urnacidada.infraestrutura;

import br.edu.unijorge.urnacidada.repositorio.ErroRepositorioException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Abre a conexão com o Cloud Firestore usando o Firebase Admin SDK.
 * <p>
 * Ordem de procura:
 * <ol>
 *   <li>Emulador local, se a variável FIRESTORE_EMULATOR_HOST estiver definida (não precisa de chave);</li>
 *   <li>arquivo indicado em -Dfirebase.credenciais=... ou na variável FIREBASE_CREDENCIAIS
 *       (ou GOOGLE_APPLICATION_CREDENTIALS);</li>
 *   <li>arquivo {@code %LOCALAPPDATA%/UrnaCidada/serviceAccount.json};</li>
 *   <li>arquivo {@code serviceAccount.json} ou {@code *firebase-adminsdk*.json} na pasta atual
 *       ou em até 3 pastas acima (por exemplo, na raiz do repositório).</li>
 * </ol>
 * Se nada for encontrado, devolve {@code Optional.empty()}.
 * Os nomes de arquivo acima já estão no .gitignore: a chave nunca vai para o GitHub.
 */
public final class ConexaoFirebase {
    static final String PROJETO_EMULADOR = "demo-urna-cidada";
    private static final String NOME_APP = "urna-cidada";
    private static final int PASTAS_ACIMA = 3;

    private ConexaoFirebase() { }

    public static Optional<Firestore> conectar() {
        Optional<String> emulador = Configuracao.ler(null, "FIRESTORE_EMULATOR_HOST");
        if (emulador.isPresent()) {
            return Optional.of(conectarEmulador(emulador.get()));
        }
        return localizarCredenciais().map(ConexaoFirebase::conectarComChave);
    }

    private static Firestore conectarEmulador(String host) {
        String projeto = Configuracao.ler("firebase.projeto", "FIREBASE_PROJETO").orElse(PROJETO_EMULADOR);
        System.out.println("[Firebase] Usando o EMULADOR do Firestore em " + host + " (projeto " + projeto + ").");
        // Com FIRESTORE_EMULATOR_HOST definida, o SDK troca sozinho as credenciais pelas do emulador.
        return FirestoreOptions.newBuilder().setProjectId(projeto).build().getService();
    }

    private static Firestore conectarComChave(Path arquivo) {
        try (InputStream entrada = Files.newInputStream(arquivo)) {
            FirebaseOptions opcoes = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(entrada))
                    .build();
            FirebaseApp app = FirebaseApp.getApps().stream()
                    .filter(existente -> existente.getName().equals(NOME_APP))
                    .findFirst()
                    .orElseGet(() -> FirebaseApp.initializeApp(opcoes, NOME_APP));
            Firestore firestore = FirestoreClient.getFirestore(app);
            System.out.println("[Firebase] Conectado ao projeto " + firestore.getOptions().getProjectId()
                    + " usando a chave " + arquivo.getFileName() + ".");
            return firestore;
        } catch (IOException e) {
            throw new ErroRepositorioException("Não foi possível ler a chave do Firebase em " + arquivo.toAbsolutePath(), e);
        }
    }

    private static Optional<Path> localizarCredenciais() {
        Optional<String> informado = Configuracao.ler("firebase.credenciais", "FIREBASE_CREDENCIAIS")
                .or(() -> Configuracao.ler(null, "GOOGLE_APPLICATION_CREDENTIALS"));
        if (informado.isPresent()) {
            Path arquivo = Path.of(informado.get());
            if (!Files.isRegularFile(arquivo)) {
                throw new ErroRepositorioException("Chave do Firebase não encontrada em " + arquivo.toAbsolutePath());
            }
            return Optional.of(arquivo);
        }
        Optional<Path> perfilLocal = localizarNoPerfilDoUsuario();
        if (perfilLocal.isPresent()) {
            return perfilLocal;
        }
        return procurarAPartirDe(Path.of(""));
    }

    private static Optional<Path> localizarNoPerfilDoUsuario() {
        return Optional.ofNullable(System.getenv("LOCALAPPDATA"))
                .filter(valor -> !valor.isBlank())
                .map(Path::of)
                .map(pasta -> pasta.resolve("UrnaCidada").resolve("serviceAccount.json"))
                .filter(Files::isRegularFile);
    }

    /** Procura a chave na pasta informada e em até {@value #PASTAS_ACIMA} pastas acima dela. */
    static Optional<Path> procurarAPartirDe(Path inicio) {
        Path pasta = inicio.toAbsolutePath().normalize();
        for (int nivel = 0; nivel <= PASTAS_ACIMA && pasta != null; nivel++) {
            Optional<Path> encontrado = procurarNaPasta(pasta);
            if (encontrado.isPresent()) {
                return encontrado;
            }
            pasta = pasta.getParent();
        }
        return Optional.empty();
    }

    private static Optional<Path> procurarNaPasta(Path pasta) {
        Path padrao = pasta.resolve("serviceAccount.json");
        if (Files.isRegularFile(padrao)) {
            return Optional.of(padrao);
        }
        // Nome do arquivo baixado no console do Firebase: <projeto>-firebase-adminsdk-xxxxx-<hash>.json
        try (DirectoryStream<Path> arquivos = Files.newDirectoryStream(pasta, "*firebase-adminsdk*.json")) {
            for (Path arquivo : arquivos) {
                if (Files.isRegularFile(arquivo)) {
                    return Optional.of(arquivo);
                }
            }
        } catch (IOException | SecurityException e) {
            // pasta sem permissão de leitura: segue procurando nas outras
        }
        return Optional.empty();
    }
}
