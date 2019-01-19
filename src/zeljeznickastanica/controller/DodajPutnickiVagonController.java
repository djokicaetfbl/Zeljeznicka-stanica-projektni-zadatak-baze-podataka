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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import zeljeznickastanica.model.dao.PutnickiVagonDAO;
import zeljeznickastanica.model.dto.PutnickiVagon;
import zeljeznickastanica.model.dto.Vagon;

/**
 * FXML Controller class
 *
 * @author djord
 */
public class DodajPutnickiVagonController implements Initializable {

    @FXML
    private Label lVrstaTeretnogVagona;
    @FXML
    private Label lBrojMjesta;
    @FXML
    private Label lLezajZaSpavanje;
    @FXML
    private Label lToalet;
    @FXML
    private Label lKlima;
    @FXML
    private Label lTV;
    @FXML
    private Label lInternet;
    @FXML
    private Label lRestoran;
    @FXML
    private Label lBar;

    @FXML
    private ComboBox<String> cmbTipVagona;
    @FXML
    private ComboBox<String> cmbVrstaTeretnogVagona;
    @FXML
    private CheckBox cbLezajZaSpavanje;
    @FXML
    private CheckBox cbToalet;
    @FXML
    private CheckBox cbBar;
    @FXML
    private CheckBox cbKlima;
    @FXML
    private CheckBox cbTV;
    @FXML
    private CheckBox cbInternet;
    @FXML
    private CheckBox cbRestoran;
    @FXML
    private TextField tfBrojMjesta;
    @FXML
    private TextField tfPutnickiVagonID;
    @FXML
    private Button bOdustani;

    @FXML
    private Button bPotvrdi;

    private PutnickiVagon putnickiVagon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bPotvrdi.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/zeljeznickastanica/resursi/accept.png"))));
        bOdustani.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/zeljeznickastanica/resursi/minus.png"))));
        if (!ZeljeznickaStanicaController.booleanDodajPutnickiVagon) {
            putnickiVagon = ZeljeznickaStanicaController.izabraniPutnickiVagon;
            tfPutnickiVagonID.setText(putnickiVagon.getVagonId());
            tfPutnickiVagonID.setEditable(false);
            tfBrojMjesta.setText(Integer.toString(putnickiVagon.getBrojMjesta()));
            cbLezajZaSpavanje.setSelected(putnickiVagon.isLezajZaSpavanje());
            cbToalet.setSelected(putnickiVagon.isToalet());
            cbBar.setSelected(putnickiVagon.isBar());
            cbKlima.setSelected(putnickiVagon.isKlima());
            cbTV.setSelected(putnickiVagon.isTv());
            cbInternet.setSelected(putnickiVagon.isInternet());
            cbRestoran.setSelected(putnickiVagon.isRestoran());
        }
    }

    public boolean unesiVagon() {
        if (!tfBrojMjesta.getText().isEmpty() && !tfPutnickiVagonID.getText().isEmpty()) {

            if (ZeljeznickaStanicaController.booleanDodajPutnickiVagon && provjeriPutnickiVagonIDUBaz(tfPutnickiVagonID.getText())) {
                upozorenjeIDVecPostojiUBazi();
                return false;
            }

            if (tfPutnickiVagonID.getText().length() > 20) {
                upozorenjeDuzinaVozID();
                return false;
            }
            String brojMjestaRegex = "\\d+";
            Pattern pattern = Pattern.compile(brojMjestaRegex);

            if (!pattern.matcher(tfBrojMjesta.getText()).matches() || Integer.parseInt(tfBrojMjesta.getText()) < 0) {
                upozorenjeNekorektanUnos();
                return false;
            }

            PutnickiVagon putnickiVagon = new PutnickiVagon();
            Integer brojMjesta = Integer.parseInt(tfBrojMjesta.getText());
            if (!ZeljeznickaStanicaController.booleanDodajPutnickiVagon) {
                System.out.println("IZMJENA");
                putnickiVagon = (PutnickiVagon) ZeljeznickaStanicaController.izabraniPutnickiVagon;
            }
            putnickiVagon.setVagonId(tfPutnickiVagonID.getText());
            putnickiVagon.setBrojMjesta(brojMjesta);
            putnickiVagon.setLezajZaSpavanje(cbLezajZaSpavanje.isSelected());
            putnickiVagon.setToalet(cbToalet.isSelected());
            putnickiVagon.setBar(cbBar.isSelected());
            putnickiVagon.setKlima(cbKlima.isSelected());
            putnickiVagon.setTv(cbTV.isSelected());
            putnickiVagon.setInternet(cbInternet.isSelected());
            putnickiVagon.setRestoran(cbRestoran.isSelected());
            if (ZeljeznickaStanicaController.booleanDodajPutnickiVagon) {
                PutnickiVagonDAO.dodajPutnickiVagon(putnickiVagon);
            } else {
                PutnickiVagonDAO.izmjeniPutnickiVagon(putnickiVagon);
                ZeljeznickaStanicaController.booleanDodajPutnickiVagon = false;
            }

        } else {
            upozorenjePoljaSuPrazna();
            return false;
        }
        return true;
    }

    @FXML
    void odustaniOdUnosaVagona(ActionEvent event) {
        Parent zeljeznickaStanicaView;
        try {
            zeljeznickaStanicaView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/ZeljeznickaStanica.fxml"));

            Scene mjestoScene = new Scene(zeljeznickaStanicaView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mjestoScene);
            window.show();

        } catch (IOException ex) {
            Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void potvrdiUnosVagona(ActionEvent event) {
        if (unesiVagon()) {
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

    private void upozorenjeDuzinaVozID() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Predugacak unos !");
        alert.showAndWait();
    }

    private void upozorenjePoljaSuPrazna() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Provjerite polja za unos podataka.");
        alert.showAndWait();
    }

    private void upozorenjeNekorektanUnos() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Nekorektan unos vrijednosti!");
        alert.showAndWait();
    }

    private boolean provjeriPutnickiVagonIDUBaz(String vozid) {
        final String zaPoredjenje = vozid;
        Optional<Vagon> tvOptional = ZeljeznickaStanicaController.vagoniPutnickiObservableList.stream().filter(e -> e.getVagonId().equals(zaPoredjenje)).findFirst();

        if (tvOptional.isPresent()) {
            return true;
        }
        return false;
    }

    private void upozorenjeIDVecPostojiUBazi() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Voz id vec postoji u bazi!");
        alert.showAndWait();
    }

}
