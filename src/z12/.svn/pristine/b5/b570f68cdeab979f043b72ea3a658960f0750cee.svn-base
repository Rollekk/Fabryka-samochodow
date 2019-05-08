package z12.Osoby;

import z12.Produkcja.Zamowienie;

import java.io.IOException;

public class Klient extends Osoba {

    private int idKlienta;
    private int numerInformacjiPodatkowej;
    private int wartoscZamowienia;

    public Klient(int idKlienta, int numerInformacjiPodatkowej, int wartoscZamowienia) {
    super();
        this.idKlienta = idKlienta;
        this.numerInformacjiPodatkowej = numerInformacjiPodatkowej;
        this.wartoscZamowienia = wartoscZamowienia;
    }

    void przegladajOferte(int idOferty) {

    }

   public Zamowienie zlozZamowienie() throws IOException{

        Zamowienie zamowienie = new Zamowienie(this.wartoscZamowienia);
        return zamowienie;
    }

    boolean personalizujOferte(int idZamowienia) {

        return true;
    }
}
