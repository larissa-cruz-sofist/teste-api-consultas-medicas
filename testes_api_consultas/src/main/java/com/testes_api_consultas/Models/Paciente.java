package com.testes_api_consultas.Models;

import java.util.Locale;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

public class Paciente {

    public String id;
    public String nome;
    public String email;
    public String cpf;
    public String telefone;
    public Boolean ativo;

    public Endereco endereco;

    public Paciente() {

        
    }

    public Paciente criarPaciente() {

        Faker faker = new Faker(new Locale("pt-BR"));
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("pt-BR"), new RandomService());

        this.nome = faker.name().fullName();
        this.email = fakeValuesService.bothify("???????##@vold.med");
        this.cpf = fakeValuesService.bothify("###.###.###-##");
        this.telefone = faker.phoneNumber().cellPhone();
        this.endereco = new Endereco().criarEndereco();

        return this;

    }

}
