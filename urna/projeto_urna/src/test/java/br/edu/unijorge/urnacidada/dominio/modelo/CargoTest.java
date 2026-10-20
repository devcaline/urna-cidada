package br.edu.unijorge.urnacidada.dominio.modelo;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class CargoTest {
    private final Partido partido = new Partido("Exemplo", "PE");
    @Test void associaEBuscaPreservandoZerosEListaProtegida() {
        Cargo cargo = new Cargo("Deputado Federal", 4);
        List<Candidato> antes = cargo.getCandidatos();
        Candidato candidato = new Candidato("Exemplo", "0012", partido, cargo);
        assertTrue(cargo.getCandidatos().isEmpty());
        cargo.adicionarCandidato(candidato);
        assertEquals("Deputado Federal", cargo.getNome());
        assertEquals(4, cargo.getQuantidadeDigitos());
        assertSame(candidato, cargo.buscarCandidato("0012").orElseThrow());
        assertTrue(cargo.buscarCandidato("9999").isEmpty());
        assertTrue(antes.isEmpty());
        assertEquals(List.of(candidato), cargo.getCandidatos());
        assertThrows(UnsupportedOperationException.class, () -> cargo.getCandidatos().clear());
    }
    @Test void impedeNumeroDuplicadoMesmoObjetoOuOutroCandidato() {
        Cargo cargo = new Cargo("Presidente", 2);
        Candidato primeiro = new Candidato("Um", "01", partido, cargo);
        cargo.adicionarCandidato(primeiro);
        assertThrows(IllegalArgumentException.class, () -> cargo.adicionarCandidato(primeiro));
        assertThrows(IllegalArgumentException.class, () -> cargo.adicionarCandidato(new Candidato("Dois", "01", partido, cargo)));
        assertEquals(1, cargo.getCandidatos().size());
    }
    @Test void rejeitaCargoDiferenteMesmoComMesmosAtributos() {
        Cargo cargo = new Cargo("Presidente", 2);
        Cargo outro = new Cargo("Presidente", 2);
        assertThrows(IllegalArgumentException.class, () -> cargo.adicionarCandidato(new Candidato("Um", "01", partido, outro)));
        assertThrows(IllegalArgumentException.class, () -> cargo.adicionarCandidato(null));
        assertTrue(cargo.getCandidatos().isEmpty());
    }
    @Test void permiteMesmoNumeroEmCargosDistintos() {
        Cargo um = new Cargo("Cargo A", 2);
        Cargo dois = new Cargo("Cargo B", 2);
        um.adicionarCandidato(new Candidato("Um", "01", partido, um));
        dois.adicionarCandidato(new Candidato("Dois", "01", partido, dois));
        assertEquals(1, dois.getCandidatos().size());
    }
    @ParameterizedTest @NullAndEmptySource @ValueSource(strings = {"1", "123", "a1", " 1", "١٢", "１２", "01\n"})
    void numeroInvalidoNaoEncontraCandidato(String numero) {
        Cargo cargo = new Cargo("Presidente", 2);
        assertFalse(cargo.validarNumero(numero));
        assertTrue(cargo.buscarCandidato(numero).isEmpty());
    }
    @ParameterizedTest @ValueSource(ints = {0, -1})
    void rejeitaQuantidadeInvalida(int quantidade) {
        assertThrows(IllegalArgumentException.class, () -> new Cargo("Cargo", quantidade));
    }
    @ParameterizedTest @NullAndEmptySource @ValueSource(strings = {" ", "\t"})
    void rejeitaNomeInvalido(String nome) {
        assertThrows(IllegalArgumentException.class, () -> new Cargo(nome, 2));
    }
}
