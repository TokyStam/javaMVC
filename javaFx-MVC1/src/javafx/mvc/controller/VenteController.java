
package javafx.mvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.mvc.model.dao.ItemProduitDao;
import javafx.mvc.model.dao.ProduitsDao;
import javafx.mvc.model.dao.VenteDao;
import javafx.mvc.model.domain.ItemProduit;
import javafx.mvc.model.domain.Produit;
import javafx.mvc.model.domain.User;
import javafx.mvc.model.domain.Vente;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class VenteController implements Initializable {
    
    
    @FXML
    private TableView<Vente> tableVente;

    @FXML
    private TableColumn<Vente, String> columnCode;
    @FXML
    private TableColumn<Vente, LocalDate> columnDate;
    @FXML
    private TableColumn<Vente, Vente> columnClient;
    
    @FXML
    private Label labelCode;
    @FXML
    private Label labelDateVente;
    @FXML
    private Label labelValeur;
    @FXML
    private Label labelPayer;
    @FXML
    private Label labelClient;
    
    private List<Vente> listeV;
    private ObservableList<Vente>ObservableListVente;
    private Stage primaryStage;
    
      public void setStage(Stage stage){
        this.primaryStage = stage;
    }
    
    private final VenteDao venteDao = new VenteDao();
    private final ItemProduitDao itemProduitDao = new ItemProduitDao();

    public VenteController() throws SQLException {
       
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherTableViewVente();
        
        // Listen for selection changes and show the person details when changed.
             tableVente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showVenteDetails(newValue));
    }    
    
     public void afficherTableViewVente(){
        columnCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("dateVente"));
        columnClient.setCellValueFactory(new PropertyValueFactory<>("user"));
      
        listeV = venteDao.listeVente();
        ObservableListVente = FXCollections.observableArrayList(listeV);
        tableVente.setItems(ObservableListVente);
    }
     
     private void showVenteDetails(Vente vente) {
        if (vente != null) {
            // Fill the labels with info from the person object.
            labelCode.setText(Integer.toString(vente.getCode()));
            labelValeur.setText(Double.toString(vente.getValeur()));
            labelPayer.setText(String.valueOf(vente.isPayer()));
            labelDateVente.setText(String.valueOf(vente.getDateVente().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            labelClient.setText(vente.getUser().toString());

        } else {
            labelCode.setText("");
            labelValeur.setText("");
            labelPayer.setText("");
            labelDateVente.setText("");
            labelClient.setText("");
        }
    }
     
     //suprimer un vente
    @FXML
    public void handleButtonDelete() throws IOException, SQLException{
        Vente vente = tableVente.getSelectionModel().getSelectedItem();
        if(vente != null){
              
                  //resinitialiser la quantite
                  for( ItemProduit listeItemDeVente:vente.getItemProduit()){
                      Produit produit = listeItemDeVente.getProduit();
                      ProduitsDao produitDao = new ProduitsDao();
                      
                      produit.setQteEnStk(produit.getQteEnStk() + listeItemDeVente.getQuantite());
                      produitDao.Update(produit);
                      
                      itemProduitDao.deleteOneItem(listeItemDeVente);
                  }
                venteDao.delete(vente);
                afficherTableViewVente();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun user selectionne!!");
            alert.show();
        }
    }
    
    //raliser un nouveau vente
    @FXML
    public void handleButtonCreate() throws IOException, SQLException{
        Vente vente = new Vente();
        List<ItemProduit> itemProduit = new ArrayList();
        vente.setItemProduit(itemProduit);
        boolean buttonValiderClicked = showVenteDialog(vente);
        if(buttonValiderClicked){
            venteDao.create(vente);
            for( ItemProduit listeItemDeVente:vente.getItemProduit()){
                Produit produit = listeItemDeVente.getProduit();
                ProduitsDao produitDao = new ProduitsDao();
                listeItemDeVente.setcVente(venteDao.recupererDerniereVente());
                
                itemProduitDao.create(listeItemDeVente);
                
                produit.setQteEnStk(produit.getQteEnStk() - listeItemDeVente.getQuantite());
                produitDao.Update(produit);
            }
            afficherTableViewVente();
        }
    }

    //afficher le boite de dialogue
    public boolean showVenteDialog(Vente vente) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UserDialogController.class.getResource("/javafx/mvc/view/venteDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Formulaire vente");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL.WINDOW_MODAL);
        dialogStage.initOwner(this.primaryStage);
        // Set the user into the controller.
        VenteDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVente(vente);
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isButtonConfirmerClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
}

