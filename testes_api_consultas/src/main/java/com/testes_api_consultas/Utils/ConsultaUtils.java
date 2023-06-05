package com.testes_api_consultas.Utils;

import com.google.gson.Gson;
import com.testes_api_consultas.Models.Consulta;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

public class ConsultaUtils {

    public Consulta requestCadastrarConsulta(String bodyConsulta) {

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response consultaResponse = (Response) 
            given()
                .header("Authorization","Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
            .when()
                .post("/consultas")
            .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        Gson gson = new Gson();
        Consulta consultaCadastrada = gson.fromJson(consultaResponse.asString(), Consulta.class);

        return consultaCadastrada;
                
    }
    
}
