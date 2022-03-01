package UdemyCurso.servicos;

import UdemyCurso.entidades.Filme;
import UdemyCurso.entidades.Locacao;
import UdemyCurso.entidades.Usuario;
import UdemyCurso.exceptions.FilmeSemEstoqueException;
import UdemyCurso.exceptions.LocadoraException;
import UdemyCurso.matchers.MatchersProprios;
import UdemyCurso.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static UdemyCurso.matchers.MatchersProprios.*;
import static org.hamcrest.CoreMatchers.*;

public class LocacaoServiceTest {

    private LocacaoService service;

    //usando esse contador, vamos perceber que ele sempre será 1, pois o junit finaliza a instancia da variavel apos cada teste
    //isso é bom para evitar efeitos colaterais, ou seja, um teste mudar o cenario em um escopo maior e influenciar outros testes
    private int notStaticCount = 0;

    //com a variavel definida como estatica, vamos resolver o problema acima e conseguir contar os testes.
    private static int staticCount = 1;

    /**
     * Rules - servem para modificar comportamentos dos testes
     */

    @Rule
    public ErrorCollector error = new ErrorCollector();
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Before: sempre será executado antes de cada teste
     * After: sempre será executado após cada teste
     * BeforeClass: sempre será executado antes da classe ser instanciada
     * AfterClass: sempre será executado após a instancia da classe ser finalizada
     */

    @Before
    public void setup() {
        service = new LocacaoService();
        //System.out.println("Before: " + staticCount);
        //staticCount++;
    }

    @After
    public void tearDown() {
        //System.out.println("After");
    }

    @BeforeClass
    public static void setupClass() {
        //System.out.println("Before Class");
    }

    @AfterClass
    public static void tearDownClass() {
        //System.out.println("After Class");
    }

    /**
     * Testes
     */

    @Test
    public void deveAlugarFilme() throws Exception {

        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //CENÁRIO
        //LocacaoService service = new LocacaoService(); -> transformado em variavel global e iniciado no before
        Usuario usuario = new Usuario("Usuário 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.00));

        //AÇÃO
        Locacao locacao = service.alugarFilmes(usuario, filmes);

        //VERIFICAÇÃO

        // 1º Opção.
        //Assert.assertEquals(5.00, locacao.getValor(), 0.01);
        //Assert.assertThat(DataUtils.dataAtual(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
        //Assert.assertThat(DataUtils.dataAtual(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), CoreMatchers.is(true));

        // 2º Opção, usando ErrorCollector - aqui, caso ocorra erro, ele vai coletar o erro e continuar os demais testes
        //error.checkThat(locacao.getValor(), is(5.00));
        //error.checkThat(DataUtils.dataAtual(locacao.getDataLocacao(), new Date()), is(true));
        //error.checkThat(DataUtils.dataAtual(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));

        // 3º Opção, usando CoresMacthers proprios. Mais Legível.
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(locacao.getDataLocacao(), isHoje());
        error.checkThat(locacao.getDataRetorno(), isHojeComDifDias(1));

    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void naoDeveAlugarSemEstoque() throws Exception {

        Usuario usuario = new Usuario("Usuário 1");

        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.00));

        service.alugarFilmes(usuario, filmes);

    }

    //    @Test
//    public void testLocacao_filmeSemEstoque2() {
//
//        //cenário
//        LocacaoService locacaoService = new LocacaoService();
//        Usuario usuario = new Usuario("Usuário 1");
//        Filme filme = new Filme("Filme 1", 0, 5.00);
//
//        //ação
//        try {
//            locacaoService.alugarFilme(usuario, filme);
//            Assert.fail("Deveria ter lançado exceção...");
//        } catch (Exception e) {
//            Assert.assertThat(
//                    e.getMessage(),
//                    CoreMatchers.is("Filme sem saldo em estoque..."));
//        }
//
//    }
//
//    @Test
//    public void testLocacao_filmeSemEstoque3() throws Exception {
//
//        //cenário
//        LocacaoService locacaoService = new LocacaoService();
//        Usuario usuario = new Usuario("Usuário 1");
//        Filme filme = new Filme("Filme 1", 0, 5.00);
//        exception.expect(Exception.class);
//        exception.expectMessage("Filme sem saldo em estoque...");
//
//        //ação
//        locacaoService.alugarFilme(usuario, filme);
//
//    }

