package com.testes_api_consultas.paciente;

import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.testes_api_consultas.Models.Paciente;
import com.testes_api_consultas.Utils.LoginUtils;
import java.util.Locale;

import com.testes_api_consultas.baseTest.BaseTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import com.github.javafaker.Faker;

public class PacienteTest extends BaseTest {

    @Test
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
                .statusCode(201);


    }

    @Test
    public void testAlterarNomeDePacienteCadastradoComStatus200() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responsePaciente = (Response) given()
        .header("Authorization","Bearer " + token)
            .body(bodyPaciente)
                .contentType(ContentType.JSON)
        .when()
            .post("/pacientes")
        .then()
            .log().all()
            .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        //Transformar o json em objeto Java        
        Paciente objPacienteResponse = gson.fromJson(responsePaciente.asString(), Paciente.class);
        Paciente objBodyPacientePut = new Paciente();
        objBodyPacientePut.id = objPacienteResponse.id;
        Faker faker = new Faker(new Locale("pt-BR"));
        objBodyPacientePut.nome = faker.name().fullName();

        //Transformar para json objBodyPacientePut.id e objBodyPacientePut.nome
        String bodyPacientePut = gson.toJson(objBodyPacientePut);
        
        given()
        .header("Authorization", "Bearer " + token)
            .body(bodyPacientePut)
                .contentType(ContentType.JSON)
        .when()
            .put("/pacientes")
        .then()
            .log().all()
            .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }
    
}
