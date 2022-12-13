/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author dell
 */
// tabel user/texperience merupakan turunan dari dbConnection
public class TabelUser extends dbConnection{
    public TabelUser() throws Exception, SQLException{
        //konstruktor
        // memanggil konstruktor parent
        super();
    }
    // Mengambil semua data di tabel experiences
    public void getUser(){
        try{
            String query = "SELECT * from texperiences";
            createQuery(query);
        }catch(Exception e){
            System.err.println(e.toString());
        }
    }
    // fungsi untuk mencari username di database
    public Object[] searchName(String username){
        // variabel untuk menyimpan isi data dari hasil searching
        Object[] row = new Object[3];
        try{
            // Proses pencarian data
            String query = "SELECT * from texperiences where username ='"+ username +"'";
            this.createQuery(query);
            //  Jika ditemukan username
            // maka mengambil isi data tersebut
            if(this.getResult().next()){
                
                row[0] = this.getResult().getString(2);
                row[1] = this.getResult().getInt(3);
                row[2] = this.getResult().getInt(4);
            }
        }catch(Exception e){
            System.err.println(e.toString());
        }
        return row;
    }
    // fungsi untuk menginput data 
    public void insertData(String username, int fall, int adapt){
        try{
            System.out.println(username);
            String query = "INSERT into texperiences VALUES(NULL,'"+ username +"',"+fall+","+adapt+")";
            createUpdate(query);
        }catch(Exception e){
            System.err.println(e.toString());
        }
    }
    public void updateData(String username, int fall, int adapt){
        try {
            String query = "UPDATE texperiences SET fall = " + fall + ", adapt= "+ adapt +" WHERE username = '" + username + "'";
            createUpdate(query);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    // Fungsi untuk mengisi tabel di jframe
    public DefaultTableModel setTable(){
        DefaultTableModel dataTabel = null;
        try{
            Object[] column = {"Username", "Fall", "Adapt"}; // nama kolom tabel
            dataTabel = new DefaultTableModel(null, column);
            String query = "SELECT * from texperiences order by adapt DESC";
            this.createQuery(query);
            while(this.getResult().next()){
                Object[] row = new Object[3];
                row[0] = this.getResult().getString(2);
                row[1] = this.getResult().getString(3);
                row[2] = this.getResult().getString(4);
                dataTabel.addRow(row);
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
        return dataTabel;
    }
}
