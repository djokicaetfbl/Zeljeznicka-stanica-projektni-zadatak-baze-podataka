/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import zeljeznickastanica.model.dao.ConnectionPool;
import zeljeznickastanica.model.dao.LokomotivaDAO;
import zeljeznickastanica.model.dao.MasinaDAO;
import zeljeznickastanica.model.dao.ZaposleniDAO;
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
    @FXML
    private ComboBox<String> cmbVrstaPogona;

    private Voz voz;

    private boolean unesiVoz() {
        if (!tfVozID.getText().isEmpty() && !tfNaziv.getText().isEmpty() && !tfSirinaKolosjeka.getText().isEmpty()
                && cmbTipVoza.getValue() != null && cmbVrstaPogona.getValue() != null) {

            if (ZeljeznickaStanicaController.booleanDodajVoz && provjeriVozIDUBaz(tfVozID.getText())) {
                upozorenjeVozID();
                return false;
            }

            if (tfNaziv.getText().length() > 20) {
                upozorenjePredugUnos();
                return false;
            }
            if (tfVozID.getText().length() > 20) {
                upozorenjePredugUnos();
                return false;
            }
            String sirinaKolosjekaRegex = "^[0-9]+([,.][0-9][0-9]?)?$";

            if (tfSirinaKolosjeka.getText().length() > 6) {
                upozorenjeSirinaKolosjeka();
                return false;
            }

            Pattern pattern = Pattern.compile(sirinaKolosjekaRegex);
            if (!pattern.matcher(tfSirinaKolosjeka.getText()).matches() || Double.parseDouble(tfSirinaKolosjeka.getText()) < 0) {
                upozorenjeSirinaKolosjeka();
                return false;
            }

            if (cmbTipVoza.getValue().equals("Lokomotiva")) {
                Lokomotiva lokomotiva = new Lokomotiva();
                String tipVoza = cmbTipVoza.getValue().toString();
                Double sirinaKolosjeka = Double.parseDouble(tfSirinaKolosjeka.getText());
                if (!ZeljeznickaStanicaController.booleanDodajVoz) {
                    //System.out.println("usaoo");
                    lokomotiva = (Lokomotiva) ZeljeznickaStanicaController.izabraniVoz;
                }

                lokomotiva.setNaziv(tfNaziv.getText());
                lokomotiva.setSirinaKolosjeka(sirinaKolosjeka);
                lokomotiva.setVozId(tfVozID.getText());
                lokomotiva.setVrstaPogona(cmbVrstaPogona.getValue().toString());
                //lokomotiva.setNamjena(tfNamjena.getText());
                lokomotiva.setNamjena("prevoz putnika");
                System.out.println("LOKOMOTIVA: " + lokomotiva);
                if (ZeljeznickaStanicaController.booleanDodajVoz) {
                    LokomotivaDAO.dodajLokomotivu(lokomotiva, tipVoza);
                } else {
                    // VozDAO.izmjeniVoz(voz);
                    //MjestaController.booleanDodaj = false;
                    //System.out.println("IZMJENA");
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
                masina.setVrstaPogona(cmbVrstaPogona.getValue().toString());
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
            return false;

        }
        //comboBox.getValue().toString().equals("Zaposleni")
        return true;
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
        if (unesiVoz()) {
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
        } else {
            return;
        }
    }

    private void upozorenjeVozID() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Voz id vec postoji u bazi!");
        alert.showAndWait();
    }

    private void upozorenjeSirinaKolosjeka() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Nekorektan unos sirine kolosjeka!");
        alert.showAndWait();
    }

    private boolean provjeriVozIDUBaz(String vozid) {
        final String zaPoredjenje = vozid;
        Optional<Voz> vozOptional = ZeljeznickaStanicaController.vozoviObservaleList.stream().filter(e -> e.getVozId().equals(zaPoredjenje)).findFirst();
        if (vozOptional.isPresent()) {
            return true;
        }
        return false;
    }

    private void upozorenjePredugUnos() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Predugacak unos!");
        alert.showAndWait();
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
        bPotvrdi.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/zeljeznickastanica/resursi/accept.png"))));
        bOdustani.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/zeljeznickastanica/resursi/minus.png"))));
        cmbTipVoza.getItems().addAll("Lokomotiva", "Masina");
        cmbVrstaPogona.getItems().addAll("Dizel", "El. energija");
        if (!ZeljeznickaStanicaController.booleanDodajVoz) {
            voz = ZeljeznickaStanicaController.izabraniVoz;
            System.out.println("VOZZZZ: " + voz);
            tfNaziv.setText(voz.getNaziv());
            tfVozID.setEditable(false);
            tfVozID.setText(voz.getVozId());
            cmbVrstaPogona.getSelectionModel().select(voz.getVrstaPogona());
            tfSirinaKolosjeka.setText(Double.toString(voz.getSirinaKolosjeka()));
            if (((Voz) (ZeljeznickaStanicaController.izabraniVoz)).getNamjena().equals("prevoz putnika")) {
                cmbTipVoza.getSelectionModel().selectFirst();
            } else {
                cmbTipVoza.getSelectionModel().selectLast();
            }

        }
    }

}
