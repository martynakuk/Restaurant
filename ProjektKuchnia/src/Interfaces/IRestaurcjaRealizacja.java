package Interfaces;

import KlasyAbstrakcyjne.Pracownik;
import KlasyAbstrakcyjne.Zamowienie;
import Models.Danie;
import Models.Menu;
import Models.Pracownicy.Dostawca;
import Models.Pracownicy.Kelner;
import Models.Pracownicy.Kucharz;
import Models.Restauracja;
import Narzedzia.BrakPracownikow;
import Narzedzia.Sprawdzanie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class IRestaurcjaRealizacja implements IRestauracja{
    Sprawdzanie blad = new Sprawdzanie();

    @Override
    public void dodajDanieDoMenu(Menu menu) {
        menu.getMenu().add(stworzDanie(blad.przyznaczIdDoDania(menu.getMenu())));
        menu.zapiszDoPliku();
        System.out.println("Danie zostalo pomyslnie dodane do menu");
    }

    public Danie stworzDanie(int id){
        Scanner skaner = new Scanner(System.in);

        System.out.println("Prosze podac nazwe dania");
        String nazwa = skaner.nextLine();
        if (!blad.sprawdzCzyTowyraz(nazwa)){
            while (!blad.sprawdzCzyTowyraz(nazwa)){
                System.out.println("Nie jest to wyraz, prosze wprowadzic jeszcze raz");
                nazwa = skaner.nextLine();
            }
        }
        System.out.println("Prosze podac opis");
        String opis = skaner.nextLine();

        System.out.println("Prosze podac cene dania");
        double cena = 0;
        do {
            String cenawprowadzona = skaner.next();
            try {
                cena = Double.parseDouble(cenawprowadzona);
            } catch (NumberFormatException n) {
                System.out.println("Nie jest to liczba, prosze wprowadzic jeszcze raz");

            }
        } while (cena == 0);

        System.out.println("Prosze podac czas wykonania dania (w sekundach)");
        int czas = 0;
        do {
            String czaswprowadzony = skaner.next();
            try {
                czas = Integer.parseInt(czaswprowadzony);
            } catch (NumberFormatException n) {
                System.out.println("Nie jest to liczba, prosze wprowadzic jeszcze raz");
            }
        } while (czas == 0);

        return new Danie(id,nazwa,cena,opis,czas);
    }

    @Override
    public void usunDanie(Menu menu) {
        Scanner skaner = new Scanner(System.in);
        String nr = "";
        //Sprawdzanie czy item istnieje oraz czy jest int
        do {
            System.out.println("Wybierz danie do usuniecia");
            blad.pokazanieMenu(menu);
            System.out.println("powrot -b");
            nr = skaner.next();


            try {
                int number = Integer.parseInt(nr);
                if (blad.sprawdzaCzyJestWMenu(menu.getMenu(), number)) {
                    System.out.println("Czy napewno chcesz usunac podane danie? [t - tak, n - nie]");
                    String decyzja = skaner.next();

                    if (decyzja.equals("t")) {
                        ArrayList<Danie> noweMenu = (ArrayList<Danie>) menu.getMenu().stream()
                                .filter(x -> x.getId() != number)
                                .collect(Collectors.toList());
                        menu.setMenu(noweMenu);
                        menu.zapiszDoPliku();
                        System.out.println("Danie zostalo pomyslnie usuniete");
                    } else if (!decyzja.equals("n")){
                        System.out.println("Wprowadz poprawna litere");
                    } else {
                        System.out.println("Usuwanie dania zostalo anulowane");
                    }
                }
            } catch (NumberFormatException n){
            }
        } while (!nr.equals("b"));
    }

    @Override
    public void otworzRestauracje(Restauracja restauracja) {
        if (restauracja.isOpen()){
            System.out.println("Restauracja jest juz otworzona");
        } else {
            restauracja.setOpen(true);
            System.out.println("Restauracja zostala otwarta");
        }
    }

    @Override
    public void zamknijRestauracje(Restauracja restauracja) {
        if (!restauracja.isOpen()){
            System.out.println("Restauracja jest juz zamknieta");
        } else {
            restauracja.setOpen(false);
            restauracja.setZamowienia(new ArrayList<Zamowienie>());
            System.out.println("Restauracja zostala zamknieta");
        }
    }

    @Override
    public void oznaczDanieJakoNieDostepne(Menu menu) {
        Scanner skaner = new Scanner(System.in);
        String numerkonca = "";

        while (!numerkonca.equals("k")){
            blad.pokazanieMenu(menu);
            System.out.println("Aby wyjsc wpisz k");
            numerkonca = skaner.next();

            if (blad.sprawdzaCzyNumer(numerkonca)) {
                int nr = Integer.parseInt(numerkonca);
                if (blad.sprawdzaCzyJestWMenu(menu.getMenu(), nr)) {
                    for (Danie danie : menu.getMenu()) {
                        if (danie.getId() == nr) {
                            danie.setAvailable(false);
                            System.out.println("Danie zostalo pomyslnie oznaczone jako niedostepne");
                        }
                    }
                    numerkonca = "k";

                } else {
                    System.out.println("Podany numer nie jest w menu");
                }
            } else if (numerkonca.equals("k")){

            } else {
                System.out.println("Nie jest to numer");
            }
        }
        menu.zapiszDoPliku();
    }

    @Override
    public void oznaczDanieJakoDostepne(Menu menu) {
        Scanner skaner = new Scanner(System.in);
        String numerkonca = "";

        while (!numerkonca.equals("k")){
            blad.pokazanieMenu(menu);
            System.out.println("Aby wyjsc wpisz k");
            numerkonca = skaner.next();

            if (blad.sprawdzaCzyNumer(numerkonca)) {
                int nr = Integer.parseInt(numerkonca);
                if (blad.sprawdzaCzyJestWMenu(menu.getMenu(), nr)) {
                    for (Danie danie : menu.getMenu()) {
                        if (danie.getId() == nr) {
                            danie.setAvailable(true);
                            System.out.println("Danie zostalo pomyslnie oznaczone jako dostepne");
                        }
                    }
                    numerkonca = "k";

                } else {
                    System.out.println("Podany numer nie jest w menu");
                }
            } else if (numerkonca.equals("k")){

            } else {
                System.out.println("Nie jest to numer");
            }
        }
        menu.zapiszDoPliku();
    }

    @Override
    public void pokazZamowieniaCzekajaceNaRealizacje(Restauracja restauracja) {
        System.out.println("Lista oczekujacych zamowien:");
        restauracja.getZamowienia().stream()
                .filter(x -> !x.isZrealizowane())
                .forEach(x -> System.out.println(
                                "id: " + x.getNr() +
                                "; Utworzony: " + x.getCzasUtworzenia() +
                                "; Id Pracownika: " + x.getNrPracownika()));
    }

    @Override
    public void aktualnyUtarg(Restauracja restauracja) {
        System.out.println("Aktualny utarg: " + restauracja.getUtarg() + "zl");
    }

    @Override
    public void dodajKucharza(Restauracja restauracja) {
        restauracja.getZatrudnieniPracownicy().add(stworzkucharza(blad.przyznaczIdDoPracownika(restauracja.getZatrudnieniPracownicy())));
        System.out.println("Kucharz zostal pomyslnie dodany");
    }

    @Override
    public void dodajKelnera(Restauracja restauracja) {
        restauracja.getZatrudnieniPracownicy().add(stworzkelnera(blad.przyznaczIdDoPracownika(restauracja.getZatrudnieniPracownicy())));
        System.out.println("Kelner zostal pomyslnie dodany");
    }

    @Override
    public void dodajDostawce(Restauracja restauracja) {
        restauracja.getZatrudnieniPracownicy().add(stworzdostawce(blad.przyznaczIdDoPracownika(restauracja.getZatrudnieniPracownicy())));
        System.out.println("Dostawca zostal pomyslnie dodany");
    }

    @Override
    public Pracownik stworzkucharza(int id) {
        Scanner skaner = new Scanner(System.in);
        System.out.println("Prosze podac imie kucharza");
        String nazwa = skaner.nextLine();
        if (!blad.sprawdzCzyTowyraz(nazwa)){
            while (!blad.sprawdzCzyTowyraz(nazwa)){
                System.out.println("Nie jest to wyraz, prosze wprowadzic jeszcze raz");
                nazwa = skaner.nextLine();
            }
        }

        System.out.println("Prosze podac nazwisko kucharza");
        String nazwisko = skaner.nextLine();
        if (!blad.sprawdzCzyTowyraz(nazwisko)){
            while (!blad.sprawdzCzyTowyraz(nazwisko)){
                System.out.println("Nie jest to wyraz, prosze wprowadzic jeszcze raz");
                nazwisko = skaner.nextLine();
            }
        }

        System.out.println("Prosze podac nr. telefonu kucharza (Przyklad: 123-123-123)");
        String nrtelefonu = skaner.next();
        if (!blad.sprawdzaCzyToNrTelefonu(nrtelefonu)){
            while (!blad.sprawdzaCzyToNrTelefonu(nrtelefonu)){
                System.out.println("Nie jest to nr. telefonu, prosze wprowadzic jeszcze raz");
                nrtelefonu = skaner.next();
            }
        }

        return new Kucharz(id,nazwa,nazwisko,nrtelefonu);
    }

    @Override
    public Pracownik stworzkelnera(int id) {
        Scanner skaner = new Scanner(System.in);
        System.out.println("Prosze podac imie kelnera");
        String nazwa = skaner.nextLine();
        if (!blad.sprawdzCzyTowyraz(nazwa)){
            while (!blad.sprawdzCzyTowyraz(nazwa)){
                System.out.println("Nie jest to wyraz, prosze wprowadzic jeszcze raz");
                nazwa = skaner.nextLine();
            }
        }

        System.out.println("Prosze podac nazwisko kelnera");
        String nazwisko = skaner.nextLine();
        if (!blad.sprawdzCzyTowyraz(nazwisko)){
            while (!blad.sprawdzCzyTowyraz(nazwisko)){
                System.out.println("Nie jest to wyraz, prosze wprowadzic jeszcze raz");
                nazwisko = skaner.nextLine();
            }
        }

        System.out.println("Prosze podac nr. telefonu kelnera (Przyklad: 123-123-123)");
        String nrtelefonu = skaner.nextLine();
        if (!blad.sprawdzaCzyToNrTelefonu(nrtelefonu)){
            while (!blad.sprawdzaCzyToNrTelefonu(nrtelefonu)){
                System.out.println("Nie jest to nr. telefonu, prosze wprowadzic jeszcze raz");
                nrtelefonu = skaner.nextLine();
            }
        }

        return new Kelner(id,nazwa,nazwisko, nrtelefonu);
    }

    @Override
    public Pracownik stworzdostawce(int id) {
        Scanner skaner = new Scanner(System.in);
        System.out.println("Prosze podac imie dostawcy");
        String nazwa = skaner.nextLine();
        if (!blad.sprawdzCzyTowyraz(nazwa)){
            while (!blad.sprawdzCzyTowyraz(nazwa)){
                System.out.println("Nie jest to wyraz, prosze wprowadzic jeszcze raz");
                nazwa = skaner.nextLine();
            }
        }

        System.out.println("Prosze podac nazwisko dostawcy");
        String nazwisko = skaner.nextLine();
        if (!blad.sprawdzCzyTowyraz(nazwisko)){
            while (!blad.sprawdzCzyTowyraz(nazwisko)){
                System.out.println("Nie jest to wyraz, prosze wprowadzic jeszcze raz");
                nazwisko = skaner.nextLine();
            }
        }

        System.out.println("Prosze podac nr. telefonu dostawcy (Przyklad: 123-123-123)");
        String nrtelefonu = skaner.nextLine();
        if (!blad.sprawdzaCzyToNrTelefonu(nrtelefonu)){
            while (!blad.sprawdzaCzyToNrTelefonu(nrtelefonu)){
                System.out.println("Nie jest to nr. telefonu, prosze wprowadzic jeszcze raz");
                nrtelefonu = skaner.nextLine();
            }
        }
        return new Dostawca(id,nazwa,nazwisko, nrtelefonu);
    }

    @Override
    public void wyswietlWszystkichPracownikow(Restauracja restauracja) {

        for (Pracownik pracownik : restauracja.getZatrudnieniPracownicy()) {
            System.out.println("Id: " + pracownik.getId() + " " + pracownik.getImie() + " " + pracownik.getNazwisko());

        }
    }

    @Override
    public void wyswietlWybranegoPracownika(Restauracja restauracja) {
        Scanner skaner = new Scanner(System.in);
        String input = "";
        while (!input.equals("k")) {
            System.out.println("Oto nasi pracownicy: ");
            wyswietlWszystkichPracownikow(restauracja);
            System.out.println("Aby pokazac szczegolowe informacje, wpisz jego id");
            System.out.println("Aby wyjsc, wpisz k");
            input = skaner.nextLine();

            if (blad.sprawdzaCzyNumer(input)){
                int inputint = Integer.parseInt(input);
                if (blad.sprawdzaCzyJestNaLisciePracownikow(restauracja.getZatrudnieniPracownicy(),inputint)){
                    for (Pracownik pracownik : restauracja.getZatrudnieniPracownicy()) {
                        if (pracownik.getId() == inputint){
                            System.out.println("Id: " + pracownik.getId() + " " + pracownik.getImie() + " " + pracownik.getNazwisko() + " Nr.telefonu: " + pracownik.getNumerTelefonu());
                            System.out.println("Napiwek: " + pracownik.getNapiwek() + " zl Zrealizowane zamowienia: " + pracownik.getLiczbaZrealizowanychZamowien());
                            System.out.println();
                        }
                    }
                } else {
                    System.out.println("Podany numer nie znajduje sie na liscie naszych pracownikow");
                }
            } else if (input.equals("k")){

            } else {
                System.out.println("Nie jest to poprawny numer, wprowadz jeszcze raz");
            }
        }
    }

    @Override
    public void wyswietlZrealizowaneZamowienia(Restauracja restauracja) {
        System.out.println("Lista zrealizowanych zamowien:");
        restauracja.getZamowienia().stream()
                .filter(Zamowienie::isDostarczone)
                .forEach(x -> System.out.println(
                        "id: " + x.getNr() +
                                "; Utworzony: " + x.getCzasUtworzenia() +
                                "; Id Pracownika: " + x.getNrPracownika()));
    }

    @Override
    public void wyswietlKucharzy(Restauracja restauracja) {
        for (Pracownik pracownik : restauracja.getZatrudnieniPracownicy()) {
            if (pracownik.getClass() == Kucharz.class){
                System.out.println("Id: " + pracownik.getId() + " " + pracownik.getImie() + " " + pracownik.getNazwisko());
            }
        }
    }

    @Override
    public void usunKucharza(Restauracja restauracja) {
        Scanner skaner = new Scanner(System.in);
        String nr = "";
        List <Pracownik> kucharze = restauracja.getZatrudnieniPracownicy().stream()
                .filter(x -> x.getClass() == Kucharz.class)
                .collect(Collectors.toList());

            while (!nr.equals("b")){
                System.out.println("Wybierz pracownika do usuniecia, wpisz jego id");
                wyswietlKucharzy(restauracja);
                System.out.println("powrot -b");
                nr = skaner.next();

                try {
                    int number = Integer.parseInt(nr);
                    if (blad.sprawdzaCzyJestNaLisciePracownikow((ArrayList<Pracownik>) kucharze, number)) {
                        System.out.println("Czy napewno chcesz usunac podanego kucharza? [t - tak, n - nie]");
                        String decyzja = skaner.next();

                        if (decyzja.equals("t")) {
                            ArrayList<Pracownik> nowiPracownicy = (ArrayList<Pracownik>) restauracja.getZatrudnieniPracownicy().stream()
                                    .filter(x -> x.getId() != number)
                                    .collect(Collectors.toList());
                            restauracja.setZatrudnieniPracownicy(nowiPracownicy);
                            kucharze.remove(0);
                            System.out.println("Kucharz zostal pomyslnie usuniety");
                            try {
                                blad.sprawdzCzyToOstatniPracownik((ArrayList<Pracownik>) kucharze);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (!decyzja.equals("n")) {
                            System.out.println("Wprowadz poprawna litere");
                        } else {
                            System.out.println("Usuwanie pracownika zostalo anulowane");
                        }
                    }  else {
                        System.out.println("Wprowadz numer istniejacy na liscie");
                    }
                } catch (NumberFormatException n) {
                }
            }
    }

    @Override
    public void wyswietlKelnerow(Restauracja restauracja) {
        for (Pracownik pracownik : restauracja.getZatrudnieniPracownicy()) {
            if (pracownik.getClass() == Kelner.class){
                System.out.println("Id: " + pracownik.getId() + " " + pracownik.getImie() + " " + pracownik.getNazwisko());
            }
        }
    }

    @Override
    public void usunKelnera(Restauracja restauracja) {
        Scanner skaner = new Scanner(System.in);
        String nr = "";
        List <Pracownik> kelnerzy = restauracja.getZatrudnieniPracownicy().stream()
                .filter(x -> x.getClass() == Kelner.class)
                .collect(Collectors.toList());

        while (!nr.equals("b")){
            System.out.println("Wybierz pracownika do usuniecia, wpisz jego id");
            wyswietlKelnerow(restauracja);
            System.out.println("powrot -b");
            nr = skaner.next();

            try {
                int number = Integer.parseInt(nr);
                if (blad.sprawdzaCzyJestNaLisciePracownikow((ArrayList<Pracownik>) kelnerzy, number)) {
                    System.out.println("Czy napewno chcesz usunac podanego kelnera? [t - tak, n - nie]");
                    String decyzja = skaner.next();

                    if (decyzja.equals("t")) {
                        ArrayList<Pracownik> nowiPracownicy = (ArrayList<Pracownik>) restauracja.getZatrudnieniPracownicy().stream()
                                .filter(x -> x.getId() != number)
                                .collect(Collectors.toList());
                        restauracja.setZatrudnieniPracownicy(nowiPracownicy);
                        kelnerzy.remove(0);
                        System.out.println("Kelner zostal pomyslnie usuniety");
                        try {
                            blad.sprawdzCzyToOstatniPracownik((ArrayList<Pracownik>) kelnerzy);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (!decyzja.equals("n")) {
                        System.out.println("Wprowadz poprawna litere");
                    } else {
                        System.out.println("Usuwanie pracownika zostalo anulowane");
                    }
                } else {
                    System.out.println("Wprowadz numer istniejacy na liscie");
                }
            } catch (NumberFormatException n) {
            }
        }
    }

    @Override
    public void wyswietlDostawcow(Restauracja restauracja) {
        for (Pracownik pracownik : restauracja.getZatrudnieniPracownicy()) {
            if (pracownik.getClass() == Dostawca.class){
                System.out.println("Id: " + pracownik.getId() + " " + pracownik.getImie() + " " + pracownik.getNazwisko());
            }
        }
    }

    @Override
    public void usunDostawce(Restauracja restauracja) {
        Scanner skaner = new Scanner(System.in);
        String nr = "";
        List <Pracownik> dostawcy = restauracja.getZatrudnieniPracownicy().stream()
                .filter(x -> x.getClass() == Dostawca.class)
                .collect(Collectors.toList());

        while (!nr.equals("b")){
            System.out.println("Wybierz pracownika do usuniecia, wpisz jego id");
            wyswietlDostawcow(restauracja);
            System.out.println("powrot -b");
            nr = skaner.next();

            try {
                int number = Integer.parseInt(nr);
                if (blad.sprawdzaCzyJestNaLisciePracownikow((ArrayList<Pracownik>) dostawcy, number)) {
                    System.out.println("Czy napewno chcesz usunac podanego dostawce? [t - tak, n - nie]");
                    String decyzja = skaner.next();

                    if (decyzja.equals("t")) {
                        ArrayList<Pracownik> nowiPracownicy = (ArrayList<Pracownik>) restauracja.getZatrudnieniPracownicy().stream()
                                .filter(x -> x.getId() != number)
                                .collect(Collectors.toList());
                        restauracja.setZatrudnieniPracownicy(nowiPracownicy);
                        dostawcy.remove(0);
                        System.out.println("Dostawca zostal pomyslnie usuniety");

                        try {
                            blad.sprawdzCzyToOstatniPracownik((ArrayList<Pracownik>) dostawcy);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (!decyzja.equals("n")) {
                        System.out.println("Wprowadz poprawna litere");
                    } else {
                        System.out.println("Usuwanie pracownika zostalo anulowane");
                    }
                }  else {
                    System.out.println("Wprowadz numer istniejacy na liscie");
                }
            } catch (NumberFormatException n) {
            }
        }
    }
}
