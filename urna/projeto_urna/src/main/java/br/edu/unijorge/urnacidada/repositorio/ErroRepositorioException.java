package br.edu.unijorge.urnacidada.repositorio;

/**
 * Falha ao gravar ou ler dados no armazenamento (rede fora do ar, permissão negada, tempo esgotado...).
 * A interface gráfica deve capturar esta exceção e avisar o mesário, sem avançar a votação.
 */
public class ErroRepositorioException extends RuntimeException {
    public ErroRepositorioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

    public ErroRepositorioException(String mensagem) {
        super(mensagem);
    }
}
