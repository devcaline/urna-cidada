package br.edu.unijorge.urnacidada.infraestrutura;

import br.edu.unijorge.urnacidada.dominio.enums.TipoVoto;
import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import br.edu.unijorge.urnacidada.dominio.modelo.Voto;
import br.edu.unijorge.urnacidada.repositorio.ErroRepositorioException;
import br.edu.unijorge.urnacidada.repositorio.RepositorioVotosBase;
import com.google.api.core.ApiFuture;
import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.StatusCode;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Guarda os votos de uma sessão em {@code sessoes/{idSessao}/votos/{idVoto}}.
 * <p>
 * Sigilo do voto: o documento NÃO guarda nome, ID do eleitor nem horário, só a escolha.
 * Idempotência: usa {@code create()}, que falha se o ID já existir; nesse caso o voto
 * já está gravado e nada é duplicado (RNFs03). Votos gravados nunca são alterados (RN13).
 */
public final class RepositorioVotosFirestore extends RepositorioVotosBase {
    static final long TEMPO_LIMITE_SEGUNDOS = 10;

    private final CollectionReference votos;
    private final List<Cargo> cargos;

    /**
     * @param cargos catálogo usado para transformar os documentos lidos de volta em objetos Voto
     */
    public RepositorioVotosFirestore(Firestore firestore, String idSessao, List<Cargo> cargos) {
        if (firestore == null || idSessao == null || idSessao.isBlank() || cargos == null) {
            throw new IllegalArgumentException("Firestore, sessão e cargos devem ser informados.");
        }
        this.votos = firestore.collection(EsquemaFirestore.SESSOES).document(idSessao)
                .collection(EsquemaFirestore.VOTOS);
        this.cargos = List.copyOf(cargos);
    }

    @Override
    protected void gravar(Voto voto) {
        try {
            esperar(votos.document(voto.getId()).create(paraDocumento(voto)));
        } catch (ExecutionException e) {
            if (jaExiste(e)) {
                return; // mesmo voto reenviado (ex.: nova tentativa após falha de rede): já está gravado
            }
            throw new ErroRepositorioException("Não foi possível registrar o voto no Firestore.", e.getCause());
        }
    }

    @Override
    public List<Voto> listar() {
        List<QueryDocumentSnapshot> documentos;
        try {
            documentos = esperar(votos.get()).getDocuments();
        } catch (ExecutionException e) {
            throw new ErroRepositorioException("Não foi possível ler os votos do Firestore.", e.getCause());
        }
        List<Voto> lista = new ArrayList<>();
        for (QueryDocumentSnapshot documento : documentos) {
            lista.add(paraVoto(documento));
        }
        return List.copyOf(lista);
    }

    // ---------- conversão objeto <-> documento ----------

    private static Map<String, Object> paraDocumento(Voto voto) {
        Candidato candidato = voto.getCandidato();
        Map<String, Object> dados = new LinkedHashMap<>();
        dados.put("cargo", EsquemaFirestore.idDoCargo(voto.getCargo()));
        dados.put("cargoNome", voto.getCargo().getNome());
        dados.put("tipo", voto.getTipo().name());
        dados.put("numero", candidato == null ? null : candidato.getNumero());
        // nome e partido repetidos de propósito: deixam o voto legível direto no console do Firebase
        dados.put("candidato", candidato == null ? null : candidato.getNome());
        dados.put("partido", candidato == null ? null : candidato.getPartido().getSigla());
        return dados;
    }

    private Voto paraVoto(DocumentSnapshot documento) {
        String idCargo = documento.getString("cargo");
        Cargo cargo = cargos.stream()
                .filter(c -> EsquemaFirestore.idDoCargo(c).equals(idCargo))
                .findFirst()
                .orElseThrow(() -> new ErroRepositorioException(
                        "Voto " + documento.getId() + " aponta para um cargo que não existe no catálogo: " + idCargo));
        TipoVoto tipo = lerTipo(documento);
        Candidato candidato = null;
        if (tipo == TipoVoto.CANDIDATO) {
            String numero = documento.getString("numero");
            candidato = cargo.buscarCandidato(numero).orElseThrow(() -> new ErroRepositorioException(
                    "Voto " + documento.getId() + " aponta para um candidato que não existe: " + cargo.getNome() + " " + numero));
        }
        return new Voto(documento.getId(), tipo, cargo, candidato, true);
    }

    private static TipoVoto lerTipo(DocumentSnapshot documento) {
        String tipo = documento.getString("tipo");
        try {
            return TipoVoto.valueOf(tipo);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ErroRepositorioException("Voto " + documento.getId() + " tem tipo inválido: " + tipo, e);
        }
    }

    // ---------- auxiliares ----------

    static <T> T esperar(ApiFuture<T> futuro) throws ExecutionException {
        try {
            return futuro.get(TEMPO_LIMITE_SEGUNDOS, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new ErroRepositorioException("O Firestore não respondeu em " + TEMPO_LIMITE_SEGUNDOS
                    + " segundos. Verifique a conexão com a internet.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ErroRepositorioException("Operação com o Firestore interrompida.", e);
        }
    }

    /** Verdadeiro se a falha foi "documento já existe" (ALREADY_EXISTS). */
    static boolean jaExiste(Throwable erro) {
        for (Throwable atual = erro; atual != null; atual = atual.getCause()) {
            if (atual instanceof ApiException api && api.getStatusCode().getCode() == StatusCode.Code.ALREADY_EXISTS) {
                return true;
            }
            if (atual instanceof StatusRuntimeException grpc && grpc.getStatus().getCode() == Status.Code.ALREADY_EXISTS) {
                return true;
            }
        }
        return false;
    }
}
