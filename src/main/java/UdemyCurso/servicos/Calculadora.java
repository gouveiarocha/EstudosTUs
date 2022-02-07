package UdemyCurso.servicos;

import UdemyCurso.exceptions.NaoPodeDividirPor0Exception;

public class Calculadora {

    public Calculadora() {
    }


    public int somar(int a, int b) {
        return a + b;
    }

    public int subtrair(int a, int b) {
        return a - b;
    }

    public int dividir(int a, int b) throws NaoPodeDividirPor0Exception {
        if (b == 0 ){
            throw new NaoPodeDividirPor0Exception();
        }
        return a / b;
    }
}
