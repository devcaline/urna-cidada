package br.edu.unijorge.urnacidada.dominio.modelo;

/** Dados imutáveis de um candidato e suas referências ao partido e ao cargo. */
public final class Candidato {
    private final String nome;
    private final String numero;
    private final Partido partido;
    private final Cargo cargo;

    public Candidato(String nome, String numero, Partido partido, Cargo cargo) {
        this.nome = Validacao.textoObrigatorio(nome, "Nome");
        this.partido = Validacao.obrigatorio(partido, "Partido");
        this.cargo = Validacao.obrigatorio(cargo, "Cargo");
        if (!cargo.validarNumero(numero)) {
            throw new IllegalArgumentException("Número incompatível com a quantidade de dígitos do cargo.");
        }
        this.numero = numero;
    }

    public String getNome() { return nome; }
    public String getNumero() { return numero; }
    public Partido getPartido() { return partido; }
    public Cargo getCargo() { return cargo; }
}
