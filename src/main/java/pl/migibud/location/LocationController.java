package pl.migibud.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class LocationController {

    private final ObjectMapper objectMapper;
    private final LocationService locationService;
    public String createLocation(String json){
        try {
            LocationDTO locationDTO = objectMapper.readValue(json, LocationDTO.class);
            System.out.println(locationDTO);
            System.out.println(locationDTO.getCity());
            Location location = locationService.create(locationDTO.getCity(),locationDTO.getRegion(),locationDTO.getCountry(), locationDTO.getLongitude(), locationDTO.getLatitude());


            return objectMapper.writeValueAsString(locationDTO);
        } catch (Exception e) {
            return String.format("{\"errorMessage\":\"%s\"}",e.getMessage());
        }
    }

}
