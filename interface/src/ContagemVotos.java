import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Locale;

import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import br.edu.unijorge.urnacidada.dominio.modelo.Voto;
import br.edu.unijorge.urnacidada.dados.CatalogoCandidatos;
import br.edu.unijorge.urnacidada.infraestrutura.BancoDeDados;
import br.edu.unijorge.urnacidada.repositorio.ErroRepositorioException;
import br.edu.unijorge.urnacidada.servico.Apuracao;

public class ContagemVotos {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private static String formatarPercentual(double percentual) {
        return String.format(PT_BR, "%.1f%%", percentual);
    }

    public static void abrir() {
        // Lê os votos gravados no banco de dados (Firebase Firestore)
        List<Voto> votos;
        try {
            votos = BancoDeDados.votos().listar();
        } catch (ErroRepositorioException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Não foi possível carregar os votos do banco de dados.\n" + ex.getMessage(),
                    "Erro na apuração", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Apuracao apuracao = new Apuracao(votos);

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
        String[] colunas = {"Nome", "Número", "Partido", "Cargo", "Votos", "%"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        List<Cargo> cargos = CatalogoCandidatos.criarCargos();
        for (Cargo cargo : cargos) {
            // Candidatos do cargo
            for (Candidato c : cargo.getCandidatos()) {
                int votosCandidato = apuracao.votosDoCandidato(c);
                modelo.addRow(new Object[]{
                        c.getNome(),
                        c.getNumero(),
                        c.getPartido().getSigla(),
                        cargo.getNome(),
                        votosCandidato,
                        formatarPercentual(apuracao.percentual(votosCandidato, cargo))
                });
            }
            // Voto em branco do respectivo cargo
            int brancos = apuracao.votosEmBranco(cargo);
            modelo.addRow(new Object[]{
                    "VOTO EM BRANCO",
                    "-",
                    "-",
                    cargo.getNome(),
                    brancos,
                    formatarPercentual(apuracao.percentual(brancos, cargo))
            });

            // Voto nulo do respectivo cargo
            int nulos = apuracao.votosNulos(cargo);
            modelo.addRow(new Object[]{
                    "VOTO NULO",
                    "-",
                    "-",
                    cargo.getNome(),
                    nulos,
                    formatarPercentual(apuracao.percentual(nulos, cargo))
            });
        }

        JTable tabela = new JTable(modelo);
        tabela.setEnabled(false);
        tabela.setFont(new Font("Arial", Font.PLAIN, 14));
        tabela.setRowHeight(30);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        janela.add(scroll, BorderLayout.CENTER);

        // Rodapé: de onde vieram os dados e quantos votos foram lidos
        JLabel fonte = new JLabel("Fonte: " + BancoDeDados.descricao() + "  |  " + votos.size() + " voto(s) registrado(s)",
                SwingConstants.CENTER);
        fonte.setFont(new Font("Arial", Font.PLAIN, 12));
        fonte.setForeground(Color.WHITE);
        fonte.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        janela.add(fonte, BorderLayout.SOUTH);

        janela.setVisible(true);
    }
}
