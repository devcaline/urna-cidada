package br.edu.unijorge.urnacidada.servico;

import br.edu.unijorge.urnacidada.dominio.enums.EstadoSessao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessaoVotacaoTest {

    @Test
    void comecaNaoIniciadaEPercorreOsEstados() {
        SessaoVotacao sessao = new SessaoVotacao(false);
        assertEquals(EstadoSessao.NAO_INICIADA, sessao.getEstado());

        sessao.iniciar(0);
        assertTrue(sessao.isEmAndamento());

        sessao.encerrar();
        assertEquals(EstadoSessao.ENCERRADA, sessao.getEstado());
    }

    @Test
    void oficinaSoIniciaComContagemZerada() {
        SessaoVotacao sessao = new SessaoVotacao(false);
        assertThrows(IllegalStateException.class, () -> sessao.iniciar(3));
        assertEquals(EstadoSessao.NAO_INICIADA, sessao.getEstado());
    }

    @Test
    void ensaioIniciaMesmoComVotosDeTestesAnteriores() {
        SessaoVotacao sessao = new SessaoVotacao(true);
        sessao.iniciar(3);
        assertTrue(sessao.isEmAndamento());
    }

    @Test
    void naoIniciaDuasVezesNemEncerraSemIniciar() {
        SessaoVotacao sessao = new SessaoVotacao(false);
        assertThrows(IllegalStateException.class, sessao::encerrar);

        sessao.iniciar(0);
        assertThrows(IllegalStateException.class, () -> sessao.iniciar(0));

        sessao.encerrar();
        assertThrows(IllegalStateException.class, sessao::encerrar);
    }
}
