/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.mvc.model.database.ConnectionsDB;
import javafx.mvc.model.domain.Produit;


/**
 *
 * @author johnyftr
 */
public class ProduitsDao {
    static Connection cnx;
    
    public ProduitsDao() throws SQLException{
        this.cnx = new ConnectionsDB().getConnect();
    }
    //creer un nouvereau utilisateut
    public boolean create(Produit produit){
        String sql = "INSERT INTO produits( designation, prix, qteEnStk, commentaire, dateDebutStk) VALUES(?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setString(1, produit.getDesignation());
            st.setDouble(2, produit.getPrix());
            st.setDouble(3, produit.getQteEnStk());
            st.setString(4, produit.getCommentaire());
            st.setDate(5,Date.valueOf(produit.getDateDebutStk()));
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ProduitsDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //suprimer un utilisateur
     public boolean delete(Produit produit){
        String sql = "DELETE FROM produits WHERE numPro = ?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, produit.getNumPro());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ProduitsDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
    // modifier un produit
    public boolean Update(Produit produit){
        String sql = "UPDATE produits SET designation=?, prix=?, qteEnStk=?, commentaire=?, dateDebutStk=? WHERE numPro = ?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setString(1, produit.getDesignation());
            st.setDouble(2, produit.getPrix());
            st.setDouble(3, produit.getQteEnStk());
            st.setString(4, produit.getCommentaire());
            st.setDate(5,Date.valueOf(produit.getDateDebutStk()));
            st.setInt(6, produit.getNumPro());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ProduitsDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    //one Produit
    public Produit showProduit(Produit produit){
            String sql = "SELECT *FROM produits WHERE numPro=" + produit.getNumPro();
            PreparedStatement st;
            Produit us = new Produit(); 
        try {
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            if(res.next()){
                us.setNumPro(res.getInt("numPro"));
                us.setDesignation(res.getString("designation"));
                us.setCommentaire(res.getString("commentaire"));
                us.setPrix(res.getDouble("prix"));
                us.setQteEnStk(res.getDouble("qteEnStk"));
                us.setDateDebutStk(res.getDate("dateDebutStk").toLocalDate()); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return us;
    }
    // liste des produit
    public List<Produit> listeProduits(){
        String sql = "SELECT *FROM produits";
        List<Produit> resultat = new ArrayList<>();
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            
            while(res.next()){
                Produit us = new Produit(); 
                us.setNumPro(res.getInt("numPro"));
                us.setDesignation(res.getString("designation"));
                us.setPrix(res.getDouble("prix"));
                us.setCommentaire(res.getString("commentaire"));
                us.setQteEnStk(res.getDouble("qteEnStk"));
                us.setDateDebutStk(res.getDate("dateDebutStk").toLocalDate());
                resultat.add(us);
            }
         
        }catch(SQLException ex){
            Logger.getLogger(ProduitsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
    }
}
