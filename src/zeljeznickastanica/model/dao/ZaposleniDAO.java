/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.model.dao;
//ConnectionPool je u ovom paketu

/**
 *
 * @author djord
 */
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import zeljeznickastanica.controller.PrikazRadnikaController;
import zeljeznickastanica.controller.ZeljeznickaStanicaController;
import static zeljeznickastanica.controller.ZeljeznickaStanicaController.zaposleniObservaleList;
import zeljeznickastanica.model.dto.Zaposleni;

public class ZaposleniDAO {

    public static void dodajZaposlenog(Zaposleni zaposleni, String zanimanjeZaposlenog) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();

            switch (zanimanjeZaposlenog) {
                case "Kondukter":
                    callableStatement = connection.prepareCall("{call insert_into_kondukter(?,?,?,?,?,?,?,?,?)}");
                    System.out.println("KONKUKTER");
                    break;
                case "Masinovodja":
                    callableStatement = connection.prepareCall("{call insert_into_masinovodja(?,?,?,?,?,?,?,?,?)}");
                    System.out.println("MASINOVODJA");
                    break;
                case "Otpravnik":
                    callableStatement = connection.prepareCall("{call insert_into_otpravnik(?,?,?,?,?,?,?,?,?)}");
                    System.out.println("OTPRAVNIK");
                    break;
                case "Tehnolog":
                    callableStatement = connection.prepareCall("{call insert_into_tehnolog_stanice(?,?,?,?,?,?,?,?,?)}");
                    System.out.println("TEHNOLOG");
                    break;
            }
            callableStatement.setString(1, zaposleni.getJMB());
            callableStatement.setString(2, zaposleni.getIme());
            callableStatement.setString(3, zaposleni.getPrezime());
            callableStatement.setString(4, zaposleni.getAdresa());
            callableStatement.setString(5, zaposleni.getBrojTelefona());
            callableStatement.setInt(6, zaposleni.getPostanskiBroj());
            callableStatement.setBigDecimal(7, zaposleni.getPlata());
            callableStatement.setDate(8, new java.sql.Date(zaposleni.getDatumRodjenja().getTime()));
            callableStatement.setString(9, zanimanjeZaposlenog);

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

    public static void izmjeniZaposlenog(Zaposleni zaposleni) {

        System.out.println("IZMJENA ZANIMANJE: " + zaposleni.getZanimanje());

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            switch (zaposleni.getZanimanje()) {
                case "Kondukter":
                    callableStatement = connection.prepareCall("{call update_kondukter(?,?,?,?,?,?,?,?,?)}");
                    System.out.println("KONKUKTER");
                    break;
                case "Masinovodja":
                    callableStatement = connection.prepareCall("{call update_masinovodja(?,?,?,?,?,?,?,?,?)}");
                    System.out.println("MASINOVODJA");
                    break;
                case "Otpravnik":
                    callableStatement = connection.prepareCall("{call update_otpravnik(?,?,?,?,?,?,?,?,?)}");
                    System.out.println("OTPRAVNIK");
                    break;
                case "Tehnolog":
                    callableStatement = connection.prepareCall("{call update_tehnolog(?,?,?,?,?,?,?,?,?)}");
                    System.out.println("TEHNOLOG");
                    break;
            }
            callableStatement.setString(1, zaposleni.getJMB());
            callableStatement.setString(2, zaposleni.getIme());
            callableStatement.setString(3, zaposleni.getPrezime());
            callableStatement.setString(4, zaposleni.getAdresa());
            callableStatement.setString(5, zaposleni.getBrojTelefona());
            callableStatement.setInt(6, zaposleni.getPostanskiBroj());
            callableStatement.setBigDecimal(7, zaposleni.getPlata());
            callableStatement.setDate(8, new java.sql.Date(zaposleni.getDatumRodjenja().getTime()));
            callableStatement.setString(9, zaposleni.getZanimanje());

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

    public static void ubaciUTabeluZaposlenih() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        Zaposleni zaposleni;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from zaposleni_info where aktivan = true ");
            while (rs.next()) {
                zaposleni = new Zaposleni(rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getBigDecimal(7),
                        rs.getDate(8), rs.getBoolean(9), rs.getString(10));
                if (!zaposleniObservaleList.contains(zaposleni)) {
                    zaposleniObservaleList.add(zaposleni);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ZeljeznickaStanicaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ZeljeznickaStanicaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void izbrisiZaposlenog(Zaposleni zaposleni) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            String query = "update zaposleni set Aktivan = false where JMB = ?";
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

    public static void izbrisiZaposlenogIzBaze(Zaposleni zaposleni) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            String query = "delete from zaposleni  where JMB = ?";
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

    public static Zaposleni pretraziZaposlenog(String JMB) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call pretrazi_zaposlenog(?)}");
            callableStatement.setString(1, JMB);
            rs = callableStatement.executeQuery();
            if (rs.next()) {
                return new Zaposleni(rs.getString("jmb"), rs.getString("ime"), rs.getString("prezime"), rs.getString("adresa"), rs.getString("brojtelefona"), rs.getInt("postanskibroj"), rs.getBigDecimal("plata"), rs.getDate("datumrodjenja"), true, rs.getString("zanimanje"));
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

    public static void ubaciUTabeluRadnika() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        Zaposleni zaposleni;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from zaposleni");
            while (rs.next()) {
                zaposleni = new Zaposleni(rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getInt(6), rs.getBigDecimal(7),
                        rs.getDate(8), rs.getBoolean(9), rs.getString(10));
                if (!PrikazRadnikaController.radniciObservaleList.contains(zaposleni)) {
                    PrikazRadnikaController.radniciObservaleList.add(zaposleni);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ZeljeznickaStanicaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ZeljeznickaStanicaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
