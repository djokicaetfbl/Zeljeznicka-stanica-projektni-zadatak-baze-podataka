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
import zeljeznickastanica.model.dao.TeretniVagonDAO;
import zeljeznickastanica.model.dto.TeretniVagon;
import zeljeznickastanica.model.dto.Vagon;
import zeljeznickastanica.model.dto.Voz;

/**
 * FXML Controller class
 *
 * @author djord
 */
public class DodajTeretniVagonController implements Initializable {

    @FXML
    private TextField tfTeretniVagonID;

    @FXML
    private ComboBox<String> cmbTipTeretnogVagona;

    @FXML
    private TextField tfDuzPrekoOdbojnika;

    @FXML
    private TextField tfUkupnaVisina;

    @FXML
    private TextField tfProsVlastitaMasa;

    @FXML
    private TextField tfNosivost;

    @FXML
    private TextField tfPovrsinaUnutrasnjosti;

    @FXML
    private TextField tfZapreminaUnutrasnjosti;

    @FXML
    private Button bOdustani;

    @FXML
    private Button bPotvrdi;

    private TeretniVagon teretniVagon;

    @FXML
    public boolean unesiVagon() {
        if (!tfTeretniVagonID.getText().isEmpty() && (cmbTipTeretnogVagona.getValue() != null) && !tfDuzPrekoOdbojnika.getText().isEmpty()
                && !tfUkupnaVisina.getText().isEmpty() && !tfProsVlastitaMasa.getText().isEmpty() && !tfNosivost.getText().isEmpty()
                && !tfPovrsinaUnutrasnjosti.getText().isEmpty() && !tfZapreminaUnutrasnjosti.getText().isEmpty()) {

            if (ZeljeznickaStanicaController.booleanDodajTeretniVagon && provjeriTeretniVagonIDUBaz(tfTeretniVagonID.getText())) {
                upozorenjeIDVecPostojiUBazi();
                return false;
            }

            if (tfTeretniVagonID.getText().length() > 20) {
                upozorenjeDuzinaVozID();
                return false;
            }

            String decimalRegex = "^[0-9]+([,.][0-9][0-9]?)?$";
            Pattern pattern = Pattern.compile(decimalRegex);
            if (!pattern.matcher(tfDuzPrekoOdbojnika.getText()).matches() || Double.parseDouble(tfDuzPrekoOdbojnika.getText()) < 0) {
                upozorenjeNekorektanUnos();
                return false;
            }
            if (!pattern.matcher(tfUkupnaVisina.getText()).matches() || Double.parseDouble(tfUkupnaVisina.getText()) < 0) {
                upozorenjeNekorektanUnos();
                return false;
            }
            if (!pattern.matcher(tfProsVlastitaMasa.getText()).matches() || Double.parseDouble(tfProsVlastitaMasa.getText()) < 0) {
                upozorenjeNekorektanUnos();
                return false;
            }
            if (!pattern.matcher(tfNosivost.getText()).matches() || Double.parseDouble(tfNosivost.getText()) < 0) {
                upozorenjeNekorektanUnos();
                return false;
            }
            if (!pattern.matcher(tfPovrsinaUnutrasnjosti.getText()).matches() || Double.parseDouble(tfPovrsinaUnutrasnjosti.getText()) < 0) {
                upozorenjeNekorektanUnos();
                return false;
            }
            if (!pattern.matcher(tfZapreminaUnutrasnjosti.getText()).matches() || Double.parseDouble(tfZapreminaUnutrasnjosti.getText()) < 0) {
                upozorenjeNekorektanUnos();
                return false;
            }

            TeretniVagon teretniVagon = new TeretniVagon();
            if (!ZeljeznickaStanicaController.booleanDodajTeretniVagon) {
                teretniVagon = (TeretniVagon) ZeljeznickaStanicaController.izabraniTeretniVagon;
            }
            teretniVagon.setVagonId(tfTeretniVagonID.getText());
            teretniVagon.setTipTeretnogVagona(cmbTipTeretnogVagona.getValue().toString());
            teretniVagon.setDuzinaPrekoOdbojnika(Double.parseDouble(tfDuzPrekoOdbojnika.getText()));
            teretniVagon.setUkupnaVisina(Double.parseDouble(tfUkupnaVisina.getText()));
            teretniVagon.setProsjecnaVlastitaMasa(Double.parseDouble(tfProsVlastitaMasa.getText()));
            teretniVagon.setNosivostVagona(Double.parseDouble(tfNosivost.getText()));
            teretniVagon.setPovrsinaUnutrasnjosti(Double.parseDouble(tfPovrsinaUnutrasnjosti.getText()));
            teretniVagon.setZapreminaUnutrasnjosti(Double.parseDouble(tfZapreminaUnutrasnjosti.getText()));

            if (ZeljeznickaStanicaController.booleanDodajTeretniVagon) {
                TeretniVagonDAO.dodajTeretniVagon(teretniVagon);
            } else {
                TeretniVagonDAO.izmjeniTeretniVagon(teretniVagon);
                ZeljeznickaStanicaController.booleanDodajTeretniVagon = false;
            }

        } else {
            upozorenjePoljaSuPrazna();
            return false;
        }
        return true;
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

    private void upozorenjeDuzinaVozID() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Predugacak unos !");
        alert.showAndWait();
    }

