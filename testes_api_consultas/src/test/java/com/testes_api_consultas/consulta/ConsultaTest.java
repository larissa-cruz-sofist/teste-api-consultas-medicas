package com.testes_api_consultas.consulta;

import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.Test;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.testes_api_consultas.Models.Consulta;
import com.testes_api_consultas.Models.Medico;
import com.testes_api_consultas.Models.Paciente;
import com.testes_api_consultas.Utils.LoginUtils;
import com.testes_api_consultas.Utils.MedicoUtils;
import com.testes_api_consultas.Utils.PacienteUtils;
import com.testes_api_consultas.baseTest.BaseTest;

import io.restassured.http.ContentType;

public class ConsultaTest extends BaseTest {

    @Test
    @DisplayName("Teste Agendar Consulta com Especialidade Definida")
    public void testAgendarConsultaComEspecialidadeComStatus200() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.cadastrarPaciente(bodyPaciente);

        Consulta consulta = new Consulta().criarConsultaEspecialidadeDefinida(objPacienteResponse.id);
        String bodyConsulta = gson.toJson(consulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        given()
                .header("Authorization","Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
            .when()
                .post("/consultas")
            .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    @DisplayName("Teste Agendar Consulta com Medico Definido")
    public void testAgendarConsultaComMedicoDefinidoComStatus200() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.cadastrarPaciente(bodyPaciente);

        Medico medico = new Medico().criarMedico();
        String bodyMedico = gson.toJson(medico);

        MedicoUtils medicoUtils = new MedicoUtils();
        Medico objMedicoResponse = medicoUtils.cadastrarMedico(bodyMedico);

        Consulta consulta = new Consulta().criarConsultaMedicoDefinido(objPacienteResponse.id, objMedicoResponse.id);
        String bodyConsulta = gson.toJson(consulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        given()
                .header("Authorization","Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
            .when()
                .post("/consultas")
            .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

    }

}
