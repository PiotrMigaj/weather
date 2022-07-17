package pl.migibud.location;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "location")

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String city;
    String region;
    String country;
    Integer longitude;
    Integer latitude;
    Instant createdDate;
}
