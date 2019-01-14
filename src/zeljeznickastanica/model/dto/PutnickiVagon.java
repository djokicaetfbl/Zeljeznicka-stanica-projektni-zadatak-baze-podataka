/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.model.dto;

/**
 *
 * @author djord
 */
public class PutnickiVagon extends Vagon {

    int brojMjesta;
    boolean lezajZaSpavanje, toalet, bar, klima, tv, internet, restoran;

    public PutnickiVagon() {
        super();
    }

    public PutnickiVagon(String vagonId, int brojMjesta, boolean lezajZaSpavanje, boolean toalet, boolean bar, boolean klima, boolean tv,
            boolean internet, boolean restoran) {
        super(vagonId);
        this.brojMjesta = brojMjesta;
        this.lezajZaSpavanje = lezajZaSpavanje;
        this.toalet = toalet;
        this.bar = bar;
        this.klima = klima;
        this.tv = tv;
        this.internet = internet;
        this.restoran = restoran;
    }

    public int getBrojMjesta() {
        return brojMjesta;
    }

    public void setBrojMjesta(int brojMjesta) {
        this.brojMjesta = brojMjesta;
    }

    public boolean isLezajZaSpavanje() {
        return lezajZaSpavanje;
    }

    public void setLezajZaSpavanje(boolean lezajZaSpavanje) {
        this.lezajZaSpavanje = lezajZaSpavanje;
    }

    public boolean isToalet() {
        return toalet;
    }

    public void setToalet(boolean toalet) {
        this.toalet = toalet;
    }

    public boolean isBar() {
        return bar;
    }

    public void setBar(boolean bar) {
        this.bar = bar;
    }

    public boolean isKlima() {
        return klima;
    }

    public void setKlima(boolean klima) {
        this.klima = klima;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isInternet() {
        return internet;
    }

    public void setInternet(boolean internet) {
        this.internet = internet;
    }

    public boolean isRestoran() {
        return restoran;
    }

    public void setRestoran(boolean restoran) {
        this.restoran = restoran;
    }

    @Override
    public String toString() {
        return "PutnickiVagon{" + "brojMjesta=" + brojMjesta + ", lezajZaSpavanje=" + lezajZaSpavanje + ", toalet=" + toalet + ", bar=" + bar + ", klima=" + klima + ", tv=" + tv + ", internet=" + internet + ", restoran=" + restoran + '}';
    }
    
    

}
