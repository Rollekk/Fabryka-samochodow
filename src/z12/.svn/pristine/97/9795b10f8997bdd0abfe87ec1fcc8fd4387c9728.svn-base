package z12.Produkcja;

import java.util.Random;

public class Testowanie {

    private boolean testPrzyspieszenia = false;
    private boolean testHamowania = false;
    private boolean testZawieszenia = false;
    private boolean testWyposazenia = false;

    public boolean isTestPrzyspieszenia() {
        return testPrzyspieszenia;
    }

    public boolean isTestHamowania() {
        return testHamowania;
    }

    public boolean isTestZawieszenia() {
        return testZawieszenia;
    }

    public boolean isTestWyposazenia() {
        return testWyposazenia;
    }

    private int obliczRyzykoNiepowodzenia()
    {
        var rand = new Random();
        return rand.nextInt(10);
    }

    public void testujPrzyspieszenie()
    {
        try {
            System.out.println("Trwa test przyspieszenia...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(obliczRyzykoNiepowodzenia() < 9)
            testPrzyspieszenia = true;
    }

    public void testujHamowanie()
    {
        try {
            System.out.println("Trwa test hamowania...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(obliczRyzykoNiepowodzenia() < 9)
            testHamowania = true;
    }

    public void testujZawieszenie()
    {
        try {
            System.out.println("Trwa test zawieszenia...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(obliczRyzykoNiepowodzenia() < 9)
            testZawieszenia = true;
    }

    public void testujWyposazenie()
    {
        try {
            System.out.println("Trwa test wyposazenia...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(obliczRyzykoNiepowodzenia() < 9)
            testWyposazenia = true;
    }
}
