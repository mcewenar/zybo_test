package ezytec.zybo.demo.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "stay")
public class Stay {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Vehicle vehicle;

    @Column(name="entry_time", nullable = false)
    private LocalDateTime entryTime;

    @Column(name="exit_time")
    private LocalDateTime exitTime;

    @Column(name="minutes_total")
    private Integer minutesTotal;

    @Column(name="charged_value")
    private Integer chargedValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StayStatus status;
}

