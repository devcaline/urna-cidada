package br.edu.unijorge.urnacidada;

import br.edu.unijorge.urnacidada.dados.CatalogoCandidatos;
import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import br.edu.unijorge.urnacidada.dominio.modelo.Partido;

import br.edu.unijorge.urnacidada.dados.CatalogoCandidatos;
import br.edu.unijorge.urnacidada.servico.VotacaoEleitor;

import java.util.List;

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

        //Teste de candidatos
        //List<Cargo> cargos = CatalogoCandidatos.criarCargos();

        //for(Cargo c : cargos){
        //System.out.println("== "+ c.getNome() + " ==");

        //for(Candidato candidatos : c.getCandidatos()){
        //System.out.println(candidatos.getNumero() + " - " + candidatos.getNome() + " (" + candidatos.getPartido().getSigla() + ")");
        //}
        //}

        VotacaoEleitor votacao = new VotacaoEleitor(CatalogoCandidatos.criarCargos());

        System.out.println("Cargo: " + votacao.getCargoAtual().getNome());

        votacao.digitar("1");
        votacao.digitar("1");
        votacao.digitar("1");
        votacao.digitar("1");
        votacao.digitar("1");

        System.out.println("Visor: " + votacao.getNumeroDigitado());
    }
}
