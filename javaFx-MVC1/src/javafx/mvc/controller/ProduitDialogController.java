/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.mvc.model.domain.Produit;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class ProduitDialogController implements Initializable {

    
    @FXML
    private TextField champNumPro;
    @FXML
    private TextField champDesignation;
    @FXML
    private TextField champPU;
    @FXML
    private TextField champQteEnStk;
    @FXML
    private DatePicker champDebutStk;
    @FXML
    private TextArea champCommentaire;
    
    @FXML
    private Button buttonValider;
    @FXML
    private Button buttonAnnuler;
    
    private Stage dialogStage;
    private boolean buttonValiderClicked = false;
    private Produit produit;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    /**
     * @return the dialogStage
     */
    public Stage getDialogStage() {
        return dialogStage;
    }

    /**
     * @param dialogStage the dialogStage to set
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * @return the buttonValiderClicked
     */
    public boolean isButtonValiderClicked() {
        return buttonValiderClicked;
    }

    /**
     * @param buttonValiderClicked the buttonValiderClicked to set
     */
    public void setButtonValiderClicked(boolean buttonValiderClicked) {
        this.buttonValiderClicked = buttonValiderClicked;
    }

    /**
     * @return the user
     */
    public Produit getProduit() {
        return produit;
    }

    /**
     * @param produit
     */
    public void setProduit(Produit produit) {
        this.produit = produit;
        if(produit != null && produit.getPrix() != null && produit.getDesignation() != null){
            this.champNumPro.setText(Integer.toString(produit.getNumPro()));
            this.champDesignation.setText(produit.getDesignation());
            this.champQteEnStk.setText(Double.toString(produit.getQteEnStk()));
            this.champPU.setText(Double.toString(produit.getPrix()));
            this.champDebutStk.setValue(produit.getDateDebutStk());
            this.champCommentaire.setText(produit.getCommentaire());
        }
        this.champNumPro.setDisable(true);
    }
    
    //bouton confirmer est clique
    @FXML
    public void handleButtonValider(){
      if(isInputValid()){
            produit.setDesignation(champDesignation.getText());
            produit.setCommentaire(champCommentaire.getText());
            produit.setPrix(Double.parseDouble(champPU.getText()));
            produit.setQteEnStk(Double.parseDouble(champQteEnStk.getText()));
            produit.setDateDebutStk(champDebutStk.getValue());

            buttonValiderClicked = true;
            dialogStage.close();
      }
    }
    
    //bouton fermer
    @FXML
    public void ButtonAnnuler() {
        dialogStage.close();
    }
    
    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        if (champDesignation.getText() == null || champDesignation.getText().length() == 0) {
            errorMessage += "Veuillez remplir le champ designation!\n";
        }
        
        if (champDebutStk.getValue()== null) {
            errorMessage += "Veuillez remplir le champ debut du stoque!\n";
        }
            
        if (champPU.getText() == null || champPU.getText().length() == 0) {
            errorMessage += "Veuillerz mentioner le prix du produit!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Double.parseDouble(champPU.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Prix invalide !\n";
            }
        }
        
         if (champQteEnStk.getText() == null || champQteEnStk.getText().length() == 0) {
            errorMessage += "Veuillerz mentioner la quantitE du produit stokE!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Double.parseDouble(champQteEnStk.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Quantite invalide !\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
         }
    }
}
    
   

