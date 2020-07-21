package br.com.alura.leilao.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Lance implements Serializable, Comparable {

    private final Usuario usuario;
    private final double valor;

    public Lance(Usuario usuario, double valor) {
        this.usuario = usuario;
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public int compareTo(@NonNull Object o){
        Lance lance = (Lance) o;
        if(valor > lance.getValor()){
            return -1;
        }
        if(valor < lance.getValor()){
            return 1;
        }
        return 0;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lance lance = (Lance) o;
        return Double.compare(lance.valor, valor) == 0 &&
                Objects.equals(usuario, lance.usuario);
    }

    @Override
    public int hashCode(){

        return Objects.hash(usuario, valor);
    }
}