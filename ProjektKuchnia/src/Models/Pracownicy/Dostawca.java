package Models.Pracownicy;

import KlasyAbstrakcyjne.Pracownik;

public class Dostawca extends Pracownik {
    private double napiwek;
    public Dostawca(int id, String imie, String nazwisko, String numerTelefonu) {
        super(id, imie, nazwisko, numerTelefonu);
    }
}
