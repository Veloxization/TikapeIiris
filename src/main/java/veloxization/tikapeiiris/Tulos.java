package veloxization.tikapeiiris;

/**
 *
 * @author Veloxization
 */
public class Tulos {
    private String nimi;
    private String maara;
    private String ohje;
    
    public Tulos(String nimi, String maara, String ohje) {
        this.nimi = nimi;
        this.maara = maara;
        this.ohje = ohje;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public String getMaara() {
        return maara;
    }
    
    public String getOhje() {
        return ohje;
    }
}
