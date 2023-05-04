package com.testes_api_consultas.Models;

public class Paciente {

    public String nome;
    public String email;
    public String cpf;
    public String telefone;

    public Endereco endereco;

    public Paciente() {

        this.nome = "Anny";
        this.email = "anny@vold.med";
        this.cpf = "422.111.413-96";
        this.telefone = "952044126";
        this.endereco = new Endereco();
        
    }

}
