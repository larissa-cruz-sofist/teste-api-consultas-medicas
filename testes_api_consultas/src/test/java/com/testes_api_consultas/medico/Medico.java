package com.testes_api_consultas.medico;

import com.testes_api_consultas.endereco.Endereco;


public class Medico {
    
    public String nome;
    public String email;
    public String crm;
    public String telefone;
    public String especialidade;

    public Endereco endereco;


    public Medico() {
        this.nome = "Clarissa";
        this.email = "clarissa@vold.med";
        this.crm = "111222";
        this.telefone = "965408510";
        this.especialidade = "DERMATOLOGIA";
        this.endereco = new Endereco();
        
    }

    
}
