package com.testes_api_consultas.login;

import org.junit.Test;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;

public class LoginTest {
    
    @Test
    public void testDadoUsuarioCadastradoObtenhoTokenStatusCode200(){
        baseURI = "http://localhost";
        port = 8080;

         given()
            .body("{\n" +
            "  \"login\": \"ana.souza@voll.med\", \n" +
            "  \"senha\": \"123456\"\n" + "}")
            .contentType(ContentType.JSON)
        .when()
            .post("/login")
        .then()
            .assertThat()
                .statusCode(200)
                .log().all()
                .extract()
                .path("token");
    }
}
