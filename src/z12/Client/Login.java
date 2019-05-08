package z12.Client;


import z12.Produkcja.Faktura;

import java.io.*;

import java.net.Socket;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class Login {
    private static String result;

    private static final String DB_URL = "jdbc:mysql://www.mebleopoczno.pl/mebleopo_projectPiP?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "mebleopo_test";
    private static final String PASS = "oh5vvuag!";

    private static String getHaslo(String podanyLogin) {
        Connection conn;
        Statement stmt;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Laczenie z baza...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Tworzenie zapytania...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT idauth, login , pass  FROM auth";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                int id = rs.getInt("idauth");
                String login1 = rs.getString("login");
                String pass1 = rs.getString("pass");

                if (login1.equals(podanyLogin)) {
                    result = pass1;

                }

                System.out.print("ID: " + id);
                System.out.print(", Login: " + login1);
                System.out.println(", Pass: " + pass1);
                System.out.println("Login1:" + login1);


            }

            rs.close();
            stmt.close();
        } catch (Exception se) {

            se.printStackTrace();
        }


        return result;


    }


    private static String getLogin(String podanyLogin) {
        Connection conn;
        Statement stmt;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");


            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            stmt = conn.createStatement();
            String sql;
            sql = "SELECT idauth, login , pass  FROM auth";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("idauth");
                String login1 = rs.getString("login");
                String pass1 = rs.getString("pass");

                if (login1.equals(podanyLogin)) {
                    result = login1;

                }


            }

            rs.close();
            stmt.close();
        } catch (Exception se) {

            se.printStackTrace();
        }


        return result;

    }


    static boolean authenticate(String username, String password) throws IOException {
        String haslo, login = null;
        haslo = getHaslo(username);
        login = getLogin(username);
        if (username.equals(login)) {


            if (haslo.equals(password)) {


                return true;
            } else return false;
        } else {
            return false;
        }

    }
}
