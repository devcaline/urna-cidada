package br.edu.unijorge.urnacidada.dominio.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Agrupa candidatos de um mesmo cargo. Montagem e consulta em uma única thread. */
public final class Cargo {
    private final String nome;
    private final int quantidadeDigitos;
    private final List<Candidato> candidatos = new ArrayList<>();

    public Cargo(String nome, int quantidadeDigitos) {
        this.nome = Validacao.textoObrigatorio(nome, "Nome");
        if (quantidadeDigitos <= 0) {
            throw new IllegalArgumentException("Quantidade de dígitos deve ser positiva.");
        }
        this.quantidadeDigitos = quantidadeDigitos;
    }

    public String getNome() { return nome; }
    public int getQuantidadeDigitos() { return quantidadeDigitos; }

    /** Retorna uma cópia imutável do estado atual da coleção. */
    public List<Candidato> getCandidatos() { return List.copyOf(candidatos); }

    /** A associação é explícita; construir um candidato não altera esta coleção. */
    public void adicionarCandidato(Candidato candidato) {
        Validacao.obrigatorio(candidato, "Candidato");
        if (candidato.getCargo() != this) {
            throw new IllegalArgumentException("Candidato pertence a outro objeto Cargo.");
        }
        if (buscarCandidato(candidato.getNumero()).isPresent()) {
            throw new IllegalArgumentException("Número já associado a este cargo.");
        }
        candidatos.add(candidato);
    }

    /** Número malformado ou ausente no catálogo resulta em Optional.empty(). */
    public Optional<Candidato> buscarCandidato(String numero) {
        if (!validarNumero(numero)) {
            return Optional.empty();
        }
        return candidatos.stream().filter(candidato -> candidato.getNumero().equals(numero)).findFirst();
    }

    /** Aceita exatamente a quantidade configurada de dígitos ASCII, incluindo zeros iniciais. */
    public boolean validarNumero(String numero) {
        return numero != null && numero.length() == quantidadeDigitos
                && numero.chars().allMatch(digito -> digito >= '0' && digito <= '9');
    }
}
