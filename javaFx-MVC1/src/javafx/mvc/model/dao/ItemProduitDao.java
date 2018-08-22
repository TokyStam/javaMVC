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
import javafx.mvc.model.domain.ItemProduit;
import javafx.mvc.model.domain.Produit;
import javafx.mvc.model.domain.Vente;


/**
 *
 * @author johnyftr
 */
public class ItemProduitDao {
    static Connection cnx;
    
    public ItemProduitDao() throws SQLException{
        this.cnx = new ConnectionsDB().getConnect();
    }
    //creer un nouvereau utilisateut
    public boolean create(ItemProduit itemProduit){
        String sql = "INSERT INTO itemeproduit( cVente, numPro, quantite, valeur) VALUES(?, ?, ?, ?)";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1,itemProduit.getcVente().getCode());
            st.setInt(2, itemProduit.getProduit().getNumPro());
            st.setDouble(3, itemProduit.getQuantite());
            st.setDouble(4, itemProduit.getValeur());
            
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ItemProduit.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
      //suprimer un utilisateur
     public boolean deleteAllItem(Vente vente){
        String sql = "DELETE FROM itemeproduit WHERE cVente = ?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, vente.getCode());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ItemProduit.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
          //suprimer un utilisateur
     public boolean deleteOneItem(ItemProduit itemProduit){
        String sql = "DELETE FROM itemeproduit WHERE cVente=? AND numPro=?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, itemProduit.getcVente().getCode());
            st.setInt(2, itemProduit.getProduit().getNumPro());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(ItemProduit.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
   
    
    // liste des produit
    public List<ItemProduit> listeProduitDeVente(Vente vente){
        String sql = "SELECT *FROM itemeproduit WHERE cVente="+ vente.getCode() ;
        List<ItemProduit> resultat = new ArrayList<>();
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            
            while(res.next()){
                //recuperer le produit
                Produit p = new Produit();
                ProduitsDao pd=new ProduitsDao();
                //recuperer le vente
                Vente v = new Vente();
                VenteDao vd = new VenteDao();
                
                //recuperer le numero de la vente et du produit
                p.setNumPro(res.getInt("numPro"));
                p = pd.showProduit(p);
                
                v.setCode(res.getInt("cVente"));
                v = vd.showVente(vente);
                
                ItemProduit us = new ItemProduit();
                us.setProduit(p);
                us.setcVente(v);
                us.setQuantite(res.getInt("quantite"));
                us.setValeur(res.getDouble("valeur"));
                  
                resultat.add(us);
            }
         
        }catch(SQLException ex){
            Logger.getLogger(ItemProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
    }
}
