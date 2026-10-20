package br.edu.unijorge.urnacidada.dominio.modelo;

/** Papel educativo opcional, sem vínculo com Voto e sem cadastro obrigatório de alunos. */
public final class Eleitor extends Usuario {
    public Eleitor(String id, String nome) { super(id, nome); }

    @Override
    public String getPapel() { return "ELEITOR"; }
}
