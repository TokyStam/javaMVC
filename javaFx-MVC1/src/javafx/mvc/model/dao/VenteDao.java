/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.mvc.model.database.ConnectionsDB;
import javafx.mvc.model.domain.ItemProduit;
import javafx.mvc.model.domain.User;
import javafx.mvc.model.domain.Vente;


/**
 *
 * @author johnyftr
 */
public class VenteDao {
    static Connection cnx;
    
    public VenteDao() throws SQLException{
        this.cnx = new ConnectionsDB().getConnect();
    }
    //creer un nouvereau utilisateut
    public boolean create(Vente vente){
        String sql = "INSERT INTO vente(valeur, payer, codeUser, dateVente) VALUES(?, ?, ?, ?)";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setDouble(1, vente.getValeur());
            st.setBoolean(2, vente.isPayer());
            st.setInt(3, vente.getUser().getId());
            st.setDate(4,Date.valueOf(vente.getDateVente()));
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(VenteDao.class.getName()).log(Level.SEVERE, null, ex); 
            return false;
        }
    }
    
    //suprimer un utilisateur
     public boolean delete(Vente vente){
        String sql = "DELETE FROM vente WHERE cVente = ?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, vente.getCode());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(VenteDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
    // modifier un utilisateur
    public boolean Update(Vente vente){
        String sql = "UPDATE vente SET valeur=?, payer=?, cClient=? WHERE cVente=?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setDouble(1, vente.getValeur());
            st.setBoolean(2, vente.isPayer());
            st.setInt(3, vente.getUser().getId());
//            st.setDate(3,Date.valueOf(vente.getDateVente()));
            
            st.setInt(4, vente.getCode());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(VenteDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    // liste les utlisateurs
    public List<Vente> listeVente(){ 
        String sql = "SELECT *FROM vente";
        List<Vente> resultat = new ArrayList<>();
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            
            while(res.next()){
                Vente vente = new Vente();
                User user = new User();
                List<ItemProduit> itemProduit = new ArrayList();
                 
                vente.setCode(res.getInt("cVente"));
                vente.setValeur(res.getDouble("valeur"));
                vente.setPayer(res.getBoolean("payer"));
                vente.setDateVente(res.getDate("dateVente").toLocalDate());
                user.setId(res.getInt("codeUser"));

                //recuper le client 
                UserDao userDao = new UserDao();
                user = userDao.showUser(user);
                
                //recuperer les iteme
                ItemProduitDao itemProduitDao = new ItemProduitDao();
                itemProduit = itemProduitDao.listeProduitDeVente(vente);
                
                vente.setUser(user);
                vente.setItemProduit(itemProduit);
                 
                resultat.add(vente);
            }
         
        }catch(SQLException ex){
            Logger.getLogger(VenteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
    }
    
    //show vnete
    public Vente showVente(Vente vente){
        String sql = "SELECT *FROM vente WHERE cVente=" + vente.getCode();
            PreparedStatement st;
            Vente us = new Vente(); 
        try {
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            if(res.next()){
                us.setCode(res.getInt("cVente"));
                us.setValeur(res.getDouble("valeur"));
                us.setPayer(res.getBoolean("payer"));
                us.setDateVente(res.getDate("dateVente").toLocalDate());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return us;
    }
    
    public Vente recupererDerniereVente(){
        String sql = "SELECT*FROM vente ORDER BY cVente DESC LIMIT 1";
            PreparedStatement st;
            Vente resultat = new Vente();
        try {
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            if(res.next()){
                resultat.setCode(res.getInt("cVente"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
     
    }
    
    public Map<Integer, ArrayList> ListeQteVenteParMoi(){
         String sql = "SELECT count(cVente) as count, EXTRACT(year from dateVente) as ans, EXTRACT(month from dateVente) as mois FROM vente GROUP BY ans,mois ORDER BY ans, mois";
         Map<Integer, ArrayList> retourner = new HashMap();
          try {
            PreparedStatement st;
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            while(res.next()){
                ArrayList lien = new ArrayList();
                if(!retourner.containsKey(res.getInt("ans"))){
                    
                    lien.add(res.getInt("mois"));
                    lien.add(res.getInt("count"));
                    retourner.put(res.getInt("ans"), lien);
                }else{
                    ArrayList LienNouveau = retourner.get(res.getInt("ans"));
                    LienNouveau.add(res.getInt("mois"));
                    LienNouveau.add(res.getInt("count"));
                }
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(ProduitsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retourner;
    }
    
}
