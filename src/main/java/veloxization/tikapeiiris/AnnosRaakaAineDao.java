
package veloxization.tikapeiiris;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Veloxization
 */
public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer> {
    
    private Database database;
    
    public AnnosRaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {
        List<AnnosRaakaAine> annosRaakaAineet = new ArrayList<>();
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM Annos");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            AnnosRaakaAine ar = new AnnosRaakaAine(rs.getInt("raaka_aine_id"), rs.getInt("annos_id"), rs.getInt("jarjestys"),
                                rs.getString("maara"), rs.getString("ohje"));
            annosRaakaAineet.add(ar);
        }
        
        rs.close();
        ps.close();
        c.close();
        
        return annosRaakaAineet;
    }

    @Override
    public AnnosRaakaAine save(AnnosRaakaAine object) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO AnnosRaakaAine (raaka_aine_id, annos_id, jarjestys, maara, ohje)"
                + " VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, object.getRaakaAineId());
        ps.setInt(2, object.getAnnosId());
        ps.setInt(3, object.getJarjestys());
        ps.setString(4, object.getMaara());
        ps.setString(5, object.getOhje());
        ps.executeUpdate();
        
        ps.close();
        c.close();
        
        return object;
    }

    @Override
    public AnnosRaakaAine update(AnnosRaakaAine object) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE AnnosRaakaAine SET raaka_aine_id = ?, annos_id = ?, jarjestys = ?,"
                + " maara = ?, ohje = ? WHERE id = ?");
        ps.setInt(1, object.getRaakaAineId());
        ps.setInt(2, object.getAnnosId());
        ps.setInt(3, object.getJarjestys());
        ps.setString(4, object.getMaara());
        ps.setString(5, object.getOhje());
        ps.executeUpdate();
        
        ps.close();
        c.close();
        
        return object;
    }
    
    public void specialDelete(int annosId, int raakaAineId) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM AnnosRaakaAine WHERE annos_id = ? AND raaka_aine_id = ?");
        ps.setInt(1, annosId);
        ps.setInt(2, raakaAineId);
        ps.executeUpdate();
        
        ps.close();
        c.close();
    }
    
    public List<Tulos> getRaakaAineet(Integer key) throws SQLException {
        List<Tulos> tulokset = new ArrayList<>();
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT RaakaAine.nimi, maara, ohje FROM RaakaAine, Annos, AnnosRaakaAine WHERE RaakaAine.id = AnnosRaakaAine.raaka_aine_id AND Annos.id = AnnosRaakaAine.annos_id AND Annos.id = ? ORDER BY jarjestys ASC");
        ps.setInt(1, key);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            String nimi = rs.getString("nimi");
            String maara = rs.getString("maara");
            String ohje = rs.getString("ohje");
            
            tulokset.add(new Tulos(nimi, maara, ohje));
        }
        
        rs.close();
        ps.close();
        c.close();
        
        return tulokset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM AnnosRaakaAine WHERE annos_id = ?");
        ps.setInt(1, key);
        ps.executeUpdate();
        
        ps.close();
        c.close();
    }
    
}
