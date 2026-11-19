package br.edu.unijorge.urnacidada.visao;

import javax.swing.*;
import java.awt.*;

import br.edu.unijorge.urnacidada.dominio.enums.EstadoSessao;
import br.edu.unijorge.urnacidada.infraestrutura.BancoDeDados;
import br.edu.unijorge.urnacidada.repositorio.ErroRepositorioException;
import br.edu.unijorge.urnacidada.servico.SessaoVotacao;


public class Menu {

    // Sessão de votação desta execução do programa (RF16, RF17, RN23)
    private static SessaoVotacao sessao;

    public static SessaoVotacao sessao() {
        return sessao;
    }

    public static void abrir() {
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

        // Botão: Contagem de Votos
        JButton btnContagem = new JButton("Contagem de Votos");
        btnContagem.setBounds(75, 150, 250, 45);
        btnContagem.setBackground(Color.BLACK);
        btnContagem.setForeground(Color.WHITE);
        btnContagem.setFont(new Font("Arial", Font.PLAIN, 14));
        btnContagem.addActionListener(e -> {
            if (AcessoMesario.confirmar(janela, "ver a contagem de votos")) {
                ContagemVotos.abrir();
            }
        });

        // Botão: Abrir Sessão → senha do mesário, conferência inicial e Urna
        JButton btnUrna = new JButton("Abrir Sessão");
        btnUrna.setBounds(75, 230, 250, 45);
        btnUrna.setBackground(Color.GREEN);
        btnUrna.setForeground(Color.BLACK);
        btnUrna.setFont(new Font("Arial", Font.BOLD, 14));
        btnUrna.addActionListener(e -> {
            if (sessao.getEstado() == EstadoSessao.ENCERRADA) {
                JOptionPane.showMessageDialog(janela,
                        "Esta sessão foi encerrada. Para uma nova votação, feche e abra o programa (RN23).");
                return;
            }
            if (!AcessoMesario.confirmar(janela, "abrir a sessão")) {
                return;
            }
            if (!sessao.isEmAndamento() && !conferirContagemInicial(janela)) {
                return;
            }
            janela.dispose();
            try {
                Urna.abrirUrna();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Aviso do modo em uso, para ninguém votar na sessão errada
        String idSessao = BancoDeDados.idSessao();
        boolean ensaio = BancoDeDados.SESSAO_ENSAIO.equals(idSessao);
        JLabel lblSessao = new JLabel(ensaio
                ? "MODO ENSAIO (teste) — sessão " + idSessao
                : "VOTAÇÃO REAL — sessão " + idSessao, SwingConstants.CENTER);
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

    /** Conferência inicial (RF15, RN16): mostra os votos já registrados e libera a votação. */
    private static boolean conferirContagemInicial(JFrame janela) {
        int votos;
        try {
            votos = BancoDeDados.votos().listar().size();
        } catch (ErroRepositorioException ex) {
            JOptionPane.showMessageDialog(janela,
                    "SEM CONEXÃO COM O BANCO DE DADOS (FIREBASE).\n\n" + ex.getMessage(),
                    "Conferência inicial", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String texto = "Sessão: " + BancoDeDados.idSessao()
                + "\nVotos já registrados: " + votos
                + (votos == 0 ? "\nTodos os candidatos estão com 0 votos (0%)." : "")
                + "\n\nLiberar a votação?";
        int opcao = JOptionPane.showConfirmDialog(janela, texto, "Conferência inicial",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcao != JOptionPane.OK_OPTION) {
            return false;
        }
        try {
            sessao.iniciar(votos);
            return true;
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(janela, ex.getMessage(), "Conferência inicial", JOptionPane.WARNING_MESSAGE);
            return false;
        }
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
        sessao = new SessaoVotacao(BancoDeDados.SESSAO_ENSAIO.equals(BancoDeDados.idSessao()));
        abrir();
    }
}