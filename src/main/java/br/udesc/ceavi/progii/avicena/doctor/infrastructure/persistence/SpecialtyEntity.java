package br.udesc.ceavi.progii.avicena.doctor.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.AuditedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "specialties")
public class SpecialtyEntity extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "specialties_seq")
    @SequenceGenerator(name = "specialties_seq", sequenceName = "specialties_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    protected SpecialtyEntity() {}

    public SpecialtyEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
