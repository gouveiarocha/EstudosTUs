package UdemyCurso.servicos;

import UdemyCurso.entidades.Filme;
import UdemyCurso.entidades.Locacao;
import UdemyCurso.entidades.Usuario;
import UdemyCurso.exceptions.FilmeSemEstoqueException;
import UdemyCurso.exceptions.LocadoraException;
import UdemyCurso.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;

public class LocacaoServiceTest {

    /**
     * Rules - servem para modificar comportamentos do teste
     */

    @Rule
    public ErrorCollector error = new ErrorCollector();
    @Rule
    public ExpectedException exception = ExpectedException.none();
    //----------------------------------------------------------

    /**
     * Tests
     */

    @Test
    public void testLocacao() throws Exception {

        //cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 1", 2, 5.00);

        //ação
        Locacao locacao = service.alugarFilme(usuario, filme);

        //verificação
        Assert.assertEquals(5.00, locacao.getValor(), 0.01);
        Assert.assertThat(
                DataUtils.dataAtual(locacao.getDataLocacao(),
                        new Date()),
                CoreMatchers.is(true));
        Assert.assertThat(
                DataUtils.dataAtual(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
                CoreMatchers.is(true));

        //verificando usando o ErrorCollector - aqui, caso ocorra erro, ele vai coletar o erro e
        //continuar os demais testes
//            error.checkThat(locacao.getValor(), CoreMatchers.is(5));
//            error.checkThat(
//                    DataUtils.dataAtual(locacao.getDataLocacao(),
//                            new Date()),
//                    CoreMatchers.is(false));
//            error.checkThat(
//                    DataUtils.dataAtual(locacao.getDataRetorno(),
//                            DataUtils.obterDataComDiferencaDias(1)),
//                    CoreMatchers.is(false));

    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void testLocacaoFilmeSemEstoque() throws Exception {

        //cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 1", 0, 5.00);

        //ação
        service.alugarFilme(usuario, filme);

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
    public void testLocacaoUsuarioVazio() throws FilmeSemEstoqueException {

        //cenário
        LocacaoService service = new LocacaoService();
        Filme filme = new Filme("Filme 1", 2, 5.00);

        //ação

        try {
            service.alugarFilme(null, filme);
        } catch (LocadoraException e) {
            Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuario NULL"));
        }

        System.out.println("Forma robusta..."); //na forma robusta, essa linha será executada, pois a falha esta sendfo tratada de forma independente no try

    }

    @Test
    public void testLocacaoFilmeVazio() throws FilmeSemEstoqueException, LocadoraException {

        //cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuário 1");
        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme NULL");

        //ação
        service.alugarFilme(usuario, null);

        System.out.println("Forma nova..."); //na forma nova, essa linha não sera executada pois o teste falhou antes e não chegou aqui.

    }

}
