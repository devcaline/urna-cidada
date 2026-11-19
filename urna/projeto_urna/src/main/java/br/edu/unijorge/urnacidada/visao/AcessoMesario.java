package br.edu.unijorge.urnacidada.visao;

import br.edu.unijorge.urnacidada.infraestrutura.SenhaMesario;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/** Caixa de senha do mesário usada pelo Menu e pela Urna (RF14, RF19, RN18). */
public final class AcessoMesario {
    private AcessoMesario() { }

    /** Pede a senha do mesário. Devolve true só se a senha estiver certa. */
    public static boolean confirmar(Component pai, String acao) {
        JPasswordField campo = new JPasswordField(12);
        int opcao = JOptionPane.showConfirmDialog(pai,
                new Object[]{"Senha do mesário para " + acao + ":", campo},
                "Área do mesário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcao != JOptionPane.OK_OPTION) {
            return false;
        }
        char[] digitada = campo.getPassword();
        try {
            if (SenhaMesario.mesario().validarSenha(new String(digitada))) {
                return true;
            }
            JOptionPane.showMessageDialog(pai, "Senha incorreta.", "Área do mesário", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(pai, ex.getMessage(), "Área do mesário", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            Arrays.fill(digitada, ' ');
        }
    }
}