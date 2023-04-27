package com.testes_api_consultas.paciente;

import com.testes_api_consultas.endereco.Endereco;

public class Paciente {

    public String nome;
    public String email;
    public String cpf;
    public String telefone;

    public Endereco endereco;

    public Paciente() {

        this.nome = "Laura";
        this.email = "laura@vold.med";
        this.cpf = "422.789.335-96";
        this.telefone = "952041089";
        this.endereco = new Endereco();
        
    }

}
