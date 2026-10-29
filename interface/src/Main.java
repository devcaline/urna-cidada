import br.edu.unijorge.urnacidada.dados.CatalogoCandidatos;
import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.servico.VotacaoEleitor;
import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Main {



    public static void adicionar_numero(JLabel texto, String número) {
        int idc = 0;
        if (texto.getText().charAt(0) == '_') {
            texto.setText(texto.getText().substring(0, idc) + número + texto.getText().substring(idc + 1));
        } else if (texto.getText().charAt(2) == '_') {
            texto.setText(texto.getText().substring(0, idc + 2) + número + texto.getText().substring(idc + 3));
        }

    }

    public static String formatarVisor(String numero, int digitos){
        String visor = "";
        for( int i=0; i < digitos; i++){
            if(i<numero.length()){
                visor += numero.charAt(i);
            }else{
                visor += "_";
            }

            visor += " ";
        }
        return visor;
    }

    public static void atualizarTela(VotacaoEleitor votacao, JLabel voto, JLabel nome, JLabel partido){
        if(votacao.isEncerrada()){
            return;
        }

        voto.setText(formatarVisor(votacao.getNumeroDigitado(),
                votacao.getCargoAtual().getQuantidadeDigitos()));

        if(votacao.isBranco()){
            voto.setText("VOTO EM BRANCO");
            nome.setText("");
            partido.setText("");
            return;
        }

        if(votacao.isNumeroCompleto()){
            Optional<Candidato> encontrado = votacao.getCandidatoEncontrado();

            if(encontrado.isPresent()){
                nome.setText((encontrado.get().getNome()));
                partido.setText(encontrado.get().getPartido().getSigla());
            }else{
                nome.setText("CANDIDATO NÃO ENCONTRADO");
                partido.setText("");
            }
        }else{
            nome.setText("");
            partido.setText("");
        }
    }

    public static void abrirUrna() throws IOException {
        VotacaoEleitor votacao = new VotacaoEleitor(CatalogoCandidatos.criarCargos());

        JFrame janela = new JFrame("Urna Eletrônica");
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel voto = new JLabel(formatarVisor(votacao.getNumeroDigitado(), votacao.getCargoAtual().getQuantidadeDigitos()));
        voto.setBounds(200, 50, 450, 40);
        voto.setFont(new Font("Arial", Font.BOLD, 45));

        JLabel cargo = new JLabel(votacao.getCargoAtual().getNome());
        cargo.setBounds(200, 15, 400, 30);
        cargo.setFont(new Font("Arial",Font.BOLD, 22));

        JLabel nome = new JLabel("");
        nome.setBounds(420, 200, 300, 30);
        nome.setFont(new Font("Arial", Font.BOLD, 22));
        JLabel partido = new JLabel("");
        partido.setBounds(420,240, 300, 30);
        partido.setFont(new Font("Arial", Font.PLAIN, 18));


        Image sem_foto = ImageIO.read(new File("assents/sem_foto.jpg"));
        Image sem_foto_editada = sem_foto.getScaledInstance(230, 300, Image.SCALE_SMOOTH);
        ImageIcon icone = new ImageIcon(sem_foto_editada);
        JLabel foto = new JLabel(icone);
        foto.setBounds(127, 150, 230, 300);

        // Botões numéricos
        JButton n1 = new JButton("1");
        n1.setBounds(715, 100, 50, 50);
        n1.setBackground(Color.BLACK);
        n1.setForeground(Color.WHITE);
        n1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                votacao.digitar("1");
                atualizarTela(votacao, voto, nome, partido);
            }
        });

        JButton n2 = new JButton("2");
        n2.setBounds(775, 100, 50, 50);
        n2.setBackground(Color.BLACK);
        n2.setForeground(Color.WHITE);
        n2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                votacao.digitar("2");
                atualizarTela(votacao, voto, nome, partido);
            }
        });

        JButton n3 = new JButton("3");
        n3.setBounds(835, 100, 50, 50);
        n3.setBackground(Color.BLACK);
        n3.setForeground(Color.WHITE);
        n3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                votacao.digitar("3");
                atualizarTela(votacao, voto, nome, partido);
            }
        });

        JButton n4 = new JButton("4");
        n4.setBounds(715, 160, 50, 50);
        n4.setBackground(Color.BLACK);
        n4.setForeground(Color.WHITE);
        n4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                votacao.digitar("4");
                atualizarTela(votacao, voto, nome, partido);
            }
        });

        JButton n5 = new JButton("5");
        n5.setBounds(775, 160, 50, 50);
        n5.setBackground(Color.BLACK);
        n5.setForeground(Color.WHITE);
        n5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                votacao.digitar("5");
                atualizarTela(votacao, voto, nome, partido);
            }
        });

        JButton n6 = new JButton("6");
        n6.setBounds(835, 160, 50, 50);
        n6.setBackground(Color.BLACK);
        n6.setForeground(Color.WHITE);
        n6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                votacao.digitar("6");
                atualizarTela(votacao, voto, nome, partido);
            }
        });

        JButton n7 = new JButton("7");
        n7.setBounds(715, 220, 50, 50);
        n7.setBackground(Color.BLACK);
        n7.setForeground(Color.WHITE);
        n7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                votacao.digitar("7");
                atualizarTela(votacao, voto, nome, partido);
            }
        });

        JButton n8 = new JButton("8");
        n8.setBounds(775, 220, 50, 50);
        n8.setBackground(Color.BLACK);
        n8.setForeground(Color.WHITE);
        n8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                votacao.digitar("8");
                atualizarTela(votacao, voto, nome, partido);
            }
        });

        JButton n9 = new JButton("9");
        n9.setBounds(835, 220, 50, 50);
        n9.setBackground(Color.BLACK);
        n9.setForeground(Color.WHITE);
        n9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                votacao.digitar("9");
                atualizarTela(votacao, voto, nome, partido);
            }
        });

        JButton n0 = new JButton("0");
        n0.setBounds(775, 280, 50, 50);
        n0.setBackground(Color.BLACK);
        n0.setForeground(Color.WHITE);
        n0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                votacao.digitar("0");
                atualizarTela(votacao, voto, nome, partido);
            }
        });

        JButton confirma = new JButton("CONFIRMA");
        confirma.setBounds(860, 400, 100, 40);
        confirma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean confirmou = votacao.confirmar();

                // 1. não confirmou: avisa o motivo
                if(!confirmou){
                    if(!votacao.isNumeroCompleto()){
                        JOptionPane.showMessageDialog(janela, "NÚMERO INCOMPLETO");
                    }else{
                        JOptionPane.showMessageDialog(janela, "CANDIDATO NÃO ENCONTRADO");
                    }
                    return;
                }

                // 2. confirmou o último cargo: FIM
                if(votacao.isEncerrada()){
                    voto.setText("FIM");
                    cargo.setText("");
                    nome.setText("");
                    partido.setText("");
                    return;
                }

                // 3. confirmou e ainda tem cargo: mostra o próximo
                    cargo.setText(votacao.getCargoAtual().getNome());
                    atualizarTela(votacao, voto, nome, partido);
                    }

        });
        confirma.setBackground(Color.GREEN);

        JButton corrige = new JButton("CORRIGE");
        corrige.setBounds(755, 405, 100, 35);
        corrige.setBackground(Color.ORANGE);
        corrige.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                votacao.corrigir();
                atualizarTela(votacao, voto, nome, partido);
            }
        });

        JButton branco = new JButton("BRANCO");
        branco.setBounds(650, 405, 100, 35);
        branco.setBackground(Color.WHITE);
        branco.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                votacao.votarBranco();
                atualizarTela(votacao, voto, nome, partido);
            }
        });

        // Adicionando elementos na janela
        janela.add(n1);
        janela.add(n2);
        janela.add(n3);
        janela.add(n4);
        janela.add(n5);
        janela.add(n6);
        janela.add(n7);
        janela.add(n8);
        janela.add(n9);
        janela.add(n0);
        janela.add(confirma);
        janela.add(branco);
        janela.add(corrige);
        janela.add(foto);
        janela.add(voto);
        janela.add(cargo);
        janela.add(partido);
        janela.add(nome);


        janela.setLayout(null);
        janela.setBounds(400, 200, 1000, 550);
        ((javax.swing.JComponent) janela.getContentPane())
                .setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK, 10));
        janela.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        abrirUrna();
    }
}
