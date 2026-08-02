package br.udesc.ceavi.progii.avicena.patient.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "endereco")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "codigo_endereco")
    private Long id;

    @Column(name = "numero")
    private int number;

    @Column(name = "complento")
    private String complement;

    @Column(name = "cep")
    private String zipCode;

    @Column(name = "rua")
    private String street;

    @Column(name = "bairro")
    private String neighborhood;

    @Column(name = "cidade")
    private String city;

    protected AddressEntity() {}

    public AddressEntity(
            Long id, int number, String complement, String zipCode, String street, String neighborhood, String city) {
        this.id = id;
        this.number = number;
        this.complement = complement;
        this.zipCode = zipCode;
        this.street = street;
        this.neighborhood = neighborhood;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getStreet() {
        return street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }
}
