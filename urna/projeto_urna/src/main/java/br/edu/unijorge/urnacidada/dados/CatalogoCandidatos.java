package br.edu.unijorge.urnacidada.dados;
import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import br.edu.unijorge.urnacidada.dominio.modelo.Partido;

import java.util.List;

public class CatalogoCandidatos {

    public static List<Cargo>
    criarCargos(){
        // ======== PARTIDOS =======
        Partido Acao = new Partido("Partido Ação","PA");
        Partido CompromissoNacional = new Partido("Partido Compromisso Nacional","PCN");
        Partido Igualdade = new Partido("Partido da União e Igualdade","PUI");

        //======== DEPUTADO FEDERAL ========
        Cargo deputado = new Cargo("Deputado Federal",4);

        Candidato vivicaxi = new Candidato("Vivicaxi","1234",Acao,deputado);
        Candidato uvalina = new Candidato("Uvalina","4321",CompromissoNacional,deputado);
        Candidato goiabete = new Candidato("Goiabete","6789",Igualdade,deputado);

        deputado.adicionarCandidato(vivicaxi);
        deputado.adicionarCandidato(uvalina);
        deputado.adicionarCandidato(goiabete);

        //======= PRESIDENTE =======
        Cargo presidente = new Cargo("Presidente",2);

        Candidato moranguete = new Candidato("Moranguete","10",Igualdade,presidente);
        Candidato abacatudo = new Candidato("Abacatudo","77",CompromissoNacional,presidente);
        Candidato bananildo = new Candidato("Bananildo","23",Acao,presidente);

        presidente.adicionarCandidato(moranguete);
        presidente.adicionarCandidato(abacatudo);
        presidente.adicionarCandidato(bananildo);

        return List.of(deputado,presidente);
    }

}
