package com.testes_api_consultas.Utils;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import com.google.gson.Gson;
import com.testes_api_consultas.Models.Medico;


public class MedicoUtils {

    public Medico requestCadastrarMedico(String bodyMedico) {

            LoginUtils loginUtils = new LoginUtils();
            String token = loginUtils.requestLoginGetToken();

            Response responseMedico = (Response) given()
            .header("Authorization","Bearer " + token)
                .body(bodyMedico)
                    .contentType(ContentType.JSON)
            .when()
                .post("/medicos")
            .then()
                .assertThat()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .response();

        Gson gson = new Gson();
        Medico medicoCadastrado = gson.fromJson(responseMedico.asString(), Medico.class);

        return medicoCadastrado;

    }

    public Medico requestObterMedicoPorId(String id) {
        
        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responseMedico = (Response) given()
        .header("Authorization","Bearer " + token)
        .when()
            .get("/medicos/" + id)
        .then()
            .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Gson gson = new Gson();
        Medico medico = gson.fromJson(responseMedico.asString(), Medico.class);

        return medico;

    }

    public void requestExcluirMedicoPorId(String idMedico) {

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        given()
            .header("Authorization", "Bearer " + token)
        .when()
            .delete("/medicos/" + idMedico)
        .then()
            .assertThat()
            .statusCode(HttpStatus.SC_NO_CONTENT);

    }
    
}
