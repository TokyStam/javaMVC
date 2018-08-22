
package javafx.mvc.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.mvc.model.dao.ProduitsDao;
import javafx.mvc.model.dao.UserDao;
import javafx.mvc.model.domain.ItemProduit;
import javafx.mvc.model.domain.Produit;
import javafx.mvc.model.domain.User;
import javafx.mvc.model.domain.Vente;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author johnyftr
 */
public class VenteDialogController implements Initializable {

    @FXML
    private ComboBox comboBoxClient;
    @FXML
    private DatePicker datePickerDateVente;
    @FXML
    private CheckBox chexkBoxPayer;
    @FXML
    private ComboBox comboBoxProduit;
    @FXML
    private TextField textFieldQteProduit;
    @FXML
    private Button buttonAddProduitIteme;
    @FXML
    private TableView tableViewItemeVente;
    @FXML
    private TableColumn columnNomProduit;
    @FXML
    private TableColumn columnQteProduit;
    @FXML
    private TableColumn columnValeurProduit;
    @FXML
    private TextField textFieldValeurVente;
    
    
    @FXML
    private Button buttonConfirmerVente;
    @FXML
    private Button buttonAnnulerVente;
    
    private List<User> listeClient;
    private List<Produit> listeProduit;
    
    private ObservableList<User> observableListUser;
    private ObservableList<Produit> observableListProduit;
    private ObservableList<ItemProduit> observableListItemProduit;
    
    private final UserDao userDao ;
    private final ProduitsDao produitDao;

    public VenteDialogController() throws SQLException {
        this.produitDao = new ProduitsDao();
        this.userDao = new UserDao();
    }
    private Vente vente ;
    private Stage dialogStage;
    private boolean buttonConfirmerClicked = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherComboBoxUser();
        afficherComboBoxProduit();
        
        columnNomProduit.setCellValueFactory(new PropertyValueFactory<>("produit"));
        columnQteProduit.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        columnValeurProduit.setCellValueFactory(new PropertyValueFactory<>("valeur"));
    }    

    //afficher la liste des clients disponible
    public void afficherComboBoxUser(){
        listeClient = userDao.listeUser();
        observableListUser = FXCollections.observableArrayList(listeClient);
        comboBoxClient.setItems(observableListUser);
    }
    
        //afficher la liste des produit disponible
    public void afficherComboBoxProduit(){
        listeProduit = produitDao.listeProduits();    
        observableListProduit = FXCollections.observableArrayList(listeProduit);
        comboBoxProduit.setItems(observableListProduit);
    }
    
    //fonction qui permet d ajouter un produit a la vente
    @FXML
    public void handleButtonAddProduit(){
        Produit produit;
        ItemProduit itemProduit = new ItemProduit();
        
        if(comboBoxProduit.getSelectionModel().getSelectedItem() != null){
            produit = (Produit) comboBoxProduit.getSelectionModel().getSelectedItem();
            if(produit.getQteEnStk() >= Integer.parseInt(textFieldQteProduit.getText())){
                itemProduit.setProduit((Produit) comboBoxProduit.getSelectionModel().getSelectedItem());
                itemProduit.setQuantite(Integer.parseInt(textFieldQteProduit.getText()));
                itemProduit.setValeur(itemProduit.getProduit().getPrix() * itemProduit.getQuantite());
                
                vente.getItemProduit().add(itemProduit);
                vente.setValeur((vente.getValeur() != null ? vente.getValeur(): Double.parseDouble("0")) + itemProduit.getValeur());
                observableListItemProduit = FXCollections.observableArrayList(vente.getItemProduit());
                tableViewItemeVente.setItems(observableListItemProduit);
               
               textFieldValeurVente.setText(String.format("%.2f", vente.getValeur()));
            }else{
                 // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setHeaderText("Ajout de produit invalide");
            alert.setContentText("La quantite en stoque de ce produit est insuffisante");

            alert.showAndWait();
            }
        }
    }
    //vente confirmer
    @FXML
    public void handleConfirmerVente(){
        if(isInputValid()){
            vente.setUser((User)comboBoxClient.getSelectionModel().getSelectedItem());
            vente.setDateVente(datePickerDateVente.getValue());
            vente.setPayer(chexkBoxPayer.isSelected());

            buttonConfirmerClicked = true;
            dialogStage.close();
        }
    }
     
   //bouton annuler vente
    @FXML
    public void handleAnnulerVente(){
        dialogStage.close();
    }
    
       /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        if (comboBoxProduit.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Choisir un client!\n";
        }
        if (datePickerDateVente.getValue() == null) {
            errorMessage += "La date de la vente est invalide!\n";
        } 
        if (observableListItemProduit == null ) {
            errorMessage += "Selectionner au moin un produit\n";
        } 
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Element invalide");
            alert.setHeaderText("SVP, corriger l'erreur si vous voulez valider la vente");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    //---------------------------creation du fentre-------------------------//
    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmerClicked() {
        return buttonConfirmerClicked;
    }

    public void setButtonConfirmerClicked(boolean buttonConfirmerClicked) {
        this.buttonConfirmerClicked = buttonConfirmerClicked;
    }
    
    
}
