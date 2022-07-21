package pl.migibud.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LocationController {

    private final ObjectMapper objectMapper;
    private final LocationService locationService;

    public String createLocation(String json) {
        try {
            LocationDTO locationDTO = objectMapper.readValue(json, LocationDTO.class);
            Location location = locationService.create(locationDTO.getCity(), locationDTO.getRegion(), locationDTO.getCountry(), locationDTO.getLongitude(), locationDTO.getLatitude());
            LocationDTO response = LocationMapper.mapToLocationDTO(location);
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            return String.format("{\"errorMessage\":\"%s\"}", e.getMessage());
        }
    }

    public String getLocations() {
        try {
            List<Location> locations = locationService.getAll();
            List<LocationDTO> locationDTOS = locations.stream()
                    .map(LocationMapper::mapToLocationDTO)
                    .collect(Collectors.toList());
            return objectMapper.writeValueAsString(locationDTOS);
        } catch (Exception e) {
            return String.format("{\"errorMessage\":\"%s\"}", e.getMessage());
        }
    }
}
