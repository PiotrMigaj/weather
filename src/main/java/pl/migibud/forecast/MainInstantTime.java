package pl.migibud.forecast;

import pl.migibud.location.Location;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.*;

public class MainInstantTime {
	public static void main(String[] args) {

		Integer day = 1;

		LocalDate localDate = LocalDate.now();
		int monthValue = localDate.getMonthValue();
		int year = localDate.getYear();
		int dayOfMonth = localDate.getDayOfMonth() + 1;
		System.out.println(dayOfMonth);

		LocalDate localDate1 = LocalDate.of(2022,07,21);
		System.out.println(localDate1);

		LocalTime localTime = LocalTime.of(12,0,0);
		System.out.println(localTime);

		LocalDateTime localDateTime = LocalDateTime.of(localDate1,localTime);
		System.out.println(localDateTime);

		Instant instant = localDateTime.toInstant(ZoneId.of("Europe/Warsaw").getRules().getOffset(localDateTime));
		System.out.println(instant);

		Instant now = Instant.now();
		System.out.println(now);

		Long aLong = Duration.between(instant,now).toHours();
		System.out.println(aLong);


	}
}
