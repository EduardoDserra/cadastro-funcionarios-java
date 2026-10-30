package br.com.carlos.funcionarios.dao;

import br.com.carlos.funcionarios.config.Conexao;
import br.com.carlos.funcionarios.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioDAO {

    public void cadastrar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, senha_hash) "
                + "VALUES (?, ?, ?) RETURNING id";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getEmail());
            comando.setString(3, usuario.getSenhaHash());

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    usuario.setId(resultado.getLong("id"));
                } else {
                    throw new SQLException(
                            "O cadastro não retornou o ID do usuário."
                    );
                }
            }
        }
    }

    public Optional<Usuario> buscarPorEmail(String email)
            throws SQLException {

        String sql = "SELECT id, nome, email, senha_hash "
                + "FROM usuarios WHERE email = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, email);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    Usuario usuario = new Usuario();

                    usuario.setId(resultado.getLong("id"));
                    usuario.setNome(resultado.getString("nome"));
                    usuario.setEmail(resultado.getString("email"));
                    usuario.setSenhaHash(
                            resultado.getString("senha_hash")
                    );

                    return Optional.of(usuario);
                }
            }
        }

        return Optional.empty();
    }
}