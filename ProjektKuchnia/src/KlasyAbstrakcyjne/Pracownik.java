package KlasyAbstrakcyjne;

public abstract class Pracownik {

    private int id;
    private String imie;
    private String nazwisko;
    private String numerTelefonu;
    private int liczbaZrealizowanychZamowien;
    private double napiwek;

    public Pracownik(int id, String imie, String nazwisko, String numerTelefonu){
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.numerTelefonu = numerTelefonu;
        this.id = id;
        this.liczbaZrealizowanychZamowien = 0;
        this.napiwek = 0;
    }
    public int getId() {
        return id;
    }

    public int getLiczbaZrealizowanychZamowien() {
        return liczbaZrealizowanychZamowien;
    }

    public void setLiczbaZrealizowanychZamowien(int liczbaZrealizowanychZamowien) {
        this.liczbaZrealizowanychZamowien = liczbaZrealizowanychZamowien;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getNumerTelefonu() {
        return numerTelefonu;
    }

    public double getNapiwek() {
        return napiwek;
    }
}
