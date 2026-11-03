package br.com.carlos.funcionarios.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class SenhaUtilTest {

    @Test
    public void deveGerarHashSha256Conhecido() {
        String esperado =
                "ba7816bf8f01cfea414140de5dae2223"
                + "b00361a396177a9cb410ff61f20015ad";

        String resultado = SenhaUtil.gerarHash("abc");

        assertEquals(esperado, resultado);
    }

    @Test
    public void deveGerarMesmoHashParaMesmaSenha() {
        String primeiroHash = SenhaUtil.gerarHash("MinhaSenha123");
        String segundoHash = SenhaUtil.gerarHash("MinhaSenha123");

        assertEquals(primeiroHash, segundoHash);
    }

    @Test
    public void deveDistinguirMaiusculasDeMinusculas() {
        String primeiroHash = SenhaUtil.gerarHash("Senha123");
        String segundoHash = SenhaUtil.gerarHash("senha123");

        assertNotEquals(primeiroHash, segundoHash);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveRejeitarSenhaNula() {
        SenhaUtil.gerarHash(null);
    }
}