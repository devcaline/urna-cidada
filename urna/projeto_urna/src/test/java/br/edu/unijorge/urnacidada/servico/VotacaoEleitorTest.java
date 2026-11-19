package br.edu.unijorge.urnacidada.servico;

import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import br.edu.unijorge.urnacidada.dominio.modelo.Partido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VotacaoEleitorTest {
    private VotacaoEleitor votacao;

    @BeforeEach
    void criarVotacao() {
        Partido partido = new Partido("Partido Teste", "PT1");
        Cargo deputado = new Cargo("Deputado Federal", 4);
        deputado.adicionarCandidato(new Candidato("Deputada Teste", "1234", partido, deputado));
        Cargo presidente = new Cargo("Presidente", 2);
        presidente.adicionarCandidato(new Candidato("Presidente Teste", "12", partido, presidente));
        votacao = new VotacaoEleitor(List.of(deputado, presidente));
    }

    private void digitar(String numero) {
        for (char digito : numero.toCharArray()) {
            votacao.digitar(String.valueOf(digito));
        }
    }

    @Test
    void comecaEmDeputadoAguardandoEleitor() {
        assertEquals("Deputado Federal", votacao.getCargoAtual().getNome());
        assertTrue(votacao.isAguardandoEleitor());
        assertFalse(votacao.isEncerrada());
    }

    @Test
    void naoAceitaMaisDigitosQueOCargo() {
        digitar("12345");
        assertEquals("1234", votacao.getNumeroDigitado());
        assertTrue(votacao.isNumeroCompleto());
    }

    @Test
    void encontraCandidatoPeloNumero() {
        digitar("1234");
        assertEquals("Deputada Teste", votacao.getCandidatoEncontrado().orElseThrow().getNome());
        assertFalse(votacao.isNulo());
    }

    @Test
    void numeroCompletoSemCandidatoEhNulo() {
        digitar("9999");
        assertTrue(votacao.isNulo());
        assertTrue(votacao.confirmar());
    }

    @Test
    void naoConfirmaNumeroIncompleto() {
        digitar("12");
        assertFalse(votacao.confirmar());
        assertEquals("Deputado Federal", votacao.getCargoAtual().getNome());
    }

    @Test
    void brancoSoComVisorVazioEBloqueiaDigitos() {
        digitar("1");
        votacao.votarBranco();
        assertFalse(votacao.isBranco());

        votacao.corrigir();
        votacao.votarBranco();
        assertTrue(votacao.isBranco());
        votacao.digitar("1");
        assertEquals("", votacao.getNumeroDigitado());
    }

    @Test
    void corrigirLimpaNumeroEBranco() {
        digitar("12");
        votacao.corrigir();
        assertEquals("", votacao.getNumeroDigitado());
        assertTrue(votacao.isAguardandoEleitor());
    }

    @Test
    void confirmarAvancaParaPresidenteEDepoisEncerra() {
        digitar("1234");
        assertTrue(votacao.confirmar());
        assertEquals("Presidente", votacao.getCargoAtual().getNome());
        assertEquals("", votacao.getNumeroDigitado());
        assertFalse(votacao.isAguardandoEleitor());

        votacao.votarBranco();
        assertTrue(votacao.confirmar());
        assertTrue(votacao.isEncerrada());
        assertThrows(IllegalStateException.class, votacao::getCargoAtual);
    }
}
