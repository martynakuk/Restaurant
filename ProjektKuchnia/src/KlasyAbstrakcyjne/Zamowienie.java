package KlasyAbstrakcyjne;

import Models.Danie;

import java.time.LocalTime;
import java.util.ArrayList;

public abstract class Zamowienie {

    private ArrayList<Danie> twojeZamowienie;
    private int nr;
    private boolean isZrealizowane;
    private int czasZrealizowania;
    protected boolean isStacjonarne;
    private LocalTime czasUtworzenia;
    private int nrPracownika;
    private boolean isDostarczone;
    private boolean isPrzedawnione;
    private double kosztZamowienia;


    public Zamowienie(ArrayList<Danie> listaDan, int id, int idPracownika) {
        this.twojeZamowienie = listaDan;
        this.nr = id;
        this.isZrealizowane = false;
        this.czasZrealizowania = czasZrealizowaniaZamowienia();
        this.czasUtworzenia = LocalTime.now().withSecond(0).withNano(0);
        this.nrPracownika = idPracownika;
        this.isDostarczone = false;
        this.isPrzedawnione = false;
        this.kosztZamowienia = obliczKosztZamowienia();
    }

    private int czasZrealizowaniaZamowienia() {

        int czas = this.twojeZamowienie.stream().mapToInt(Danie::getCzasZrobienia).sum();
        // this.twojeZamowienie.stream()
        //                .forEach(x -> czas += x.getCzasZrobienia());
        // Moj poprzedni kod, uzylam pomocy IntellJ w realizacji
        return czas;
    }

    public ArrayList<Danie> getTwojeZamowienie() {
        return twojeZamowienie;
    }

    public int getNr() {
        return nr;
    }

    public boolean isZrealizowane() {
        return isZrealizowane;
    }


    public int getNrPracownika() {
        return nrPracownika;
    }


    public boolean isDostarczone() {
        return isDostarczone;
    }


    public double obliczKosztZamowienia() {
        double kosztzam = 0;
        for (Danie danie : this.getTwojeZamowienie()) {
            kosztzam += danie.getCena();
        }
        return kosztzam;
    }

    public double getKosztZamowienia() {
        return kosztZamowienia;
    }


    public LocalTime getCzasUtworzenia() {
        return czasUtworzenia;
    }
}
