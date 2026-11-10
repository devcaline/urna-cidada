package br.edu.unijorge.urnacidada.repositorio;

import br.edu.unijorge.urnacidada.dominio.modelo.Voto;

/**
 * Base comum dos repositórios de votos (herança + abstração).
 * Faz as validações do contrato uma única vez e delega a gravação para a subclasse.
 */
public abstract class RepositorioVotosBase implements RepositorioVotos {

    @Override
    public final void salvar(Voto voto) {
        if (voto == null) {
            throw new IllegalArgumentException("Voto deve ser informado.");
        }
        if (!voto.isConfirmado()) {
            throw new IllegalArgumentException("Somente votos confirmados podem ser registrados (RN06).");
        }
        gravar(voto);
    }

    /**
     * Grava um voto já validado. Se o ID já existir, não faz nada (idempotência).
     * @throws ErroRepositorioException se o armazenamento falhar
     */
    protected abstract void gravar(Voto voto);
}
