
package veloxization.tikapeiiris;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Veloxization
 */
public interface Dao<T, K> {
    List<T> findAll() throws SQLException;
    T save(T object) throws SQLException;
    T update(T object) throws SQLException;
    void delete(K key) throws SQLException;
}
