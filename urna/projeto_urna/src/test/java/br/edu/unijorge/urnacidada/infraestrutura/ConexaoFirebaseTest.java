package br.edu.unijorge.urnacidada.infraestrutura;

import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ConexaoFirebaseTest {

    @Test void encontraChaveBaixadaDoConsoleEmPastaAcima(@TempDir Path raiz) throws Exception {
        Path chave = Files.writeString(raiz.resolve("urna-cidada-firebase-adminsdk-abc12-0123456789.json"), "{}");
        Path subpasta = Files.createDirectories(raiz.resolve("urna/projeto_urna"));
        assertEquals(chave.toRealPath(), ConexaoFirebase.procurarAPartirDe(subpasta).orElseThrow().toRealPath());
    }

    @Test void prefereServiceAccountJsonNaMesmaPasta(@TempDir Path raiz) throws Exception {
        Files.writeString(raiz.resolve("x-firebase-adminsdk-y.json"), "{}");
        Path padrao = Files.writeString(raiz.resolve("serviceAccount.json"), "{}");
        assertEquals(padrao.toRealPath(), ConexaoFirebase.procurarAPartirDe(raiz).orElseThrow().toRealPath());
    }

    @Test void semChaveRetornaVazio(@TempDir Path raiz) throws Exception {
        Path profunda = Files.createDirectories(raiz.resolve("a/b/c/d"));
        assertTrue(ConexaoFirebase.procurarAPartirDe(profunda).isEmpty());
    }

    @Test void idDoCargoSemAcentoNemEspaco() {
        assertEquals("deputado-federal", EsquemaFirestore.idDoCargo(new Cargo("Deputado Federal", 4)));
        assertEquals("presidente", EsquemaFirestore.idDoCargo(new Cargo("Presidente", 2)));
        assertEquals("vereador-a-camara", EsquemaFirestore.idDoCargo(new Cargo(" Vereador à Câmara ", 5)));
    }
}
