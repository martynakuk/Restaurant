package Models.Pracownicy;

import KlasyAbstrakcyjne.Pracownik;

public class Kelner extends Pracownik {
    private double napiwek;

    public Kelner(int id, String imie, String nazwisko, String numerTelefonu) {
        super(id, imie, nazwisko, numerTelefonu);
    }
}
