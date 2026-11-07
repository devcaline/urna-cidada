package br.edu.unijorge.urnacidada.repositorio;

import br.edu.unijorge.urnacidada.dominio.modelo.Voto;
import java.util.List;

/**
 * Contrato de armazenamento dos votos de uma sessão.
 * Implementação: {@code infraestrutura.RepositorioVotosFirestore} (Firebase Cloud Firestore).
 * <p>
 * Regras comuns a todas as implementações:
 * <ul>
 *   <li>rejeitar voto nulo (referência null) e voto não confirmado;</li>
 *   <li>idempotência: salvar duas vezes o mesmo ID grava um único registro (RNFs03);</li>
 *   <li>voto gravado não é alterado (RN13).</li>
 * </ul>
 * Consultas devem exigir autorização externa a esta interface (RN19/RN21).
 */
public interface RepositorioVotos {
    /**
     * Salva voto confirmado; retorno normal significa gravação concluída.
     * @throws IllegalArgumentException se o voto for null ou não estiver confirmado
     * @throws ErroRepositorioException se o armazenamento falhar (o voto NÃO foi garantido)
     */
    void salvar(Voto voto);

    /**
     * Lista registros confirmados do escopo autorizado; lista não nula, vazia se não houver.
     * @throws ErroRepositorioException se o armazenamento falhar
     */
    List<Voto> listar();
}
