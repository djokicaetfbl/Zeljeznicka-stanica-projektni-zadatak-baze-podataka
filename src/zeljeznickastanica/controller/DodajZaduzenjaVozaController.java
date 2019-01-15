/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import zeljeznickastanica.model.dao.ConnectionPool;
import zeljeznickastanica.model.dao.ZaduzenjaVozovaDAO;
import zeljeznickastanica.model.dto.Mjesto;
import zeljeznickastanica.model.dto.ZaduzenjaVozova;

/**
 * FXML Controller class
 *
 * @author djord
 */
public class DodajZaduzenjaVozaController implements Initializable {

    @FXML
    private ComboBox<String> cmbJMBMasinovodja;

    @FXML
    private ComboBox<String> cmbVozID;

    @FXML
    private TextField tfOd;

    @FXML
    private TextField tfDo;

    @FXML
    private Button bOdustani;

    @FXML
    private Button bPotvrdi;

    private void ubaciUCMBJMBMasinovodja() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select jmb from masinovodja");
            while (rs.next()) {
                cmbJMBMasinovodja.getItems().add(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DodajZaposlenogController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DodajZaposlenogController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    private void ubaciUCMBVozID() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select vozid from voz");
            while (rs.next()) {
                cmbVozID.getItems().add(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DodajZaposlenogController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DodajZaposlenogController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void unesiZaduzenjeVoza() {
        if (cmbJMBMasinovodja.getValue() != null && cmbVozID != null && !tfOd.getText().isEmpty() && !tfDo.getText().isEmpty()) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (!tfDo.getText().matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")) {
                upozorenjePogresanDatum();
                return;
            }

            ZaduzenjaVozova zv = new ZaduzenjaVozova();

            zv.setJmbMasinovodje(cmbJMBMasinovodja.getValue().toString());
            zv.setVozid(cmbVozID.getValue().toString());
            zv.setOd(tfOd.getText());
            zv.setDoo(tfDo.getText());

            //sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (!sdf.parse(tfOd.getText()).before(sdf.parse(tfDo.getText()))) {
                    upozorenjeNepravilanUnosDatumDoKojegVrijediZaduzenje();
                }
            } catch (ParseException ex) {
                Logger.getLogger(DodajZaduzenjaVozaController.class.getName()).log(Level.SEVERE, null, ex);
            }

            ZaduzenjaVozovaDAO.dodajZaduzenjeVoza(zv);
        } else {
            upozorenjePoljaSuPrazna();
        }
    }

    private void upozorenjeNepravilanUnosDatumDoKojegVrijediZaduzenje() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Nepravilan unos datuma do kojeg vrijedi zaduzenje.");
        alert.showAndWait();
    }

    private void upozorenjePoljaSuPrazna() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Provjerite polja za unos podataka.");
        alert.showAndWait();
    }

    private void upozorenjePogresanDatum() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa datuma rodjenja !");
        alert.setHeaderText(null);
        alert.setContentText("Provjerite da li je unos datuma ispravan.");
        alert.showAndWait();
    }

    @FXML
    void nazadNaZaduzivanjeVoza(ActionEvent event) {
        Parent mjestoView;
        try {
            mjestoView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/ZaduzenjaVozova.fxml"));

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
    void potvrdiZaduzivanjeVoza(ActionEvent event) {
        unesiZaduzenjeVoza();
        Parent mjestoView;
        try {
            mjestoView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/ZaduzenjaVozova.fxml"));

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
        tfOd.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        tfOd.setEditable(false);
        ubaciUCMBJMBMasinovodja();
        ubaciUCMBVozID();
    }

}
