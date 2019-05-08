package z12.Samochod;

public class Silnik {
private String rodzajSilnika;
private String nazwaKodowa;
private int pojemnosc;
private int iloscZaworow;
private int iloscWalkow;
private String rodzajWtrysku;
private int iloscCylindrow;
private Boolean zmiennyRozdzad;

    public String getRodzajSilnika() { return rodzajSilnika; }

    public void setRodzajSilnika(String rodzajSilnika) {
        this.rodzajSilnika = rodzajSilnika;
    }

    public String getNazwaKodowa() {
        return nazwaKodowa;
    }

    public void setNazwaKodowa(String nazwaKodowa) {
        this.nazwaKodowa = nazwaKodowa;
    }

    public int getPojemnosc() {
        return pojemnosc;
    }

    public void setPojemnosc(int pojemnosc) {
        this.pojemnosc = pojemnosc;
    }

    public int getIloscZaworow() {
        return iloscZaworow;
    }

    public void setIloscZaworow(int iloscZaworow) {
        this.iloscZaworow = iloscZaworow;
    }

    public int getIloscWalkow() {
        return iloscWalkow;
    }

    public void setIloscWalkow(int iloscWalkow) {
        this.iloscWalkow = iloscWalkow;
    }

    public String getRodzajWtrysku() {
        return rodzajWtrysku;
    }

    public void setRodzajWtrysku(String rodzajWtrysku) {
        this.rodzajWtrysku = rodzajWtrysku;
    }

    public int getIloscCylindrow() {
        return iloscCylindrow;
    }

    public void setIloscCylindrow(int iloscCylindrow) {
        this.iloscCylindrow = iloscCylindrow;
    }

    public Boolean getZmiennyRozdzad() {
        return zmiennyRozdzad;
    }

    public void setZmiennyRozdzad(Boolean zmiennyRozdzad) {
        this.zmiennyRozdzad = zmiennyRozdzad;
    }
}
