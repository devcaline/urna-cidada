package br.edu.unijorge.urnacidada.dominio.modelo;

import br.edu.unijorge.urnacidada.dominio.enums.TipoVoto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class VotoTest {
    private final Cargo cargo = new Cargo("Presidente", 2);
    private final Candidato candidato = new Candidato("Exemplo", "01", new Partido("Exemplo", "PE"), cargo);
    @Test void representaEscolhaSemConfirmacaoOuPersistencia() {
        Voto voto = new Voto("exemplo-1", TipoVoto.CANDIDATO, cargo, candidato, false);
        assertEquals("exemplo-1", voto.getId());
        assertEquals(TipoVoto.CANDIDATO, voto.getTipo());
        assertSame(cargo, voto.getCargo());
        assertSame(candidato, voto.getCandidato());
        assertFalse(voto.isConfirmado());
    }
    @Test void representaEstadoConfirmadoInformadoSemExecutarTransicao() {
        Voto voto = new Voto("exemplo-2", TipoVoto.CANDIDATO, cargo, candidato, true);
        assertTrue(voto.isConfirmado());
    }
    @Test void representaBrancoSemCandidato() {
        Voto voto = new Voto("exemplo-3", TipoVoto.BRANCO, cargo, null, false);
        assertEquals(TipoVoto.BRANCO, voto.getTipo());
        assertSame(cargo, voto.getCargo());
        assertNull(voto.getCandidato());
        assertFalse(voto.isConfirmado());
    }
    @Test void impedeCombinacoesIncompativeis() {
        assertThrows(IllegalArgumentException.class, () -> new Voto("id", TipoVoto.CANDIDATO, cargo, null, false));
        assertThrows(IllegalArgumentException.class, () -> new Voto("id", TipoVoto.BRANCO, cargo, candidato, false));
        assertThrows(IllegalArgumentException.class, () -> new Voto("id", TipoVoto.CANDIDATO, new Cargo("Presidente", 2), candidato, false));
        assertThrows(IllegalArgumentException.class, () -> new Voto("id", null, cargo, candidato, false));
        assertThrows(IllegalArgumentException.class, () -> new Voto("id", TipoVoto.BRANCO, null, null, false));
    }
    @Test void naoDefineComportamentoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Voto("id", TipoVoto.NULO, cargo, null, false));
        assertThrows(IllegalArgumentException.class, () -> new Voto("id", TipoVoto.NULO, cargo, candidato, false));
    }
    @ParameterizedTest @NullAndEmptySource @ValueSource(strings = {" ", "\n"})
    void exigeIdentificadorLocal(String id) {
        assertThrows(IllegalArgumentException.class, () -> new Voto(id, TipoVoto.BRANCO, cargo, null, false));
    }
}
