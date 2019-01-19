/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import zeljeznickastanica.model.dto.Zaposleni;

/**
 *
 * @author djord
 */
public class MasinovodjaDAO {
        public static void izbrisiZaposlenogIzBaze(Zaposleni zaposleni) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            String query = "delete from masinovodja  where JMB = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, zaposleni.getJMB());
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
