/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import zeljeznickastanica.model.dao.MjestoDAO;
import zeljeznickastanica.model.dto.Mjesto;
import zeljeznickastanica.model.dto.Voz;

/**
 * FXML Controller class
 *
 * @author djord
 */
public class DodajMjestoController implements Initializable {

    @FXML
    private TextField tfPostanskiBroj;

    @FXML
    private TextField tfNaziv;

    @FXML
    private TextField tfDrzava;

    @FXML
    private Button bPotvrdi;

    @FXML
    private Button bOdustani;

    private Mjesto mjesto;

    private void unesiMjesto() {
        if (!tfPostanskiBroj.getText().isEmpty() && !tfNaziv.getText().isEmpty() && !tfDrzava.getText().isEmpty()) {
            String postanskiBrojRegex = "\\d+";
            Pattern pattern = Pattern.compile(postanskiBrojRegex);
            if (!pattern.matcher(tfPostanskiBroj.getText()).matches() || tfPostanskiBroj.getText().length() > 20) {
                upozorenjeNeispravanPostanskiBroj();
                return;
            }

            if (MjestaController.booleanDodaj && provjeriMjestoIDUBazi(Integer.parseInt(tfPostanskiBroj.getText()))) {
                upozorenjeMjestoID();
                return;
            }

            String slovaRegex = "[a-z A-Z]+";
            pattern = Pattern.compile(slovaRegex);

            if (!pattern.matcher(tfNaziv.getText()).matches() || tfNaziv.getText().length() > 20) {
                upozorenjeNeispravanUnos();
                return;
            }

            if (!pattern.matcher(tfNaziv.getText()).matches() || tfDrzava.getText().length() > 20) {
                upozorenjeNeispravanUnos();
                return;
            }

            Mjesto mjesto = new Mjesto();

            Integer postanskiBroj = Integer.parseInt(tfPostanskiBroj.getText());
            if (!MjestaController.booleanDodaj) {
                System.out.println("usaoo");
                mjesto = MjestaController.izabranoMjesto;
            }
            mjesto.setPostanskiBroj(postanskiBroj);
            mjesto.setNaziv(tfNaziv.getText());
            mjesto.setDrzava(tfDrzava.getText());
            if (MjestaController.booleanDodaj) {
                MjestoDAO.dodajMjesto(mjesto);
            } else {
                MjestoDAO.izmjeniMjesto(mjesto);
                MjestaController.booleanDodaj = false;
            }

        } else {
            upozorenjePoljaSuPrazna();
        }
    }

    private void upozorenjeMjestoID() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Postanski broj vec postoji u bazi!");
        alert.showAndWait();
    }

    private void upozorenjeNeispravanUnos() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Neispravan unos!.");
        alert.showAndWait();
    }

    private void upozorenjeNeispravanPostanskiBroj() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Provjerite postanski broj.");
        alert.showAndWait();
    }

    private boolean provjeriMjestoIDUBazi(Integer postanskiBroj) {
        final int zaPoredjenje = postanskiBroj;
        for (Mjesto m : MjestaController.mjestaObservaleList) {
            if (m.getPostanskiBroj() == postanskiBroj) {
                return true;
            }
        }
        return false;
    }

    private void upozorenjePoljaSuPrazna() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Provjerite polja za unos podataka.");
        alert.showAndWait();
    }

    @FXML
    void odustaniOdUnosaMjesta(ActionEvent event) {
        Parent mjestoView;
        try {
            mjestoView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/Mjesta.fxml"));

            Scene mjestoScene = new Scene(mjestoView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mjestoScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void potvrdiUnosMjesta(ActionEvent event) {
        unesiMjesto();
        Parent mjestoView;
        try {
            mjestoView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/Mjesta.fxml"));

            Scene mjestoScene = new Scene(mjestoView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mjestoScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!MjestaController.booleanDodaj) {
            tfPostanskiBroj.setEditable(false);
            mjesto = MjestaController.izabranoMjesto;
            //String postanskiBrojString  = Integer.toString(mjesto.getPostanskiBroj());
            tfPostanskiBroj.setText(Integer.toString(mjesto.getPostanskiBroj()));
            tfNaziv.setText(mjesto.getNaziv());
            tfDrzava.setText(mjesto.getDrzava());
        }
    }

}
