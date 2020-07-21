package br.com.alura.leilao.leilao.builder;

import android.os.Build;
import android.support.annotation.RequiresApi;

import br.com.alura.leilao.leilao.Leilao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Usuario;

public class LeilaoBuilder {

    private final Leilao leilao;

    public LeilaoBuilder(String descricao) {
        this.leilao = new Leilao(descricao);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public LeilaoBuilder lance(Usuario usuario, double valor) {
        this.leilao.propoe(new Lance(usuario, valor));
        return this;
    }

    public Leilao build() {
        return leilao;
    }

}
