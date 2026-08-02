package br.udesc.ceavi.progii.avicena.patient.domain;

import java.util.Objects;

public class Address {

    private final int number;
    private final String complement;
    private final String zipCode;
    private final String street;
    private final String neighborhood;
    private final String city;

    public Address(int number, String complement, String zipCode, String street, String neighborhood, String city) {
        this.number = number;
        this.complement = complement;
        this.zipCode = zipCode;
        this.street = street;
        this.neighborhood = neighborhood;
        this.city = city;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Address)) {
            return false;
        }
        Address other = (Address) obj;
        return number == other.number
                && Objects.equals(complement, other.complement)
                && Objects.equals(zipCode, other.zipCode)
                && Objects.equals(street, other.street)
                && Objects.equals(neighborhood, other.neighborhood)
                && Objects.equals(city, other.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, complement, zipCode, street, neighborhood, city);
    }
}
