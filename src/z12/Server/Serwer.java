package z12.Server;

import z12.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.sql.*;


public class Serwer {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Projekt?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "Zaq12wsx";
    static int increment=0;
    static int ilosc=0;

    public int getIncrement() {
        return increment;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Serwer dziala na porcie 9899");
        int clientNumber = 0;
        try (var listener = new ServerSocket(9899)) {
            while (true) {
                new Capitalizer(listener.accept(), clientNumber++).start();
            }
        }
    }

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


    private static class Capitalizer extends Thread {
        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            System.out.println("Klient #" + clientNumber + " dolaczyl na  " + socket);
        }

        public String getSamochod(int ilosc, int ktory) throws ClassNotFoundException, SQLException {
            String[] Samochod = new String[10];
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            int i=0;
            try {

                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                stmt = conn.createStatement();

                String sql = "SELECT * FROM Samochod";
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    int idSamochodu = rs.getInt("idSamochodu");
                    String Silnik = rs.getString("Silnik");
                    String Naped = rs.getString("Naped");
                    String Kola = rs.getString("Kola");
                    String Wyposazenie = rs.getString("Wyposazenie");

                    Samochod[i]="ID: "+idSamochodu+" Silnik: "+Silnik+" Napęd: "+Naped+" Koła: "+Kola+" Wyposażenie: "+Wyposazenie;
               i++;
                }



                rs.close();
                stmt.close();
            } catch (Exception se) {

                se.printStackTrace();
            }
            return Samochod[ktory];
        }

        public void run() {
            try {
                var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                var out = new PrintWriter(socket.getOutputStream(), true);




                // Get messages from the client, line by line; return them capitalized

                String dane;
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt;
                try {

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);

                    stmt = conn.createStatement();

                    String sql = "SELECT COUNT(*) FROM Samochod";
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next()){
                        ilosc = rs.getInt("count(*)");
                    }

                    System.out.println("Ilosc samochodow:"+ilosc);

                    rs.close();
                    stmt.close();
                } catch (Exception se) {

                    se.printStackTrace();
                }
                while (true) {

                    var input = in.readLine();
                    if (input == null || input.isEmpty()) {
                        break;
                    }
                    if(input.equals("nextgen") || input.equals("prevgen"))
                    {
                        dane=getSamochod(ilosc,increment);
                        if(increment==0)
                        {
                            increment=1;
                            out.println(getSamochod(ilosc,increment));
                        }
                        if(increment==ilosc)
                        {
                            System.out.println("MAKS");
                                out.println(dane);
                            increment--;

                        }

                        else {
                            if(input.equals("nextgen"))
                            {
                                increment++;
                                out.println(dane);
                            }
                            if(input.equals("prevgen"))
                            {
                                increment--;
                                out.println(dane);
                            }
                        }

                        }



                    if (input.equals("PRZELEW") || input.equals("KARTA") || input.equals("GOTOWKA"))
                    {
                        int iloscFaktur=getIloscFaktur();
                        int iloscZamowien=getIloscZamowien();
                        if(input.equals("PRZELEW"))
                        {

                            System.out.println("PRZESLANO PLATNOSC PRZELEWEM DO SERWERA");
                            try{

                                final Statement st = conn.createStatement();
                                String sql = "INSERT INTO Faktura (idFaktury, cena, rodzajPlatnosci, rodzajFaktury)"
                                        + " VALUES ("+(getIloscFaktur()+1)+",243432,'PRZELEW','FVAT')";
                                st.executeUpdate(sql);
                                String sql2 = "INSERT INTO Zamowienie (idZamowienia, wartoscZamowienia, dataZamowienia, idFak,idSamochodu,czyGotowe)"
                                        + " VALUES ("+(getIloscZamowien()+1)+",243432,'"+Data.getDate()+"',"+getIloscFaktur()+","+increment+",0)";
                                st.executeUpdate(sql2);

                            }
                            catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            System.out.println(in.readLine());
                        }
                        if(input.equals("KARTA"))
                        {

                            System.out.println("PRZESLANO PLASTNOSC KARTA DO SERWERA");
                            try{

                                final Statement st = conn.createStatement();
                                String sql = "INSERT INTO Faktura (idFaktury, cena, rodzajPlatnosci, rodzajFaktury)"
                                        + " VALUES ("+(getIloscFaktur()+1)+",243432,'KARTA','FVAT')";
                                st.executeUpdate(sql);
                                String sql2 = "INSERT INTO Zamowienie (idZamowienia, wartoscZamowienia, dataZamowienia, idFak,idSamochodu,czyGotowe)"
                                        + " VALUES ("+(getIloscZamowien()+1)+",243432,'"+Data.getDate()+"',"+getIloscFaktur()+","+increment+",0)";
                                st.executeUpdate(sql2);

                            }
                            catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            System.out.println(in.readLine());
                        }
                        if(input.equals("GOTOWKA"))
                        {

                            System.out.println("PRZESLANO PLATNOSC GOTOWKA DO SERWERA");
                            try{

                                final Statement st = conn.createStatement();
                                String sql = "INSERT INTO Faktura (idFaktury, cena, rodzajPlatnosci, rodzajFaktury)"
                                        + " VALUES ("+(getIloscFaktur()+1)+",243432,'GOTOWKA','FVAT')";
                                st.executeUpdate(sql);
                                String sql2 = "INSERT INTO Zamowienie (idZamowienia, wartoscZamowienia, dataZamowienia, idFak,idSamochodu,czyGotowe)"
                                        + " VALUES ("+(getIloscZamowien()+1)+",243432,'"+Data.getDate()+"',"+getIloscFaktur()+","+increment+",0)";
                                st.executeUpdate(sql2);

                            }
                            catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            System.out.println(in.readLine());
                        }
                    }

                }
            } catch (IOException e) {
                System.out.println("Blad z klientem#" + clientNumber);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try { socket.close(); } catch (IOException e) {}
                System.out.println("Polaczenie z klientem # " + clientNumber + " zamkniete");
            }


        }
    }
}