package com.br.syncrename.Models;

import org.joda.time.DateTime;

public class Arquivo {

    private String nome;
    private DateTime data;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public DateTime getData() {
        return data;
    }

    public void setData(DateTime data) {
        this.data = data;
    }
}
