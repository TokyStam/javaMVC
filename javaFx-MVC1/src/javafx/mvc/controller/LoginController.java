/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class LoginController implements Initializable {

    @FXML
    private Button buttonGererVente;
    @FXML
    private AnchorPane anchorPaneGererVente;
    
     private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void handleGererVente() throws IOException{
        primaryStage.close();
                
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/mvc/view/conteneur.fxml"));  
        VBox vBox = (VBox) loader.load();

        Stage dialogStage = new Stage();
        Scene scene = new Scene(vBox);
        dialogStage.setResizable(false);
        dialogStage.setScene(scene);

        ConteneurController controller = loader.getController();
        controller.setStage(dialogStage);
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
    }
    
}
