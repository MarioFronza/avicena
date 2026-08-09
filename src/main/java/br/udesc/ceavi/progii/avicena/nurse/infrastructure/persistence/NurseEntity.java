package br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "nurses")
public class NurseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    @Column(name = "formation", nullable = false)
    private String formation;

    @Column(name = "hours_completed", nullable = false)
    private int hoursCompleted;

    protected NurseEntity() {}

    public NurseEntity(Long id, PersonEntity person, String formation, int hoursCompleted) {
        this.id = id;
        this.person = person;
        this.formation = formation;
        this.hoursCompleted = hoursCompleted;
    }

    public Long getId() {
        return id;
    }

    public PersonEntity getPerson() {
        return person;
    }

    public String getName() {
        return person.getName();
    }

    public String getCpf() {
        return person.getCpf();
    }

    public String getPhone() {
        return person.getPhone();
    }

    public String getFormation() {
        return formation;
    }

    public int getHoursCompleted() {
        return hoursCompleted;
    }
}
