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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import zeljeznickastanica.model.dao.VrstaTeretnogVagonaDAO;
import zeljeznickastanica.model.dto.Mjesto;
import zeljeznickastanica.model.dto.Voz;
import zeljeznickastanica.model.dto.VrstaTeretnogVagona;

/**
 * FXML Controller class
 *
 * @author djord
 */
public class DadajVrstuTeretnogVagonaController implements Initializable {

    @FXML
    private TextField tfVrstaId;

    @FXML
    private TextField tfNaziv;

    @FXML
    private Button bOdustani;

    @FXML
    private Button bPotvrdi;

    private VrstaTeretnogVagona vtv;

    private boolean unesiVTV() {
        if (!tfVrstaId.getText().isEmpty() && !tfNaziv.getText().isEmpty()) {

            if (VrstaTeretnogVagonaController.booleanDodajVTV && provjeriVTVIDUBazi(tfVrstaId.getText())) {
                upozorenjeVTVID();
                return false;
            }

            if (tfVrstaId.getText().length() > 20) {
                upozorenjePredugacakUnos();
                return false;
            }

            if (tfNaziv.getText().length() > 20) {
                upozorenjePredugacakUnos();
                return false;
            }

            VrstaTeretnogVagona vtv = new VrstaTeretnogVagona();

            if (!VrstaTeretnogVagonaController.booleanDodajVTV) {
                vtv = VrstaTeretnogVagonaController.izabranoVTV;
            }
            vtv.setTip(tfVrstaId.getText());
            vtv.setNaziv(tfNaziv.getText());
            if (VrstaTeretnogVagonaController.booleanDodajVTV) {
                VrstaTeretnogVagonaDAO.dodajVTV(vtv);
            } else {
                VrstaTeretnogVagonaDAO.izmjeniVTV(vtv);
                VrstaTeretnogVagonaController.booleanDodajVTV = false;
            }

        } else {
            upozorenjePoljaSuPrazna();
            return false;
        }
        return true;
    }

    @FXML
    void nazadNaVrstaTeretnogVagonaForm(ActionEvent event) {
        Parent vrstaTVView;
        try {
            vrstaTVView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/VrstaTeretnogVagona.fxml"));

            Scene vrstaTVScene = new Scene(vrstaTVView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(vrstaTVScene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void potvrdiUnosVrsteTeretnogVagona(ActionEvent event) {
        if (unesiVTV()) {
            Parent vrstaTVView;
            try {
                vrstaTVView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/VrstaTeretnogVagona.fxml"));

                Scene vrstaTVScene = new Scene(vrstaTVView);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(vrstaTVScene);
                window.show();
            } catch (IOException ex) {
                Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return;
        }
    }

    private boolean provjeriVTVIDUBazi(String vtvID) {
        final String zaPoredjenje = vtvID;
        Optional<VrstaTeretnogVagona> vtvOptional = VrstaTeretnogVagonaController.vtvObservaleList.stream().filter(e -> e.getTip().equals(zaPoredjenje)).findFirst();
        if (vtvOptional.isPresent()) {
            return true;
        }
        return false;
    }

    private void upozorenjeVTVID() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("ID vec postoji u BAZI!");
        alert.showAndWait();
    }

    private void upozorenjePredugacakUnos() {
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

        if (!VrstaTeretnogVagonaController.booleanDodajVTV) {
            vtv = VrstaTeretnogVagonaController.izabranoVTV;
            tfVrstaId.setText(vtv.getTip());
            tfVrstaId.setEditable(false);
            tfNaziv.setText(vtv.getNaziv());
        }
    }

}
