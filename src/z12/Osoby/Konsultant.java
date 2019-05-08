package z12.Osoby;

public class Konsultant extends Pracownik {


    public Konsultant(int idPracownika, float pensjaPracownika, String dzialPracownika) {
        super(idPracownika, pensjaPracownika, dzialPracownika);
    }

    boolean przyznajRabat(int idZamowienia)
        {
            return true;
        }

    boolean dodajOferte()
    {
    return true;
    }

    boolean aktualizujOferte(int idOferty)
    {
    return true;
    }

    boolean usunOferte(int idOferty)
    {
    return true;
    }

    boolean edytujZamowienie(int idZamowieia)
    {
    return true;
    }

    boolean anulujZamowienie(int idZamowienia)
    {
    return true;
    }

    int wystawFakture(int idZamowienia)
    {
    return 1;
    }

    boolean wygenerujDokumenty(int idZamowienia)
    {
    return true;
    }

}
