package metier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

public class IMetier implements Initializable {
    private Connection connx;

    public TableView<Professeur> tableView;
    public TableView<Departement> departTableView;
    @FXML
    private HBox profButtons;
    @FXML
    private HBox departButtons;
    @FXML
    private ChoiceBox<String> departChoicebox;
    @FXML
    private TextField fieldSearch;
    @FXML
    private TextField departFieldSearch;
    @FXML
    private TableColumn<Professeur, Integer> ID;
    @FXML
    private TableColumn<Professeur, Departement> Departement;
    @FXML
    private TableColumn<Professeur, String> Nom;
    @FXML
    private TableColumn<Professeur, String> Prenom;
    @FXML
    private TableColumn<Professeur, String> CIN;
    @FXML
    private TableColumn<Professeur, String> Adresse;
    @FXML
    private TableColumn<Professeur, String> Email;
    @FXML
    private TableColumn<Professeur, String> Telephone;
    @FXML
    private TableColumn<Professeur, LocalDate> Date_recrutement;
    @FXML
    private TableColumn<Departement, Integer> ID_Depart;
    @FXML
    private TableColumn<Departement, String> Nom_Depart;

    private ObservableList<Professeur>  profsData = FXCollections.observableArrayList();
    private ObservableList<Departement>  departsData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ID.setCellValueFactory(new PropertyValueFactory<Professeur, Integer>("id_prof"));
        Departement.setCellValueFactory(new PropertyValueFactory<Professeur, Departement>("Departement"));
        Nom.setCellValueFactory(new PropertyValueFactory<Professeur, String>("nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<Professeur, String>("prenom"));
        CIN.setCellValueFactory(new PropertyValueFactory<Professeur, String>("cin"));
        Adresse.setCellValueFactory(new PropertyValueFactory<Professeur, String>("adresse"));
        Telephone.setCellValueFactory(new PropertyValueFactory<Professeur, String>("telephone"));
        Email.setCellValueFactory(new PropertyValueFactory<Professeur, String>("email"));
        Date_recrutement.setCellValueFactory(new PropertyValueFactory<Professeur, LocalDate>("date_recrutement"));

        ID_Depart.setCellValueFactory(new PropertyValueFactory<Departement, Integer>("id_depart"));
        Nom_Depart.setCellValueFactory(new PropertyValueFactory<Departement, String>("nom"));

        try {
            connx= SignletonConnexionDB.getConnection();
            Statement stm=connx.createStatement();
            ResultSet rs=stm.executeQuery("select * from Profs INNER JOIN departement ON Profs.departement = departement.id_depart");
            while (rs.next()) {
                Professeur profData = new Professeur(rs.getString("nom"), rs.getString("prenom"), rs.getString("cin"), rs.getString("adresse"), rs.getString("telephone"), rs.getString("email"),rs.getDate("date_recrutement").toLocalDate(),rs.getInt("id_prof"), new Departement(rs.getInt("id_depart"),rs.getString("nomDepart")));
                profsData.add(profData);
            }
            tableView.setItems(profsData);

            ArrayList<String> departements = new ArrayList<String>();
            departements.add("All");
            ResultSet rsDepart=stm.executeQuery("select * from departement");
            while (rsDepart.next()) {
                Departement departData = new Departement(rsDepart.getInt("id_depart"),rsDepart.getString("nomDepart"));
                departsData.add(departData);
                departements.add(rsDepart.getInt("id_depart") + "- " + rsDepart.getString("nomDepart"));
            }
            departTableView.setItems(departsData);
            ObservableList<String> departlist = FXCollections.observableArrayList(departements);
            departChoicebox.setItems(departlist);
            departChoicebox.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openAddProfWindow(ActionEvent event) throws Exception{
        Group root = new Group();
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gui/addProfLayout.fxml"));
        BorderPane frame = fxmlLoader.load();

        AddProfController secondaryController = (AddProfController) fxmlLoader.getController();
        secondaryController.addProfTableView = tableView;
        secondaryController.profsDatas = profsData;

        root.getChildren().add(frame);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/styles.css").toString());
        stage.setTitle("Ajouter un professeur");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();
    }
    @FXML
    public void openAddDepartWindow(ActionEvent event) throws Exception{
        Group root = new Group();
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gui/addDepartLayout.fxml"));
        BorderPane frame = fxmlLoader.load();

        AddDepartController secondaryController = (AddDepartController) fxmlLoader.getController();
        secondaryController.addDepartTableView = departTableView;
        secondaryController.departsDatas = departsData;

        root.getChildren().add(frame);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/styles.css").toString());
        stage.setTitle("Ajouter un departement");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();
    }

    @FXML
    public void openProfModifyWindow(ActionEvent event) throws Exception{
        Group root = new Group();
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gui/modifyProfLayout.fxml"));
        BorderPane frame = fxmlLoader.load();

        ModifyProfController secondaryController = (ModifyProfController) fxmlLoader.getController();
        secondaryController.modifyProfTableView = tableView;
        secondaryController.profsDatas = profsData;
        Professeur professeur= (Professeur) tableView.getSelectionModel().getSelectedItem();
        root.getChildren().add(frame);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/styles.css").toString());
        stage.setTitle("Modifier un professeur");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        if(professeur != null) {
            secondaryController.setSelecetdindex(tableView.getSelectionModel().getSelectedIndex());
            secondaryController.getFieldID().setText(Integer.toString(professeur.getId_prof()));
            secondaryController.getFieldNom().setText(professeur.getNom());
            secondaryController.getFieldPrenom().setText(professeur.getPrenom());
            secondaryController.getFieldCIN().setText(professeur.getCin());
            secondaryController.getFieldAdresse().setText(professeur.getAdresse());
            secondaryController.getFieldTelephone().setText(professeur.getTelephone());
            secondaryController.getFieldEmail().setText(professeur.getEmail());
            secondaryController.getFieldDate().setValue(professeur.getDate_recrutement());
            secondaryController.getFieldDepartement().getSelectionModel().select(professeur.getDepartement().getId_depart() + "- " + professeur.getDepartement().getNom());
            stage.show();
        }else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez sélectionner un Professeur !");
            alert.show();
        }
    }
    @FXML
    public void openDepartModifyWindow(ActionEvent event) throws Exception{
        Group root = new Group();
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gui/modifyDepartLayout.fxml"));
        BorderPane frame = fxmlLoader.load();

        ModifyDepartController secondaryController = (ModifyDepartController) fxmlLoader.getController();
        secondaryController.modifyDepartTableView = departTableView;
        secondaryController.departsDatas = departsData;
        Departement departement= (Departement) departTableView.getSelectionModel().getSelectedItem();
        root.getChildren().add(frame);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/styles.css").toString());
        stage.setTitle("Modifier un departement");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        if(departement != null) {
            secondaryController.setSelecetdindex(departTableView.getSelectionModel().getSelectedIndex());
            secondaryController.getFieldID().setText(Integer.toString(departement.getId_depart()));
            secondaryController.getFieldNom().setText(departement.getNom());
            stage.show();
        }else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez sélectionner un Departement !");
            alert.show();
        }
    }

    @FXML
    public void supprimerProf(){
        int indice=tableView.getSelectionModel().getSelectedIndex();
        Professeur professeur= (Professeur) tableView.getSelectionModel().getSelectedItem();
        try {
            Statement stm=connx.createStatement();
            if(professeur != null) {
            stm.executeUpdate("DELETE FROM Profs WHERE id_prof='"+ professeur.getId_prof() +"'");
            Alert alert1=new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setHeaderText("Suppression");
            alert1.setContentText("Professeur"+ tableView.getSelectionModel().getSelectedItems().toArray()[0].toString().split("Nom:")[1].split(",")[0] +" Supprimé !");
            alert1.show();
                tableView.getItems().remove(indice);
            }else{
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Veuillez sélectionner un Professeur !");
                alert.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void supprimerDepart(){
        int indice=departTableView.getSelectionModel().getSelectedIndex();
        Departement departement= (Departement) departTableView.getSelectionModel().getSelectedItem();
        try {
            Statement stm=connx.createStatement();
            if(departement != null) {
                stm.executeUpdate("DELETE FROM departement WHERE id_depart='"+ departement.getId_depart() +"'");
                Alert alert1=new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setHeaderText("Suppression");
                System.out.println();
                alert1.setContentText("Departement "+ departTableView.getSelectionModel().getSelectedItems().toArray()[0].toString() +" Supprimé !");
                alert1.show();
                departTableView.getItems().remove(indice);
            }else{
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Veuillez sélectionner un Departement !");
                alert.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void departTrierListe(ActionEvent event){
        try {
            Statement stm=connx.createStatement();
            ChoiceBox choiceBox = (ChoiceBox) event.getSource();
            String choixDepart;
            ResultSet rs;
            if (choiceBox.getValue() == "All") {
                rs=stm.executeQuery("SELECT * FROM Profs INNER JOIN departement ON Profs.departement = departement.id_depart");
                tableView.getItems().clear();
                while (rs.next()) {
                    Professeur profData = new Professeur(rs.getString("nom"), rs.getString("prenom"), rs.getString("cin"), rs.getString("adresse"), rs.getString("telephone"), rs.getString("email"),rs.getDate("date_recrutement").toLocalDate(),rs.getInt("id_prof"), new Departement(rs.getInt("id_depart"),rs.getString("nomDepart")));
                    profsData.add(profData);
                }
                tableView.setItems(profsData);
            } else {
                choixDepart = choiceBox.getValue().toString().split("-",2)[0];
                rs=stm.executeQuery("select * from Profs INNER JOIN departement ON Profs.departement = departement.id_depart WHERE id_depart Like '%"+choixDepart+"%'");
                tableView.getItems().clear();
                while (rs.next()) {
                    Professeur profData = new Professeur(rs.getString("nom"), rs.getString("prenom"), rs.getString("cin"), rs.getString("adresse"), rs.getString("telephone"), rs.getString("email"),rs.getDate("date_recrutement").toLocalDate(),rs.getInt("id_prof"), new Departement(rs.getInt("id_depart"),rs.getString("nomDepart")));
                    profsData.add(profData);
                }
                tableView.setItems(profsData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public  void chercherProfesseur(){
        try {
            Statement stm=connx.createStatement();
            String search=fieldSearch.getText();
            ResultSet rs=stm.executeQuery("SELECT * FROM Profs INNER JOIN departement ON Profs.departement = departement.id_depart WHERE nom Like '%"+search+"%' OR prenom Like '%"+search+"%' OR cin Like '%"+search+"%' OR telephone Like '%"+search+"%' OR email Like '%"+search+"%' OR adresse Like '%"+search+"%' OR date_recrutement Like '%"+search+"%'");
            tableView.getItems().clear();
            while (rs.next()) {
                Professeur profData = new Professeur(rs.getString("nom"), rs.getString("prenom"), rs.getString("cin"), rs.getString("adresse"), rs.getString("telephone"), rs.getString("email"),rs.getDate("date_recrutement").toLocalDate(),rs.getInt("id_prof"), new Departement(rs.getInt("id_depart"),rs.getString("nomDepart")));
                profsData.add(profData);
            }
            tableView.setItems(profsData);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public  void chercherDepartement(){
        try {
            Statement stm=connx.createStatement();
            String search=departFieldSearch.getText();
            ResultSet rs=stm.executeQuery("SELECT * FROM departement WHERE id_depart Like '%"+search+"%' OR nomDepart Like '%"+search+"%'");
            departTableView.getItems().clear();
            while (rs.next()) {
                Departement departData = new Departement(rs.getInt("id_depart"),rs.getString("nomDepart"));
                departsData.add(departData);
            }
            departTableView.setItems(departsData);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void changeTables(ActionEvent event){
        Button button = (Button) event.getSource();
        if (button.getId() == "DP") {
            button.setText("Professeurs");
            profButtons.setVisible(true);
            profButtons.setManaged(true);
            tableView.setVisible(true);
            tableView.setManaged(true);
            departButtons.setVisible(false);
            departButtons.setManaged(false);
            departTableView.setVisible(false);
            departTableView.setManaged(false);
            button.setId("PR");
        } else {
            button.setText("Departements");
            profButtons.setVisible(false);
            profButtons.setManaged(false);
            tableView.setVisible(false);
            tableView.setManaged(false);
            departButtons.setVisible(true);
            departButtons.setManaged(true);
            departTableView.setVisible(true);
            departTableView.setManaged(true);
            button.setId("DP");
        }
    }
}
