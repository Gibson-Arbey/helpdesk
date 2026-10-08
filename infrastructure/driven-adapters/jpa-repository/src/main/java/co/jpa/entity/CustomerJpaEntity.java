package co.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class CustomerJpaEntity {

    @Id
    @Column(name = "cust_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cust_name")
    private String name;

    @Column(name = "cust_email")
    private String email;
}
