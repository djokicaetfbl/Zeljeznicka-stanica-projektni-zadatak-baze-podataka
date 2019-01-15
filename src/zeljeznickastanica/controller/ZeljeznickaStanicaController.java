/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.controller;

import com.sun.org.apache.xml.internal.utils.Trie;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import zeljeznickastanica.model.dao.ConnectionPool;
import zeljeznickastanica.model.dao.LokomotivaDAO;
import zeljeznickastanica.model.dao.MasinaDAO;
import zeljeznickastanica.model.dao.PutnickiVagonDAO;
import zeljeznickastanica.model.dao.TeretniVagonDAO;
import zeljeznickastanica.model.dao.VagonDAO;
import zeljeznickastanica.model.dao.VozDAO;
import zeljeznickastanica.model.dao.ZaposleniDAO;
import zeljeznickastanica.model.dto.Lokomotiva;
import zeljeznickastanica.model.dto.Masina;
import zeljeznickastanica.model.dto.PutnickiVagon;
import zeljeznickastanica.model.dto.TeretniVagon;
import zeljeznickastanica.model.dto.Vagon;
import zeljeznickastanica.model.dto.Voz;
import zeljeznickastanica.model.dto.Zaposleni;

/**
 *
 * @author djord
 */
public class ZeljeznickaStanicaController implements Initializable {

    @FXML
    private ComboBox comboBox;// = new ComboBox();
    @FXML
    private Button bDodaj;
    @FXML
    private Button bIzbrisi;
    @FXML
    private Button bIzmjeni;
    @FXML
    private Button bPretrazi;
    @FXML
    private TableView univerzalnaTabelaTableView;

    @FXML
    private TableColumn<Zaposleni, String> jmbColumn;
    @FXML
    private TableColumn<Zaposleni, String> imeColumn;
    @FXML
    private TableColumn<Zaposleni, String> prezimeColumn;
    @FXML
    private TableColumn<Zaposleni, String> adresaColumn;
    @FXML
    private TableColumn<Zaposleni, String> brojTelefonaColumn;
    @FXML
    private TableColumn<Zaposleni, Integer> postanskiBrojColumn;
    @FXML
    private TableColumn<Zaposleni, BigDecimal> plataColumn;
    @FXML
    private TableColumn<Zaposleni, LocalDate> datumRodjenjaColumn;
    @FXML
    private TableColumn<Zaposleni, String> zanimanjeColumn;
    @FXML
    private TextField tfPretraga;
    @FXML
    private MenuBar mbMeniBar;

    @FXML
    private MenuItem miIzlaz;

    @FXML
    private ComboBox<String> cmbTipVagona;

    @FXML
    private MenuItem miMjesta;
    // @FXML
    //private TableColumn<Zaposleni,LocalDate> datumRodjenjaColumn; -> bice datumRodjenja.getValue() da se dobije vrijednost
    public static boolean booleanDodaj = false, booleanDodajVoz = false;

    public static ObservableList<Zaposleni> zaposleniObservaleList = FXCollections.observableArrayList();

    @FXML
    private MenuItem miRadnici;
    public static Zaposleni izabraniZaposleni, zaposleniIzPretrage;

    public static Voz izabraniVoz;
    public static Lokomotiva lokomotivaIzPretrage;
    @FXML
    private MenuItem miZaduzenjaVozova;
    public static Masina masinaIzPretrage;
    public static PutnickiVagon putnickiVagonIzPretrage;
    public static TeretniVagon teretniVagonIzPretrage;

    @FXML
    TableColumn<Voz, String> vozNazivColumn;
    @FXML
    TableColumn<Voz, String> vozIdColumn;
    @FXML
    TableColumn<Voz, String> vrstaPogonaColumn;
    @FXML
    TableColumn<Voz, String> namjenaColumn;
    @FXML
    TableColumn<Voz, String> sirinaKolosjeka;

    public static ObservableList<Voz> vozoviObservaleList = FXCollections.observableArrayList();

