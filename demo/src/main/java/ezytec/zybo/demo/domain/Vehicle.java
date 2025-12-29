package ezytec.zybo.demo.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "vehicles",
        uniqueConstraints = {@UniqueConstraint(name="uk_vehicles_plate", columnNames = "plate")})
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String plate;

    //Un usuario puede parquear muchos vehículos, pero un vehículo pertenece a un solo usuario.
    //Muchos a uno
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name="fk_vehicle_user"))
    //Por terminar
    private User user;

    @Version
    private Long version;

}

