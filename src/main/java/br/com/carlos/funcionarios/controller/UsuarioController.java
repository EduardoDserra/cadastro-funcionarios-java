package br.com.carlos.funcionarios.controller;

import br.com.carlos.funcionarios.model.Usuario;
import br.com.carlos.funcionarios.service.UsuarioService;
import java.sql.SQLException;

public class UsuarioController {

    private final UsuarioService usuarioService = new UsuarioService();

    public Usuario cadastrar(String nome, String email, String senha)
            throws SQLException {
        return usuarioService.cadastrar(nome, email, senha);
    }
}