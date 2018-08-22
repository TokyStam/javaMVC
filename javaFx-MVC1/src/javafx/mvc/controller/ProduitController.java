/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.mvc.model.dao.ProduitsDao;
import javafx.mvc.model.domain.Produit;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class ProduitController implements Initializable {

    @FXML
    private TableView<Produit> tableViewProduit;
    @FXML
    private TableColumn<Produit, Integer> columnNumPro;
    @FXML
    private TableColumn<Produit, String> columnDesignation;
    @FXML
    private TableColumn<Produit, Double> columnPU;
    @FXML
    private TableColumn<Produit, Double> columnQteEnStk;
    @FXML
    private Label labelReference;
    @FXML
    private Label labelDesignation;
    @FXML
    private Label LabelPU;
    @FXML
    private Label labelQteEnStk;
    @FXML
    private Label labelDebut;
    @FXML
    private Label labelCommentaire;
    
    private List<Produit> listeProduit;
    private ObservableList<Produit> ObservableListProduit;
    private ProduitsDao produitsDao;
    private Stage primaryStage;

    public ProduitController() throws SQLException {
        this.produitsDao = new ProduitsDao();
    }
    
    public void setStage(Stage stage){
        this.primaryStage = stage;
    }
    
    @FXML
    private Button buttonCreerProduit;
    @FXML
    private Button buttonModifierProduit;
    @FXML
    private Button buttonSuprimerProduit;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherTableViewProduit();
          //Listen for selection changes and show the person details when changed.
             tableViewProduit.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProduitDetails(newValue));
  
    }    
    
    public void afficherTableViewProduit(){
        columnNumPro.setCellValueFactory(new PropertyValueFactory<>("numPro"));
        columnDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
        columnPU.setCellValueFactory(new PropertyValueFactory<>("prix"));
        columnQteEnStk.setCellValueFactory(new PropertyValueFactory<>("qteEnStk"));
        
        listeProduit = produitsDao.listeProduits();
 
        ObservableListProduit = FXCollections.observableArrayList(listeProduit);
        tableViewProduit.setItems(ObservableListProduit);
        
    }
    
      /**
     * afficher les listes de personnes.
     * si la personne est vide toutes la valeur du label sera vide
     *
     * @param person the product or null
     */
        private void showProduitDetails(Produit produit) {
        if (produit != null) {
            // Fill the labels with info from the person object.
            labelReference.setText(Integer.toString(produit.getNumPro()));
            labelDesignation.setText(produit.getDesignation());
            LabelPU.setText(Double.toString(produit.getPrix()));
            labelQteEnStk.setText(Double.toString(produit.getQteEnStk()));
            labelCommentaire.setText(produit.getCommentaire());
            labelDebut.setText(String.valueOf(produit.getDateDebutStk().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
     
        } else {
            // Person is null, remove all the text.
            labelReference.setText("");
            labelDesignation.setText("");
            LabelPU.setText("");
            labelQteEnStk.setText("");
            labelCommentaire.setText("");
            labelDebut.setText("");
        }
    }
       
    //ajouter un nouveau Produit
    @FXML
    public void handleButtonAjouter() throws IOException{
        Produit produit = new Produit();
        boolean buttonValiderClicked = showProduitDialog(produit);
        if(buttonValiderClicked){
            produitsDao.create(produit);
            afficherTableViewProduit();
        }
    }
    
    
    //Modifier un produit
    @FXML
    public void handleButtonUpdate() throws IOException{
        Produit produit = tableViewProduit.getSelectionModel().getSelectedItem();
        if(produit != null){
            boolean buttonValiderClicked = showProduitDialog(produit);
            if(buttonValiderClicked){
                produitsDao.Update(produit);
                afficherTableViewProduit();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun Produit selectionne!!");
            alert.show();
        }

    }
   // suprimer un Produit
    @FXML
    public void handleButtonDelete() throws IOException{
        Produit produit = tableViewProduit.getSelectionModel().getSelectedItem();
        if(produit != null){
                produitsDao.delete(produit);
                afficherTableViewProduit();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun Produit selectionne!!");
            alert.show();
        }

    }
    
   // afficher le boite de dialogue
    public boolean showProduitDialog(Produit produit) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ProduitDialogController.class.getResource("/javafx/mvc/view/produitDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Formulaire Produit");
   
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL.WINDOW_MODAL);
        dialogStage.initOwner(this.primaryStage);
        //set the product into the controller
        ProduitDialogController controller= loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setProduit(produit);
        
        dialogStage.showAndWait();
        
        return controller.isButtonValiderClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
    
}
