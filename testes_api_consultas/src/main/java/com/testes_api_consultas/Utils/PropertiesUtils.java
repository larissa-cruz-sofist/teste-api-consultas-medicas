package com.testes_api_consultas.Utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesUtils {
    
    public String getProperty(String valorChave) {

        String appConfigPath = "/C:/Users/larissa.cruz_sofist/Desktop/Projetos/testes-api-consultas/testes_api_consultas/src/test/java/com/testes_api_consultas/resources/application.properties";
        Properties appProps = new Properties();

        try{

            appProps.load(new FileInputStream(appConfigPath));
    
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return appProps.getProperty(valorChave);

    }

}
