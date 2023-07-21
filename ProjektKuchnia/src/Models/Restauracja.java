package Models;

import KlasyAbstrakcyjne.Pracownik;
import KlasyAbstrakcyjne.Zamowienie;
import Models.Pracownicy.Dostawca;
import Models.Pracownicy.Kelner;
import Models.Pracownicy.Kucharz;
import Models.Zamowienia.NieStacjonarneZamowienie;
import Models.Zamowienia.StacjonarneZamowienie;
import Narzedzia.Sprawdzanie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Restauracja {
    private Menu menu;
    private ArrayList <Zamowienie> zamowienia;
    private boolean isOpen;
    private ArrayList <Pracownik> zatrudnieniPracownicy;
    private Sprawdzanie sprawdzanie = new Sprawdzanie();
    private double utarg;

    public Restauracja() {
        this.menu = new Menu();
        this.zamowienia = new ArrayList<>();
        this.isOpen = false;
        this.zatrudnieniPracownicy = new ArrayList<>();
        pracownicyDoRestauracji();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        if (open){
            zamowieniaDoRestauracji();
        }
        isOpen = open;
    }

    public ArrayList<Zamowienie> getZamowienia() {
        return zamowienia;
    }

    public Menu getMenu() {
        return menu;
    }

    public ArrayList<Pracownik> getZatrudnieniPracownicy() {
        return zatrudnieniPracownicy;
    }

    public void setZatrudnieniPracownicy(ArrayList<Pracownik> zatrudnieniPracownicy) {
        this.zatrudnieniPracownicy = zatrudnieniPracownicy;
    }

    public void pracownicyDoRestauracji(){
        this.getZatrudnieniPracownicy().add(new Kucharz(sprawdzanie.przyznaczIdDoPracownika(this.getZatrudnieniPracownicy()),"Jacek", "Ziemniak", "123-456-789"));
        this.getZatrudnieniPracownicy().add(new Kucharz(sprawdzanie.przyznaczIdDoPracownika(this.getZatrudnieniPracownicy()),"Ania", "Burak", "987-654-321"));
        this.getZatrudnieniPracownicy().add(new Kelner(sprawdzanie.przyznaczIdDoPracownika(this.getZatrudnieniPracownicy()),"Kasia", "Owoc", "123-456-123"));
        this.getZatrudnieniPracownicy().add(new Dostawca(sprawdzanie.przyznaczIdDoPracownika(this.getZatrudnieniPracownicy()),"Robert", "Warzywo", "456-123-456"));
    }

    public void zamowieniaDoRestauracji(){
        losoweZamowienieBezPrint();
        losoweZamowienieBezPrint();
        losoweZamowienieBezPrint();
        losoweZamowienieBezPrint();
        losoweZamowienieBezPrint();
        losoweZamowienieBezPrint();
        losoweZamowienieBezPrint();
        losoweZamowienieBezPrint();
        losoweZamowienieBezPrint();
        losoweZamowienieBezPrint();
    }

    public void losoweZamowienieBezPrint(){
        ArrayList<Danie> zamowienie = new ArrayList<>();

        int wybor = (int) (1 + (Math.random() * 2));
        int iloscPozycji = (int) (1 + (Math.random() * 15));
        // Numer 15, gdyz jest to najbardziej maksymalna realistyczna liczba zamowionych dan

        ArrayList<Danie> daniaRestauracji = this.getMenu().getMenu();

        for (int i = 0; i < iloscPozycji; i++) {
            int pozycja = (int) (Math.random() * this.getMenu().getMenu().size());
            if (sprawdzanie.sprawdzaCzyJestWMenu(this.getMenu().getMenu(), pozycja)) {
                List<Danie> wybraneDania = daniaRestauracji.stream()
                        .filter(x -> x.getId() == pozycja)
                        .collect(Collectors.toList());
                zamowienie.add(wybraneDania.get(0));
            }
        }

        if (wybor == 1) {
            StacjonarneZamowienie stacjzam = new StacjonarneZamowienie(zamowienie, sprawdzanie.przyznaczNrDoZamowienia(this.getZamowienia()),sprawdzanie.przyznaczStacjonarneZamowienieDoKelnera(this.getZatrudnieniPracownicy()));
            this.getZamowienia().add(stacjzam);

        } else {
            NieStacjonarneZamowienie niestacjzam = new NieStacjonarneZamowienie(zamowienie, sprawdzanie.przyznaczNrDoZamowienia(this.getZamowienia()),sprawdzanie.przyznaczNiestacjonarneZamowienieDoDostawcy(this.getZatrudnieniPracownicy()));
            this.getZamowienia().add(niestacjzam);
        }
    }

    public double getUtarg() {
        return utarg;
    }

    public void setUtarg(double utarg) {
        this.utarg = utarg;
    }

    public void setZamowienia(ArrayList<Zamowienie> zamowienia) {
        this.zamowienia = zamowienia;
    }
}
