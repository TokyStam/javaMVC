/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class ConteneurController implements Initializable {
    @FXML
    private MenuItem menuListeUser;
    @FXML
    private MenuItem menuFermer;
    @FXML
    private MenuItem menuGraphAge;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private VBox Vbox;
    
    
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO     
        
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/mvc/view/produit1.fxml"));;
       
        try {
            AnchorPane a = (AnchorPane) loader.load();
            ProduitController produitController = loader.getController();
            produitController.setStage(getStage());
            anchorPane.getChildren().setAll(a); 
        } catch (IOException ex) {
            Logger.getLogger(ConteneurController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    

    @FXML
    public void handleItemeUser() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/mvc/view/user_1.fxml"));;
        AnchorPane a = (AnchorPane) loader.load();
        UserController userController = loader.getController();
        userController.setStage(stage);
        anchorPane.getChildren().setAll(a); 
    }
    
    @FXML
    public void handleItemeProduit() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/mvc/view/produit1.fxml"));;
        AnchorPane a = (AnchorPane) loader.load();
        ProduitController produitController = loader.getController();
        produitController.setStage(stage);
        anchorPane.getChildren().setAll(a); 
    }
    
    @FXML
    public void handleItemeVente() throws IOException{
   
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/mvc/view/vente.fxml"));;
        AnchorPane a = (AnchorPane) loader.load();
        VenteController venteController = loader.getController();
        venteController.setStage(stage);
        anchorPane.getChildren().setAll(a); 
    }
    
    @FXML
    public void handleGrapheVenteParMoi() throws IOException{
         AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/mvc/view/grapheVenteParMoi.fxml"));
        anchorPane.getChildren().setAll(a); 
    }
    
    @FXML
    public void handleFermer() throws IOException{
         stage.close();
    }
    
}
