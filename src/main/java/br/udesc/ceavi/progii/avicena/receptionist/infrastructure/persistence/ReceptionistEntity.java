package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.persistence;

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
@Table(name = "receptionists")
public class ReceptionistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    @Column(name = "work_hours", nullable = false)
    private int workHours;

    @Column(name = "salary", nullable = false)
    private float salary;

    @Column(name = "overtime_hours", nullable = false)
    private int overtimeHours;

    @Column(name = "labor_card_number", nullable = false)
    private int laborCardNumber;

    protected ReceptionistEntity() {}

    public ReceptionistEntity(
            Long id, PersonEntity person, int workHours, float salary, int overtimeHours, int laborCardNumber) {
        this.id = id;
        this.person = person;
        this.workHours = workHours;
        this.salary = salary;
        this.overtimeHours = overtimeHours;
        this.laborCardNumber = laborCardNumber;
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

    public int getWorkHours() {
        return workHours;
    }

    public float getSalary() {
        return salary;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public int getLaborCardNumber() {
        return laborCardNumber;
    }
}
