/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import zeljeznickastanica.model.dao.ZaposleniDAO;
import zeljeznickastanica.model.dto.Zaposleni;

/**
 * FXML Controller class
 *
 * @author djord
 */
public class PrikazRadnikaController implements Initializable {

    @FXML
    private TableView radniciTableView;

    @FXML
    private Button bNazad;

    @FXML
    private TableColumn<Zaposleni, String> jmbColumn;
    @FXML
    private TableColumn<Zaposleni, String> imeColumn;
    @FXML
    private TableColumn<Zaposleni, String> prezimeColumn;
    @FXML
    private TableColumn<Zaposleni, String> adresaColumn;
    @FXML
    private TableColumn<Zaposleni, String> brojTelefonaColumn;
    @FXML
    private TableColumn<Zaposleni, Integer> postanskiBrojColumn;
    @FXML
    private TableColumn<Zaposleni, BigDecimal> plataColumn;
    @FXML
    private TableColumn<Zaposleni, LocalDate> datumRodjenjaColumn;
    @FXML
    private TableColumn<Zaposleni, String> zanimanjeColumn;

    public static ObservableList<Zaposleni> radniciObservaleList = FXCollections.observableArrayList();

    private void postaviKoloneZaTabeluZaposlenih(ObservableList zaposleni) {
        jmbColumn = new TableColumn("JMB");
        jmbColumn.setCellValueFactory(new PropertyValueFactory<>("JMB"));

        imeColumn = new TableColumn("Ime");
        imeColumn.setCellValueFactory(new PropertyValueFactory<>("ime"));

        prezimeColumn = new TableColumn("Prezime");
        prezimeColumn.setCellValueFactory(new PropertyValueFactory<>("prezime"));

        adresaColumn = new TableColumn("Adresa");
        adresaColumn.setCellValueFactory(new PropertyValueFactory<>("adresa"));

        brojTelefonaColumn = new TableColumn("Broj telefona");
        brojTelefonaColumn.setCellValueFactory(new PropertyValueFactory<>("brojTelefona"));

        postanskiBrojColumn = new TableColumn("PostanskiBroj");
        postanskiBrojColumn.setCellValueFactory(new PropertyValueFactory<>("postanskiBroj"));

        plataColumn = new TableColumn("Plata");
        plataColumn.setCellValueFactory(new PropertyValueFactory<>("plata"));

        datumRodjenjaColumn = new TableColumn("Datum rodjenja");
        datumRodjenjaColumn.setCellValueFactory(new PropertyValueFactory<>("datumRodjenja"));

        zanimanjeColumn = new TableColumn<>("Zanimanje");
        zanimanjeColumn.setCellValueFactory(new PropertyValueFactory<>("zanimanje"));

        radniciTableView.getColumns().addAll(jmbColumn, imeColumn, prezimeColumn, datumRodjenjaColumn, adresaColumn,
                brojTelefonaColumn, postanskiBrojColumn, plataColumn, zanimanjeColumn);
    }

    public void tabelaRadnika(ObservableList zaposleni) {
        radniciTableView.setItems((ObservableList<Zaposleni>) zaposleni);
        postaviKoloneZaTabeluZaposlenih(zaposleni);
    }

    @FXML
    void nazadNaGlavnuFormu(ActionEvent event) {
        Parent dodajZaposlenogView;
        try {
            dodajZaposlenogView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/ZeljeznickaStanica.fxml"));

            Scene dodajZaposlenogScene = new Scene(dodajZaposlenogView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(dodajZaposlenogScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(ZeljeznickaStanicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bNazad.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/zeljeznickastanica/resursi/back.png"))));
        radniciTableView.getColumns().clear();

        ZaposleniDAO.ubaciUTabeluRadnika();
        tabelaRadnika(radniciObservaleList);
    }

}
