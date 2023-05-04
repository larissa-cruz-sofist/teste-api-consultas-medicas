package com.testes_api_consultas.Models;

public class Medico {
    
    public String id;
    public String nome;
    public String email;
    public String crm;
    public String telefone;
    public String especialidade;


    public Endereco endereco;


    public Medico() {

        
    }

    public Medico criarMedico() {

        this.nome = "Carlos Roberto";
        this.email = "carlosroberto@vold.med";
        this.crm = "555771";
        this.telefone = "965408001";
        this.especialidade = "DERMATOLOGIA";
        this.endereco = new Endereco();

        return this;
    }

    
}
