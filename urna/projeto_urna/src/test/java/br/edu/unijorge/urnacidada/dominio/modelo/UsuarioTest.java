package br.edu.unijorge.urnacidada.dominio.modelo;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {
    @Test void demonstraHerancaEPolimorfismoSemAutenticar() {
        List<Usuario> usuarios = List.of(new Eleitor("exemplo-e", "Exemplo Eleitor"), new Mesario("exemplo-m", "Exemplo Mesário"));
        assertEquals(List.of("ELEITOR", "MESARIO"), usuarios.stream().map(Usuario::getPapel).toList());
        assertEquals("exemplo-e", usuarios.get(0).getId());
        assertEquals("Exemplo Eleitor", usuarios.get(0).getNome());
        assertEquals("exemplo-m", usuarios.get(1).getId());
        assertEquals("Exemplo Mesário", usuarios.get(1).getNome());
    }
    @ParameterizedTest @NullAndEmptySource @ValueSource(strings = {" ", "\t"})
    void validaDadosBasicosNasDuasSubclasses(String invalido) {
        assertThrows(IllegalArgumentException.class, () -> new Eleitor(invalido, "Nome"));
        assertThrows(IllegalArgumentException.class, () -> new Eleitor("id", invalido));
        assertThrows(IllegalArgumentException.class, () -> new Mesario(invalido, "Nome"));
        assertThrows(IllegalArgumentException.class, () -> new Mesario("id", invalido));
    }
}
