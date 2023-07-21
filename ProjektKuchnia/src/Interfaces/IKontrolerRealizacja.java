package Interfaces;

import Models.*;
import Models.Zamowienia.NieStacjonarneZamowienie;
import Models.Zamowienia.StacjonarneZamowienie;
import Narzedzia.Sprawdzanie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class IKontrolerRealizacja implements IKontroler {
    private Sprawdzanie blad = new Sprawdzanie();
    private IRestaurcjaRealizacja restauracjaInterface = new IRestaurcjaRealizacja();

    @Override
    public void wprowadzenieDoProgramu() {
        System.out.println("Witamy w restauracji!");
        System.out.println(" -> Aby uzyc interfejsu, wpisz numer / litere jej odpowiadajaca i zatwierdz przyciskiem ENTER\n");
        System.out.println("1. Zloz zamowienie");
        System.out.println("2. Administracja\n");
        System.out.println("wyjscie -b; pomoc -h");
    }

    @Override
    public void opcjeZamowienia(Restauracja restauracja, int wybor) {
        ArrayList<Danie> order = new ArrayList<>();
        Scanner skaner = new Scanner(System.in);
        Sprawdzanie blad = new Sprawdzanie();
        String something = "";

        do {
            System.out.println();
            System.out.println("Wybierz danie z menu:");
            blad.pokazanieMenu(restauracja.getMenu());
            blad.wyswietlDolneZamowenieMenu();

            String input = skaner.next();

            if (blad.sprawdzaCzyNumer(input)) {
                something = "number";
            } else {
                something = input;
            }

            switch (something) {
                case "p":
                    blad.pokazZamowienie(order);
                    break;
                case "b":
                    break;
                case "c":
                    if (order.isEmpty()) {
                        System.out.println("Aby zlozyc zamowienie, nalezy dodac przynajmniej jedna pozycje");
                        something = "z";
                    } else {
                        if (wybor == 1) {
                            StacjonarneZamowienie zamustaczytkownika = new StacjonarneZamowienie(order, blad.przyznaczNrDoZamowienia(restauracja.getZamowienia()), blad.przyznaczStacjonarneZamowienieDoKelnera(restauracja.getZatrudnieniPracownicy()));
                            restauracja.getZamowienia().add(zamustaczytkownika);
                            System.out.println("Twoj nr. zamowienia to: " + zamustaczytkownika.getNr());
                        } else {
                            NieStacjonarneZamowienie zamuniestacuzytkownika = new NieStacjonarneZamowienie(order, blad.przyznaczNrDoZamowienia(restauracja.getZamowienia()), blad.przyznaczNiestacjonarneZamowienieDoDostawcy(restauracja.getZatrudnieniPracownicy()));
                            restauracja.getZamowienia().add(zamuniestacuzytkownika);
                            System.out.println("Twoj nr. zamowienia to: " + zamuniestacuzytkownika.getNr());
                        }
                    }
                    System.out.println();
                    break;
                case "number":
                    if (blad.sprawdzaCzyJestWMenu(restauracja.getMenu().getMenu(), Integer.parseInt(input))) {
                        List<Danie> lista = restauracja.getMenu().getMenu();

                        int id = Integer.parseInt(input);
                        List<Danie> wybraneDania = lista.stream()
                                .filter(x -> x.getId() == id)
                                .collect(Collectors.toList());
                        order.add(wybraneDania.get(0));

                        System.out.println("Danie: " + wybraneDania.get(0).getNazwa() + " zostało pomyślnie dodane do twojego zamówienia");
                    }
                    break;
                default:
                    System.out.println("Prosze wprowadzic poprawny numer lub litere");
            }
        } while (!(something.equals("b") || something.equals("c")));
        wprowadzenieDoProgramu();
    }

    @Override
    public void losoweZamowienie(Restauracja restauracja) {
        ArrayList<Danie> zamowienie = new ArrayList<>();

        int wybor = (int) (1 + (Math.random() * 2));
        int iloscPozycji = (int) (1 + (Math.random() * 15));
        // Numer 15, gdyz jest to najbardziej maksymalna realistyczna liczba zamowionych dan

        ArrayList<Danie> daniaRestauracji = restauracja.getMenu().getMenu();

        for (int i = 0; i < iloscPozycji; i++) {
            int pozycja = (int) (Math.random() * restauracja.getMenu().getMenu().size());
            if (blad.sprawdzaCzyJestWMenu(restauracja.getMenu().getMenu(), pozycja)) {
                List<Danie> wybraneDania = daniaRestauracji.stream()
                        .filter(x -> x.getId() == pozycja)
                        .collect(Collectors.toList());
                zamowienie.add(wybraneDania.get(0));
            }
        }
        System.out.println();

        if (wybor == 1) {
            StacjonarneZamowienie stacjzam = new StacjonarneZamowienie(zamowienie, blad.przyznaczNrDoZamowienia(restauracja.getZamowienia()), blad.przyznaczStacjonarneZamowienieDoKelnera(restauracja.getZatrudnieniPracownicy()));
            restauracja.getZamowienia().add(stacjzam);
            System.out.println("Losowe zamowienie, stacjonarne, zostalo utworzone, nr: " + stacjzam.getNr());
            blad.pokazZamowienie(stacjzam.getTwojeZamowienie());
        } else {
            NieStacjonarneZamowienie niestacjzam = new NieStacjonarneZamowienie(zamowienie, blad.przyznaczNrDoZamowienia(restauracja.getZamowienia()), blad.przyznaczNiestacjonarneZamowienieDoDostawcy(restauracja.getZatrudnieniPracownicy()));
            restauracja.getZamowienia().add(niestacjzam);
            System.out.println("Losowe zamowienie, niestacjonarne, zostalo utworzone, nr: " + niestacjzam.getNr());
            blad.pokazZamowienie(niestacjzam.getTwojeZamowienie());
        }
        System.out.println();
        wprowadzenieDoProgramu();
    }

    @Override
    public void administracja(Restauracja restauracja) {
        Scanner skaner = new Scanner(System.in);

        String input = "0";

        do {
            System.out.println("1. Otworz restauracje");
            System.out.println("2. Zamknij / zastopuj restauracje");
            System.out.println("3. Zarzadzaj zamowieniami");
            System.out.println("4. Zarzadzaj pracownikami");
            System.out.println("5. Zarzadzaj menu");
            System.out.println("6. Pokaz utarg");
            System.out.println("powrot -b");

            input = skaner.next();

            switch (input) {
                case "1":
                    this.restauracjaInterface.otworzRestauracje(restauracja);
                    break;
                case "2":
                    this.restauracjaInterface.zamknijRestauracje(restauracja);
                    break;
                case "3":
                    blad.wyswietlZarzadzanieZamowieniami();
                    String wybor = skaner.next();
                    if (wybor.equals("1")) {
                        this.restauracjaInterface.pokazZamowieniaCzekajaceNaRealizacje(restauracja);
                    } else if (wybor.equals("2")) {
                        this.restauracjaInterface.wyswietlZrealizowaneZamowienia(restauracja);
                    } else if (wybor.equals("b")) {
                    } else {
                        System.out.println("Prosimy o poprawne wpisanie wyboru");
                    }
                    break;
                case "4":
                    blad.wyswietlZarzadzaniePracownikami();
                    wybor = skaner.next();

                    if (wybor.equals("1")) {
                        blad.wyswietlTypyPraciwnikow();
                        String wybor1 = skaner.next();
                        if (wybor1.equals("1")) {
                            this.restauracjaInterface.dodajKucharza(restauracja);
                        } else if (wybor1.equals("2")) {
                            this.restauracjaInterface.dodajKelnera(restauracja);
                        } else if (wybor1.equals("3")) {
                            this.restauracjaInterface.dodajDostawce(restauracja);
                        } else if (wybor1.equals("b")) {

                        } else {
                            System.out.println("Prosze wpisac poprawny numer");
                        }
                    } else if (wybor.equals("2")) {
                        blad.wyswietlTypyPraciwnikow();
                        String wybor1 = skaner.next();
                        if (wybor1.equals("1")) {
                            this.restauracjaInterface.usunKucharza(restauracja);
                        } else if (wybor1.equals("2")) {
                            this.restauracjaInterface.usunKelnera(restauracja);
                        } else if (wybor1.equals("3")) {
                            this.restauracjaInterface.usunDostawce(restauracja);
                        } else if (wybor1.equals("b")) {

                        } else {
                            System.out.println("Prosze wpisac poprawny numer");
                        }
                    } else if (wybor.equals("3")) {
                        this.restauracjaInterface.wyswietlWszystkichPracownikow(restauracja);
                    } else if (wybor.equals("4")) {
                        this.restauracjaInterface.wyswietlWybranegoPracownika(restauracja);
                    } else if (wybor.equals("b")) {
                    } else {
                        System.out.println("Prosimy o poprawne wpisanie wyboru");
                    }
                    break;
                case "5":
                    blad.wyswietlZarzadzanieMenu();
                    wybor = skaner.next();
                    if (wybor.equals("1")) {
                        blad.pokazanieMenu(restauracja.getMenu());
                    } else if (wybor.equals("2")) {
                        this.restauracjaInterface.dodajDanieDoMenu(restauracja.getMenu());
                    } else if (wybor.equals("3")) {
                        this.restauracjaInterface.usunDanie(restauracja.getMenu());
                    } else if (wybor.equals("4")) {
                        this.restauracjaInterface.oznaczDanieJakoNieDostepne(restauracja.getMenu());
                    } else if (wybor.equals("5")) {
                        this.restauracjaInterface.oznaczDanieJakoDostepne(restauracja.getMenu());
                    } else if (wybor.equals("b")) {
                    } else {
                        System.out.println("Prosimy o poprawne wpisanie wyboru");
                    }
                    break;
                case "6":
                    this.restauracjaInterface.aktualnyUtarg(restauracja); //not counting, need to have a setZamovenie as done, where it's called and sets the utarg to correct value
                    break;
                case "b":
                    break;
                default:
                    System.out.println("Prosimy o poprawne wpisanie wyboru");
            }
            System.out.println();
        } while (!input.equals("b"));
        wprowadzenieDoProgramu();
    }
}




