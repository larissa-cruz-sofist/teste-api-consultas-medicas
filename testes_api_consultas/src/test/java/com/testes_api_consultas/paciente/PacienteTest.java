package com.testes_api_consultas.paciente;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.testes_api_consultas.Models.Paciente;
import com.testes_api_consultas.Models.Endereco;
import com.testes_api_consultas.Utils.LoginUtils;
import com.testes_api_consultas.Utils.PacienteUtils;

import java.util.Locale;

import com.testes_api_consultas.baseTest.BaseTest;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import com.github.javafaker.Faker;

public class PacienteTest extends BaseTest {

    @Test
    @DisplayName("Teste Cadastrar Novo Paciente")
    public void testCadastrarPacienteComStatus201() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        given()
                .header("Authorization","Bearer " + token)
                .body(bodyPaciente)
                .contentType(ContentType.JSON)
            .when()
                .post("/pacientes")
            .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);

    }

    @Test
    @DisplayName("Teste Alterar Nome Paciente")
    public void testAlterarNomeDePacienteCadastradoComStatus200() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);    

        Paciente objBodyPacientePut = new Paciente();
        objBodyPacientePut.id = objPacienteResponse.id;
        Faker faker = new Faker(new Locale("pt-BR"));
        String novoNome = faker.name().fullName();
        objBodyPacientePut.nome = novoNome;

        //Transformar para json objBodyPacientePut.id e objBodyPacientePut.nome
        String bodyPacientePut = gson.toJson(objBodyPacientePut);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();
        
        given()
        .header("Authorization", "Bearer " + token)
            .body(bodyPacientePut)
                .contentType(ContentType.JSON)
        .when()
            .put("/pacientes")
        .then()
            .log().all()
            .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("nome", Matchers.equalTo(novoNome));

    }

    @Test
    @DisplayName("Teste Alterar Numero do Endereco do Paciente")
    public void testAlterarNumeroEnderecoDePacienteCadastradoComStatus200() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Paciente objBodyPacientePut = new Paciente();
        Endereco endereco = new Endereco();
        objBodyPacientePut.endereco = endereco;
        objBodyPacientePut.endereco.numero = endereco.numero;

        objBodyPacientePut.id = objPacienteResponse.id;
        Faker faker = new Faker(new Locale("pt-BR"));
        String novoNumeroEndereco = faker.address().buildingNumber();
        objBodyPacientePut.endereco.numero = novoNumeroEndereco;

        String bodyPacientePut = gson.toJson(objBodyPacientePut);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();
        
        given()
        .header("Authorization", "Bearer " + token)
            .body(bodyPacientePut)
                .contentType(ContentType.JSON)
        .when()
            .put("/pacientes")
        .then()
            .log().all()
            .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("endereco.numero", Matchers.equalTo(novoNumeroEndereco));


    }

    @Test
    @DisplayName("Teste excluir paciente pelo id")
    public void testExcluirPacientePeloIdComStatus204() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente pacienteCadastrado = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        given()
        .header("Authorization", "Bearer " + token)
        .when()
            .delete("/pacientes/" + pacienteCadastrado.id)
        .then()
            .log().all()
            .assertThat()
            .statusCode(HttpStatus.SC_NO_CONTENT);

        Paciente pacienteCancelado = pacienteUtils.requestObterPacientePorId(pacienteCadastrado.id);
        assertFalse(pacienteCancelado.ativo);

    }
    
}
