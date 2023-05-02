package com.testes_api_consultas.medico;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;

import com.testes_api_consultas.baseTest.BaseTest;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.testes_api_consultas.Models.Medico;
import com.testes_api_consultas.Utils.LoginUtils;

public class MedicoTest extends BaseTest {

    @Test
    public void testCadastrarMedicoComStatus201() {

        Medico medico = new Medico();
        Gson gson = new GsonBuilder().create();
        String bodyMedico = gson.toJson(medico);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        given()
        .header("Authorization","Bearer " + token)
            .body(bodyMedico)
                .contentType(ContentType.JSON)
        .when()
            .post("/medicos")
        .then()
            .log().all()
            .assertThat()
                .statusCode(201);
                

    }

}
