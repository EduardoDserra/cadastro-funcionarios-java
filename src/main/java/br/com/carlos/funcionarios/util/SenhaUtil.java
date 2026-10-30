package br.com.carlos.funcionarios.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SenhaUtil {

    private SenhaUtil() {
    }

    public static String gerarHash(String senha) {
        if (senha == null) {
            throw new IllegalArgumentException(
                "A senha não pode ser nula."
            );
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                senha.getBytes(StandardCharsets.UTF_8)
            );

            StringBuilder resultado = new StringBuilder();

            for (byte valor : hash) {
                resultado.append(String.format("%02x", valor & 0xff));
            }

            return resultado.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                "Algoritmo SHA-256 indisponível.", e
            );
        }
    }
}