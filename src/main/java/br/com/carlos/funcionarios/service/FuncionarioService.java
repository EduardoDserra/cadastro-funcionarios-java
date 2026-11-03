package br.com.carlos.funcionarios.service;

import br.com.carlos.funcionarios.dao.FuncionarioDAO;
import br.com.carlos.funcionarios.model.Funcionario;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class FuncionarioService {

    private final FuncionarioDAO funcionarioDAO;

    public FuncionarioService() {
        this(new FuncionarioDAO());
    }

    public FuncionarioService(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = Objects.requireNonNull(
                funcionarioDAO, "O DAO deve ser informado.");
    }

    public void cadastrar(String nome, LocalDate dataAdmissao,
            BigDecimal salario, boolean status) throws SQLException {

        Funcionario funcionario = validarDados(
                nome, dataAdmissao, salario, status);

        funcionarioDAO.cadastrar(funcionario);
    }

    public void atualizar(Long id, String nome, LocalDate dataAdmissao,
            BigDecimal salario, boolean status) throws SQLException {

        validarId(id);

        Funcionario funcionario = validarDados(
                nome, dataAdmissao, salario, status);

        funcionario.setId(id);
        funcionarioDAO.atualizar(funcionario);
    }

    public void excluir(Long id) throws SQLException {
        validarId(id);
        funcionarioDAO.excluir(id);
    }

    public List<Funcionario> listar() throws SQLException {
        return funcionarioDAO.listar();
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Selecione um funcionário válido.");
        }
    }

    private Funcionario validarDados(String nome, LocalDate dataAdmissao,
            BigDecimal salario, boolean status) {

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe o nome do funcionário.");
        }

        if (nome.trim().length() > 150) {
            throw new IllegalArgumentException(
                    "O nome deve ter no máximo 150 caracteres.");
        }

        if (dataAdmissao == null) {
            throw new IllegalArgumentException(
                    "Informe uma data de admissão válida.");
        }

        if (salario == null) {
            throw new IllegalArgumentException("Informe o salário.");
        }

        if (salario.signum() < 0) {
            throw new IllegalArgumentException(
                    "O salário não pode ser negativo.");
        }

        BigDecimal salarioValidado;

        try {
            salarioValidado = salario.setScale(
                    2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException(
                    "O salário deve ter no máximo duas casas decimais.");
        }

        if (salarioValidado.compareTo(
                new BigDecimal("9999999999.99")) > 0) {
            throw new IllegalArgumentException(
                    "O salário excede o valor máximo permitido.");
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome.trim());
        funcionario.setDataAdmissao(dataAdmissao);
        funcionario.setSalario(salarioValidado);
        funcionario.setStatus(status);

        return funcionario;
    }
}