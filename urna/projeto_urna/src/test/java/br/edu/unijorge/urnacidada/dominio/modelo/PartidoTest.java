package br.edu.unijorge.urnacidada.dominio.modelo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class PartidoTest {
    @Test void representaPartidoFicticio() {
        Partido partido = new Partido("Partido Exemplo Técnico", "PET");
        assertEquals("Partido Exemplo Técnico", partido.getNome());
        assertEquals("PET", partido.getSigla());
        assertEquals("Partido Exemplo Técnico (PET)", partido.toString());
    }
    @ParameterizedTest @NullAndEmptySource @ValueSource(strings = {" ", "\t\n", "\u2003"})
    void rejeitaNomeInvalido(String nome) {
        assertThrows(IllegalArgumentException.class, () -> new Partido(nome, "PET"));
    }
    @ParameterizedTest @NullAndEmptySource @ValueSource(strings = {" ", "\t\n", "\u2003"})
    void rejeitaSiglaInvalida(String sigla) {
        assertThrows(IllegalArgumentException.class, () -> new Partido("Exemplo", sigla));
    }
}
