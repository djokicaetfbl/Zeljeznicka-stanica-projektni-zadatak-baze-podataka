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
public class TeretniVagon extends Vagon {

    private String tipTeretnogVagona;
    private double duzinaPrekoOdbojnika, ukupnaVisina, prosjecnaVlastitaMasa, nosivostVagona, povrsinaUnutrasnjosti, zapreminaUnutrasnjosti;

    public TeretniVagon() {
        super();
    }

    public TeretniVagon(String vagonId, String tipTeretnogVagona, double duzinaPrekoOdbojnika, double ukupnaVisina, double prosjecnaVlastitaMasa, double nosivostVagona, double povrsinaUnutrasnjosti, double zapreminaUnutrasnjosti) {
        super(vagonId);
        this.tipTeretnogVagona = tipTeretnogVagona;
        this.duzinaPrekoOdbojnika = duzinaPrekoOdbojnika;
        this.ukupnaVisina = ukupnaVisina;
        this.prosjecnaVlastitaMasa = prosjecnaVlastitaMasa;
        this.nosivostVagona = nosivostVagona;
        this.povrsinaUnutrasnjosti = povrsinaUnutrasnjosti;
        this.zapreminaUnutrasnjosti = zapreminaUnutrasnjosti;
    }

    public String getTipTeretnogVagona() {
        return tipTeretnogVagona;
    }

    public void setTipTeretnogVagona(String tipTeretnogVagona) {
        this.tipTeretnogVagona = tipTeretnogVagona;
    }

    public double getDuzinaPrekoOdbojnika() {
        return duzinaPrekoOdbojnika;
    }

    public void setDuzinaPrekoOdbojnika(double duzinaPrekoOdbojnika) {
        this.duzinaPrekoOdbojnika = duzinaPrekoOdbojnika;
    }

    public double getUkupnaVisina() {
        return ukupnaVisina;
    }

    public void setUkupnaVisina(double ukupnaVisina) {
        this.ukupnaVisina = ukupnaVisina;
    }

    public double getProsjecnaVlastitaMasa() {
        return prosjecnaVlastitaMasa;
    }

    public void setProsjecnaVlastitaMasa(double prosjecnaVlastitaMasa) {
        this.prosjecnaVlastitaMasa = prosjecnaVlastitaMasa;
    }

    public double getNosivostVagona() {
        return nosivostVagona;
    }

    public void setNosivostVagona(double nosivostVagona) {
        this.nosivostVagona = nosivostVagona;
    }

    public double getPovrsinaUnutrasnjosti() {
        return povrsinaUnutrasnjosti;
    }

    public void setPovrsinaUnutrasnjosti(double povrsinaUnutrasnjosti) {
        this.povrsinaUnutrasnjosti = povrsinaUnutrasnjosti;
    }

    public double getZapreminaUnutrasnjosti() {
        return zapreminaUnutrasnjosti;
    }

    public void setZapreminaUnutrasnjosti(double zapreminaUnutrasnjosti) {
        this.zapreminaUnutrasnjosti = zapreminaUnutrasnjosti;
    }

    @Override
    public String toString() {
        return "TeretniVagon{" + "tipTeretnogVagona=" + tipTeretnogVagona + ", duzinaPrekoOdbojnika=" + duzinaPrekoOdbojnika + ", ukupnaVisina=" + ukupnaVisina + ", prosjecnaVlastitaMasa=" + prosjecnaVlastitaMasa + ", nosivostVagona=" + nosivostVagona + ", povrsinaUnutrasnjosti=" + povrsinaUnutrasnjosti + ", zapreminaUnutrasnjosti=" + zapreminaUnutrasnjosti + '}';
    }

}
