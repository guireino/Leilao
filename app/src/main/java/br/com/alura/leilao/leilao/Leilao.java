package br.com.alura.leilao.leilao;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alura.leilao.exception.LanceMenor;
import br.com.alura.leilao.exception.LanceUsuario;
import br.com.alura.leilao.exception.Lance_5;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Usuario;

public class Leilao implements Serializable {

    private final String descricao;
    private final List<Lance> lances;
    private double maiorLance = 0.0;
    private double menorLance = 0.0;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void propoe(Lance lance){

        valido(lance);
        lances.add(lance);
        double valorLance = lance.getValor();
        if (defineMaior(valorLance)) return;

        Collections.sort(lances);
        calculaMaiorLance(valorLance);
    }

    private boolean defineMaior(double valorLance) {
        if(lances.size() == 1){
            maiorLance = valorLance;
            menorLance = valorLance;
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void valido(Lance lance){
        double valor = lance.getValor();

        if (lanceForMenor(valor)) throw new LanceMenor();

        if(seTemLance()){
            Usuario usuarioNovo = lance.getUsuario();
            if (lanceForMesmo(usuarioNovo)) throw new LanceUsuario();
            if (usuarioDeuCinco(usuarioNovo)) throw new Lance_5();
        }
    }

    private boolean seTemLance(){
        return !lances.isEmpty();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean usuarioDeuCinco(Usuario usuarioNovo) {
        int lancesUsuario = 0;
        for (Lance i : lances){

            Usuario usuario = i.getUsuario();

            if(usuario.equals(usuarioNovo)){
                lancesUsuario++;
                if(lancesUsuario == 5){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean lanceForMesmo(Usuario usuarioNovo) {
        Usuario ultimoUsuario = lances.get(0).getUsuario();
        if(usuarioNovo.equals(ultimoUsuario)){
            return true;
        }
        return false;
    }

    private boolean lanceForMenor(double valor) {
        if(maiorLance > valor){
            return true;
        }
        return false;
    }

    private void calculaMenorLance(double valorLance) {
        if (valorLance < menorLance){
            menorLance = valorLance;
        }
    }

    private void calculaMaiorLance(double valorLance) {
        if(valorLance > maiorLance){
            maiorLance = valorLance;
        }
    }

    public double getMenorLance(){
        return menorLance;
    }

    public double getMaiorLance(){
        return maiorLance;
    }

    public String getDescricao(){
        return descricao;
    }

    public List<Lance> tresMaioresLances(){

        int size = lances.size();
        if(size > 3){
            size = 3;
        }
        return lances.subList(0, size);
    }

    public int quantidadeLances() {
        return  lances.size();
    }
}