/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.mvc.controller.LoginController;
import javafx.mvc.model.dao.ProduitsDao;
import javafx.mvc.model.domain.Produit;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author johnyftr
 */
public class JavaFxMVC extends Application {
    @Override
      public void start(Stage stage) throws Exception {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/login.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Login");
        dialogStage.setResizable(false);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        // Set the user into the controller.
        LoginController controller = loader.getController();
        controller.setPrimaryStage(dialogStage);
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
//        Parent root = FXMLLoader.load(getClass().getResource("view/conteneur.fxml"));  
//        
//        Scene scene = new Scene(root);
//        
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
//--------------------------------zone de teste de DAO-------------------------------------------//
//          System.out.println("1dddddddddddddddddddddddd");
//          Produit p = new Produit(); 
//          p.setNumPro(1);
//     
//       ProduitsDao ud = new ProduitsDao();
//       ud.listeProduits();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        System.out.println("fetra");
       

         
    }
    
   
    
}
