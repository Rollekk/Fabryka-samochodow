package z12.Produkcja;

import java.util.Random;

public class KartaPostepu {

    private boolean etapKaroserii = false;
    private boolean etapPrzygotowaniaDoLakierowania = false;
    private boolean etapLakierowania = false;
    private boolean etapMontazuMechaniki = false;
    private boolean etapMontazuWnetrza = false;
    private boolean etapMontazuElementowZewnetrznych = false;
    private boolean etapUzupelnieniaPlynow = false;
    private boolean etapTestowania = false;

    public boolean isEtapKaroserii() {
        return etapKaroserii;
    }

    public boolean isEtapPrzygotowaniaDoLakierowania() {
        return etapPrzygotowaniaDoLakierowania;
    }

    public boolean isEtapLakierowania() {
        return etapLakierowania;
    }

    public boolean isEtapMontazuMechaniki() {
        return etapMontazuMechaniki;
    }

    public boolean isEtapMontazuWnetrza() {
        return etapMontazuWnetrza;
    }

    public boolean isEtapMontazuElementowZewnetrznych() {
        return etapMontazuElementowZewnetrznych;
    }

    public boolean isEtapUzupelnieniaPlynow() {
        return etapUzupelnieniaPlynow;
    }

    public boolean isEtapTestowania() {
        return etapTestowania;
    }

    private int obliczRyzykoUsterki()
    {
        var rand = new Random();
        return rand.nextInt(10);
    }

    public void wykonajEtapKaroserii()
    {
        try {
            System.out.println("Trwa produkcja karoserii...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(obliczRyzykoUsterki() < 9)
            etapKaroserii = true;
    }

    public void wykonajEtapPrzygotowaniaDoLakierowania()
    {
        try {
            System.out.println("Trwa przygotowanie do lakierowania...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(obliczRyzykoUsterki() < 9)
            etapPrzygotowaniaDoLakierowania = true;
    }

    public void wykonajEtapLakierowania()
    {
        try {
            System.out.println("Trwa lakierowanie...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(obliczRyzykoUsterki() < 9)
            etapLakierowania = true;
    }

    public void wykonajEtapMontazuMechaniki()
    {
        try {
            System.out.println("Trwa montaz mechaniki...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(obliczRyzykoUsterki() < 9)
            etapMontazuMechaniki = true;
    }

    public void wykonajEtapMontazuWnetrza()
    {
        try {
            System.out.println("Trwa montaz wnetrza...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(obliczRyzykoUsterki() < 9)
            etapMontazuWnetrza = true;
    }

    public void wykonajEtapMontazuElementowZewnetrznych()
    {
        try {
            System.out.println("Trwa montaz elementow zewnetrznych...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(obliczRyzykoUsterki() < 9)
            etapMontazuElementowZewnetrznych = true;
    }

    public void wykonajEtapUzupelnieniaPlynow()
    {
        try {
            System.out.println("Trwa uzupelnianie plynow...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(obliczRyzykoUsterki() < 9)
            etapUzupelnieniaPlynow = true;
    }

    public void wykonajEtapTestowania()
    {
        var tester = new Testowanie();
        if(testujAuto(tester))
            etapTestowania = true;
    }

    private boolean testujAuto(Testowanie tester)
    {
        var zatwierdzono = true;
        if(!tester.isTestPrzyspieszenia())
        {
            tester.testujPrzyspieszenie();
            if(!tester.isTestPrzyspieszenia())
                zatwierdzono = false;
        }
        if(!tester.isTestHamowania())
        {
            tester.testujHamowanie();
            if(!tester.isTestHamowania())
                zatwierdzono = false;
        }
        if(!tester.isTestZawieszenia())
        {
            tester.testujZawieszenie();
            if(!tester.isTestZawieszenia())
                zatwierdzono = false;
        }
        if(!tester.isTestWyposazenia())
        {
            tester.testujWyposazenie();
            if (!tester.isTestWyposazenia())
                zatwierdzono = false;
        }
        return zatwierdzono;
    }
}
