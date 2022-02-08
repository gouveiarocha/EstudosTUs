package UdemyCurso.servicos;

import UdemyCurso.entidades.Filme;
import UdemyCurso.entidades.Locacao;
import UdemyCurso.entidades.Usuario;
import UdemyCurso.exceptions.FilmeSemEstoqueException;
import UdemyCurso.exceptions.LocadoraException;
import UdemyCurso.utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static UdemyCurso.utils.DataUtils.adicionarDias;

public class LocacaoService {

    public Locacao alugarFilmes(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {

        if (filmes == null) {
            throw new LocadoraException("Filme NULL");
        }

        if (usuario == null || filmes.isEmpty()) {
            throw new LocadoraException("Usuario NULL");
        }

        for (Filme filme : filmes) {
            if (filme.getEstoque() == 0) {
                throw new FilmeSemEstoqueException();
            }
        }

        Locacao locacao = new Locacao();
        locacao.setListaFilmes(filmes);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());

        Double valorTotal = 0d;
        for (int i = 0; i < filmes.size(); i++) {
            Filme filme = filmes.get(i);
            Double valorFilme = filme.getPrecoLocacao();
            switch (i) {
                case 2:
                    valorFilme = valorFilme * 0.75;
                    break;
                case 3:
                    valorFilme = valorFilme * 0.5;
                    break;
                case 4:
                    valorFilme = valorFilme * 0.25;
                    break;
                case 5:
                    valorFilme = valorFilme * 0.0;
                    break;
            }
            valorTotal += valorFilme;
        }
        locacao.setValor(valorTotal);


        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)){
            dataEntrega = adicionarDias(dataEntrega, 1);
        }
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locacao...
        //TODO adicionar método para salvar

        return locacao;
    }


}