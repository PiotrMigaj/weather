package pl.migibud.location;

class LocationMapperUtil { // todo rename to LocationMapper

    static LocationDTO mapToLocationDTO(Location location) { // mapToLocationDTO asLocationDTO
        return LocationDTO.builder()
                .id(location.getId())
                .city(location.getCity())
                .region(location.getRegion())
                .country(location.getCountry())
                .longitude(location.getLongitude())
                .latitude(location.getLatitude())
                .build();
    }
}
