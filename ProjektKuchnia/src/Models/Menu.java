package Models;

import Narzedzia.Sprawdzanie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Menu {
    ArrayList <Danie> menu;
    Path filePath = Paths.get("src/Pliki/Menumenu.txt");
    Sprawdzanie blad = new Sprawdzanie();

    public void setMenu(ArrayList<Danie> menu) {
        this.menu = menu;
    }
    public ArrayList<Danie> getMenu() {
        return menu;
    }

    public Menu() {
        this.menu = new ArrayList<>();
        fillMenu();
        zapiszDoPliku();
    }

    private void fillMenu(){
        menu.add(new Danie(blad.przyznaczIdDoDania(menu), "Schabowy z ziemniakami", 32, "Swiezo usmazony schab wieprzowy z ziemniakami", 40));
        menu.add(new Danie(blad.przyznaczIdDoDania(menu),"Salatka z lososiem", 36, "Wiosenna salatka z lososiem", 20));
        menu.add(new Danie(blad.przyznaczIdDoDania(menu), "Bigos", 29, "Rodzinny bigos", 20));
        menu.add(new Danie(blad.przyznaczIdDoDania(menu), "Gulasz wolowy", 40, "Dlugo gotowany gulasz na najlepszej jakosci miesie wolowym", 35));
        menu.add(new Danie(blad.przyznaczIdDoDania(menu), "Specjal dla dzieci: Pizza pepperoni", 25, "Dla najmlodszych, swiezo wypiekana pizza", 60));
    }

    public void zapiszDoPliku(){
        ArrayList<String> daniaDoDodania = new ArrayList<>();
        menu.stream()
                .forEach(x -> daniaDoDodania.add(x.toString()));
        try {
            Files.write(this.filePath, daniaDoDodania);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Jest problem");
        }
    }

    public ArrayList<String> showMenu(){
        ArrayList<String> daniaDoDodania = new ArrayList<>();
        menu.stream()
                .forEach(x -> daniaDoDodania.add(x.toString()));
        try {
            Files.readAllLines(this.filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Jest problem");
        }
        return daniaDoDodania;
    }

}
