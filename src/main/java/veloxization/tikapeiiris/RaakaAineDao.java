
package veloxization.tikapeiiris;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Veloxization
 */
public class RaakaAineDao implements Dao<RaakaAine, Integer> {
    
    private Database database;
    
    public RaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
        List<RaakaAine> raakaAineet = new ArrayList<>();
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM RaakaAine");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            RaakaAine r = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
            raakaAineet.add(r);
        }
        
        rs.close();
        ps.close();
        c.close();
        
        return raakaAineet;
    }
    
    public RaakaAine findByName(String name) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM RaakaAine WHERE nimi = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        
        RaakaAine ra = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        
        rs.close();
        ps.close();
        c.close();
        
        return ra;
    }

    @Override
    public RaakaAine save(RaakaAine object) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO RaakaAine (nimi) VALUES (?)");
        ps.setString(1, object.getNimi());
        ps.executeUpdate();
        
        ps.close();
        c.close();
        
        return object;
    }

    @Override
    public RaakaAine update(RaakaAine object) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE RaakaAine SET nimi = ? WHERE id = ?");
        ps.setString(1, object.getNimi());
        ps.setInt(2, object.getId());
        ps.executeUpdate();
        
        ps.close();
        c.close();
        
        return object;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");
        ps.setInt(1, key);
        ps.executeUpdate();
        
        ps.close();
        c.close();
    }
    
}
