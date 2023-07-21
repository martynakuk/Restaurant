package Models.Zamowienia;

import KlasyAbstrakcyjne.Zamowienie;
import Models.Danie;

import java.util.ArrayList;

public class NieStacjonarneZamowienie extends Zamowienie {
    private String adresDostawy;

    public NieStacjonarneZamowienie(ArrayList<Danie> zamowienieUzytkownika, int id, int idDostawcy) {
        super(zamowienieUzytkownika,id, idDostawcy);
        this.isStacjonarne = false;
    }

}
