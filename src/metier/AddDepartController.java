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
import java.util.ResourceBundle;

public class AddDepartController implements Initializable{
    @FXML
    private TextField fieldNom;

    public TableView<Departement> addDepartTableView;
    @FXML
    private TableColumn<Departement, Integer> departID_Depart;
    @FXML
    private TableColumn<Departement, String> departNom_Depart;

    private Connection connx;
    public ObservableList<Departement> departsDatas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        departID_Depart.setCellValueFactory(new PropertyValueFactory<Departement, Integer>("id_depart"));
        departNom_Depart.setCellValueFactory(new PropertyValueFactory<Departement, String>("nom"));
        try {
            connx= SignletonConnexionDB.getConnection();
            Statement stm=connx.createStatement();
            ResultSet rs=stm.executeQuery("select * from departement");
            while (rs.next()) {
                Departement departData = new Departement(rs.getInt("id_depart"),rs.getString("nomDepart"));
                departsDatas.add(departData);
            }
            addDepartTableView.setItems(departsDatas);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void ajouterDepart(){
        try {
            Statement stm=connx.createStatement();
            String nom=fieldNom.getText();
            stm.executeUpdate("insert into departement (`id_depart`,`nomDepart`) values (null ,'"+nom+"')");
            ResultSet rs=stm.executeQuery("SELECT * FROM departement ORDER BY id_depart DESC LIMIT 1");
            while (rs.next()) {
                Departement departData = new Departement(rs.getInt("id_depart"),nom);
                departsDatas.add(departData);;
            }
            addDepartTableView.setItems(departsDatas);
            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText("Ajout");
            alert1.setContentText("Departement " + nom +" Ajouté !");
            alert1.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
