import javax.swing.*;
import java.awt.*;

import br.edu.unijorge.urnacidada.infraestrutura.BancoDeDados;


public class Menu {

    public static void main() {
        JFrame janela = new JFrame("Menu Principal");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(null);
        janela.setBounds(400, 200, 400, 395);

        // Título
        JLabel titulo = new JLabel("Sistema de Votação", SwingConstants.CENTER);
        titulo.setBounds(0, 20, 400, 40);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        // Botão: Exibição de Candidatos
        JButton btnCandidatos = new JButton("Exibição de Candidatos");
        btnCandidatos.setBounds(75, 90, 250, 45);
        btnCandidatos.setBackground(Color.BLACK);
        btnCandidatos.setForeground(Color.WHITE);
        btnCandidatos.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCandidatos.addActionListener(e -> {
            ExibicaoCandidatos.abrir();
        });

        // Botão: Relatório
        /*JButton btnRelatorio = new JButton("Relatório");
        btnRelatorio.setBounds(75, 210, 250, 45);
        btnRelatorio.setBackground(Color.BLACK);
        btnRelatorio.setForeground(Color.WHITE);
        btnRelatorio.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRelatorio.addActionListener(e -> {
            // TODO: abrir interface de Relatório
            JOptionPane.showMessageDialog(janela, "Tela de Relatório (em desenvolvimento)");
        });*/

        // Botão: Contagem de Votos
        JButton btnContagem = new JButton("Contagem de Votos");
        btnContagem.setBounds(75, 150, 250, 45);
        btnContagem.setBackground(Color.BLACK);
        btnContagem.setForeground(Color.WHITE);
        btnContagem.setFont(new Font("Arial", Font.PLAIN, 14));
        btnContagem.addActionListener(e -> {
            ContagemVotos.abrir();
        });

        // Botão: Urna Eletrônica → abre a interface do Main.java
        JButton btnUrna = new JButton("Iniciar Votação");
        btnUrna.setBounds(75, 230, 250, 45);
        btnUrna.setBackground(Color.GREEN);
        btnUrna.setForeground(Color.BLACK);
        btnUrna.setFont(new Font("Arial", Font.BOLD, 14));
        btnUrna.addActionListener(e -> {
            janela.setVisible(false);
            CadastroVotantes.abrirCadastroVotantes();
        });

        // Aviso do modo em uso, para ninguém votar na sessão errada
        String sessao = BancoDeDados.idSessao();
        boolean ensaio = BancoDeDados.SESSAO_ENSAIO.equals(sessao);
        JLabel lblSessao = new JLabel(ensaio
                ? "MODO ENSAIO (teste) — sessão " + sessao
                : "VOTAÇÃO REAL — sessão " + sessao, SwingConstants.CENTER);
        lblSessao.setBounds(0, 295, 400, 25);
        lblSessao.setFont(new Font("Arial", Font.BOLD, 13));
        lblSessao.setForeground(ensaio ? new Color(210, 100, 0) : new Color(0, 130, 0));

        janela.add(titulo);
        janela.add(btnCandidatos);

        janela.add(btnContagem);
        janela.add(btnUrna);
        janela.add(lblSessao);

        janela.setVisible(true);
    }

    /**
     * Pergunta, ao abrir o programa, se é um ensaio (teste) ou a votação real da oficina.
     * @return false se a janela foi fechada sem escolher
     */
    private static boolean escolherSessao() {
        String[] opcoes = {"ENSAIO (teste)", "OFICINA (votação real)"};
        int escolha = JOptionPane.showOptionDialog(null,
                "Como a urna vai ser usada agora?\n\n"
                        + "ENSAIO: para testar e demonstrar. Os votos vão para a sessão \""
                        + BancoDeDados.SESSAO_ENSAIO + "\".\n"
                        + "OFICINA: votação de verdade com os alunos. Os votos vão para a sessão \""
                        + BancoDeDados.SESSAO_PADRAO + "\".",
                "Urna Cidadã — escolha o modo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
        if (escolha == 0) {
            BancoDeDados.usarSessao(BancoDeDados.SESSAO_ENSAIO);
            return true;
        }
        if (escolha == 1) {
            BancoDeDados.usarSessao(BancoDeDados.SESSAO_PADRAO);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        if (!escolherSessao()) {
            System.exit(0);
        }
        main();
    }
}
