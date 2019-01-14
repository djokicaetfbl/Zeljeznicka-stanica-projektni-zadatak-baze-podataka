/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import zeljeznickastanica.controller.ZeljeznickaStanicaController;
import zeljeznickastanica.model.dto.Lokomotiva;
import zeljeznickastanica.model.dto.Voz;
import zeljeznickastanica.model.dto.Zaposleni;

/**
 *
 * @author djord
 */
public class VozDAO {

    public static void ubaciUTabeluVozova() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        Voz voz;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from voz_info");
            while (rs.next()) {

                voz = new Voz(rs.getString("vozid"), rs.getString("vrstapogona"),
                        rs.getString("namjena"), rs.getDouble("sirinakolosjeka"), rs.getString("naziv"));
                if (!ZeljeznickaStanicaController.vozoviObservaleList.contains(voz)) {
                    ZeljeznickaStanicaController.vozoviObservaleList.add(voz);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LokomotivaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LokomotivaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void izbrisiVoz(Voz voz) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            String query = "delete from voz  where vozid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, voz.getVozId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ZaposleniDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ZaposleniDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
