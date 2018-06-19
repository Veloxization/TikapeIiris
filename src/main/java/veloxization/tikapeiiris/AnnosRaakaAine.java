
package veloxization.tikapeiiris;

/**
 *
 * @author Veloxization
 */
public class AnnosRaakaAine {
    private int raakaAineId;
    private int annosId;
    private int jarjestys;
    private String maara;
    private String ohje;
    
    public AnnosRaakaAine(int raakaAineId, int annosId, int jarjestys, String maara, String ohje) {
        this.raakaAineId = raakaAineId;
        this.annosId = annosId;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }
    
    public int getRaakaAineId() {
        return raakaAineId;
    }
    
    public int getAnnosId() {
        return annosId;
    }
    
    public int getJarjestys() {
        return jarjestys;
    }
    
    public String getMaara() {
        return maara;
    }
    
    public String getOhje() {
        return ohje;
    }
}
