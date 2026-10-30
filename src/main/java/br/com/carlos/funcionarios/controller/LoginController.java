package br.com.carlos.funcionarios.controller;

import br.com.carlos.funcionarios.model.Usuario;
import br.com.carlos.funcionarios.service.UsuarioService;
import java.sql.SQLException;

public class LoginController {

    private final UsuarioService usuarioService = new UsuarioService();

    public Usuario autenticar(String email, String senha)
            throws SQLException {

        return usuarioService.autenticar(email, senha);
    }
}