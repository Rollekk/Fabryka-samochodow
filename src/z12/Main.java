package z12;



import z12.Client.LoginDialog;
import z12.Osoby.Klient;
import z12.Pracownik.LoginDialogP;
import z12.Client.Login;
import z12.Produkcja.KartaPostepu;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;

import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

import java.util.*;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Main {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Projekt?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "Zaq12wsx";
    static int iteracja=0;
    private static String nazwaKlienta = null;
    private static String nazwaPracownika = null;
    private static String received;



    public static int getIloscFaktur()
    {
        Connection conn;
        Statement stmt;
        String result = null;
        int ilosc = 0;
        try {

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql = "SELECT COUNT(*) FROM Faktura";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                ilosc = rs.getInt("count(*)");
            }

            System.out.println("Ilosc faktur:"+ilosc);

            rs.close();
            stmt.close();
        } catch (Exception se) {

            se.printStackTrace();
        }


        return ilosc;

    }

    public static int getIloscZamowien()
    {
        Connection conn;
        Statement stmt;
        String result = null;
        int ilosc = 0;
        try {

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            String sql = "SELECT COUNT(*) FROM Zamowienie";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                ilosc = rs.getInt("count(*)");
            }

            System.out.println("Ilosc zamowien:"+ilosc);

            rs.close();
            stmt.close();
        } catch (Exception se) {

            se.printStackTrace();
        }


        return ilosc;

    }


    static void setIteracja(int it)
    {
        iteracja=it;

    }

    static int getIteracja()
    {
        return iteracja;
    }


    public static void main(String[] args) throws Exception{

        Socket socket = new Socket("localhost", 9899);
        var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        var out = new PrintWriter(socket.getOutputStream(), true);
        Connection conn;

        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Random rndm = new Random();

        Klient klient = new Klient(rndm.nextInt(2), rndm.nextInt(100), rndm.nextInt(4000));

       Date date = new Date();
       SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy ");

        final JFrame frame = new JFrame("System zarządzania fabryką samochodów");

        final JLabel lbInformation = new JLabel("Projekt - Podstawy Inżynierii Programowania") ;
        final JLabel lbInformation2 = new JLabel("Kornaś, Kowalczyk, Krakowiak, Kowalewski") ;
        final JLabel data = new JLabel("Czas uruchomienia: " + Data.getDate()) ;

        final JLabel infoKlient = new JLabel("LOGOWANIE DLA KLIENTA:", SwingConstants.CENTER) ;
        final JButton btnLoginK = new JButton("Kliknij aby zalogować");
        final JButton btnNowaOferta = new JButton("Przegladaj oferty");
        final JButton btnZlozOferte = new JButton("Zloz oferte");
        final JButton btnSprawdzZamowienie = new JButton("Sprawdz stan zamowienia");
        final JButton btnLogoutK = new JButton("Kliknij aby wylogowac");

        final JLabel infoPracownik = new JLabel("LOGOWANIE DLA PRACOWNIKA:", SwingConstants.CENTER) ;
        final JButton btnLoginP = new JButton("Kliknij aby zalogować");
        final JButton btnEdytujOferta = new JButton("Edytuj oferty");
        final JButton btnFaktury = new JButton("Przegladaj faktury");
        final JButton btnZlecenia = new JButton("Wykonaj zlecenie");
        final JButton btnGotoweZlecenia = new JButton("Sprawdz gotowe zamowienia");
        final JButton btnPrzycisk = new JButton("PRZYCISK");
        final JButton btnLogoutP = new JButton("Kliknij aby wylogowac");


        LoginDialog loginDlg = new LoginDialog(frame);
        KartaPostepu karta = new KartaPostepu();

        btnZlozOferte.setEnabled(false);
        btnSprawdzZamowienie.setEnabled(false);
        btnLogoutK.setEnabled(false);

        btnEdytujOferta.setEnabled(false);
        btnFaktury.setEnabled(false);
        btnZlecenia.setEnabled(false);
        btnGotoweZlecenia.setEnabled(false);
        btnLogoutP.setEnabled(false);

        //Klient
        btnLogoutK.addActionListener(
                e -> {
                        btnLoginK.setText("Kliknij aby zalogować");
                        btnLoginK.setEnabled(true);
                        btnLogoutK.setEnabled(false);
                        btnNowaOferta.setEnabled(false);
                        btnZlozOferte.setEnabled(false);
                        btnSprawdzZamowienie.setEnabled(false);
                        btnLoginP.setEnabled(true);

                        System.out.println("Wylogowano klienta: " + nazwaKlienta);

                });
        btnLoginK.addActionListener(
                e -> {

                    loginDlg.setVisible(true);

                    if(loginDlg.isSucceeded()){
                        nazwaKlienta = loginDlg.getUsername();
                        btnLoginK.setText("Witaj " + nazwaKlienta  + "!");
                        btnLoginK.setEnabled(false);
                        btnLogoutK.setEnabled(true);
                        btnNowaOferta.setEnabled(true);
                        btnZlozOferte.setEnabled(true);
                        btnSprawdzZamowienie.setEnabled(true);
                        btnLoginP.setEnabled(false);

                        System.out.println("Zalogowano "+  nazwaKlienta);
                    }
                });

        btnNowaOferta.addActionListener(e -> {

            final JButton btnPrev= new JButton("Poprzedni");
            final JLabel labinformacja= new JLabel("");

            JButton btnExit = new JButton("Wyjdz");

            var path = "http://www.bhmpics.com/wallpapers/bmw_m2_coupe_m_performance_parts_2016-1366x768.jpg";
            URL url;
            BufferedImage image = null;
            try {
                url = new URL(path);
                image = ImageIO.read(url);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            image = Support.createResizedCopy(image, 480, 270);
            var imgLabel = new JLabel(new ImageIcon(image));
            var newOfertaWindow = new JFrame("Aktualna oferta");

            newOfertaWindow.setVisible(true);

            final JButton btnNext = new JButton("Następny");

            btnNext.addActionListener(f -> {

             out.println("nextgen");
             try {
                 received =  in.readLine();

             } catch (IOException e1) {
                 e1.printStackTrace();
             }
             labinformacja.setText(received);
             System.out.println(received);

            });

            btnPrev.addActionListener(g -> {
                  out.println("prevgen");
                  try {
                      received =  in.readLine();

                  } catch (IOException e1) {
                      e1.printStackTrace();
                  }
                  labinformacja.setText(received);
                  System.out.println(received);
            });

            final JButton btnPick = new JButton("Wybierz");
            btnPick.addActionListener(g -> {
                out.println("nextgen");
                try {
                    received =  in.readLine();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                newOfertaWindow.getContentPane().removeAll();
                newOfertaWindow.repaint();
                newOfertaWindow.setVisible(true);

                newOfertaWindow.getContentPane().add(new JLabel(received));

                newOfertaWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newOfertaWindow.setSize(800, 600);
                newOfertaWindow.setLayout(new GridLayout(7, 1));
                final JButton btnOrder = new JButton("Zamow");

                newOfertaWindow.getContentPane().add(btnOrder);
                newOfertaWindow.getContentPane().add(btnExit);

                btnExit.addActionListener(f ->{

                    newOfertaWindow.dispose();

                });
                if(loginDlg.isSucceeded()){
                    btnOrder.setEnabled(true);
                }else{
                    btnOrder.setEnabled(false);
                    JOptionPane.showMessageDialog(newOfertaWindow,
                            "Prosze sie zalogowac aby zlozyc zamowienie",
                            "Informacja",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                btnOrder.addActionListener(f ->{
                    try {
                        klient.zlozZamowienie();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    newOfertaWindow.dispose();
                });
            });

            newOfertaWindow.add(new JLabel("OFERTA AKTUALNA NA DZIEN: " + ft.format(date)));
            newOfertaWindow.add(new JLabel(""));

            btnExit.addActionListener(g -> {
                newOfertaWindow.dispose();
            });

            newOfertaWindow.getContentPane().add(labinformacja);
            newOfertaWindow.getContentPane().add(imgLabel);

            newOfertaWindow.getContentPane().add(btnPrev);
            newOfertaWindow.getContentPane().add(btnNext);

            newOfertaWindow.getContentPane().add(btnExit);
            newOfertaWindow.getContentPane().add(btnPick);

            newOfertaWindow.setSize(800, 500);
            newOfertaWindow.setLayout(new GridLayout(4,2));
            newOfertaWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newOfertaWindow.setAlwaysOnTop(true);
            newOfertaWindow.pack();
});
        btnZlozOferte.addActionListener(e->{

            ArrayList<String> Silniki = new ArrayList<>();
            Silniki.add("Wybierz...");
            ArrayList<String> Napedy = new ArrayList<>();
            Napedy.add("Wybierz...");
            ArrayList<String> Kola = new ArrayList<>();
            Kola.add("Wybierz...");

            Statement stmt = null;
            String result = null;



            try {
                stmt = conn.createStatement();
                int i=1;
                String sql = "SELECT * FROM Silnik";
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){

                    String rodzajSilnika = rs.getString("rodzajSilnika");
                    String nazwaKodowa = rs.getString("nazwaKodowa");
                    Silniki.add(rodzajSilnika + " " + nazwaKodowa);
                    i++;
                }

                rs.close();
                stmt.close();


            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                stmt = conn.createStatement();
                int i=1;
                String sql = "SELECT * FROM Naped";
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){

                    String rodzajSkrzyni = rs.getString("rodzajSkrzyni");
                    String iloscBiegow = rs.getString("iloscBiegow");
                    String rodzajSprzegla = rs.getString("rodzajSprzegla");
                    int sprzegloDwumasowe = rs.getInt("sprzegloDwumasowe");
                    int czy4na4 = rs.getInt("czy4na4");

                    if(sprzegloDwumasowe==0 & czy4na4==0)
                    {
                        Napedy.add(rodzajSkrzyni+" "+iloscBiegow+" biegow,sprzeglo "+rodzajSprzegla+" bez dwumasy i bez 4x4");
                    }
                   if(sprzegloDwumasowe==1 & czy4na4==0)
                   {
                       Napedy.add(rodzajSkrzyni+" "+iloscBiegow+" biegow,sprzeglo "+rodzajSprzegla+" z dwumasą i bez 4x4");

                   }
                    if(sprzegloDwumasowe==0 & czy4na4==1)
                    {
                        Napedy.add(rodzajSkrzyni+" "+iloscBiegow+" biegow,sprzeglo "+rodzajSprzegla+" bez dwumasy i z 4x4");

                    }
                    if(sprzegloDwumasowe==1 & czy4na4==1)
                    {
                        Napedy.add(rodzajSkrzyni+" "+iloscBiegow+" biegow,sprzeglo "+rodzajSprzegla+" z dwumasa i z 4x4");

                    }
                    i++;

                }

                rs.close();
                stmt.close();

            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            try {
                stmt = conn.createStatement();
                int i=1;
                String sql = "SELECT * FROM Kola";
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){

                    int wielkoscFelgi = rs.getInt("wielkoscFelgi");
                    String materialKol = rs.getString("materialKol");
                    int rozstawSrub = rs.getInt("rozstawSrub");
                    int iloscSrub = rs.getInt("iloscSrub");
                    int szerokoscOpony = rs.getInt("szerokoscOpony");
                    int wysokoscOpony = rs.getInt("wysokoscOpony");

                    i++;
                    Kola.add(materialKol+" "+wielkoscFelgi+"/"+szerokoscOpony+"/"+wysokoscOpony+" sruby: "+rozstawSrub+"x"+iloscSrub);

                }

                rs.close();
                stmt.close();

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            JFrame createOfertaWindow = new JFrame("Oferty");

            JButton btnOrder = new JButton("Zamow");
            JButton btnExit = new JButton("Wyjdz");
            JTextField txtUwagi = new JTextField(50);


            final JComboBox btnlistaSilnik=new JComboBox(Silniki.toArray());
            final JComboBox btnlistaNaped=new JComboBox(Napedy.toArray());
            final JComboBox btnlistaKola = new JComboBox(Kola.toArray());


            String[] Wyposazenie = {"Wybierz...", "Standard", "Premium"};
            JComboBox btnlistaWyposazenie = new JComboBox(Wyposazenie);
            String[] Dodatki = {"Wybierz...", "Podgrzewane siedzenia", "Dodatkowe naglosnienie", "Podgrzewana kierownica"};
            JComboBox btnLista = new JComboBox(Dodatki);

            createOfertaWindow.add(new JLabel("Prosze wybrac czesci: "));
            createOfertaWindow.add(new JLabel(""));
            createOfertaWindow.add(new JLabel("Silnik:"));
            createOfertaWindow.add(btnlistaSilnik);
            createOfertaWindow.add(new JLabel("Naped:"));
            createOfertaWindow.add(btnlistaNaped);
            createOfertaWindow.add(new JLabel("Kola:"));
            createOfertaWindow.add(btnlistaKola);
            createOfertaWindow.add(new JLabel("Wyposazenie:"));
            createOfertaWindow.add(btnlistaWyposazenie);
            createOfertaWindow.add(new JLabel("Dodatki: "));
            createOfertaWindow.add(btnLista);
            createOfertaWindow.add(new JLabel("Dodatkowe informacje: "));
            createOfertaWindow.add(txtUwagi);
            createOfertaWindow.add(btnExit);
            createOfertaWindow.add(btnOrder);
            createOfertaWindow.setVisible(true);
            createOfertaWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            createOfertaWindow.setSize(400, 300);
            createOfertaWindow.setLayout(new GridLayout(8, 2));

            btnOrder.addActionListener(g -> {

                if(btnlistaSilnik.getSelectedIndex() == 0 || btnlistaNaped.getSelectedIndex() == 0 || btnlistaKola.getSelectedIndex() == 0 || btnlistaWyposazenie.getSelectedIndex() == 0){
                    JOptionPane.showMessageDialog(createOfertaWindow,
                            "Nie wybrano wszystkich elementow",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }else {
                    String wybranySilnik = (String) btnlistaSilnik.getSelectedItem();
                    String wybranyNaped = (String) btnlistaNaped.getSelectedItem();
                    String wybraneKola = (String) btnlistaKola.getSelectedItem();
                    String wybraneWyposazenie = (String) btnlistaWyposazenie.getSelectedItem();
                    String uwagi = txtUwagi.getText();
                    System.out.println("Silnik: " + wybranySilnik + " \nNaped: " + wybranyNaped + " \nKola: " + wybraneKola + " \nWyposazenie: " + wybraneWyposazenie + "\nUwagi: " + uwagi);
                    int ilosc=0;
                    try {

                         final Statement stm = conn.createStatement();

                        String sql = "SELECT * FROM Samochod";
                        ResultSet rs = stm.executeQuery(sql);
                        while(rs.next()){

                            ilosc++;
                        }

                        rs.close();
                        stm.close();


                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    try{
                        ilosc=ilosc+1;
                        final Statement st = conn.createStatement();
                        String sql = "INSERT INTO Samochod (idSamochodu, Silnik, Naped, Kola, Wyposazenie, Uwagi)"
                                + " VALUES ("+ilosc+",'"+wybranySilnik+"','"+wybranyNaped+"','"+wybraneKola+"','"+wybraneWyposazenie+"','"+uwagi+"')";
                       st.executeUpdate(sql);

                    }
                    catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    createOfertaWindow.dispose();
                }
            });

            btnExit.addActionListener(g -> {
                createOfertaWindow.dispose();
            });
        });
        btnSprawdzZamowienie.addActionListener(e -> {

            JFrame checkZamowienieWindow = new JFrame("Karta postepu");
            JTextField txtNumer = new JTextField(10);

            JButton btnAnuluj = new JButton("Anuluj");
            JButton btnSzukaj = new JButton("Szukaj");

            checkZamowienieWindow.getContentPane().add(new JLabel("Prosze podac numer zamowienia:"));
            checkZamowienieWindow.getContentPane().add(txtNumer);
            checkZamowienieWindow.getContentPane().add(btnSzukaj);
            checkZamowienieWindow.getContentPane().add(btnAnuluj);

            checkZamowienieWindow.setVisible(true);
            checkZamowienieWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            checkZamowienieWindow.setSize(300, 150);
            checkZamowienieWindow.setLayout(new GridLayout(4, 1));

            btnAnuluj.addActionListener(f ->{
                checkZamowienieWindow.dispose();
            });

            btnSzukaj.addActionListener(g ->{

                if(txtNumer.getText().isEmpty()){
                    JOptionPane.showMessageDialog(checkZamowienieWindow,
                            "Nic nie podano",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }else {

                    int nrZamowienia = Integer.parseInt(txtNumer.getText());
                    JButton btnWyjdz = new JButton("Wyjdz");
                    boolean czyJest = false;
                    Statement stmt;

                    try {
                        stmt = conn.createStatement();

                        String sql = "SELECT * FROM Zamowienie WHERE idZamowienia=" + nrZamowienia;
                        ResultSet rs = stmt.executeQuery(sql);
                        while (rs.next()) {
                            czyJest = true;
                            int idZamowienia = rs.getInt("idZamowienia");
                            int wartoscZamowienia = rs.getInt("wartoscZamowienia");
                            String dataZamowienia = rs.getString("dataZamowienia");
                            int czyGotoweKaro = rs.getInt("czyGotoweKaro");
                            int czyGotoweLakier = rs.getInt("czyGotoweLakier");
                            int czyGotoweMecha = rs.getInt("czyGotoweMecha");
                            int czyGotoweWnetrz = rs.getInt("czyGotoweWnetrz");
                            int czyGotoweZewn = rs.getInt("czyGotoweZewn");
                            int czyGotowePlyny = rs.getInt("czyGotowePlyny");
                            int czyGotoweTesty = rs.getInt("czyGotoweTesty");


                            checkZamowienieWindow.getContentPane().removeAll();
                            checkZamowienieWindow.repaint();

                            checkZamowienieWindow.getContentPane().add(new JLabel("ID zamowienia: " + idZamowienia));
                            checkZamowienieWindow.getContentPane().add(new JLabel("Wartosc zamowienia: " + wartoscZamowienia + " PLN"));
                            checkZamowienieWindow.getContentPane().add(new JLabel("Data zamowienia: " + dataZamowienia));
                            checkZamowienieWindow.getContentPane().add(new JLabel(""));
                            checkZamowienieWindow.getContentPane().add(new JLabel("Etap Karoserii: "));

                            if (czyGotoweKaro == 1) {
                                JLabel rdy = new JLabel("Gotowe");
                                rdy.setForeground(Color.green);

                                checkZamowienieWindow.getContentPane().add(rdy);
                            } else {
                                JLabel notRdy = new JLabel("W toku...");
                                notRdy.setForeground(Color.red);

                                checkZamowienieWindow.getContentPane().add(notRdy);
                            }

                            checkZamowienieWindow.getContentPane().add(new JLabel("Etap Lakierowania: "));

                            if (czyGotoweLakier == 1) {
                                JLabel rdy = new JLabel("Gotowe");
                                rdy.setForeground(Color.green);

                                checkZamowienieWindow.getContentPane().add(rdy);
                            } else {
                                JLabel notRdy = new JLabel("W toku...");
                                notRdy.setForeground(Color.red);

                                checkZamowienieWindow.getContentPane().add(notRdy);
                            }

                            checkZamowienieWindow.getContentPane().add(new JLabel("Etap Montazu Mechaniki: "));

                            if (czyGotoweMecha == 1) {
                                JLabel rdy = new JLabel("Gotowe");
                                rdy.setForeground(Color.green);

                                checkZamowienieWindow.getContentPane().add(rdy);
                            } else {
                                JLabel notRdy = new JLabel("W toku...");
                                notRdy.setForeground(Color.red);

                                checkZamowienieWindow.getContentPane().add(notRdy);
                            }

                            checkZamowienieWindow.getContentPane().add(new JLabel("Etap Montazu Wnetrza: "));

                            if (czyGotoweWnetrz == 1) {
                                JLabel rdy = new JLabel("Gotowe");
                                rdy.setForeground(Color.green);

                                checkZamowienieWindow.getContentPane().add(rdy);
                            } else {
                                JLabel notRdy = new JLabel("W toku...");
                                notRdy.setForeground(Color.red);

                                checkZamowienieWindow.getContentPane().add(notRdy);
                            }

                            checkZamowienieWindow.getContentPane().add(new JLabel("Etap Montazu Elementow Zewnetrznych:"));

                            if (czyGotoweZewn == 1) {
                                JLabel rdy = new JLabel("Gotowe");
                                rdy.setForeground(Color.green);

                                checkZamowienieWindow.getContentPane().add(rdy);
                            } else {
                                JLabel notRdy = new JLabel("W toku...");
                                notRdy.setForeground(Color.red);

                                checkZamowienieWindow.getContentPane().add(notRdy);
                            }

                            checkZamowienieWindow.getContentPane().add(new JLabel("Etap Uzupelnienia Plynow:"));

                            if (czyGotowePlyny == 1) {
                                JLabel rdy = new JLabel("Gotowe");
                                rdy.setForeground(Color.green);

                                checkZamowienieWindow.getContentPane().add(rdy);
                            } else {
                                JLabel notRdy = new JLabel("W toku...");
                                notRdy.setForeground(Color.red);

                                checkZamowienieWindow.getContentPane().add(notRdy);
                            }

                            checkZamowienieWindow.getContentPane().add(new JLabel("Etap Testowania: "));

                            if (czyGotoweTesty == 1) {
                                JLabel rdy = new JLabel("Gotowe");
                                rdy.setForeground(Color.green);

                                checkZamowienieWindow.getContentPane().add(rdy);
                            } else {
                                JLabel notRdy = new JLabel("W toku...");
                                notRdy.setForeground(Color.red);

                                checkZamowienieWindow.getContentPane().add(notRdy);
                            }


                            btnWyjdz.addActionListener(f -> {
                                checkZamowienieWindow.dispose();
                            });

                            checkZamowienieWindow.getContentPane().add(new JLabel(""));
                            checkZamowienieWindow.getContentPane().add(btnWyjdz);
                            checkZamowienieWindow.setVisible(true);
                            checkZamowienieWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            checkZamowienieWindow.setSize(500, 400);
                            checkZamowienieWindow.setLayout(new GridLayout(10, 2));

                        }
                        if (czyJest == false) {
                            JOptionPane.showMessageDialog(checkZamowienieWindow,
                                    "Nie ma takiego zamowienia",
                                    "Informacja",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }

                        rs.close();
                        stmt.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });

        });
        //Pracownik
        btnLogoutP.addActionListener(
                e -> {
                    btnLoginP.setText("Kliknij aby zalogować");
                    btnLoginP.setEnabled(true);
                    btnLoginK.setEnabled(true);
                    btnEdytujOferta.setEnabled(false);
                    btnFaktury.setEnabled(false);
                    btnZlecenia.setEnabled(false);
                    btnGotoweZlecenia.setEnabled(false);
                    btnLogoutP.setEnabled(false);
                    System.out.println("Wylogowano pracownika: "+ nazwaPracownika);

                });
        btnLoginP.addActionListener(
                e -> {
                    LoginDialogP loginDlgP = new LoginDialogP(frame);
                    loginDlgP.setVisible(true);

                    if(loginDlgP.isSucceeded()){
                        nazwaPracownika = loginDlgP.getUsername();
                        btnLoginP.setText("Witaj " + nazwaPracownika  + "!");
                        System.out.println("Zalogowano na pracownika: "+  nazwaPracownika);
                        btnLoginP.setEnabled(false);
                        btnLoginK.setEnabled(false);
                        btnEdytujOferta.setEnabled(true);
                        btnFaktury.setEnabled(true);
                        btnZlecenia.setEnabled(true);
                        btnGotoweZlecenia.setEnabled(true);
                        btnLogoutP.setEnabled(true);
                    }
                });

        btnEdytujOferta.addActionListener(e -> {

            JFrame editOfertaWindow = new JFrame("Oferty");
            final JButton btnDalej= new JButton("Dalej");
            JRadioButton btnOferta1 = new JRadioButton(("Oferta #1"));
            JRadioButton btnOferta2 = new JRadioButton(("Oferta #2"));
            JRadioButton btnOferta3 = new JRadioButton(("Oferta #3"));
            JRadioButton btnOferta4 = new JRadioButton(("Oferta #4"));

            editOfertaWindow.add(new JLabel("AKTUALNE OFERTY NA DZIEN: " + ft.format(date), SwingConstants.CENTER));
            ButtonGroup btnGrp = new ButtonGroup();

            btnGrp.add(btnOferta1);
            btnGrp.add(btnOferta2);
            btnGrp.add(btnOferta3);
            btnGrp.add(btnOferta4);

            editOfertaWindow.getContentPane().add(btnOferta1);
            editOfertaWindow.getContentPane().add(btnOferta2);
            editOfertaWindow.getContentPane().add(btnOferta3);
            editOfertaWindow.getContentPane().add(btnOferta4);
            editOfertaWindow.getContentPane().add(btnDalej);

            editOfertaWindow.setVisible(true);
            editOfertaWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            editOfertaWindow.setSize(800, 600);
            editOfertaWindow.setLayout(new GridLayout(6,1));


            JButton btnSave = new JButton("Zapisz");
            JButton btnExit = new JButton("Wyjdz");
            btnExit.addActionListener(g -> {
                editOfertaWindow.dispose();
            });

            String[] Silniki = {"Spalinowy (iskrowy)", "Spalinowy (samoczynny)", "Elektryczny"};
            JComboBox btnlistaSilnik = new JComboBox(Silniki);
            String[] Napedy = {"4x4", "Przod", "Tyl"};
            JComboBox btnlistaNaped = new JComboBox(Napedy);
            String[] Kola = {"Terenowe", "Miejskie"};
            JComboBox btnlistaKola = new JComboBox(Kola);
            String[] Wyposeznie = {"Standard", "Premium"};
            JComboBox btnlistaWyposazenie = new JComboBox(Wyposeznie);

                  btnDalej.addActionListener(f -> {
                      if(btnOferta1.isSelected()){

                          editOfertaWindow.getContentPane().removeAll();
                          editOfertaWindow.repaint();
                          editOfertaWindow.setVisible(true);
                          editOfertaWindow.add(new JLabel("Wybrano Oferte #1"));
                          editOfertaWindow.add(new JLabel("Samochod #1"));
                          editOfertaWindow.add(new JLabel("Silnik:"));
                          editOfertaWindow.add(btnlistaSilnik);
                          editOfertaWindow.add(new JLabel("Naped:"));
                          editOfertaWindow.add(btnlistaNaped);
                          editOfertaWindow.add(new JLabel("Kola:"));
                          editOfertaWindow.add(btnlistaKola);
                          editOfertaWindow.add(new JLabel("Wyposazenie:"));
                          editOfertaWindow.add(btnlistaWyposazenie);
                          editOfertaWindow.add(btnExit);
                          editOfertaWindow.add(btnSave);
                          editOfertaWindow.setSize(400, 300);
                          editOfertaWindow.setLayout(new GridLayout(6, 2));

                      }else if(btnOferta2.isSelected()){

                          editOfertaWindow.getContentPane().removeAll();
                          editOfertaWindow.repaint();
                          editOfertaWindow.setVisible(true);
                          editOfertaWindow.add(new JLabel("Wybrano Oferte #2"));
                          editOfertaWindow.add(new JLabel("Samochod #2"));
                          editOfertaWindow.add(new JLabel("Silnik:"));
                          editOfertaWindow.add(btnlistaSilnik);
                          editOfertaWindow.add(new JLabel("Naped:"));
                          editOfertaWindow.add(btnlistaNaped);
                          editOfertaWindow.add(new JLabel("Kola:"));
                          editOfertaWindow.add(btnlistaKola);
                          editOfertaWindow.add(new JLabel("Wyposazenie:"));
                          editOfertaWindow.add(btnlistaWyposazenie);
                          editOfertaWindow.add(btnExit);
                          editOfertaWindow.add(btnSave);
                          editOfertaWindow.setSize(400, 300);
                          editOfertaWindow.setLayout(new GridLayout(6, 2));

                      }else if(btnOferta3.isSelected()){

                          editOfertaWindow.getContentPane().removeAll();
                          editOfertaWindow.repaint();
                          editOfertaWindow.setVisible(true);
                          editOfertaWindow.add(new JLabel("Wybrano Oferte #3"));
                          editOfertaWindow.add(new JLabel("Samochod #3"));
                          editOfertaWindow.add(new JLabel("Silnik:"));
                          editOfertaWindow.add(btnlistaSilnik);
                          editOfertaWindow.add(new JLabel("Naped:"));
                          editOfertaWindow.add(btnlistaNaped);
                          editOfertaWindow.add(new JLabel("Kola:"));
                          editOfertaWindow.add(btnlistaKola);
                          editOfertaWindow.add(new JLabel("Wyposazenie:"));
                          editOfertaWindow.add(btnlistaWyposazenie);
                          editOfertaWindow.add(btnExit);
                          editOfertaWindow.add(btnSave);
                          editOfertaWindow.setSize(400, 300);
                          editOfertaWindow.setLayout(new GridLayout(6, 2));

                      }else if(btnOferta4.isSelected()){

                          editOfertaWindow.getContentPane().removeAll();
                          editOfertaWindow.repaint();
                          editOfertaWindow.setVisible(true);
                          editOfertaWindow.add(new JLabel("Wybrano Oferte #4"));
                          editOfertaWindow.add(new JLabel("Samochod #4"));
                          editOfertaWindow.add(new JLabel("Silnik:"));
                          editOfertaWindow.add(btnlistaSilnik);
                          editOfertaWindow.add(new JLabel("Naped:"));
                          editOfertaWindow.add(btnlistaNaped);
                          editOfertaWindow.add(new JLabel("Kola:"));
                          editOfertaWindow.add(btnlistaKola);
                          editOfertaWindow.add(new JLabel("Wyposazenie:"));
                          editOfertaWindow.add(btnlistaWyposazenie);
                          editOfertaWindow.add(btnExit);
                          editOfertaWindow.add(btnSave);
                          editOfertaWindow.setSize(400, 300);
                          editOfertaWindow.setLayout(new GridLayout(6, 2));

                      }

                      btnSave.addActionListener(g -> {
                          String wybranySilnik = (String) btnlistaSilnik.getSelectedItem();
                          String wybranyNaped = (String) btnlistaNaped.getSelectedItem();
                          String wybraneKola = (String) btnlistaKola.getSelectedItem();
                          String wybraneWyposazenie = (String) btnlistaWyposazenie.getSelectedItem();
                          System.out.println("Silnik: " + wybranySilnik + " Naped: " + wybranyNaped + " Kola: " + wybraneKola + " Wyposazenie: "+ wybraneWyposazenie);
                          editOfertaWindow.dispose();

                      });


                  });

            editOfertaWindow.pack();
        });

        btnFaktury.addActionListener(e -> {

            int ilosc = 0;
            List listaID= new ArrayList() ;
            List cenaID= new ArrayList() ;
            List platnosc= new ArrayList() ;
            List rodzaj= new ArrayList() ;

            final JLabel labinformacja = new JLabel("");
            final JButton btnExit = new JButton("Wyjdz");

            var path = "https://www.mf.gov.pl/image/journal/article?img_id=6606460&t=1541686708517";
            URL url;
            BufferedImage image = null;
            try {
                url = new URL(path);
                image = ImageIO.read(url);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            image = Support.createResizedCopy(image, 480, 270);
            var imgLabel = new JLabel(new ImageIcon(image));
            var fakturyWindow = new JFrame("Faktury");

            Statement stmt = null;
            String result = null;
            try {
                stmt = conn.createStatement();

                String sql = "SELECT * FROM Faktura";
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    ilosc++;
                    int idFaktury = rs.getInt("idFaktury");
                    int cenanafak = rs.getInt("cena");
                    String rodzajPlatnosci = rs.getString("rodzajPlatnosci");
                    String rodzajFaktury = rs.getString("rodzajFaktury");
                    listaID.add(idFaktury);
                    cenaID.add(cenanafak);
                    platnosc.add(rodzajPlatnosci);
                    rodzaj.add(rodzajFaktury);

                }

                rs.close();
                stmt.close();

            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            JLabel infoID= new JLabel("ID Faktury:");
            JLabel infoCena= new JLabel("Cena na fakturze: ");
            JLabel infoPlatnosc= new JLabel("Rodzaj platnosci: ");
            JLabel infoFaktura= new JLabel("Rodzaj faktury: ");

            fakturyWindow.pack();
            fakturyWindow.setVisible(true);

            infoID.setText("ID Faktury: " + String.valueOf(listaID.get(iteracja)));
            infoCena.setText("Cena na fakturze: " + String.valueOf(cenaID.get(iteracja)));
            infoPlatnosc.setText("Rodzaj platnosci: "+ String.valueOf(platnosc.get(iteracja)));
            infoFaktura.setText("Rodzaj faktury: " + String.valueOf(rodzaj.get(iteracja)));

            final JButton btnNext = new JButton("Następna");

            int finalIlosc = ilosc;
            btnNext.addActionListener(f -> {

                int temp;
                temp=getIteracja();
                temp++;
                setIteracja(temp);
                if(temp == finalIlosc)
                {
                    setIteracja(0);
                }
                infoID.setText("Id Faktury: "+String.valueOf(listaID.get(iteracja)));
                infoCena.setText("Cena na fakturze: "+String.valueOf(cenaID.get(iteracja)));
                infoPlatnosc.setText("Rodzaj platnosci: "+String.valueOf(platnosc.get(iteracja)));
                infoFaktura.setText("Rodzaj faktury: "+String.valueOf(rodzaj.get(iteracja)));

            } );
            btnExit.addActionListener(g -> {
                fakturyWindow.dispose();
            });

            fakturyWindow.add(new JLabel("Faktury na dzien: " + ft.format(date)));
            fakturyWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            fakturyWindow.setSize(400, 300);
            fakturyWindow.setLayout(new GridLayout(4,2));
            fakturyWindow.setAlwaysOnTop(true);

            fakturyWindow.getContentPane().add(labinformacja);
            fakturyWindow.getContentPane().add(infoID);
            fakturyWindow.getContentPane().add(infoCena);
            fakturyWindow.getContentPane().add(infoFaktura);
            fakturyWindow.getContentPane().add(infoPlatnosc);

            fakturyWindow.getContentPane().add(btnExit);
            fakturyWindow.getContentPane().add(btnNext);



            fakturyWindow.pack();
        });

        btnZlecenia.addActionListener(e->{

            JFrame zleceniaWindow = new JFrame("Zlecenia");

            JRadioButton btnKaroseria = new JRadioButton(("Zlec wykonanie karoserii"));
            JRadioButton btnLakierowanie = new JRadioButton(("Zlec lakierowanie"));
            JRadioButton btnMechanika = new JRadioButton(("Zlec montaz mechaniki"));
            JRadioButton btnWnetrze = new JRadioButton(("Zlec montaz wnetrza"));
            JRadioButton btnZewnatrz = new JRadioButton(("Zlec montaz elementow zewnetrznych"));
            JRadioButton btnPlyny = new JRadioButton(("Zlec uzupelnienie plynow"));
            JRadioButton btnTesty = new JRadioButton(("Zlec testowanie auta"));

            final ButtonGroup btnGrp = new ButtonGroup();
            final JButton btnDalej = new JButton("Wybierz");
            final JButton btnSzukaj = new JButton("Szukaj");
            final JButton btnAnuluj = new JButton("Anuluj");

            JTextField txtNumer = new JTextField(10);

            zleceniaWindow.getContentPane().add(new JLabel("Prosze podac numer zamowienia:"));
            zleceniaWindow.getContentPane().add(txtNumer);
            zleceniaWindow.getContentPane().add(btnSzukaj);
            zleceniaWindow.getContentPane().add(btnAnuluj);

            zleceniaWindow.setVisible(true);
            zleceniaWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            zleceniaWindow.setSize(300, 150);
            zleceniaWindow.setLayout(new GridLayout(4, 1));

            btnAnuluj.addActionListener(f ->{
                zleceniaWindow.dispose();
            });

            btnSzukaj.addActionListener(f->{

                if(txtNumer.getText().isEmpty()){
                    JOptionPane.showMessageDialog(zleceniaWindow,
                            "Nic nie podano",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }else {
                    int nrZamowienia = Integer.parseInt(txtNumer.getText());

                    boolean czyJest = false;

                    Statement stmt;
                    try {
                        stmt = conn.createStatement();

                        String sql = "SELECT * FROM Zamowienie WHERE idZamowienia=" + nrZamowienia;
                        ResultSet rs = stmt.executeQuery(sql);
                        while (rs.next()) {

                            zleceniaWindow.getContentPane().removeAll();
                            zleceniaWindow.repaint();

                            btnGrp.add(btnKaroseria);
                            btnGrp.add(btnLakierowanie);
                            btnGrp.add(btnMechanika);
                            btnGrp.add(btnWnetrze);
                            btnGrp.add(btnZewnatrz);
                            btnGrp.add(btnPlyny);
                            btnGrp.add(btnTesty);

                            zleceniaWindow.getContentPane().add(new JLabel("Prosze wybrac zlecenie do wykonania: "));
                            zleceniaWindow.getContentPane().add(btnKaroseria);
                            zleceniaWindow.getContentPane().add(btnLakierowanie);
                            zleceniaWindow.getContentPane().add(btnMechanika);
                            zleceniaWindow.getContentPane().add(btnWnetrze);
                            zleceniaWindow.getContentPane().add(btnZewnatrz);
                            zleceniaWindow.getContentPane().add(btnPlyny);
                            zleceniaWindow.getContentPane().add(btnTesty);
                            zleceniaWindow.getContentPane().add(btnDalej);

                            zleceniaWindow.setVisible(true);
                            zleceniaWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            zleceniaWindow.setSize(800, 600);
                            zleceniaWindow.setLayout(new GridLayout(9, 1));

                            czyJest = true;
                            btnDalej.addActionListener(g -> {

                                if (btnKaroseria.isSelected()) {

                                    karta.wykonajEtapKaroserii();
                                    String sqll = "UPDATE Zamowienie SET czyGotoweKaro = 1 WHERE idZamowienia=" + nrZamowienia;

                                    try {
                                        stmt.executeUpdate(sqll);
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }

                                    JOptionPane.showMessageDialog(zleceniaWindow,
                                            "Zlecenie zostalo wyslane...",
                                            "Informacja",
                                            JOptionPane.INFORMATION_MESSAGE);

                                    zleceniaWindow.dispose();

                                } else if (btnLakierowanie.isSelected()) {

                                    karta.wykonajEtapLakierowania();
                                    String sqll = "UPDATE Zamowienie SET czyGotoweLakier = 1 WHERE idZamowienia=" + nrZamowienia;
                                    try {
                                        stmt.executeUpdate(sqll);
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }

                                    JOptionPane.showMessageDialog(zleceniaWindow,
                                            "Zlecenie zostalo wyslane...",
                                            "Informacja",
                                            JOptionPane.INFORMATION_MESSAGE);

                                    zleceniaWindow.dispose();

                                } else if (btnMechanika.isSelected()) {

                                    karta.wykonajEtapMontazuMechaniki();
                                    String sqll = "UPDATE Zamowienie SET czyGotoweMecha = 1 WHERE idZamowienia=" + nrZamowienia;

                                    try {
                                        stmt.executeUpdate(sqll);
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }

                                    JOptionPane.showMessageDialog(zleceniaWindow,
                                            "Zlecenie zostalo wyslane...",
                                            "Informacja",
                                            JOptionPane.INFORMATION_MESSAGE);

                                    zleceniaWindow.dispose();

                                } else if (btnWnetrze.isSelected()) {

                                    karta.wykonajEtapMontazuWnetrza();
                                    String sqll = "UPDATE Zamowienie SET czyGotoweWnetrz = 1 WHERE idZamowienia=" + nrZamowienia;

                                    try {
                                        stmt.executeUpdate(sqll);
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }

                                    JOptionPane.showMessageDialog(zleceniaWindow,
                                            "Zlecenie zostalo wyslane...",
                                            "Informacja",
                                            JOptionPane.INFORMATION_MESSAGE);

                                    zleceniaWindow.dispose();

                                } else if (btnZewnatrz.isSelected()) {

                                    karta.wykonajEtapMontazuElementowZewnetrznych();
                                    String sqll = "UPDATE Zamowienie SET czyGotoweZewn = 1 WHERE idZamowienia=" + nrZamowienia;

                                    try {
                                        stmt.executeUpdate(sqll);
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }

                                    JOptionPane.showMessageDialog(zleceniaWindow,
                                            "Zlecenie zostalo wyslane...",
                                            "Informacja",
                                            JOptionPane.INFORMATION_MESSAGE);

                                    zleceniaWindow.dispose();

                                } else if (btnPlyny.isSelected()) {

                                    karta.wykonajEtapUzupelnieniaPlynow();
                                    String sqll = "UPDATE Zamowienie SET czyGotowePlyny = 1 WHERE idZamowienia=" + nrZamowienia;

                                    try {
                                        stmt.executeUpdate(sqll);
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }

                                    JOptionPane.showMessageDialog(zleceniaWindow,
                                            "Zlecenie zostalo wyslane...",
                                            "Informacja",
                                            JOptionPane.INFORMATION_MESSAGE);

                                    zleceniaWindow.dispose();

                                } else if (btnTesty.isSelected()) {

                                    karta.wykonajEtapTestowania();
                                    String sqll = "UPDATE Zamowienie SET czyGotoweTesty = 1 WHERE idZamowienia=" + nrZamowienia;

                                    try {
                                        stmt.executeUpdate(sqll);
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }

                                    JOptionPane.showMessageDialog(zleceniaWindow,
                                            "Zlecenie zostalo wyslane...",
                                            "Informacja",
                                            JOptionPane.INFORMATION_MESSAGE);

                                    zleceniaWindow.dispose();

                                } else {
                                    JOptionPane.showMessageDialog(zleceniaWindow,
                                            "Nie zaznaczono zadnej opcji",
                                            "ERROR",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                                    String sqlGotowe = "UPDATE Zamowienie SET czyGotowe = 1 WHERE czyGotoweLakier = 1 AND czyGotoweMecha = 1 AND czyGotoweWnetrz = 1 AND czyGotoweZewn = 1 AND czyGotowePlyny = 1 AND czyGotoweTesty = 1 AND czyGotoweKaro =1";
                                    try {
                                        stmt.executeUpdate(sqlGotowe);
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }
                            });
                        }
                        if (czyJest == false) {
                            JOptionPane.showMessageDialog(zleceniaWindow,
                                    "Nie ma takiego zamowienia",
                                    "Informacja",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });

        });

        btnGotoweZlecenia.addActionListener(e -> {

            JFrame gotoweZleceniaWindow = new JFrame("Gotowe Zlecenia");
            JButton btnNext = new JButton("Wybierz");
            JButton btnExit = new JButton("Wyjdz");
            JButton btnDone = new JButton("Zakoncz zamowienie");
            JButton btnCancel = new JButton("Anuluj");

            btnCancel.addActionListener(f->{
                gotoweZleceniaWindow.dispose();
            });
            btnExit.addActionListener(f->{
                gotoweZleceniaWindow.dispose();
            });


            JTable tabela = new JTable();
            DefaultTableModel dtm = new DefaultTableModel(0, 0);
            JScrollPane scrollPane = new JScrollPane(tabela);

            boolean czyJest = false;
            Statement stmt;

            try {
                stmt = conn.createStatement();
                String sql = "SELECT * FROM Zamowienie WHERE czyGotowe = 1";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {

                    czyJest = true;
                    int idZamowienia = rs.getInt("idZamowienia");
                    int wartoscZamowienia = rs.getInt("wartoscZamowienia");
                    String dataZamowienia = rs.getString("dataZamowienia");
                    int idFaktury = rs.getInt("idFak");
                    int idSamochodu = rs.getInt("idSamochodu");


                    String[] nazwyKolumn = {"Numer zamowienia", "Data zamowienia", "Cena"};


                    dtm.setColumnIdentifiers(nazwyKolumn);
                    tabela.setModel(dtm);
                    dtm.addRow(new Object[]{idZamowienia, dataZamowienia, wartoscZamowienia});
                    tabela.setLayout(new FlowLayout());
                    tabela.setPreferredScrollableViewportSize(new Dimension(500, 500));
                    tabela.setFillsViewportHeight(true);
                    gotoweZleceniaWindow.add(scrollPane);

                    btnNext.addActionListener(f-> {

                        Statement statmt;
                        int column = 0;
                        int row = tabela.getSelectedRow();

                        if(tabela.isRowSelected(row) == false){
                            JOptionPane.showMessageDialog(gotoweZleceniaWindow,
                                    "Nic nie zaznaczono",
                                    "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                        }else {

                            JTable tabelaZamowienie = new JTable();
                            JTable tabelaFaktura = new JTable();
                            JTable tabelaSamochod = new JTable();
                            DefaultTableModel dtmZamowienie = new DefaultTableModel(0, 0);
                            DefaultTableModel dtmFaktura = new DefaultTableModel(0, 0);
                            DefaultTableModel dtmSamochod = new DefaultTableModel(0, 0);

                            JScrollPane scrollPaneZamowienie = new JScrollPane(tabelaZamowienie);
                            JScrollPane scrollPaneFaktura = new JScrollPane(tabelaFaktura);
                            JScrollPane scrollPaneSamochod = new JScrollPane(tabelaSamochod);

                            int ddata = (int) tabela.getValueAt(column, row);

                            try {
                                statmt = conn.createStatement();
                                String sqlZamowienie = "SELECT * FROM Zamowienie WHERE idZamowienia=" + ddata;
                                statmt.executeQuery(sqlZamowienie);

                                String[] nazwyKolumnZamowienie = {"Numer zamowienia", "Data zamowienia", "Cena", "Numer Faktury", "ID Samochodu"};

                                dtmZamowienie.setColumnIdentifiers(nazwyKolumnZamowienie);
                                tabelaZamowienie.setModel(dtmZamowienie);
                                dtmZamowienie.addRow(new Object[]{idZamowienia, dataZamowienia, wartoscZamowienia, idFaktury, idSamochodu});
                                tabelaZamowienie.setLayout(new FlowLayout());
                                tabelaZamowienie.setPreferredScrollableViewportSize(new Dimension(100, 100));
                                tabelaZamowienie.setFillsViewportHeight(true);

                                String sqlFaktura = "SELECT * FROM Faktura WHERE idFaktury=" + idFaktury;
                                ResultSet res  = statmt.executeQuery(sqlFaktura);
                                while (res.next()) {
                                    String rodzajFaktury = res.getString("rodzajFaktury");
                                    String rodzajPlatnosci = res.getString("rodzajPlatnosci");
                                    String[] nazwyKolumnFaktura = {"Numer Faktury", "Rodzaj Faktury", "rodzajPlatnosci"};

                                    dtmFaktura.setColumnIdentifiers(nazwyKolumnFaktura);
                                    tabelaFaktura.setModel(dtmFaktura);
                                    dtmFaktura.addRow(new Object[]{idFaktury, rodzajFaktury, rodzajPlatnosci});
                                    tabelaFaktura.setLayout(new FlowLayout());
                                    tabelaFaktura.setPreferredScrollableViewportSize(new Dimension(100, 100));
                                    tabelaFaktura.setFillsViewportHeight(true);
                                }
                                String sqlSamochod = "SELECT * FROM Samochod WHERE idSamochodu=" + idSamochodu;
                                ResultSet ress = statmt.executeQuery(sqlSamochod);
                                while (ress.next()) {

                                    String Kola = ress.getString("Kola");
                                    String Naped = ress.getString("Naped");
                                    String Silnik = ress.getString("Silnik");
                                    String Uwagi = ress.getString("Uwagi");
                                    String Wyposazenie = ress.getString("Wyposazenie");

                                    String[] nazwyKolumnSamochod = {"ID Samochodu", "Silnik", "Naped", "Kola", "Wyposazenie", "Uwagi"};

                                    dtmSamochod.setColumnIdentifiers(nazwyKolumnSamochod);
                                    tabelaSamochod.setModel(dtmSamochod);
                                    dtmSamochod.addRow(new Object[]{idSamochodu, Silnik, Naped, Kola, Wyposazenie, Uwagi});
                                    tabelaSamochod.setLayout(new FlowLayout());
                                    tabelaSamochod.setPreferredScrollableViewportSize(new Dimension(100, 100));
                                    tabelaSamochod.setFillsViewportHeight(true);
                                }
                                rs.close();
                                res.close();
                                res.close();
                                statmt.close();
                                stmt.close();
                            } catch (SQLException exc) {
                                exc.printStackTrace();
                            }

                            gotoweZleceniaWindow.getContentPane().removeAll();
                            gotoweZleceniaWindow.repaint();

                            gotoweZleceniaWindow.add(scrollPaneZamowienie);
                            gotoweZleceniaWindow.add(scrollPaneFaktura);
                            gotoweZleceniaWindow.add(scrollPaneSamochod);
                            gotoweZleceniaWindow.add(btnDone);
                            gotoweZleceniaWindow.add(btnCancel);
                            gotoweZleceniaWindow.setSize(600, 300);
                            gotoweZleceniaWindow.setLayout(new GridLayout(5, 1));
                        }
                    });
                    btnDone.addActionListener(g->{
                        Statement statement;
                        try {
                            statement = conn.createStatement();

                            String sqlZam = "DELETE FROM Zamowienie WHERE idZamowienia=" + idZamowienia;
                            statement.executeUpdate(sqlZam);
                            String sqlFak = "DELETE FROM Faktura WHERE idFaktury=" + idFaktury;
                            statement.executeUpdate(sqlFak);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        gotoweZleceniaWindow.dispose();
                    });
                }

                if(czyJest == false){
                    JOptionPane.showMessageDialog(gotoweZleceniaWindow,
                            "Nie ma gotowych zlecen",
                            "Informacja",
                            JOptionPane.INFORMATION_MESSAGE);
                }else{
                    gotoweZleceniaWindow.setVisible(true);
                    gotoweZleceniaWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    gotoweZleceniaWindow.setSize(300, 350);
                    gotoweZleceniaWindow.getContentPane().add(btnNext);
                    gotoweZleceniaWindow.getContentPane().add(btnExit);
                    gotoweZleceniaWindow.setLayout(new GridLayout(3, 1));
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new GridLayout(17,1));
        frame.getContentPane().add(lbInformation);
        frame.getContentPane().add(lbInformation2);

        frame.getContentPane().add(infoKlient);
        frame.getContentPane().add(btnLoginK);
        frame.getContentPane().add(btnNowaOferta);
        frame.getContentPane().add(btnZlozOferte);
        frame.getContentPane().add(btnSprawdzZamowienie);
        frame.getContentPane().add(btnLogoutK);

        frame.getContentPane().add(infoPracownik);
        frame.getContentPane().add(btnLoginP);
        frame.getContentPane().add(btnEdytujOferta);
        frame.getContentPane().add(btnFaktury);
        frame.getContentPane().add(btnZlecenia);
        frame.getContentPane().add(btnGotoweZlecenia);
        frame.getContentPane().add(btnLogoutP);

        frame.getContentPane().add(data);

        frame.setResizable(false);
        frame.setVisible(true);
        getIloscFaktur();
        getIloscZamowien();

    }
}
