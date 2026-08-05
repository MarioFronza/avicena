package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "diagnostico_primario")
public class DiagnosisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "codigo")
    private Long id;

    @Column(name = "pressao")
    private float pressao;

    @Column(name = "temperatura")
    private int temperatura;

    @Column(name = "altura")
    private int altura;

    @Column(name = "peso")
    private int peso;

    @Column(name = "historico_remedio")
    private String historicoRemedio;

    @ManyToOne
    @JoinColumn(name = "id_consulta")
    private AppointmentEntity appointment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_diagnostico_final")
    private FinalDiagnosisEntity finalDiagnosis;

    protected DiagnosisEntity() {}

    public DiagnosisEntity(
            Long id,
            float pressao,
            int temperatura,
            int altura,
            int peso,
            String historicoRemedio,
            AppointmentEntity appointment,
            FinalDiagnosisEntity finalDiagnosis) {
        this.id = id;
        this.pressao = pressao;
        this.temperatura = temperatura;
        this.altura = altura;
        this.peso = peso;
        this.historicoRemedio = historicoRemedio;
        this.appointment = appointment;
        this.finalDiagnosis = finalDiagnosis;
    }

    public Long getId() {
        return id;
    }

    public float getPressao() {
        return pressao;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public int getAltura() {
        return altura;
    }

    public int getPeso() {
        return peso;
    }

    public String getHistoricoRemedio() {
        return historicoRemedio;
    }

    public AppointmentEntity getAppointment() {
        return appointment;
    }

    public FinalDiagnosisEntity getFinalDiagnosis() {
        return finalDiagnosis;
    }
}
