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

        this.nome = "Pamela Silva";
        this.email = "pamelasilva@vold.med";
        this.crm = "555588";
        this.telefone = "965408551";
        this.especialidade = "DERMATOLOGIA";
        this.endereco = new Endereco();

        return this;
    }

    
}
