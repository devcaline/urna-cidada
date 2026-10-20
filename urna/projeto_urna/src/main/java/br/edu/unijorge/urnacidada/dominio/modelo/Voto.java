package br.edu.unijorge.urnacidada.dominio.modelo;

import br.edu.unijorge.urnacidada.dominio.enums.TipoVoto;

/**
 * Retrato imutável de uma escolha. Construção não confirma, registra ou contabiliza votos.
 * O estado confirmado é informado pelo chamador; não comprova persistência ou autorização.
 */
public final class Voto {
    private final String id;
    private final TipoVoto tipo;
    private final Cargo cargo;
    private final Candidato candidato;
    private final boolean confirmado;

    public Voto(String id, TipoVoto tipo, Cargo cargo, Candidato candidato, boolean confirmado) {
        this.id = Validacao.textoObrigatorio(id, "Identificador");
        this.tipo = Validacao.obrigatorio(tipo, "Tipo de voto");
        this.cargo = Validacao.obrigatorio(cargo, "Cargo");
        if (tipo == TipoVoto.NULO) {
            throw new IllegalArgumentException("Representação de voto nulo aguarda definição da equipe.");
        }
        if (tipo == TipoVoto.CANDIDATO && (candidato == null || candidato.getCargo() != cargo)) {
            throw new IllegalArgumentException("Voto em candidato exige candidato do mesmo objeto Cargo.");
        }
        if (tipo == TipoVoto.BRANCO && candidato != null) {
            throw new IllegalArgumentException("Voto em branco não possui candidato.");
        }
        this.candidato = candidato;
        this.confirmado = confirmado;
    }

    public String getId() { return id; }
    public TipoVoto getTipo() { return tipo; }
    public Cargo getCargo() { return cargo; }
    /** Retorna null exclusivamente para BRANCO. */
    public Candidato getCandidato() { return candidato; }
    public boolean isConfirmado() { return confirmado; }
}
