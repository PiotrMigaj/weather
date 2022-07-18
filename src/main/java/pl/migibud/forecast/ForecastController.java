package pl.migibud.forecast;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ForecastController {

	private final ForecastService forecastService;


	public String getForecast(Long id,Integer day){

		forecastService.getForecast(id,day);

		return "Twoja pogoda: "+id+" "+day;
	}
}
