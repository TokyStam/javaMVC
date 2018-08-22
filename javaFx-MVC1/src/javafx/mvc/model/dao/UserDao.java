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
import javafx.mvc.model.domain.User;


/**
 *
 * @author johnyftr
 */
public class UserDao {
    static Connection cnx;
    
    public UserDao() throws SQLException{
        this.cnx = new ConnectionsDB().getConnect();
    }
    //creer un nouvereau utilisateut
    public boolean create(User user){
        String sql = "INSERT INTO user( nom, age, dateNais) VALUES(?, ?, ?)";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setString(1, user.getNom());
            st.setInt(2, user.getAge());
            st.setDate(3,Date.valueOf(user.getDateNais()));
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //suprimer un utilisateur
     public boolean delete(User user){
        String sql = "DELETE FROM user WHERE id = ?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setInt(1, user.getId());
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
    // modifier un utilisateur
    public boolean Update(User user){
        String sql = "UPDATE user SET nom=?, age=?, dateNais=? WHERE id = ?";
        
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setString(1, user.getNom());
            st.setInt(2, user.getAge());
            st.setDate(3,Date.valueOf(user.getDateNais()));
            
            st.setInt(4, user.getId());
            
            st.execute();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    //show user
    public User showUser(User user){
        String sql = "SELECT *FROM user WHERE id=" + user.getId();
            PreparedStatement st;
            User us = new User(); 
        try {
            st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            if(res.next()){
                us.setId(res.getInt("id"));
                us.setAge(res.getInt("age"));
                us.setNom(res.getString("nom"));
                us.setDateNais(res.getDate("dateNais").toLocalDate());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return us;
    }
    
    // liste les utlisateurs
    public List<User> listeUser(){
        String sql = "SELECT *FROM user";
        List<User> resultat = new ArrayList<>();
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            ResultSet res = st.executeQuery();
            
            while(res.next()){
                User us = new User(); 
                us.setId(res.getInt("id"));
                us.setNom(res.getString("nom"));
                us.setAge(res.getInt("age"));
                us.setDateNais(res.getDate("dateNais").toLocalDate());
                resultat.add(us);
            }
         
        }catch(SQLException ex){
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return resultat;
    }
}
