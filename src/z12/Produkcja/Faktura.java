package z12.Produkcja;

public class Faktura {

    public int idFaktury;
    private int cena;
    private String rodzajPlatnosci;
    private String data;
    private static int iloscFaktrur;

    public Faktura(int cena, String rodzajPlatnosci, String data)
    {
        idFaktury = ++iloscFaktrur;
        this.cena = cena;
        this.rodzajPlatnosci = rodzajPlatnosci;
        this.data = data;
    }

    public void wystawFakture()
    {

    }

    private int liczCene()
    {
        return 1;
    }
}
