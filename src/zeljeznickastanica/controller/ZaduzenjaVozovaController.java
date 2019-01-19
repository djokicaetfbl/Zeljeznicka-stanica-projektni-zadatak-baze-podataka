/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import zeljeznickastanica.model.dao.ZaduzenjaVozovaDAO;
import zeljeznickastanica.model.dto.ZaduzenjaVozova;

/**
 * FXML Controller class
 *
 * @author djord
 */
public class ZaduzenjaVozovaController implements Initializable {

    @FXML
    private Button bDodaj;

    @FXML
    private Button bPretrazi;

    @FXML
    private Button bNazad;
    @FXML
    TableColumn<ZaduzenjaVozova, String> jmbColumn;
    @FXML
    TableColumn<ZaduzenjaVozova, String> imeColumn;
    @FXML
    TableColumn<ZaduzenjaVozova, String> prezimeColumn;
    @FXML
    TableColumn<ZaduzenjaVozova, String> vozidColumn;
    @FXML
    private TextField tfPretrazi;
    //@FXML
    //TableColumn<ZaduzenjaVozova, LocalDate> oDColumn;
    //@FXML
    //TableColumn<ZaduzenjaVozova, LocalDate> dOColumn;
    @FXML
    TableColumn<ZaduzenjaVozova, String> oDColumn;
    @FXML
    TableColumn<ZaduzenjaVozova, String> dOColumn;
    public static ZaduzenjaVozova izabranoZaduzenje;
    public static List<ZaduzenjaVozova> zaduzenjeIzPretrage;

    @FXML
    private TableView zaduzenjaVozovaTableView;

    public static ObservableList<ZaduzenjaVozova> zvObservaleList = FXCollections.observableArrayList();

    private void postaviKoloneZaTabeluZaduzenjaVozova(ObservableList zvObservableList) {
        jmbColumn = new TableColumn("JMB");
        jmbColumn.setCellValueFactory(new PropertyValueFactory<>("jmbMasinovodje"));

        imeColumn = new TableColumn("Ime");
        imeColumn.setCellValueFactory(new PropertyValueFactory<>("ime"));

        prezimeColumn = new TableColumn("Prezime");
        prezimeColumn.setCellValueFactory(new PropertyValueFactory<>("prezime"));

        vozidColumn = new TableColumn("Voz id");
        vozidColumn.setCellValueFactory(new PropertyValueFactory<>("vozid"));

        oDColumn = new TableColumn("Od");
        oDColumn.setCellValueFactory(new PropertyValueFactory<>("od"));

        dOColumn = new TableColumn("Do");
        dOColumn.setCellValueFactory(new PropertyValueFactory<>("doo"));

        zaduzenjaVozovaTableView.getColumns().addAll(jmbColumn, imeColumn, prezimeColumn, vozidColumn, oDColumn, dOColumn);
    }

    public void tabelaMjesta(ObservableList zv) {
        zaduzenjaVozovaTableView.setItems((ObservableList<ZaduzenjaVozova>) zv);
        postaviKoloneZaTabeluZaduzenjaVozova(zv);
    }

    @FXML
    void dodajZaduzenje(ActionEvent event) {
        Parent mjestoView;
        try {
            mjestoView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/DodajZaduzenjaVoza.fxml"));

            Scene mjestoScene = new Scene(mjestoView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mjestoScene);
            window.show();

        } catch (IOException ex) {
            Logger.getLogger(MjestaController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void pretraziZaduzenje(ActionEvent event) {
        zaduzenjaVozovaTableView.getColumns().clear();
        String jmbRegex = "\\d+";
        Pattern pattern = Pattern.compile(jmbRegex);
        if (pattern.matcher(tfPretrazi.getText()).matches() && (tfPretrazi.getText().length() == 13)) {
            zaduzenjeIzPretrage = ZaduzenjaVozovaDAO.pretraziZaduzenjaVozova(tfPretrazi.getText());
            ObservableList<ZaduzenjaVozova> zaduzenjeIzPretrageObservableList = FXCollections.observableArrayList();
            zaduzenjeIzPretrageObservableList.addAll(zaduzenjeIzPretrage);
            tabelaMjesta(zaduzenjeIzPretrageObservableList);
        } else if (tfPretrazi.getText().equals("")) {
            tabelaMjesta(zvObservaleList);
        } else {
            upozorenjePretraga();
        }
    }

    private void upozorenjePretraga() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Provjerite polja za pretragu po JMB-u.");
        alert.showAndWait();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bNazad.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/zeljeznickastanica/resursi/back.png"))));
        bDodaj.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/zeljeznickastanica/resursi/rsz_plus.png"))));
        bPretrazi.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/zeljeznickastanica/resursi/search.png"))));
        zaduzenjaVozovaTableView.getColumns().clear();
        ZaduzenjaVozovaDAO.ubaciUTabeluZaduzenjaVozova();
        tabelaMjesta(zvObservaleList);
    }

}
