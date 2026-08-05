package br.udesc.ceavi.progii.avicena.receptionist.infrastructure.persistence;

import br.udesc.ceavi.progii.avicena.patient.domain.MaritalStatus;
import br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence.AddressEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "atendente")
public class ReceptionistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "codigo")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "telefone", nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "codigo_endereco")
    private AddressEntity address;

    @Column(name = "estado_civil")
    private MaritalStatus maritalStatus;

    @Column(name = "carga_horaria", nullable = false)
    private int workHours;

    @Column(name = "salario", nullable = false)
    private float salary;

    @Column(name = "hora_extra", nullable = false)
    private int overtimeHours;

    @Column(name = "numero_carteira_de_trabalho", nullable = false)
    private int laborCardNumber;

    protected ReceptionistEntity() {}

    public ReceptionistEntity(
            Long id,
            String name,
            String cpf,
            String phone,
            AddressEntity address,
            MaritalStatus maritalStatus,
            int workHours,
            float salary,
            int overtimeHours,
            int laborCardNumber) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.workHours = workHours;
        this.salary = salary;
        this.overtimeHours = overtimeHours;
        this.laborCardNumber = laborCardNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPhone() {
        return phone;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
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
