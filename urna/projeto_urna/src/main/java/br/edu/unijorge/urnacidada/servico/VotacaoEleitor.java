package br.edu.unijorge.urnacidada.servico;

import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;

import java.util.List;
import java.util.Optional;

public class VotacaoEleitor {
    private List<Cargo> cargos;
    private int etapaAtual;
    private String numeroDigitado = "";
    private boolean branco;

    public String getNumeroDigitado() {
        return numeroDigitado;
    }

    public boolean isBranco() {
        return branco;
    }

    public int getEtapaAtual() {
        return etapaAtual;
    }

    /** Todo eleitor começa na primeira etapa,
     * com o visor vazio e sem voto em branco. */
    public VotacaoEleitor(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    /** A votação termina quando a etapa passa
     * do último cargo da lista (RN08). */
    public boolean isEncerrada(){
        return etapaAtual == cargos.size();
    }

    /** Devolve o cargo que o eleitor está votando agora.
     * Não pode ser chamado após o encerramento. */
    public Cargo getCargoAtual(){
        if(isEncerrada()){
            throw new IllegalStateException("VOTAÇÃO ENCERRADA.");
        }
        return cargos.get(etapaAtual);
    }

    public void digitar(String digito){

        if(isEncerrada()){
            return;
        }

        if(branco){
            return;
        }

        if(isNumeroCompleto()){
            return;
        }

        numeroDigitado += digito;

    }

    public boolean isNumeroCompleto(){
        return numeroDigitado.length() >= getCargoAtual().getQuantidadeDigitos();
    }



    /** É voto nulo quando o número está completo mas não é de nenhum candidato (TSE). */
    public boolean isNulo(){
        return !branco && isNumeroCompleto() && getCandidatoEncontrado().isEmpty();
    }


    /** Senha do mesário para encerrar o loop de votação: 9990 em Deputado, 99 em Presidente. */
    public boolean isSenhaDeSaida(){
        if(isEncerrada() || branco){
            return false;
        }
        String senha = getCargoAtual().getQuantidadeDigitos() == 4 ? "9990" : "99";
        return numeroDigitado.equals(senha);
    }

    public Optional<Candidato> getCandidatoEncontrado(){
        return  getCargoAtual().buscarCandidato(getNumeroDigitado());
    }

    public void corrigir(){
        numeroDigitado = "";
        branco = false;
    }

    public void votarBranco(){
        if(isEncerrada()){
            return;
        }

        if(getNumeroDigitado().length() > 0){
            return;
        }

        branco = true;
    }

    public boolean confirmar(){
        if(isEncerrada()){
            return false;
        }

        boolean escolhaValida = branco || isNumeroCompleto();

        if(!escolhaValida){
            return false;
        }


        etapaAtual++;
        corrigir();

        return true;
    }

}
