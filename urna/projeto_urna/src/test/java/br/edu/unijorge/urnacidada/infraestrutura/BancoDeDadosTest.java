package br.edu.unijorge.urnacidada.infraestrutura;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BancoDeDadosTest {

    @AfterEach void limpar() {
        BancoDeDados.esquecerSessaoEscolhida();
    }

    @Test void sessaoEscolhidaNaTelaInicialTemPrioridade() {
        BancoDeDados.usarSessao(BancoDeDados.SESSAO_ENSAIO);
        assertEquals("ensaio", BancoDeDados.idSessao());
        assertTrue(BancoDeDados.descricao().endsWith("sessão ensaio"));

        BancoDeDados.usarSessao(BancoDeDados.SESSAO_PADRAO);
        assertEquals("oficina-2026", BancoDeDados.idSessao());
    }

    @Test void rejeitaSessaoVazia() {
        assertThrows(IllegalArgumentException.class, () -> BancoDeDados.usarSessao(" "));
        assertThrows(IllegalArgumentException.class, () -> BancoDeDados.usarSessao(null));
    }
}
