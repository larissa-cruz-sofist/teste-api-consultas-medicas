package com.testes_api_consultas.Utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesUtils {
    
    public String getProperty(String valorChave) {

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "com/testes_api_consultas/resources/application.properties";
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
