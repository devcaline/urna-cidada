package br.edu.unijorge.urnacidada.dominio.modelo;

/** Dados de um partido fictício, independentes de interface e armazenamento. */
public final class Partido {
    private final String nome;
    private final String sigla;

    public Partido(String nome, String sigla) {
        this.nome = Validacao.textoObrigatorio(nome, "Nome");
        this.sigla = Validacao.textoObrigatorio(sigla, "Sigla");
    }

    public String getNome() { return nome; }
    public String getSigla() { return sigla; }

    @Override
    public String toString() { return nome + " (" + sigla + ")"; }
}
