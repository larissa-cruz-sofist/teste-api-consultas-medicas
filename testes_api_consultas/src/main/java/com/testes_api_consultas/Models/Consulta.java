package com.testes_api_consultas.Models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;


public class Consulta {

    public String id;
    public String idPaciente;
    public String especialidade;
    public String data;
    public String idMedico;
    public Boolean ativo;

    public Consulta() {

    }

    public Consulta criarConsultaEspecialidadeDefinida(String idPaciente, int hora, int minuto, DayOfWeek dia, EspecialidadeMedico especialidadeMedico) {

        LocalDateTime dataEHora = LocalDate.now().with(TemporalAdjusters.next(dia)).atTime(hora, minuto);

        this.idPaciente = idPaciente;
        this.especialidade = especialidadeMedico.toString();
        this.data = dataEHora.toString();

        return this;
    }


    public Consulta criarConsultaMedicoDefinido(String idPaciente, String idMedico) {

        LocalDateTime dataEHora = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.THURSDAY)).atTime(15, 0);

        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.data = dataEHora.toString();

        return this;


    }

    public Consulta criarConsultaMedicoDefinidoComDataHoraDia(String idPaciente, String idMedico, int hora, int minuto, DayOfWeek dia) {

        LocalDateTime dataEHora = LocalDate.now().with(TemporalAdjusters.next(dia)).atTime(hora, minuto);

        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.data = dataEHora.toString();

        return this;


    }

    public Consulta criarConsultaComMenosdeTrintaMinAntecedencia(String idPaciente) {

        LocalDateTime dataEHora = LocalDateTime.now().plusMinutes(20);

        this.idPaciente = idPaciente;
        this.especialidade = EspecialidadeMedico.ORTOPEDIA.toString();
        this.data = dataEHora.toString();

        return this;
    }

    public Consulta criarConsultaNoDomingo(String idPaciente) {

        LocalDateTime dataEHora = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).atTime(10, 0);

        this.idPaciente = idPaciente;
        this.especialidade = EspecialidadeMedico.GINECOLOGIA.toString();
        this.data = dataEHora.toString();

        return this;
    }

    public Consulta criarConsultaAntesDaAberturaDaClinica(String idPaciente, int hora, int minuto, DayOfWeek dia) {

        LocalDateTime dataEHora = LocalDate.now().with(TemporalAdjusters.next(dia)).atTime(hora, minuto);

        this.idPaciente = idPaciente;
        this.especialidade = EspecialidadeMedico.DERMATOLOGIA.toString();
        this.data = dataEHora.toString();

        return this;
    }

    public Consulta criarConsultaDepoisDoEncerramentoDaClinica(String idPaciente, int hora, int minuto, DayOfWeek dia) {

        LocalDateTime dataEHora = LocalDate.now().with(TemporalAdjusters.next(dia)).atTime(hora, minuto);

        this.idPaciente = idPaciente;
        this.especialidade = EspecialidadeMedico.DERMATOLOGIA.toString();
        this.data = dataEHora.toString();

        return this;
    }
    
}
