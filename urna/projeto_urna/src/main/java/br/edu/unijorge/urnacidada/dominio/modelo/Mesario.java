package br.edu.unijorge.urnacidada.dominio.modelo;

/** Mesário da sessão: guarda a senha administrativa e só permite conferi-la, nunca lê-la (RF14, RN18). */
public final class Mesario {
    private final String senha;

    public Mesario(String senha) {
        this.senha = Validacao.textoObrigatorio(senha, "Senha do mesário");
    }

    /** Confere a senha digitada sem expor a senha guardada. */
    public boolean validarSenha(String tentativa) {
        return tentativa != null && senha.equals(tentativa);
    }
}