    private void upozorenjeIDVecPostojiUBazi() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Voz id vec postoji u bazi!");
        alert.showAndWait();
    }

    private boolean provjeriTeretniVagonIDUBaz(String vozid) {
        final String zaPoredjenje = vozid;
        Optional<Vagon> tvOptional = ZeljeznickaStanicaController.vagoniTeretniObservableList.stream().filter(e -> e.getVagonId().equals(zaPoredjenje)).findFirst();

        if (tvOptional.isPresent()) {
            return true;
        }
        return false;
    }

    private void ubaciUCMBTipTeretnogVagona() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select tip from vrsta_t_v");
            while (rs.next()) {
                cmbTipTeretnogVagona.getItems().add(rs.getString(1));
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bPotvrdi.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/zeljeznickastanica/resursi/accept.png"))));
        bOdustani.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/zeljeznickastanica/resursi/minus.png"))));
        //cmbTipTeretnogVagona.getItems().addAll("Zatvoreni", "Otvoreni");
        ubaciUCMBTipTeretnogVagona();
        if (!ZeljeznickaStanicaController.booleanDodajTeretniVagon) {
            teretniVagon = ZeljeznickaStanicaController.izabraniTeretniVagon;
            cmbTipTeretnogVagona.getItems().add(teretniVagon.getTipTeretnogVagona());
            tfTeretniVagonID.setText(teretniVagon.getVagonId());
            tfTeretniVagonID.setEditable(false);

            //cmbTipTeretnogVagona.getSelectionModel().selectFirst(); // ovo treba popraviti al nek stoji za sad ovako
            cmbTipTeretnogVagona.getSelectionModel().select(teretniVagon.getTipTeretnogVagona());

            tfDuzPrekoOdbojnika.setText(Double.toString(teretniVagon.getDuzinaPrekoOdbojnika()));
            tfUkupnaVisina.setText(Double.toString(teretniVagon.getUkupnaVisina()));
            tfProsVlastitaMasa.setText(Double.toString(teretniVagon.getProsjecnaVlastitaMasa()));
            tfNosivost.setText(Double.toString(teretniVagon.getNosivostVagona()));
            tfPovrsinaUnutrasnjosti.setText(Double.toString(teretniVagon.getPovrsinaUnutrasnjosti()));
            tfZapreminaUnutrasnjosti.setText(Double.toString(teretniVagon.getZapreminaUnutrasnjosti()));
        }
    }

}
