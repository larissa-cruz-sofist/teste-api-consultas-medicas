package com.testes_api_consultas.medico;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import org.hamcrest.Matchers;

import java.util.Locale;

import com.testes_api_consultas.baseTest.BaseTest;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.testes_api_consultas.Models.Endereco;
import com.testes_api_consultas.Models.Medico;
import com.testes_api_consultas.Utils.LoginUtils;
import com.testes_api_consultas.Utils.MedicoUtils;
import com.github.javafaker.Faker;

public class MedicoTest extends BaseTest {

    @Test
    @DisplayName("Teste Cadastrar Novo Medico")
    public void testCadastrarMedicoComStatus201() {

        Medico medico = new Medico().criarMedico();
        Gson gson = new GsonBuilder().create();
        String bodyMedico = gson.toJson(medico);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        given()
                .header("Authorization", "Bearer " + token)
                .body(bodyMedico)
                .contentType(ContentType.JSON)
            .when()
                .post("/medicos")
            .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);

    }

    @Test
    @DisplayName("Teste Alterar Nome do Medico")
    public void testAlterarNomeDeMedicoCadastradoComStatus200() {

        Medico medico = new Medico().criarMedico();
        Gson gson = new GsonBuilder().create();
        String bodyMedico = gson.toJson(medico);

        MedicoUtils medicoUtils = new MedicoUtils();
        Medico objMedicoResponse = medicoUtils.cadastrarMedico(bodyMedico);
      
        Medico objBodyMedicoPut = new Medico();
        objBodyMedicoPut.id = objMedicoResponse.id;
        Faker faker = new Faker(new Locale("pt-BR"));
        String novoNome = faker.name().fullName();
        objBodyMedicoPut.nome = novoNome;

        //Transformar para json objBodyMedicoPut.id e objBodyMedicoPut.nome
        String bodyMedicoPut = gson.toJson(objBodyMedicoPut);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();
        
        given()
        .header("Authorization", "Bearer " + token)
            .body(bodyMedicoPut)
                .contentType(ContentType.JSON)
        .when()
            .put("/medicos")
        .then()
            .log().all()
            .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("nome", Matchers.equalTo(novoNome));

    }

    @Test
    @DisplayName("Teste Alterar Numero do Endereco do Medico")
    public void testAlterarNumeroEnderecoDeMedicoCadastradoComStatus200() {

        Medico medico = new Medico().criarMedico();
        Gson gson = new GsonBuilder().create();
        String bodyMedico = gson.toJson(medico);

        MedicoUtils medicoUtils = new MedicoUtils();
        Medico objMedicoResponse = medicoUtils.cadastrarMedico(bodyMedico);

        Medico objBodyMedicoPut = new Medico();
        Endereco endereco = new Endereco();
        objBodyMedicoPut.endereco = endereco;
        objBodyMedicoPut.endereco.numero = endereco.numero;

        objBodyMedicoPut.id = objMedicoResponse.id;
        Faker faker = new Faker(new Locale("pt-BR"));
        String novoNumeroEndereco = faker.address().buildingNumber();
        objBodyMedicoPut.endereco.numero = novoNumeroEndereco;

        String bodyMedicoPut = gson.toJson(objBodyMedicoPut);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();
        
        given()
        .header("Authorization", "Bearer " + token)
            .body(bodyMedicoPut)
                .contentType(ContentType.JSON)
        .when()
            .put("/medicos")
        .then()
            .log().all()
            .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("endereco.numero", Matchers.equalTo(novoNumeroEndereco));


    }

    @Test
    @DisplayName("Teste excluir médico pelo id")
    public void testExcluirMedicoPeloIdComStatus204() {

        Medico medico = new Medico().criarMedico();
        Gson gson = new GsonBuilder().create();
        String bodyMedico = gson.toJson(medico);

        MedicoUtils medicoUtils = new MedicoUtils();
        Medico medicoCadastrado = medicoUtils.cadastrarMedico(bodyMedico);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        given()
        .header("Authorization", "Bearer " + token)
        .when()
            .delete("/medicos/" + medicoCadastrado.id)
        .then()
            .log().all()
            .assertThat()
            .statusCode(HttpStatus.SC_NO_CONTENT);

    }


}
