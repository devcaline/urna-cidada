import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import br.edu.unijorge.urnacidada.dados.CatalogoCandidatos;

public class ContagemVotos {

    // Armazena os votos por número do candidato
    private static final Map<String, Integer> votosCandidatos = new HashMap<>();

    // Armazena os votos em branco por nome do cargo
    private static final Map<String, Integer> votosBrancos = new HashMap<>();

    public static void registrarVotoCandidato(Candidato candidato) {
        if (candidato != null) {
            String numero = candidato.getNumero();
            votosCandidatos.put(numero, votosCandidatos.getOrDefault(numero, 0) + 1);
        }
    }

    public static void registrarVotoBranco(Cargo cargo) {
        if (cargo != null) {
            String nomeCargo = cargo.getNome();
            votosBrancos.put(nomeCargo, votosBrancos.getOrDefault(nomeCargo, 0) + 1);
        }
    }

    public static int getVotosCandidato(Candidato candidato) {
        if (candidato == null) return 0;
        return votosCandidatos.getOrDefault(candidato.getNumero(), 0);
    }

    public static int getVotosBrancos(Cargo cargo) {
        if (cargo == null) return 0;
        return votosBrancos.getOrDefault(cargo.getNome(), 0);
    }

    public static void abrir() {
        JFrame janela = new JFrame("Contagem de Votos");
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setBounds(80, 50, 850, 520);
        janela.getContentPane().setBackground(Color.GRAY);
        janela.setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Apuração e Contagem de Votos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        janela.add(titulo, BorderLayout.NORTH);

        // Tabela baseada na mesma lógica de ExibicaoCandidatos
        String[] colunas = {"Nome", "Número", "Partido", "Cargo", "Votos"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        List<Cargo> cargos = CatalogoCandidatos.criarCargos();
        for (Cargo cargo : cargos) {
            // Candidatos do cargo
            for (Candidato c : cargo.getCandidatos()) {
                modelo.addRow(new Object[]{
                        c.getNome(),
                        c.getNumero(),
                        c.getPartido().getSigla(),
                        cargo.getNome(),
                        getVotosCandidato(c)
                });
            }
            // Voto em branco do respectivo cargo
            modelo.addRow(new Object[]{
                    "VOTO EM BRANCO",
                    "-",
                    "-",
                    cargo.getNome(),
                    getVotosBrancos(cargo)
            });
        }

        JTable tabela = new JTable(modelo);
        tabela.setEnabled(false);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));
        tabela.setRowHeight(30);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        janela.add(scroll, BorderLayout.CENTER);

        janela.setVisible(true);
    }
}

