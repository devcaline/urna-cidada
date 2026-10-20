package br.edu.unijorge.urnacidada.repositorio;

import br.edu.unijorge.urnacidada.dominio.modelo.Voto;
import java.util.List;

/**
 * Contrato inicial, ainda sujeito à revisão da equipe; não há implementação nesta entrega.
 * Futuro adaptador deve rejeitar null e escolhas não confirmadas e garantir registro único
 * no escopo da sessão. Consultas devem exigir autorização externa a esta interface.
 * IDs, esquema, concorrência, falhas e política de repetição constam nas pendências.
 */
public interface RepositorioVotos {
    /** Salva voto confirmado; retorno normal deve significar gravação concluída. */
    void salvar(Voto voto);

    /** Lista registros confirmados do escopo autorizado; lista não nula, vazia se não houver. */
    List<Voto> listar();
}
