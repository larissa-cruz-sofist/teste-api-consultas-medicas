package com.testes_api_consultas.Utils;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class MessagesPropertiesUtils {

    public String getPropertyMessage(String valorChave) {

        String appConfigPath = "/C:/Users/larissa.cruz_sofist/Desktop/Projetos/testes-api-consultas/testes_api_consultas/src/test/java/com/testes_api_consultas/resources/messages.properties";
        Properties appProps = new Properties();

        try{
            
            appProps.load(new InputStreamReader(new FileInputStream(appConfigPath), "UTF-8"));
    
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return appProps.getProperty(valorChave);

    }
    
}
