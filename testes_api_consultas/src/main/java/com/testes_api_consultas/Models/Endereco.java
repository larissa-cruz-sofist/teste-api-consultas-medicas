package com.testes_api_consultas.Models;

public class Endereco {

    public String logradouro;
    public String bairro;
    public String cep;
    public String cidade;
    public String uf;
    public String numero;
    public String complemento;

    public Endereco() {
        
        this.logradouro = "rua indaia";
        this.bairro = "vila mariana";
        this.cep = "56987142";
        this.cidade = "sao paulo";
        this.uf = "sp";
        this.numero = "150";
        this.complemento = "bloco 2, apto 13";
    }

    
}