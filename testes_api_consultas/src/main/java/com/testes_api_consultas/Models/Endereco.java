package com.testes_api_consultas.Models;

import java.util.Locale;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

public class Endereco {


    public String logradouro;
    public String bairro;
    public String cep;
    public String cidade;
    public String uf;
    public String numero;
    public String complemento;

    
    public Endereco() {


    }


    public Endereco criarEndereco() {

        Faker faker = new Faker(new Locale("pt-BR"));
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("pt-BR"), new RandomService());
        
        this.logradouro = faker.address().streetName();
        this.bairro = faker.address().lastName();
        this.cep = fakeValuesService.numerify("########");
        //this.cep = faker.address().zipCode();
        this.cidade = faker.address().city();
        this.uf = faker.address().stateAbbr();
        this.numero = faker.address().buildingNumber();
        this.complemento = faker.address().secondaryAddress();

        return this;

    }


} 

