import UdemyCurso.entidades.Usuario;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class Asserts {

    @Test
    public void test(){

        Assert.assertTrue(true);
        Assert.assertFalse(false);

        Assert.assertEquals(1,1); //também é possivel comparar outros tipos de dados.

        //equals com double ou float, é preciso um delta, que indica a quantidade de casas decimais a comparar.
        Assert.assertEquals(0.51, 0.51, 0.01);
        //exemplo mais claro usando o PI, se não usarmos o delta, o teste irá falhar.
        Assert.assertEquals(Math.PI, 3.14, 0.01);

        //comparando usando tipo primitivo com classe wrapper
        int i = 5;
        Integer i2 = 5;
        Assert.assertEquals(Integer.valueOf(i), i2);
        Assert.assertEquals(i, i2.intValue());

        //strings
        Assert.assertEquals("bola", "bola");
        Assert.assertNotEquals("bola", "casa");
        Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
        Assert.assertTrue("bola".startsWith("bo"));

        //objetos
        Usuario u1 = new Usuario("Usuario 1");
        Usuario u2 = new Usuario("Usuario 1");
        Usuario u3 = null;
        Assert.assertEquals(u1, u2); // para o teste funcionar, a classe precisa sobrescrever o método equals
        //Assert.assertSame(u1, u2); //falha: apesar de serem iguais, não é a mesma instancia
        Assert.assertSame(u1, u1); //instancias iguais
        Assert.assertNotSame(u1, u2); //instancias diferentes
        Assert.assertTrue(u3==null);
        Assert.assertNull(u3);
        Assert.assertNotNull(u1);

        //assertThat
        Assert.assertThat(5, is(5)); // assumeque(5, é 5)
        //mais exemplos:
        Assert.assertThat(5, is(equalTo(5)));
        Assert.assertThat(5, is((not(10))));

    }

}
