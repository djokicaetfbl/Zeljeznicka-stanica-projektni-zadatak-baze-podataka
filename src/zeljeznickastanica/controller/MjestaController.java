/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.controller;

import java.io.IOException;
import java.net.URL;
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
import zeljeznickastanica.model.dao.MjestoDAO;
import zeljeznickastanica.model.dao.VrstaTeretnogVagonaDAO;
import zeljeznickastanica.model.dto.Mjesto;
import zeljeznickastanica.model.dto.Zaposleni;

/**
 * FXML Controller class
 *
 * @author djord
 */
public class MjestaController implements Initializable {

    @FXML
    private Button bDodaj;

    @FXML
    private Button bIzmjeni;

    @FXML
    private Button bPretrazi;

    @FXML
    private TextField tfPretrazi;

    @FXML
    private TableView mjestaTableView;

    @FXML
    private Button bNazad;

    @FXML
    TableColumn<Mjesto, Integer> postanskiBrojColumn;
    @FXML
    TableColumn<Mjesto, String> nazivMjestaColumn;
    @FXML
    TableColumn<Mjesto, String> drzavaColumn;

    public static ObservableList<Mjesto> mjestaObservaleList = FXCollections.observableArrayList();

    public static boolean booleanDodaj = false;
    public static Mjesto izabranoMjesto, mjestoIzPretrage;

    @FXML
    void dodajButton(ActionEvent event) {
        booleanDodaj = true;
        Parent dodajMjestoView;
        try {
            dodajMjestoView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/DodajMjesto.fxml"));

            Scene dodajMjestoScene = new Scene(dodajMjestoView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(dodajMjestoScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void izmjeniButton(ActionEvent event) {
        booleanDodaj = false;
        ObservableList<Mjesto> izabranaVrsta, mjestaObservaleList;
        mjestaObservaleList = mjestaTableView.getItems();
        izabranaVrsta = mjestaTableView.getSelectionModel().getSelectedItems();
        izabranoMjesto = (Mjesto) izabranaVrsta.get(0);
        Parent dodajMjestoView;
        try {
            dodajMjestoView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/DodajMjesto.fxml"));

            Scene dodajMjestoScene = new Scene(dodajMjestoView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(dodajMjestoScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        booleanDodaj = false;
    }

    @FXML
    void pretraziButton(ActionEvent event) {
        mjestaTableView.getColumns().clear();
        String slovaRegex = "[a-z A-Z]+";
        Pattern pattern = Pattern.compile(slovaRegex);
        if (pattern.matcher(tfPretrazi.getText()).matches()) {
            mjestoIzPretrage = MjestoDAO.pretraziMjesto(tfPretrazi.getText());
            ObservableList<Mjesto> mjestoIzPretrageObservableList = FXCollections.observableArrayList();
            mjestoIzPretrageObservableList.add(mjestoIzPretrage);
            tabelaMjesta(mjestoIzPretrageObservableList);
        } else if (tfPretrazi.getText().equals("")) {
            tabelaMjesta(mjestaObservaleList);
        } else {
            upozorenjePretraga();
        }

    }

    @FXML
    void nazadNaGlavnuFormu(ActionEvent event) {
        Parent mjestoView;
        try {
            mjestoView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/ZeljeznickaStanica.fxml"));

            Scene mjestoScene = new Scene(mjestoView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mjestoScene);
            window.show();

        } catch (IOException ex) {
            Logger.getLogger(MjestaController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void postaviKoloneZaTabeluMjesta(ObservableList zaposleni) {
        postanskiBrojColumn = new TableColumn("Poštanski broj");
        postanskiBrojColumn.setCellValueFactory(new PropertyValueFactory<>("postanskiBroj"));

        nazivMjestaColumn = new TableColumn("Naziv");
        nazivMjestaColumn.setCellValueFactory(new PropertyValueFactory<>("naziv"));

        drzavaColumn = new TableColumn("Država");
        drzavaColumn.setCellValueFactory(new PropertyValueFactory<>("drzava"));

        mjestaTableView.getColumns().addAll(postanskiBrojColumn, nazivMjestaColumn, drzavaColumn);
    }

    public void tabelaMjesta(ObservableList mjesta) {
        mjestaTableView.setItems((ObservableList<Mjesto>) mjesta);
        postaviKoloneZaTabeluMjesta(mjesta);
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
        mjestaTableView.getColumns().clear();
        MjestoDAO.ubaciUTabeluMjesta();
        tabelaMjesta(mjestaObservaleList);
    }

}
