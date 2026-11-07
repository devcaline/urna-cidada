package br.edu.unijorge.urnacidada.infraestrutura;

import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Nomes das coleções do Firestore, reunidos num só lugar. Estrutura completa em docs/banco-de-dados.md:
 * <pre>
 * partidos/{sigla}
 * cargos/{idCargo}
 *     candidatos/{numero}
 * sessoes/{idSessao}
 *     votos/{idVoto}
 * </pre>
 */
public final class EsquemaFirestore {
    public static final String PARTIDOS = "partidos";
    public static final String CARGOS = "cargos";
    /** Subcoleção de cargos: o número é o ID do documento, o que garante número único por cargo (RN14). */
    public static final String CANDIDATOS = "candidatos";
    public static final String SESSOES = "sessoes";
    /** Subcoleção de sessoes: cada oficina/sessão tem os seus próprios votos. */
    public static final String VOTOS = "votos";

    private EsquemaFirestore() { }

    /** Converte o nome do cargo em ID de documento: "Deputado Federal" vira "deputado-federal". */
    public static String idDoCargo(Cargo cargo) {
        String semAcento = Normalizer.normalize(cargo.getNome(), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return semAcento.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-+|-+$)", "");
    }
}
