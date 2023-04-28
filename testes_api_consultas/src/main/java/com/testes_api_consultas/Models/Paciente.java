package com.testes_api_consultas.Models;

public class Paciente {

    public String nome;
    public String email;
    public String cpf;
    public String telefone;

    public Endereco endereco;

    public Paciente() {

        this.nome = "Joao";
        this.email = "joao@vold.med";
        this.cpf = "422.789.444-96";
        this.telefone = "952041559";
        this.endereco = new Endereco();
        
    }

}
