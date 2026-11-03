package br.com.carlos.funcionarios;

import br.com.carlos.funcionarios.view.LoginFrame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo tema
                    : UIManager.getInstalledLookAndFeels()) {

                if ("Nimbus".equals(tema.getName())) {
                    UIManager.setLookAndFeel(tema.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | UnsupportedLookAndFeelException e) {

            Logger.getLogger(Main.class.getName()).log(
                    Level.WARNING, "Não foi possível aplicar o tema Nimbus.", e);
        }

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}