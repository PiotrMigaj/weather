package pl.migibud.forecast;

import lombok.*;
import pl.migibud.location.Location;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "forecast")

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
class Forecast {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	double temperature;
	int pressure;
	int humidity;
	double windSpeed;
	int windDirection;
	@ManyToOne
	@JoinColumn(name = "location_id")
	Location location;
	Instant createDate;
	LocalDate forecastDate;

}
