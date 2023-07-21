package Narzedzia;

import KlasyAbstrakcyjne.Pracownik;
import KlasyAbstrakcyjne.Zamowienie;
import Models.Danie;
import Models.Menu;
import Models.Pracownicy.Dostawca;
import Models.Pracownicy.Kelner;
import Models.Restauracja;
import Models.Zamowienia.StacjonarneZamowienie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sprawdzanie {

    public Sprawdzanie() {
    }

    public boolean sprawdzaCzyNumer(String string){
        return string.matches("[0-9]+");
    }

    public boolean sprawdzaCzyJestWMenu(ArrayList <Danie> menu, int number){
        List<Danie> przefiltrowanaLista = menu.stream()
                .filter(x -> x.getId() == number)
                .collect(Collectors.toList());

        return !(przefiltrowanaLista.isEmpty());
    }

    public boolean sprawdzaCzyJestNaLisciePracownikow(ArrayList <Pracownik> listapracownikow, int number){
        List<Pracownik> przefiltrowanalista = listapracownikow.stream()
                .filter(x -> x.getId() == number)
                .collect(Collectors.toList());
        return !(przefiltrowanalista.isEmpty());
    }

    public int przyznaczIdDoDania(ArrayList <Danie> menu){
        int max = 0;
        for (Danie danie : menu) {
            if (danie.getId() > max){
                max = danie.getId();
            }
        }
        return max += 1;
    }

    public int przyznaczNrDoZamowienia(ArrayList <Zamowienie> zamowienia){
        int max = 0;
        for (Zamowienie zamowienie : zamowienia) {
            if (zamowienie.getNr() > max){
                max = zamowienie.getNr();
            }
        }
        return max += 1;
    }

    public void pokazZamowienie(ArrayList<Danie> zamowienie){
        System.out.println("Oto twoje zamowienie:");

        int i = 1;
        double kosztZamowienia = 0;
        int czasZamowienia = 0;
        for (Danie danie : zamowienie) {
            System.out.println(i + ". " + danie.getNazwa() + " Cena: " + danie.getCena() + "zl");
            kosztZamowienia += danie.getCena();
            czasZamowienia += danie.getCzasZrobienia();
            i++;
        }

        System.out.println("Koszt zamowienia: " + kosztZamowienia + "zl");
        System.out.println("Czas wykonania: " + czasZamowienia + " sekund");

    }

    public void pokazanieMenu(Menu menu) {
        for (int i = 0; i < menu.showMenu().size(); i++) {
            String rezultat = "";
            String calalinijka = menu.showMenu().get(i);
            rezultat += calalinijka.substring(0, calalinijka.indexOf(')')) + ") ";
            rezultat += calalinijka.substring(calalinijka.indexOf(')') + 2, calalinijka.indexOf(';'));
            rezultat += calalinijka.substring(calalinijka.indexOf(';') + 1, calalinijka.indexOf('$'));
            rezultat += "\n     ~ " + calalinijka.substring(calalinijka.indexOf('@') + 2, calalinijka.indexOf('&'));
            if (calalinijka.contains("false*"))
                rezultat += "\n     - Pozycja niedostepna";
            System.out.println(rezultat);
        }
    }

    public boolean sprawdzCzyTowyraz(String podanaLiteralubLiczba){
        return podanaLiteralubLiczba.matches("[A-Za-z]*");
    }

    public double kosztZamowienia(Zamowienie zamowienie){
        int i = 1;
        double kosztZamowienia = 0;
        for (Danie danie : zamowienie.getTwojeZamowienie()) {
            kosztZamowienia += danie.getCena();
            i++;
        }
        return kosztZamowienia;
    }

    public int czasZamowienia(Zamowienie zamowienie){
        int i = 1;
        int czas = 0;
        for (Danie danie : zamowienie.getTwojeZamowienie()) {
            czas += danie.getCzasZrobienia();
            i++;
        }
        return czas;
    }

    public int przyznaczIdDoPracownika(ArrayList <Pracownik> listaPracownikow){
        int max = 0;
        for (Pracownik pracownik : listaPracownikow) {
            if (pracownik.getId() > max){
                max = pracownik.getId();
            }
        }
        return max += 1;
    }

    public boolean sprawdzaCzyToNrTelefonu(String numerTelefonu){
        return numerTelefonu.matches("[0-9]{3}-[0-9]{3}-[0-9]{3}");
    }

    public int przyznaczStacjonarneZamowienieDoKelnera(ArrayList <Pracownik> listaPracownikow){
        List <Pracownik> Kelnerzy = listaPracownikow.stream()
                .filter(x -> x.getClass() == Kelner.class)
                .toList();
        int lKelnerow = Kelnerzy.size();
        int wybranyKelner = (int)((Math.random()*lKelnerow));

        return Kelnerzy.get(wybranyKelner).getId();
    }

    public int przyznaczNiestacjonarneZamowienieDoDostawcy(ArrayList <Pracownik> listaPracownikow){
        List <Pracownik> Dostawcy = listaPracownikow.stream()
                .filter(x -> x.getClass() == Dostawca.class)
                .toList();
        int lDostawcow = Dostawcy.size();
        int wybranyDostawca = (int)((Math.random())*lDostawcow);

        return Dostawcy.get(wybranyDostawca).getId();
    }


    public void przydzielPunktZrealizowanegoZamowienia(Zamowienie zamowienie, Restauracja restauracja) {

            restauracja.getZatrudnieniPracownicy().stream()
                    .filter(x -> x.getId() == zamowienie.getNrPracownika())
                    .forEach(x -> x.setLiczbaZrealizowanychZamowien(x.getLiczbaZrealizowanychZamowien() +1));
            restauracja.setUtarg(restauracja.getUtarg() + zamowienie.getKosztZamowienia());
    }
    public void wyswietlZamowienieMenu(){
        System.out.println("Wybierz opcje:\n");
        System.out.println("1. Zjedz na miejscu");
        System.out.println("2. Wez na wynos");
        System.out.println("3. Losowe zamowienie\n");
        System.out.println("wyjscie -b; pomoc -h");
    }

    public void wyswietlDolneZamowenieMenu(){
        System.out.println("\nzloz zamowienie -c, wyjscie -b; pokaz zamowienie -p");
    }

    public void pomoc(){
        System.out.println(
                "Pomoc ->" +
                "\n - Wybierz opcje poprzez odpowiadajacy numer oraz zatwierdz przyciskiem ENTER" +
                "\n - Lub wpisz litere z dolu Menu, zatwierdz przyciskiem ENTER");
    }

    public void wyswietlZarzadzanieZamowieniami(){
        System.out.println("Zarzadzaj zamowieniami:" +
                "\n1. Wyswietl niezrealizowane zamowienia" +
                "\n2. Wyswietl zrealizowane zamowienia" +
                "\npowrot -b");
    }

    public void wyswietlZarzadzaniePracownikami(){
        System.out.println("1. Zatrudnij pracownika" +
                "\n2. Zwolnij pracownika" +
                "\n3. Wyswietl wszystkich pracownikow" +
                "\n4. Wyswietl detale pracownika" +
                "\npowrot -b");
    }
    public void wyswietlTypyPraciwnikow(){
        System.out.println("1. Kucharz" +
                "\n2. Kelner" +
                "\n3. Dostawca" +
                "\n powrot -b");
    }

    public void wyswietlZarzadzanieMenu(){
        System.out.println("1. Pokaz Menu" +
                "\n2. Dodaj danie do Menu" +
                "\n3. Usun danie z Menu" +
                "\n4. Oznacz danie jako niedostepne" +
                "\n5. Oznacz danie jako dostepne" +
                "\n powrot -b");
    }

    public void sprawdzCzyToOstatniPracownik(ArrayList <Pracownik> pracownicyDanegoTypu) throws Exception {
       try {
           if (pracownicyDanegoTypu.isEmpty()){
               throw new BrakPracownikow("Brak pracownikow na danym dziale");
           }
       } catch (BrakPracownikow s){
           System.out.println("Brak pracownikow na danym dziale");
       }
    }
}
