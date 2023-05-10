package com.testes_api_consultas.Utils;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import com.google.gson.Gson;
import com.testes_api_consultas.Models.Medico;


public class MedicoUtils {

    public Medico cadastrarMedico(String bodyMedico) {

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
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .response();

        Gson gson = new Gson();
        Medico medicoCadastrado = gson.fromJson(responseMedico.asString(), Medico.class);

        return medicoCadastrado;

    }
    
}
