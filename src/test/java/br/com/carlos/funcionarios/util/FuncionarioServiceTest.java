package br.com.carlos.funcionarios.service;

import br.com.carlos.funcionarios.dao.FuncionarioDAO;
import br.com.carlos.funcionarios.model.Funcionario;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FuncionarioServiceTest {

    private static final LocalDate DATA = LocalDate.of(2026, 10, 30);

    private DaoSimulado dao;
    private FuncionarioService service;

    @Before
    public void preparar() {
        dao = new DaoSimulado();
        service = new FuncionarioService(dao);
    }

    @Test
    public void deveCadastrarComDadosValidos() throws SQLException {
        service.cadastrar(
                "  Ana Silva  ", DATA, new BigDecimal("2500"), true);

        assertEquals(1, dao.chamadas);
        assertEquals("cadastrar", dao.operacao);
        assertEquals("Ana Silva", dao.funcionario.getNome());
        assertEquals(DATA, dao.funcionario.getDataAdmissao());
        assertEquals(new BigDecimal("2500.00"),
                dao.funcionario.getSalario());
        assertTrue(dao.funcionario.isStatus());
    }

    @Test
    public void deveAceitarSalarioZeroEStatusInativo() throws SQLException {
        service.cadastrar("Ana", DATA, BigDecimal.ZERO, false);

        assertEquals(new BigDecimal("0.00"),
                dao.funcionario.getSalario());
        assertFalse(dao.funcionario.isStatus());
    }

    @Test
    public void deveAceitarLimitesDeNomeESalario() throws SQLException {
        String nome = repetir('A', 150);

        service.cadastrar(
                nome, DATA, new BigDecimal("9999999999.99"), true);

        assertEquals(nome, dao.funcionario.getNome());
        assertEquals(new BigDecimal("9999999999.99"),
                dao.funcionario.getSalario());
    }

    @Test
    public void deveRejeitarNomeNuloVazioOuComEspacos() {
        for (String nome : Arrays.asList(null, "", "   ")) {
            assertThrows(IllegalArgumentException.class,
                    () -> service.cadastrar(
                            nome, DATA, BigDecimal.TEN, true));
        }

        assertEquals(0, dao.chamadas);
    }

    @Test
    public void deveRejeitarNomeAcimaDoLimite() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar(
                        repetir('A', 151), DATA, BigDecimal.TEN, true));

        assertEquals(0, dao.chamadas);
    }

    @Test
    public void deveRejeitarDataAusente() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar(
                        "Ana", null, BigDecimal.TEN, true));

        assertEquals(0, dao.chamadas);
    }

    @Test
    public void deveRejeitarSalarioAusente() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar("Ana", DATA, null, true));

        assertEquals(0, dao.chamadas);
    }

    @Test
    public void deveRejeitarSalarioNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar(
                        "Ana", DATA, new BigDecimal("-0.01"), true));

        assertEquals(0, dao.chamadas);
    }

    @Test
    public void deveRejeitarFracaoDeCentavo() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar(
                        "Ana", DATA, new BigDecimal("100.001"), true));

        assertEquals(0, dao.chamadas);
    }

    @Test
    public void deveRejeitarSalarioAcimaDoLimite() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar(
                        "Ana", DATA, new BigDecimal("10000000000"), true));

        assertEquals(0, dao.chamadas);
    }

    @Test
    public void deveAtualizarPreservandoId() throws SQLException {
        service.atualizar(
                7L, "  Ana  ", DATA, new BigDecimal("3000"), false);

        assertEquals(1, dao.chamadas);
        assertEquals("atualizar", dao.operacao);
        assertEquals(Long.valueOf(7), dao.funcionario.getId());
        assertEquals("Ana", dao.funcionario.getNome());
        assertEquals(DATA, dao.funcionario.getDataAdmissao());
        assertEquals(new BigDecimal("3000.00"),
                dao.funcionario.getSalario());
        assertFalse(dao.funcionario.isStatus());
    }

    @Test
    public void deveValidarDadosTambemNaAtualizacao() {
        assertThrows(IllegalArgumentException.class,
                () -> service.atualizar(
                        7L, "   ", DATA, BigDecimal.TEN, true));

        assertEquals(0, dao.chamadas);
    }

    @Test
    public void deveRejeitarIdsInvalidos() {
        for (Long id : Arrays.asList(null, 0L, -1L)) {
            assertThrows(IllegalArgumentException.class,
                    () -> service.excluir(id));

            assertThrows(IllegalArgumentException.class,
                    () -> service.atualizar(
                            id, "Ana", DATA, BigDecimal.TEN, true));
        }

        assertEquals(0, dao.chamadas);
    }

    @Test
    public void deveExcluirPeloIdInformado() throws SQLException {
        service.excluir(7L);

        assertEquals(1, dao.chamadas);
        assertEquals("excluir", dao.operacao);
        assertEquals(Long.valueOf(7), dao.idExcluido);
    }

    @Test
    public void deveRetornarFuncionariosDoDao() throws SQLException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Ana");
        dao.lista = Arrays.asList(funcionario);

        List<Funcionario> resultado = service.listar();

        assertEquals(1, dao.chamadas);
        assertEquals(1, resultado.size());
        assertEquals("Ana", resultado.get(0).getNome());
    }

    @Test
    public void devePropagarFalhaAoCadastrar() {
        dao.falha = new SQLException("Falha simulada");

        SQLException erro = assertThrows(SQLException.class,
                () -> service.cadastrar(
                        "Ana", DATA, BigDecimal.TEN, true));

        assertSame(dao.falha, erro);
    }

    @Test
    public void devePropagarFalhaAoAtualizar() {
        dao.falha = new SQLException("Falha simulada");

        SQLException erro = assertThrows(SQLException.class,
                () -> service.atualizar(
                        7L, "Ana", DATA, BigDecimal.TEN, true));

        assertSame(dao.falha, erro);
    }

    @Test
    public void devePropagarFalhaAoExcluir() {
        dao.falha = new SQLException("Falha simulada");

        SQLException erro = assertThrows(SQLException.class,
                () -> service.excluir(7L));

        assertSame(dao.falha, erro);
    }

    @Test
    public void devePropagarFalhaAoListar() {
        dao.falha = new SQLException("Falha simulada");

        SQLException erro = assertThrows(
                SQLException.class, () -> service.listar());

        assertSame(dao.falha, erro);
    }

    private static String repetir(char caractere, int quantidade) {
        char[] caracteres = new char[quantidade];
        Arrays.fill(caracteres, caractere);
        return new String(caracteres);
    }

    private static class DaoSimulado extends FuncionarioDAO {

        private int chamadas;
        private String operacao;
        private Funcionario funcionario;
        private Long idExcluido;
        private List<Funcionario> lista = Collections.emptyList();
        private SQLException falha;

        private void verificarFalha() throws SQLException {
            if (falha != null) {
                throw falha;
            }
        }

        @Override
        public void cadastrar(Funcionario funcionario) throws SQLException {
            chamadas++;
            verificarFalha();
            operacao = "cadastrar";
            this.funcionario = funcionario;
        }

        @Override
        public void atualizar(Funcionario funcionario) throws SQLException {
            chamadas++;
            verificarFalha();
            operacao = "atualizar";
            this.funcionario = funcionario;
        }

        @Override
        public void excluir(Long id) throws SQLException {
            chamadas++;
            verificarFalha();
            operacao = "excluir";
            idExcluido = id;
        }

        @Override
        public List<Funcionario> listar() throws SQLException {
            chamadas++;
            verificarFalha();
            return lista;
        }
    }
}