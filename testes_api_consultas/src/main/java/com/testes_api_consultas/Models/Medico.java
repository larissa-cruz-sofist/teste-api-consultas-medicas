package com.testes_api_consultas.Models;

public class Medico {
    
    public String nome;
    public String email;
    public String crm;
    public String telefone;
    public String especialidade;

    public Endereco endereco;


    public Medico() {
        this.nome = "Paula";
        this.email = "paula@vold.med";
        this.crm = "333111";
        this.telefone = "965408741";
        this.especialidade = "DERMATOLOGIA";
        this.endereco = new Endereco();
        
    }

    
}
