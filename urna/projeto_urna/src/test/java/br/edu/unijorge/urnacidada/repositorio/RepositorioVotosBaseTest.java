package br.edu.unijorge.urnacidada.repositorio;

import br.edu.unijorge.urnacidada.dados.CatalogoCandidatos;
import br.edu.unijorge.urnacidada.dominio.enums.TipoVoto;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import br.edu.unijorge.urnacidada.dominio.modelo.Voto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioVotosBaseTest {
    /** Subclasse mínima só para observar o que chega até gravar(). */
    private static final class RepositorioEspiao extends RepositorioVotosBase {
        final List<Voto> gravados = new ArrayList<>();
        @Override protected void gravar(Voto voto) { gravados.add(voto); }
        @Override public List<Voto> listar() { return List.copyOf(gravados); }
    }

    private final Cargo presidente = CatalogoCandidatos.criarCargos().get(1);

    @Test void repassaVotoConfirmado() {
        RepositorioEspiao repositorio = new RepositorioEspiao();
        Voto voto = new Voto("a", TipoVoto.BRANCO, presidente, null, true);
        repositorio.salvar(voto);
        assertEquals(List.of(voto), repositorio.gravados);
    }

    @Test void rejeitaNuloENaoConfirmadoSemGravar() {
        RepositorioEspiao repositorio = new RepositorioEspiao();
        assertThrows(IllegalArgumentException.class, () -> repositorio.salvar(null));
        assertThrows(IllegalArgumentException.class,
                () -> repositorio.salvar(new Voto("b", TipoVoto.BRANCO, presidente, null, false)));
        assertTrue(repositorio.gravados.isEmpty());
    }
}
