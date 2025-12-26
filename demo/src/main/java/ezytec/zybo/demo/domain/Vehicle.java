package ezytec.zybo.demo.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String plate;

    //Un usuario puede parquear muchos vehículos, pero un vehículo pertenece a un solo usuario.
    //Muchos a uno
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name="vehic-user"))
    //Por terminar
    private User user;

}

