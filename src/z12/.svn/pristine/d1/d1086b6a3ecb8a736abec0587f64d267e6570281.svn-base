package z12.Osoby;


import z12.Produkcja.KartaPostepu;

public class PracownikProdukcji extends Pracownik {

    public PracownikProdukcji(int idPracownika, float pensjaPracownika, String dzialPracownika) {
        super(idPracownika, pensjaPracownika, dzialPracownika);
    }

    private boolean kontrolujEtapProdukcji(KartaPostepu karta, int etap)
    {
        var etapZakonczonyPowodzeniem = false;
        switch (etap)
        {
            case 1:
                karta.wykonajEtapKaroserii();
                etapZakonczonyPowodzeniem = karta.isEtapKaroserii();
                break;
            case 2:
                karta.wykonajEtapPrzygotowaniaDoLakierowania();
                etapZakonczonyPowodzeniem = karta.isEtapPrzygotowaniaDoLakierowania();
                break;
            case 3:
                karta.wykonajEtapLakierowania();
                etapZakonczonyPowodzeniem = karta.isEtapLakierowania();
                break;
            case 4:
                karta.wykonajEtapMontazuMechaniki();
                etapZakonczonyPowodzeniem = karta.isEtapMontazuMechaniki();
                break;
            case 5:
                karta.wykonajEtapMontazuWnetrza();
                etapZakonczonyPowodzeniem = karta.isEtapMontazuWnetrza();
                break;
            case 6:
                karta.wykonajEtapMontazuElementowZewnetrznych();
                etapZakonczonyPowodzeniem = karta.isEtapMontazuElementowZewnetrznych();
                break;
            case 7:
                karta.wykonajEtapUzupelnieniaPlynow();
                etapZakonczonyPowodzeniem = karta.isEtapUzupelnieniaPlynow();
                break;
            case 8:
                karta.wykonajEtapTestowania();
                etapZakonczonyPowodzeniem = karta.isEtapTestowania();
                break;
        }
        return etapZakonczonyPowodzeniem;
    }

    private int zglosBrak()
    {
        return 1;
    }

    private boolean produkujSamochod()
    {
        var ilosEtapow = 8;
        var karta = new KartaPostepu();
        boolean etapZakonczonyPowodzeniem;
        for (var etap = 1; etap <= ilosEtapow; etap++)
        {
            do {
                etapZakonczonyPowodzeniem = kontrolujEtapProdukcji(karta, etap);
                if(!etapZakonczonyPowodzeniem)
                    System.out.println("Zlecam poprawę etapu produkcji...");
            } while (!etapZakonczonyPowodzeniem);
        }
        return true;
    }

    private boolean naprawReklamacje()
    {
        return false;
    }
}