
package veloxization.tikapeiiris;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Veloxization
 */
public class AnnosDao implements Dao<Annos, Integer> {
    private Database database;
    
    public AnnosDao(Database database) {
        this.database = database;
    }

    @Override
    public List findAll() throws SQLException {
        List<Annos> annokset = new ArrayList<>();
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM Annos");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Annos a = new Annos(rs.getInt("id"), rs.getString("nimi"));
            annokset.add(a);
        }
        
        rs.close();
        ps.close();
        c.close();
        
        return annokset;
    }
    
    public Annos findOne(Integer key) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM Annos WHERE id = ?");
        ps.setInt(1, key);
        ResultSet rs = ps.executeQuery();
        
        Annos a = new Annos(rs.getInt("id"), rs.getString("nimi"));
        
        rs.close();
        ps.close();
        c.close();
        
        return a;
    }
    
    public Annos findByName(String name) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM Annos WHERE nimi = ?");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        
        Annos a = new Annos(rs.getInt("id"), rs.getString("nimi"));
        
        rs.close();
        ps.close();
        c.close();
        
        return a;
    }

    @Override
    public Annos save(Annos object) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO Annos (nimi) VALUES (?)");
        ps.setString(1, object.getNimi());
        ps.executeUpdate();
        
        ps.close();
        c.close();
        
        return object;
    }
    
    @Override
    public Annos update(Annos object) throws SQLException {
        Connection c = database.getConnection();
        PreparedStatement ps = c.prepareStatement("UPDATE Annos SET nimi = ? WHERE id = ?");
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
        PreparedStatement ps = c.prepareStatement("DELETE FROM Annos WHERE id = ?");
        ps.setInt(1, key);
        ps.executeUpdate();
        
        ps.close();
        c.close();
    }
    
}
