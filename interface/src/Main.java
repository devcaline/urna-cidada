import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() throws IOException {
    JFrame janela = new JFrame();
    janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Encerrar ao fechar programa

    JLabel voto = new JLabel("_ _");
    voto.setBounds(250, 50, 200,40);
    voto.setFont(new Font("Arial", Font.BOLD, 45));

    Image sem_foto = ImageIO.read(new File("assents/sem_foto.jpg"));

    Image sem_foto_editada = sem_foto.getScaledInstance(230, 300, Image.SCALE_SMOOTH);

    ImageIcon icone = new ImageIcon(sem_foto_editada);
    JLabel foto = new JLabel(icone);
    foto.setBounds(50,150, 230, 300);
    // Botões numéricos

    JButton n1 = new JButton("1");
    n1.setBounds(715, 100, 50,50);
    n1.setBackground(Color.BLACK);
    n1.setForeground(Color.WHITE);

    JButton n2 = new JButton("2");
    n2.setBounds(775, 100, 50,50);
    n2.setBackground(Color.BLACK);
    n2.setForeground(Color.WHITE);

    JButton n3 = new JButton("3");
    n3.setBounds(835, 100, 50,50);
    n3.setBackground(Color.BLACK);
    n3.setForeground(Color.WHITE);

    JButton n4 = new JButton("4");
    n4.setBounds(715, 160, 50,50);
    n4.setBackground(Color.BLACK);
    n4.setForeground(Color.WHITE);

    JButton n5 = new JButton("5");
    n5.setBounds(775, 160, 50,50);
    n5.setBackground(Color.BLACK);
    n5.setForeground(Color.WHITE);

    JButton n6 = new JButton("6");
    n6.setBounds(835, 160, 50,50);
    n6.setBackground(Color.BLACK);
    n6.setForeground(Color.WHITE);

    JButton n7 = new JButton("7");
    n7.setBounds(715, 220, 50,50);
    n7.setBackground(Color.BLACK);
    n7.setForeground(Color.WHITE);

    JButton n8 = new JButton("8");
    n8.setBounds(775, 220, 50,50);
    n8.setBackground(Color.BLACK);
    n8.setForeground(Color.WHITE);

    JButton n9 = new JButton("9");
    n9.setBounds(835, 220, 50,50);
    n9.setBackground(Color.BLACK);
    n9.setForeground(Color.WHITE);

    JButton n0 = new JButton("0");
    n0.setBounds(775, 280, 50,50);
    n0.setBackground(Color.BLACK);
    n0.setForeground(Color.WHITE);

    JButton confirma = new JButton("CONFIRMA");
    confirma.setBounds(860, 400, 100, 40);
    confirma.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Voto confirmado!");
            voto.setText("FIM");
        }

    });
    confirma.setBackground(Color.GREEN);



    JButton corrige = new JButton("CORRIGE");
    corrige.setBounds(755, 405, 100, 35);
    corrige.setBackground(Color.orange);


    JButton branco = new JButton("BRANCO");
    branco.setBounds(650, 405, 100, 35);
    branco.setBackground(Color.white);


    // ADICIONANDO ELEMENTOS NA JANELA
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

    janela.setLayout(null);
    // Definir tamanho | x: eixo horizontal y: eixo vertical
    janela.setBounds(400,200,1000,550);
    janela.setVisible(true); // Janela fica visível
}
