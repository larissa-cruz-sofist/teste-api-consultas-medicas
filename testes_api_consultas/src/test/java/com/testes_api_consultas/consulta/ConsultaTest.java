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
import com.testes_api_consultas.Utils.ConsultaUtils;
import com.testes_api_consultas.Utils.LoginUtils;
import com.testes_api_consultas.Utils.MedicoUtils;
import com.testes_api_consultas.Utils.PacienteUtils;
import com.testes_api_consultas.baseTest.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ConsultaTest extends BaseTest {

    @Test
    @DisplayName("Teste Agendar Consulta com Especialidade Definida")
    public void testAgendarConsultaComEspecialidadeComStatus200() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

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
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Medico medico = new Medico().criarMedico();
        String bodyMedico = gson.toJson(medico);

        MedicoUtils medicoUtils = new MedicoUtils();
        Medico objMedicoResponse = medicoUtils.requestCadastrarMedico(bodyMedico);

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

    @Test
    @DisplayName("Teste Agendar Consulta com Menos de Trinta Minutos de Antecedencia")
    public void testAgendarConsultaComMenosTrintaMinComStatus400() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Consulta consulta = new Consulta().criarConsultaComMenosdeTrintaMinAntecedencia(objPacienteResponse.id);
        String bodyConsulta = gson.toJson(consulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responseConsulta = (Response) 

            given()
                .header("Authorization","Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
            .when()
                .post("/consultas")
            .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

            
            String response400 = responseConsulta.getBody().asString();

            assertEquals("Consulta deve ser agendada com antecedência mínima de 30 minutos", response400);

    }

    @Test
    @DisplayName("Teste Agendar Consulta No Domingo")
    public void testAgendarConsultaNoDomingoComStatus400() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Consulta consulta = new Consulta().criarConsultaNoDomingo(objPacienteResponse.id);
        String bodyConsulta = gson.toJson(consulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responseConsulta = (Response) 

            given()
                .header("Authorization","Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
            .when()
                .post("/consultas")
            .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

            
            String response400 = responseConsulta.getBody().asString();

            assertEquals("Consulta fora do horário de funcionamento da clínica", response400);

    }

    @Test
    @DisplayName("Teste Agendar Consulta Antes Da Abertura")
    public void testAgendarConsultaAntesDaAberturaComStatus400() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Consulta consulta = new Consulta().criarConsultaAntesDaAberturaDaClinica(objPacienteResponse.id);
        String bodyConsulta = gson.toJson(consulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responseConsulta = (Response) 

            given()
                .header("Authorization","Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
            .when()
                .post("/consultas")
            .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

            
            String response400 = responseConsulta.getBody().asString();

            assertEquals("Consulta fora do horário de funcionamento da clínica", response400);

    }

    @Test
    @DisplayName("Teste Agendar Consulta Depois Do Encerramento")
    public void testAgendarConsultaDepoisDoEncerramentoComStatus400() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Consulta consulta = new Consulta().criarConsultaDepoisDoEncerramentoDaClinica(objPacienteResponse.id);
        String bodyConsulta = gson.toJson(consulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responseConsulta = (Response) 

            given()
                .header("Authorization","Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
            .when()
                .post("/consultas")
            .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

            
            String response400 = responseConsulta.getBody().asString();

            assertEquals("Consulta fora do horário de funcionamento da clínica", response400);

    }

    @Test
    @DisplayName("Teste Agendar Consulta com Medico Excluído")
    public void testAgendarConsultaComMedicoExcluidoComStatus400() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Medico medico = new Medico().criarMedico();
        String bodyMedico = gson.toJson(medico);

        MedicoUtils medicoUtils = new MedicoUtils();
        Medico objMedicoResponse = medicoUtils.requestCadastrarMedico(bodyMedico);

        medicoUtils.requestExcluirMedicoPorId(objMedicoResponse.id);

        Consulta consulta = new Consulta().criarConsultaMedicoDefinido(objPacienteResponse.id, objMedicoResponse.id);
        String bodyConsulta = gson.toJson(consulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responseConsulta = (Response) 
            given()
                .header("Authorization","Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
            .when()
                .post("/consultas")
            .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();


            String response400 = responseConsulta.getBody().asString();

            assertEquals("Consulta não pode ser agendada com médico excluído", response400);

    }

    @Test
    @DisplayName("Teste Agendar Outra Consulta no mesmo horario com mesmo Medico")
    public void testAgendarOutraConsultaNoMesmoHorarioComMesmoMedicoComStatus400() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Medico medico = new Medico().criarMedico();
        String bodyMedico = gson.toJson(medico);

        MedicoUtils medicoUtils = new MedicoUtils();
        Medico objMedicoResponse = medicoUtils.requestCadastrarMedico(bodyMedico);

        Consulta consulta = new Consulta().criarConsultaMedicoDefinido(objPacienteResponse.id, objMedicoResponse.id);
        String bodyConsulta = gson.toJson(consulta);

        ConsultaUtils consultaUtils = new ConsultaUtils();
        Consulta objConsulta = consultaUtils.requestCadastrarConsultaComMedicoDefinido(bodyConsulta);
        String bodyConsulta2 = gson.toJson(objConsulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responseConsulta = (Response) 
            given()
                .header("Authorization","Bearer " + token)
                .body(bodyConsulta2)
                .contentType(ContentType.JSON)
            .when()
                .post("/consultas")
            .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();


            String response400 = responseConsulta.getBody().asString();

            assertEquals("Médico já possui outra consulta agendada nesse mesmo horário", response400);

    }

}
