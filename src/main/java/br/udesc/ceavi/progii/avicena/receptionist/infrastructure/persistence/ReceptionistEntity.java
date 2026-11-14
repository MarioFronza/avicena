package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.control.dao.AuditedEntity;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.PersonEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "receptionists")
public class ReceptionistEntity extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receptionists_seq")
    @SequenceGenerator(name = "receptionists_seq", sequenceName = "receptionists_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    @Column(name = "work_hours", nullable = false)
    private int workHours;

    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

    @Column(name = "overtime_hours", nullable = false)
    private int overtimeHours;

    @Column(name = "labor_card_number", nullable = false)
    private String laborCardNumber;

    protected ReceptionistEntity() {}

    public ReceptionistEntity(
            Long id, PersonEntity person, int workHours, BigDecimal salary, int overtimeHours, String laborCardNumber) {
        this.id = id;
        this.person = person;
        this.workHours = workHours;
        this.salary = salary;
        this.overtimeHours = overtimeHours;
        this.laborCardNumber = laborCardNumber;
    }

    public void update(int workHours, BigDecimal salary, int overtimeHours, String laborCardNumber) {
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

    public BigDecimal getSalary() {
        return salary;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public String getLaborCardNumber() {
        return laborCardNumber;
    }
}
