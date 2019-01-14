/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.controller;

import java.io.IOException;
import java.math.BigDecimal;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import zeljeznickastanica.model.dao.LokomotivaDAO;
import zeljeznickastanica.model.dao.MasinaDAO;
import zeljeznickastanica.model.dto.Lokomotiva;
import zeljeznickastanica.model.dto.Masina;
import zeljeznickastanica.model.dto.Voz;

/**
 * FXML Controller class
 *
 * @author djord
 */
public class DodajVozController implements Initializable {

    @FXML
    private TextField tfNaziv;

    @FXML
    private TextField tfVozID;

    @FXML
    private TextField tfVrstaPogona;

    @FXML
    private TextField tfSirinaKolosjeka;

    @FXML
    private Button bPotvrdi;

    @FXML
    private Button bOdustani;
    @FXML
    private ComboBox<String> cmbTipVoza;

    private Voz voz;

    private void unesiVoz() {
        if (!tfVozID.getText().isEmpty() && !tfVrstaPogona.getText().isEmpty() && !tfSirinaKolosjeka.getText().isEmpty()) {

            if (cmbTipVoza.getValue().equals("Lokomotiva")) {
                Lokomotiva lokomotiva = new Lokomotiva();
                String tipVoza = cmbTipVoza.getValue().toString();
                System.out.println("TIP VOZA: " + tipVoza);
                Double sirinaKolosjeka = Double.parseDouble(tfSirinaKolosjeka.getText());
                System.out.println("Sirina kolosjeka: " + sirinaKolosjeka);
                if (!ZeljeznickaStanicaController.booleanDodajVoz) {
                    System.out.println("usaoo");
                    lokomotiva = (Lokomotiva) ZeljeznickaStanicaController.izabraniVoz;
                }
                lokomotiva.setNaziv(tfNaziv.getText());
                lokomotiva.setSirinaKolosjeka(sirinaKolosjeka);
                lokomotiva.setVozId(tfVozID.getText());
                lokomotiva.setVrstaPogona(tfVrstaPogona.getText());
                //lokomotiva.setNamjena(tfNamjena.getText());
                lokomotiva.setNamjena("prevoz putnika");
                System.out.println("LOKOMOTIVA: " + lokomotiva);
                if (ZeljeznickaStanicaController.booleanDodajVoz) {
                    LokomotivaDAO.dodajLokomotivu(lokomotiva, tipVoza);
                } else {
                    // VozDAO.izmjeniVoz(voz);
                    //MjestaController.booleanDodaj = false;
                    System.out.println("IZMJENA");
                    LokomotivaDAO.izmjeniLokomotivu(lokomotiva);
                    ZeljeznickaStanicaController.booleanDodajVoz = false;
                }

            } else {
                Masina masina = new Masina();
                String tipVoza = cmbTipVoza.getValue().toString();
                System.out.println("TIP VOZA: " + tipVoza);
                Double sirinaKolosjeka = Double.parseDouble(tfSirinaKolosjeka.getText());
                System.out.println("Sirina kolosjeka: " + sirinaKolosjeka);
                if (!ZeljeznickaStanicaController.booleanDodajVoz) {
                    System.out.println("usaoo");

                    //PAZI DA OVO STAVIS ZADNJI PUT SI SE 
                    //NAPATIO SAT VREMENA TRAZECI GRESKU :S
                    masina = (Masina) ZeljeznickaStanicaController.izabraniVoz;
                }
                masina.setNaziv(tfNaziv.getText());
                masina.setSirinaKolosjeka(sirinaKolosjeka);
                masina.setVozId(tfVozID.getText());
                masina.setVrstaPogona(tfVrstaPogona.getText());
                // masina.setNamjena(tfNamjena.getText());
                masina.setNamjena("prevoz tereta");
                if (ZeljeznickaStanicaController.booleanDodajVoz) {
                    MasinaDAO.dodajMasinu(masina, tipVoza);
                } else {
                    // VozDAO.izmjeniVoz(voz);
                    //MjestaController.booleanDodaj = false;
                    MasinaDAO.izmjeniMasinu(masina);
                    ZeljeznickaStanicaController.booleanDodajVoz = false;
                }

            }
        } else {
            upozorenjePoljaSuPrazna();

        }
        //comboBox.getValue().toString().equals("Zaposleni")

    }

    @FXML
    void odustaniOdUnosaVoza(ActionEvent event) {
        Parent zeljeznickaStanicaView;
        try {
            zeljeznickaStanicaView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/ZeljeznickaStanica.fxml"));

            Scene zeljeznickaStanicaScene = new Scene(zeljeznickaStanicaView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(zeljeznickaStanicaScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void potvrdiUnosVoza(ActionEvent event) {
        unesiVoz();
        Parent zeljeznickaStanicaView;
        try {
            zeljeznickaStanicaView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/ZeljeznickaStanica.fxml"));

            Scene zeljeznickaStanicaScene = new Scene(zeljeznickaStanicaView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(zeljeznickaStanicaScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void upozorenjePoljaSuPrazna() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Provjerite polja za unos podataka.");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTipVoza.getItems().addAll("Lokomotiva", "Masina");
        if (!ZeljeznickaStanicaController.booleanDodajVoz) {
            voz = ZeljeznickaStanicaController.izabraniVoz;
            tfNaziv.setText(voz.getNaziv());
            tfVozID.setEditable(false);
            tfVozID.setText(voz.getVozId());
            tfVrstaPogona.setText(voz.getVrstaPogona());
            tfSirinaKolosjeka.setText(Double.toString(voz.getSirinaKolosjeka()));
            if (((Voz) (ZeljeznickaStanicaController.izabraniVoz)).getNamjena().equals("prevoz putnika")) {
                cmbTipVoza.getSelectionModel().selectFirst();
            } else {
                cmbTipVoza.getSelectionModel().selectLast();
            }

        }
    }

}
