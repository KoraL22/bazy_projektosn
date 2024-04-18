package projektjava;

import java.util.Date;

public class ksiazka {
    int ID;
    String Tytuł;
    int ilość_stron;
    String Autor;
    String redakcja;
    Date rok_wydania;
    String rodzaj;

    public ksiazka(int ID, String Tytuł, int ilość_stron, String Autor, String redakcja, Date rok_wydania, String rodzaj) {
        this.ID = ID;
        this.Tytuł = Tytuł;
        this.ilość_stron = ilość_stron;
        this.Autor = Autor;
        this.redakcja = redakcja;
        this.rok_wydania = rok_wydania;
        this.rodzaj = rodzaj;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTytuł() {
        return Tytuł;
    }

    public void setTytuł(String tytuł) {
        Tytuł = tytuł;
    }

    public int getIlość_stron() {
        return ilość_stron;
    }

    public void setIlość_stron(int ilość_stron) {
        this.ilość_stron = ilość_stron;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        Autor = autor;
    }

    public String getRedakcja() {
        return redakcja;
    }

    public void setRedakcja(String redakcja) {
        this.redakcja = redakcja;
    }

    public Date getRok_wydania() {
        return rok_wydania;
    }

    public void setRok_wydania(Date rok_wydania) {
        this.rok_wydania = rok_wydania;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

}
