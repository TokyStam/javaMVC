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
import javafx.mvc.model.dao.UserDao;
import javafx.mvc.model.domain.User;
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
public class UserController implements Initializable {

    @FXML
    private TableView<User> tableViewUser;
    @FXML
    private TableColumn<User, String> columnId;
    @FXML
    private TableColumn<User, String> columnNom;
    @FXML
    private TableColumn<User, String> columnAge;
    @FXML
    private TableColumn<User, LocalDate> columnDateNais;
    @FXML
    private Label labelId;
    @FXML
    private Label LabelNom;
    @FXML
    private Label LabelAge;
    @FXML
    private Label labelDateNais;
    
    private List<User> listeUser;
    private ObservableList<User> ObservableListUser;
    private UserDao userDao;
    private Stage primaryStage;

    public UserController() throws SQLException {
        this.userDao = new UserDao();
    }
    
    public void setStage(Stage stage){
        this.primaryStage = stage;
    }
    
    @FXML
    private Button buttonCreer;
    @FXML
    private Button buttonModifier;
    @FXML
    private Button buttonSuprimer;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       afficherTableViewUser();
       
         // Listen for selection changes and show the person details when changed.
             tableViewUser.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
  
    }    
    
    public void afficherTableViewUser(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnDateNais.setCellValueFactory(new PropertyValueFactory<>("dateNais"));
        
        listeUser = userDao.listeUser();
        ObservableListUser = FXCollections.observableArrayList(listeUser);
        tableViewUser.setItems(ObservableListUser);
        
    }
    
      /**
     * afficher les listes de personnes.
     * si la personne est vide toutes la valeur du label sera vide
     *
     * @param person the person or null
     */
        private void showPersonDetails(User user) {
        if (user != null) {
            // Fill the labels with info from the person object.
            labelId.setText(Integer.toString(user.getId()));
            LabelNom.setText(user.getNom());
            LabelAge.setText(Integer.toString(user.getAge()));
            labelDateNais.setText(String.valueOf(user.getDateNais().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
     
        } else {
            // Person is null, remove all the text.
            labelId.setText("");
            LabelNom.setText("");
            LabelAge.setText("");
            labelDateNais.setText("");
        }
    }
       
    //ajouter un nouveau user
    @FXML
    public void handleButtonCreate() throws IOException{
        User user = new User();
        boolean buttonValiderClicked = showUserDialog(user);
        if(buttonValiderClicked){
            userDao.create(user);
            afficherTableViewUser();
        }
    }
    //Modifier un user
    @FXML
    public void handleButtonUpdate() throws IOException{
        User user = tableViewUser.getSelectionModel().getSelectedItem();
        if(user != null){
            boolean buttonValiderClicked = showUserDialog(user);
            if(buttonValiderClicked){
                userDao.Update(user);
                afficherTableViewUser();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun user selectionne!!");
            alert.show();
        }

    }
    //suprimer un user
    @FXML
    public void handleButtonDelete() throws IOException{
        User user = tableViewUser.getSelectionModel().getSelectedItem();
        if(user != null){
                userDao.delete(user);
                afficherTableViewUser();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun user selectionne!!");
            alert.show();
        }

    }
    
    //afficher le boite de dialogue
    public boolean showUserDialog(User user) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UserDialogController.class.getResource("/javafx/mvc/view/userDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Formulaire user");
        dialogStage.initModality(Modality.APPLICATION_MODAL.WINDOW_MODAL);
        dialogStage.initOwner(this.primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the user into the controller.
        UserDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setUser(user);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isButtonValiderClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
  
}
