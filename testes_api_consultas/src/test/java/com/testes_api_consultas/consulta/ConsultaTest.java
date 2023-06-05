package com.testes_api_consultas.consulta;

import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.testes_api_consultas.Models.Consulta;
import com.testes_api_consultas.Models.EspecialidadeMedico;
import com.testes_api_consultas.Models.Medico;
import com.testes_api_consultas.Models.Paciente;
import com.testes_api_consultas.Utils.ConsultaUtils;
import com.testes_api_consultas.Utils.LoginUtils;
import com.testes_api_consultas.Utils.MedicoUtils;
import com.testes_api_consultas.Utils.MessagesPropertiesUtils;
import com.testes_api_consultas.Utils.PacienteUtils;
import com.testes_api_consultas.baseTest.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.util.stream.Stream;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ConsultaTest extends BaseTest {

    MessagesPropertiesUtils messagesPropertiesUtils = new MessagesPropertiesUtils();

    @DisplayName("Teste Agendar Consulta com Especialidade Definida")
    @ParameterizedTest
    @MethodSource("horariosDatasEspecialidadesConsultas")
    public void testAgendarConsultaComEspecialidadeComStatus200(int hora, int minuto, DayOfWeek dia, EspecialidadeMedico especialidadeMedico) {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Consulta consulta = new Consulta().criarConsultaEspecialidadeDefinida(objPacienteResponse.id, hora, minuto, dia, especialidadeMedico);
        String bodyConsulta = gson.toJson(consulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        given()
                .header("Authorization", "Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
        .when()
                .post("/consultas")
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

    }

    private static Stream<Arguments> horariosDatasEspecialidadesConsultas() {
        return Stream.of(
                Arguments.of(7, 0, DayOfWeek.MONDAY, EspecialidadeMedico.CARDIOLOGIA),
                Arguments.of(7, 1, DayOfWeek.TUESDAY, EspecialidadeMedico.DERMATOLOGIA),
                Arguments.of(10, 0, DayOfWeek.FRIDAY, EspecialidadeMedico.CARDIOLOGIA),
                Arguments.of(18, 58, DayOfWeek.WEDNESDAY, EspecialidadeMedico.GINECOLOGIA),
                Arguments.of(18, 59, DayOfWeek.THURSDAY, EspecialidadeMedico.ORTOPEDIA));
    }

    @DisplayName("Teste Agendar Consulta com Medico Definido")
    @ParameterizedTest
    @MethodSource("horariosDatasConsultas")
    public void testAgendarConsultaComMedicoDefinidoComStatus200(int hora, int minuto, DayOfWeek dia) {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Medico medico = new Medico().criarMedico();
        String bodyMedico = gson.toJson(medico);

        MedicoUtils medicoUtils = new MedicoUtils();
        Medico objMedicoResponse = medicoUtils.requestCadastrarMedico(bodyMedico);

        Consulta consulta = new Consulta().criarConsultaMedicoDefinidoComDataHoraDia(objPacienteResponse.id, objMedicoResponse.id, hora, minuto, dia);
        String bodyConsulta = gson.toJson(consulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        given()
                .header("Authorization", "Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
        .when()
                .post("/consultas")
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

    }

    private static Stream<Arguments> horariosDatasConsultas() {
        return Stream.of(
                Arguments.of(7, 0, DayOfWeek.MONDAY),
                Arguments.of(7, 1, DayOfWeek.TUESDAY),
                Arguments.of(18, 58, DayOfWeek.WEDNESDAY),
                Arguments.of(10, 0, DayOfWeek.FRIDAY),
                Arguments.of(18, 59, DayOfWeek.THURSDAY));
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
                .header("Authorization", "Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
        .when()
                .post("/consultas")
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

        String response400 = responseConsulta.getBody().asString();

        assertEquals(messagesPropertiesUtils.getPropertyMessage("messegeConsultaTrintaMin"), response400);

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
                .header("Authorization", "Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
        .when()
                .post("/consultas")
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

        String response400 = responseConsulta.getBody().asString();

        assertEquals(messagesPropertiesUtils.getPropertyMessage("messegeConsultaForaDoHorario"), response400);

    }

    @DisplayName("Teste Agendar Consulta Antes Da Abertura")
    @ParameterizedTest
    @MethodSource("horariosDatasConsultasAntesAbertura")
    public void testAgendarConsultaAntesDaAberturaComStatus400(int hora, int minuto, DayOfWeek dia) {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Consulta consulta = new Consulta().criarConsultaAntesDaAberturaDaClinica(objPacienteResponse.id, hora, minuto, dia);
        String bodyConsulta = gson.toJson(consulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responseConsulta = (Response)

        given()
                .header("Authorization", "Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
        .when()
                .post("/consultas")
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

        String response400 = responseConsulta.getBody().asString();

        assertEquals(messagesPropertiesUtils.getPropertyMessage("messegeConsultaForaDoHorario"), response400);

    }

    private static Stream<Arguments> horariosDatasConsultasAntesAbertura() {
        return Stream.of(
                Arguments.of(6, 59, DayOfWeek.MONDAY),
                Arguments.of(5, 0, DayOfWeek.TUESDAY),
                Arguments.of( 6, 58, DayOfWeek.WEDNESDAY),
                Arguments.of(6, 30, DayOfWeek.THURSDAY),
                Arguments.of(4, 0, DayOfWeek.FRIDAY),
                Arguments.of(6, 59, DayOfWeek.SATURDAY),
                Arguments.of(10, 0, DayOfWeek.SUNDAY));
    }

    @DisplayName("Teste Agendar Consulta Depois Do Encerramento")
    @ParameterizedTest
    @MethodSource("horariosDatasConsultasDepoisEncerramento")
    public void testAgendarConsultaDepoisDoEncerramentoComStatus400(int hora, int minuto, DayOfWeek dia) {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Consulta consulta = new Consulta().criarConsultaDepoisDoEncerramentoDaClinica(objPacienteResponse.id, hora, minuto, dia);
        String bodyConsulta = gson.toJson(consulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responseConsulta = (Response)

        given()
                .header("Authorization", "Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
        .when()
                .post("/consultas")
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

        String response400 = responseConsulta.getBody().asString();

        assertEquals(messagesPropertiesUtils.getPropertyMessage("messegeConsultaForaDoHorario"), response400);

    }

    private static Stream<Arguments> horariosDatasConsultasDepoisEncerramento() {
        return Stream.of(
                Arguments.of(19, 0, DayOfWeek.MONDAY),
                Arguments.of(19, 1, DayOfWeek.TUESDAY),
                Arguments.of( 20, 0, DayOfWeek.WEDNESDAY),
                Arguments.of(21, 0, DayOfWeek.THURSDAY),
                Arguments.of(3, 0, DayOfWeek.FRIDAY),
                Arguments.of(19, 0, DayOfWeek.SATURDAY),
                Arguments.of(19, 30, DayOfWeek.SUNDAY));
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
                .header("Authorization", "Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
        .when()
                .post("/consultas")
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

        String response400 = responseConsulta.getBody().asString();

        assertEquals(messagesPropertiesUtils.getPropertyMessage("messageConsultaMedicoExcluido"), response400);

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
        Consulta objConsulta = consultaUtils.requestCadastrarConsulta(bodyConsulta);
        String bodyConsulta2 = gson.toJson(objConsulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responseConsulta = (Response) 
        
        given()
                .header("Authorization", "Bearer " + token)
                .body(bodyConsulta2)
                .contentType(ContentType.JSON)
        .when()
                .post("/consultas")
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

        String response400 = responseConsulta.getBody().asString();

        assertEquals(messagesPropertiesUtils.getPropertyMessage("messageOutraConsultaComMesmoMedicoMesmoHorario"), response400);

    }

    @Test
    @DisplayName("Teste Agendar Consulta com Paciente Excluído")
    public void testAgendarConsultaComPacienteExcluidoComStatus400() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Medico medico = new Medico().criarMedico();
        String bodyMedico = gson.toJson(medico);

        MedicoUtils medicoUtils = new MedicoUtils();
        Medico objMedicoResponse = medicoUtils.requestCadastrarMedico(bodyMedico);

        pacienteUtils.requestExcluirPacientePorId(objPacienteResponse.id);

        Consulta consulta = new Consulta().criarConsultaMedicoDefinido(objPacienteResponse.id, objMedicoResponse.id);
        String bodyConsulta = gson.toJson(consulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responseConsulta = (Response) 
        
        given()
                .header("Authorization", "Bearer " + token)
                .body(bodyConsulta)
                .contentType(ContentType.JSON)
        .when()
                .post("/consultas")
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

        String response400 = responseConsulta.getBody().asString();

        assertEquals(messagesPropertiesUtils.getPropertyMessage("messageConsultaPacienteExcluido"), response400);

    }

    @Test
    @DisplayName("Teste Agendar Outra Consulta no mesmo Dia para o mesmo Paciente")
    public void testAgendarOutraConsultaNoMesmoDiaParaMesmoPacienteComStatus400() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Consulta consulta = new Consulta().criarConsultaEspecialidadeDefinida(objPacienteResponse.id, 10, 0, DayOfWeek.MONDAY, EspecialidadeMedico.CARDIOLOGIA);
        String bodyConsulta = gson.toJson(consulta);

        ConsultaUtils consultaUtils = new ConsultaUtils();
        Consulta objConsulta = consultaUtils.requestCadastrarConsulta(bodyConsulta);

        Consulta objConsultaNova = new Consulta().criarConsultaEspecialidadeDefinida(objConsulta.idPaciente, 15, 0, DayOfWeek.MONDAY, EspecialidadeMedico.DERMATOLOGIA);
        String bodyConsultaNova = gson.toJson(objConsultaNova);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        Response responseConsulta = (Response) 
        
        given()
                .header("Authorization", "Bearer " + token)
                .body(bodyConsultaNova)
                .contentType(ContentType.JSON)
        .when()
                .post("/consultas")
        .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

        String response400 = responseConsulta.getBody().asString();

        assertEquals(messagesPropertiesUtils.getPropertyMessage("messagePacientePossuiConsultaMesmoDia"), response400);

    }

    @Test
    @DisplayName("Teste excluir consulta pelo id")
    public void testExcluirConsultaPeloIdComStatus204() {

        Paciente paciente = new Paciente().criarPaciente();
        Gson gson = new GsonBuilder().create();
        String bodyPaciente = gson.toJson(paciente);

        PacienteUtils pacienteUtils = new PacienteUtils();
        Paciente objPacienteResponse = pacienteUtils.requestCadastrarPaciente(bodyPaciente);

        Consulta consulta = new Consulta().criarConsultaEspecialidadeDefinida(objPacienteResponse.id, 10, 0, DayOfWeek.WEDNESDAY, EspecialidadeMedico.GINECOLOGIA);
        String bodyConsulta = gson.toJson(consulta);

        ConsultaUtils consultaUtils = new ConsultaUtils();
        Consulta objConsulta = consultaUtils.requestCadastrarConsulta(bodyConsulta);

        LoginUtils loginUtils = new LoginUtils();
        String token = loginUtils.requestLoginGetToken();

        given()
        .header("Authorization", "Bearer " + token)
        .when()
            .delete("/consultas/" + objConsulta.id)
        .then()
            .assertThat()
            .statusCode(HttpStatus.SC_NO_CONTENT);

        Consulta consultaCancelada = consultaUtils.requestObterConsultaPorId(objConsulta.id);
        assertFalse(consultaCancelada.ativo);

    }

}
