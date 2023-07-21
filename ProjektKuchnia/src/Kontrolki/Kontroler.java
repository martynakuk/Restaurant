package Kontrolki;


import Interfaces.IKontrolerRealizacja;
import Models.Restauracja;
import Narzedzia.Sprawdzanie;

import java.util.Scanner;

public class Kontroler {
    private IKontrolerRealizacja uzytkownikInterface;
    private Sprawdzanie blad = new Sprawdzanie();
    private Restauracja restauracja;

    public Kontroler() {
        this.uzytkownikInterface = new IKontrolerRealizacja();
        this.restauracja = new Restauracja();
        uzytkownikInterface.wprowadzenieDoProgramu();
        wyborUzytkownika();
    }

    public void wyborUzytkownika() {
        Scanner skaner = new Scanner(System.in);
        String wybor = "";

        while (!wybor.equals("b")) {
            wybor = skaner.next();
            switch (wybor) {
                case "b":
                    System.out.println("Dziekujemy za wspolprace!");
                    break;
                case "h":
                    blad.pomoc();
                    System.out.println("wyjscie -b");
                    String back = "";
                    do {
                        back = skaner.next();
                        if (back.equals("b")) {
                            uzytkownikInterface.wprowadzenieDoProgramu();
                        } else {
                            System.out.println("Wpisz b aby wrocic");
                        }
                    } while (!back.equals("b"));

                    break;
                case "1":
                    blad.wyswietlZamowienieMenu();
                    String wyborTypuZamowenia = skaner.next();
                    if (restauracja.isOpen() && (wyborTypuZamowenia.equals("1") || wyborTypuZamowenia.equals("2"))) {
                        uzytkownikInterface.opcjeZamowienia(this.restauracja, Integer.parseInt(wyborTypuZamowenia));
                    } else if (restauracja.isOpen() && wyborTypuZamowenia.equals("3")) {
                        uzytkownikInterface.losoweZamowienie(this.restauracja);
                    } else if (restauracja.isOpen() && wyborTypuZamowenia.equals("h")) {
                        blad.pomoc();
                        System.out.println();
                        uzytkownikInterface.wprowadzenieDoProgramu();
                    } else if (restauracja.isOpen()) {
                        System.out.println("Prosimy o poprawne wpisanie wyboru");
                    } else {
                        System.out.println("Restauracja jest zamknieta");
                        uzytkownikInterface.wprowadzenieDoProgramu();
                    }
                    break;
                case "2":
                    uzytkownikInterface.administracja(this.restauracja);
                    break;
                default:
                    System.out.println("Prosimy o poprawne wpisanie nr. wyboru");
            }
        }
    }
}
