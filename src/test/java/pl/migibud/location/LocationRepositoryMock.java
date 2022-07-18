package pl.migibud.location;

import java.util.List;
import java.util.Optional;

public class LocationRepositoryMock implements LocationRepository{
	@Override
	public Location save(Location location) {
		location.setId(1L);
		return location;
	}

	@Override
	public List<Location> findAll() {
		Location location1 = new Location(1L,"Wrocław","Dolny Śląsk","Polska",118,-45,null);
		Location location2 = new Location(2L,"Poznań","Wielkopolska","Polska",125,-38,null);
		return List.of(location1,location2);
	}

	@Override
	public Optional<Location> findById(Long id) {
		throw new UnsupportedOperationException();
	}
}
