package com.testes_api_consultas.Models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;


public class Consulta {

    public String idPaciente;
    public String especialidade;
    public String data;
    public String idMedico;

    public Consulta() {

    }

    public Consulta criarConsultaEspecialidadeDefinida(String idPaciente) {

        LocalDateTime dataEHora = LocalDateTime.now().plusHours(1);

        this.idPaciente = idPaciente;
        this.especialidade = EspecialidadeMedico.DERMATOLOGIA.toString();
        this.data = dataEHora.toString();

        return this;
    }


    public Consulta criarConsultaMedicoDefinido(String idPaciente, String idMedico) {

        LocalDateTime dataEHora = LocalDateTime.now().plusHours(1);

        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.data = dataEHora.toString();

        return this;


    }

    public Consulta criarConsultaComMenosdeTrintaMinAntecedencia(String idPaciente) {

        LocalDateTime dataEHora = LocalDateTime.now().plusMinutes(20);

        this.idPaciente = idPaciente;
        this.especialidade = EspecialidadeMedico.DERMATOLOGIA.toString();
        this.data = dataEHora.toString();

        return this;
    }

    public Consulta criarConsultaNoDomingo(String idPaciente) {

        LocalDateTime dataEHora = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).atTime(10, 0);

        this.idPaciente = idPaciente;
        this.especialidade = EspecialidadeMedico.DERMATOLOGIA.toString();
        this.data = dataEHora.toString();

        return this;
    }

    public Consulta criarAntesDaAberturaDaClinica(String idPaciente) {

        LocalDateTime dataEHora = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(06, 55);

        this.idPaciente = idPaciente;
        this.especialidade = EspecialidadeMedico.DERMATOLOGIA.toString();
        this.data = dataEHora.toString();

        return this;
    }
    
}
