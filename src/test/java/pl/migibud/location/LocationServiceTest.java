package pl.migibud.location;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class LocationServiceTest {

	LocationService locationService;

	@BeforeEach
	void setUp(){
		LocationRepository locationRepository = new LocationRepositoryMock();
		locationService = new LocationService(locationRepository);
	}

	@Test
	void createLocation_whenAllDataIsCorrect_returnsCorrectLocationObject(){
		//when
		Location location = locationService.create("Wrocław", "Dolny Śląsk", "Polska", 45, 50);
		//then
		assertThat(location.getId()).isNotNull();
		assertThat(location.getCity()).isEqualTo("Wrocław");
		assertThat(location.getRegion()).isEqualTo("Dolny Śląsk");
		assertThat(location.getCountry()).isEqualTo("Polska");
		assertThat(location.getLongitude()).isEqualTo(45);
		assertThat(location.getLatitude()).isEqualTo(50);

	}

	@Test
	void createLocation_whenAllDataIsCorrectExceptRegionWhichIsBlank_returnsCorrectLocationObjectWithNullRegion(){
		//when
		Location location = locationService.create("Wrocław", "", "Polska", 45, 50);
		//then
		assertThat(location.getId()).isNotNull();
		assertThat(location.getCity()).isEqualTo("Wrocław");
		assertThat(location.getRegion()).isNull();
		assertThat(location.getCountry()).isEqualTo("Polska");
		assertThat(location.getLongitude()).isEqualTo(45);
		assertThat(location.getLatitude()).isEqualTo(50);
	}

	@Test
	void createLocation_whenCityIsNull_throwsAnException(){
		//when
		Throwable throwable = catchThrowable(()->locationService.create(null, "", "Polska", -180, -90));
		//then
		assertThat(throwable).isNotNull();
		assertThat(throwable.getMessage()).contains("city");
		assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void createLocation_whenCountryIsNull_throwsAnException(){
		//when
		Throwable throwable = catchThrowable(()->locationService.create("Wrocław", "", null, 180, 90));
		//then
		assertThat(throwable).isNotNull();
		assertThat(throwable.getMessage()).contains("country");
		assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void createLocation_whenLongitudeExceedMaxRange_throwsAnException(){
		//when
		Throwable throwable = catchThrowable(()->locationService.create("Wrocław", "", "Polsa", 181, 90));
		//then
		assertThat(throwable).isNotNull();
		assertThat(throwable.getMessage()).contains("Długość");
		assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void createLocation_whenLongitudeExceedMinRange_throwsAnException(){
		//when
		Throwable throwable = catchThrowable(()->locationService.create("Wrocław", "", "Polsa", -181, -91));
		//then
		assertThat(throwable).isNotNull();
		assertThat(throwable.getMessage()).contains("Długość");
		assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void createLocation_whenLatitudeExceedMaxRange_throwsAnException(){
		//when
		Throwable throwable = catchThrowable(()->locationService.create("Wrocław", "", "Polsa", 180, 91));
		//then
		assertThat(throwable).isNotNull();
		assertThat(throwable.getMessage()).contains("Szerokość");
		assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void createLocation_whenLatitudeExceedMinRange_throwsAnException(){
		//when
		Throwable throwable = catchThrowable(()->locationService.create("Wrocław", "", "Polsa", -180, -91));
		//then
		assertThat(throwable).isNotNull();
		assertThat(throwable.getMessage()).contains("Szerokość");
		assertThat(throwable).isExactlyInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void shouldReturnListOfLocations(){
		//when
		List<Location> locations = locationService.getAll();
		//then
		assertThat(locations).isNotNull();
		assertThat(locations).hasSizeGreaterThan(0);
	}



}