package com.testes_api_consultas.medico;

import org.junit.Test;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;

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

        String token = requestLoginGetToken();

        given()
        .header("Authorization","Bearer " + token)
            .body("{\n" +
                "  \"nome\": \"Maria\", \n" +
                "  \"email\": \"maria@vold.med\", \n" + 
                "  \"crm\": \"000000\", \n" + 
                "  \"telefone\": \"20601257\", \n" + 
                "  \"especialidade\": \"ORTOPEDIA\", \n" + 
                "  \"endereco\": { \n" + 
                "   \"logradouro\": \"rua lopes\", \n" +
                "   \"bairro\": \"mooca\", \n" +
                "   \"cep\": \"03231896\", \n" +
                "   \"cidade\": \"Sao Paulo\", \n" +
                "   \"uf\": \"SP\", \n" +
                "   \"numero\": \"2687\", \n" +
                "   \"complemento\": \"bloco 3, apto 11\" \n" + 
                "}\n" + 
                "}")
                .contentType(ContentType.JSON)
        .when()
            .post("/medicos")
        .then()
            .log().all()
            .assertThat()
                .statusCode(201);
                

    }

}
