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

        boolean escolhaValida = branco || getCandidatoEncontrado().isPresent();

        if(!escolhaValida){
            return false;
        }


        etapaAtual++;
        corrigir();

        return true;
    }

}
