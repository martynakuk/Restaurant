package Interfaces;

import KlasyAbstrakcyjne.Pracownik;
import Models.Danie;
import Models.Menu;
import Models.Restauracja;



public interface IRestauracja {

    void dodajDanieDoMenu(Menu menu);
    Danie stworzDanie(int id);

    void usunDanie(Menu menu);

    void otworzRestauracje(Restauracja restauracja); //Rozpoczecie pracy restauracji
    void zamknijRestauracje(Restauracja restauracja); //Zatrzymanie pracy restauracji

    void oznaczDanieJakoNieDostepne(Menu menu);
    void oznaczDanieJakoDostepne(Menu menu);

    void pokazZamowieniaCzekajaceNaRealizacje(Restauracja restauracja);
    void aktualnyUtarg(Restauracja restauracja);

    void dodajKucharza(Restauracja restauracja);
    void dodajKelnera(Restauracja restauracja);
    void dodajDostawce(Restauracja restauracja);

    Pracownik stworzkucharza(int id);
    Pracownik stworzkelnera(int id);
    Pracownik stworzdostawce(int id);

    void wyswietlWszystkichPracownikow(Restauracja restauracja);
    void wyswietlWybranegoPracownika(Restauracja restauracja);

    void wyswietlZrealizowaneZamowienia(Restauracja restauracja);

    void wyswietlKucharzy(Restauracja restauracja);
    void usunKucharza(Restauracja restauracja);

    void wyswietlKelnerow(Restauracja restauracja);
    void usunKelnera(Restauracja restauracja);

    void wyswietlDostawcow(Restauracja restauracja);
    void usunDostawce(Restauracja restauracja);

}
