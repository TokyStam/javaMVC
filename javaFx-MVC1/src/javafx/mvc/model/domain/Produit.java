/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author johnyftr
 */
public class Produit implements Serializable{
    private int numPro ;
    private String designation;
    private double qteEnStk;
    private String commentaire;
    private LocalDate dateDebutStk;
    private Double prix;

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public int getNumPro() {
        return numPro;
    }
    
    public Produit(){}
    public Produit(int numPro, String designation, double qteEnStk, double prix, String commentaire, LocalDate dateDebutStk) {
        this.numPro = numPro;
        this.designation = designation;
        this.qteEnStk = qteEnStk;
        this.commentaire = commentaire;
        this.dateDebutStk = dateDebutStk;
        this.prix = prix;
    }

    @Override
    public String toString() {
        return  designation ;
    }

    public void setNumPro(int numPro) {
        this.numPro = numPro;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getQteEnStk() {
        return qteEnStk;
    }

    public void setQteEnStk(double qteEnStk) {
        this.qteEnStk = qteEnStk;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDate getDateDebutStk() {
        return dateDebutStk;
    }

    public void setDateDebutStk(LocalDate dateDebutStk) {
        this.dateDebutStk = dateDebutStk;
    }
 
    
    
}
