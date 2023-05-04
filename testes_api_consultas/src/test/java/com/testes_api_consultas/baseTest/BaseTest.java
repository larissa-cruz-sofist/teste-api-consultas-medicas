package com.testes_api_consultas.baseTest;

import static io.restassured.RestAssured.*;

import com.testes_api_consultas.Utils.PropertiesUtils;

public class BaseTest {

    public BaseTest() {

        PropertiesUtils propertiesUtils = new PropertiesUtils();
        baseURI = propertiesUtils.getProperty("baseURI");
        port = Integer.parseInt(propertiesUtils.getProperty("port"));
        
    }

}
