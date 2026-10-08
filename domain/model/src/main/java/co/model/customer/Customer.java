package co.model.customer;

import lombok.Getter;

@Getter
public class Customer {

    private final Long id;
    private final String name;
    private final String email;

    private Customer(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static Customer create(String name, String email) {
        return new Customer(null, name, email);
    }

    public static Customer restore(Long id, String name, String email) {
        return new Customer(id, name, email);
    }
}
