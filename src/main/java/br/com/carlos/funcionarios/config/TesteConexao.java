package br.com.carlos.funcionarios.config;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {
        try (Connection conexao = Conexao.conectar()) {
            System.out.println(
                "Conexão realizada com sucesso! Banco: "
                    + conexao.getCatalog()
            );
        } catch (SQLException e) {
            System.err.println("Erro na conexão: " + e.getMessage());
        }
    }
}