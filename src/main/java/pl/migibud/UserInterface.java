package pl.migibud;

import lombok.RequiredArgsConstructor;
import pl.migibud.location.LocationController;

import java.util.Scanner;

@RequiredArgsConstructor
public class UserInterface {

    private final LocationController locationController;

    public void run() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Aplikacja jest uruchomiana\n");
        System.out.println("Witaj w aplikacji pogodowej!");
        while (true) {
            System.out.println("Dostępne akcje:");
            System.out.println("0 - zamknij aplikację");
            System.out.println("1 - dodaj nową lokalizację");
            int input = scanner.nextInt();
            switch (input) {
                case 0:
                    return;
                case 1:
                    this.createLocationEntry();
                    break;
                default:
                    System.out.println("Nie ma takiej opcji. Wybierz jeszcze raz co chcesz zrobić");
                    break;
            }
        }
    }

    public void createLocationEntry() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj miasto:");
        String city = scanner.nextLine();
        System.out.println("Podaj region:");
        String region = scanner.nextLine();
        System.out.println("Podaj państwo:");
        String country = scanner.nextLine();
        System.out.println("Podaj długość geograficzną:");
        Integer longitude = scanner.nextInt();
        System.out.println("Podaj szerokość geograficzną:");
        Integer latitude = scanner.nextInt();
        String json = String.format("{\"city\":\"%s\",\"region\":\"%s\",\"country\":\"%s\",\"longitude\":%s,\"latitude\":%s}", city, region, country, longitude, latitude);
        System.out.println(json);
        String httpResponse = locationController.createLocation(json);
        System.out.println("Odpowiedź z serwera: " + httpResponse);
    }
}
