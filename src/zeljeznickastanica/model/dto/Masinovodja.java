/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author djord
 */
public class Masinovodja extends Zaposleni {
    

    public Masinovodja() {
    }

    public Masinovodja(String JMB, String ime, String prezime, String adresa, String brojTelefona, Integer postanskiBroj, BigDecimal plata, Date datumRodjenja, boolean aktivan,String zanimanje) {
        super(JMB, ime, prezime, adresa, brojTelefona, postanskiBroj, plata, datumRodjenja, aktivan,"Masinovodja");
    }

   
    
}
