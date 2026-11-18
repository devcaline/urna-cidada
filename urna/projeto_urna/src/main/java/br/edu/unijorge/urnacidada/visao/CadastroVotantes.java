package br.edu.unijorge.urnacidada.visao;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CadastroVotantes {

    // Atributos da classe Eleitor (herdados de Usuario conforme diagrama)
    private String id;
    private String nome;

    public CadastroVotantes(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void votar() {
        JOptionPane.showMessageDialog(null, "Eleitor " + nome + " apto para votar!");
    }

    public boolean autenticar() {
        return id != null && !id.isEmpty() && nome != null && !nome.isEmpty();
    }

    private static ArrayList<CadastroVotantes> listaVotantes = new ArrayList<>();

    public static void abrirCadastroVotantes() {
        JFrame janela = new JFrame("Cadastro de Votantes");
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setLayout(null);
        janela.setBounds(400, 200, 450, 400);

        JLabel titulo = new JLabel("Cadastro de Votantes", SwingConstants.CENTER);
        titulo.setBounds(0, 20, 450, 40);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel labelId = new JLabel("ID do Votante:");
        labelId.setBounds(50, 90, 150, 25);
        labelId.setFont(new Font("Arial", Font.PLAIN, 14));

        JTextField campoId = new JTextField();
        campoId.setBounds(200, 90, 200, 25);
        campoId.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel labelNome = new JLabel("Nome do Votante:");
        labelNome.setBounds(50, 135, 150, 25);
        labelNome.setFont(new Font("Arial", Font.PLAIN, 14));

        JTextField campoNome = new JTextField();
        campoNome.setBounds(200, 135, 200, 25);
        campoNome.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(75, 195, 300, 40);
        btnCadastrar.setBackground(Color.BLACK);
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrar.addActionListener(e -> {
            String idDigitado = campoId.getText().trim();
            String nomeDigitado = campoNome.getText().trim();

            if (idDigitado.isEmpty() || nomeDigitado.isEmpty()) {
                JOptionPane.showMessageDialog(janela, "Preencha todos os campos!");
                return;
            }

            for (CadastroVotantes v : listaVotantes) {
                if (v.getId().equals(idDigitado)) {
                    JOptionPane.showMessageDialog(janela, "ID já cadastrado!");
                    return;
                }
            }

            CadastroVotantes novoVotante = new CadastroVotantes(idDigitado, nomeDigitado);

            if (novoVotante.autenticar()) {
                campoId.setText("");
                campoNome.setText("");

                janela.setVisible(false);
                try {
                    Urna.abrirUrna();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(janela, "Dados inválidos. Cadastro não realizado.");
            }
        });

        JButton btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setBounds(75, 295, 140, 40);
        btnVoltar.setBackground(Color.darkGray);
        btnVoltar.setForeground(Color.BLACK);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.addActionListener(e -> {
            janela.setVisible(false);
            Menu.main();
        });

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(235, 295, 140, 40);
        btnLimpar.setBackground(Color.ORANGE);
        btnLimpar.setForeground(Color.BLACK);
        btnLimpar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLimpar.addActionListener(e -> {
            campoId.setText("");
            campoNome.setText("");
        });

        janela.add(titulo);
        janela.add(labelId);
        janela.add(campoId);
        janela.add(labelNome);
        janela.add(campoNome);

        janela.add(btnCadastrar);
        janela.add(btnVoltar);
        janela.add(btnLimpar);

        janela.setVisible(true);
    }

    private static void atualizarLista(JTextArea area) {
        StringBuilder sb = new StringBuilder();
        for (CadastroVotantes v : listaVotantes) {
            sb.append("ID: ").append(v.getId()).append(" | Nome: ").append(v.getNome()).append("\n");
        }
        area.setText(sb.toString());
    }
}

