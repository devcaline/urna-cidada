import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import br.edu.unijorge.urnacidada.dados.CatalogoCandidatos;
import br.edu.unijorge.urnacidada.dominio.modelo.Candidato;
import br.edu.unijorge.urnacidada.dominio.modelo.Cargo;
import br.edu.unijorge.urnacidada.servico.VotacaoEleitor;

public class Urna {


    public static JLabel carregar_imagem(String src){
        JLabel foto = new JLabel();
        foto.setBounds(25, 100, 140, 175);
        foto.setBorder(BorderFactory.createLineBorder(new Color(170, 170, 178), 1));
        foto.setBackground(new Color(185, 185, 190));
        foto.setOpaque(true);
        foto.setHorizontalAlignment(SwingConstants.CENTER);
        try {

            Image img = ImageIO.read(new File(src));
            foto.setIcon(new ImageIcon(img.getScaledInstance(140, 175, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            foto.setFont(new Font("Arial", Font.PLAIN, 11));
            foto.setForeground(new Color(100, 100, 105));
            foto.setText("<html><center>SEM<br>FOTO</center></html>");
        }
        return  foto;
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

    public static void atualizarTela(VotacaoEleitor votacao, JLabel voto, JLabel nome, JLabel partido, JLabel foto,JLabel vice){
        if(votacao.isEncerrada()){
            return;
        }
        vice.setText("");

        voto.setText(formatarVisor(votacao.getNumeroDigitado(),
                votacao.getCargoAtual().getQuantidadeDigitos()));

        if(votacao.isBranco()){
            voto.setText("BRANCO");
            nome.setText("BRANCO");
            partido.setText("PARTIDO INEXISTENTE");
            return;
        }

        if(votacao.isNumeroCompleto()){
            Optional<Candidato> encontrado = votacao.getCandidatoEncontrado();

            if(encontrado.isPresent()){
                nome.setText((encontrado.get().getNome()));
                partido.setText(encontrado.get().getPartido().getSigla());
                if(encontrado.get().getVice() != null){                        // ← NOVO
                    vice.setText("VICE: " + encontrado.get().getVice());
                }
                try{
                Image img = ImageIO.read(new File(encontrado.get().getFoto_src()));

                foto.setIcon(new ImageIcon(
                        img.getScaledInstance(140, 175, Image.SCALE_SMOOTH)
                ));

                foto.setText("");

                }catch (Exception e){
                    System.out.println("erro!");

                }

            }else{
                nome.setText("VOTO NULO");
                partido.setText("NÚMERO ERRADO");
                try{
                    Image img = ImageIO.read(new File("assents/sem_foto.jpg"));

                    foto.setIcon(new ImageIcon(
                            img.getScaledInstance(140, 175, Image.SCALE_SMOOTH)
                    ));

                    foto.setText("");

                }catch (Exception e){
                    System.out.println("erro!");

                }
            }
        }else{
            nome.setText("");
            partido.setText("");
            try{
                Image img = ImageIO.read(new File("assents/sem_foto.jpg"));

                foto.setIcon(new ImageIcon(
                        img.getScaledInstance(140, 175, Image.SCALE_SMOOTH)
                ));

                foto.setText("");

            }catch (Exception e){
                System.out.println("erro!");

            }
        }
    }

    // ── Fábrica de botão numérico (estilo numpad escuro) ─────────────────────
    private static JButton criarBotaoNum(String label) {
        JButton btn = new JButton(label);
        btn.setBackground(new Color(42, 42, 46));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 26));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ── Abertura da janela principal ──────────────────────────────────────────
    public static void abrirUrna() throws IOException {
        VotacaoEleitor votacao = new VotacaoEleitor(CatalogoCandidatos.criarCargos());

        // Dimensões globais
        final int W = 900, H = 575;
        final int HDR_H = 65, FOOTER_H = 45;
        final int MAIN_H = H - HDR_H - FOOTER_H;   // 465
        final int NUMPAD_X = 515, NUMPAD_W = W - NUMPAD_X;  // 385

        JFrame janela = new JFrame("Urna Eletrônica");
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setLayout(null);
        janela.setResizable(false);
        janela.setBounds(200, 80, W, H);

        // ════════════════════════════════════════════════════════════════════
        //  HEADER — fundo escuro, logo TSE + relógio
        // ════════════════════════════════════════════════════════════════════
        JPanel header = new JPanel(null);
        header.setBackground(new Color(28, 30, 36));
        header.setBounds(0, 0, W, HDR_H);

        // Brasão / logo placeholder
        JLabel lblBrasao = new JLabel("UJ", SwingConstants.CENTER);
        lblBrasao.setBounds(10, 8, 48, 48);
        lblBrasao.setFont(new Font("Serif", Font.PLAIN, 36));
        //lblBrasao.setForeground(new Color(210, 195, 140));
        lblBrasao.setForeground(Color.blue);

        // Título TSE
        JLabel lblJE = new JLabel("URNA CIDADÃ");
        lblJE.setBounds(65, 10, 280, 22);
        lblJE.setFont(new Font("Arial", Font.BOLD, 14));
        lblJE.setForeground(Color.WHITE);

        JLabel lblTSE = new JLabel("CENTRO UNIVERSITÁRIO JORGE AMADO");
        lblTSE.setBounds(65, 33, 280, 16);
        lblTSE.setFont(new Font("Arial", Font.PLAIN, 10));
        lblTSE.setForeground(new Color(160, 160, 170));

        // Divisor vertical 1
        JPanel div1 = new JPanel();
        div1.setBackground(new Color(75, 75, 85));
        div1.setBounds(510, 12, 1, 40);

        // Eleições / Urna
        JLabel lblEleicoes = new JLabel("ELEIÇÕES 2026");
        lblEleicoes.setBounds(525, 10, 180, 18);
        lblEleicoes.setFont(new Font("Arial", Font.PLAIN, 11));
        lblEleicoes.setForeground(new Color(160, 160, 170));

        JLabel lblUrnaEl = new JLabel("URNA ELETRÔNICA");
        lblUrnaEl.setBounds(525, 30, 180, 16);
        lblUrnaEl.setFont(new Font("Arial", Font.PLAIN, 10));
        lblUrnaEl.setForeground(new Color(130, 130, 140));

        // Divisor vertical 2
        JPanel div2 = new JPanel();
        div2.setBackground(new Color(75, 75, 85));
        div2.setBounds(720, 12, 1, 40);

        // Relógio — hora e data reais
        LocalDateTime now = LocalDateTime.now();
        JLabel lblHora = new JLabel(now.format(DateTimeFormatter.ofPattern("HH:mm")));
        lblHora.setBounds(735, 8, 150, 28);
        lblHora.setFont(new Font("Arial", Font.BOLD, 20));
        lblHora.setForeground(Color.WHITE);

        JLabel lblData = new JLabel(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lblData.setBounds(735, 36, 150, 16);
        lblData.setFont(new Font("Arial", Font.PLAIN, 10));
        lblData.setForeground(new Color(160, 160, 170));

        header.add(lblBrasao);
        header.add(lblJE);
        header.add(lblTSE);
        header.add(div1);
        header.add(lblEleicoes);
        header.add(lblUrnaEl);
        header.add(div2);
        header.add(lblHora);
        header.add(lblData);

        // ════════════════════════════════════════════════════════════════════
        //  PAINEL PRINCIPAL — cinza claro
        // ════════════════════════════════════════════════════════════════════
        JPanel main = new JPanel(null);
        main.setBackground(new Color(215, 216, 220));
        main.setBounds(0, HDR_H, W, MAIN_H);

        // ── Lado esquerdo: informações do candidato ───────────────────────
        JLabel lblSeuVoto = new JLabel("SEU VOTO PARA");
        lblSeuVoto.setBounds(25, 18, 250, 18);
        lblSeuVoto.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSeuVoto.setForeground(new Color(75, 75, 80));

        JLabel cargo = new JLabel(votacao.getCargoAtual().getNome());
        cargo.setBounds(25, 36, 380, 40);
        cargo.setFont(new Font("Arial", Font.BOLD, 30));
        cargo.setForeground(new Color(12, 12, 18));

        // Linha separadora horizontal
        JPanel linhaSep = new JPanel();
        linhaSep.setBackground(new Color(170, 170, 178));
        linhaSep.setBounds(0, 83, NUMPAD_X, 1);

        // Foto do candidato
        JLabel foto = carregar_imagem("assents/sem_foto.jpg");

        // NÚMERO
        JLabel lblNumeroTitulo = new JLabel("NÚMERO");
        lblNumeroTitulo.setBounds(192, 100, 200, 17);
        lblNumeroTitulo.setFont(new Font("Arial", Font.PLAIN, 11));
        lblNumeroTitulo.setForeground(new Color(90, 90, 95));

        // Display do voto (lógica preservada do Main)
        JLabel voto = new JLabel(formatarVisor(votacao.getNumeroDigitado(), votacao.getCargoAtual().getQuantidadeDigitos()));
        voto.setBounds(185, 114, 300, 68);
        voto.setFont(new Font("Arial", Font.BOLD, 58));
        voto.setForeground(new Color(10, 10, 15));

        // CANDIDATO
        JLabel lblCandidatoTitulo = new JLabel("CANDIDATO");
        lblCandidatoTitulo.setBounds(192, 190, 250, 17);
        lblCandidatoTitulo.setFont(new Font("Arial", Font.PLAIN, 11));
        lblCandidatoTitulo.setForeground(new Color(90, 90, 95));

        JLabel nome = new JLabel("");
        nome.setBounds(192, 208, 295, 28);
        nome.setFont(new Font("Arial", Font.BOLD, 18));
        nome.setForeground(new Color(10, 10, 15));

        JLabel partido = new JLabel("");
        partido.setBounds(192, 237, 250, 18);
        partido.setFont(new Font("Arial", Font.PLAIN, 12));
        partido.setForeground(new Color(90, 90, 95));

        JLabel vice = new JLabel("");
        vice.setBounds(192, 256, 250, 18);
        vice.setFont(new Font("Arial", Font.PLAIN, 12));
        vice.setForeground(new Color(90, 90, 95));

        main.add(lblSeuVoto);
        main.add(cargo);
        main.add(linhaSep);
        main.add(foto);
        main.add(nome);
        main.add(partido);
        main.add(vice);
        main.add(lblNumeroTitulo);
        main.add(voto);
        main.add(lblCandidatoTitulo);
        main.add(nome);
        main.add(partido);

        // ── Lado direito: numpad escuro ───────────────────────────────────
        JPanel numpad = new JPanel(null);
        numpad.setBackground(new Color(28, 30, 36));
        numpad.setBounds(NUMPAD_X, 0, NUMPAD_W, MAIN_H);

        // Grade de botões: 3 colunas × 4 linhas (última é o 0 centralizado)
        final int BW = 98, BH = 63;
        final int SX = 22, SY = 22, GX = 11, GY = 10;

        JButton n1 = criarBotaoNum("1"); n1.setBounds(SX,              SY,               BW, BH);
        JButton n2 = criarBotaoNum("2"); n2.setBounds(SX + (BW+GX),    SY,               BW, BH);
        JButton n3 = criarBotaoNum("3"); n3.setBounds(SX + (BW+GX)*2,  SY,               BW, BH);

        JButton n4 = criarBotaoNum("4"); n4.setBounds(SX,              SY + (BH+GY),     BW, BH);
        JButton n5 = criarBotaoNum("5"); n5.setBounds(SX + (BW+GX),    SY + (BH+GY),     BW, BH);
        JButton n6 = criarBotaoNum("6"); n6.setBounds(SX + (BW+GX)*2,  SY + (BH+GY),     BW, BH);

        JButton n7 = criarBotaoNum("7"); n7.setBounds(SX,              SY + (BH+GY)*2,   BW, BH);
        JButton n8 = criarBotaoNum("8"); n8.setBounds(SX + (BW+GX),    SY + (BH+GY)*2,   BW, BH);
        JButton n9 = criarBotaoNum("9"); n9.setBounds(SX + (BW+GX)*2,  SY + (BH+GY)*2,   BW, BH);

        // 0 centralizado na coluna do meio
        JButton n0 = criarBotaoNum("0"); n0.setBounds(SX + (BW+GX),    SY + (BH+GY)*3,   BW, BH);

        // Botões de ação — largura proporcional para cobrir as 3 colunas
        final int AY   = SY + (BH+GY)*4 + 8;
        final int AW   = (NUMPAD_W - SX*2 - GX*2) / 3;  // ~107px

        JButton branco = new JButton("BRANCO");
        branco.setBounds(SX, AY, AW, 52);
        branco.setBackground(new Color(238, 238, 238));
        branco.setForeground(new Color(20, 20, 25));
        branco.setFont(new Font("Arial", Font.BOLD, 13));
        branco.setFocusPainted(false);
        branco.setBorderPainted(false);
        branco.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton corrige = new JButton("CORRIGE");
        corrige.setBounds(SX + AW + GX, AY, AW, 52);
        corrige.setBackground(new Color(210, 72, 0));
        corrige.setForeground(Color.WHITE);
        corrige.setFont(new Font("Arial", Font.BOLD, 13));
        corrige.setFocusPainted(false);
        corrige.setBorderPainted(false);
        corrige.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton confirma = new JButton("CONFIRMA");
        confirma.setBounds(SX + (AW+GX)*2, AY, AW, 52);
        confirma.setBackground(new Color(0, 148, 0));
        confirma.setForeground(Color.WHITE);
        confirma.setFont(new Font("Arial", Font.BOLD, 13));
        confirma.setFocusPainted(false);
        confirma.setBorderPainted(false);
        confirma.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ── Action listeners (lógica original preservada) ─────────────────
        ActionListener digitarNum = e -> {
            votacao.digitar(((JButton) e.getSource()).getText());
            atualizarTela(votacao, voto, nome, partido, foto, vice);
        };
        n1.addActionListener(digitarNum);
        n2.addActionListener(digitarNum);
        n3.addActionListener(digitarNum);
        n4.addActionListener(digitarNum);
        n5.addActionListener(digitarNum);
        n6.addActionListener(digitarNum);
        n7.addActionListener(digitarNum);
        n8.addActionListener(digitarNum);
        n9.addActionListener(digitarNum);
        n0.addActionListener(digitarNum);

        corrige.addActionListener(e -> {

            votacao.corrigir();
            atualizarTela(votacao, voto, nome, partido, foto, vice);
        });

        confirma.addActionListener(e -> {

            Cargo cargoVotado = votacao.getCargoAtual();
            boolean eraBranco = votacao.isBranco();
            boolean eraNulo = votacao.isNulo();
            Optional<Candidato> candidatoVotado = votacao.getCandidatoEncontrado();

            boolean confirmou = votacao.confirmar();

            // 1. não confirmou: avisa o motivo
            if(!confirmou){
                if(!votacao.isNumeroCompleto()){
                    JOptionPane.showMessageDialog(janela, "NÚMERO INCOMPLETO");
                }
                return;
            }

            // Registra o voto contabilizado
            if (eraBranco) {
                ContagemVotos.registrarVotoBranco(cargoVotado);
            }else if(eraNulo){
                ContagemVotos.registrarVotoNulo(cargoVotado);
            }else if (candidatoVotado.isPresent()) {
                ContagemVotos.registrarVotoCandidato(candidatoVotado.get());
            }

            // 2. confirmou o último cargo: FIM
            if(votacao.isEncerrada()){
                // Toca o áudio de confirmação
                try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(new File("assents/confirma-urna.wav")));
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                // Monta a tela de FIM no painel principal
                main.removeAll();
                main.setBackground(Color.WHITE);

                JLabel lblFim = new JLabel("FIM", SwingConstants.CENTER);
                lblFim.setFont(new Font("Arial", Font.BOLD, 120));
                lblFim.setForeground(new Color(12, 12, 18));
                lblFim.setBounds(0, 40, NUMPAD_X, 150);

                JPanel linhaVerde = new JPanel();
                linhaVerde.setBackground(new Color(0, 150, 0));
                linhaVerde.setBounds(80, 200, NUMPAD_X - 160, 4);

                JLabel lblMsg = new JLabel("<html><center>SEU VOTO FOI CONFIRMADO!<br>OBRIGADO POR VOTAR.</center></html>", SwingConstants.CENTER);
                lblMsg.setFont(new Font("Arial", Font.BOLD, 18));
                lblMsg.setForeground(new Color(50, 50, 55));
                lblMsg.setBounds(0, 215, NUMPAD_X, 60);

                LocalDateTime agora = LocalDateTime.now();
                JLabel lblHoraFim = new JLabel(agora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss")), SwingConstants.CENTER);
                lblHoraFim.setFont(new Font("Arial", Font.PLAIN, 13));
                lblHoraFim.setForeground(new Color(120, 120, 125));
                lblHoraFim.setBounds(0, 290, NUMPAD_X, 20);

                main.add(lblFim);
                main.add(linhaVerde);
                main.add(lblMsg);
                main.add(lblHoraFim);
                main.repaint();
                main.revalidate();

                // Após 4 segundos, volta ao menu
                Timer timer = new Timer(4000, evt -> {
                    janela.dispose();
                    try {
                        Menu.main();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                timer.setRepeats(false);
                timer.start();

                return;
            }

            // 3. confirmou e ainda tem cargo: mostra o próximo
            cargo.setText(votacao.getCargoAtual().getNome());
            atualizarTela(votacao, voto, nome, partido, foto, vice);
            try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(new File("assents/ok.wav")));
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        });

        branco.addActionListener(e -> {
            //voto.setText("BRANCO");
            votacao.votarBranco();
            atualizarTela(votacao, voto, nome, partido, foto, vice);
        });

        numpad.add(n1); numpad.add(n2); numpad.add(n3);
        numpad.add(n4); numpad.add(n5); numpad.add(n6);
        numpad.add(n7); numpad.add(n8); numpad.add(n9);
        numpad.add(n0);
        numpad.add(branco);
        numpad.add(corrige);
        numpad.add(confirma);

        main.add(numpad);

        // ════════════════════════════════════════════════════════════════════
        //  FOOTER — dica de uso e acessibilidade
        // ════════════════════════════════════════════════════════════════════
        JPanel footer = new JPanel(null);
        footer.setBackground(new Color(198, 200, 205));
        footer.setBounds(0, HDR_H + MAIN_H, W, FOOTER_H);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(155, 157, 162)));

        JLabel iconeInfo = new JLabel("ℹ");
        iconeInfo.setBounds(12, 9, 22, 26);
        iconeInfo.setFont(new Font("Arial", Font.BOLD, 17));
        iconeInfo.setForeground(new Color(45, 65, 145));

        JLabel txtInfo = new JLabel(
                "<html>Use o teclado numérico para digitar o número do candidato." +
                "<br>Depois, pressione CONFIRMA.</html>");
        txtInfo.setBounds(38, 4, 540, 36);
        txtInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        txtInfo.setForeground(new Color(35, 35, 40));

        JLabel lblAcess = new JLabel("♿  Acessibilidade  ▶");
        lblAcess.setBounds(W - 175, 12, 160, 20);
        lblAcess.setFont(new Font("Arial", Font.PLAIN, 11));
        lblAcess.setForeground(new Color(45, 65, 145));

        footer.add(iconeInfo);
        footer.add(txtInfo);
        footer.add(lblAcess);

        // ── Monta a janela ────────────────────────────────────────────────
        janela.add(header);
        janela.add(main);
        janela.add(footer);

        janela.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        abrirUrna();
    }
}
