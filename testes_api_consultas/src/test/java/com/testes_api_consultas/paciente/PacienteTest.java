package com.testes_api_consultas.paciente;

import static io.restassured.RestAssured.*;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;

public class PacienteTest {

    public static String requestLoginGetToken() {

        String token = 
        given()
            .body("{\n" +
            "  \"login\": \"ana.souza@voll.med\", \n" +
            "  \"senha\": \"123456\"\n" + "}")
            .contentType(ContentType.JSON)
        .when()
            .post("/login")
        .then()
            .extract()
            .path("token");

    return token;

    }

    @Test
    public void testCadastrarPacienteComStatus201() {

        baseURI = "http://localhost";
        port = 8080;

        Paciente paciente = new Paciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        String token = requestLoginGetToken();

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
