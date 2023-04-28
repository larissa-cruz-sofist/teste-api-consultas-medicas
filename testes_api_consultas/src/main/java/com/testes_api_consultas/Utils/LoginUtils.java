package com.testes_api_consultas.Utils;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;

import com.testes_api_consultas.Models.Login;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoginUtils {

    public String requestLoginGetToken() {

        baseURI = "http://localhost";
        port = 8080;

        Login login = new Login();
        Gson gson = new GsonBuilder().create();
        String bodyLogin = gson.toJson(login);

        String token = 
        given()
            .body(bodyLogin)
            .contentType(ContentType.JSON)
        .when()
            .post("/login")
        .then()
            .extract()
            .path("token");

    return token;

    }

}
