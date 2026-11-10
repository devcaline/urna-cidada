package br.edu.unijorge.urnacidada.infraestrutura;

import br.edu.unijorge.urnacidada.dados.CatalogoCandidatos;
import br.edu.unijorge.urnacidada.dominio.enums.EstadoSessao;
import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import br.edu.unijorge.urnacidada.dominio.modelo.Partido;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteBatch;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Prepara o banco: publica no Firestore o catálogo da simulação (partidos, cargos e candidatos
 * de {@link CatalogoCandidatos}) e cria o documento da sessão de votação.
 * <p>
 * Rode este main uma vez, depois de colocar a chave do Firebase na pasta do projeto.
 * Pode ser rodado de novo sem problema: o catálogo é sobrescrito e a sessão só é criada se não existir.
 * Os votos já gravados nunca são apagados por aqui.
 */
public final class InicializarBanco {
    private InicializarBanco() { }

    public static void main(String[] args) throws Exception {
        Firestore firestore = ConexaoFirebase.conectar().orElseThrow(() -> new IllegalStateException(
                "Chave do Firebase não encontrada. Veja docs/banco-de-dados.md."));
        try {
            publicarCatalogo(firestore, CatalogoCandidatos.criarCargos());
            String idSessao = BancoDeDados.idSessao();
            boolean criada = criarSessaoSeNaoExistir(firestore, idSessao);
            System.out.println("Sessão '" + idSessao + "' " + (criada ? "criada." : "já existia (mantida)."));
            int votos = RepositorioVotosFirestore.esperar(firestore.collection(EsquemaFirestore.SESSOES)
                    .document(idSessao).collection(EsquemaFirestore.VOTOS).get()).size();
            System.out.println("Votos já registrados nesta sessão: " + votos
                    + (votos == 0 ? " (zerada, pronta para votar — RN16)." : "."));
            System.out.println("Pronto! Confira em https://console.firebase.google.com > Firestore Database.");
        } finally {
            firestore.close();
        }
    }

    static void publicarCatalogo(Firestore firestore, List<Cargo> cargos) throws ExecutionException {
        WriteBatch lote = firestore.batch();
        Map<String, Partido> partidos = new LinkedHashMap<>();
        int ordem = 1;
        for (Cargo cargo : cargos) {
            DocumentReference documentoCargo = firestore.collection(EsquemaFirestore.CARGOS)
                    .document(EsquemaFirestore.idDoCargo(cargo));
            lote.set(documentoCargo, Map.of(
                    "nome", cargo.getNome(),
                    "quantidadeDigitos", cargo.getQuantidadeDigitos(),
                    "ordem", ordem++));
            for (Candidato candidato : cargo.getCandidatos()) {
                Map<String, Object> dados = new LinkedHashMap<>();
                dados.put("nome", candidato.getNome());
                dados.put("numero", candidato.getNumero());
                dados.put("partido", candidato.getPartido().getSigla());
                dados.put("vice", candidato.getVice());
                dados.put("foto", candidato.getFoto_src());
                lote.set(documentoCargo.collection(EsquemaFirestore.CANDIDATOS).document(candidato.getNumero()), dados);
                partidos.putIfAbsent(candidato.getPartido().getSigla(), candidato.getPartido());
            }
        }
        for (Partido partido : partidos.values()) {
            lote.set(firestore.collection(EsquemaFirestore.PARTIDOS).document(partido.getSigla()),
                    Map.of("nome", partido.getNome(), "sigla", partido.getSigla()));
        }
        RepositorioVotosFirestore.esperar(lote.commit());
        System.out.println("Catálogo publicado: " + cargos.size() + " cargos e " + partidos.size() + " partidos.");
    }

    /** @return true se a sessão foi criada agora; false se ela já existia */
    static boolean criarSessaoSeNaoExistir(Firestore firestore, String idSessao) throws ExecutionException {
        DocumentReference sessao = firestore.collection(EsquemaFirestore.SESSOES).document(idSessao);
        try {
            RepositorioVotosFirestore.esperar(sessao.create(Map.of(
                    "estado", EstadoSessao.NAO_INICIADA.name(),
                    "criadaEm", FieldValue.serverTimestamp())));
            return true;
        } catch (ExecutionException e) {
            if (RepositorioVotosFirestore.jaExiste(e)) {
                return false;
            }
            throw e;
        }
    }
}
