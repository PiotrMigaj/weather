package pl.migibud.forecast;

public class ForecastController {


	public String getForecast(Long id,Integer day){



		return "Twoja pogoda: "+id+" "+day;
	}
}
