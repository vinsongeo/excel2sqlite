package tools.excel2sqlite;

import java.sql.SQLException;
import java.util.List;

import tools.excel2sqlite.beans.DataTable;

public interface IDatabaseAssistant {

    boolean createDatabase(String fileName) throws SQLException ;
    void createTables(List<DataTable> tables , String url) throws SQLException ;
}
