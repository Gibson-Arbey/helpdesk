package co.jpa.mapper;

import co.jpa.entity.CustomerJpaEntity;
import co.model.customer.Customer;

public class CustomerJpaMapper {

    public static Customer toDomain(CustomerJpaEntity customerJpaEntity) {
        if (customerJpaEntity == null) {
            return null;
        }
        return Customer.restore(
                customerJpaEntity.getId(),
                customerJpaEntity.getName(),
                customerJpaEntity.getEmail()
        );
    }

    public static CustomerJpaEntity toEntity(Customer customer) {
        if (customer == null) {
            return null;
        }
        return CustomerJpaEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
    }
}
