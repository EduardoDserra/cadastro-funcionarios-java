package br.com.carlos.funcionarios.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Conexao {

    private Conexao() {
    }

    public static Connection conectar() throws SQLException {
        Properties propriedades = new Properties();

        try (InputStream arquivo =
                Files.newInputStream(Paths.get("db.properties"))) {

            propriedades.load(arquivo);

        } catch (IOException e) {
            throw new SQLException(
                "Não foi possível ler o arquivo db.properties.", e
            );
        }

        String url = propriedades.getProperty("db.url");
        String usuario = propriedades.getProperty("db.usuario");
        String senha = propriedades.getProperty("db.senha");

        if (url == null || usuario == null || senha == null) {
            throw new SQLException(
                "Preencha db.url, db.usuario e db.senha no db.properties."
            );
        }

        return DriverManager.getConnection(url, usuario, senha);
    }
}