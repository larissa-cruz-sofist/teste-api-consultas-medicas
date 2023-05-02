package com.testes_api_consultas.baseTest;

import static io.restassured.RestAssured.*;

public class BaseTest {

    public BaseTest() {

        baseURI = "http://localhost";
        port = 8080;

    }

}
