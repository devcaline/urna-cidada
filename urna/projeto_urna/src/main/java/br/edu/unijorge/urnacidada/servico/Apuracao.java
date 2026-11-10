package br.edu.unijorge.urnacidada.servico;

import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import br.edu.unijorge.urnacidada.dominio.modelo.Voto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Conta os votos registrados (RF13/RF18). Só considera votos confirmados (RN22) e calcula
 * percentuais sem divisão por zero (UC09). Cargo é comparado pelo nome e candidato pelo número,
 * então funciona mesmo com objetos vindos de catálogos montados em momentos diferentes.
 * O percentual é sobre o total de votos do cargo (brancos e nulos incluídos), para a tabela somar 100%.
 */
public final class Apuracao {
    private final Map<String, Integer> votosPorCandidato = new HashMap<>();
    private final Map<String, Integer> brancosPorCargo = new HashMap<>();
    private final Map<String, Integer> nulosPorCargo = new HashMap<>();
    private final Map<String, Integer> totalPorCargo = new HashMap<>();

    public Apuracao(List<Voto> votos) {
        for (Voto voto : votos) {
            if (voto == null || !voto.isConfirmado()) {
                continue;
            }
            String cargo = voto.getCargo().getNome();
            totalPorCargo.merge(cargo, 1, Integer::sum);
            switch (voto.getTipo()) {
                case CANDIDATO -> votosPorCandidato.merge(chave(cargo, voto.getCandidato().getNumero()), 1, Integer::sum);
                case BRANCO -> brancosPorCargo.merge(cargo, 1, Integer::sum);
                case NULO -> nulosPorCargo.merge(cargo, 1, Integer::sum);
            }
        }
    }

    public int votosNulos(Cargo cargo) {
        return nulosPorCargo.getOrDefault(cargo.getNome(), 0);
    }

    public int votosDoCandidato(Candidato candidato) {
        return votosPorCandidato.getOrDefault(chave(candidato.getCargo().getNome(), candidato.getNumero()), 0);
    }

    public int votosEmBranco(Cargo cargo) {
        return brancosPorCargo.getOrDefault(cargo.getNome(), 0);
    }

    public int totalDoCargo(Cargo cargo) {
        return totalPorCargo.getOrDefault(cargo.getNome(), 0);
    }

    /** Percentual (0 a 100) de {@code votos} sobre o total do cargo; 0 se ninguém votou. */
    public double percentual(int votos, Cargo cargo) {
        int total = totalDoCargo(cargo);
        return total == 0 ? 0.0 : votos * 100.0 / total;
    }

    private static String chave(String cargo, String numero) {
        return cargo + "|" + numero;
    }
}
