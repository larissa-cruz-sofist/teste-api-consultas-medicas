package com.testes_api_consultas.paciente;

import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.testes_api_consultas.Models.Paciente;
import com.testes_api_consultas.Utils.LoginUtils;

import com.testes_api_consultas.baseTest.BaseTest;

import io.restassured.http.ContentType;

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
    
}
