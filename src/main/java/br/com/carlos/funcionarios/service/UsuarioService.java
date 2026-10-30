package br.com.carlos.funcionarios.service;

import br.com.carlos.funcionarios.dao.UsuarioDAO;
import br.com.carlos.funcionarios.model.Usuario;
import br.com.carlos.funcionarios.util.SenhaUtil;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario cadastrar(String nome, String email, String senha)
            throws SQLException {

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o nome.");
        }

        nome = nome.trim();

        if (nome.length() > 150) {
            throw new IllegalArgumentException(
                    "O nome deve ter no máximo 150 caracteres."
            );
        }

        email = validarEmail(email);
        validarSenhaPreenchida(senha);

        if (senha.length() < 6) {
            throw new IllegalArgumentException(
                    "A senha deve ter pelo menos 6 caracteres."
            );
        }

        if (usuarioDAO.buscarPorEmail(email).isPresent()) {
            throw new IllegalArgumentException(
                    "Este e-mail já está cadastrado."
            );
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenhaHash(SenhaUtil.gerarHash(senha));

        try {
            usuarioDAO.cadastrar(usuario);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new IllegalArgumentException(
                        "Este e-mail já está cadastrado."
                );
            }

            throw e;
        }

        return usuario;
    }

    public Usuario autenticar(String email, String senha)
            throws SQLException {

        email = validarEmail(email);
        validarSenhaPreenchida(senha);

        Optional<Usuario> encontrado = usuarioDAO.buscarPorEmail(email);

        if (encontrado.isPresent()) {
            Usuario usuario = encontrado.get();
            String hashInformado = SenhaUtil.gerarHash(senha);

            if (hashInformado.equals(usuario.getSenhaHash())) {
                return usuario;
            }
        }

        throw new IllegalArgumentException("E-mail ou senha incorretos.");
    }

    private String validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o e-mail.");
        }

        String normalizado = email.trim().toLowerCase(Locale.ROOT);

        if (normalizado.length() > 254
                || !normalizado.matches(
                        "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {

            throw new IllegalArgumentException(
                    "Informe um e-mail válido."
            );
        }

        return normalizado;
    }

    private void validarSenhaPreenchida(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe a senha.");
        }
    }
}