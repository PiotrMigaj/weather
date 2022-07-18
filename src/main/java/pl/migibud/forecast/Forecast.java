package pl.migibud.forecast;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.migibud.location.Location;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "forecast")

@NoArgsConstructor
@Getter
@Setter
class Forecast {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	float temperature;
	int pressure;
	int humidity;
	int windSpeed;
	int windDirection;
	@ManyToOne
	@JoinColumn(name = "location_id")
	Location location;
	Instant createDate;
	Instant forecastDate;

}
