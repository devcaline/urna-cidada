package br.edu.unijorge.urnacidada;

import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import br.edu.unijorge.urnacidada.dominio.modelo.Partido;

/** Exemplo de objetos, sem executar o processo de votação. */
public final class Main {
    private Main() { }

    public static void main(String[] args) {
        Cargo cargo = new Cargo("Deputado Federal", 4);
        Partido partido = new Partido("Partido Exemplo Técnico", "PET");
        Candidato candidato = new Candidato("Candidato Exemplo Técnico", "0012", partido, cargo);
        cargo.adicionarCandidato(candidato);
        System.out.println("EXEMPLO TÉCNICO: dados fictícios, não são candidaturas definitivas.");
        cargo.buscarCandidato("0012").ifPresent(encontrado ->
                System.out.printf("%s | %s | %s | %s%n", cargo.getNome(), encontrado.getNumero(),
                        encontrado.getNome(), encontrado.getPartido()));
    }
}
