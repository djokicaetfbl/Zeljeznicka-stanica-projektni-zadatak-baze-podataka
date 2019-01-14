/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.model.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author djord
 */
public class Masina extends Voz {

    //private String tipVoza = "Lokomotiva";

    public Masina() {
    }

    public Masina(String vozId, String vrstaPogona, String namjena, Double sirinaKolosjeka,String naziv) {
        super(vozId, vrstaPogona, namjena, sirinaKolosjeka,naziv);
        this.tipVoza = "Masina";
    }


    public String getTipVoza() {
        return tipVoza;
    }

    public void setTipVoza(String tipVoza) {
        this.tipVoza = tipVoza;
    }

}
