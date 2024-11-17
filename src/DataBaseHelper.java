/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fadil
 */
public class DataBaseHelper {
      private Connection conn; 
      
      public DataBaseHelper() {
        // Membuat koneksi ke database
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:kontak.db");
            // Memanggil method untuk membuat tabel jika belum ada
            createTable();
        } catch (SQLException e) {
        }
    }
      // Method untuk membuat tabel "kontak" jika belum ada
    private void createTable() {
        
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS kontak ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nama TEXT NOT NULL, "
                + "nomor TEXT NOT NULL, "
                + "kategori TEXT NOT NULL)";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCreateTable);
            System.out.println("Tabel kontak berhasil dibuat atau sudah ada.");
        } catch (SQLException e) {
        }
    }
      
      // Method untuk menambahkan kontak baru
    public void tambahKontak(String nama, String nomor, String kategori) {
        String sqlInsert = "INSERT INTO kontak (nama, nomor, kategori) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setString(1, nama);
            pstmt.setString(2, nomor);
            pstmt.setString(3, kategori);
            pstmt.executeUpdate();
            System.out.println("Kontak berhasil ditambahkan.");
        } catch (SQLException e) {
        }
    }
      
      // Method untuk menghapus kontak berdasarkan ID
    public void hapusKontak(int id) {
        String sqlDelete = "DELETE FROM kontak WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Kontak berhasil dihapus.");
        } catch (SQLException e) {
        }
    }
    
    // Method untuk memperbarui kontak berdasarkan ID
    public void updateKontak(int id, String nama, String nomor, String kategori) {
        String sqlUpdate = "UPDATE kontak SET nama = ?, nomor = ?, kategori = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setString(1, nama);
            pstmt.setString(2, nomor);
            pstmt.setString(3, kategori);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Kontak berhasil diperbarui.");
        } catch (SQLException e) {
        }
    }
      
      // Method untuk mengambil semua kontak dari database
    public List<String[]> getKontakList() {
        List<String[]> kontakList = new ArrayList<>();
        String sqlSelect = "SELECT * FROM kontak";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelect)) {
            while (rs.next()) {
                kontakList.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("nama"),
                        rs.getString("nomor"),
                        rs.getString("kategori")
                });
            }
        } catch (SQLException e) {
        }
        return kontakList;
    }
}