/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import zeljeznickastanica.model.dao.VrstaTeretnogVagonaDAO;
import zeljeznickastanica.model.dto.Mjesto;
import zeljeznickastanica.model.dto.VrstaTeretnogVagona;

/**
 * FXML Controller class
 *
 * @author djord
 */
public class VrstaTeretnogVagonaController implements Initializable {

    @FXML
    private Button bDodaj;

    @FXML
    private Button bIzmjeni;

    @FXML
    private Button bPretrazi;

    @FXML
    private Button bNazad;
    @FXML
    private TextField tfPretrazi;
    public static boolean booleanDodajVTV = false;

    @FXML
    TableColumn<VrstaTeretnogVagona, String> tipColumn;
    @FXML
    TableColumn<VrstaTeretnogVagona, String> nazivColumn;
    @FXML
    private TableView vrstaTeretnogVagonaTableView;

    public static ObservableList<VrstaTeretnogVagona> vtvObservaleList = FXCollections.observableArrayList();

    public static VrstaTeretnogVagona izabranoVTV;//, vtvIzPretrage;
    public static List<VrstaTeretnogVagona> vtvIzPretrage;

    private void postaviKoloneZaTabeluVTV(ObservableList vtv) {
        tipColumn = new TableColumn("Tip");
        tipColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));

        nazivColumn = new TableColumn("Naziv");
        nazivColumn.setCellValueFactory(new PropertyValueFactory<>("naziv"));

        vrstaTeretnogVagonaTableView.getColumns().addAll(tipColumn, nazivColumn);
    }

    public void tabelaTVT(ObservableList vtv) {
        vrstaTeretnogVagonaTableView.setItems((ObservableList<VrstaTeretnogVagona>) vtv);
        postaviKoloneZaTabeluVTV(vtv);
    }

    @FXML
    void dodajVrstuTeretnogVagona(ActionEvent event) {
        booleanDodajVTV = true;
        Parent vrstaTVView;
        try {
            vrstaTVView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/DadajVrstuTeretnogVagona.fxml"));

            Scene vrstaTVScene = new Scene(vrstaTVView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(vrstaTVScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void izmjeniVrstuTeretnogVagona(ActionEvent event) {
        booleanDodajVTV = false;

        ObservableList<VrstaTeretnogVagona> izabranaVrsta, vtvObservaleList;
        vtvObservaleList = vrstaTeretnogVagonaTableView.getItems();
        izabranaVrsta = vrstaTeretnogVagonaTableView.getSelectionModel().getSelectedItems();
        izabranoVTV = (VrstaTeretnogVagona) izabranaVrsta.get(0);
        Parent vrstaTVView;
        try {
            vrstaTVView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/DadajVrstuTeretnogVagona.fxml"));

            Scene vrstaTVScene = new Scene(vrstaTVView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(vrstaTVScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        booleanDodajVTV = false;
    }

    @FXML
    void nazadNaGlavnuFormu(ActionEvent event) {
        Parent vrstaTVView;
        try {
            vrstaTVView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/ZeljeznickaStanica.fxml"));

            Scene vrstaTVScene = new Scene(vrstaTVView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(vrstaTVScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void pretraziVrstuTeretnogVagona(ActionEvent event) {
        vrstaTeretnogVagonaTableView.getColumns().clear();
        //String slovaRegex = "[a-z A-Z]+";
        // Pattern pattern = Pattern.compile(slovaRegex);
        // if (pattern.matcher(tfPretrazi.getText()).matches()) {
        vtvIzPretrage = VrstaTeretnogVagonaDAO.pretraziVTV(tfPretrazi.getText());
        ObservableList<VrstaTeretnogVagona> vtvIzPretrageObservableList = FXCollections.observableArrayList();
        vtvIzPretrageObservableList.addAll(vtvIzPretrage);
        tabelaTVT(vtvIzPretrageObservableList);
        //} else 
        if (tfPretrazi.getText().equals("")) {
            tabelaTVT(vtvObservaleList);
        }// else {
        //    upozorenjePretraga();
        //}
    }

    private void upozorenjePretraga() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Provjerite polje za unos pretrage.");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vrstaTeretnogVagonaTableView.getColumns().clear();
        VrstaTeretnogVagonaDAO.ubaciUTabeluVTV();
        tabelaTVT(vtvObservaleList);
    }

}
