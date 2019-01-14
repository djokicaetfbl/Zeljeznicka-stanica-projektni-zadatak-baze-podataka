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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import zeljeznickastanica.model.dao.ConnectionPool;
import zeljeznickastanica.model.dao.TeretniVagonDAO;
import zeljeznickastanica.model.dto.TeretniVagon;

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
    public void unesiVagon() {
        if (!tfTeretniVagonID.getText().isEmpty() && (cmbTipTeretnogVagona.getValue() != null) && !tfDuzPrekoOdbojnika.getText().isEmpty()
                && !tfUkupnaVisina.getText().isEmpty() && !tfProsVlastitaMasa.getText().isEmpty() && !tfNosivost.getText().isEmpty()
                && !tfPovrsinaUnutrasnjosti.getText().isEmpty() && !tfZapreminaUnutrasnjosti.getText().isEmpty()) {

            TeretniVagon teretniVagon = new TeretniVagon();
            if (!ZeljeznickaStanicaController.booleanDodajTeretniVagon) {
                System.out.println("IZMJENA");
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
            // upozorenjePoljaSuPrazna();
        }

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
        unesiVagon();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
