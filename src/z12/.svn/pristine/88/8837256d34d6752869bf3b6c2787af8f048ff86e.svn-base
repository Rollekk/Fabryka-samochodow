package z12.Produkcja;

import java.text.SimpleDateFormat;

public class Platnosci {

    private int zaliczka;
    private String dataPlatnosci;
    private boolean oplaconoZaliczke;

    public Platnosci(int zaliczka)
    {
        this.zaliczka = zaliczka;
        oplaconoZaliczke = false;
    }

    public void oplacZaliczke()
    {
        var dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        dataPlatnosci = dateFormat.format(dataPlatnosci);
        oplaconoZaliczke = true;
    }

    public int getZaliczka() {
        return zaliczka;
    }

    public String getDataPlatnosci() {
        return dataPlatnosci;
    }

    public boolean isOplaconoZaliczke() {
        return oplaconoZaliczke;
    }
}
