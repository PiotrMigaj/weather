package pl.migibud.forecast;

import pl.migibud.location.Location;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.*;

public class MainInstantTime {
	public static void main(String[] args) {

		Instant now = Instant.now();
		System.out.println(now);

		LocalDate localDate = LocalDate.ofInstant(now, ZoneId.systemDefault());
		System.out.println(localDate);


		LocalDate now1 = LocalDate.of(2022,7,22);
		System.out.println(now1);

		Instant instant = now1.atStartOfDay(ZoneId.systemDefault()).toInstant();
		System.out.println(instant);

		int i = now.compareTo(instant);
		System.out.println(i);

	}
}
