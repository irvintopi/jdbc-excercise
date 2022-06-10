package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Mapper<ENTITY> {

    public abstract ENTITY map(ResultSet result) throws SQLException;

}
