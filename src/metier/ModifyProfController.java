package metier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModifyProfController implements Initializable{

    private int selecetdindex;
    @FXML
    private TextField fieldID;
    @FXML
    private TextField fieldNom;
    @FXML
    private TextField fieldPrenom;
    @FXML
    private TextField fieldCIN;
    @FXML
    private TextField fieldAdresse;
    @FXML
    private TextField fieldTelephone;
    @FXML
    private TextField fieldEmail;
    @FXML
    private ChoiceBox<String> fieldDepartement;
    @FXML
    private DatePicker fieldDate;

    public TableView<Professeur> modifyProfTableView;
    @FXML
    private TableColumn<Professeur, Integer> profID;
    @FXML
    private TableColumn<Professeur, Departement> profDepartement;
    @FXML
    private TableColumn<Professeur, String> profNom;
    @FXML
    private TableColumn<Professeur, String> profPrenom;
    @FXML
    private TableColumn<Professeur, String> profCIN;
    @FXML
    private TableColumn<Professeur, String> profAdresse;
    @FXML
    private TableColumn<Professeur, String> profEmail;
    @FXML
    private TableColumn<Professeur, String> profTelephone;
    @FXML
    private TableColumn<Professeur, LocalDate> profDate_recrutement;
    @FXML
    private Connection connx;
    public ObservableList<Professeur> profsDatas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        profID.setCellValueFactory(new PropertyValueFactory<Professeur, Integer>("id_prof"));
        profDepartement.setCellValueFactory(new PropertyValueFactory<Professeur, Departement>("Departement"));
        profNom.setCellValueFactory(new PropertyValueFactory<Professeur, String>("nom"));
        profPrenom.setCellValueFactory(new PropertyValueFactory<Professeur, String>("prenom"));
        profCIN.setCellValueFactory(new PropertyValueFactory<Professeur, String>("cin"));
        profAdresse.setCellValueFactory(new PropertyValueFactory<Professeur, String>("adresse"));
        profTelephone.setCellValueFactory(new PropertyValueFactory<Professeur, String>("telephone"));
        profEmail.setCellValueFactory(new PropertyValueFactory<Professeur, String>("email"));
        profDate_recrutement.setCellValueFactory(new PropertyValueFactory<Professeur, LocalDate>("date_recrutement"));

        try {
            connx= SignletonConnexionDB.getConnection();
            Statement stm=connx.createStatement();
            ResultSet rs=stm.executeQuery("select * from Profs INNER JOIN departement ON Profs.departement = departement.id_depart;");
            while (rs.next()) {
                Professeur profData = new Professeur(rs.getString("nom"), rs.getString("prenom"), rs.getString("cin"), rs.getString("adresse"), rs.getString("telephone"), rs.getString("email"),rs.getDate("date_recrutement").toLocalDate(),rs.getInt("id_prof"), new Departement(rs.getInt("id_depart"),rs.getString("nomDepart")));
                profsDatas.add(profData);
            }
            modifyProfTableView.setItems(profsDatas);

            ArrayList<String> departements = new ArrayList<String>();
            ResultSet rsDepart=stm.executeQuery("select * from departement");
            while (rsDepart.next()) {
                departements.add(rsDepart.getInt("id_depart") + "- " + rsDepart.getString("nomDepart"));
            }
            ObservableList<String> departlist = FXCollections.observableArrayList(departements);
            fieldDepartement.setItems(departlist);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void modifierProf(){
        try {
            Statement stm=connx.createStatement();
            String ID = fieldID.getText();
            String nom=fieldNom.getText();
            String prenom=fieldPrenom.getText();
            String cin=fieldCIN.getText();
            String adresse=fieldAdresse.getText();
            String telephone=fieldTelephone.getText();
            String email=fieldEmail.getText();
            Departement profDepartement = new Departement(Integer.parseInt(fieldDepartement.getValue().toString().split("-",2)[0]), fieldDepartement.getValue().toString().split(" ",2)[1]);
            LocalDate date= fieldDate.getValue();
            stm.executeUpdate("UPDATE Profs SET departement = '"+profDepartement.getId_depart()+"', nom = '"+nom+"', prenom = '"+prenom+"', cin = '"+cin+"', adresse = '"+adresse+"', telephone = '"+telephone+"', email = '"+email+"',date_recrutement = '"+date+"' WHERE id_prof = '"+ ID +"'");

            Professeur profData = new Professeur(nom,prenom,cin,adresse,telephone,email,date,Integer.parseInt(ID),profDepartement);
            modifyProfTableView.getItems().set(selecetdindex, profData);

            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText("Modification");
            alert1.setContentText("Le professeur " + nom +" a bien été modifié !");
            alert1.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public TextField getFieldID() {
        return fieldID;
    }

    public TextField getFieldNom() {
        return fieldNom;
    }

    public TextField getFieldPrenom() {
        return fieldPrenom;
    }

    public TextField getFieldCIN() {
        return fieldCIN;
    }

    public TextField getFieldAdresse() {
        return fieldAdresse;
    }

    public TextField getFieldTelephone() {
        return fieldTelephone;
    }

    public TextField getFieldEmail() {
        return fieldEmail;
    }

    public ChoiceBox<String> getFieldDepartement() {
        return fieldDepartement;
    }

    public DatePicker getFieldDate() {
        return fieldDate;
    }

    public void setSelecetdindex(int selecetdindex) {
        this.selecetdindex = selecetdindex;
    }
}
