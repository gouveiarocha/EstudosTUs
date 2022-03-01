package UdemyCurso.matchers;

import UdemyCurso.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date> {

    private Integer qtdDias;

    public DataDiferencaDiasMatcher(Integer qtdDias) {
        this.qtdDias = qtdDias;
    }

    @Override
    protected boolean matchesSafely(Date date) {
        return DataUtils.dataAtual(date, DataUtils.obterDataComDiferencaDias(qtdDias));
    }

    @Override
    public void describeTo(Description description) {

    }

}