    @FXML
    TableColumn<Vagon, String> vagonIDColumn;
    @FXML
    TableColumn<Vagon, String> brojMjestaColumn;
    @FXML
    TableColumn<Vagon, String> lezajZaSpavanjeColumn;
    @FXML
    TableColumn<Vagon, String> toaletColumn;
    @FXML
    TableColumn<Vagon, String> barColumn;
    @FXML
    TableColumn<Vagon, String> klimaColumn;
    @FXML
    TableColumn<Vagon, String> tvColumn;
    @FXML
    TableColumn<Vagon, String> internetColumn;
    @FXML
    TableColumn<Vagon, String> restoranColumn;

    public static ObservableList<Vagon> vagoniPutnickiObservableList = FXCollections.observableArrayList();

    @FXML
    TableColumn<Vagon, String> vagonIdColumn;
    @FXML
    TableColumn<Vagon, String> tipColumn;
    @FXML
    TableColumn<Vagon, String> duzinaPrekoOdbojnikaColumn;
    @FXML
    TableColumn<Vagon, String> ukupnaVisinaColumn;
    @FXML
    TableColumn<Vagon, String> prosjecnaVlastitaMasaColumn;
    @FXML
    TableColumn<Vagon, String> nosivostVagonaColumn;
    @FXML
    TableColumn<Vagon, String> povrsinaUnutrasnjostiColumn;
    @FXML
    TableColumn<Vagon, String> zapreminaUnutrasnjostiColumn;

    public static ObservableList<Vagon> vagoniTeretniObservableList = FXCollections.observableArrayList();
    public static boolean booleanDodajTeretniVagon = false;
    public static boolean booleanDodajPutnickiVagon = false;
    public static PutnickiVagon izabraniPutnickiVagon;
    public static TeretniVagon izabraniTeretniVagon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBox.getItems().addAll("Voz", "Zaposleni", "Vagon");
        cmbTipVagona.getItems().addAll("Putnicki vagon", "Teretni vagon");
        cmbTipVagona.setVisible(false);
        univerzalnaTabelaTableView.getColumns().clear();

        ZaposleniDAO.ubaciUTabeluZaposlenih();

        LokomotivaDAO.ubaciUTabeluVozova();
        MasinaDAO.ubaciUTabeluVozova();

        PutnickiVagonDAO.ubaciUTabeluVagona();
        TeretniVagonDAO.ubaciUTabeluVagona();
        univerzalnaTabelaTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public Zaposleni getIzabraniZaposleni() {
        return izabraniZaposleni;
    }

    public void setIzabraniZaposleni(Zaposleni izabraniZaposleni) {
        this.izabraniZaposleni = izabraniZaposleni;
    }

