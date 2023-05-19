package com.testes_api_consultas.Utils;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import com.google.gson.Gson;
import com.testes_api_consultas.Models.Paciente;


public class PacienteUtils {

    public Paciente requestCadastrarPaciente(String bodyPaciente) {

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

        Gson gson = new Gson();
        Paciente pacienteCadastrado = gson.fromJson(responsePaciente.asString(), Paciente.class);

        return pacienteCadastrado;

    }

    public Paciente requestObterPacientePorId(String id) {
        
        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responsePaciente = (Response) given()
        .header("Authorization","Bearer " + token)
        .when()
            .get("/pacientes/" + id)
        .then()
            .log().all()
            .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Gson gson = new Gson();
        Paciente paciente = gson.fromJson(responsePaciente.asString(), Paciente.class);

        return paciente;

    }
    
}
