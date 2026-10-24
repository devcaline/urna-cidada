import javax.swing.*;
import java.awt.*;


public class Menu {

    public static void main(String[] args) {
        JFrame janela = new JFrame("Menu Principal");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(null);
        janela.setBounds(400, 200, 400, 450);

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
            // TODO: abrir interface de Exibição de Candidatos
            JOptionPane.showMessageDialog(janela, "Tela de Exibição de Candidatos (em desenvolvimento)");
        });

        // Botão: Cadastro de Votantes
        JButton btnVotantes = new JButton("Cadastro de Votantes");
        btnVotantes.setBounds(75, 150, 250, 45);
        btnVotantes.setBackground(Color.BLACK);
        btnVotantes.setForeground(Color.WHITE);
        btnVotantes.setFont(new Font("Arial", Font.PLAIN, 14));
        btnVotantes.addActionListener(e -> {
            // TODO: abrir interface de Cadastro de Votantes
            JOptionPane.showMessageDialog(janela, "Tela de Cadastro de Votantes (em desenvolvimento)");
        });

        // Botão: Relatório
        JButton btnRelatorio = new JButton("Relatório");
        btnRelatorio.setBounds(75, 210, 250, 45);
        btnRelatorio.setBackground(Color.BLACK);
        btnRelatorio.setForeground(Color.WHITE);
        btnRelatorio.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRelatorio.addActionListener(e -> {
            // TODO: abrir interface de Relatório
            JOptionPane.showMessageDialog(janela, "Tela de Relatório (em desenvolvimento)");
        });

        // Botão: Contagem de Votos
        JButton btnContagem = new JButton("Contagem de Votos");
        btnContagem.setBounds(75, 270, 250, 45);
        btnContagem.setBackground(Color.BLACK);
        btnContagem.setForeground(Color.WHITE);
        btnContagem.setFont(new Font("Arial", Font.PLAIN, 14));
        btnContagem.addActionListener(e -> {
            // TODO: abrir interface de Contagem de Votos
            JOptionPane.showMessageDialog(janela, "Tela de Contagem de Votos (em desenvolvimento)");
        });

        // Botão: Urna Eletrônica → abre a interface do Main.java
        JButton btnUrna = new JButton("Urna Eletrônica");
        btnUrna.setBounds(75, 330, 250, 45);
        btnUrna.setBackground(Color.GREEN);
        btnUrna.setForeground(Color.BLACK);
        btnUrna.setFont(new Font("Arial", Font.BOLD, 14));
        btnUrna.addActionListener(e -> {
            janela.setVisible(false);
            try {
                Main.abrirUrna();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        janela.add(titulo);
        janela.add(btnCandidatos);
        janela.add(btnVotantes);
        janela.add(btnRelatorio);
        janela.add(btnContagem);
        janela.add(btnUrna);

        janela.setVisible(true);
    }
}
