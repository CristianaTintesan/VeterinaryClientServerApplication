package dao;

import java.sql.SQLException;
import java.util.List;

public interface DAOApi<T> {
    T getById(int id) throws SQLException;

    List<T> getAll() throws SQLException;

    void update(int id, List<String> params) throws SQLException;

    void insert(T t) throws SQLException;

    void delete(int id);
}