    public void dodajButton(ActionEvent event) {
        //booleanDodaj = true;
        Parent dodajZaposlenogView;
        Parent dodajVozView;
        Parent dodajPutnickiVagonView;
        Parent dodajTeretniVagonView;
        if (comboBox.getSelectionModel().isEmpty()) {
            upozorenjeComboBox();
            return;
        }
        if (comboBox.getValue().toString().equals("Zaposleni")) {
            booleanDodaj = true;
            try {
                dodajZaposlenogView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/DodajZaposlenog.fxml"));

                Scene dodajZaposlenogScene = new Scene(dodajZaposlenogView);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(dodajZaposlenogScene);
                window.show();
            } catch (IOException ex) {
                Logger.getLogger(ZeljeznickaStanicaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (comboBox.getValue().toString().equals("Voz")) {
            booleanDodajVoz = true;
            try {
                dodajVozView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/DodajVoz.fxml"));

                Scene dodajVozScene = new Scene(dodajVozView);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(dodajVozScene);
                window.show();
            } catch (IOException ex) {
                Logger.getLogger(ZeljeznickaStanicaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (comboBox.getValue().toString().equals("Vagon")) {
            if (cmbTipVagona.getValue() != null) {
                if (cmbTipVagona.getValue().toString().equals("Putnicki vagon")) {
                    booleanDodajPutnickiVagon = true;
                    try {
                        dodajPutnickiVagonView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/DodajPutnickiVagon.fxml"));

                        Scene dodajVagonScene = new Scene(dodajPutnickiVagonView);
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(dodajVagonScene);
                        window.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ZeljeznickaStanicaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (cmbTipVagona.getValue().toString().equals("Teretni vagon")) {
                    booleanDodajTeretniVagon = true;
                    try {
                        dodajPutnickiVagonView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/DodajTeretniVagon.fxml"));

                        Scene dodajVagonScene = new Scene(dodajPutnickiVagonView);
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(dodajVagonScene);
                        window.show();
                    } catch (IOException ex) {
                        Logger.getLogger(ZeljeznickaStanicaController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            } else {
                upozorenjeComboBox();
            }
        } else {
            upozorenjeComboBox();
        }
    }

    private void upozorenjeComboBox() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom izbora u combo box-u!");
        alert.setHeaderText(null);
        alert.setContentText("Izaberite iz padajuceg menija u combo box-u");
        alert.showAndWait();
    }

    public void izbrisiButton() {
        if (comboBox.getSelectionModel().isEmpty()) {
            upozorenjeComboBox();
            return;
        }
        if (comboBox.getSelectionModel().getSelectedItem().equals("Zaposleni")) {
            Zaposleni zaposleni;
            ObservableList<Zaposleni> izabranaVrsta, zaposleniObservaleList;
            zaposleniObservaleList = univerzalnaTabelaTableView.getItems();
            izabranaVrsta = univerzalnaTabelaTableView.getSelectionModel().getSelectedItems();
            //if (!booleanDodaj) {
            //    ZaposleniDAO.izbrisiZaposlenogIzBaze((Zaposleni) izabranaVrsta.get(0));
            //} else {
            ZaposleniDAO.izbrisiZaposlenog((Zaposleni) izabranaVrsta.get(0));
            //}
            zaposleniObservaleList.removeAll(izabranaVrsta);
        } else if (comboBox.getSelectionModel().getSelectedItem().equals("Voz")) {
            Voz voz;
            ObservableList<Voz> izabranaVrsta, vozObservaleList;
            vozObservaleList = univerzalnaTabelaTableView.getItems();
            izabranaVrsta = univerzalnaTabelaTableView.getSelectionModel().getSelectedItems();
            System.out.println("KKK: " + izabranaVrsta.get(0));
            VozDAO.izbrisiVoz((Voz) izabranaVrsta.get(0));
            vozObservaleList.removeAll(izabranaVrsta);
        } else if (comboBox.getSelectionModel().getSelectedItem().equals("Vagon")) {
            Vagon vagon;
            ObservableList<Vagon> izabranaVrsta, vagonObservaleList;
            vagonObservaleList = univerzalnaTabelaTableView.getItems();
            izabranaVrsta = univerzalnaTabelaTableView.getSelectionModel().getSelectedItems();
            VagonDAO.izbrisiVagon((Vagon) izabranaVrsta.get(0));
            vagonObservaleList.removeAll(izabranaVrsta);
        }
    }

    public void izmjeniButton(ActionEvent event) {
        if (comboBox.getSelectionModel().isEmpty()) {
            upozorenjeComboBox();
            return;
        }
        if (comboBox.getSelectionModel().getSelectedItem().equals("Zaposleni")) {
            booleanDodaj = false;
            ObservableList<Zaposleni> izabranaVrsta, zaposleniObservaleList;
            zaposleniObservaleList = univerzalnaTabelaTableView.getItems();
            izabranaVrsta = univerzalnaTabelaTableView.getSelectionModel().getSelectedItems();
            izabraniZaposleni = (Zaposleni) izabranaVrsta.get(0);
            if (izabraniZaposleni != null) {
                Parent izmjeniZaposlenogView;
                try {
                    izmjeniZaposlenogView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/DodajZaposlenog.fxml"));

                    Scene dodajZaposlenogScene = new Scene(izmjeniZaposlenogView);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(dodajZaposlenogScene);
                    window.show();
                } catch (IOException ex) {
                    Logger.getLogger(ZeljeznickaStanicaController.class.getName()).log(Level.SEVERE, null, ex);
                }

                //  izbrisiButton();
            } else {
                upozorenjeComboBox(); // treba staviti upozorenje niste izabrali nista
            }
        }
        if (comboBox.getSelectionModel().getSelectedItem().equals("Voz")) {
            booleanDodajVoz = false;
            ObservableList<Voz> izabranaVrsta, vozoviObservaleList;
            vozoviObservaleList = univerzalnaTabelaTableView.getItems();
            izabranaVrsta = univerzalnaTabelaTableView.getSelectionModel().getSelectedItems();
            izabraniVoz = (Voz) izabranaVrsta.get(0);
            System.out.println("IZABRANI VOZ : "+izabraniVoz);
            if (izabraniVoz != null) {
                Parent dodajVozView;
            try {
                dodajVozView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/DodajVoz.fxml"));

                Scene dodajVozScene = new Scene(dodajVozView);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(dodajVozScene);
                window.show();
            } catch (IOException ex) {
                Logger.getLogger(ZeljeznickaStanicaController.class.getName()).log(Level.SEVERE, null, ex);
            }
                booleanDodajVoz = false;
            } else {
                upozorenjeComboBox(); // treba staviti upozorenje niste izabrali nista
            }
        }
        if (comboBox.getSelectionModel().getSelectedItem().equals("Vagon")) {
            if (cmbTipVagona.getValue() != null) {
                if (cmbTipVagona.getValue().toString().equals("Putnicki vagon")) {
                    booleanDodajPutnickiVagon = false;
                    ObservableList<Vagon> izabranaVrsta, vagoniPutnickiObservableList;
                    vagoniPutnickiObservableList = univerzalnaTabelaTableView.getItems();
                    izabranaVrsta = univerzalnaTabelaTableView.getSelectionModel().getSelectedItems();
                    izabraniPutnickiVagon = (PutnickiVagon) izabranaVrsta.get(0);
                    if (izabraniPutnickiVagon != null) {
                        Parent dodajPutnickiVagonView;
                        try {
                            dodajPutnickiVagonView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/DodajPutnickiVagon.fxml"));

                            Scene dodajPutnickiVagonScene = new Scene(dodajPutnickiVagonView);
                            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            window.setScene(dodajPutnickiVagonScene);
                            window.show();
                        } catch (IOException ex) {
                            Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        booleanDodajPutnickiVagon = false;
                    } else {
                        upozorenjeComboBox(); // treba staviti upozorenje niste izabrali nista
                    }
                } else if (cmbTipVagona.getValue().toString().equals("Teretni vagon")) {
                    booleanDodajTeretniVagon = false;
                    ObservableList<Vagon> izabranaVrsta, vagoniTeretniObservableList;
                    vagoniTeretniObservableList = univerzalnaTabelaTableView.getItems();
                    izabranaVrsta = univerzalnaTabelaTableView.getSelectionModel().getSelectedItems();
                    izabraniTeretniVagon = (TeretniVagon) izabranaVrsta.get(0);
                    if (izabraniTeretniVagon != null) {
                        Parent dodajTeretniVagonView;
                        try {
                            dodajTeretniVagonView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/DodajTeretniVagon.fxml"));

                            Scene dodajTeretniVagonScene = new Scene(dodajTeretniVagonView);
                            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            window.setScene(dodajTeretniVagonScene);
                            window.show();
                        } catch (IOException ex) {
                            Logger.getLogger(MjestaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        booleanDodajTeretniVagon = false;
                    } else {
                        upozorenjeComboBox(); // treba staviti upozorenje niste izabrali nista
                    }
                } else {
                    upozorenjeComboBox();
                }

            } else {
                upozorenjeComboBox();
            }
        } //else {
        //     upozorenjeComboBox();
        // }
    }

    public void pretraziButton() {
        if (comboBox.getSelectionModel().isEmpty()) {
            upozorenjeComboBox();
            return;
        }
        if (comboBox.getSelectionModel().getSelectedItem().equals("Zaposleni")) {
            String jmbRegex = "\\d+";
            Pattern pattern = Pattern.compile(jmbRegex);
            if (pattern.matcher(tfPretraga.getText()).matches() && (tfPretraga.getText().length() == 13)) {
                univerzalnaTabelaTableView.getColumns().clear();
                zaposleniIzPretrage = ZaposleniDAO.pretraziZaposlenog(tfPretraga.getText());
                ObservableList<Zaposleni> zaposleniIzPretrageObservableList = FXCollections.observableArrayList();
                zaposleniIzPretrageObservableList.add(zaposleniIzPretrage);
                tabelaZaposlenih(zaposleniIzPretrageObservableList);
            } else {
                upozorenjePretraga();
                return;
            }
        } else if (comboBox.getSelectionModel().getSelectedItem().equals("Voz")) {
           // String slovaRegex = "[a-z A-Z d+]+";
            //  Pattern pattern = Pattern.compile(slovaRegex);

            //    if (pattern.matcher(tfPretraga.getText()).matches()) {
            univerzalnaTabelaTableView.getColumns().clear();
            lokomotivaIzPretrage = LokomotivaDAO.pretraziVoz(tfPretraga.getText());
            masinaIzPretrage = MasinaDAO.pretraziVoz(tfPretraga.getText());
            System.out.println("LOKOMOTIVA IZ PRETRAGE: " + lokomotivaIzPretrage);
            System.out.println("MASINA IZ PRETRAGE: " + masinaIzPretrage);

            if (lokomotivaIzPretrage != null) {
                ObservableList<Voz> lokomotivaIzPretrageObservableList = FXCollections.observableArrayList();
                lokomotivaIzPretrageObservableList.add(lokomotivaIzPretrage);
                tabelaVozova(lokomotivaIzPretrageObservableList);
            } else {
                ObservableList<Voz> masinaIzPretrageObservableList = FXCollections.observableArrayList();
                masinaIzPretrageObservableList.add(masinaIzPretrage);
                tabelaVozova(masinaIzPretrageObservableList);
            }

            // } else {
            //     upozorenjePretraga();
            //     return;
            //  }
        } else if (comboBox.getSelectionModel().getSelectedItem().equals("Vagon")) {
            if (cmbTipVagona.getValue() != null) {
                if (cmbTipVagona.getSelectionModel().getSelectedItem().equals("Putnicki vagon")) {
                    univerzalnaTabelaTableView.getColumns().clear();
                    putnickiVagonIzPretrage = PutnickiVagonDAO.pretraziPutnickiVagon(tfPretraga.getText());
                    System.out.println("PUTNICKI VAGON IZ PRETRAGE: " + putnickiVagonIzPretrage);

                    if (putnickiVagonIzPretrage != null) {
                        ObservableList<PutnickiVagon> putnickiVagonIzPretrageObservableList = FXCollections.observableArrayList();
                        putnickiVagonIzPretrageObservableList.add(putnickiVagonIzPretrage);
                        tabelaPutnickihVagona(putnickiVagonIzPretrageObservableList);
                    }
                } else if (cmbTipVagona.getSelectionModel().getSelectedItem().equals("Teretni vagon")) {
                    tfPretraga.setPromptText("");
                    tfPretraga.setPromptText("Unesite ID voza za pretragu");
                    univerzalnaTabelaTableView.getColumns().clear();
                    teretniVagonIzPretrage = TeretniVagonDAO.pretraziTeretniVagon(tfPretraga.getText());
                    System.out.println("TERETNI VAGON IZ PRETRAGE: " + teretniVagonIzPretrage);

                    if (teretniVagonIzPretrage != null) {
                        ObservableList<TeretniVagon> teretniVagonIzPretrageObservableList = FXCollections.observableArrayList();
                        teretniVagonIzPretrageObservableList.add(teretniVagonIzPretrage);
                        tabelaTeretnihVagona(teretniVagonIzPretrageObservableList);
                    }
                }

            } else {
                upozorenjeComboBox();
            }

        }
    }

    public void izaberiIzKomboBoksa() {
        if (comboBox.getValue().toString().equals("Zaposleni")) {
            tfPretraga.setText("");
            tfPretraga.setPromptText("");
            tfPretraga.setPromptText("Unesite JMB zaposlenog");
            cmbTipVagona.setVisible(false);
            resetujTabelu();
            tabelaZaposlenih(zaposleniObservaleList);
        }

        if (comboBox.getValue().toString().equals("Voz")) {
            tfPretraga.setText("");
            tfPretraga.setPromptText("");
            tfPretraga.setPromptText("Unesite naziv voza za pretragu");
            cmbTipVagona.setVisible(false);
            resetujTabelu();
            tabelaVozova(vozoviObservaleList);
        }
        if (comboBox.getValue().toString().equals("Vagon")) {
            tfPretraga.setText("");
            tfPretraga.setPromptText("");
            tfPretraga.setPromptText("Unesite ID vagona za pretragu");
            cmbTipVagona.setVisible(true);
            resetujTabelu();

        }
    }

    @FXML
    void izaberiTipVagona(ActionEvent event) {
        if (cmbTipVagona.getValue().toString().equals("Putnicki vagon")) {
            tfPretraga.setText("");
            tfPretraga.setPromptText("");
            tfPretraga.setPromptText("Unesite ID vagona za pretragu");
            resetujTabelu();
            tabelaPutnickihVagona(vagoniPutnickiObservableList);
        } else if (cmbTipVagona.getValue().toString().equals("Teretni vagon")) {
            tfPretraga.setText("");
            tfPretraga.setPromptText("");
            tfPretraga.setPromptText("Unesite ID vagona za pretragu");
            System.out.println("USAO TVVV");
            resetujTabelu();
            tabelaTeretnihVagona(vagoniTeretniObservableList);
        }
    }

    public void resetujTabelu() {
        univerzalnaTabelaTableView.getColumns().clear();
    }

    private void postaviKoloneZaTabeluZaposlenih(ObservableList zaposleni) {
        jmbColumn = new TableColumn("JMB");
        jmbColumn.setCellValueFactory(new PropertyValueFactory<>("JMB"));

        imeColumn = new TableColumn("Ime");
        imeColumn.setCellValueFactory(new PropertyValueFactory<>("ime"));

        prezimeColumn = new TableColumn("Prezime");
        prezimeColumn.setCellValueFactory(new PropertyValueFactory<>("prezime"));

        // TableColumn datumRodjenjaColumn = new TableColumn("Datum rodjenja");
        // imeColumn.setCellValueFactory(new PropertyValueFactory<>("datum rodjenja"));
        adresaColumn = new TableColumn("Adresa");
        adresaColumn.setCellValueFactory(new PropertyValueFactory<>("adresa"));

        brojTelefonaColumn = new TableColumn("Broj telefona");
        brojTelefonaColumn.setCellValueFactory(new PropertyValueFactory<>("brojTelefona"));

        postanskiBrojColumn = new TableColumn("PostanskiBroj");
        postanskiBrojColumn.setCellValueFactory(new PropertyValueFactory<>("postanskiBroj"));

        plataColumn = new TableColumn("Plata");
        plataColumn.setCellValueFactory(new PropertyValueFactory<>("plata"));

        datumRodjenjaColumn = new TableColumn("Datum rodjenja");
        datumRodjenjaColumn.setCellValueFactory(new PropertyValueFactory<>("datumRodjenja"));

        zanimanjeColumn = new TableColumn<>("Zanimanje");
        zanimanjeColumn.setCellValueFactory(new PropertyValueFactory<>("zanimanje"));

        univerzalnaTabelaTableView.getColumns().addAll(jmbColumn, imeColumn, prezimeColumn, datumRodjenjaColumn, adresaColumn,
                brojTelefonaColumn, postanskiBrojColumn, plataColumn, zanimanjeColumn);
    }

    public void tabelaZaposlenih(ObservableList zaposleni) {
        univerzalnaTabelaTableView.setItems((ObservableList<Zaposleni>) zaposleni);
        postaviKoloneZaTabeluZaposlenih(zaposleni);
    }

    private void upozorenjePretraga() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greska prilikom unosa podataka !");
        alert.setHeaderText(null);
        alert.setContentText("Provjerite polja za pretragu po JMB-u.");
        alert.showAndWait();
    }

    private void postaviKoloneZaTabeluVozova(ObservableList zaposleni) {

        // ovo da ubaci u tabelu mozda probati realizovati kao triger
        vozNazivColumn = new TableColumn("Naziv");
        vozNazivColumn.setCellValueFactory(new PropertyValueFactory<>("naziv"));

        vozIdColumn = new TableColumn("Tip");
        vozIdColumn.setCellValueFactory(new PropertyValueFactory<>("tipVoza"));

        vrstaPogonaColumn = new TableColumn("Vrsta pogona");
        vrstaPogonaColumn.setCellValueFactory(new PropertyValueFactory<>("vrstaPogona"));

        namjenaColumn = new TableColumn("Namjena");
        namjenaColumn.setCellValueFactory(new PropertyValueFactory<>("namjena"));

        sirinaKolosjeka = new TableColumn("Sirina kolosjeka [mm]");
        sirinaKolosjeka.setCellValueFactory(new PropertyValueFactory<>("sirinaKolosjeka"));

        univerzalnaTabelaTableView.getColumns().addAll(vozNazivColumn, vozIdColumn, vrstaPogonaColumn, namjenaColumn, sirinaKolosjeka);

    }

    public void tabelaVozova(ObservableList vozovi) {
        univerzalnaTabelaTableView.setItems(vozovi);
        postaviKoloneZaTabeluVozova(vozovi);
    }

    private void postaviKoloneZaTabeluPutnickihVagona(ObservableList putnickiVagoni) {
        vagonIDColumn = new TableColumn("VagonID");
        vagonIDColumn.setCellValueFactory(new PropertyValueFactory<>("vagonId"));

        brojMjestaColumn = new TableColumn("Broj Mjesta");
        brojMjestaColumn.setCellValueFactory(new PropertyValueFactory<>("brojMjesta"));

        lezajZaSpavanjeColumn = new TableColumn("Leza za Spavanje");
        lezajZaSpavanjeColumn.setCellValueFactory(new PropertyValueFactory<>("lezajZaSpavanje"));

        toaletColumn = new TableColumn("Toalet");
        toaletColumn.setCellValueFactory(new PropertyValueFactory<>("toalet"));

        barColumn = new TableColumn("Bar");
        barColumn.setCellValueFactory(new PropertyValueFactory<>("bar"));

        klimaColumn = new TableColumn("Klima");
        klimaColumn.setCellValueFactory(new PropertyValueFactory<>("klima"));

        tvColumn = new TableColumn("TV");
        tvColumn.setCellValueFactory(new PropertyValueFactory<>("tv"));

        internetColumn = new TableColumn("Internet");
        internetColumn.setCellValueFactory(new PropertyValueFactory<>("internet"));

        restoranColumn = new TableColumn("Restoran");
        restoranColumn.setCellValueFactory(new PropertyValueFactory<>("restoran"));

        univerzalnaTabelaTableView.getColumns().addAll(vagonIDColumn, brojMjestaColumn, lezajZaSpavanjeColumn, toaletColumn, barColumn, klimaColumn, tvColumn, internetColumn, restoranColumn);

    }

    public void tabelaPutnickihVagona(ObservableList putnickiVagoni) {
        univerzalnaTabelaTableView.setItems(putnickiVagoni);
        postaviKoloneZaTabeluPutnickihVagona(putnickiVagoni);
    }

    private void postaviKoloneZaTabeluTeretnihVagona(ObservableList teretniVagoni) {
        vagonIdColumn = new TableColumn("VagonID");
        vagonIdColumn.setCellValueFactory(new PropertyValueFactory<>("vagonId"));

        tipColumn = new TableColumn("Tip");
        tipColumn.setCellValueFactory(new PropertyValueFactory<>("tipTeretnogVagona"));

        duzinaPrekoOdbojnikaColumn = new TableColumn("Duz. prek. odbojnika [mm]");
        duzinaPrekoOdbojnikaColumn.setCellValueFactory(new PropertyValueFactory<>("duzinaPrekoOdbojnika"));

        ukupnaVisinaColumn = new TableColumn("Visina [mm]");
        ukupnaVisinaColumn.setCellValueFactory(new PropertyValueFactory<>("ukupnaVisina"));

        prosjecnaVlastitaMasaColumn = new TableColumn("Pros. vlastita masa [t]");
        prosjecnaVlastitaMasaColumn.setCellValueFactory(new PropertyValueFactory<>("prosjecnaVlastitaMasa"));

        nosivostVagonaColumn = new TableColumn("Nosivost [t]");
        nosivostVagonaColumn.setCellValueFactory(new PropertyValueFactory<>("nosivostVagona"));

        povrsinaUnutrasnjostiColumn = new TableColumn("Povr. unutrasnjosti [m2]");
        povrsinaUnutrasnjostiColumn.setCellValueFactory(new PropertyValueFactory<>("povrsinaUnutrasnjosti"));

        zapreminaUnutrasnjostiColumn = new TableColumn("Zapr. unutrasnjosti [m3]");
        zapreminaUnutrasnjostiColumn.setCellValueFactory(new PropertyValueFactory<>("zapreminaUnutrasnjosti"));

        univerzalnaTabelaTableView.getColumns().addAll(vagonIdColumn, tipColumn, duzinaPrekoOdbojnikaColumn, ukupnaVisinaColumn, prosjecnaVlastitaMasaColumn, nosivostVagonaColumn, povrsinaUnutrasnjostiColumn, zapreminaUnutrasnjostiColumn);
    }

    public void tabelaTeretnihVagona(ObservableList teretniVagoni) {
        univerzalnaTabelaTableView.setItems(teretniVagoni);
        postaviKoloneZaTabeluTeretnihVagona(teretniVagoni);
    }

    @FXML
    void mjestaForm(ActionEvent event) {
        Parent mjestaView;
        try {
            mjestaView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/Mjesta.fxml"));

            Scene mjestaScene = new Scene(mjestaView);
            Stage window = (Stage) mbMeniBar.getScene().getWindow();
            window.setScene(mjestaScene);
            window.show();

        } catch (IOException ex) {
            Logger.getLogger(ZeljeznickaStanicaController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void mjestaForm1(ActionEvent event) {
    }

    @FXML
    void vrsteTeretnihVagonaForm(ActionEvent event) {
        Parent vrsteTVView;
        try {
            vrsteTVView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/VrstaTeretnogVagona.fxml"));

            Scene vrsteTVScene = new Scene(vrsteTVView);
            Stage window = (Stage) mbMeniBar.getScene().getWindow();
            window.setScene(vrsteTVScene);
            window.show();

        } catch (IOException ex) {
            Logger.getLogger(ZeljeznickaStanicaController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void vrsteTeretnihVagonaForm1(ActionEvent event) {

    }

    @FXML
    void zaduzenjaVozovaForm(ActionEvent event) {
        Parent vrsteTVView;
        try {
            vrsteTVView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/ZaduzenjaVozova.fxml"));

            Scene vrsteTVScene = new Scene(vrsteTVView);
            Stage window = (Stage) mbMeniBar.getScene().getWindow();
            window.setScene(vrsteTVScene);
            window.show();

        } catch (IOException ex) {
            Logger.getLogger(ZeljeznickaStanicaController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void zaduzenjaVozovaForm1(ActionEvent event) {

    }

    @FXML
    void prikazRadnikaForm(ActionEvent event) {

        Parent vrsteTVView;
        try {
            vrsteTVView = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/PrikazRadnika.fxml"));

            Scene vrsteTVScene = new Scene(vrsteTVView);
            Stage window = (Stage) mbMeniBar.getScene().getWindow();
            window.setScene(vrsteTVScene);
            window.show();

        } catch (IOException ex) {
            Logger.getLogger(ZeljeznickaStanicaController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void prikazRadnikaForm1(ActionEvent event) {

    }

    @FXML
    void izlazIzAplikacije(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void izlazIzAplikacije2(ActionEvent event) {

    }
}
