package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "diagnostico_final")
public class FinalDiagnosisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "codigo")
    private Long id;

    @Column(name = "doenca")
    private String doenca;

    @Column(name = "remedios")
    private String remedios;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "exame")
    private String exame;

    protected FinalDiagnosisEntity() {}

    public FinalDiagnosisEntity(Long id, String doenca, String remedios, String descricao, String exame) {
        this.id = id;
        this.doenca = doenca;
        this.remedios = remedios;
        this.descricao = descricao;
        this.exame = exame;
    }

    public Long getId() {
        return id;
    }

    public String getDoenca() {
        return doenca;
    }

    public String getRemedios() {
        return remedios;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getExame() {
        return exame;
    }
}
