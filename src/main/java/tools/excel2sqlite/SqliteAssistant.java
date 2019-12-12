package tools.excel2sqlite;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import tools.excel2sqlite.beans.Column;
import tools.excel2sqlite.beans.DataTable;
 
/**
 *
 * @author sqlitetutorial.net
 */
public class SqliteAssistant implements IDatabaseAssistant {


    public boolean createDatabase(String fileName) throws SQLException {
    	 
        String url = "jdbc:sqlite:" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                return true;
            }
 
        } catch (SQLException e) {
            System.out.println("createDatabase" + e.getMessage());
            throw e;
        }
        return false;
    }
    
    /**
     * Create a new table in the test database
     * @throws SQLException 
     *
     */
    public void createTables(List<DataTable> tables , String url) throws SQLException {
        // SQLite connection string
        
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + url);
                Statement stmt = conn.createStatement()) {
        	
        	for(DataTable table : tables)
        	{
                // SQL statement for creating a new table
                StringBuffer sql = new StringBuffer();
                sql.append("CREATE TABLE IF NOT EXISTS "+table.getName()+" (\n");
                       
                for(Column c : table.getColumns())
                {
                	sql.append(this.createColumnString(c) + ",\n");
                }
                sql.delete(sql.length() - 2, sql.length());
                sql.append(");");
                // create a new table
                stmt.execute(sql.toString());
                
                
                fillTable(stmt,table);
                
        	}
        	
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
    
    private String createColumnString(Column column)
    {
    	return column.getName() + " " + 
    			column.getDataType() + " " + 
    			(column.isNotNull()?"NOT NULL ":"")+
    			(column.isPrimaryKey()?"PRIMARY KEY ":"");
    }
    
    private void fillTable(Statement stmt, DataTable table) throws SQLException
    {
    	for(List<Object> row : table.getTableData())
    	{
	        // SQL statement for insert new data
	        StringBuffer sql = new StringBuffer();
	        sql.append("INSERT INTO " + table.getName() + " (\n");
	        
	        for(Column c : table.getColumns())
	        {
	        	sql.append("`" + c.getName() + "`" + ",\n");
	        }
	        sql.delete(sql.length() - 2, sql.length());

	        sql.append(") VALUES(\n");
	        for(Object o : row)
	        {
	        	sql.append("'" + o + "'" + ",\n");
	        }
	        sql.delete(sql.length() - 2, sql.length());
	        sql.append(");");
	        // insert new data
	        stmt.execute(sql.toString());
    	}
        
    }
}