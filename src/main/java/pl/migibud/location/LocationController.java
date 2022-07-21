package pl.migibud.location;

import java.util.List;

public class LocationController {

    public List<Location> getAllLocations() {
        return List.of(
                new Location(1L),
                new Location(2L)
        );
    }
}
