package com.testes_api_consultas.medico;

import org.junit.Test;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MedicoTest {



    public static String requestLoginGetToken() {

        String token = given()
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
    public void testCadastrarMedicoComStatus201() {


        baseURI = "http://localhost";
        port = 8080;

        Medico medico = new Medico();
        Gson gson = new GsonBuilder().create();
        String bodyMedico = gson.toJson(medico);

        String token = requestLoginGetToken();

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
