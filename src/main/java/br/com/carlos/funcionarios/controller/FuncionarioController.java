package br.com.carlos.funcionarios.controller;

import br.com.carlos.funcionarios.model.Funcionario;
import br.com.carlos.funcionarios.service.FuncionarioService;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FuncionarioController {

    private final FuncionarioService funcionarioService
            = new FuncionarioService();

    public void cadastrar(String nome, LocalDate dataAdmissao,
            BigDecimal salario, boolean status) throws SQLException {

        funcionarioService.cadastrar(
                nome, dataAdmissao, salario, status);
    }

    public List<Funcionario> listar() throws SQLException {
        return funcionarioService.listar();
    }

    public void atualizar(Long id, String nome, LocalDate dataAdmissao,
            BigDecimal salario, boolean status) throws SQLException {

        funcionarioService.atualizar(
                id, nome, dataAdmissao, salario, status);
    }

    public void excluir(Long id) throws SQLException {
        funcionarioService.excluir(id);
    }
}
