package UdemyCurso.servicos;

import UdemyCurso.exceptions.NaoPodeDividirPor0Exception;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {

    private Calculadora calculadora;

    @Before
    public void setup() {
        calculadora = new Calculadora();
    }

    @Test
    public void deveSomarDoisValores() {

        //cenario
        int a = 5;
        int b = 10;

        //acao
        int resultado = calculadora.somar(a, b);

        //verificacao
        Assert.assertEquals(15, resultado);
    }

    @Test
    public void deveSubtrairDoisValores() {
        int a = 10;
        int b = 5;
        Assert.assertEquals(a - b, calculadora.subtrair(a, b));
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPor0Exception {
        int a = 100;
        int b = 50;
        Assert.assertEquals(a / b, calculadora.dividir(a, b));
    }

    @Test(expected = NaoPodeDividirPor0Exception.class)
    public void lancarExcecaoDivisaoPor0() throws NaoPodeDividirPor0Exception {
        int a = 100;
        int b = 0;
        calculadora.dividir(a, b);
    }

}
