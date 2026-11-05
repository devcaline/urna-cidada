import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import br.edu.unijorge.urnacidada.dados.CatalogoCandidatos;
import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;

public class ExibicaoCandidatos {

    public static void abrir() {
        JFrame janela = new JFrame("Exibição de Candidatos");
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setBounds(80, 50, 800, 500);
        janela.getContentPane().setBackground(Color.GRAY);
        janela.setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Candidatos Cadastrados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        janela.add(titulo, BorderLayout.NORTH);

        // Tabela
        String[] colunas = {"Nome", "Número", "Partido", "Cargo"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        List<Cargo> cargos = CatalogoCandidatos.criarCargos();
        for (Cargo cargo : cargos) {
            for (Candidato c : cargo.getCandidatos()) {
                modelo.addRow(new Object[]{
                        c.getNome(),
                        c.getNumero(),
                        c.getPartido().getSigla(),
                        cargo.getNome()
                });
            }
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
