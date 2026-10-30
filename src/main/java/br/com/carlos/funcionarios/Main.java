package br.com.carlos.funcionarios;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                null,
                "Sistema iniciado!\nJava: "
                    + System.getProperty("java.version"),
                "Cadastro de Funcionários",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
    }
} 