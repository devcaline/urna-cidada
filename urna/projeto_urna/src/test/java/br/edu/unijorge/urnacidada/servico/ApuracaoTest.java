package br.edu.unijorge.urnacidada.servico;

import br.edu.unijorge.urnacidada.dados.CatalogoCandidatos;
import br.edu.unijorge.urnacidada.dominio.enums.TipoVoto;
import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import br.edu.unijorge.urnacidada.dominio.modelo.Voto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApuracaoTest {
    private final List<Cargo> cargos = CatalogoCandidatos.criarCargos();
    private final Cargo deputado = cargos.get(0);
    private final Cargo presidente = cargos.get(1);

    private Candidato candidato(Cargo cargo, String numero) {
        return cargo.buscarCandidato(numero).orElseThrow();
    }

    @Test void semVotosTudoZeroSemDividirPorZero() {
        Apuracao apuracao = new Apuracao(List.of());
        assertEquals(0, apuracao.votosDoCandidato(candidato(presidente, "43")));
        assertEquals(0, apuracao.votosEmBranco(presidente));
        assertEquals(0.0, apuracao.percentual(0, presidente));
    }

    @Test void contaPorCandidatoBrancoECargoSeparadamente() {
        Apuracao apuracao = new Apuracao(List.of(
                new Voto("1", TipoVoto.CANDIDATO, presidente, candidato(presidente, "43"), true),
                new Voto("2", TipoVoto.CANDIDATO, presidente, candidato(presidente, "43"), true),
                new Voto("3", TipoVoto.BRANCO, presidente, null, true),
                new Voto("4", TipoVoto.CANDIDATO, deputado, candidato(deputado, "7777"), true)));
        assertEquals(2, apuracao.votosDoCandidato(candidato(presidente, "43")));
        assertEquals(0, apuracao.votosDoCandidato(candidato(presidente, "12")));
        assertEquals(1, apuracao.votosEmBranco(presidente));
        assertEquals(0, apuracao.votosEmBranco(deputado));
        assertEquals(3, apuracao.totalDoCargo(presidente));
        assertEquals(1, apuracao.totalDoCargo(deputado));
        assertEquals(200.0 / 3, apuracao.percentual(2, presidente), 1e-9);
        assertEquals(100.0, apuracao.percentual(1, deputado), 1e-9);
    }

    @Test void contaNulosSeparadosDosBrancosEEntramNoTotal() {
        Apuracao apuracao = new Apuracao(List.of(
                new Voto("1", TipoVoto.NULO, presidente, null, true),
                new Voto("2", TipoVoto.NULO, presidente, null, true),
                new Voto("3", TipoVoto.BRANCO, presidente, null, true),
                new Voto("4", TipoVoto.CANDIDATO, presidente, candidato(presidente, "12"), true)));
        assertEquals(2, apuracao.votosNulos(presidente));
        assertEquals(0, apuracao.votosNulos(deputado));
        assertEquals(1, apuracao.votosEmBranco(presidente));
        assertEquals(4, apuracao.totalDoCargo(presidente));
        assertEquals(50.0, apuracao.percentual(apuracao.votosNulos(presidente), presidente), 1e-9);
    }

    @Test void ignoraVotoNaoConfirmado() {
        Apuracao apuracao = new Apuracao(List.of(
                new Voto("1", TipoVoto.CANDIDATO, presidente, candidato(presidente, "43"), false)));
        assertEquals(0, apuracao.totalDoCargo(presidente));
    }

    @Test void funcionaComCatalogoMontadoEmOutroMomento() {
        Cargo outroPresidente = CatalogoCandidatos.criarCargos().get(1);
        Apuracao apuracao = new Apuracao(List.of(
                new Voto("1", TipoVoto.CANDIDATO, presidente, candidato(presidente, "77"), true)));
        assertEquals(1, apuracao.votosDoCandidato(candidato(outroPresidente, "77")));
    }
}
