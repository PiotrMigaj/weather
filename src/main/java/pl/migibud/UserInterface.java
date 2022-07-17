package pl.migibud;

import java.util.Scanner;

public class UserInterface {

    private static final Scanner SCANNER = new Scanner(System.in);

    public void run() {

        System.out.println("Aplikacja jest uruchomiana\n");

        while (true) {
            System.out.println("Witaj w aplikacji pogodowej!");
            System.out.println("Wybierz co chcesz zrobić:");
            System.out.println("0 - zamknij aplikację");

            String input = SCANNER.next();

            switch (input) {
                case "0":
                    return;
                default:
                    System.out.println("Nie ma takiej opcji. Wybierz jeszcze raz co chcesz zrobić");
                    break;
            }

        }

    }

}