    @Test
    public void naoDeveAlugarSemUsuario() throws FilmeSemEstoqueException {

        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.00));

        try {
            service.alugarFilmes(null, filmes);
        } catch (LocadoraException e) {
            Assert.assertThat(e.getMessage(), is("Usuario NULL"));
        }

        //System.out.println("Forma robusta..."); //na forma robusta, essa linha será executada, pois a falha esta sendfo tratada de forma independente no try

    }

    @Test
    public void naoDeveAlugarSemFilme() throws FilmeSemEstoqueException, LocadoraException {

        Usuario usuario = new Usuario("Usuário 1");
        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme NULL");

        service.alugarFilmes(usuario, null);

        //System.out.println("Forma nova..."); //na forma nova, essa linha não sera executada pois o teste falhou antes e não chegou aqui.

    }

    /**
     * Métodos abaixo migrados para a classe CalculoValorLocacaoTest com recurso de testes parametrizaveis.
     */

//    @Test
//    public void deveDescontar25PorcentoNo3Filme() throws FilmeSemEstoqueException, LocadoraException {
//
//        Usuario usuario = new Usuario("Usuário 1");
//        List<Filme> filmes = Arrays.asList(
//                new Filme("Filme 1", 1, 4.00),
//                new Filme("Filme 2", 10, 4.00),
//                new Filme("Filme 3", 15, 4.00));
//
//        Locacao resultado = service.alugarFilmes(usuario, filmes);
//
//        Assert.assertThat(resultado.getValor(), CoreMatchers.is(11.0));
//
//    }
//
//    @Test
//    public void deveDescontar50PorcentoNo4Filme() throws FilmeSemEstoqueException, LocadoraException {
//
//        Usuario usuario = new Usuario("Usuário 1");
//        List<Filme> filmes = Arrays.asList(
//                new Filme("Filme 1", 1, 4.00),
//                new Filme("Filme 2", 10, 4.00),
//                new Filme("Filme 3", 15, 4.00),
//                new Filme("Filme 4", 15, 4.00));
//
//        Locacao resultado = service.alugarFilmes(usuario, filmes);
//
//        Assert.assertThat(resultado.getValor(), CoreMatchers.is(13.0));
//
//    }
//
//    @Test
//    public void deveDescontar75PorcentoNo5Filme() throws FilmeSemEstoqueException, LocadoraException {
//
//        Usuario usuario = new Usuario("Usuário 1");
//        List<Filme> filmes = Arrays.asList(
//                new Filme("Filme 1", 1, 4.00),
//                new Filme("Filme 2", 10, 4.00),
//                new Filme("Filme 3", 15, 4.00),
//                new Filme("Filme 4", 15, 4.00),
//                new Filme("Filme 5", 15, 4.00));
//
//        Locacao resultado = service.alugarFilmes(usuario, filmes);
//
//        Assert.assertThat(resultado.getValor(), CoreMatchers.is(14.0));
//
//    }
//
//    @Test
//    public void deveDescontar100PorcentoNo6Filme() throws FilmeSemEstoqueException, LocadoraException {
//
//        Usuario usuario = new Usuario("Usuário 1");
//        List<Filme> filmes = Arrays.asList(
//                new Filme("Filme 1", 1, 4.00),
//                new Filme("Filme 2", 10, 4.00),
//                new Filme("Filme 3", 15, 4.00),
//                new Filme("Filme 4", 15, 4.00),
//                new Filme("Filme 5", 15, 4.00),
//                new Filme("Filme 6", 15, 4.00));
//
//        Locacao resultado = service.alugarFilmes(usuario, filmes);
//
//        Assert.assertThat(resultado.getValor(), CoreMatchers.is(14.0));
//
//    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

        //acao
        Locacao retorno = service.alugarFilmes(usuario, filmes);

        //verificacao
//        boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
//        Assert.assertTrue(ehSegunda);
        //usando Matchers Proprios.
        //Assert.assertThat(retorno.getDataRetorno(), MatchersProprios.caiEm(Calendar.MONDAY));
        Assert.assertThat(retorno.getDataRetorno(), caiNumaSegunda()); //melhor forma.

    }

}
