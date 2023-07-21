package Models;

public class Danie {
    private double cena;
    private String nazwa;
    private boolean isAvailable;
    private int id;
    private String opis;
    private int czasZrobienia;

    public int getCzasZrobienia() {
        return czasZrobienia;
    }

    public int getId(){
        return id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public double getCena() {
        return cena;
    }

    public Danie (int id, String nazwa, double cena, String opis, int czasZrobienia){
        this.id = id;
        this.nazwa = nazwa;
        this.cena = cena;
        this.opis = opis;
        this.czasZrobienia = czasZrobienia;
        this.isAvailable = true;
    }

    @Override
    public String toString(){
        return this.id + ") " + this.nazwa + "; Cena: " + this.cena + "zl$\nOpis:@ " + this.opis + "& Dostepne: " + this.isAvailable + "*";
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

}
