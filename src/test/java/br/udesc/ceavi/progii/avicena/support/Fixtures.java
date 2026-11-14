package br.udesc.ceavi.progii.avicena.support;

import br.udesc.ceavi.progii.avicena.shared.domain.Address;

public final class Fixtures {

    private Fixtures() {}

    public static Address address() {
        return new Address(100, "Apt 2", "88000000", "Main St", "Downtown", "Florianopolis");
    }
}
