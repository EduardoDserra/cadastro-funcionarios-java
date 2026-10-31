package br.com.carlos.funcionarios.dao;

import br.com.carlos.funcionarios.config.Conexao;
import br.com.carlos.funcionarios.model.Funcionario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void cadastrar(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO funcionarios "
                + "(nome, data_admissao, salario, status) "
                + "VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setDate(2, Date.valueOf(funcionario.getDataAdmissao()));
            stmt.setBigDecimal(3, funcionario.getSalario());
            stmt.setBoolean(4, funcionario.isStatus());

            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    funcionario.setId(resultado.getLong("id"));
                } else {
                    throw new SQLException(
                            "Não foi possível obter o ID do funcionário.");
                }
            }
        }
    }

    public List<Funcionario> listar() throws SQLException {
        String sql = "SELECT id, nome, data_admissao, salario, status "
                + "FROM funcionarios ORDER BY nome, id";

        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet resultado = stmt.executeQuery()) {

            while (resultado.next()) {
                Funcionario funcionario = new Funcionario();

                funcionario.setId(resultado.getLong("id"));
                funcionario.setNome(resultado.getString("nome"));
                funcionario.setDataAdmissao(
                        resultado.getDate("data_admissao").toLocalDate());
                funcionario.setSalario(resultado.getBigDecimal("salario"));
                funcionario.setStatus(resultado.getBoolean("status"));

                funcionarios.add(funcionario);
            }
        }

        return funcionarios;
    }

    public void atualizar(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE funcionarios "
                + "SET nome = ?, data_admissao = ?, salario = ?, status = ? "
                + "WHERE id = ?";

        try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setDate(2, Date.valueOf(funcionario.getDataAdmissao()));
            stmt.setBigDecimal(3, funcionario.getSalario());
            stmt.setBoolean(4, funcionario.isStatus());
            stmt.setLong(5, funcionario.getId());

            int quantidade = stmt.executeUpdate();

            if (quantidade == 0) {
                throw new SQLException(
                        "O funcionário não foi encontrado para alteração.");
            }
        }
    }

    public void excluir(Long id) throws SQLException {
        String sql = "DELETE FROM funcionarios WHERE id = ?";

        try (Connection conexao = Conexao.conectar(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);

            int quantidade = stmt.executeUpdate();

            if (quantidade == 0) {
                throw new SQLException(
                        "O funcionário não foi encontrado para exclusão.");
            }
        }
    }
}
