package Interfaces;


import Models.Restauracja;

public interface IKontroler {
    void wprowadzenieDoProgramu();
    void opcjeZamowienia(Restauracja restauracja, int wybor);
    void losoweZamowienie(Restauracja restauracja);
    void administracja(Restauracja restauracja);

}
