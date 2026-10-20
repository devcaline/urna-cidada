package br.edu.unijorge.urnacidada.dominio.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class CandidatoTest {
    private final Partido partido = new Partido("Exemplo", "PE");
    private final Cargo cargo = new Cargo("Deputado Federal", 4);
    @Test void preservaDadosRelacionamentosEQuantidadePorCargo() {
        Candidato candidato = new Candidato("Exemplo técnico", "0012", partido, cargo);
        assertEquals("Exemplo técnico", candidato.getNome());
        assertEquals("0012", candidato.getNumero());
        assertSame(partido, candidato.getPartido());
        assertSame(cargo, candidato.getCargo());
        assertEquals("01", new Candidato("Outro", "01", partido, new Cargo("Presidente", 2)).getNumero());
    }
    @ParameterizedTest @NullAndEmptySource @ValueSource(strings = {" ", "\n"})
    void rejeitaNomeInvalido(String nome) {
        assertThrows(IllegalArgumentException.class, () -> new Candidato(nome, "0012", partido, cargo));
    }
    @ParameterizedTest @NullAndEmptySource @ValueSource(strings = {"12", "12345", "abcd", " 012", "１２３４", "0012\n"})
    void rejeitaNumeroInvalido(String numero) {
        assertThrows(IllegalArgumentException.class, () -> new Candidato("Nome", numero, partido, cargo));
    }
    @Test void rejeitaRelacionamentosAusentes() {
        assertThrows(IllegalArgumentException.class, () -> new Candidato("Nome", "0012", null, cargo));
        assertThrows(IllegalArgumentException.class, () -> new Candidato("Nome", "0012", partido, null));
    }
}
