package com.testes_api_consultas.consulta;

import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.Test;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.testes_api_consultas.Models.Consulta;
import com.testes_api_consultas.Utils.LoginUtils;

import com.testes_api_consultas.baseTest.BaseTest;

import io.restassured.http.ContentType;

public class ConsultaTest extends BaseTest {

    @Test
    @DisplayName("Teste Agendar Consulta com Especialidade Definida")
    public void testAgendarConsultaComEspecialidadeComStatus200() {

        Consulta consulta = new Consulta().criarConsultaEspecialidadeDefinida();
        Gson gson = new GsonBuilder().create();
        String bodyConsulta = gson.toJson(consulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        given()
                .header("Authorization","Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
            .when()
                .post("/consultas")
            .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

    }

}
