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
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import zeljeznickastanica.model.dao.ConnectionPool;
import zeljeznickastanica.model.dao.ZaposleniDAO;
import zeljeznickastanica.model.dto.Mjesto;
import zeljeznickastanica.model.dto.Zaposleni;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author djord
 */
public class DodajZaposlenogController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField tfJMB, tfIme, tfPrezime, tfAdresa, tfbrojTelefona, tfMjesto, tfPlata;
    @FXML
    private Button bOdustani, bPotvrdi;
    @FXML
    private ChoiceBox cbZanimanje, cbPostanskiBroj;//, cbMjesta;
    @FXML
    private ComboBox<String> cbMjesta;
    @FXML
    private TextField tfDatumRodjenja;
    
    private String mjesto;
    
    public static HashMap<Integer, String> mjesta = new HashMap<Integer, String>();

    //private String[] zanimanje = new String[]{"Kondukter", "Masinovodja", "Otpravnik", "Tehnolog"};//,"Magacioner"};
    private Zaposleni zaposleni;
    ZeljeznickaStanicaController zeljeznickaStanicaController = new ZeljeznickaStanicaController();
    
    public void unesiZaposlenog() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date datum = new Date();
        System.out.println("Datum: " + datum);
        
        if (!tfJMB.getText().isEmpty() && !tfIme.getText().isEmpty() && !tfPrezime.getText().isEmpty() && !tfAdresa.getText().isEmpty()
                && !tfbrojTelefona.getText().isEmpty() && !tfPlata.getText().isEmpty() && (cbZanimanje.getSelectionModel().getSelectedItem() != null)
                && (cbMjesta.getSelectionModel().getSelectedItem() != null) && !tfDatumRodjenja.getText().isEmpty()) {
            
            String jmbRegex = "\\d+";
            Pattern pattern = Pattern.compile(jmbRegex);
            
            if (!pattern.matcher(tfJMB.getText()).matches() && tfJMB.getText().length() != 13) {
                upozorenjeNeispravanJMB();
                return;
            } else {
                if (!provjeriMaticniBrojUBazi(tfJMB.getText()) && ZeljeznickaStanicaController.booleanDodaj) {
                    upoorenjeMaticniBroj();
                    return;
                }
                
            }
            
            if (!tfDatumRodjenja.getText().matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")) {
                upozorenjePogresanDatum();
                return;
            }
            
            String zanimanjeZaposlenog;
            Date datumRodjenja = null;
            Zaposleni zaposleni = new Zaposleni();
            if (!ZeljeznickaStanicaController.booleanDodaj) {
                zaposleni = ZeljeznickaStanicaController.izabraniZaposleni;
            }
            
            mjesto = cbMjesta.getValue().toString();
            //Integer postanskiBroj = provjeriPostanskiBroj(mjesto.split(":")[1]);
            Integer postanskiBroj = Integer.parseInt(cbMjesta.getValue().toString().split(":")[0]);
            zaposleni.setJMB(tfJMB.getText());
            zaposleni.setIme(tfIme.getText());
            zaposleni.setPrezime(tfPrezime.getText());
            zaposleni.setAdresa(tfAdresa.getText());
            zaposleni.setBrojTelefona(tfbrojTelefona.getText());
            zaposleni.setPostanskiBroj(postanskiBroj);
            zaposleni.setPlata(new BigDecimal(tfPlata.getText()));
            zaposleni.setZanimanje(cbZanimanje.getValue().toString());
            //java.sql.Date.valueOf(zaposleni.getDatumRodjenja().toString())           
            try {
                datumRodjenja = dateFormat.parse(tfDatumRodjenja.getText());
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
            zaposleni.setDatumRodjenja(datumRodjenja);
            zaposleni.setAktivan(true);
            zanimanjeZaposlenog = cbZanimanje.getValue().toString();
            if (ZeljeznickaStanicaController.booleanDodaj) {
                ZaposleniDAO.dodajZaposlenog(zaposleni, zanimanjeZaposlenog);
            } else {
                //ZaposleniDAO.izbrisiZaposlenog(ZeljeznickaStanicaController.izabraniZaposleni);
                //ZaposleniDAO.dodajZaposlenog(zaposleni, zanimanjeZaposlenog);
                ZaposleniDAO.izmjeniZaposlenog(zaposleni);
            }
            //zeljeznickaStanicaController.dodajZaposlenogUTabelu(zaposleni); // nema potrebe prilko inicajlizacije u zeljecicka stanica control ce insertovati u zaposlene trenutno stanje
            tfJMB.clear();
            tfIme.clear();
            tfPrezime.clear();
            tfAdresa.clear();
            tfbrojTelefona.clear();
            tfPlata.clear();
            cbZanimanje.getSelectionModel().clearSelection();
            cbMjesta.getSelectionModel().clearSelection();
        } else {
            upozorenjePoljaSuPrazna();
        }
        
    }
    
    private boolean provjeriMaticniBrojUBazi(String jmb) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        String pomJMB = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT JMB,Aktivan from zaposleni where Aktivan = true and JMB=" + jmb);
            while (rs.next()) {
                pomJMB = rs.getString(1);
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
        return pomJMB == null;
    }
    
    private void ubaciUCBMjesto() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT postanskibroj,naziv from mjesto");
            while (rs.next()) {
                cbMjesta.getItems().add(rs.getInt(1) + ":" + rs.getString(2));
                mjesta.put(rs.getInt(1), rs.getString(2));
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
    
    public void potvrdiUnosZaposlenogButton(ActionEvent event) {
        //  if(ZeljeznickaStanicaController.booleanDodaj){
        unesiZaposlenog();
        // ZeljeznickaStanicaController.booleanDodaj = false;
        // } else {
        //     System.out.println("IZMJENA !");
        //  }
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
    
    public void odustaniOdUnosaZaposlenogButton(ActionEvent event) {
        //((Stage) bOdustani.getScene().getWindow()).close();
        //if (!ZeljeznickaStanicaController.booleanDodaj) {
        //     unesiZaposlenog();
        // }
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
    
    private int provjeriPostanskiBroj(String mjesto) {
        if (mjesto.equals("Banja Luka")) {
            return 78000;
        } else if (mjesto.equals("Sarajevo")) {
            return 71000;
        } else if (mjesto.equals("Mostar")) {
            return 88000;
        } else if (mjesto.equals("Doboj")) {
            return 74000;
        } else {
            return 79000; // prijedor
        }
    }
    
    private void upozorenjePoljaSuPrazna() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Provjerite polja za unos podataka.");
        alert.showAndWait();
    }
    
    private void upoorenjeMaticniBroj() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Uneseni JMB vec postoji u bazi podataka.");
        alert.showAndWait();
    }
    
    private void upozorenjeNeispravanJMB() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa JMB !");
        alert.setHeaderText(null);
        alert.setContentText("Provjerite da li JMB ima 13 karaktera.");
        alert.showAndWait();
    }
    
    private void upozorenjePogresanDatum() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa datuma rodjenja !");
        alert.setHeaderText(null);
        alert.setContentText("Provjerite da li je unos ispravan.");
        alert.showAndWait();
    }
    
    private void obavjestenjeUspjenoDodatZaposleni() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uspjeno dodavanje zaposlenog !");
        alert.setHeaderText(null);
        alert.setContentText("Zaposleni uspjesno dodat");
        alert.showAndWait();
    }
    
    private String vratiMiImeMjestaZaposlenoga(int postanskiBroj) {
        for (Map.Entry entry : mjesta.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
            if (entry.getKey().equals(postanskiBroj)) {
                return (String) entry.getValue();
            }
        }
        return "";
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        
        cbZanimanje.getItems().addAll("Kondukter", "Masinovodja", "Otpravnik", "Tehnolog");
        ubaciUCBMjesto();
        if (ZeljeznickaStanicaController.booleanDodaj == false) {

            /* if (cbZanimanje.getSelectionModel().isEmpty()) {
             cbZanimanje.getSelectionModel().selectFirst();
             }
             if (cbMjesta.getSelectionModel().isEmpty()) {
             cbMjesta.getSelectionModel().selectFirst();
             } */
            zaposleni = ZeljeznickaStanicaController.izabraniZaposleni; // = zeljeznickaStanicaController.getIzabraniZaposleni();
            cbZanimanje.getSelectionModel().select(zaposleni.getZanimanje());
            System.out.println("P O S T NSKI BROJ : " + zaposleni.getPostanskiBroj().toString());
            cbMjesta.getSelectionModel().select(zaposleni.getPostanskiBroj() + ":" + vratiMiImeMjestaZaposlenoga(zaposleni.getPostanskiBroj()));
            tfJMB.setText(zaposleni.getJMB());
            tfJMB.setEditable(false);
            //tfJMB.setText("");
            tfIme.setText(zaposleni.getIme());
            tfPrezime.setText(zaposleni.getPrezime());
            tfAdresa.setText(zaposleni.getAdresa());
            tfbrojTelefona.setText(zaposleni.getBrojTelefona());
            tfPlata.setText(zaposleni.getPlata().toString());
            System.out.println("DATUM RODJENJA: " + zaposleni.getDatumRodjenja());
            tfDatumRodjenja.setText(zaposleni.getDatumRodjenja().toString());
            //if(cbZanimanje.getSelectionModel().equals(rb))
        }
    }
    
}