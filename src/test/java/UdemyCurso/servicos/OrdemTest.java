package UdemyCurso.servicos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Exemplo onde o junit n garante a ordem de execução dos metodos de teste e gera problemas.
 * Para contornar esse problema, usar a anotação fixMethodOrder
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //por ordem alfabética
public class OrdemTest {

    public static int contador = 0;

    @Test
    public void inicia(){
        contador = 1;
    }

    @Test
    public void verifica(){
        Assert.assertEquals(1, contador);
    }

}
