/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.main;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import zeljeznickastanica.model.dao.ConnectionPool;
import java.time.*;

/**
 *
 * @author djord
 */
public class ZeljeznickaStanica extends Application {

    @Override
    public void start(Stage rootStage) throws Exception {
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(ZeljeznickaStanica.class.getName()).log(Level.SEVERE, null, ex);
        }
        Handler handler = null;
        try {
            handler = new FileHandler("./error.log");
            Logger.getLogger("").addHandler(handler);
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(ZeljeznickaStanica.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parent root = FXMLLoader.load(getClass().getResource("/zeljeznickastanica/view/ZeljeznickaStanica.fxml"));

        Scene scene = new Scene(root);
        rootStage.setTitle("Zeljeznicka Stanica");
        rootStage.getIcons().add(new Image("/zeljeznickastanica/resursi/voz.png"));
        rootStage.setScene(scene);
        rootStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
