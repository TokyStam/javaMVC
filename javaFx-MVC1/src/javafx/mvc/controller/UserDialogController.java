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
import javafx.mvc.model.domain.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class UserDialogController implements Initializable {

    
    @FXML
    private TextField champId;
    @FXML
    private TextField champNom;
    @FXML
    private TextField champAge;
    @FXML
    private DatePicker champDateNais;
    
    @FXML
    private Button buttonValider;
    @FXML
    private Button buttonAnnuler;
    
    private Stage dialogStage;
    private boolean buttonValiderClicked = false;
    private User user;
    
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
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
        if(this.user != null ||  user.getAge() == 0){
            this.champId.setText(Integer.toString(user.getId()));
            this.champNom.setText(user.getNom());
            this.champAge.setText(Integer.toString(user.getAge()));
            this.champDateNais.setValue(user.getDateNais());
        }
       this.champId.setDisable(true);
    }
    
    //bouton confirmer est clique
    @FXML
    public void handleButtonValider(){
       if(isInputValid()){
            user.setNom(champNom.getText());
            user.setAge(Integer.parseInt(champAge.getText()));
            user.setDateNais(champDateNais.getValue());

            buttonValiderClicked = true;
            dialogStage.close();
       }
           
    }
    
    //bouton fermer
    @FXML
    public void handleButtonAnnuler() {
        dialogStage.close();
    }
    
    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        if (champNom.getText() == null || champNom.getText().length() == 0) {
            errorMessage += "Le nom saisi est invalide!\n";
        }
        if (champAge.getText() == null || champAge.getText().length() == 0) {
            errorMessage += "L'age saisi est invalide!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(champAge.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Age invalide (il doit etre un entier)!\n";
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
