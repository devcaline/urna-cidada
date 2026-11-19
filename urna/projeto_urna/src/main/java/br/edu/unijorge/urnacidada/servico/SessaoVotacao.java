package br.edu.unijorge.urnacidada.servico;

import br.edu.unijorge.urnacidada.dominio.enums.EstadoSessao;

/** Ciclo da sessão de votação: não iniciada → em andamento → encerrada (RF16, RF17, RN16, RN23). */
public final class SessaoVotacao {
    private final boolean ensaio;
    private EstadoSessao estado = EstadoSessao.NAO_INICIADA;

    /** @param ensaio true no modo ENSAIO, em que votos de testes anteriores não bloqueiam o início */
    public SessaoVotacao(boolean ensaio) {
        this.ensaio = ensaio;
    }

    public EstadoSessao getEstado() { return estado; }

    public boolean isEmAndamento() { return estado == EstadoSessao.EM_ANDAMENTO; }

    /** Libera a votação depois da conferência inicial. Na oficina, exige a contagem zerada (RN16). */
    public void iniciar(int votosJaRegistrados) {
        if (estado != EstadoSessao.NAO_INICIADA) {
            throw new IllegalStateException("A sessão já foi iniciada.");
        }
        if (!ensaio && votosJaRegistrados > 0) {
            throw new IllegalStateException("A sessão já tem " + votosJaRegistrados
                    + " voto(s). A contagem precisa estar zerada antes de liberar a votação (RN16).");
        }
        estado = EstadoSessao.EM_ANDAMENTO;
    }

    /** Encerra a votação. Depois disso a urna não abre mais nesta execução (RN23). */
    public void encerrar() {
        if (!isEmAndamento()) {
            throw new IllegalStateException("Só é possível encerrar uma sessão em andamento.");
        }
        estado = EstadoSessao.ENCERRADA;
    }
}