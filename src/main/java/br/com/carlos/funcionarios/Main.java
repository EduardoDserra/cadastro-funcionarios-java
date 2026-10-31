package br.com.carlos.funcionarios;

import br.com.carlos.funcionarios.view.LoginFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}