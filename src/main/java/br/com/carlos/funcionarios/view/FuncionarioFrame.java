/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.carlos.funcionarios.view;

import br.com.carlos.funcionarios.controller.FuncionarioController;
import br.com.carlos.funcionarios.model.Funcionario;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 *
 * @author user
 */
public class FuncionarioFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FuncionarioFrame.class.getName());
    private final FuncionarioController funcionarioController
            = new FuncionarioController();
    private Long idEmEdicao = null;

    private void limparCampos() {
        idEmEdicao = null;

        txtNome.setText("");
        txtDataAdmissao.setText("");
        txtSalario.setText("");
        chkAtivo.setSelected(true);

        btnCadastrar.setText("Cadastrar");
        tblFuncionarios.clearSelection();
        txtNome.requestFocusInWindow();
    }

    /**
     * Creates new form FuncionarioFrame
     */
    public FuncionarioFrame() {

        initComponents();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnCadastrar);
        carregarFuncionarios();
    }

    private void definirOcupado(boolean ocupado) {
        btnCadastrar.setEnabled(!ocupado);
        btnLimpar.setEnabled(!ocupado);
        btnEditar.setEnabled(!ocupado);
        btnExcluir.setEnabled(!ocupado);
        btnSair.setEnabled(!ocupado);

        txtNome.setEnabled(!ocupado);
        txtDataAdmissao.setEnabled(!ocupado);
        txtSalario.setEnabled(!ocupado);
        chkAtivo.setEnabled(!ocupado);
        tblFuncionarios.setEnabled(!ocupado);
    }

    private int obterLinhaSelecionada() {
        int linha = tblFuncionarios.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um funcionário na tabela.",
                    "Selecione um registro",
                    JOptionPane.WARNING_MESSAGE);

            return -1;
        }

        return tblFuncionarios.convertRowIndexToModel(linha);
    }

    private void carregarFuncionarios() {
        definirOcupado(true);

        new SwingWorker<List<Funcionario>, Void>() {

            @Override
            protected List<Funcionario> doInBackground() throws Exception {
                return funcionarioController.listar();
            }

            @Override
            protected void done() {
                try {
                    List<Funcionario> funcionarios = get();

                    DefaultTableModel modelo
                            = (DefaultTableModel) tblFuncionarios.getModel();

                    modelo.setRowCount(0);

                    DateTimeFormatter formatoData
                            = DateTimeFormatter.ofPattern("dd/MM/uuuu");

                    NumberFormat formatoMoeda
                            = NumberFormat.getCurrencyInstance(
                                    new Locale("pt", "BR"));

                    for (Funcionario funcionario : funcionarios) {
                        modelo.addRow(new Object[]{
                            funcionario.getId(),
                            funcionario.getNome(),
                            funcionario.getDataAdmissao().format(formatoData),
                            formatoMoeda.format(funcionario.getSalario()),
                            funcionario.isStatus() ? "Ativo" : "Inativo"
                        });
                    }

                    int total = funcionarios.size();

                    lblTotal.setText(
                            total + (total == 1
                                    ? " funcionário cadastrado"
                                    : " funcionários cadastrados"));

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();

                    JOptionPane.showMessageDialog(
                            FuncionarioFrame.this,
                            "O carregamento foi interrompido.",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE);

                } catch (ExecutionException e) {
                    logger.log(
                            java.util.logging.Level.SEVERE,
                            "Erro ao carregar funcionários",
                            e.getCause());

                    JOptionPane.showMessageDialog(
                            FuncionarioFrame.this,
                            "Não foi possível carregar os funcionários. "
                            + "Verifique a conexão com o banco.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);

                } finally {
                    definirOcupado(false);
                }
            }
        }.execute();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblDataAdmissao = new javax.swing.JLabel();
        txtDataAdmissao = new javax.swing.JTextField();
        lblSalario = new javax.swing.JLabel();
        txtSalario = new javax.swing.JTextField();
        chkAtivo = new javax.swing.JCheckBox();
        btnCadastrar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblFuncionarios = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        lblLista = new javax.swing.JLabel();
        lblOrientacao = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cadastro de Funcionários");
        setResizable(false);

        lblNome.setText("Nome");

        lblDataAdmissao.setText("\tData de admissão (dd/mm/aaaa)");

        lblSalario.setText("Salário (R$)");

        chkAtivo.setSelected(true);
        chkAtivo.setText("Ativo");

        btnCadastrar.setBackground(new java.awt.Color(0, 120, 215));
        btnCadastrar.setText("Cadastrar ");
        btnCadastrar.addActionListener(this::btnCadastrarActionPerformed);

        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(this::btnLimparActionPerformed);

        tblFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nome", "Data de admissão", "Salário", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblFuncionarios);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setText("Funcionários");

        lblSubtitulo.setText("Cadastre e gerencie os funcionários.");

        lblLista.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLista.setText("Funcionários cadastrados");

        lblOrientacao.setText("Selecione um funciário para edtar ou excluir");

        btnEditar.setText("Editar selecionado");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnExcluir.setBackground(new java.awt.Color(220, 53, 69));
        btnExcluir.setText("Excluir selecionado");
        btnExcluir.addActionListener(this::btnExcluirActionPerformed);

        lblTotal.setText("0 Funcionários cadastrados");

        btnSair.setText("Sair");
        btnSair.addActionListener(this::btnSairActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblOrientacao)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDataAdmissao)
                        .addGap(18, 18, 18)
                        .addComponent(lblSalario))
                    .addComponent(lblNome)
                    .addComponent(lblSubtitulo)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtDataAdmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(chkAtivo))
                    .addComponent(lblLista)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtNome, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(380, 380, 380)
                                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExcluir))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTitulo)
                            .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSubtitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNome)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDataAdmissao)
                    .addComponent(lblSalario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataAdmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkAtivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLista)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOrientacao)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        final String nome = txtNome.getText();
        final boolean ativo = chkAtivo.isSelected();

        final LocalDate dataAdmissao;
        final BigDecimal salario;

        final Long id = idEmEdicao;

        try {
            DateTimeFormatter formato = DateTimeFormatter
                    .ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

            dataAdmissao = LocalDate.parse(
                    txtDataAdmissao.getText().trim(), formato);

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe uma data válida no formato dd/mm/aaaa.",
                    "Verifique os dados",
                    JOptionPane.WARNING_MESSAGE);

            txtDataAdmissao.requestFocusInWindow();
            return;
        }

        String salarioTexto = txtSalario.getText().trim();

        if (!salarioTexto.matches("-?\\d+(,\\d{1,2})?")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe o salário sem pontos ou símbolo de moeda. "
                    + "Exemplo: 2500,00.",
                    "Verifique os dados",
                    JOptionPane.WARNING_MESSAGE);

            txtSalario.requestFocusInWindow();
            return;
        }

        salario = new BigDecimal(salarioTexto.replace(',', '.'));

        definirOcupado(true);

        new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                if (id == null) {
                    funcionarioController.cadastrar(
                            nome, dataAdmissao, salario, ativo);
                } else {
                    funcionarioController.atualizar(
                            id, nome, dataAdmissao, salario, ativo);
                }

                return null;
            }

            @Override
            protected void done() {
                boolean salvo = false;

                try {
                    get();
                    salvo = true;

                    JOptionPane.showMessageDialog(
                            FuncionarioFrame.this,
                            id == null
                                    ? "Funcionário cadastrado com sucesso!"
                                    : "Funcionário atualizado com sucesso!");

                    limparCampos();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();

                    JOptionPane.showMessageDialog(
                            FuncionarioFrame.this,
                            "A operação foi interrompida.",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE);

                } catch (ExecutionException e) {
                    Throwable causa = e.getCause();

                    if (causa instanceof IllegalArgumentException) {
                        JOptionPane.showMessageDialog(
                                FuncionarioFrame.this,
                                causa.getMessage(),
                                "Verifique os dados",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        logger.log(
                                java.util.logging.Level.SEVERE,
                                "Erro ao cadastrar funcionário",
                                causa);

                        JOptionPane.showMessageDialog(
                                FuncionarioFrame.this,
                                "Não foi possível cadastrar o funcionário. "
                                + "Verifique a conexão com o banco.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } finally {
                    definirOcupado(false);
                }

                if (salvo) {
                    carregarFuncionarios();
                }
            }
        }.execute();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int linha = obterLinhaSelecionada();

        if (linha < 0) {
            return;
        }

        DefaultTableModel modelo
                = (DefaultTableModel) tblFuncionarios.getModel();

        idEmEdicao = ((Number) modelo.getValueAt(linha, 0)).longValue();

        txtNome.setText(modelo.getValueAt(linha, 1).toString());
        txtDataAdmissao.setText(modelo.getValueAt(linha, 2).toString());

        String salario = modelo.getValueAt(linha, 3).toString()
                .replaceAll("[^0-9,.-]", "")
                .replace(".", "");

        txtSalario.setText(salario);

        chkAtivo.setSelected(
                "Ativo".equals(modelo.getValueAt(linha, 4).toString()));

        btnCadastrar.setText("Salvar alterações");
        txtNome.requestFocusInWindow();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        new LoginFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int linha = obterLinhaSelecionada();

        if (linha < 0) {
            return;
        }

        DefaultTableModel modelo
                = (DefaultTableModel) tblFuncionarios.getModel();

        final Long id
                = ((Number) modelo.getValueAt(linha, 0)).longValue();

        String nome = modelo.getValueAt(linha, 1).toString();

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja excluir o funcionário \"" + nome + "\"?\n"
                + "Essa ação não pode ser desfeita.",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        definirOcupado(true);

        new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                funcionarioController.excluir(id);
                return null;
            }

            @Override
            protected void done() {
                boolean excluido = false;

                try {
                    get();
                    excluido = true;

                    limparCampos();

                    JOptionPane.showMessageDialog(
                            FuncionarioFrame.this,
                            "Funcionário excluído com sucesso!");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();

                    JOptionPane.showMessageDialog(
                            FuncionarioFrame.this,
                            "A operação foi interrompida.",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE);

                } catch (ExecutionException e) {
                    logger.log(
                            java.util.logging.Level.SEVERE,
                            "Erro ao excluir funcionário",
                            e.getCause());

                    JOptionPane.showMessageDialog(
                            FuncionarioFrame.this,
                            "Não foi possível excluir o funcionário. "
                            + "Verifique a conexão com o banco.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);

                } finally {
                    definirOcupado(false);
                }

                if (excluido) {
                    carregarFuncionarios();
                }
            }
        }.execute();
    }//GEN-LAST:event_btnExcluirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FuncionarioFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnSair;
    private javax.swing.JCheckBox chkAtivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblDataAdmissao;
    private javax.swing.JLabel lblLista;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblOrientacao;
    private javax.swing.JLabel lblSalario;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblFuncionarios;
    private javax.swing.JTextField txtDataAdmissao;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtSalario;
    // End of variables declaration//GEN-END:variables
}
