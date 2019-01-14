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

            Mjesto mjesto = new Mjesto();

            Integer postanskiBroj = Integer.parseInt(tfPostanskiBroj.getText());
            System.out.println("POSTANSKI BROJ: " + postanskiBroj);
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
