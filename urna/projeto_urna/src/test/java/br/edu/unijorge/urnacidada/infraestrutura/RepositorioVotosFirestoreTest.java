package br.edu.unijorge.urnacidada.infraestrutura;

import br.edu.unijorge.urnacidada.dados.CatalogoCandidatos;
import br.edu.unijorge.urnacidada.dominio.enums.TipoVoto;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import br.edu.unijorge.urnacidada.dominio.modelo.Voto;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste de integração com o Firestore de verdade (emulador local).
 * Só roda com o emulador ligado; sem ele, é ignorado. Como rodar:
 * <pre>firebase emulators:exec --only firestore "mvn -f urna/projeto_urna/pom.xml test"</pre>
 */
@EnabledIfEnvironmentVariable(named = "FIRESTORE_EMULATOR_HOST", matches = ".+")
class RepositorioVotosFirestoreTest {
    private static Firestore firestore;
    private final List<Cargo> cargos = CatalogoCandidatos.criarCargos();
    private final Cargo deputado = cargos.get(0);
    private final Cargo presidente = cargos.get(1);

    @BeforeAll static void conectar() {
        firestore = ConexaoFirebase.conectar().orElseThrow();
    }

    @AfterAll static void fechar() throws Exception {
        firestore.close();
    }

    private RepositorioVotosFirestore novaSessao() {
        return new RepositorioVotosFirestore(firestore, "teste-" + UUID.randomUUID(), cargos);
    }

    @Test void gravaELeDeVolta() {
        RepositorioVotosFirestore repositorio = novaSessao();
        repositorio.salvar(new Voto("v1", TipoVoto.CANDIDATO, deputado, deputado.buscarCandidato("1234").orElseThrow(), true));
        repositorio.salvar(new Voto("v2", TipoVoto.BRANCO, presidente, null, true));

        List<Voto> lidos = repositorio.listar();
        assertEquals(2, lidos.size());
        Voto candidato = lidos.stream().filter(v -> v.getId().equals("v1")).findFirst().orElseThrow();
        assertEquals(TipoVoto.CANDIDATO, candidato.getTipo());
        assertSame(deputado, candidato.getCargo());
        assertEquals("Vivicaxi", candidato.getCandidato().getNome());
        assertTrue(candidato.isConfirmado());
        Voto branco = lidos.stream().filter(v -> v.getId().equals("v2")).findFirst().orElseThrow();
        assertEquals(TipoVoto.BRANCO, branco.getTipo());
        assertNull(branco.getCandidato());
    }

    @Test void reenviarMesmoIdNaoDuplicaNemAltera() {
        RepositorioVotosFirestore repositorio = novaSessao();
        repositorio.salvar(new Voto("mesmo", TipoVoto.CANDIDATO, presidente, presidente.buscarCandidato("43").orElseThrow(), true));
        repositorio.salvar(new Voto("mesmo", TipoVoto.CANDIDATO, presidente, presidente.buscarCandidato("43").orElseThrow(), true));
        repositorio.salvar(new Voto("mesmo", TipoVoto.BRANCO, presidente, null, true));

        List<Voto> lidos = repositorio.listar();
        assertEquals(1, lidos.size());
        assertEquals("43", lidos.get(0).getCandidato().getNumero());
    }

    @Test void sessoesNaoSeMisturam() {
        RepositorioVotosFirestore oficinaA = novaSessao();
        RepositorioVotosFirestore oficinaB = novaSessao();
        oficinaA.salvar(new Voto("a", TipoVoto.BRANCO, presidente, null, true));
        assertEquals(1, oficinaA.listar().size());
        assertTrue(oficinaB.listar().isEmpty());
    }

    @Test void documentoNaoIdentificaEleitorNemHorario() throws Exception {
        String sessao = "teste-" + UUID.randomUUID();
        new RepositorioVotosFirestore(firestore, sessao, cargos)
                .salvar(new Voto("sigilo", TipoVoto.CANDIDATO, presidente, presidente.buscarCandidato("12").orElseThrow(), true));
        DocumentSnapshot doc = firestore.collection("sessoes").document(sessao)
                .collection("votos").document("sigilo").get().get();
        assertEquals(java.util.Set.of("cargo", "cargoNome", "tipo", "numero", "candidato", "partido"), doc.getData().keySet());
        assertEquals("presidente", doc.getString("cargo"));
        assertEquals("PA", doc.getString("partido"));
    }

    @Test void inicializarBancoPublicaCatalogoECriaSessaoUmaVez() throws Exception {
        InicializarBanco.publicarCatalogo(firestore, cargos);
        assertEquals(3, firestore.collection("cargos").document("deputado-federal")
                .collection("candidatos").get().get().size());
        assertEquals("Zé Noura", firestore.collection("cargos").document("presidente")
                .collection("candidatos").document("43").get().get().getString("vice"));
        assertEquals(3, firestore.collection("partidos").get().get().size());

        String sessao = "teste-" + UUID.randomUUID();
        assertTrue(InicializarBanco.criarSessaoSeNaoExistir(firestore, sessao));
        assertFalse(InicializarBanco.criarSessaoSeNaoExistir(firestore, sessao));
        assertEquals("NAO_INICIADA", firestore.collection("sessoes").document(sessao).get().get().getString("estado"));
    }
}
