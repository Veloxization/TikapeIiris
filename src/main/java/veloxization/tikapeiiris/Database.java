
package veloxization.tikapeiiris;

import java.sql.*;

/**
 *
 * @author Veloxization
 */
public class Database {

    private String dbAddress;

    public Database(String address) throws Exception {
        this.dbAddress = address;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.dbAddress);
    }
}
