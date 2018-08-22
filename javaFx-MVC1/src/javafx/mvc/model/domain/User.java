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
public class User implements Serializable{
    private int id ;
    private String nom;
    private int age;
    private LocalDate dateNais;
 
    //construction
    public User(){}
    public User(int id, String nom, int age, LocalDate date) {
        this.id = id;
        this.nom = nom;
        this.age = age;
        this.dateNais = date;
    }

    public LocalDate getDateNais() {
        return dateNais;
    }

    public void setDateNais(LocalDate dateNais) {
        this.dateNais = dateNais;
    }

    @Override
    public String toString() {
        return this.nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
