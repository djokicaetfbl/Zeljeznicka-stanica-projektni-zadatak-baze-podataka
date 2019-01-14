/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import zeljeznickastanica.controller.ZeljeznickaStanicaController;
import zeljeznickastanica.model.dto.TeretniVagon;

/**
 *
 * @author djord
 */
public class TeretniVagonDAO {

    public static void ubaciUTabeluVagona() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        TeretniVagon teretniVagon;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from teretni_vagon_info");
            while (rs.next()) {
                teretniVagon = new TeretniVagon(rs.getString("vagonid"), rs.getString("tip"),
                        rs.getDouble("duzinaprekoodbojnika"), rs.getDouble("ukupnavisina"), rs.getDouble("prosjecnavlastitamasa"),
                        rs.getDouble("nosivostvagona"), rs.getDouble("povrsinaunutrasnjosti"), rs.getDouble("zapreminaunutrasnjosti"));
                if (!ZeljeznickaStanicaController.vagoniTeretniObservableList.contains(teretniVagon)) {
                    ZeljeznickaStanicaController.vagoniTeretniObservableList.add(teretniVagon);
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

    public static void dodajTeretniVagon(TeretniVagon teretniVagon) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call insert_into_teretni_vagon(?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, teretniVagon.getVagonId());
            callableStatement.setString(2, teretniVagon.getTipTeretnogVagona());
            callableStatement.setDouble(3, teretniVagon.getDuzinaPrekoOdbojnika());
            callableStatement.setDouble(4, teretniVagon.getUkupnaVisina());
            callableStatement.setDouble(5, teretniVagon.getProsjecnaVlastitaMasa());
            callableStatement.setDouble(6, teretniVagon.getNosivostVagona());
            callableStatement.setDouble(7, teretniVagon.getPovrsinaUnutrasnjosti());
            callableStatement.setDouble(8, teretniVagon.getZapreminaUnutrasnjosti());

            callableStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ZaposleniDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (callableStatement != null) {
                try {
                    callableStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ZaposleniDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void izmjeniTeretniVagon(TeretniVagon teretniVagon) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call update_teretni_vagon(?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, teretniVagon.getVagonId());
            callableStatement.setString(2, teretniVagon.getTipTeretnogVagona());
            callableStatement.setDouble(3, teretniVagon.getDuzinaPrekoOdbojnika());
            callableStatement.setDouble(4, teretniVagon.getUkupnaVisina());
            callableStatement.setDouble(5, teretniVagon.getProsjecnaVlastitaMasa());
            callableStatement.setDouble(6, teretniVagon.getNosivostVagona());
            callableStatement.setDouble(7, teretniVagon.getPovrsinaUnutrasnjosti());
            callableStatement.setDouble(8, teretniVagon.getZapreminaUnutrasnjosti());

            callableStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ZaposleniDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (callableStatement != null) {
                try {
                    callableStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ZaposleniDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static TeretniVagon pretraziTeretniVagon(String vagonId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call pretrazi_teretni_vagon(?)}");
            callableStatement.setString(1, vagonId);
            rs = callableStatement.executeQuery();
            if (rs.next()) {
                return new TeretniVagon(rs.getString("vagonid"), rs.getString("tip"), rs.getDouble("duzinaprekoodbojnika"),
                        rs.getDouble("ukupnavisina"), rs.getDouble("prosjecnavlastitamasa"), rs.getDouble("nosivostvagona"), rs.getDouble("povrsinaunutrasnjosti"), rs.getDouble("zapreminaunutrasnjosti"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ZaposleniDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
        }
        return null;
    }
}
