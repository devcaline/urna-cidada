package br.edu.unijorge.urnacidada.dominio.modelo;

/** Papel de mesário; não armazena senha nem executa operações administrativas. */
public final class Mesario extends Usuario {
    public Mesario(String id, String nome) { super(id, nome); }

    @Override
    public String getPapel() { return "MESARIO"; }
}
