package com.testes_api_consultas.Models;

import java.util.Locale;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

public class Medico {
    
    public String id;
    public String nome;
    public String email;
    public String crm;
    public String telefone;
    public String especialidade;
    public Boolean ativo;


    public Endereco endereco;


    public Medico() {

        
    }

    public Medico criarMedico() {

        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("pt-BR"), new RandomService());
        Faker faker = new Faker(new Locale("pt-BR"));

        this.nome = faker.name().fullName();
        this.email = fakeValuesService.bothify("???????##@vold.med");
        this.crm =  fakeValuesService.numerify("######");
        this.telefone = faker.phoneNumber().cellPhone();
        this.especialidade = EspecialidadeMedico.GINECOLOGIA.toString();
        this.endereco = new Endereco().criarEndereco();

        return this;
    }

    
}
