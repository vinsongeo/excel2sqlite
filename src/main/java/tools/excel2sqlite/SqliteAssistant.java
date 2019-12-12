package tools.excel2sqlite;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 *
 * @author sqlitetutorial.net
 */
public class SqliteAssistant {
     /**
     * Connect to a sample database
     */
    public void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:/sqlite/db/chinook.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public boolean createNewDatabase(String fileName) {
    	 
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                return true;
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    /**
     * Create a new table in the test database
     *
     */
    public void createNewTables(DataTable[] tables) {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/tests.db";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
        	
        	for(DataTable table : tables)
        	{
                // SQL statement for creating a new table
                String sql = "CREATE TABLE IF NOT EXISTS "+table.getName()+" (\n";
                       
                for(Column c : table.getColumns())
                {
                	sql += this.createColumnString(c) + ",\n";
                }
                
                sql += ");";
                // create a new table
                stmt.execute(sql);
        	}
        	

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String createColumnString(Column column)
    {
    	return column.getName() + " " + 
    			column.getDataType() + " " + 
    			(column.isNotNull()?"NOT NULL ":"")+
    			(column.isPrimaryKey()?"PRIMARY KEY ":"");
    }
}