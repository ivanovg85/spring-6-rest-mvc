package art.cookedincode.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Georgi Ivanov
 */
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;
    private String name;

    @Version
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
