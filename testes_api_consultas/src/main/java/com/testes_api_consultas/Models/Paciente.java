package com.testes_api_consultas.Models;

public class Paciente {

    public String nome;
    public String email;
    public String cpf;
    public String telefone;

    public Endereco endereco;

    public Paciente() {

        this.nome = "Ana";
        this.email = "ana@vold.med";
        this.cpf = "422.111.444-96";
        this.telefone = "952044126";
        this.endereco = new Endereco();
        
    }

}
