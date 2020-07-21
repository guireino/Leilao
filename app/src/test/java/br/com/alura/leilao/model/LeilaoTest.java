package br.com.alura.leilao.model;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import br.com.alura.leilao.exception.LanceMenor;
import br.com.alura.leilao.exception.LanceUsuario;
import br.com.alura.leilao.exception.Lance_5;
import br.com.alura.leilao.leilao.Leilao;
import br.com.alura.leilao.leilao.builder.LeilaoBuilder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.*;

public class LeilaoTest {

    public static final double DELTA = 0.0001;
    private final Leilao CONSOLE = new Leilao("Console");
    private final Usuario ALEX = new Usuario("Alex");

    @Test
    public void getDevolveDescricao(){
        // executar ação esperada
        String descricaoDevolvida = CONSOLE.getDescricao();

        // testar resultado esperado
        // assertEquals("Console", descricaoDevolvida);

        assertThat(descricaoDevolvida, is(equalTo("Console")));
    }

    @Test
    public void getDevolveMaior_1(){
        CONSOLE.propoe(new Lance(ALEX, 200.0));

        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

//        assertEquals(200.0, maiorLanceDevolvido, DELTA);
        assertThat(maiorLanceDevolvido, closeTo(200.0, DELTA));
//        assertThat(4.1 + 5.3, closeTo(4.4 + 5.0, DELTA));
    }

    @Test
    public void getDevolveMaior_2(){
        CONSOLE.propoe(new Lance(ALEX, 100.0));
        CONSOLE.propoe(new Lance(new Usuario("Fran"), 200.0));

        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        assertEquals(200.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void getDevolveMenorLance_1(){
        CONSOLE.propoe(new Lance(ALEX, 200.0));

        double menorLanceDevolvido = CONSOLE.getMenorLance();

        assertEquals(200.0, menorLanceDevolvido, DELTA);
    }

    @Test
    public void getDevolveMenor_1(){
        CONSOLE.propoe(new Lance(ALEX, 100.0));
        CONSOLE.propoe(new Lance(new Usuario("Fran"), 200.0));

        double menorLanceDevolvido = CONSOLE.getMenorLance();

        assertEquals(100.0, menorLanceDevolvido, DELTA);
    }

    @Test
    public void getDevolverTresMaiores_3(){
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Fran"), 300.0));
        CONSOLE.propoe(new Lance(ALEX, 400.0));

        // Test Driven Development
        List<Lance> tresMaioresLances = CONSOLE.tresMaioresLances();

//        assertEquals(3, tresMaioresLances.size());
//        assertThat(tresMaioresLances, hasSize(equalTo(3)));
//        assertEquals(400.0, tresMaioresLances.get(0).getValor(), DELTA);
//        assertThat(tresMaioresLances, hasItem(new Lance(ALEX, 400.0)));
//        assertEquals(300.0, tresMaioresLances.get(1).getValor(), DELTA);
//        assertEquals(200.0, tresMaioresLances.get(2).getValor(), DELTA);

//        assertThat(tresMaioresLances, contains(new Lance(ALEX, 400.0), new Lance(new Usuario("Fran"), 300.0),
//                new Lance(ALEX, 200.0)));

        assertThat(tresMaioresLances, both(Matchers.<Lance>hasSize(3)).and(contains(new Lance(ALEX, 400.0),
                new Lance(new Usuario("Fran"), 300.0), new Lance(ALEX, 200.0))));
    }

   @Test
    public void getDevolverTresMaioresLances(){

        CONSOLE.propoe(new Lance(ALEX, 300.0));
        final Usuario FRAN = new Usuario("Fran");
        CONSOLE.propoe(new Lance(FRAN, 400.0));
        CONSOLE.propoe(new Lance(ALEX, 500.0));
        CONSOLE.propoe(new Lance(FRAN, 600.0));

        final List<Lance> lances = CONSOLE.tresMaioresLances();

        assertEquals(3, lances.size());
        assertEquals(600.0, lances.get(0).getValor(), DELTA);
        assertEquals(500.0, lances.get(1).getValor(), DELTA);
        assertEquals(400.0, lances.get(2).getValor(), DELTA);

        CONSOLE.propoe(new Lance(ALEX, 700.0));

        List<Lance> lancesMaisUm = CONSOLE.tresMaioresLances();

        assertEquals(3, lancesMaisUm.size());
        assertEquals(700.0, lancesMaisUm.get(0).getValor(), DELTA);
        assertEquals(600.0, lancesMaisUm.get(1).getValor(), DELTA);
        assertEquals(500.0, lancesMaisUm.get(2).getValor(), DELTA);
   }

   @Test
   public void getDevolverZeroMaiorLance(){
        double lance = CONSOLE.getMaiorLance();
        assertEquals(0.0, lance, DELTA);
   }

   @Test
   public void getDevolverZeroMenorLance(){
        double lance = CONSOLE.getMenorLance();
        assertEquals(0.0, lance, DELTA);
   }

   @Test(expected = LanceMenor.class)
   public void getNoAddLance_2(){
        CONSOLE.propoe(new Lance(ALEX, 500.0));
        CONSOLE.propoe(new Lance(new Usuario("Fran"), 400.0));
   }

   @Test(expected = LanceUsuario.class)
   public void getNoAddLanceMesmoUsuario(){
       CONSOLE.propoe(new Lance(ALEX, 500.0));
       CONSOLE.propoe(new Lance(ALEX, 600.0));
   }

   @Test(expected = Lance_5.class)
   public void getNoAddLanceMaiorTodos(){

       final Usuario FRAN = new Usuario("Fran");
       final Leilao console = new LeilaoBuilder("Console").lance(ALEX, 100.0).lance(FRAN, 200.0)
               .lance(ALEX, 300.0).lance(FRAN, 400.0).lance(ALEX, 500.0).lance(FRAN, 600.0)
               .lance(ALEX, 700.0).lance(FRAN, 800.0).lance(ALEX, 900.0).lance(FRAN, 1000.0)
               .lance(ALEX, 1100.0).build();
   }
}