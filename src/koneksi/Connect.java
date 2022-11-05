/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 *
 * @author PANASONIC
 */
public class Connect {
    private static Connection koneksi;
    public static Connection GetConnection(){
        if (koneksi == null) {
            try {
                String url ="jdbc:mysql://localhost:3306/inventory";
                String username ="root";
                String password ="";
                koneksi = DriverManager.getConnection(url,username,password);
                System.out.println("Koneksi Database Berhasil");
            }
            catch (SQLException e){
                System.out.println("Koneksi Database Gagal");
            }
        }return koneksi;
    }
}
