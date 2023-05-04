package com.testes_api_consultas.medico;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

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

        Medico medico = new Medico().criarMedico();
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

    @Test
    public void testAlterarNomeDeMedicoCadastradoComStatus200() {

        Medico medico = new Medico().criarMedico();
        Gson gson = new GsonBuilder().create();
        String bodyMedico = gson.toJson(medico);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responseMedico = (Response) given()
        .header("Authorization","Bearer " + token)
            .body(bodyMedico)
                .contentType(ContentType.JSON)
        .when()
            .post("/medicos")
        .then()
            .log().all()
            .assertThat()
                .statusCode(201)
                .extract()
                .response();

        //Transformar o json em objeto Java        
        Medico objMedicoResponse = gson.fromJson(responseMedico.asString(), Medico.class);
        Medico objBodyMedicoPut = new Medico();
        objBodyMedicoPut.id = objMedicoResponse.id;
        objBodyMedicoPut.nome = "Carlos";

        //Transformar para json objBodyMedicoPut.id e objBodyMedicoPut.nome
        String bodyMedicoPut = gson.toJson(objBodyMedicoPut);
        
        given()
        .header("Authorization", "Bearer " + token)
            .body(bodyMedicoPut)
                .contentType(ContentType.JSON)
        .when()
            .put("/medicos")
        .then()
            .log().all()
            .assertThat()
                .statusCode(200);
    }

}
