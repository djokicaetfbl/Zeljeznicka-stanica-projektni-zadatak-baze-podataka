/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.model.dto;

import java.util.Date;
import java.util.Objects;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author djord
 */
public class Zaposleni {

    private String JMB;
    private String ime;
    private String prezime;
    private String adresa;
    private String brojTelefona;
    private BigDecimal plata;
    private String zanimanje;

    private Date datumRodjenja;

    private Integer postanskiBroj;
    private boolean aktivan = true;
    

    public Zaposleni() {

    }

    public Zaposleni(String JMB, String ime, String prezime, String adresa, String brojTelefona, Integer postanskiBroj, BigDecimal plata, Date datumRodjenja, boolean aktivan, String zanimanje) {
        this.JMB = JMB;
        this.ime = ime;
        this.prezime = prezime;
        // this.datumRodjenja = datumRodjenja;
        this.adresa = adresa;
        this.brojTelefona = brojTelefona;
        this.plata = plata;
        this.postanskiBroj = postanskiBroj;
        this.aktivan = aktivan;
        this.datumRodjenja = datumRodjenja;
        this.zanimanje = zanimanje;
    }

    @Override
    public String toString() {
        return "Zaposleni{" + "JMB=" + JMB + ", ime=" + ime + ", prezime=" + prezime + ", adresa=" + adresa + ", brojTelefona=" + brojTelefona + ", plata=" + plata + ", datumRodjenja=" + datumRodjenja + ", postanskiBroj=" + postanskiBroj + ", aktivan=" + aktivan + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Zaposleni other = (Zaposleni) obj;
        if (!Objects.equals(this.JMB, other.JMB)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.JMB);
        return hash;
    }

    public String getJMB() {
        return JMB;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public BigDecimal getPlata() {
        return plata;
    }

    public Integer getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setJMB(String JMB) {
        this.JMB = JMB;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public void setPlata(BigDecimal plata) {
        this.plata = plata;
    }

    public void setPostanskiBroj(Integer postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }

    public String getZanimanje() {
        return zanimanje;
    }

    public void setZanimanje(String zanimanje) {
        this.zanimanje = zanimanje;
    }

}
