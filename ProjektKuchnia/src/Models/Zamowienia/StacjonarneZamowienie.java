package Models.Zamowienia;

import KlasyAbstrakcyjne.Zamowienie;
import Models.Danie;


import java.util.ArrayList;

public class StacjonarneZamowienie extends Zamowienie {
    private int nrStolika;

    public StacjonarneZamowienie(ArrayList<Danie> zamowienieUzytkownika, int id, int idKelnera) {
        super(zamowienieUzytkownika, id, idKelnera);
        this.isStacjonarne = true;
    }

}
