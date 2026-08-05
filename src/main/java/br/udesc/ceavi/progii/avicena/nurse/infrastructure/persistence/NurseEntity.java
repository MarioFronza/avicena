package br.udesc.ceavi.progii.avicena.nurse.infrastructure.persistence;

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
@Table(name = "enfermeiro")
public class NurseEntity {

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

    @Column(name = "formacao", nullable = false)
    private String formation;

    @Column(name = "hr_cursadas", nullable = false)
    private int hoursCompleted;

    protected NurseEntity() {}

    public NurseEntity(
            Long id,
            String name,
            String cpf,
            String phone,
            AddressEntity address,
            MaritalStatus maritalStatus,
            String formation,
            int hoursCompleted) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.address = address;
        this.maritalStatus = maritalStatus;
        this.formation = formation;
        this.hoursCompleted = hoursCompleted;
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

    public String getFormation() {
        return formation;
    }

    public int getHoursCompleted() {
        return hoursCompleted;
    }
}
