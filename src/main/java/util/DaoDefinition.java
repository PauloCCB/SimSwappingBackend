package util;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DaoDefinition<T> extends BeanPropertyRowMapper<T>{
    public DaoDefinition(Class<T> mappedClass){
        super(mappedClass);
    }

    public boolean findColumnaEnResultSet(String columna,ResultSet rs){
        try{
            return rs.findColumn(columna) > 0;
        }catch(SQLException ex){
            return false;
        }
    }

    public boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }
}
