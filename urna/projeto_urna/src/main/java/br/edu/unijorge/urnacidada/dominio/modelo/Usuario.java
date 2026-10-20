package br.edu.unijorge.urnacidada.dominio.modelo;

/** Identificação ilustrativa de papéis; não autentica nem concede permissões. */
public abstract class Usuario {
    private final String id;
    private final String nome;

    protected Usuario(String id, String nome) {
        this.id = Validacao.textoObrigatorio(id, "Identificador");
        this.nome = Validacao.textoObrigatorio(nome, "Nome");
    }

    public final String getId() { return id; }
    public final String getNome() { return nome; }
    public abstract String getPapel();
}
