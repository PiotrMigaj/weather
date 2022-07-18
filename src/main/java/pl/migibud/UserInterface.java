package pl.migibud;

import lombok.RequiredArgsConstructor;
import pl.migibud.forecast.ForecastController;
import pl.migibud.location.LocationController;

import java.util.InputMismatchException;
import java.util.Scanner;

@RequiredArgsConstructor
public class UserInterface {

    private final LocationController locationController;
    private final ForecastController forecastController;

    public void run() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Aplikacja jest uruchomiana\n");
        System.out.println("Witaj w aplikacji pogodowej!");
        while (true) {
            System.out.println("Dostępne akcje:");
            System.out.println("0 - zamknij aplikację");
            System.out.println("1 - dodaj nową lokalizację");
            System.out.println("2 - pobierz wszystkie lokalizacje");
            System.out.println("3 - pobierz pogodę dla wskazanej lokalizacji");
            int input = scanner.nextInt();
            switch (input) {
                case 0:
                    return;
                case 1:
                    this.createLocationEntry();
                    break;
                case 2:
                    this.getAllLocationsFromDB();
                    break;
                case 3:
                    this.getForecast();
                    break;
                default:
                    System.out.println("Nie ma takiej opcji. Wybierz jeszcze raz co chcesz zrobić");
                    break;
            }
        }
    }

    private void createLocationEntry() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj miasto:");
        String city = scanner.nextLine();
        System.out.println("Podaj region:");
        String region = scanner.nextLine();
        System.out.println("Podaj państwo:");
        String country = scanner.nextLine();
        System.out.println("Podaj długość geograficzną:");
        Integer longitude;
        while (true){
            try {
                longitude = scanner.nextInt();
                break;
            }catch (InputMismatchException e){
                scanner.nextLine();
                System.out.println("Długość geograficzna musi być wartością liczbową. Wprowadź jeszcze raz:");
            }
        }
        System.out.println("Podaj szerokość geograficzną:");
        Integer latitude = null;
        while (true){
            try {
                latitude = scanner.nextInt();
                break;
            }catch (InputMismatchException e){
                scanner.nextLine();
                System.out.println("Szerokość geograficzna musi być wartością liczbową. Wprowadź jeszcze raz:");
            }
        }
        String json = String.format("{\"city\":\"%s\",\"region\":\"%s\",\"country\":\"%s\",\"longitude\":%s,\"latitude\":%s}", city, region, country, longitude, latitude);
        String httpResponse = locationController.createLocation(json);
        System.out.println("Odpowiedź z serwera: " + httpResponse);
    }

    private void getAllLocationsFromDB(){
        String httpResponse = locationController.getLocations();
        System.out.println("Odpowiedź z serwera: " + httpResponse);
    }

    private void getForecast(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj id lokalizacji:");
        Long id = null;
        while (true){
            try {
                id = scanner.nextLong();
                scanner.nextLine();
                break;
            }catch (InputMismatchException e){
                scanner.nextLine();
                System.out.println("Id lokalizacji musi być wartością liczbową. Wprowadź jeszcze raz:");
            }
        }

        System.out.println("Podaj na ilę dni w przód wyświetlić pogodę:");
        Integer day = null;
        while (true){
            try {
                String dayString = scanner.nextLine();
                if (dayString.isBlank()){
                    break;
                }
                day = Integer.parseInt(dayString);
                break;
            }catch (NumberFormatException e){
                System.out.println("Liczba dni musi być wartością liczbową. Wprowadź jeszcze raz:");
            }
        }
        String httpResponse = forecastController.getForecast(id, day);
        System.out.println(httpResponse);
    }

}
