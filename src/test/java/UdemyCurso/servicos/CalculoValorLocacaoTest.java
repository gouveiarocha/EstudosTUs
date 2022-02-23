package UdemyCurso.servicos;

import UdemyCurso.entidades.Filme;
import UdemyCurso.entidades.Locacao;
import UdemyCurso.entidades.Usuario;
import UdemyCurso.exceptions.FilmeSemEstoqueException;
import UdemyCurso.exceptions.LocadoraException;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Testes parametrizáveis.
 */

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    private LocacaoService service;

    //criar as variaveis que ficam variando para os testes

    @Parameterized.Parameter
    public List<Filme> filmes;

    @Parameterized.Parameter(value = 1)
    public Integer valorLocacao;

    @Parameterized.Parameter(value = 2)
    public String cenario;

    @Before
    public void setup() {
        service = new LocacaoService();
    }

    private static Filme filme1 = new Filme("Filme 1", 1, 4.00);
    private static Filme filme2 = new Filme("Filme 2", 10, 4.00);
    private static Filme filme3 = new Filme("Filme 3", 15, 4.00);
    private static Filme filme4 = new Filme("Filme 4", 15, 4.00);
    private static Filme filme5 = new Filme("Filme 5", 15, 4.00);
    private static Filme filme6 = new Filme("Filme 6", 15, 4.00);

    @Parameterized.Parameters(name = "Teste: {2}")
    public static Collection<Object[]> getParametros() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(filme1, filme2, filme3), 11, "3 Filmes - 25%"},
                {Arrays.asList(filme1, filme2, filme3, filme4), 13, "4 Filmes - 50%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14, "5 Filmes - 75%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14, "6 Filmes - 100%"}
        });
    }

    @Test
    public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {

        Usuario usuario = new Usuario("Usuário 1");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 1, 4.00),
                new Filme("Filme 2", 10, 4.00),
                new Filme("Filme 3", 15, 4.00));


        Locacao resultado = service.alugarFilmes(usuario, filmes);

        Assert.assertThat(resultado.getValor(), CoreMatchers.is(11.0));

    }

}
