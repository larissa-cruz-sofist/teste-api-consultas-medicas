package com.testes_api_consultas.Models;

public class Medico {
    
    public String nome;
    public String email;
    public String crm;
    public String telefone;
    public String especialidade;

    public Endereco endereco;


    public Medico() {
        this.nome = "Pedro";
        this.email = "pedro@vold.med";
        this.crm = "333444";
        this.telefone = "965408574";
        this.especialidade = "DERMATOLOGIA";
        this.endereco = new Endereco();
        
    }

    
}
