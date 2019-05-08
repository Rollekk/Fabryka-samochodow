package z12.Produkcja;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import java.io.PrintWriter;
import java.net.Socket;


public class Zamowienie {

    private int nrZamowienia;
    private int wartoscZamowienia;
    private String dataZamowienia;
    private static int iloscZamowien;
    private boolean zamowienieGotowe;
    private Faktura faktura;
    private Platnosci platnosc;


    public Zamowienie(int wartoscZamowienia) throws IOException {
        Socket socket = new Socket("localhost", 9899);
         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        this.wartoscZamowienia = wartoscZamowienia;
        nrZamowienia = ++iloscZamowien;
        zamowienieGotowe = false;
        wybierzRodzajPlatnosci();

    }

    private void zlecPlatnosc(String rodzajPlatnosci, PrintWriter out){
        faktura = new Faktura(wartoscZamowienia, rodzajPlatnosci, dataZamowienia);
        platnosc = new Platnosci(wartoscZamowienia/5);
        System.out.println(rodzajPlatnosci);
        out.println(rodzajPlatnosci);
    }
private void wyslijDane(PrintWriter out)
{
    out.println("NUMER ZAMOWIENIA:"+nrZamowienia+" WARTOSC: "+wartoscZamowienia+" FAKTURA: "+faktura.idFaktury);

}
    public boolean isZamowienieGotowe(boolean czy) {
        return czy;
    }

    private void wybierzRodzajPlatnosci() throws IOException {
        var socket = new Socket("localhost", 9899);
        var out = new PrintWriter(socket.getOutputStream(), true);


        var rodzajPlatnosciWindow = new JFrame("Rodzaj Platnosci");
        rodzajPlatnosciWindow.getContentPane().add(new JLabel("Prosze wybrac rodzaj platnosci"), SwingConstants.CENTER);

        ButtonGroup btnGrp = new ButtonGroup();

        JRadioButtonMenuItem rbtnPrzelew = new JRadioButtonMenuItem("Przelew");
        JRadioButtonMenuItem rbtnGotowka = new JRadioButtonMenuItem("Gotowka");
        JRadioButtonMenuItem rbtnKarta = new JRadioButtonMenuItem("Karta");
        btnGrp.add(rbtnPrzelew);
        btnGrp.add(rbtnGotowka);
        btnGrp.add(rbtnKarta);

        JButton btnAccept = new JButton("Potwierdz");

        rodzajPlatnosciWindow.getContentPane().add(rbtnPrzelew);
        rodzajPlatnosciWindow.getContentPane().add(rbtnGotowka);
        rodzajPlatnosciWindow.getContentPane().add(rbtnKarta);
        rodzajPlatnosciWindow.getContentPane().add(btnAccept);

        btnAccept.addActionListener(e -> {

            String rodzajPlatnosci = null;
            if(rbtnPrzelew.isSelected()){
                rodzajPlatnosci = "PRZELEW";
            }else if(rbtnGotowka.isSelected()){
                rodzajPlatnosci = "GOTOWKA";
            }else if(rbtnKarta.isSelected()) {
                rodzajPlatnosci =  "KARTA";
            }

            rodzajPlatnosciWindow.dispose();
            zlecPlatnosc(rodzajPlatnosci,out);
            wyslijDane(out);

        });


        rodzajPlatnosciWindow.setSize(500, 500);
        rodzajPlatnosciWindow.setLayout(new GridLayout(5,1));
        rodzajPlatnosciWindow.setVisible(true);
        rodzajPlatnosciWindow.pack();

    }
}