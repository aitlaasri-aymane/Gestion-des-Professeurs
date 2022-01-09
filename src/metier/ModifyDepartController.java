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

public class ModifyDepartController implements Initializable{

    private int selecetdindex;
    @FXML
    private TextField fieldID;
    @FXML
    private TextField fieldNom;

    public TableView<Departement> modifyDepartTableView;
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
            modifyDepartTableView.setItems(departsDatas);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void modifierDepart(){
        try {
            Statement stm=connx.createStatement();
            String ID = fieldID.getText();
            String nom=fieldNom.getText();
            stm.executeUpdate("UPDATE departement SET nomDepart = '"+nom+"' WHERE id_depart = '"+ ID +"'");

            Departement departData = new Departement(Integer.parseInt(ID),nom);
            modifyDepartTableView.getItems().set(selecetdindex, departData);

            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText("Modification");
            alert1.setContentText("Le departement " + nom +" a bien été modifié !");
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

    public void setSelecetdindex(int selecetdindex) {
        this.selecetdindex = selecetdindex;
    }
}
