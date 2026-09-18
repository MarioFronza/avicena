package br.udesc.ceavi.progii.avicena.appointment.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "urgency_statuses")
public class UrgencyStatusEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "label", nullable = false)
    private String label;

    protected UrgencyStatusEntity() {}

    public UrgencyStatusEntity(Long id, String code, String label) {
        this.id = id;
        this.code = code;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}
